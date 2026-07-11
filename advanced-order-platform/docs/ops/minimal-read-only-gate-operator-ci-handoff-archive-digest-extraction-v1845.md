# Minimal read-only gate Operator-CI handoff ArchiveDigest extraction v1845

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| Move one coherent digest layer | 17 non-controller digest, consumer-packet, replay, boundary, scorecard, renderer, and support files moved to `ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest` | The v1845 readability guard checks the exact file list and old-root absence | implemented |
| Keep the HTTP entry visible | The ArchiveDigest Spring controller remains in direct-root `ops` and imports the moved service/response | Exact controller path and import assertions | implemented |
| Move package-local tests | Five digest service/catalog/support tests move beside the package; the controller Markdown test remains root-visible | Exact test list, old-path absence, and retained-test assertion | implemented |
| Preserve the upstream contract | The digest service consumes only the v1844 archive-verification service/response | Source and import assertions in the v1845 guard | implemented |
| Preserve route bytes and ownership | The service uses the v1840 public release-acceptance RoutePaths owner; no new route constant is created | Route-owner assertion and the unchanged 1,111-line root aggregator ratchet | implemented |
| Prepare the next dependency-safe cut | Eleven ConsumerPackage production files import the public digest service or response | Named downstream-consumer assertions | implemented |
| Keep static analysis aligned | The digest response and nested Markdown response are relocated in both SpotBugs mirror blocks | New FQN required and old FQN rejected | implemented |
| Tighten the finite census | Direct root 638 -> 621; movable 533 -> 516; Operator-CI bucket 113 -> 96; unassigned remains 0 | Census script plus global, historical, and exact bucket tests | implemented |
| Preserve read-only behavior | No process start, credential read, raw URL resolution, managed audit HTTP, write, deployment, or rollback path is introduced | Existing digest aggregate, source, catalog, replay, controller, architecture, and full-verify tests | passed |
| Explain before final verification | A Chinese walkthrough under the v1843-v1847 continuation directory explains the actual input/output and blocking rules | Walkthrough policy plus v1845 token assertions | implemented |

## Boundary and dependency flow

The package owns the digest registry response and service, one source-archive
projection, six digest sections, four consumer packets, five read-only replay
instructions, eight boundary locks, six scorecard entries, six Markdown
sections, and their renderers. The root retains only the public Spring
controller. Internal catalogs and renderers remain package-private; only the
service, immutable response, and one test factory cross the package boundary.

```text
v1844 Operator-CI archive-verification response
  -> v1845 ArchiveDigest registry
  -> v1846 queued ConsumerPackage registry
```

This version creates no production class and changes no response field. It
moves 17 existing production files and five tests. The service now imports the
public route owner directly rather than reaching back through the root route
aggregator. ConsumerPackage already imports the new digest boundary, so the
next extraction can move downstream without opening digest internals.

## Mechanical proof commands

```powershell
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q -Dtest='OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigest*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1844Tests,ReadabilityUpkeepOpsConsolidationExtractionV1845Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepMaintainabilityBudgetTests,ReadabilityUpkeepWalkthroughPolicyTests' test
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd verify
```

Before final verification, compilation passes and the focused suite completes
successfully in 1m02s. Full `mvnw verify` then passes in 11m38s with 1,736
tests, JaCoCo checks met, and SpotBugs reporting zero findings. The census reports 621
direct-root files, 105 retained files, 516 remaining non-controllers, 96 files
in the Operator-CI bucket, and zero unassigned files. Focused and full verify
receipts are recorded in the Java-owned progress ledger after the implementation
commit is pushed and its CI run is captured.

## Failure conditions

The version fails if any listed implementation remains in root, the controller
or its Markdown test leaves root, a package-private catalog becomes public
without a real consumer, ConsumerPackage imports an internal digest type, a
route or response byte changes, an old SpotBugs FQN survives, total `ops` Java
files exceed 1,352, direct-root files exceed 621, the census has an unassigned
file, the walkthrough policy fails, or any full-verify test, coverage check, or
static-analysis check fails.
