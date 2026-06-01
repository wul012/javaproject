# 231. Java v229 v1 contract consumer readiness handoff route inventory

v229 是 readiness handoff 的 route inventory 版本。v225 已经暴露 endpoint，v226-v228 已经冻结和验证 response 本身；
这一版改为检查“它在系统入口清单里的注册是否完整”。

## 检查点

`OpsShardReadinessV1ContractConsumerReadinessHandoffRouteInventoryTests` 分三组。

第一组检查 endpoint-pair registry：

- `OpsShardReadinessV1ContractEndpointPairs.endpointPairs()` 仍然是 11 个 pair；
- 最后一个 pair 的 live endpoint 是 readiness handoff；
- 最后一个 pair 的 fixture endpoint 是 readiness handoff fixture。

第二组检查 route 和 probe inventory：

- `OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF` 等于 `/v1-contract-consumer-readiness-handoff`；
- service endpoint 等于 `BASE_PATH + route path`；
- live probe endpoints 包含 `GET /api/v1/ops/shard-readiness/v1-contract-consumer-readiness-handoff`；
- fixture probe endpoints 包含 `GET /contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json`。

第三组检查 static fixture：

- classpath 能找到 `static/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json`。

## 为什么这个版本必要

接口类 evidence 很容易出现“实现存在，但清单漏登记”的问题。例如 controller 有 route，但 evidence probe list 没有；
或者 fixture 文件存在，但 endpoint pair registry 没同步。这样的缺口会让下游消费者不知道该 probe 哪些 URL。

v229 把这些入口统一检查，后面如果有人改路由或 fixture 名，测试会在本地直接失败。

## 边界

本版依然是只读清单校验。它没有新增 controller route，没有新增 service 行为，也没有改 v225 response 内容。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffRouteInventoryTests,OpsShardReadinessV1ContractEndpointPairsTests,OpsShardReadinessEvidenceEndpointsTests,OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrationTests" test
```

结果：通过。
