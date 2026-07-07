# Operator evidence value supply base extraction v1831

v1831 moves the `OperatorEvidenceValueSupply` base implementation out of the
direct-root `ops` package and into:

```text
com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply
```

The two public Spring controllers remain in the root package:

- `OpsShardReadinessOperatorEvidenceValueSupplyFoundationController`
- `OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController`

Direct Java files in the root `ops` package fall from 848 to 833. The remaining
direct-root non-controller backlog falls from 743 to 728. Total `ops` Java files
stay at 1,352 because the old Support helper is folded into `SlotCatalog`,
offsetting the new route owner. Do not rename or move archive roots.
The files stay at 1,352 with no total-count relaxation.

## What Moved

The moved package owns the 12 base read-only services, the shared response
record, and the slot catalog. The old local Support helper is folded into
`SlotCatalog` so the package gains a route owner without increasing the total
`ops` Java file count. The moved services still produce the same read-only
evidence payloads and keep the same Spring transaction boundary.

The public route suffixes now live in
`OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths`. The root
`OpsShardReadinessRoutePaths` class delegates to that owner so existing
controller annotations and route-path tests keep proving byte-identical endpoint
strings.

## Cross-Package Readers

`OperatorEvidenceValueSupplyAdapterPreflight` and
`OperatorEvidenceValueSupplyApprovalPreflight` already consume base endpoint
constants as upstream proof. v1831 updates those imports to the moved package
and keeps the service `ENDPOINT` constants public. This makes the dependency
graph explicit:

```text
OperatorEvidenceValueDraft -> OperatorEvidenceValueSupply
    -> AdapterPreflight / ApprovalPreflight / signed-approval descendants
```

No write routing, active shard router, credential value, raw endpoint,
managed-audit connection, deployment, rollback, or Node-driven process control
surface is opened.

## Mechanical Evidence

- `scripts/ops-root-census.ps1 -Json` reports 833 direct-root Java files, 105
  retained-root files, 728 remaining direct-root non-controller files, zero
  unassigned files, and zero files in both
  `OperatorEvidenceValueSupplyAdapterPreflight` and `OperatorEvidenceValueSupply
  base` buckets.
- `ReadabilityUpkeepOpsConsolidationExtractionV1831Tests` proves the moved
  package, retained controllers, route owner, downstream imports, SpotBugs FQN,
  root-count ratchet, and v1831 walkthrough archive.
- `ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests` binds the live census
  to 833/105/728.
- The v1830 historical guard now allows later reductions below 848 while still
  proving AdapterPreflight remained extracted.

## Next Cut

The next low-coupling work should move the compared-evidence chain in small
contract-preserving batches:

1. `ComparedEvidenceCandidateBlueprint`
2. `ComparedEvidenceCandidateIntakePreflight`
3. `ComparedEvidenceEvaluationPreflight`
4. `ComparedPackageReview`

Each batch should keep the same recipe: leaf route owner, explicit inbound
endpoint readers, root-count ratchet, docs before final verify, and no archive
path movement.
