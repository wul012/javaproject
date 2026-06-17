# v1809 manual evidence worksheet extraction

This note records the twelfth contract-preserving ops extraction.

## Scope

v1809 moves the `ManualEvidenceWorksheet` implementation family out of the root
`com.codexdemo.orderplatform.ops` package and into the narrower
`ops.maintenance.manualevidenceworksheet` package.

Moved implementation files:

- `OpsShardReadinessManualEvidenceWorksheetRoutePaths`
- `OpsShardReadinessManualEvidenceWorksheetCatalogService`
- `OpsShardReadinessManualEvidenceWorksheetSlotTemplateService`
- `OpsShardReadinessManualEvidenceWorksheetValidationRulesService`
- `OpsShardReadinessManualEvidenceWorksheetRedactionRulesService`
- `OpsShardReadinessManualEvidenceWorksheetMissingValuePolicyService`
- `OpsShardReadinessManualEvidenceWorksheetTargetScopeRegistryService`
- `OpsShardReadinessManualEvidenceWorksheetImporterPreflightService`
- `OpsShardReadinessManualEvidenceWorksheetRouteProfileSummaryService`
- `OpsShardReadinessManualEvidenceWorksheetArchivePlanService`
- `OpsShardReadinessManualEvidenceWorksheetOperatorHandoffService`
- `OpsShardReadinessManualEvidenceWorksheetCiBudgetService`
- `OpsShardReadinessManualEvidenceWorksheetCloseoutService`
- `OpsShardReadinessManualEvidenceWorksheetResponse`
- `OpsShardReadinessManualEvidenceWorksheetSupport`

The two public controllers stay in the root package:

- `OpsShardReadinessManualEvidenceWorksheetFoundationController`
- `OpsShardReadinessManualEvidenceWorksheetAssuranceController`

The global `OpsShardReadinessRoutePaths` aggregator also stays in the root
package and delegates the same suffix constants to the moved family route owner.

Direct Java files in root `ops` package moved from 1,152 to 1,137.

## Contract boundary

This is an internal package-ownership change. It does not change:

- HTTP route strings
- response fields
- response item names
- warning strings
- read-only evidence semantics
- archive paths
- fixture paths
- production runtime configuration
- deployment or rollback behavior

The family route owner is public and has a public `BASE_PATH` plus public suffix
constants. Moved services now build their `ENDPOINT` strings from that owner
instead of from the root aggregator. The root aggregator delegates back to the
same owner, so callers still see byte-identical route strings.

## Cross-family endpoint recipe

`ManualEvidenceWorksheet` is upstream of
`OperatorEvidenceImportPreflight`. After v1809 the moved
`OperatorEvidenceImportPreflight` services import immutable endpoint constants
from the new `ops.maintenance.manualevidenceworksheet` package instead of from
the root package.

The worksheet services also read several upstream `RuntimeExecution` endpoint
constants. Those constants were made public only as immutable read-only string
references. No runtime execution service was moved, started, or invoked by this
change.

## Safety line

Do not rename or move archive roots such as `a/` through `f/`, `e/<version>/`,
historical evidence JSON files, screenshots, or code-walkthrough archives as
part of this extraction.

v1809 does not open write routing, active shard routing, credential value
access, raw endpoint resolution, managed audit connections, deployment,
rollback, Java process startup, Node process startup, or mini-kv process
startup.

## Verification hooks

`ReadabilityUpkeepOpsConsolidationExtractionV1809Tests` keeps this note
discoverable from the ops index, checks that representative implementation
files live under `ops.maintenance.manualevidenceworksheet`, checks that the two
controllers and the root route aggregator remain in the root package, and
asserts the new root package count of 1,137.

The retained root `OpsShardReadinessManualEvidenceWorksheetRoutePathsTests`
continues to compare the root aggregator, the split route owner, and service
`ENDPOINT` values.
