# 264. Java v262 v1 contract consumer readiness handoff archive slug parity

v262 验证归档文件 slug 一致性。

测试从 catalog evidence JSON 文件名推导 archive stem，然后检查 browser snapshot、HTML 和 PNG 是否使用同一个 stem。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveSlugParityTests,OpsShardReadinessV1ContractConsumerReadinessHandoffBoundaryFieldCompletenessTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
