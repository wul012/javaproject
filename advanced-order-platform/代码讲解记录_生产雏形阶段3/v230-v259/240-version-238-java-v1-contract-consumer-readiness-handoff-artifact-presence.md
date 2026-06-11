> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 240. Java v238 v1 contract consumer readiness handoff artifact presence

v238 是 artifact presence guard。它把 v225 readiness handoff 的关键归档文件纳入测试。

## 为什么测试文件存在性

当前推进方式强调“版本化证据 + 只读边界 + 交接归档”。这意味着代码通过还不够，证据文件也必须留在仓库里。

如果后续清理目录时误删了 PNG、browser snapshot 或说明文档，代码测试可能依然通过，但交接证据会断。

## 测试内容

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffArtifactPresenceTests`：

- 检查 static fixture；
- 检查 v225 evidence JSON；
- 检查 v225 browser snapshot；
- 检查 v225 HTML evidence page；
- 检查 v225 PNG screenshot；
- 检查 v225 中文说明。

测试还要求文件名和 v225 readiness handoff 版本保持一致，避免归档文件被替换成无关内容。

## 工程价值

v238 保护的是交接证据的可追溯性。对于连续多版本推进，这种 guard 能降低“代码还在，但证据没了”的风险。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffArtifactPresenceTests,OpsShardReadinessV1ContractConsumerReadinessHandoffFixtureParityTests,OpsShardReadinessV1ContractConsumerReadinessHandoffRouteInventoryTests" test
```

结果：通过。
