> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 230. Java v228 v1 contract consumer readiness handoff integrity

v228 是 readiness handoff 的完整性版本。它不新增 endpoint，也不改 response schema，而是把 v225 handoff 的关键字段做成精确测试。

## 本版锁住什么

第一层是 digest evidence。v225 handoff 的 `digestEvidence` 必须等于 Java v220 digest 的 evidence 列表，也就是 v215-v219
那组 checklist 输入证据。这里不能混入 v225 自己的 evidence，也不能混入 v226 之后的 handoff guard evidence。

第二层是 guard evidence。`handoffGuardEvidence` 必须只包含 v221-v224 四条 digest guard receipt：

- v220 digest snapshot freeze；
- v220 digest historical compatibility；
- v1 contract consumer evidence digest integrity；
- v1 contract consumer readiness completion。

第三层是 checks。`handoffChecks` 不直接打开任何执行能力，只记录可验证的只读事实：

- digest version 是 Java v220；
- digest evidence count 是 5；
- digest check count 是 7；
- handoff guard evidence count 是 4；
- probes are GET-only；
- upstream actions are denied；
- Node 不允许启停 Java 或 mini-kv。

## 为什么要排除未来证据

v226、v227、v228 自身都会生成新的 evidence receipt。它们属于“对 v225 handoff 的后续验证”，不属于“v225 handoff 发布时的输入”。
如果把这些未来 receipt 放进 frozen v225 handoff，就会制造时间倒流式的证据链。v228 用测试明确禁止这种回填。

## 执行边界

本版额外把 blocked operations 和布尔字段完整断言了一遍，避免只看 `readOnly=true` 而漏掉某个更细的能力开关。

保持关闭的能力包括：

- write routing；
- active shard router；
- credential value read；
- raw endpoint parse；
- managed audit connection；
- deployment or rollback；
- Node start/stop Java or mini-kv。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrityTests,OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshotTests,OpsShardReadinessV1ContractConsumerReadinessHandoffHistoricalCompatibilityTests" test
```

结果：通过。
