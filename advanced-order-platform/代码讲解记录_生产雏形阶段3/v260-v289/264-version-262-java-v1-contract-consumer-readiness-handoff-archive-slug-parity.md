> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 264. Java v262 v1 contract consumer readiness handoff archive slug parity

v262 验证归档文件 slug 一致性。

测试从 catalog evidence JSON 文件名推导 archive stem，然后检查 browser snapshot、HTML 和 PNG 是否使用同一个 stem。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveSlugParityTests,OpsShardReadinessV1ContractConsumerReadinessHandoffBoundaryFieldCompletenessTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
