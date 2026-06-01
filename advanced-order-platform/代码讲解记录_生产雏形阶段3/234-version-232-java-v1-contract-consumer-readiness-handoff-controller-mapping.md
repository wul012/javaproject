# 234. Java v232 v1 contract consumer readiness handoff controller mapping

v232 是 controller mapping guard。它验证 readiness handoff 从 service 注册到 HTTP controller 的最后一段连接。

## 为什么需要 controller 层护栏

v229 和 v231 都能说明 endpoint 出现在清单里，但清单正确并不等于 controller 一定正确映射。例如未来重排构造函数参数、
拆 controller 或修改 route annotation，都可能造成 readiness handoff 的真实 HTTP route 漏接。

v232 用反射测试把这些关系锁住。

## 测试点

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffControllerMappingTests`：

- 读取 `consumerReadinessHandoff()` 方法；
- 断言返回类型是 `OpsShardReadinessV1ContractConsumerReadinessHandoffResponse`；
- 断言 `@GetMapping` value 是 `OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF`；
- 断言 controller 构造函数参数中 evidence digest service 后面接 readiness handoff service。

focused test 还带上：

- `OpsShardReadinessV1ContractControllerSplitTests`；
- `OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrationTests`。

这样反射检查和 MockMvc 运行路径互相补位。

## 边界说明

本版不改 controller 行为，只补测试和证据常量。它不会启动实际服务，也不会打开任何写操作。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffControllerMappingTests,OpsShardReadinessV1ContractControllerSplitTests,OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrationTests" test
```

结果：通过。
