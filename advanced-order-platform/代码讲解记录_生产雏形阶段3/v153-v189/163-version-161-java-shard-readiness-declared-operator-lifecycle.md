> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 163. Java v161：declared operator lifecycle evidence

## 背景

Node v387 已验证 Node v386 的 Java / mini-kv operator service lifecycle evidence intake 归档。结论是 Java v160 已提供 operator-owned placeholder evidence，但 mini-kv v151 仍是 template-only。Node v388 只能在看到 declared operator evidence 后继续消费，否则暂停。Java v161 因此在 Java 侧补齐 declared lifecycle evidence。

## 改动入口

- `OpsShardReadinessDeclaredOperatorLifecycleService`：组合 Java v160 lifecycle evidence，输出 v161 declared operator lifecycle evidence。
- `OpsShardReadinessDeclaredOperatorLifecycleResponse`：承载 owner、start command、port、GET smoke、fail-closed、cleanup 和 runtime gate prerequisites。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/declared-operator-lifecycle`。
- `OpsShardReadinessEvidenceEndpoints`：登记 v161 live endpoint 和 fixture endpoint。
- `java-shard-readiness-declared-operator-lifecycle-v161.fixture.json`：提供静态 fixture。

## 核心输出

```text
version=Java v161
sourceLifecycleEvidenceVersion=Java v160
lastVerifiedByNodeVersion=Node v387
nextNodeConsumerHint=Node v388
operatorLifecycleDeclared=true
startupCommandDeclared=true
portDeclared=true
getOnlySmokeDeclared=true
cleanupDeclared=true
failClosedDeclared=true
runtimeProbeAllowed=false
nodeMayStartService=false
nodeMayStopService=false
status=passed
```

## 生命周期声明

- Java service owner、start owner、stop owner 均为 `java-platform-operator`。
- 启动命令引用为 `mvn spring-boot:run -Dspring-boot.run.profiles=local`，但 Node 不能从本证据执行。
- 端口声明为 `8080`，base URL 只暴露 handle：`java-local-readonly-base-url`。
- smoke targets 全部 GET-only。
- runtime gate 仍需要 mini-kv declared lifecycle evidence 和单独批准。

## 测试

- `OpsShardReadinessDeclaredOperatorLifecycleServiceTests`：验证 v161 lifecycle evidence 内容。
- `OpsShardReadinessDeclaredOperatorLifecycleIntegrationTests`：验证 live endpoint 和 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint helper 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 登记 v161 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessDeclaredOperatorLifecycleServiceTests,OpsShardReadinessDeclaredOperatorLifecycleIntegrationTests,OpsShardReadinessOperatorServiceLifecycleServiceTests,OpsShardReadinessOperatorServiceLifecycleIntegrationTests,OpsShardReadinessEvidenceEndpointsTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志和 Mockito/JDK 动态 agent 警告，但 Maven 退出码为 0。
