> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 164. Java v162：runtime execution artifact candidate

## 背景

Node v395 已完成 v394 runtime execution artifact intake preflight 的归档验证。v394 明确记录没有 Java v162 或 mini-kv v153 runtime artifact candidate，本轮 Java v162 只补 Java 侧 runtime artifact candidate，不打开 runtime execution packet。

## 改动入口

- `OpsShardReadinessRuntimeExecutionArtifactCandidateService`：组合 Java v161 declared lifecycle evidence，输出 Java v162 runtime execution artifact candidate。
- `OpsShardReadinessRuntimeExecutionArtifactCandidateResponse`：承载 Java owner、loopback port、GET smoke、cleanup proof、process cleanup rules 和 stop conditions。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/runtime-execution-artifact-candidate`。
- `OpsShardReadinessEvidenceEndpoints`：登记 v162 live endpoint 和 fixture endpoint。
- `java-shard-readiness-runtime-execution-artifact-candidate-v162.fixture.json`：提供静态 fixture。

## 核心输出

```text
version=Java v162
sourceDeclaredLifecycleVersion=Java v161
lastVerifiedByNodeVersion=Node v395
nextNodeConsumerHint=Node v396
javaRuntimeArtifactCandidatePresent=true
javaRuntimeArtifactsComplete=true
crossProjectRuntimeArtifactsComplete=false
runtimeExecutionPacketPresent=false
runtimeExecutionPacketExecutable=false
readyForRuntimeExecutionPacket=false
readyForRuntimeLiveReadGate=false
executionAllowed=false
startsJavaService=false
startsMiniKvService=false
status=passed
```

## Java 侧 artifact 内容

- owner / startup command owner / cleanup owner 均为 `java-platform-operator`。
- Java loopback port 为 `8080`。
- smoke commands 全部 GET-only。
- cleanup proof 记录 operator 责任和后续归档要求，但本证据不执行 cleanup。
- process cleanup rules 禁止停止 pre-existing Java service。
- 仍缺 mini-kv v153 candidate、cross-project execution packet、Node approved runtime window。

## 测试

- `OpsShardReadinessRuntimeExecutionArtifactCandidateServiceTests`：验证 Java v162 candidate 内容。
- `OpsShardReadinessRuntimeExecutionArtifactCandidateIntegrationTests`：验证 live endpoint 和 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint helper 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 登记 v162 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessRuntimeExecutionArtifactCandidateServiceTests,OpsShardReadinessRuntimeExecutionArtifactCandidateIntegrationTests,OpsShardReadinessDeclaredOperatorLifecycleServiceTests,OpsShardReadinessDeclaredOperatorLifecycleIntegrationTests,OpsShardReadinessEvidenceEndpointsTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志和 Mockito/JDK 动态 agent 警告，但 Maven 退出码为 0。
