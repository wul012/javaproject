# v1810 signed approval capture artifact preflight extraction

This note records the thirteenth contract-preserving ops extraction.

## Scope

v1810 moves the `OperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflight`
implementation family out of the root `com.codexdemo.orderplatform.ops` package
and into the narrower `ops.maintenance.signedapprovalcaptureartifactpreflight`
package.

Sixteen implementation files moved, for example:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService`

The two public controllers stay in the root package:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFoundationController`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightAssuranceController`

The global `OpsShardReadinessRoutePaths` aggregator also stays in the root
package and continues to delegate the same suffix constants to the public family
route owner.

Direct Java files in root `ops` package moved from 1,137 to 1,121.

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
`OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths` already lived
in `ops.maintenance.signedapproval` from the v1804 route-path consolidation. It
is public; v1810 only adds a public `BASE_PATH` so the moved services can build
their `ENDPOINT` strings from that owner instead of from the package-private root
aggregator. The root aggregator still delegates to the same owner, so callers see
byte-identical route strings.

## Cross-family endpoint recipe

This family is internally clean: it reads no other family's route constants. The
one cross-family coupling is its `FragmentCatalog`, which references ten sibling
`OperatorEvidenceValueSupplySignedApprovalCapturePreflight` service endpoint
constants. After v1810 those ten `CapturePreflight` endpoint constants are
publicized as immutable read-only strings and imported into the moved
`FragmentCatalog`. No `CapturePreflight` service was moved, started, or invoked
by this change.

On the inbound side, the sibling `ArtifactDraftReadiness` item catalogs (which
stay in root) read several of this family's service endpoint constants. Those
endpoint constants are publicized as immutable read-only strings so the retained
root readers keep compiling across the new package boundary.

## Safety line

Do not rename or move archive roots such as `a/` through `f/`, `e/<version>/`,
historical evidence JSON files, screenshots, or code-walkthrough archives as part
of this extraction.

v1810 does not open write routing, active shard routing, credential value access,
raw endpoint resolution, managed audit connections, deployment, rollback, Java
process startup, Node process startup, or mini-kv process startup.

## Verification hooks

`ReadabilityUpkeepOpsConsolidationExtractionV1810Tests` keeps this note
discoverable from the ops index, checks that representative implementation files
live under `ops.maintenance.signedapprovalcaptureartifactpreflight`, checks that
the two controllers and the root route aggregator remain in the root package, and
guards that the root package count does not regress above 1,121.

The retained root
`OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRoutePathsTests`
continues to compare the root aggregator, the split route owner, and service
`ENDPOINT` values.
