import java.nio.file.*;
import java.util.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.HermiT.Reasoner;

/** Run from the repository root using OWLAPI 5.5.1 and HermiT 1.4.5.519. */
public class DomainReleaseCheck {
  static void check(boolean value, String message) {
    if (!value) throw new AssertionError(message);
  }
  public static void main(String[] args) throws Exception {
    var release = Path.of("releases/0.1.1/odo.owl");
    check(Arrays.equals(Files.readAllBytes(release),
        Files.readAllBytes(Path.of("releases/latest/odo.owl"))), "latest differs");
    if (args.length > 0) check(Arrays.equals(Files.readAllBytes(release),
        Files.readAllBytes(Path.of(args[0]))), "consumer differs");
    var manager = OWLManager.createOWLOntologyManager();
    var baseline = manager.loadOntologyFromOntologyDocument(Path.of("releases/0.1.0/odo.owl").toFile());
    var ontology = manager.loadOntologyFromOntologyDocument(release.toFile());
    var f = manager.getOWLDataFactory();
    String ns = "http://integratedmodelling.org/odo#";
    check(ontology.getOntologyID().getVersionIRI().orElseThrow().toString()
        .equals("http://integratedmodelling.org/odo/0.1.1"), "version IRI");
    check(ontology.annotations().anyMatch(a -> a.getProperty().equals(f.getOWLVersionInfo())
        && a.getValue().asLiteral().map(v -> v.getLiteral().equals("Version 0.1.1")).orElse(false)),
        "version info");
    var oldReasoner = new Reasoner.ReasonerFactory().createReasoner(baseline);
    var reasoner = new Reasoner.ReasonerFactory().createReasoner(ontology);
    try {
      check(oldReasoner.isConsistent(), "baseline inconsistent");
      check(reasoner.isConsistent(), "release inconsistent");
      var domain = f.getOWLClass(IRI.create(ns + "Domain"));
      var epistemic = f.getOWLClass(IRI.create(ns + "Epistemic"));
      var kinds = List.of("PhysicalDomain", "KnowledgeDomain", "NormativeDomain").stream()
          .map(n -> f.getOWLClass(IRI.create(ns + n))).toList();
      check(ontology.containsAxiom(f.getOWLSubClassOfAxiom(domain, epistemic)), "Domain parent changed");
      for (var kind : kinds) {
        check(ontology.containsAxiom(f.getOWLSubClassOfAxiom(kind, domain)), "missing Domain parent");
        check(reasoner.isSatisfiable(kind), "unsatisfiable " + kind);
      }
      for (int i=0; i<kinds.size(); i++) for (int j=i+1; j<kinds.size(); j++)
        check(!reasoner.isSatisfiable(f.getOWLObjectIntersectionOf(kinds.get(i), kinds.get(j))),
            "domain branches overlap");
      check(reasoner.isSatisfiable(f.getOWLObjectIntersectionOf(domain,
          f.getOWLObjectComplementOf(f.getOWLObjectUnionOf(new HashSet<>(kinds))))), "exhaustive branches");
      check(reasoner.isSatisfiable(f.getOWLObjectIntersectionOf(epistemic,
          f.getOWLObjectComplementOf(kinds.get(1)))), "Epistemic equated with KnowledgeDomain");
      var interprets = f.getOWLObjectProperty(IRI.create(ns + "interpretsPhysicalDomain"));
      var physical = kinds.get(0);
      var knowledge = kinds.get(1);
      check(ontology.containsAxiom(f.getOWLObjectPropertyDomainAxiom(interprets, knowledge)), "interpretation source");
      check(ontology.containsAxiom(f.getOWLObjectPropertyRangeAxiom(interprets, physical)), "interpretation target");
      check(ontology.containsAxiom(f.getOWLSubClassOfAxiom(knowledge,
          f.getOWLObjectSomeValuesFrom(interprets, physical))), "missing interpretation existential");
      check(!reasoner.isSatisfiable(f.getOWLObjectIntersectionOf(knowledge,
          f.getOWLObjectMaxCardinality(0, interprets, physical))), "KnowledgeDomain permits no interpretation");
      check(reasoner.isSatisfiable(f.getOWLObjectIntersectionOf(knowledge,
          f.getOWLObjectMinCardinality(2, interprets, physical))), "multiple physical targets disallowed");
      check(reasoner.isSatisfiable(f.getOWLObjectIntersectionOf(physical,
          f.getOWLObjectMinCardinality(2, interprets.getInverseProperty(), knowledge))), "multiple interpreters disallowed");
      // Deliberately satisfiable: OWL can supply an anonymous witness. This is why
      // k.LAB must separately reject authored KnowledgeDomains with no explicit link.
      var witnessOntology = manager.createOntology(IRI.create("urn:odo-test:open-world"));
      manager.addAxioms(witnessOntology, ontology.getAxioms());
      manager.addAxiom(witnessOntology, f.getOWLClassAssertionAxiom(knowledge,
          f.getOWLNamedIndividual(IRI.create("urn:odo-test:unlinkedKnowledge"))));
      var witnessReasoner = new Reasoner.ReasonerFactory().createReasoner(witnessOntology);
      try { check(witnessReasoner.isConsistent(), "open-world witness unexpectedly rejected"); }
      finally { witnessReasoner.dispose(); }
      var before = oldReasoner.getUnsatisfiableClasses().getEntitiesMinusBottom();
      var after = reasoner.getUnsatisfiableClasses().getEntitiesMinusBottom();
      check(before.equals(after), "named-class satisfiability regression: " + after);
      System.out.println("PASS: versions, byte parity, consistency, disjointness, nonexhaustiveness, Epistemic distinction, interpretation typing/existential/many-to-many, open-world validation boundary.");
      System.out.println("Baseline unsatisfiable named classes (unchanged): " + before);
    } finally { oldReasoner.dispose(); reasoner.dispose(); }
  }
}
