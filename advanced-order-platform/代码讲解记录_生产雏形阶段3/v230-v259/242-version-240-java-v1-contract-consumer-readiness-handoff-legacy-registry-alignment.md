> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 242. Java v240 v1 contract consumer readiness handoff legacy registry alignment

v240 是对 v239 之后 full test alignment 的正式版本化。

上一轮最后一个提交修复了两个旧测试的期待：它们原来把更宽的 rolling evidence registry 当成了 v1 consumer registry，导致 read-only catalog 或 runtime closeout 被误认为应该出现在 v1 consumer 合同链里。

## 本轮修复后的边界

现在边界分成两层：
- `OpsShardReadinessV1ContractEndpointPairs`：只描述 v1 consumer 合同链，保持 11 个 endpoint pair；
- `OpsShardReadinessEvidenceEndpoints`：描述整个 shard-readiness evidence 的滚动链，可以包含 read-only catalog、handoff verification、runtime closeout 等后续证据。

v240 新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffLegacyRegistryAlignmentTests`，用更明确的名字把这条边界锁住。

## 测试覆盖

测试主要确认四件事：
- v1 consumer registry 仍然是 11 个 pair；
- endpoint catalog -> consumer bundle -> checklist -> evidence digest -> readiness handoff 的顺序不变；
- read-only catalog 和 runtime execution pass evidence closeout 不进入 v1 consumer registry；
- fixture registry 与 live registry 保持同样的边界。

另外，测试还确认 rolling evidence endpoints 仍然能从 echo、v1 alignment、consumer digest、readiness handoff 继续走到 read-only catalog。
这说明 v240 不是收窄整体证据链，而是避免把不同层级的 registry 混为一谈。

## 工程意义

v240 的价值在于把“补丁修红”变成“证据归档”。
后续版本继续推进时，如果有人又把 read-only catalog 误加进 v1 consumer registry，v240 的测试会直接失败。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffLegacyRegistryAlignmentTests,OpsShardReadinessV1ContractConsumerHandoffBundleIntegrityTests,OpsShardReadinessV1ContractConsumerVerificationChecklistIntegrityTests" test
```
