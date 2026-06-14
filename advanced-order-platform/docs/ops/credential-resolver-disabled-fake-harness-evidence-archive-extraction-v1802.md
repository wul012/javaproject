# v1802 credential resolver disabled fake harness evidence archive extraction

This note records the sixth contract-preserving class extraction after the
v1796 inventory. It is the second extraction outside the CodeWalkthrough family
and the first to extract a dependency-injected "evidence" registry: the
credential resolver disabled fake harness evidence archive. It moves the
implementation out of the crowded root `ops` package while keeping the public
route and response contract unchanged.

## Extraction Scope

Moved from `com.codexdemo.orderplatform.ops` to
`com.codexdemo.orderplatform.ops.maintenance.credentialresolver`:

- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveBoundaryCatalog`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveHandoffCatalog`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRenderer`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRequirementCatalog`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRuntimeGuardCatalog`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSourceCatalog`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSupport`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveVerificationCatalog`
- `OpsShardReadinessCredentialResolverRoutePaths`

The root controller stays in `com.codexdemo.orderplatform.ops`. The root
`OpsShardReadinessRoutePaths` table still delegates the public suffix through
the now-public credential resolver route-path class in the new subpackage.
Because this service is dependency-injected with the shared `OpsEvidenceService`
and built in tests through `OpsEvidenceServiceTestFixtures`, two extra
visibility steps were needed (both behaviour-neutral): the moved service/catalogs
now import the public `OpsEvidenceService` and `ReleaseApprovalRehearsalResponse`
types they previously referenced same-package, and the shared test fixture
`OpsEvidenceServiceTestFixtures` was made public so the moved test support can
reuse it.

## Root Package Pressure

| Metric | v1801 baseline | v1802 after extraction |
| --- | ---: | ---: |
| All main Java files under `ops` | 1,352 | 1,352 |
| Direct Java files in root `ops` package | 1,280 | 1,269 |
| Main Java files whose names include `Readiness` | 1,210 | 1,210 |

This is intentionally a root-pressure reduction, not a behavior change. The
file count under `ops` is unchanged because the same implementation files now
live under a narrower maintenance package.

## Contract Preservation

The external endpoint remains:

```text
/api/v1/ops/shard-readiness/credential-resolver-disabled-fake-harness-evidence-archive
```

The response still reports its source plan, java/mini-kv requirements, fake
harness boundary, runtime guards, verification gates, handoff notes, and
markdown sections unchanged, with `readOnly=true`, no credential value reads, no
raw endpoint URL resolution, and no managed audit connection.

## Archive Boundary

Do not rename or move archive roots.

This extraction does not rename or move archive roots. `a/` through `f/`,
`e/<version>/`, evidence JSON files, screenshot archives, and historical code
walkthrough folders remain in place. Node-side references to Java evidence
archives are therefore not invalidated.

## Test Boundary

The package-local catalog test and the package-local test support moved with the
implementation; the moved test support imports the now-public shared
`OpsEvidenceServiceTestFixtures`. The combined service/controller test stays in
the root package (it asserts the route delegates through the root
`OpsShardReadinessRoutePaths`) and now constructs the service directly through
the shared fixture instead of the moved package-local support.

The extraction is guarded by:

- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveCatalogTests`
- `OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveServiceControllerTests`
- `ReadabilityUpkeepOpsConsolidationExtractionV1802Tests`
- `ReadabilityUpkeepGovernanceConsolidationPlanTests`

## Stop Line

No write routing, active shard routing, credential value reads, raw endpoint URL
resolution, managed audit HTTP/TCP connection, deployment, rollback, Java
autostart, mini-kv autostart, or historical archive movement is opened by this
version.
