# 161. Java v159：live-read gate lifecycle plan

## 背景

Node v383 已验证 Node v382 对 Java v158 与 mini-kv v149 boundary handoff 的归档回放。Node 下一步如果要进入 live-read gate，需要先有服务启动、端口、owner、smoke target、fail-closed 与 cleanup 责任计划。Java v159 在 Java 侧补一份只读生命周期计划。

## 改动入口

- `OpsShardReadinessLiveReadGatePlanService`：组合 Java v158 boundary handoff，输出 v159 lifecycle plan。
- `OpsShardReadinessLiveReadGatePlanResponse`：承载 owner 字段、lifecycle plan、smoke targets、fail-closed rules、cleanup responsibilities。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/live-read-gate-plan`。
- `OpsShardReadinessEvidenceEndpoints`：登记 v159 live endpoint 和 fixture endpoint。
- `java-shard-readiness-live-read-gate-plan-v159.fixture.json`：提供静态 fixture。

## 核心输出

```text
version=Java v159
sourceBoundaryHandoffVersion=Java v158
lastVerifiedByNodeVersion=Node v383
nextNodeConsumerHint=Node v384
liveReadGateAllowed=false
serviceStartAllowedByNode=false
serviceStopAllowedByNode=false
status=passed
```

## 生命周期边界

- Node 不能从本计划启动 Java。
- Java operator 在 live-read window 前启动服务。
- Java port/baseUrl 必须由 operator 明确声明。
- Node 只执行 GET smoke targets。
- smoke 失败、owner 缺失、cleanup 缺失都 fail closed。

## 测试

- `OpsShardReadinessLiveReadGatePlanServiceTests`：验证 v159 plan 内容。
- `OpsShardReadinessLiveReadGatePlanIntegrationTests`：验证 live endpoint 和 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint helper 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 登记 v159 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessLiveReadGatePlanServiceTests,OpsShardReadinessLiveReadGatePlanIntegrationTests,OpsShardReadinessActiveShardPlanHandoffServiceTests,OpsShardReadinessActiveShardPlanHandoffIntegrationTests,OpsShardReadinessEvidenceEndpointsTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志和 Mockito/JDK 动态 agent 警告，但 Maven 退出码为 0。
