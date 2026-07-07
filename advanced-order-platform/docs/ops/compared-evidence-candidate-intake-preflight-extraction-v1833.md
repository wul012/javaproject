# Compared evidence candidate intake preflight extraction v1833

v1833 moves the `ComparedEvidenceCandidateIntakePreflight` implementation out
of the direct-root `ops` package and into:

```text
com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateintakepreflight
```

The public Spring controller remains in the root package:

- `OpsShardReadinessComparedEvidenceCandidateIntakePreflightController`

Direct Java files in the root `ops` package fall from 819 to 805. The remaining
direct-root non-controller backlog falls from 714 to 700. Total `ops` Java files
stay at 1,352 because the standalone `GateCatalog` is folded into the moved
`GuardCatalog`, offsetting the new route owner.
Do not rename or move archive roots. The files stay at 1,352 with no
total-count relaxation.

## Requirement Evidence Matrix

| Requirement | Implementation | Evidence | Status |
| --- | --- | --- | --- |
| Move the intake-preflight implementation out of root | Thirteen implementation files now live in `ops.maintenance.comparedevidencecandidateintakepreflight`; only the controller remains in root | `ReadabilityUpkeepOpsConsolidationExtractionV1833Tests` checks package placement and root absence | Complete |
| Preserve endpoint bytes | `OpsShardReadinessComparedEvidenceCandidateIntakePreflightRoutePaths` owns the five suffixes and full endpoint strings; root aggregation delegates to it | route-path tests compare root suffixes and moved service `ENDPOINT` values | Complete |
| Avoid total file growth | `GateCatalog` is folded into `GuardCatalog`, offsetting the new route owner | v1833 guard checks no standalone GateCatalog remains and total `ops` Java files stay <= 1,352 | Complete |
| Keep upstream candidate-blueprint evidence explicit | Moved slot catalogs read `ComparedEvidenceCandidateBlueprintRoutePaths`, which was extracted in v1832 | v1833 guard checks the moved slot catalogs import the blueprint route owner | Complete |
| Keep downstream profile-section readers explicit | ProfileSection imports the moved intake-preflight catalog service and response | v1833 guard checks main and test support imports | Complete |

## What Moved

The moved package owns the source, comparison, policy, and closeout services,
their slot catalogs, the guard catalog, the shared slot catalog, support,
response, catalog service, and family route owner. The retained root controller
imports those moved services and response types directly.

The old gate list is no longer a separate file. `GuardCatalog` now owns the
gate list and the guard builders because both describe fail-closed intake
preflight conditions. This keeps the package cohesive and prevents the new route
owner from loosening the total-file-count ratchet.

## Cross-Package Readers

`SignedApprovalDraftTextPackageProfileSection` reads the intake-preflight
catalog response and catalog service, so v1833 updates those imports to the
moved package. CandidateDocument has a historical catalog-suffix exposure for
candidate-intake-preflight; it now delegates that suffix to the new
intake-preflight route owner so there is only one current owner for the route
bytes.

The moved slot catalogs read the v1832 candidate-blueprint route owner for their
source, comparison, policy, and closeout evidence pointers. No write routing,
active shard router, credential value, raw endpoint, managed-audit connection,
deployment, rollback, Node-driven process control, or mini-kv process control
surface is opened.

## Mechanical Evidence

- `scripts/ops-root-census.ps1 -Json` reports 805 direct-root Java files, 105
  retained-root files, 700 remaining direct-root non-controller files, zero
  unassigned files, and a zero-count `ComparedEvidenceCandidateIntakePreflight`
  bucket.
- `ReadabilityUpkeepOpsConsolidationExtractionV1833Tests` proves the moved
  package, retained controller, route owner, CandidateDocument delegation,
  GateCatalog fold-in, upstream blueprint imports, downstream ProfileSection
  imports, SpotBugs FQN relocation, root-count ratchet, total-count ratchet, and
  v1833 walkthrough archive.
- `ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests` binds the live census
  to 805/105/700.
- The v1832 historical guard now allows later reductions below 819 while still
  proving CandidateBlueprint remained extracted.

## Checkpoint

v1833 is the fifth extraction batch after the v1828 endgame census. After the
local and remote gates close, this is a review checkpoint before the next
five-batch run. The next technical candidates remain
`ComparedEvidenceEvaluationPreflight` and `ComparedPackageReview`.
