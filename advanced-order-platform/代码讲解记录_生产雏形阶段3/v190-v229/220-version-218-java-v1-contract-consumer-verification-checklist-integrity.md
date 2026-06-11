> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 220. Java v218 v1 contract consumer verification checklist integrity

This version adds a cross-layer integrity guard for the v215 checklist.

`OpsShardReadinessV1ContractConsumerVerificationChecklistIntegrityTests` checks:

- the v1 endpoint pair registry remains focused at nine pairs;
- endpoint catalog, consumer handoff bundle, and consumer verification checklist stay adjacent;
- checklist handoff bundle fields match the frozen v211 bundle;
- checklist counts and blocked operations match the bundle;
- required evidence excludes checklist self evidence and later receipts;
- verification checks mirror the v211 bundle version and counts.

The service now exposes a named v218 integrity evidence path constant for later handoff work.
