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

The check proves version metadata, release/latest/consumer byte parity, ontology
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
the configuration-description fixture. Consumer compiler acceptance, rejection,
bearer isolation and atomic failure tests live in `ConfigurationDescriptionSupportTest`.
