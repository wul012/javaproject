# v1808 operator evidence import preflight registry extraction

This note records the eleventh contract-preserving extraction after the v1796
inventory. It moves the operator-evidence-import-preflight registry family out
of the root `ops` package into the cohesive
`ops.maintenance.operatorevidenceimportpreflight` subpackage.

Contract-preserving and archive-preserving: every route, response shape,
read-only flag, archive root, evidence file, and historical walkthrough path is
unchanged.

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.operatorevidenceimportpreflight`:
fifteen non-controller implementation files, including the response record,
support helper, service classes, and the family route-path class
`OpsShardReadinessOperatorEvidenceImportPreflightRoutePaths`.

The two public `@RestController` classes stay in the root package:

- `OpsShardReadinessOperatorEvidenceImportPreflightFoundationController`
- `OpsShardReadinessOperatorEvidenceImportPreflightAssuranceController`

The global `OpsShardReadinessRoutePaths` aggregator also stays in root. It now
imports the moved family route-path owner and delegates the same suffix strings
to it.

## Cross-Family Endpoint Boundary

This family sits between the manual-evidence worksheet family and the
operator-evidence-value-draft family, so the extraction has two explicit
cross-family edges:

- Upstream references: moved ImportPreflight services read
  `ManualEvidenceWorksheet` and `RuntimeExecutionLiveReadGate` `ENDPOINT`
  constants as source links in their read-only response payloads. Those endpoint
  constants are now public, with no string changes.
- Downstream references: the already-relocated value-draft slot catalog reads
  ImportPreflight `ENDPOINT` constants. v1807 had already opened the first
  subset; v1808 completes the package move and keeps all ImportPreflight
  endpoint constants public where root tests or downstream registries need them.

No route string changed. The visibility changes are immutable string exposure
only; they do not add write routing, execution, credentials, endpoint
resolution, deployment, rollback, or managed-audit connections.

## Root Package Pressure

| Metric | v1807 baseline | v1808 after extraction |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,167 | 1,152 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

The total file count under `ops` is unchanged because the same classes now live
under a narrower maintenance package; only the direct root-package count drops.

## Contract Preservation

The root controllers still map through `OpsShardReadinessRoutePaths.BASE_PATH`.
Their `@GetMapping` suffixes are still read from the root aggregator, and the
root aggregator now delegates those suffixes to the moved family route-path
class. This preserves the public API surface while letting the implementation
live in a package that names the registry family.

The response record keeps the same field order and nested item shape. The
services still return the same version labels, profile labels, item names,
read-only markers, and warning strings.

## Archive Boundary

Do not rename or move archive roots.

This extraction does not rename or move `a/` through `f/`, `e/<version>/`, JSON
evidence files, screenshot archives, or historical code walkthrough folders.
Node-side references to Java evidence archives remain valid.

## Test Boundary

Three package-local service/support tests moved into the new test subpackage.
The route-path guard test stays in root because it verifies the root aggregator
and imports the moved route-path owner and public service endpoint constants.
The HTTP integration tests stay in their existing application-level package and
continue to exercise the public route surface.

The extraction is guarded by:

- `ReadabilityUpkeepOpsConsolidationExtractionV1808Tests`
- `OpsShardReadinessOperatorEvidenceImportPreflightRoutePathsTests`
- `OpsShardReadinessOperatorEvidenceImportPreflightFoundationServiceTests`
- `OpsShardReadinessOperatorEvidenceImportPreflightAssuranceServiceTests`
- `OpsShardReadinessOperatorEvidenceImportPreflightSupportTests`
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, managed audit HTTP/TCP connection, deployment, rollback, Java
autostart, mini-kv autostart, or historical archive movement is opened by this
version.
