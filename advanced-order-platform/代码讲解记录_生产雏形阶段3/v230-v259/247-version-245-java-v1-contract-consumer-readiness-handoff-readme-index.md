> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 247. Java v245 v1 contract consumer readiness handoff README index

v245 是 README 索引一致性版本。

证据文件落地后，还需要能被人从总索引中找到。
因此本版新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffReadmeIndexTests`。

## 测试内容

测试读取 `e/README.md`，然后遍历 post-handoff catalog：
- 每个 catalog version 都必须有 README entry；
- README entry 的出现顺序必须和 catalog version 顺序一致；
- v245 evidence path 必须指向 `e/245/evidence/...v245.json`。

这个测试避免两种问题：
- 文件已经生成，但 README 忘记追加；
- README 里版本顺序被打乱，影响人工追踪。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffReadmeIndexTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogJsonBoundaryTests" test
```
