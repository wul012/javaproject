# 205. Java v203 v202 consumer probe plan snapshot freeze

## What changed

- Added `OpsShardReadinessV1ContractConsumerProbePlanSnapshot`.
- Updated `OpsShardReadinessV1ContractConsumerProbePlanService` to return the frozen Java v202 probe plan.
- Added snapshot-specific coverage and kept the service/integration coverage intact.
- Added v203 evidence and visual archive.

## Why it matters

The Java v202 consumer probe plan is now historically stable. Future endpoint registry changes can happen without changing the probe plan receipt a consumer may already have archived.

## Boundary

No new route, runtime execution, write routing, active shard routing, credential reads, raw endpoint parsing, managed audit connection, deployment, rollback, Java/mini-kv process control, or upstream action is allowed.
