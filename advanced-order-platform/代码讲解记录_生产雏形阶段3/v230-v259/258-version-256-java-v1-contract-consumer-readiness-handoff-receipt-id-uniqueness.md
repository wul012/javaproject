> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 258. Java v256 v1 contract consumer readiness handoff receipt id uniqueness

v256 把 evidence JSON 的 receiptId 纳入测试。

测试遍历 post-handoff catalog，读取每个 JSON 的 `receiptId`：
- 要求以 `-v<version>` 结尾；
- 要求所有 receiptId 不重复。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptIdUniquenessTests,OpsShardReadinessV1ContractConsumerReadinessHandoffFixtureContractBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
