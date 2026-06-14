# v1801 screenshot explanation archive registry extraction

This note records the fifth contract-preserving class extraction after the
v1796 inventory and the v1797–v1800 CodeWalkthrough registry extractions. It is
the first extraction outside the CodeWalkthrough family: it moves the screenshot
explanation archive registry implementation out of the crowded root `ops`
package while keeping the public route and response contract unchanged.

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive`:

- `OpsShardReadinessScreenshotExplanationArchiveBoundaryCatalog`
- `OpsShardReadinessScreenshotExplanationArchiveCurrentCatalog`
- `OpsShardReadinessScreenshotExplanationArchiveNamingRuleCatalog`
- `OpsShardReadinessScreenshotExplanationArchiveRegistryRenderer`
- `OpsShardReadinessScreenshotExplanationArchiveRegistryResponse`
- `OpsShardReadinessScreenshotExplanationArchiveRegistryService`
- `OpsShardReadinessScreenshotExplanationArchiveRegistrySupport`
- `OpsShardReadinessScreenshotExplanationArchiveRoutePaths`
- `OpsShardReadinessScreenshotExplanationArchiveSegmentCatalog`
- `OpsShardReadinessScreenshotExplanationArchiveVerificationCatalog`

The root controller stays in `com.codexdemo.orderplatform.ops`. That controller
is still the Spring entry point for the existing route, and the root
`OpsShardReadinessRoutePaths` table still delegates the public suffix through
the now-public screenshot explanation archive route-path class in the new
subpackage. The moved service references its own subpackage route-path class for
the endpoint string, so the produced route is byte-identical.

## Root Package Pressure

| Metric | v1800 baseline | v1801 after extraction |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,290 | 1,280 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

This is intentionally a root-pressure reduction, not a behavior change. The
file count under `ops` is unchanged because the same implementation files now
live under a narrower maintenance package.

## Contract Preservation

The external endpoint remains:

```text
/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry
```

The response still reports:

- `version=Java v1773`
- `profile=java-shard-readiness-screenshot-explanation-archive-registry.v1`
- `readOnly=true`
- `capturesScreenshot=false`
- `startsJavaService=false`
- `startsMiniKvService=false`
- no credential values
- no raw endpoint URL resolution
- no managed audit connection

## Archive Boundary

Do not rename or move archive roots.

This extraction does not rename or move archive roots. `a/` through `f/`,
`e/<version>/`, evidence JSON files, screenshot archives, and historical code
walkthrough folders remain in place. In particular, the screenshot explanation
archive segments described by this registry stay where they are; only the Java
implementation classes move package. Node-side references to Java evidence
archives are therefore not invalidated.

## Test Boundary

Package-local service, renderer, boundary, immutability, closeout, f-root-policy,
and test-support helpers moved with the implementation. The segmentation docs
test, the root route-path test, and the controller test remain in the root
package; the controller and route-path tests construct the service directly and
import the public route-path class, mirroring v1797–v1800.

The extraction is guarded by:

- `OpsShardReadinessScreenshotExplanationArchiveRegistryServiceTests`
- `OpsShardReadinessScreenshotExplanationArchiveRegistryRendererTests`
- `OpsShardReadinessScreenshotExplanationArchiveRegistryBoundaryTests`
- `OpsShardReadinessScreenshotExplanationArchiveRegistryImmutabilityTests`
- `OpsShardReadinessScreenshotExplanationArchiveRegistryCloseoutTests`
- `OpsShardReadinessScreenshotExplanationArchiveFRootPolicyTests`
- `OpsShardReadinessScreenshotExplanationArchiveRegistryControllerTests`
- `OpsShardReadinessScreenshotExplanationArchiveRoutePathsTests`
- `ReadabilityUpkeepOpsConsolidationExtractionV1801Tests`
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, managed audit HTTP/TCP connection, deployment, rollback, Java
autostart, mini-kv autostart, or historical archive movement is opened by this
version.
