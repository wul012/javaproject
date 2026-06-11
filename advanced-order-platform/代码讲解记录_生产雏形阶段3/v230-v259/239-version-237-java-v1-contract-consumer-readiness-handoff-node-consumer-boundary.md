> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 239. Java v237 v1 contract consumer readiness handoff node consumer boundary

v237 是 Node consumer boundary guard。它把前面一直强调的“Node 不应卡住 Java / mini-kv，也不能提前获得执行能力”写成测试。

## 背景

readiness handoff 是给下游消费者看的，而当前协作规则要求 Node 更多做消费和门禁，不要自动启动或停止 Java / mini-kv。

因此 v237 验证的不是 endpoint 是否存在，而是 consumer 读到 handoff 后，仍然没有任何操作权限。

## 测试内容

新增 `OpsShardReadinessV1ContractConsumerReadinessHandoffNodeConsumerBoundaryTests`：

- checklist、digest、handoff 三层都 `nodeMayStartOrStopJavaOrMiniKv=false`；
- handoff 的 `startsJavaService=false`；
- handoff 的 `startsMiniKvService=false`；
- handoff 不允许 write routing；
- handoff 不允许 active shard router；
- handoff 不允许 credential value read；
- handoff 不允许 raw endpoint parse；
- handoff 不允许 managed audit connection；
- handoff 不允许 deployment/rollback；
- handoff checks 和 blocked operations 中都能看到 Node no-start/no-stop 规则。

## 工程价值

这个 guard 把跨仓协作边界固化在 Java 侧。即使未来 Node 继续消费这些 evidence，也不能把消费动作误解成执行授权。

## 测试证据

验证命令：

```powershell
mvn -q "-Dtest=OpsShardReadinessV1ContractConsumerReadinessHandoffNodeConsumerBoundaryTests,OpsShardReadinessV1ContractConsumerReadinessHandoffBoundaryMatrixTests,OpsShardReadinessV1ContractConsumerReadinessHandoffIntegrityTests" test
```

结果：通过。
