# Worldview semantics and ODO 0.1.1

Status: approved normative contracts; ODO foundation implemented locally, other implementation tracks pending.
Date: 2026-10-12.

This repository document is canonical. The [discussion Page](https://chatgpt.com/space/page_7884472cce9c8191bd8ffcd4f9bab73a)
is a mirror (reported revision 8 in the supplied follow-up); it has not been read or updated
as part of this change. Repository decisions take precedence. Agreed contracts below are
normative; `ONTOLOGY_LANGUAGE.md` examples and current implementation are not conformance
oracles. All worldview statements are universals. Particulars and same-instance binding
belong to models and digital twins.

## Release authority and migration

The numbered release in integratedmodelling/odo-im is authoritative. The baseline is
`aa2cc5611799ceed2aad52ae2c2a349da29e3c4b`. The intended prior release was 0.1.0;
its `versionIRI` and `versionInfo` incorrectly say 0.10.0. Preserve the historical
`releases/0.1.0` artifacts byte for byte. Prepare 0.1.1 with version IRI
`http://integratedmodelling.org/odo/0.1.1` and versionInfo `Version 0.1.1`.
The ontology IRI and term namespace remain unchanged. `releases/latest/odo.owl`
and updated consumer bundles must be byte-identical to `releases/0.1.1/odo.owl`.
The historical `odo.properties` is an empty JDBC editor configuration, not release
metadata; it is preserved historically and not propagated. No project/POM version bump is needed.

## Settled domain contract

`PhysicalDomain`, `KnowledgeDomain`, and `NormativeDomain` are subclasses of `Domain`,
pairwise disjoint and nonexhaustive. There is no covering union or equivalence axiom.
Detailed articulation belongs in each worldview.

Knowledge is descriptive/explanatory. Normative concerns what is prescribed, required,
permitted, or ought to be. The distinction is semantic function, not subject matter.
`Domain` remains under the technical `Epistemic` class; that placement
does not make `Epistemic` equivalent to `KnowledgeDomain`.

Every KnowledgeDomain must explicitly identify at least one PhysicalDomain it
interprets. Multiple physical targets and multiple knowledge domains per physical
target are permitted. The user approved **`interpretsPhysicalDomain`** on 2026-10-12.
The OWL property is implemented in this increment.
OWL typing: KnowledgeDomain -> PhysicalDomain, with an existential
restriction on KnowledgeDomain, no functional/inverse-functional or maximum-cardinality axiom.
An OWL existential alone cannot enforce an explicitly declared target under the
open-world assumption. k.LAB must reject missing or wrongly typed explicit links
before OWL translation. The existential is implemented; explicit authored-link
validation remains assigned to the validation/domain-ingestion track. Neither the
existential nor the annotation implements that explicit-link check. Routine OWL
property names may be selected consistently; actual language forms and semantic
changes still require review.

The approved compositional syntax direction is `KnowledgeDomain about Earth`,
rather than a dedicated `interprets` clause. Approval was conditional on a grammar
check; the independent reviewer reported that `about` is unused in the `.xtext`
grammars and that `Worldview.xtext` lines 19 and 44 already accept `ConceptExpression`,
so only a new modifier is needed. The subject-matter relation maps to
`interpretsPhysicalDomain` and remains separate from `INHERENT` and bearer
reconciliation. An owning ontology's domain being `about Earth` does not make Earth
the bearer of all its qualities.

A specialized domain may inherit explicitly authored grounding: effective authored
provenance through inheritance counts toward the required physical interpretation.
A generic OWL anonymous witness does not satisfy this requirement. The exact syntax
for multiple targets, including list versus intersection, remains open. Implementation
has newly started in a separate local task and is not complete.

ABOUT representation remains a general, extensible subject-matter relation. The user
envisions later norm-related concepts and prefers avoiding unnecessary new keywords.
Currently accepted validation is limited to KnowledgeDomain -> PhysicalDomain;
future endpoint types and their semantic contracts are unspecified. Generality of
the representation does not authorize additional accepted endpoints or semantics.

Geography -> Earth is intended; the existing Physical-tagged geography is the migration
baseline. An `in domain` preamble applies to owned declarations, including nested ones;
imports and references retain their original domains. Multiple-domain grammar is deferred.
Concrete Knowledge qualities require effective describes grounding, including inherited
grounding; abstract roots may defer it. A Normative framework must be identifiable;
its absence is unresolved and cannot certify compliance.

## Describes and bearer constraints

The general describes contract is relaxed; specializations may impose stronger bounds.
The default is compatible universal bearers; cross-bearer cases require an explicit
relationship. Infer missing X constraints from Y, retain narrower constraints, and refine
compatible constraints through intersection. Reject proven unsatisfiable combinations;
unknown compatibility is unresolved. Preserve the full authored, inherited, and derived
constraint expression. Refine X or the local expression without globally redefining Y.
Substantial Y itself serves as bearer when the relation explicitly characterizes Y;
do not guess an ultimate bearer. Sharing the same Location type never binds the same
Location individual.

## Typed characterization and specialized relations

`as V` is typed, one-way necessary characterization. The converse requires an explicit
sufficient definition. It does not instantiate, compute, or generate events; zero flow
alone is not a cessation transition.

Scalar equality follows compatible unit conversion: 3 and 3.0 match. A configured runtime
tolerance may permit nearby values; never truncate 3.7 to 3. Ranges have explicit inclusive
or exclusive endpoints. Identity/authority category closure includes the category itself
and its specializations; unknown ancestry remains unresolved.

`increases` is nondecreasing and `decreases` nonincreasing, permitting plateaus with
relevant conditions and scope fixed. These are not causal; causality remains in
`causing` / `caused by`. `marks` is Boolean characterization without built-in truth or
threshold assumptions; models interpret values. `classifies` implies neither exclusivity
nor exhaustiveness by default. Only `discretizes` requires numeric nonoverlap, contiguity,
and complete coverage of the declared range, with each boundary assigned exactly once;
coverage outside the declared range is not required. Start with strict validation.

Validate target/scope applicability, types, units, inherited refinement, range containment,
and contradictions. Models bind bearers and context and evaluate observations. Normative
required/permitted values do not establish actual state; compliance requires both the
requirement and an observation.

## Subjectivity and visibility

Subjectivity anywhere in an observable propagates through every operator, filler, and
value. Any subjective output or dependency makes the whole model private to its own
namespace. Public declarations, same-project access, and scenarios cannot bypass this
restriction. The namespace modeler controls meaning.

## Abstract and any resolution

`any` does not reclassify a concrete ontology concept; concrete self is a possible candidate.
Enumerate abstract predicates and `any X` predicates inside the original observable.
Resolve each first by CLASSIFICATION into concrete closures, form the Cartesian product
of those sets, substitute each combination into the original observable, and mark the
resulting dependencies optional. Successful empty classification is a valid empty
expansion and the enclosing model may continue. Failure and unknown remain distinct
from empty. Do not enumerate naive descendants of the whole observable. Preserve
canonical immutability.

Occurrences of the same abstract or `any` predicate at distinct relationship roles,
bearers, or positions have independent classification and substitution choices.
For example, Forest/Wetland choices at two independent endpoints produce four
combinations, not two. Shared substitution requires explicit semantic binding;
identical names, text, or AST identity are insufficient. A predicate may itself be
a role whose identity depends on its relationship position. Classification request
and cache identity must preserve context, role, and position. The representation
and syntax of explicit co-reference have not been selected.

Classification prerequisite stages belong to the enclosing observation attempt
transaction. Newly staged results roll back when the parent attempt fails; already
committed classifications remain reusable. The user explicitly approved this contract
and identified deferred resolutions as needing the same transaction boundary. This
does not approve automatic retry or cycle policies, numeric defaults, or evidence-retention
choices. Runtime foundation implementation has separately started; it is not complete.

Missing classification information is an explicit pending state, eligible to resume
when prerequisites change. Successful empty classification remains a valid outcome;
execution errors are failures. There are no automatic timed retries unless configured.
This approved deferred lifecycle does not select the remaining retry/cycle policies
or evidence-retention choices.

## Runtime and matching

Transport and enforce the target, complete bearer expression, scope, modality, typed
value, units, and provenance. These constrain valid use without computing scientific
values. Runtime implementation follows a design checkpoint.

Shared twins do not exist: cross-twin matching is specification-only. After aligning
target, semantics, units, scope, and modality, provider guarantees must be a subset of
consumer acceptance. Overlap is insufficient; unresolved alignment is not incompatibility.
No access grants or synthesized computations follow from matching.

Observation strategies express scientific and contextualization pathways where
applicable. The core workflow manages generic readiness, coverage, continuation,
snapshots, and transactions. The user confirms the original deferred-resolution
direction and notes that models in specialized contexts may cover an observable by
merging qualities in instantiated secondary substantials. This example motivates the
strategy/core design distinction; it is not a claim of an implemented feature. The
runtime-design task is inspecting the exact API split.

Value operators, including `whose`, remain a major resolver gap. Current parsing,
transport, and subjectivity preservation do not implement their evaluation.

## Multi-project worldview ingestion and subscriptions

A worldview comes from multiple projects through incremental contributions. The
reasoner must record supplying-service and project provenance in a subscription
registry, with a subscription contract supporting periodic polling for changes or
requested notifications. The user explicitly approved this direction and rejected
the current atomic source-ingestion model.

Incremental source ingestion is distinct from atomic activation of a coherent,
validated operational snapshot. Assemble and validate changes in an isolated candidate;
an invalid or unresolved candidate preserves the prior valid snapshot. Publication
here means activation of the operational snapshot, not external release publication.
There is no requirement for a distributed atomic load of all source projects or
services. Polling intervals, notification protocols, and removal policies remain open.
A design-first task has started; the contract is not yet implemented and no live
subscriptions are claimed.

## Validation responsibility and implementation tracks

k.LAB validates before OWL. Invalid authored input receives diagnostics. Accepted input
that produces inconsistent OWL is a validator/translator software bug.

Conformance testing must cover:

- Domain ownership, disjointness, interpretation links, and Knowledge grounding.
- Bearer derivation, typed `as` necessary-only semantics, category closure, unit tolerance,
  range endpoints, and discretization.
- Subjectivity across all operators and every model visibility path.
- Abstract/any classification, Cartesian expansion, optional dependencies, empty versus
  failure outcomes, and canonical immutability.
- Parser-adapter-reasoner-catalog serialization, restart behavior, and cache separation
  of X from `any X`.
- Runtime transport and enforcement, and specification-only cross-twin matching.

Subjectivity, classification expansion, validation, and runtime design are assigned to
other isolated tracks. This increment owns ODO release artifacts and canonical decisions;
the listed contracts are not a claim that those other implementations are complete.

## Descriptive relation matrix: proposal for review

This matrix is an **unapproved option, not an accepted recommendation**. In particular,
an unrestricted parent exceeds the settled wording that general describes is relaxed.
Positive and negative source/target examples are required before choosing its bounds
or language-to-OWL mapping. No describes hierarchy change is authorized by this table.

The release already declares all five specializations below as subproperties of
`describesQuality`. They must not be redundantly reintroduced or placed below `affects`.
There is no standalone OWL property named `describes` in the inspected source.

| Relation | Current OWL source | Current OWL target | Proposed source | Proposed target |
| --- | --- | --- | --- | --- |
| General `describes` (language concept; proposed OWL property) | absent | absent | unrestricted (`owl:Thing`, by omitting domain) | unrestricted (`owl:Thing`, by omitting range) |
| `describesQuality` | Predicate union Quality | Quality | Predicate union Quality | Quality |
| `increasesWith` | Quality | Quality | Quality | Quality |
| `decreasesWith` | Quality | Quality | Quality | Quality |
| `marksQuality` | Quality | Presence | Quality | Presence |
| `classifiesQuality` | Predicate | Quality | Predicate | Quality |
| `discretizesQuality` | Ordering | QuantifiableQuality | Ordering | QuantifiableQuality |
| `interpretsPhysicalDomain` (approved; implemented) | absent in 0.1.0 | absent in 0.1.0 | KnowledgeDomain | PhysicalDomain |

The option shown is to introduce a broad `describes` parent and retain the
existing specialized bounds under `describesQuality`. This still requires agreement
on the language-to-OWL mapping: if language `describes` continues to map directly to
`describesQuality`, relaxing only a new parent would not relax that language relation.
No changes to this hierarchy or its domain/range axioms are made before that decision.
Any further specialization typing changes await approval of the open type matrix. OWL domain
and range axioms infer membership; k.LAB validation is the admissibility boundary.
Causality stays separate in `causing` / `caused by`; descriptive links do not imply it.

## Consumer inventory

Local search under `C:/Users/Ferd/git` found these bundles:

| Repository and path | Baseline | Disposition |
| --- | --- | --- |
| klab-services: `klab.services.reasoner/src/main/resources/knowledge/odo.owl` | byte-identical to authoritative 0.1.0; 89 class and 59 object-property declarations | updated in isolated consumer branch |
| klab: `klab.engine/src/main/resources/knowledge/odo.owl` | divergent; 85 class and 40 object-property declarations | unchanged; migration compatibility decision needed |
| diocan: `klab.engine/src/main/resources/knowledge/odo.owl` | divergent; 85 class and 40 object-property declarations | unchanged; migration compatibility decision needed |

Baseline SHA-256 values:

- authoritative 0.1.0, former latest, klab-services: `20fdf58f620017620443d739305ed19e009069fa1d70f6467cdfbcacb7011f99`
- klab: `26441f53cab95b2abc6eb15063b2634d54d323045530166c7afbfaf883806c6`
- diocan: `55c5c7731dc89af1589f8fe81b8463ef25284857d349a7f3b9a79bd0a32b6782`

## Acceptance

Acceptance includes matching release metadata and consumer bytes, unchanged historical
artifacts, pairwise disjoint but individually satisfiable domain classes, satisfiable
Domain outside the three branches, and no regression in ontology consistency or
previously satisfiable named classes. These checks do not substitute for the pending
k.LAB validation work.

Verified locally on 2026-10-12 with OWLAPI 5.5.1 and HermiT 1.4.5.519:
baseline and new ontology consistent; zero unsatisfiable named classes in either;
all three domain branches individually satisfiable and pairwise disjoint; Domain
outside their union satisfiable; Epistemic outside KnowledgeDomain satisfiable.
Version metadata and exact release/latest/reasoner-bundle parity passed, as did XML
parsing and Git whitespace checks. Historical release has no Git diff (Windows
checkout newline normalization can change raw working-copy hashes).
The full newly staged numbered copy retains pre-existing trailing whitespace and
the final blank line from 0.1.0. Checks pass for newly authored lines and all other
staged files; inherited formatting is preserved to retain the reviewed artifact bytes.
After adding the approved interpretation property and existential, the extended
test passed typing, minimum-one restriction, multiple-target/multiple-interpreter
satisfiability, and the open-world explicit-link boundary fixture. The complete
baseline/regression checks passed again. The synchronized working-copy SHA-256 is
`2c152c15e44db0e8c2bf41e21a08671e16b80d10373ac7d2efa95eced1b1b90e`.
See `tests/README.md` and `tests/DomainReleaseCheck.java` for reproduction.
No full k.LAB application suite was run for this artifact-only consumer change.

## Open choices and remaining coordination

These are proposals or unresolved choices, not approved contracts:

1. Exact multi-target syntax (list versus intersection) for the approved `about`
   modifier, and relation naming/syntax for the normative framework. The OWL name
   `interpretsPhysicalDomain` and the compositional `about` direction are approved;
   modifier implementation and effective authored-link validation remain in progress.
2. Broadened describes source/target matrix and language-to-OWL mapping (proposal above).
3. Runtime tolerance default and algorithm.
4. Exact-only category syntax and explicit sufficient-definition syntax.
5. Runtime schema, enforcement, and error policy, following the design checkpoint.
6. Expansion limits, deduplication, ordering, and failure diagnostics.
7. Institutional/evaluative domain placement.
8. Future ABOUT endpoint types and contracts beyond KnowledgeDomain -> PhysicalDomain.
9. Worldview subscription polling intervals, notification protocols, and removal policies.

Migration of the divergent legacy bundles and mirroring this repository document to
the discussion Page also remain coordination work. Multiple-domain grammar is deferred.
