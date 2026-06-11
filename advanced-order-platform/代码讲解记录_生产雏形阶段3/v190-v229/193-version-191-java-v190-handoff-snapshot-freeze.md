# 193. Java v191 v190 handoff snapshot freeze

## What changed

- Added `OpsShardReadinessV1ContractAlignmentHandoffSnapshot`.
- Updated `OpsShardReadinessV1ContractAlignmentHandoffService` to build v190 handoff from frozen snapshot inputs.
- Updated service coverage and added snapshot-specific coverage.
- Added v191 evidence and visual archive.

## Why it matters

v190 introduced a read-only handoff endpoint. v191 prevents that historical handoff receipt from drifting if later Java versions add endpoints or change live alignment helpers. The live registry tests still guard current endpoint reachability; the handoff receipt is now historical evidence.

## Boundary

No new route, runtime execution, write routing, active shard routing, credential reads, raw endpoint parsing, managed audit connection, deployment, rollback, or Node-managed Java/mini-kv process control.
