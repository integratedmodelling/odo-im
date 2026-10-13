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
      var configuration = f.getOWLClass(IRI.create(ns + "Configuration"));
      var quality = f.getOWLClass(IRI.create(ns + "Quality"));
      var describesConfiguration = f.getOWLObjectProperty(IRI.create(ns + "describesConfiguration"));
      var describesQuality = f.getOWLObjectProperty(IRI.create(ns + "describesQuality"));
      check(ontology.containsAxiom(f.getOWLObjectPropertyDomainAxiom(describesConfiguration, domain)),
          "configuration description source");
      check(ontology.containsAxiom(f.getOWLObjectPropertyRangeAxiom(describesConfiguration, configuration)),
          "configuration description target");
      check(!reasoner.isEntailed(f.getOWLSubObjectPropertyOfAxiom(describesConfiguration, describesQuality)),
          "configuration descriptions inherit quality range");
      check(ontology.getAxioms(AxiomType.OBJECT_PROPERTY_RANGE).stream().filter(a -> a.getProperty().equals(describesQuality))
          .allMatch(a -> a.getRange().equals(quality)), "quality description range changed");
      var descriptionFixture = manager.createOntology(IRI.create("urn:odo-test:configuration-description"));
      manager.addAxioms(descriptionFixture, ontology.getAxioms());
      var earth = f.getOWLClass(IRI.create("urn:odo-test:EarthSystem"));
      var mars = f.getOWLClass(IRI.create("urn:odo-test:MarsSystem"));
      var parent = f.getOWLClass(IRI.create("urn:odo-test:EarthDomain"));
      var child = f.getOWLClass(IRI.create("urn:odo-test:MultiDomain"));
      manager.addAxioms(descriptionFixture, Set.of(
          f.getOWLSubClassOfAxiom(earth, configuration), f.getOWLSubClassOfAxiom(mars, configuration),
          f.getOWLDisjointClassesAxiom(earth, mars), f.getOWLSubClassOfAxiom(parent, physical),
          f.getOWLSubClassOfAxiom(parent, f.getOWLObjectSomeValuesFrom(describesConfiguration, earth)),
          f.getOWLSubClassOfAxiom(child, parent),
          f.getOWLSubClassOfAxiom(child, f.getOWLObjectSomeValuesFrom(describesConfiguration, mars))));
      var descriptionReasoner = new Reasoner.ReasonerFactory().createReasoner(descriptionFixture);
      try {
        check(descriptionReasoner.isSatisfiable(child), "independent configuration witnesses conflated");
        check(!descriptionReasoner.isEntailed(f.getOWLSubClassOfAxiom(parent, quality)), "Domain inferred Quality");
        check(!descriptionReasoner.isEntailed(f.getOWLSubClassOfAxiom(earth, quality)), "Configuration inferred Quality");
        check(!descriptionReasoner.isSatisfiable(f.getOWLObjectIntersectionOf(child,
            f.getOWLObjectMaxCardinality(1, describesConfiguration, configuration))), "maximum-one conflict missed");
        check(!descriptionReasoner.isSatisfiable(f.getOWLObjectIntersectionOf(parent,
            f.getOWLObjectAllValuesFrom(describesConfiguration, mars))), "universal conflict missed");
        check(descriptionFixture.getAxioms(AxiomType.CLASS_ASSERTION).isEmpty(), "description fixture uses instances");
      } finally { descriptionReasoner.dispose(); }
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
      System.out.println("PASS: describesConfiguration typing, quality-range isolation, independent inherited witnesses, universal/cardinality conflicts, class-level translation.");
    } finally { oldReasoner.dispose(); reasoner.dispose(); }
  }
}
