# Operator-CI verification dossier extraction v1847

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| Move one coherent dossier layer | 25 non-controller provenance, section-digest, audience-route, CI-lane, acceptance-gate, boundary-audit, checklist, receipt, scorecard, renderer, service, response, and support files moved to `ops.maintenance.operatorcidossier` | v1847 exact-list guard checks new paths and old-root absence | implemented |
| Keep public HTTP discovery stable | The dossier Spring controller remains direct-root and imports the moved service/response | Retained-controller and import assertions | implemented |
| Move package-local tests | Seven dossier tests move beside implementation; the controller Markdown aggregate test remains root-visible | Exact test list and retained-test assertions | implemented |
| Resolve reproduced path risk | Literal ancestry measured 307 characters, intermediate semantic package 272, and selected package keeps the longest moved test path at 248 | Guard rejects both long package segments; endgame ledger records all three measurements | implemented |
| Preserve upstream and route ownership | The service consumes only the v1846 ConsumerPackage service/response and v1840 public RoutePaths owner | Source dependency assertions | implemented |
| Prepare the next track | Twelve ReleaseAcceptance production consumers import only the public dossier service/response | Named downstream assertions | implemented |
| Keep SpotBugs aligned | Dossier response and nested Markdown response relocate in both mirrored exclusion blocks | New FQN required; old root FQN rejected | implemented |
| Tighten the checkpoint census | Direct root 598 -> 573; movable 493 -> 468; Operator-CI bucket 73 -> 48; unassigned remains 0 | Census script plus exact/global/historical ratchets | implemented |
| Preserve read-only behavior | No process start, secret read, raw URL resolution, managed audit HTTP, write, deployment, or rollback is introduced | Existing dossier source/provenance/section/audience/CI/acceptance/boundary/checklist/receipt/controller tests and full verify | passed |
| Explain before final verification | Chinese walkthrough explains dossier inputs, transformations, outputs, path decision, and blocking rules | Walkthrough policy plus v1847 token assertions | implemented |

## Boundary and dependency flow

The dossier converts the v1846 consumer package into a reviewer-facing
verification bundle: one source snapshot, six provenance entries, nine section
digests, four audience routes, five CI lanes, five acceptance gates, eight
boundary audits, five release-checklist entries, four handoff receipts, ten
scorecard entries, and ten Markdown sections. The root keeps only the Spring
controller. Only the service, immutable response, and test factory cross the
new package boundary.

```text
v1846 ConsumerPackage service/response
  -> v1847 VerificationDossier service/response
  -> queued ReleaseAcceptance service/response
```

The complete ancestry package was not operational on Windows. The selected
`operatorcidossier` package keeps both role and artifact explicit while the
long class names retain the full domain. This is a package-path correction,
not a public contract change.

## Mechanical proof commands

```powershell
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q -Dtest='OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossier*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1846Tests,ReadabilityUpkeepOpsConsolidationExtractionV1847Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepMaintainabilityBudgetTests,ReadabilityUpkeepWalkthroughPolicyTests' test
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd verify
```

Before final verification, compilation passes and the focused suite completes
successfully in 52s. Full `mvnw verify` then passes in 8m18s with 1,748 tests,
JaCoCo checks met, and SpotBugs reporting zero findings. The census reports 573
direct-root files, 105 retained files, 468 remaining non-controllers, 48 files
in the Operator-CI bucket, and zero unassigned files. Because v1847 is the
five-version checkpoint, both local gates and all v1843-v1847 remote CI runs
must pass before the checkpoint can be handed to external review.

## Failure conditions

The version fails if the 25/7 exact lists drift, the controller or controller
Markdown aggregate test leaves root, either overlong package segment appears,
an internal catalog becomes public without a consumer, ReleaseAcceptance
reaches into dossier internals, any route or response byte changes, the old
SpotBugs FQN survives, direct-root files exceed 573, total `ops` Java files
exceed 1,352, the census has an unassigned file, any walkthrough/test/coverage/
format/static-analysis gate fails, or any checkpoint CI run concludes non-green.
