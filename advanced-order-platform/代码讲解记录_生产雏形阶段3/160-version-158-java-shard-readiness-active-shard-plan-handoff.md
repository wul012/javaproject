# 160. Java v158：active shard plan boundary handoff

## 背景

Node v381 已验证 Node v380 对 Java v157 handoff 与 mini-kv v147 activePrototypePlan frozen baseline 的归档回放。Java v158 不做 live read，也不启用 active shard prototype，而是给 Java 侧补一份只读边界 handoff。

## 改动入口

- `OpsShardReadinessActiveShardPlanHandoffService`：组合 Java v157 handoff，输出 v158 active shard plan boundary handoff。
- `OpsShardReadinessActiveShardPlanHandoffResponse`：承载 Java role、prototype authority、boundary rules、stop conditions。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/active-shard-plan-handoff`。
- `OpsShardReadinessEvidenceEndpoints`：登记 v158 live endpoint 和 fixture endpoint。
- `java-shard-readiness-active-shard-plan-handoff-v158.fixture.json`：提供静态 fixture。

## 核心输出

```text
version=Java v158
sourceHandoffVersion=Java v157
lastConsumedByNodeVersion=Node v380
nodeArchiveVerificationVersion=Node v381
activeShardPrototypeEnabled=false
liveReadAllowed=false
status=passed
```

## Java 边界

- Java 只作为 read-only contract echo / handoff producer。
- active shard prototype authority 留在 mini-kv 独立计划。
- 不启用 Java shard router 或 write routing。
- 不从 Java handoff 启动或停止 Node / mini-kv。
- live-read gate 需要独立服务启停计划。

## 测试

- `OpsShardReadinessActiveShardPlanHandoffServiceTests`：验证 v158 handoff 内容。
- `OpsShardReadinessActiveShardPlanHandoffIntegrationTests`：验证 live endpoint 和 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint helper 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 登记 v158 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessActiveShardPlanHandoffServiceTests,OpsShardReadinessActiveShardPlanHandoffIntegrationTests,OpsShardReadinessEvidenceHandoffServiceTests,OpsShardReadinessEvidenceHandoffIntegrationTests,OpsShardReadinessEvidenceEndpointsTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志和 Mockito/JDK 动态 agent 警告，但 Maven 退出码为 0。
