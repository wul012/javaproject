# 227. Java v225 v1 contract consumer readiness handoff

## What changed

v225 introduces a new read-only endpoint:

`GET /api/v1/ops/shard-readiness/v1-contract-consumer-readiness-handoff`

The endpoint is implemented by `OpsShardReadinessV1ContractConsumerReadinessHandoffService` and returns `OpsShardReadinessV1ContractConsumerReadinessHandoffResponse`.

## Why this layer exists

The previous run ended at v224 with a completed consumer digest chain:

- v215 created the consumer verification checklist.
- v216-v219 froze and guarded that checklist.
- v220 created the consumer evidence digest.
- v221-v224 froze and guarded the digest.

The new v225 handoff endpoint gives a downstream consumer one final place to read both the digest input chain and the digest guard chain. It is a handoff summary, not a runtime switch.

## Evidence model

The response deliberately splits evidence into two lists.

`digestEvidence` contains the v215-v219 evidence that belongs to the frozen v220 digest input chain. This is the evidence Node should read to understand the checklist source and its earlier guards.

`handoffGuardEvidence` contains v221-v224 evidence. These receipts prove that the digest itself was frozen, kept historically compatible, checked for integrity, and completed as a consumer readiness surface.

The split prevents a subtle regression: later guard receipts must not be backfilled into the frozen v220 digest. They prove the digest; they do not rewrite the digest.

## Registration points

The endpoint is registered in:

- `OpsShardReadinessRoutePaths`;
- `OpsShardReadinessV1ContractController`;
- `OpsShardReadinessV1ContractEndpointPairs`;
- `OpsShardReadinessEvidenceEndpoints`;
- `OpsEvidenceService` probe and evidence endpoint lists;
- historical endpoint snapshot compatibility tests.

## Tests added or updated

`OpsShardReadinessV1ContractConsumerReadinessHandoffServiceTests` checks the response shape, digest reference, evidence lists, handoff checks, blocked operations, receipt id, and evidence path.

`OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrationTests` checks both the live endpoint and the static fixture.

Existing registry, controller split, route path, endpoint pair, OpsEvidence, evidence endpoint, historical compatibility, route inventory, and consumer readiness completion tests were updated from ten to eleven v1 contract pairs.

## Boundary

This version does not enable write routing, active shard routing, credential value reads, raw endpoint parsing, managed audit connections, deployment, rollback, Node-driven Java startup/shutdown, or mini-kv startup/shutdown.
