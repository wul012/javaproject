# 198. Java v196 shard readiness v1 contract operator checklist

## What changed

- Added `OpsShardReadinessV1ContractOperatorChecklistResponse`.
- Added `OpsShardReadinessV1ContractOperatorChecklistService`.
- Exposed `GET /api/v1/ops/shard-readiness/v1-contract-operator-checklist`.
- Added the static fixture `/contracts/java-shard-readiness-v1-contract-operator-checklist-v196.fixture.json`.
- Registered the new endpoint pair in shard-readiness evidence ordering and ops evidence read-only probe lists.
- Added service and MockMvc integration coverage.

## Why it matters

The Java side now has a compact checklist surface for Node and operators to consume without opening execution. It references the v193 packet plus v194/v195 guards, so the handoff is easy to verify while preserving historical snapshots.

## Boundary

No write routing, active shard routing, credential reads, raw endpoint parsing, managed audit connection, deployment, rollback, runtime execution, or Node-managed Java/mini-kv process control.
