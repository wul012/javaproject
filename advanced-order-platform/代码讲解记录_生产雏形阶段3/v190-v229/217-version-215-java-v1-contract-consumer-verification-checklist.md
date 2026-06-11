# 217. Java v215 v1 contract consumer verification checklist

This version adds the next consumer-facing read-only contract layer:

- `OpsShardReadinessV1ContractConsumerVerificationChecklistService` builds a v215 checklist from the frozen v211 handoff bundle snapshot.
- `OpsShardReadinessV1ContractController` exposes `GET /api/v1/ops/shard-readiness/v1-contract-consumer-verification-checklist`.
- The static fixture `/contracts/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.fixture.json` mirrors the endpoint contract.
- Route-path, controller split, endpoint pair, evidence endpoint, OpsEvidence, historical compatibility, service, and integration tests all register the new read-only surface.

The new checklist deliberately remains a consumer verification receipt. It does not open write routing, an active shard router, credential value reads, raw endpoint parsing, managed audit connections, deployment or rollback, or Node process control over Java / mini-kv.
