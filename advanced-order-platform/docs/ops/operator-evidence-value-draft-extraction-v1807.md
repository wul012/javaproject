# v1807 operator evidence value draft registry extraction

This note records the tenth contract-preserving extraction after the v1796
inventory, and the first to apply the cross-family endpoint sub-recipe. It moves
the operator-evidence-value-draft registry family out of the root `ops` package
into the cohesive `ops.maintenance.operatorevidencevaluedraft` subpackage.
Contract-preserving and archive-preserving: every route, response shape, and
read-only flag is byte-identical.

(There is no v1806 extraction: v1806 was a documentation-only quality closeout.
This extraction is the next file-moving step and is numbered v1807.)

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluedraft`: 16
non-controller implementation files (services, catalogs, renderers, support,
response) plus the family route-path class
`OpsShardReadinessOperatorEvidenceValueDraftRoutePaths`. The two public
`@RestController` classes stay in the root package, as does the global
`OpsShardReadinessRoutePaths` aggregator. The family route-path class was made
public with a public `BASE_PATH`, and its suffix constants were made public so
the relocated services and the aggregator can both read them.

## Cross-Family Endpoint Sub-Recipe

Unlike the self-contained families extracted in v1799 through v1805, this family
sits inside the interconnected operator-evidence subsystem and has two-way
endpoint coupling that had to be handled explicitly, by visibility only:

- Outbound: the relocated value-draft files read the `ENDPOINT` constant of seven
  `OperatorEvidenceImportPreflight` services (catalog, closeout, import-blocker
  matrix, missing-value guard, redaction-preservation, slot-normalization,
  target-scope-mapping). Those seven `ENDPOINT` constants were made public and
  imported by the relocated files.
- Inbound: the root `OperatorEvidenceValueSupplySlotCatalog` and a value-draft
  route guard test read the `ENDPOINT` constants of value-draft services. Those
  value-draft `ENDPOINT` constants were made public so the retained root readers
  can keep reading them across the package boundary.

No endpoint string changed; this is visibility widening plus imports only. The
route constants themselves continue to flow through the unchanged aggregator.

## Root Package Pressure

| Metric | v1805 baseline | v1807 after extraction |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,183 | 1,167 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

The total file count under `ops` is unchanged because the same classes now live
under a narrower maintenance package; only the direct root-package count drops.

## Contract Preservation

No route string changes. Both value-draft controllers keep their request
mappings and read their route suffixes from the unchanged root aggregator.
Response records keep their fields, ordering, and immutable collections.

## Archive Boundary

Do not rename or move archive roots.

This extraction does not rename or move archive roots. `a/` through `f/`,
`e/<version>/`, evidence JSON files, screenshot archives, and historical code
walkthrough folders remain in place. Node-side references to Java evidence
archives are therefore not invalidated.

## Test Boundary

Two tests stay in the root package because they assert against the controllers
or the aggregator's package-private constants; they import the relocated types.
Two package-local tests moved to the test subpackage. One retained root test from
the operator-evidence-value-supply family imports relocated value-draft services
through the now-public endpoint constants.

The extraction is guarded by:

- `ReadabilityUpkeepOpsConsolidationExtractionV1807Tests`
- `OpsShardReadinessOperatorEvidenceValueDraftRoutePathsTests`
- `OpsShardReadinessOperatorEvidenceValueDraftFoundationServiceTests`
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, managed audit HTTP/TCP connection, deployment, rollback, Java
autostart, mini-kv autostart, or historical archive movement is opened by this
version.
