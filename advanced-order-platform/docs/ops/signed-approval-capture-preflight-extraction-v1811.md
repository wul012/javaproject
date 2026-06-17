# v1811 signed approval capture preflight extraction

This note records the fourteenth contract-preserving ops extraction.

## Scope

v1811 moves the `OperatorEvidenceValueSupplySignedApprovalCapturePreflight`
implementation family out of the root `com.codexdemo.orderplatform.ops` package
and into the narrower `ops.maintenance.signedapprovalcapturepreflight` package.

Sixteen implementation files moved, for example:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService`

The two public controllers stay in the root package:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFoundationController`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAssuranceController`

The global `OpsShardReadinessRoutePaths` aggregator also stays in the root
package and continues to delegate the same suffix constants to the public family
route owner.

Direct Java files in root `ops` package moved from 1,121 to 1,105.

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

The family route owner
`OpsShardReadinessSignedApprovalCapturePreflightRoutePaths` already lived in
`ops.maintenance.signedapproval` from the v1804 route-path consolidation. It is
public; v1811 only adds a public `BASE_PATH` so the moved services can build
their `ENDPOINT` strings from that owner instead of from the package-private
root aggregator. The root aggregator still delegates to the same owner, so
callers see byte-identical route strings.

## Cross-family endpoint recipe

This family has two endpoint-only cross-family edges.

The outgoing edge is in `InputCatalog`: the moved catalog records source
evidence from eleven sibling `ApprovalPreflight` services. Those upstream
services stay in the root package, but their immutable `ENDPOINT` strings are
now public and imported by the moved catalog. No upstream service behavior,
route string, or controller mapping changes.

The incoming edge is from the already-moved v1810
`CaptureArtifactPreflightFragmentCatalog`: that catalog reads ten
`CapturePreflight` endpoint constants as source evidence. After v1811 it imports
those constants from `ops.maintenance.signedapprovalcapturepreflight` instead of
from the root package. The constants remain immutable read-only strings.

The retained root controllers and retained root route guard tests also import
the moved public service/response types. This preserves the public controller
surface while letting the implementation package stop contributing sixteen
direct files to the root `ops` directory.

## Safety line

Do not rename or move archive roots such as `a/` through `f/`, `e/<version>/`,
historical evidence JSON files, screenshots, or code-walkthrough archives as
part of this extraction.

v1811 does not open write routing, active shard routing, credential value access,
raw endpoint resolution, managed audit connections, deployment, rollback, Java
process startup, Node process startup, or mini-kv process startup.

## Verification hooks

`ReadabilityUpkeepOpsConsolidationExtractionV1811Tests` keeps this note
discoverable from the ops index, checks that representative implementation files
live under `ops.maintenance.signedapprovalcapturepreflight`, checks that the two
controllers and the root route aggregator remain in the root package, and guards
that the root package count does not regress above 1,105.

The retained root
`OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRoutePathsTests`
continues to compare the root aggregator, the split route owner, and service
`ENDPOINT` values.
