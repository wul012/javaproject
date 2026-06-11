# 206. Java v204 v202 consumer probe plan historical snapshot compatibility

## What changed

- Updated `OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests`.
- Raised the rolling registry guard from 28 to 29 endpoint pairs after the v202 probe plan.
- Added explicit assertions that v179 and v184 snapshots do not contain the v202 probe plan endpoint or fixture.
- Added v204 evidence and visual archive.

## Why it matters

The Java shard-readiness registry can keep growing, but old endpoint snapshots stay immutable. v204 makes that guarantee executable for the v202 consumer probe plan.

## Boundary

No new route, runtime execution, write routing, active shard routing, credential reads, raw endpoint parsing, managed audit connection, deployment, rollback, Java/mini-kv process control, or upstream action is allowed.
