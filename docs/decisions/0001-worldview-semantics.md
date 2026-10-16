# Worldview semantics and ODO 0.1.1

Status: approved normative contracts; ODO foundation and reviewed language/services changes merged locally; runtime gaps remain.
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

Every authored KnowledgeDomain specialization must explicitly identify at least
one PhysicalDomain it interprets. Exact generic category ownership has the narrow
exception approved below. Multiple physical targets and multiple knowledge domains per physical
target are permitted. The user approved **`interpretsPhysicalDomain`** on 2026-10-12.
The OWL property is implemented in this increment.
OWL typing: KnowledgeDomain -> PhysicalDomain, with an existential
restriction on KnowledgeDomain, no functional/inverse-functional or maximum-cardinality axiom.
An OWL existential alone cannot enforce an explicitly declared target under the
open-world assumption. k.LAB must reject missing or wrongly typed explicit links
before OWL translation. The existential and reviewed ABOUT authored-grounding
validation are implemented in the local checkpoint below. Neither the existential
nor the annotation alone implements that explicit-link check. Routine OWL
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
A generic OWL anonymous witness does not satisfy this requirement. ABOUT semantics
and authored-grounding handling passed independent review and are merged locally;
this does not imply installed artifacts, live activation, or completed runtime behavior.

The approved multiple-target convention is repeated modifiers:
`KnowledgeDomain about Earth about Society` requires separate subject-matter links
to Earth and Society. `KnowledgeDomain about (A and B)` instead has one composite
target. These examples remain subject to the current KnowledgeDomain -> PhysicalDomain
type constraints; the syntax does not independently establish the types of named
targets. No comma alternative is approved. Language associativity, precedence, and
explicit grouping must make the distinction clear to the reader/modeler. Canonical
serialization and parse/serialize roundtrips must preserve target parentheses and
source-branch scope, without merging separate links, splitting a composite target,
or moving an ABOUT modifier to a different source branch.

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

### Generic Knowledge category ownership (approved 2026-10-15)

An ontology preamble selecting precisely the canonical generic `odo:KnowledgeDomain`
may remain at generic category ownership without an authored physical interpretation
link. A pure core alias such as `imod:Knowledge` denotes that same canonical
selection. This exception concerns that exact generic selection, not any expression
that merely has KnowledgeDomain among its types or ancestors.

Worldviews deepen through layers of abstraction. A non-root ontology may select an
abstract domain category before articulating a subject-matter specialization. Such
category ownership does not invent an ABOUT target or assert that an anonymous OWL
existential witness is an authored physical interpretation.

Every distinct authored KnowledgeDomain specialization still requires an explicitly
authored or inherited PhysicalDomain interpretation. Abstract specializations are
included: this is not a blanket abstract-domain exemption. Composed or specialized
domain expressions do not receive the exact-selection exception. Wrongly typed or
unknown ABOUT targets remain invalid; no authored grounding may be fabricated on
the canonical generic KnowledgeDomain to satisfy a consumer.

Concrete Knowledge-owned qualities still require effective `describes` grounding,
including when their owning ontology selects the generic category. The exception
does not relax quality-description validation, domain kind disjointness, ABOUT
endpoint typing, or the separate treatment of bearer inherency. The existing OWL
existential remains unchanged.

This approved contract requires a narrowly scoped services change and independent
review and whole-candidate validation before applying the ontology migration.
This documentation change does not certify implementation, deployment or live
activation; it changes no grammar, services source, OWL or release artifact.

## Describes and bearer constraints

### General Observable-range description (approved 2026-10-15)

Plain language `describes` may target any **Observable**, including a Quality,
Configuration, Substantial, Process, Event or Relationship. Specialized clauses
retain their tighter contracts. This clarifies the earlier approval that general
describes is relaxed; the former Quality-only dispatch plus Domain-to-Configuration
exception did not express the intended general range.

Retain the existing plain-source bound **Predicate union Quality**, including
Domain through its Predicate ancestry. Broadening the target does not narrow the
source to Domain or Quality alone and does not authorize an unrelated source
expansion. A Knowledge-owned description need not target a Physical-owned concept;
that is at most a convention to consider, not an endpoint validation rule.

Introduce `odo:describes` with that source union and range `odo:Observable`.
`describesQuality` and `describesConfiguration` are direct subproperties of it.
Keep their existing domains and ranges: Predicate union Quality to Quality, and
Domain to Configuration, respectively. All five specialized quality predicates
remain under `describesQuality`, with their own bounds unchanged. Do not put the
general property below either narrower property: inherited OWL domain/range
intersections would incorrectly coerce general sources or targets.

Ordinary language `describes` maps to the general property. Necessary restrictions
use `SubClassOf` with existential fillers, without equivalence, reverse sufficiency,
individuals, exclusive targets or cardinalities. Independent restrictions can use
independent witnesses. A descriptive link is not causation or a computation; models
enact change and may validly return an unchanged result after re-evaluation.

Concrete Knowledge-owned qualities still require effective description grounding,
including inherited general or specialized descriptions. This includes descriptions
of Configurations; they are observer-recognized patterns, not measurement datasets.
The generic Knowledge-category selection exception remains unchanged. ABOUT and
its explicit PhysicalDomain grounding remain separate from description targets.

Preserve the universal bearer compatibility, inference and refinement contract
below. An Observable target's type alone does not identify its ultimate bearer.
Distinguish its own bearer constraints from the target itself; a Substantial may
serve as bearer when the relation explicitly characterizes it. Preserve full
authored/inherited/derived conjunctions and provenance; retain narrower constraints,
refine compatible ones locally, reject proven impossibility, and keep uncertainty
unresolved. Do not mutate a shared target or bind different existential witnesses
to one particular. Cross-bearer interpretations require an explicit relationship.
Domain subject matter must not automatically become an owned quality's bearer.

The prior 2026-10-13 Domain-to-Configuration addition remains valid through the
narrow property. Its former status as the only non-Quality exception, exclusion of
Configuration descriptions from quality grounding, and blanket bypass of bearer
analysis are superseded by this general contract and kind-aware reconciliation.

ODO 0.1.1 remains a working version. Numbered/latest candidate bytes must agree;
historical 0.1.0 stays byte-identical. Services translation, grounding recognition,
queries and bearer validation must be reviewed and tested together before local
integration. This approval does not certify installed artifacts or runtime behavior.

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

## Approved identity-induced abstractness (2026-10-16)

The owner approved implementing identity-driven de-abstraction for all observable
kinds, including dependent observables and processes. The owner also explicitly
approved this distinction: an authored `abstract` keyword remains an independent
restriction; `requires identity` induces separately dischargeable abstractness.

A declaration requiring an identity root is abstract for semantic resolution until
suitable concrete identity evidence satisfies that requirement. Effective requirements
include inherited and multiple requirements. Satisfying one does not erase others,
an explicit abstract declaration, or independent abstract components. Ordinary
identity hierarchies and authority identities are admitted according to their own
compatibility and authority restrictions; namespace similarity is not suitability.
The declaration stays unchanged. Worldview concepts remain universals: no particular
observation is created and no existential witnesses are bound to the same individual.

Existing declarations combining explicit `abstract` with identity requirements need
deliberate migration if identity was intended as their only abstraction source.
This includes physical:Depth, physical:Height and physical:Width in the current
worldview. ArtifactProduction and ArtifactModification retain independent abstractness.
No worldview migration is authorized by this implementation patch.

An isolated services implementation and tests are prepared for independent review;
this decision does not certify integration, installed artifacts or live behavior.
The prior dated discussion below is historical; its open dependent scope and
missing explicit-keyword distinction are superseded by this approval. The open
question concerning `type of <identity>` is unchanged.

### Prior provisional discussion (2026-10-15)

**Early design direction; not an implemented global concretization rule.**
The user proposes that a declaration requiring an identity root from a known
authority is abstract. An observable or composed expression supplied a concrete,
suitable identity from that authority may become concrete; the original declaration
itself remains abstract. This is about semantic expressions suitable for resolution,
not automatic instantiation of a particular or mutation of the worldview declaration.

The user extends the general direction beyond authority identities: identity may
concretize an otherwise abstract substantial, with a possible extension to dependents
still open. Identity applies to its bearer as a whole. Other unmet requirements or
abstract components may keep a qualified expression abstract; supplying one identity
does not discharge every independent constraint or change the declaration itself.

Whether normal predicate-to-quality reification, `type of <identity>`, should be
illegal is an explicit open question. It is not a settled prohibition. Existing
grammar, validator behavior and uses are neither rejected nor removed by this note.

Biological individuals and ecological populations motivate this direction. The
user also settles the proposed `economics:MarketValue` declaration as abstract,
with a future suitable identity from an established commodity authority in mind.
No commodity provider, identity root or authority identifier is selected here.
No active authority requirement is invented to fill that gap.

Keep two existing language forms distinct: `requires identity` names an identity
constraint; `requires authority` configures an authority bridge on an identity
declaration. A quantity such as MarketValue must not acquire a fabricated provider
binding merely because its future identity requirement is under discussion.
Existing syntax, retained requirement lists and OWL restrictions do not demonstrate
authority-aware concretization of an expression.

Before implementation, specify how a required root is recognized as belonging to
a known configured authority, what makes a supplied identity suitable, and how
multiple/inherited requirements and other unresolved abstract components interact.
Matching a namespace string is not an agreed suitability test. Preserve the
distinction between the declaration's abstract status and the status of a qualified
observable/expression; supplying an identity does not rewrite the declaration.

This provisional idea does not establish new rules for every `requires` clause,
automatic abstractness propagation, resolver concretization, or authority fallback.
It does not retype biological individuals/populations or alter their current
declarations. Those mechanisms and their validation need a separate reviewed
design; no grammar, service, resolver or OWL implementation is changed here.

## Conditional role conferral and contextual bearers (2026-10-15)

**Agreed specification direction; future runtime, not a current implementation.**
The user states that a forthcoming runtime consequence engine will analyze these
implications. Process-to-Role conferral is the immediate design case. The language
must support a `within` qualification so a specialized contextual bearer can be
checked and the role attributed and resolved only under the required conditions.
Broader predicate conferral is not approved by this decision.

Worldview declarations remain universal statements and provide a stable semantic
spine connecting a process, a conferred role and its contextual-bearer constraints.
The actual consequence depends on resolution. If a process confers a role, the
future consequence engine assigns that role only if a model resolving "role in
process-bearer" can be found. This phrase states the intended resolution question;
it does not introduce new executable source syntax.

Attribution is model-mediated under the control of scientists and their community.
A semantic connection, eligibility classification or OWL entailment alone does not
assign the role. The `within` qualification participates in that model resolution
as a constraint on the specialized contextual bearer; it does not bypass resolution
or identify a particular recipient merely through type compatibility.

Finding an appropriate resolving model is a required gate, not a license to invent
further runtime behavior. Missing-model or resolution-failure behavior, multiple
models or candidate recipients, and attribution timing have no defaults selected
by this decision. The declaration does not itself bind an occurrence to a recipient
or impose an unconditional consequence.

The conferral rule's `within` context must not be silently converted into ordinary
`of` inherency, an unqualified role assignment, a global restriction on every bearer,
or an OWL necessary existential. No grammar, services, consequence engine, or new
OWL axioms are implemented by this specification update. The existing ODO `confers`
property and dormant service-bean list do not establish such implementation.

### Existing representation and proposed reuse

The active Observable grammar already defines `ContextualizedExpression` as a
concept expression with an optional `within` context expression. The language API
exposes that separate context through `SemanticSyntax.getContextualization()`;
`SemanticSyntaxImpl` preserves it for scoped implication/emergence references.
Reuse of this syntax/transport shape for conferral is proposed, not implemented.

A future service binding should preserve the source process, conferred role,
contextual-bearer expression, model-resolution requirement, condition structure
and source provenance together.
In particular, retain which `within` belongs to which binding when lists or inverse
forms are used. Do not flatten the context into the role or process expression.
The existing `KimConceptStatement.getDeclaredInherent()` path is not a substitute:
it represents a different declaration-level bearer constraint. Current adaptation
retains scoped implication/emergence source but excludes those scoped expressions
from the adapted clause collections; it does not provide an end-to-end conferral path.

### Semantic choices still requiring user direction

- **Within target type and scope:** is the target a countable bearer, any Observable,
  a Configuration, or a richer contextual expression? Does each conferred role have
  its own context, or does one context scope a whole list? The existing grammar's
  optionality does not decide whether a conferral may omit `within`.
- **Compatibility versus identity:** specify the specialization/applicability checks
  between contextual bearer, role applicability and process participants. Compatible
  types do not identify the same particular or witness. Recipient identity/co-reference
  requires an explicit runtime binding; it cannot follow from a shared type alone.
- **Conditions carried to runtime:** model-mediated attribution is agreed; decide
  which additional conditions are required, where they are authored and how they
  reach resolution. Preserve the full expression, provenance and unresolved status.
  Transport is not evaluation. Missing-model/failure behavior, model or recipient
  multiplicity, and timing remain open; no fallback, selection, retry or trigger rule
  and no new condition syntax are selected.
- **Inverse form:** decide whether `conferred by` is exact shorthand for the same
  forward conditional rule, preserving its contextual bearer and conditions. A reverse
  existential on the role is not equivalent to a forward process restriction. Rule
  ownership and provenance across namespaces also need an explicit policy.

Before any OWL mapping, review the current `confers` range: separate Attribute and
Role ranges mean their intersection, not the comment's "attribute or role". Neither
a Role-only correction, a union range, nor use of untyped `confersTrait` is silently
adopted here. The user offered to add grammar; implementation ownership and the
remaining semantic choices must be coordinated before activating the feature.

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

## Verified local implementation checkpoint

The supplied independently verified integration checkpoint is now merged into the
main local `develop` branches:

- klab-services: `9144e79b5516533456aafaaaa04026ba5e1b9041`.
- klab-languages: `9bbc9d1173e380ba134cf6a237a3474721667075`.

ODO's reviewed release and decisions were already fast-forwarded to local `master`
at `58fa7d6e505ed4e351fa4e1c78cab2a8b0443fa7`; this checkpoint update changes only
the decision document. ABOUT semantics and the combined subjectivity, copy,
occurrence, and privacy guard passed independent review. These are merged source
implementations, not claims of live service behavior.

Reported verification: all 68 focused tests passed. The combined run using the
actual declared Xtext 2.32 artifact ran 704 tests: 698 passed, four failed, one
errored, and one was skipped. The four failures and one error were freshly
reproduced on the language baseline. This is a baseline-qualified result, not an
all-green combined suite. These cross-repository results are the supplied verified
checkpoint, not tests rerun by this document-only update.

Installed language JARs and live services remain unchanged. Builds require the
matching private language artifacts; no install, push, or deployment is part of
this checkpoint.

Still unimplemented are runtime classification continuation, end-to-end typed
constraint enforcement/evaluation, and multi-source snapshot publication and
subscription behavior. Cross-twin matching remains specification-only. Merged
occurrence/copy/privacy safeguards and ABOUT validation do not establish these
runtime capabilities. Value-operator evaluation, including `whose`, remains a
resolver gap; preserving parsing, transport, and subjectivity is not evaluation.

## Descriptive relation matrix (approved 2026-10-15)

The general relation has Observable range. The old unrestricted `owl:Thing` matrix
was an unapproved option; it is not the chosen correction. OWL domain/range axioms
infer membership, so k.LAB must validate admissibility before emission.

| Relation | OWL source | OWL target | Parent |
| --- | --- | --- | --- |
| `describes` | Predicate union Quality | Observable | none |
| `describesQuality` | Predicate union Quality | Quality | describes |
| `describesConfiguration` | Domain | Configuration | describes |
| `increasesWith` | Quality | Quality | describesQuality |
| `decreasesWith` | Quality | Quality | describesQuality |
| `marksQuality` | Quality | Presence | describesQuality |
| `classifiesQuality` | Predicate | Quality | describesQuality |
| `discretizesQuality` | Ordering | QuantifiableQuality | describesQuality |

The unary-operator `describedType` query remains a distinct existing API contract;
nearest or flattened fillers do not represent a complete inherited description
conjunction. Audit consumers without silently redefining that query. Descriptive
properties are not OWL subproperties of `affects` or ABOUT. Typed `as`, monotonic
scope, Boolean marking and discretization retain the separate contracts above.

## Consumer inventory

Local search under `C:/Users/Ferd/git` found these bundles:

| Repository and path | Baseline | Disposition |
| --- | --- | --- |
| klab-services: `klab.services.reasoner/src/main/resources/knowledge/odo.owl` | byte-identical to authoritative 0.1.0; 89 class and 59 object-property declarations | updated in isolated consumer branch, now integrated into local develop |
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
previously satisfiable named classes. These checks do not substitute for k.LAB
validation or the remaining runtime work.

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
No full k.LAB application suite was run by the ODO artifact task. The separate
integration checkpoint and its baseline-qualified results are recorded above.

## Open choices and remaining coordination

These are proposals or unresolved choices, not approved contracts:

1. Relation naming/syntax for the normative framework. The OWL name
   `interpretsPhysicalDomain`, compositional `about`, and repeated-modifier
   multiple-target convention are approved; reviewed ABOUT implementation is merged
   locally as recorded above, without claiming live activation or broader endpoint support.
2. Further source expansion beyond Predicate union Quality; the approved Observable-range
   correction and language-to-OWL mapping are recorded above.
3. Runtime tolerance default and algorithm.
4. Exact-only category syntax and explicit sufficient-definition syntax.
5. Runtime schema, enforcement, and error policy, following the design checkpoint.
6. Expansion limits, deduplication, ordering, and failure diagnostics.
7. Institutional/evaluative domain placement.
8. Future ABOUT endpoint types and contracts beyond KnowledgeDomain -> PhysicalDomain.
9. Worldview subscription polling intervals, notification protocols, and removal policies.

Migration of the divergent legacy bundles and mirroring this repository document to
the discussion Page also remain coordination work. Multiple-domain grammar is deferred.
