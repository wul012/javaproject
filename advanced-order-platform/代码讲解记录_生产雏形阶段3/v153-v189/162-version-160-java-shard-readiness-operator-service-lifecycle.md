> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 162. Java v160：operator service lifecycle evidence

## 背景

Node v385 已验证 Node v384 对 Java v159 与 mini-kv v150 live-read gate plan 的归档回放。Node 下一步等待 operator-owned service lifecycle evidence。Java v160 在 Java 侧补一份只读 lifecycle evidence，供后续 Node 版本消费，但不打开 runtime probe。

## 改动入口

- `OpsShardReadinessOperatorServiceLifecycleService`：组合 Java v159 gate plan，输出 v160 operator lifecycle evidence。
- `OpsShardReadinessOperatorServiceLifecycleResponse`：承载 operator owner、port/baseUrl template、GET smoke、fail-closed rules 和 cleanup responsibilities。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/operator-service-lifecycle`。
- `OpsShardReadinessEvidenceEndpoints`：登记 v160 live endpoint 和 fixture endpoint。
- `java-shard-readiness-operator-service-lifecycle-v160.fixture.json`：提供静态 fixture。

## 核心输出

```text
version=Java v160
sourceGatePlanVersion=Java v159
lastVerifiedByNodeVersion=Node v385
nextNodeConsumerHint=Node v386
operatorOwned=true
runtimeProbeAllowed=false
nodeMayStartService=false
nodeMayStopService=false
status=passed
```

## 生命周期边界

- Java service owner、start owner、stop owner 属于 operator confirmation。
- Java port/baseUrl 必须在 live-read window 前明确声明。
- Node 不能从本证据启动或停止 Java。
- smoke targets 必须是 GET-only。
- owner、port、cleanup 或 smoke 任一缺失都 fail closed。

## 测试

- `OpsShardReadinessOperatorServiceLifecycleServiceTests`：验证 v160 lifecycle evidence 内容。
- `OpsShardReadinessOperatorServiceLifecycleIntegrationTests`：验证 live endpoint 和 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint helper 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 登记 v160 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessOperatorServiceLifecycleServiceTests,OpsShardReadinessOperatorServiceLifecycleIntegrationTests,OpsShardReadinessLiveReadGatePlanServiceTests,OpsShardReadinessLiveReadGatePlanIntegrationTests,OpsShardReadinessEvidenceEndpointsTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志和 Mockito/JDK 动态 agent 警告，但 Maven 退出码为 0。
