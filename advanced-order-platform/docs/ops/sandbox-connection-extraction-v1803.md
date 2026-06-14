# v1803 sandbox connection registry extraction

This note records the seventh contract-preserving class extraction after the
v1796 inventory. It is the third extraction outside the CodeWalkthrough family,
the second dependency-injected "evidence" registry family, and the first to move
a whole family that owns two controllers and two registry sub-clusters sharing a
single route-path class: the sandbox connection blocked-execution-context
dossier and the sandbox connection precheck upstream-receipt verification
manifest. It moves the implementation out of the crowded root `ops` package
while keeping both public routes and both response contracts unchanged.

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.sandboxconnection`:

- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierBoundaryCatalog`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierContextCatalog`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierDownstreamIntakeCatalog`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierExecutionGuardCatalog`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierHandoffCatalog`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierPreconditionEvidenceCatalog`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierRenderer`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSourceCatalog`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierVerificationCatalog`
- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierWarningCatalog`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestBoundaryCatalog`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestCodeHealthCatalog`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestFieldCatalog`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestHandoffCatalog`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestReferenceCatalog`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestRenderer`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSourceCatalog`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSplitCatalog`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestVerificationCatalog`
- `OpsShardReadinessSandboxConnectionRoutePaths`

Both root controllers
(`OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierController` and
`OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestController`)
stay in `com.codexdemo.orderplatform.ops`. The root `OpsShardReadinessRoutePaths`
table still delegates both public suffixes through the now-public sandbox
connection route-path class in the new subpackage. Because both services are
dependency-injected with the shared `OpsEvidenceService` and built in tests
through `OpsEvidenceServiceTestFixtures`, three behaviour-neutral visibility
steps were needed: the moved services/catalogs/support now import the public
`OpsEvidenceService` and `ReleaseApprovalRehearsalResponse` types they previously
referenced same-package; the single shared schema-version constant
`RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION`
on `OpsEvidenceService` was made public; and the moved test support reuses the
already-public `OpsEvidenceServiceTestFixtures`.

## Root Package Pressure

| Metric | v1802 baseline | v1803 after extraction |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,269 | 1,243 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

This is intentionally a root-pressure reduction, not a behavior change. The file
count under `ops` is unchanged because the same implementation files now live
under a narrower maintenance package. This is the largest single root-pressure
reduction so far (twenty-six files) because the family carries two sibling
registry sub-clusters that share one route-path class.

## Contract Preservation

The external endpoints remain:

```text
/api/v1/ops/shard-readiness/sandbox-connection-blocked-execution-context-normalization-dossier
/api/v1/ops/shard-readiness/sandbox-connection-precheck-upstream-receipt-verification-manifest
```

Both responses still report their source plan, context/precondition/boundary
evidence, execution guards, warnings, downstream intake, split modules, code
health gates, verification gates, handoff notes, and markdown sections
unchanged, with `readOnly=true`, no credential value reads, no raw endpoint URL
resolution, and no actual sandbox connection attempt.

## Archive Boundary

Do not rename or move archive roots.

This extraction does not rename or move archive roots. `a/` through `f/`,
`e/<version>/`, evidence JSON files, screenshot archives, and historical code
walkthrough folders remain in place. Node-side references to Java evidence
archives are therefore not invalidated.

## Test Boundary

The package-local catalog, source, boundary, immutability, and split tests, plus
both package-local test supports, moved with the implementation; the moved test
supports import the now-public shared `OpsEvidenceServiceTestFixtures`. The two
route-and-controller tests stay in the root package (each asserts the route
delegates through the root `OpsShardReadinessRoutePaths`) and now construct their
service directly through the shared fixture instead of the moved package-local
support.

The extraction is guarded by:

- `OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierControllerTests`
- `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestControllerTests`
- `ReadabilityUpkeepOpsConsolidationExtractionV1803Tests`
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, actual sandbox connection attempt, managed audit HTTP/TCP connection,
deployment, rollback, Java autostart, mini-kv autostart, or historical archive
movement is opened by this version.
