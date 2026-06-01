# 197. Java v195 v193 evidence packet historical snapshot compatibility

## What changed

- Updated `OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests`.
- Raised the rolling registry guard from 25 to 26 endpoint pairs after the v193 evidence packet.
- Added explicit assertions that the v193 packet endpoint and fixture are absent from frozen v179 and v184 snapshots.
- Added v195 evidence and visual archive.

## Why it matters

The Java evidence registry can keep growing, but older handoff snapshots must remain stable. v195 makes that behavior executable for the v193 evidence packet.

## Boundary

No new route, runtime execution, write routing, active shard routing, credential reads, raw endpoint parsing, managed audit connection, deployment, rollback, or Node-managed Java/mini-kv process control.
