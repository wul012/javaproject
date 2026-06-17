# v1812 approval preflight extraction

This note records the fifteenth contract-preserving ops extraction.

## Scope

v1812 moves the `OperatorEvidenceValueSupplyApprovalPreflight`
implementation family out of the root `com.codexdemo.orderplatform.ops`
package and into the narrower `ops.maintenance.approvalpreflight` package.

Fifteen physical implementation files moved, for example:

- `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService`
- `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse`
- `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport`
- `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService`

The package-private
`OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog`
now lives beside the item catalog in the same Java file. That keeps the narrow
package cohesion while offsetting the new route owner file, so the total `ops`
Java file-count ratchet remains flat at 1,352 instead of being relaxed.

The two public controllers stay in the root package:

- `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightFoundationController`
- `OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightAssuranceController`

The global `OpsShardReadinessRoutePaths` aggregator also stays in the root
package and delegates the same approval-preflight suffix constants to the new
public family route owner,
`OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths`.

Direct Java files in root `ops` package moved from 1,105 to 1,089.

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

## Cross-family endpoint recipe

The moved `ItemCatalog` records source evidence from seven upstream
operator-evidence-value-supply and adapter-preflight services. Those upstream
services stay in the root package; v1812 only exposes their immutable
`ENDPOINT` strings as public read-only constants so the moved catalog can keep
pointing at the same source evidence without invoking upstream behavior.

The downstream edge from v1811 is also narrowed. The already-moved
`SignedApprovalCapturePreflightInputCatalog` now imports the approval-preflight
endpoint constants from `ops.maintenance.approvalpreflight` instead of from the
root package. That keeps the staged signed-approval chain readable while
preserving the same endpoint strings.

## Safety line

Do not rename or move archive roots such as `a/` through `f/`, `e/<version>/`,
historical evidence JSON files, screenshots, or code-walkthrough archives as
part of this extraction.

v1812 does not open write routing, active shard routing, credential value
access, raw endpoint resolution, managed audit connections, deployment,
rollback, Java process startup, Node process startup, or mini-kv process
startup.

## Verification hooks

`ReadabilityUpkeepOpsConsolidationExtractionV1812Tests` keeps this note
discoverable from the ops index, checks that representative implementation
files live under `ops.maintenance.approvalpreflight`, checks that the two
controllers and the root route aggregator remain in the root package, and
guards that the root package count does not regress above 1,089.

The retained root
`OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePathsTests`
continues to compare the root aggregator, the split route owner, and service
`ENDPOINT` values.
