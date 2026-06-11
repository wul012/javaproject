> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 246. Java v244 v1 contract consumer readiness handoff catalog JSON boundary

v244 的重点是解析 evidence JSON，检查字段语义。

v243 只能证明文件存在。
如果一个 JSON 文件存在，但里面写成 `executionAllowed: true`，归档存在性测试是看不出来的。
所以 v244 增加 `OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogJsonBoundaryTests`。

## 检查方式

测试遍历 post-handoff catalog 的所有 receipt：
1. 用 catalog 的 `evidencePath` 找到 JSON；
2. 用 Jackson `ObjectMapper` 解析；
3. 检查 `version`、`status`、`readOnly`、`executionAllowed`。

这几个字段是 evidence 收据的最低边界：
- version 必须能追溯；
- status 必须是 passed；
- readOnly 必须为 true；
- executionAllowed 必须为 false。

## 工程意义

这个测试会随着 catalog 自动增长。
后续 v245、v246 等版本只要加入 catalog，它们的 JSON 也会被 v244 测试覆盖。

验证命令：
```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogJsonBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogArchivePresenceTests" test
```
