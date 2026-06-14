# v1800 code walkthrough depth registry extraction

This note records the fourth contract-preserving class extraction after the
v1796 inventory and the v1797/v1798/v1799 extractions. It moves the code
walkthrough depth registry implementation out of the crowded root `ops` package
while keeping the public route and response contract unchanged. With this batch
all three CodeWalkthrough registry families (compliance, quality gate, quality
audit, depth) have been moved out of the root package.

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.walkthrough.depth`:

- `OpsShardReadinessCodeWalkthroughDepthBoundaryCatalog`
- `OpsShardReadinessCodeWalkthroughDepthRegistryRenderer`
- `OpsShardReadinessCodeWalkthroughDepthRegistryResponse`
- `OpsShardReadinessCodeWalkthroughDepthRegistryService`
- `OpsShardReadinessCodeWalkthroughDepthRegistrySupport`
- `OpsShardReadinessCodeWalkthroughDepthRoutePaths`
- `OpsShardReadinessCodeWalkthroughDepthRuleCatalog`
- `OpsShardReadinessCodeWalkthroughDepthVerificationCatalog`

The root controller stays in `com.codexdemo.orderplatform.ops`. That controller
is still the Spring entry point for the existing route, and the root
`OpsShardReadinessRoutePaths` table still delegates the public suffix through
the now-public depth route-path class in the new subpackage. The moved service
references its own subpackage route-path class for the endpoint string, so the
produced route is byte-identical to the pre-extraction value.

## Root Package Pressure

| Metric | v1799 baseline | v1800 after extraction |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,298 | 1,290 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

This is intentionally a root-pressure reduction, not a behavior change. The
file count under `ops` is unchanged because the same implementation files now
live under a narrower maintenance package.

## Contract Preservation

The external endpoint remains:

```text
/api/v1/ops/shard-readiness/code-walkthrough-depth-registry
```

The response still reports:

- `version=Java v1778`
- `readOnly=true`
- `executionAllowed=false`
- `startsJavaService=false`
- `startsMiniKvService=false`
- no credential values
- no raw endpoint URL resolution
- no managed audit connection

## Archive Boundary

Do not rename or move archive roots.

This extraction does not rename or move archive roots. `a/` through `f/`,
`e/<version>/`, evidence JSON files, screenshot archives, and historical code
walkthrough folders remain in place. Node-side references to Java evidence
archives are therefore not invalidated.

## Test Boundary

Package-local service, renderer, boundary, and test-support helpers moved with
the implementation. Root route-path and controller tests remain in the root
package so they continue to prove the public route table and Spring controller
entry point. The root controller and route-path tests construct the service
directly and import the public route-path class instead of using the
package-local test support, mirroring v1797/v1798/v1799.

The extraction is guarded by:

- `OpsShardReadinessCodeWalkthroughDepthRegistryServiceTests`
- `OpsShardReadinessCodeWalkthroughDepthRegistryRendererTests`
- `OpsShardReadinessCodeWalkthroughDepthBoundaryTests`
- `OpsShardReadinessCodeWalkthroughDepthRegistryControllerTests`
- `OpsShardReadinessCodeWalkthroughDepthRoutePathsTests`
- `ReadabilityUpkeepOpsConsolidationExtractionV1800Tests`
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, managed audit HTTP/TCP connection, deployment, rollback, Java
autostart, mini-kv autostart, or historical archive movement is opened by this
version.
