# v1797 code walkthrough compliance extraction

This note records the first contract-preserving class extraction after the
v1796 ops consolidation inventory. It moves the code walkthrough compliance
registry implementation out of the crowded root `ops` package while keeping the
public route and response contract unchanged.

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance`:

- `OpsShardReadinessCodeWalkthroughComplianceArchiveRangeCatalog`
- `OpsShardReadinessCodeWalkthroughComplianceBoundaryRuleCatalog`
- `OpsShardReadinessCodeWalkthroughComplianceDocumentationRuleCatalog`
- `OpsShardReadinessCodeWalkthroughComplianceRegistryRenderer`
- `OpsShardReadinessCodeWalkthroughComplianceRegistryResponse`
- `OpsShardReadinessCodeWalkthroughComplianceRegistryService`
- `OpsShardReadinessCodeWalkthroughComplianceRegistrySupport`
- `OpsShardReadinessCodeWalkthroughComplianceRequiredHeadingCatalog`
- `OpsShardReadinessCodeWalkthroughComplianceRoutePaths`
- `OpsShardReadinessCodeWalkthroughComplianceTestCoverageCatalog`
- `OpsShardReadinessCodeWalkthroughComplianceVersionCatalog`

The root controller stays in `com.codexdemo.orderplatform.ops`. That controller
is still the Spring entry point for the existing route, and the root
`OpsShardReadinessRoutePaths` table still delegates the public suffix through a
small public route-path class in the new subpackage.

## Root Package Pressure

The extraction keeps the total ops source footprint stable and reduces the
root package pressure measured by J6:

| Metric | v1796 baseline | v1797 after extraction |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,330 | 1,319 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

This is intentionally a root-pressure reduction, not a behavior change. The
file count under `ops` is unchanged because the same implementation files now
live under a narrower maintenance package.

## Contract Preservation

The external endpoint remains:

```text
/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry
```

The response still reports:

- `version=Java v1747`
- `profile=java-shard-readiness-code-walkthrough-compliance-registry.v1`
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

Package-local service, renderer, immutability, and boundary tests moved with
the implementation. Root route-path and controller tests remain in the root
package so they continue to prove the public route table and Spring controller
entry point.

The extraction is guarded by:

- `OpsShardReadinessCodeWalkthroughComplianceRegistryServiceTests`
- `OpsShardReadinessCodeWalkthroughComplianceRegistryRendererTests`
- `OpsShardReadinessCodeWalkthroughComplianceRegistryBoundaryTests`
- `OpsShardReadinessCodeWalkthroughComplianceRegistryImmutabilityTests`
- `OpsShardReadinessCodeWalkthroughComplianceRegistryControllerTests`
- `OpsShardReadinessCodeWalkthroughComplianceRoutePathsTests`
- `ReadabilityUpkeepOpsConsolidationExtractionTests`
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, managed audit HTTP/TCP connection, deployment, rollback, Java
autostart, mini-kv autostart, or historical archive movement is opened by this
version.
