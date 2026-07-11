# Minimal read-only gate Operator-CI handoff core extraction v1844

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| Move one coherent production boundary | 27 non-controller base, archive-rendering, and archive-verification files moved to `ops.maintenance.minimalreadonlygateoperatorcihandoff` | `ReadabilityUpkeepOpsConsolidationExtractionV1844Tests#operatorCiCoreMovesWhileControllersStayRootVisible` checks the exact list and old-path absence | implemented |
| Keep HTTP discovery stable | Two Spring controllers remain in direct-root `ops` | The same guard checks both controller files and the controller imports | implemented |
| Move package-local tests | Seven service/catalog/support tests moved beside the package; two controller Markdown tests remain root-visible | `packageTestsMoveWhileControllerMarkdownTestsStayRoot` checks both exact sets | implemented |
| Preserve the upstream contract | The base registry consumes only the public v1843 execution archive-verification service/response | `upstreamAndRouteOwnershipRemainExplicit` checks the imported package and service chain | implemented |
| Preserve route bytes and ownership | Both services use the v1840 public `OpsShardReadinessReleaseAcceptanceRoutePaths`; no new route constant is created | The same guard checks the public owner; the root aggregator line ratchet remains 1,111 | implemented |
| Prepare the next dependency-safe cut | Eight ArchiveDigest consumers import the moved archive-verification service/response | `archiveDigestImportsTheMovedArchiveBoundary` checks every named consumer | implemented |
| Keep static-analysis suppressions accurate | Both response records and both nested Markdown records are relocated in both SpotBugs mirror blocks | `spotbugsAndCountRatchetsFollowTheMove` rejects the old root FQNs | implemented |
| Tighten, never loosen, the census | Direct root 665 -> 638; movable 560 -> 533; Operator-CI bucket 140 -> 113; unassigned remains 0 | Census script plus historical/global ratchet tests | implemented |
| Preserve the read-only safety contract | No write route, credential value, raw endpoint resolution, managed audit HTTP, deployment, rollback, or process start is introduced | Existing service tests, boundary scorecards, architecture tests, and full verify | passed |
| Explain before final verification | Chinese walkthrough is stored under the v1843-v1847 continuation directory | Walkthrough policy tests and the v1844 guard check version marker and required headings | implemented |

## Extraction boundary

The root package keeps only the two public HTTP controllers for this slice. The
new package owns the base Operator-CI handoff registry, its immutable response,
lane/batch/boundary/scorecard catalogs, renderers, and the complete archive
verification registry. This is a semantic unit: the base endpoint translates
the v1843 execution archive into operator and CI plans; the second endpoint
proves those plans are durably represented as read-only archive evidence.

The extraction adds no production class. It moves 27 existing classes and
seven existing tests, so total `ops` Java count must remain at or below 1,352.
The route strings remain byte-owned by the public release-acceptance route
owner introduced in v1840. The root route aggregator continues to delegate and
stays at its existing 1,111-line ceiling.

## Dependency direction

```text
v1843 execution archive-verification service/response
  -> v1844 Operator-CI base registry
  -> v1844 Operator-CI archive-verification registry
  -> v1845 queued ArchiveDigest registry
```

Only four production types cross the v1844 package boundary: the two public
services and two public immutable responses. Package-private catalogs,
renderers, score calculations, and support builders do not leak. Tests use two
public factory supports because the two controller-oriented Markdown tests and
the queued ArchiveDigest tests intentionally remain outside this package.

## Mechanical proof commands

```powershell
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q -Dtest='OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoff*Tests,OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistry*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1843Tests,ReadabilityUpkeepOpsConsolidationExtractionV1844Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepMaintainabilityBudgetTests,ReadabilityUpkeepWalkthroughPolicyTests' test
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd verify
```

Before the final verify, the compile gate and focused suite pass; the focused
suite completed in 1m11s. Full `mvnw verify` then passed in 13m19s with
1,730 tests, JaCoCo checks met, and SpotBugs reporting zero findings. The census reports 638
direct-root files, 105 retained root files, 533 remaining non-controllers, 113
files in the Operator-CI bucket, and zero unassigned files. Focused and full
verification receipts are appended to the Java-owned progress ledger after
the implementation commit is pushed and its remote CI run is captured.

## Failure conditions

The version fails if any moved class returns to root, either controller leaves
root, any package-private implementation is made public without a consumer, an
ArchiveDigest consumer reaches around the archive-verification boundary, a
route byte changes, total `ops` Java files increase above 1,352, the direct-root
ratchet rises above 638, the bucket census has an unassigned file, a SpotBugs
entry still names the old package, or any full-verify test, coverage check, or
static-analysis check fails.
