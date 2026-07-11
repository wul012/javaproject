# Minimal read-only gate Operator-CI ConsumerPackage extraction v1846

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| Move one coherent consumer-package layer | 23 non-controller manifest, audience, section, acceptance, CI-matrix, boundary, checklist, scorecard, renderer, service, response, and support files moved to `ops.maintenance.minimalreadonlygateoperatorciconsumerpackage` | v1846 exact-list guard checks new paths and old-root absence | implemented |
| Keep HTTP discovery stable | The ConsumerPackage Spring controller remains direct-root and imports the moved service/response | Retained-controller and import assertions | implemented |
| Move package-local tests | Four package tests move beside implementation; the controller Markdown aggregate test remains root-visible | Exact test list and retained-test assertions | implemented |
| Avoid a reproduced Windows path failure | The literal full-prefix package measured 264 characters; the shorter semantic package measures at most 244 in this workspace | Guard rejects the overlong package segment; census documentation records both measurements | implemented |
| Preserve upstream and route ownership | The service consumes only the v1845 digest service/response and v1840 public RoutePaths owner | Source-level dependency assertions | implemented |
| Prepare the next cut | Twelve VerificationDossier production consumers import only the public ConsumerPackage service/response | Named downstream assertions | implemented |
| Keep SpotBugs aligned | ConsumerPackage response and nested Markdown response move in both mirrored exclusion blocks | New FQN required; old root FQN rejected | implemented |
| Tighten the census | Direct root 621 -> 598; movable 516 -> 493; Operator-CI bucket 96 -> 73; unassigned remains 0 | Census script plus current, historical, and bucket guards | implemented |
| Preserve read-only behavior | No process start, secret read, raw URL resolution, managed audit HTTP, write, deployment, or rollback path is introduced | Existing package source/catalog/checklist/controller, architecture, and full-verify tests | passed |
| Explain before final verification | Chinese walkthrough explains inputs, outputs, path deviation, status conjunction, and Java/mini-kv boundaries | Walkthrough policy plus v1846 token assertions | implemented |

## Boundary and path decision

The package transforms the v1845 digest into a consumer-facing bundle: one
source snapshot, five manifest entries, four audiences, five sections, five
acceptance criteria, five read-only CI matrix rows, eight boundary locks, five
handoff checklist entries, eight scorecard entries, and nine Markdown sections.
The root retains only the Spring controller. Package-private catalogs and
renderers remain hidden; only the service, immutable response, and test factory
cross the package boundary.

The initially obvious package segment
`minimalreadonlygateoperatorcihandoffarchivedigestconsumerpackage` was rejected
before moving files because the longest Windows target measured 264 characters.
The chosen `minimalreadonlygateoperatorciconsumerpackage` keeps the domain and
responsibility visible while measuring 244 characters. No Java class name,
route string, JSON field, or public endpoint changes as a result.

```text
v1845 ArchiveDigest service/response
  -> v1846 ConsumerPackage service/response
  -> v1847 queued VerificationDossier service/response
```

## Mechanical proof commands

```powershell
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q -Dtest='OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackage*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1845Tests,ReadabilityUpkeepOpsConsolidationExtractionV1846Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepMaintainabilityBudgetTests,ReadabilityUpkeepWalkthroughPolicyTests' test
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd verify
```

Before final verification, compilation passes and the focused suite completes
successfully in 1m09s. Full `mvnw verify` then passes in 10m30s with 1,742
tests, JaCoCo checks met, and SpotBugs reporting zero findings. The census reports 598
direct-root files, 105 retained files, 493 remaining non-controllers, 73 files
in the Operator-CI bucket, and zero unassigned files. Focused and full-verify
receipts are recorded in the Java-owned progress ledger after the implementation
commit is pushed and its CI run is captured.

## Failure conditions

The version fails if the 23/4 exact lists drift, the controller or controller
Markdown aggregate test leaves root, the overlong package segment appears, an
internal catalog becomes public without a consumer, VerificationDossier reaches
inside the package, a route or response byte changes, the old SpotBugs FQN
survives, total `ops` Java files exceed 1,352, direct-root files exceed 598, an
unassigned census file appears, or any walkthrough, test, coverage, formatting,
or static-analysis gate fails.
