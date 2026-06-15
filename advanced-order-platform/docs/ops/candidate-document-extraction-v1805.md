# v1805 candidate document registry extraction

This note records the ninth contract-preserving extraction after the v1796
inventory, and the largest by far. It moves the entire candidate-document
registry family — eight registry lanes (request-package handoff, submission
precheck, intake packet, material request, material submission precheck,
material submission precheck handoff, profile-section registry, and the
request-package surface) — out of the root `ops` package into the cohesive
`ops.maintenance.candidatedocument` subpackage. Contract-preserving and
archive-preserving: every route, response shape, and read-only flag is
byte-identical.

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.candidatedocument`: 57
non-controller implementation files (services, catalogs, renderers, supports,
responses, registries) plus the family route-path class
`OpsShardReadinessCandidateDocumentRoutePaths`. The eight public
`@RestController` classes stay in the root package, as does the global
`OpsShardReadinessRoutePaths` aggregator.

The family's dependency injection is intra-family (registry services compose
sibling candidate-document services), so the whole family moves as one unit with
no cross-package service wiring. The family route-path class was made public and
given a public `BASE_PATH`; its suffix constants were made public so the
relocated services and the root aggregator can both read them.

One genuine cross-family edge was handled explicitly: two candidate-document
catalogs cross-reference the compared-evidence candidate-intake-preflight catalog
route, which the aggregator previously defined inline. That single route constant
was moved into the candidate-document route-path class as its public owner, and
the aggregator now delegates to it (matching how the aggregator delegates every
other family constant). The compared-evidence family continues to read the
constant through the aggregator with the same value, so its routes are unchanged.

## Root Package Pressure

| Metric | v1804 baseline | v1805 after extraction |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,240 | 1,183 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

The total file count under `ops` is unchanged because the same classes now live
under a narrower maintenance package; only the direct root-package count drops.
This is the single largest root-pressure reduction in the consolidation program
so far.

## Contract Preservation

No route string changes. Every candidate-document endpoint is still
`/api/v1/ops/shard-readiness` plus the same per-lane suffix. The eight
controllers keep their request mappings and read their route suffixes from the
unchanged root aggregator. Response records keep their fields, ordering, and
immutable collections.

## Archive Boundary

Do not rename or move archive roots.

This extraction does not rename or move archive roots. `a/` through `f/`,
`e/<version>/`, evidence JSON files, screenshot archives, and historical code
walkthrough folders remain in place. Node-side references to Java evidence
archives are therefore not invalidated.

## Test Boundary

Eleven tests stay in the root package because they assert against the controller
classes (which stay in root) or the aggregator's package-private constants; they
import the relocated implementation and route-path types. Thirty package-local
tests moved to the test subpackage. Two shared test-support classes referenced by
the retained root tests were made public so both the moved and the retained tests
can build the family services.

The extraction is guarded by:

- `ReadabilityUpkeepOpsConsolidationExtractionV1805Tests`
- `OpsShardReadinessCandidateDocumentRoutePathsTests`
- the eight candidate-document controller tests
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, managed audit HTTP/TCP connection, deployment, rollback, Java
autostart, mini-kv autostart, or historical archive movement is opened by this
version.
