# 201. Java v199 shard readiness v1 contract handoff manifest

## What changed

- Added `OpsShardReadinessV1ContractHandoffManifestResponse`.
- Added `OpsShardReadinessV1ContractHandoffManifestService`.
- Exposed `GET /api/v1/ops/shard-readiness/v1-contract-handoff-manifest`.
- Added the static fixture `/contracts/java-shard-readiness-v1-contract-handoff-manifest-v199.fixture.json`.
- Registered the new endpoint pair in shard-readiness evidence ordering and ops evidence read-only probe lists.
- Added service and MockMvc integration coverage.

## Why it matters

The Java side now has one compact manifest that connects the packet, checklist, freeze evidence, and compatibility guards. That gives Node and operators a stable handoff surface without opening any execution path.

## Boundary

No write routing, active shard routing, credential reads, raw endpoint parsing, managed audit connection, deployment, rollback, runtime execution, or Node-managed Java/mini-kv process control.
