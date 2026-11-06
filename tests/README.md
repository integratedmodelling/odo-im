# ODO release check

Requires Java 21, Maven, OWLAPI 5.5.1, and HermiT 1.4.5.519. The small POM only
resolves validation dependencies; it does not change any product version.

From the repository root, in PowerShell:

```powershell
$odoClasspathFile = Join-Path $env:TEMP ('odo-validation-' + [guid]::NewGuid() + '.txt')
mvn -o -f tests/pom.xml org.apache.maven.plugins:maven-dependency-plugin:3.6.1:build-classpath "-Dmdep.outputFile=$odoClasspathFile"
$odoValidationClasspath = Get-Content -LiteralPath $odoClasspathFile -Raw
java -cp $odoValidationClasspath tests/DomainReleaseCheck.java ../klab-services/klab.services.reasoner/src/main/resources/knowledge/odo.owl
Remove-Item -LiteralPath $odoClasspathFile
```

The optional final argument checks a bundled consumer. Omit it for standalone
release checks. Offline Maven requires cached dependencies; remove `-o` only when
dependency downloads are permitted. The machine-specific classpath is written to a
unique temporary file outside the repository. Java source-file mode does not emit
class files into the repository. No `.gitignore` exception is needed.

The check proves version metadata, numbered-release/consumer byte parity, ontology
consistency, domain disjointness and nonexhaustiveness, individual domain satisfiability,
the distinction between Epistemic and KnowledgeDomain, and no new unsatisfiable named
classes compared with 0.1.0. It checks interpretation typing, the minimum-one
restriction, and satisfiability with multiple physical targets and multiple interpreters.
An explicit open-world fixture also proves that a KnowledgeDomain individual without
an asserted link remains OWL-consistent. It does not implement or test the pending
k.LAB explicit interpretation-link validation.

The working 0.1.1 checks additionally cover the approved Domain-to-Configuration
description property, its isolation from the Quality range, independent inherited
witnesses, universal/cardinality conflicts, and absence of instance assertions in
the configuration-description fixture. Consumer typed dispatch and bearer-isolation
tests live in `WorldviewDeclarationSupportTest`. Whole-candidate OWL acceptance,
logical rejection and preservation of the prior accepted generation are covered
by `SemanticEnvironmentFactoryTest` and `CandidatePublicationCoordinatorTest` in
klab-services.

The current check targets ODO 0.2.1, preserving historical 0.1.0, 0.1.1 and 0.2.0 bytes.
It additionally verifies the annotated observational-containment vocabulary without
mereological/inverse/transitive implications, Configuration without a required native
Countable bearer, and satisfiable Process emergence ingredients. Parser/transport and
asserted-min-zero contract tests remain in the Services suites. Numbered-release/consumer
bytes must agree; the scoped LF attributes preserve the exact committed bytes.

The 0.2.1 checks also cover abstract CollectiveSubject and CollectiveAgent roots,
their distinct and nonexhaustive kinds, absence of observational collection and
containment implications, and independence of member agency from group agency.
The check also rejects reintroduction of the retired `releases/latest/odo.owl`
copy. Use the README's commit-pinned numbered release URL for distribution;
consumers of the former latest URL must migrate before that URL disappears.

Energy must inherit ExtensivePhysicalProperty and remain satisfiable, without
IntensivePhysicalProperty ancestry and with its joule unit preserved. Consumer
measurement and derivative checks accompany this candidate-only correction.
