> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 235. Java v233 v1 contract consumer readiness handoff fixture parity

v233 是 fixture parity guard。它解决的是“静态 fixture 是否真的等于服务返回的 frozen contract”。

## 为什么要做 JSON tree 级别比较

只检查几个关键字段会留下空洞。例如 fixture 里可能多了字段、少了字段，或者列表顺序变化，但关键字段测试仍然通过。

本版使用 `ObjectMapper`：

- 从 classpath 读取 static fixture；
- 把 frozen v225 handoff response 转成 JSON tree；
- 直接比较两棵 JSON tree。

这样 response schema、字段值、数组顺序都会被覆盖。

## 测试点

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffFixtureParityTests`：

- classpath 下能找到 readiness handoff fixture；
- fixture JSON 等于 `valueToTree(v225Handoff())`；
- response 中的 fixture endpoint 等于 service 常量；
- response 中的 evidence path 等于 service 常量。

focused test 还带上 route inventory 和 integration，保证 fixture 文件不仅内容正确，也能经由 HTTP static path 暴露。

## 边界说明

v233 仍然只验证只读合同，不启动外部服务、不解析 raw endpoint、不触发写操作。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffFixtureParityTests,OpsShardReadinessV1ContractConsumerReadinessHandoffRouteInventoryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrationTests" test
```

结果：通过。
