# Collective entities - ODO 0.2.1

Status: isolated candidate; publication and consumer integration remain separate.

The approved domain-neutral roots are `CollectiveSubject` beneath `Subject` and
`CollectiveAgent` beneath `Agent`. Both are abstract. Existing Subject/Agent
disjointness applies; neither collective root specializes the other. The roots
do not exhaust their parent kinds.

A collective entity can have its own identity and cohort under stated criteria
for unity and continuity. It differs from an observational collection or query
result. No membership property, minimum size, common member type, closed list,
fixed membership, identity transition, member agency or observational containment
is asserted by these roots. In particular, `isCollective` remains the separate
annotation for collective observation and is not attached to either entity kind.

IMOD exposes matching core aliases. Its biological Subject branch remains
`life:Assemblage` -> `imod:CollectiveSubject`; anatomical and cellular assemblages
inherit that lineage. `life:CollectiveAgent` specializes `imod:CollectiveAgent`.
Population, Community and SocialGroup specialize the biological Agent branch.
Community is articulated through populations, without a direct individual-member
restriction. Organization uses only the generic Agent branch: its social meaning
does not justify a biological parent or imply agency of every member.

Configuration-first recognition and subsequent ordinary models retain their
separate contracts. This change adds no emergence clause, configuration recipe,
membership relationship or runtime detection behavior. Existing review records
for those pending scientific articulations remain applicable.

0.2.1 is an additive successor. Preserve all 0.1.0, 0.1.1 and 0.2.0 artifacts.
Numbered 0.2.1 and the isolated Services bundle must be byte-identical. The retired
mutable latest alias stays absent.
Commit the release first, then pin IMOD to that exact local commit and numbered
path. This pin is not remotely usable until a separately authorized publication.
Production import capture must retain exact bytes; no admission waiver is allowed.

Remaining scientific work: articulate the applicable social-connection and
population configurations, their support/cohort criteria and models. Whether a
particular observationally selected group establishes a collective entity belongs
to its explicit scientific criteria; lineage alone cannot decide that question.
