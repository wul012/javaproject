# Compared evidence candidate blueprint extraction v1832

v1832 moves the `ComparedEvidenceCandidateBlueprint` implementation out of the
direct-root `ops` package and into:

```text
com.codexdemo.orderplatform.ops.maintenance.comparedevidencecandidateblueprint
```

The public Spring controller remains in the root package:

- `OpsShardReadinessComparedEvidenceCandidateBlueprintController`

Direct Java files in the root `ops` package fall from 833 to 819. The remaining
direct-root non-controller backlog falls from 728 to 714. Total `ops` Java files
stay at 1,352 because the old `ComparedEvidenceCandidateBlueprintEndpointRefs`
class is folded into the new route owner instead of being carried as a second
file. Do not rename or move archive roots.
The files stay at 1,352 with no total-count relaxation.

## Requirement Evidence Matrix

| Requirement | Implementation | Evidence | Status |
| --- | --- | --- | --- |
| Move the candidate blueprint implementation out of root | Thirteen implementation files now live in `ops.maintenance.comparedevidencecandidateblueprint`; only the controller remains in root | `ReadabilityUpkeepOpsConsolidationExtractionV1832Tests` checks the package and root absence | Complete |
| Preserve endpoint bytes | `OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths` owns the five suffixes and full endpoint strings; root aggregation delegates to it | route-path tests compare root suffixes and moved service `ENDPOINT` values | Complete |
| Avoid total file growth | The old EndpointRefs helper is removed and its five full endpoint constants live on the route owner | v1832 guard checks no `ComparedEvidenceCandidateBlueprintEndpointRefs` file or reference remains and total `ops` Java files stay <= 1,352 | Complete |
| Keep inbound readers explicit | CandidateIntakePreflight slot catalogs and ProfileSection readers import the moved package boundary | v1832 guard checks route-owner and service imports | Complete |
| Keep outbound read-only source explicit | Moved section catalogs import the still-root `ComparedEvidenceEvaluationPreflightEndpointRefs`, whose immutable constants are public | v1832 guard checks public class and constants | Complete |

## What Moved

The moved package owns the source, comparison, policy, and closeout services,
their section catalogs, the blocker catalog, the shared support helper, the
catalog service, the response record, and the family route owner. The retained
root controller imports those services directly from the new package so the HTTP
entry point remains visible in the old root package while implementation pressure
moves into the maintenance namespace.

The old `OpsShardReadinessComparedEvidenceCandidateBlueprintEndpointRefs` helper
is intentionally not preserved. Its five full endpoint constants now live on
`OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths` beside the suffix
constants. That keeps the route owner useful to downstream readers and avoids
adding a new file while removing a root file.

## Cross-Package Readers

`ComparedEvidenceCandidateIntakePreflight` reads the candidate blueprint source,
comparison, policy, and closeout endpoints as upstream evidence. Those slot
catalogs now import
`OpsShardReadinessComparedEvidenceCandidateBlueprintRoutePaths` from the moved
package. `SignedApprovalDraftTextPackageProfileSection` reads the candidate
blueprint catalog response and catalog service; those readers now import the
moved response and service.

The moved blueprint section catalogs still read the next upstream
`ComparedEvidenceEvaluationPreflight` endpoint references. v1832 makes that
still-root endpoint-reference class and its immutable constants public, without
moving the evaluation-preflight implementation early. This is a read-only
boundary change only.

No write routing, active shard router, credential value, raw endpoint,
managed-audit connection, deployment, rollback, Node-driven process control, or
mini-kv process control surface is opened.

## Mechanical Evidence

- `scripts/ops-root-census.ps1 -Json` reports 819 direct-root Java files, 105
  retained-root files, 714 remaining direct-root non-controller files, zero
  unassigned files, and a zero-count `ComparedEvidenceCandidateBlueprint`
  bucket.
- `ReadabilityUpkeepOpsConsolidationExtractionV1832Tests` proves the moved
  package, retained controller, route owner, EndpointRefs collapse, inbound
  readers, outbound public read-only endpoint refs, SpotBugs FQN relocation,
  root-count ratchet, total-count ratchet, and v1832 walkthrough archive.
- `ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests` binds the live census
  to 819/105/714.
- The v1831 historical guard allows later reductions below 833 while still
  proving the ValueSupply base remained extracted.

## Next Cut

The next low-coupling work should continue the compared-evidence chain:

1. `ComparedEvidenceCandidateIntakePreflight`
2. `ComparedEvidenceEvaluationPreflight`
3. `ComparedPackageReview`

Each batch should keep the same recipe: one family per version, leaf route
owner, explicit endpoint readers, root-count ratchet, total-file-count guard,
docs before final verify, and no archive path movement.
