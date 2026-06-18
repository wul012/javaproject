# v1813 signed approval artifact draft readiness extraction

This note records the sixteenth contract-preserving ops extraction.

## Scope

v1813 moves the `OperatorEvidenceValueSupplySignedApprovalArtifactDraftReadiness`
implementation family out of the root `com.codexdemo.orderplatform.ops` package
and into the narrower `ops.maintenance.signedapprovalartifactdraftreadiness`
package.

Sixteen implementation files moved, for example:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceItemCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationItemCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessGateCatalog`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessResponse`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSupport`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService`

The two public controllers stay in the root package:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationController`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceController`

The global `OpsShardReadinessRoutePaths` aggregator also stays in the root
package and continues to delegate the same suffix constants to the public family
route owner.

Direct Java files in root `ops` package moved from 1,089 to 1,073.

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
`OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths` already lived in
`ops.maintenance.signedapproval` from the v1804 route-path consolidation. It is
public; v1813 only adds a public `BASE_PATH` so the moved services can build their
`ENDPOINT` strings from that owner instead of from the package-private root
aggregator. The root aggregator still delegates to the same owner, so callers see
byte-identical route strings.

## Cross-family endpoint recipe

This family's only outbound coupling is its item catalogs, which read ten sibling
`CaptureArtifactPreflight` service endpoint constants. Those constants were
already publicized as immutable read-only strings during the v1810 extraction, so
v1813 needs no further outbound visibility change — the moved item catalogs simply
keep importing them from `ops.maintenance.signedapprovalcaptureartifactpreflight`.

On the inbound side, three sibling families that stay in root — `ArtifactDraftPreflight`,
`ArtifactDraftReviewPackagePreflight`, and `SignedApprovalDraftProfileSection` —
read several of this family's service endpoint constants. Those endpoint constants
are publicized as immutable read-only strings so the retained root readers keep
compiling across the new package boundary.

## Safety line

Do not rename or move archive roots such as `a/` through `f/`, `e/<version>/`,
historical evidence JSON files, screenshots, or code-walkthrough archives as part
of this extraction.

v1813 does not open write routing, active shard routing, credential value access,
raw endpoint resolution, managed audit connections, deployment, rollback, Java
process startup, Node process startup, or mini-kv process startup.

## Verification hooks

`ReadabilityUpkeepOpsConsolidationExtractionV1813Tests` keeps this note
discoverable from the ops index, checks that representative implementation files
live under `ops.maintenance.signedapprovalartifactdraftreadiness`, checks that the
two controllers and the root route aggregator remain in the root package, and
guards that the root package count does not regress above 1,073.

The retained root
`OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRoutePathsTests`
continues to compare the root aggregator, the split route owner, and service
`ENDPOINT` values.
