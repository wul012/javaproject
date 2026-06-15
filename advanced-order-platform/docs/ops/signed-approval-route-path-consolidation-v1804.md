# v1804 signed approval route-path consolidation

This note records the eighth contract-preserving extraction after the v1796
inventory. It differs from the seven before it: instead of moving a registry's
implementation layers, it consolidates three pure route-path constant holders —
the signed-approval artifact-draft-readiness, capture-artifact-preflight, and
capture-preflight route-path classes — into a new `ops.maintenance.signedapproval`
subpackage. No service, controller, or response moves in this version. The point
is to stand up the signed-approval maintenance subpackage with its shared
route-path leaves first, so the larger operator-evidence-value-supply
signed-approval registry families can migrate into it later.

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.signedapproval`:

- `OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths`
- `OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths`
- `OpsShardReadinessSignedApprovalCapturePreflightRoutePaths`

Each class holds the `OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_*` route
suffix constants for one signed-approval registry lane. The three classes are
referenced directly only by two consumers each: the root
`OpsShardReadinessRoutePaths` aggregator (which delegates the matching public
suffix), and the single matching `...RoutePathsTests` guard. Both consumers were
repointed by import only; the registry services and controllers that own those
routes stay in the root package and continue to read the suffixes through the
root aggregator, so nothing they reference changes.

To allow the cross-package references, the three route-path classes and their
suffix constants were made public. This is the only visibility change in this
version, and it is behaviour-neutral: the constant values are unchanged.

## Root Package Pressure

| Metric | v1803 baseline | v1804 after consolidation |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,243 | 1,240 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

This is intentionally a root-pressure reduction, not a behavior change. The file
count under `ops` is unchanged because the same route-path classes now live under
a narrower maintenance package. The reduction is small (three files) by design:
the remaining substantial families are aggregator-backed and cross-coupled
through each other's endpoint constants, so the clean single-step targets are now
the route-path leaves.

## Contract Preservation

No route string changes. The aggregator still exposes every
`OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_*` constant with the same value,
delegating to the relocated public route-path classes. Every registry endpoint
that is built from those constants resolves to the same path, with all read-only
flags intact.

## Archive Boundary

Do not rename or move archive roots.

This consolidation does not rename or move archive roots. `a/` through `f/`,
`e/<version>/`, evidence JSON files, screenshot archives, and historical code
walkthrough folders remain in place. Node-side references to Java evidence
archives are therefore not invalidated.

## Test Boundary

The three `...RoutePathsTests` guards stay in the root package because they assert
each relocated suffix equals the root aggregator's package-private constant; each
now imports the relocated public route-path class. No test moved.

The consolidation is guarded by:

- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRoutePathsTests`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightRoutePathsTests`
- `OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRoutePathsTests`
- `ReadabilityUpkeepOpsConsolidationExtractionV1804Tests`
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, managed audit HTTP/TCP connection, deployment, rollback, Java
autostart, mini-kv autostart, or historical archive movement is opened by this
version.
