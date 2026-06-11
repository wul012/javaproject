# 165. Java v163：runtime execution packet contribution

## 背景

Node v396 已消费 Java v162 和 mini-kv v153，并明确指出：Java v162 是 Java-side candidate，不是 cross-project runtime approval。下一步需要完整回答六项 runtime execution packet requirement。Java v163 只补 Java 侧 packet contribution，供后续 Node 关联 mini-kv 和 runtime window。

## 改动入口

- `OpsShardReadinessRuntimeExecutionPacketContributionService`：组合 Java v162 candidate，输出 Java v163 packet contribution。
- `OpsShardReadinessRuntimeExecutionPacketContributionResponse`：承载 Java 侧六项 requirement、缺失 cross-project artifacts、fail-closed rules 和 stop conditions。
- `OpsShardReadinessController`：新增 `GET /api/v1/ops/shard-readiness/runtime-execution-packet-contribution`。
- `OpsShardReadinessEvidenceEndpoints`：登记 v163 live endpoint 和 fixture endpoint。
- `java-shard-readiness-runtime-execution-packet-contribution-v163.fixture.json`：提供静态 fixture。

## 核心输出

```text
version=Java v163
sourceRuntimeArtifactCandidateVersion=Java v162
lastClarifiedByNodeVersion=Node v396
nextNodeConsumerHint=Node v397
javaPacketContributionPresent=true
javaPacketContributionComplete=true
crossProjectRuntimeExecutionPacketPresent=false
crossProjectRuntimeExecutionPacketExecutable=false
readyForRuntimeExecutionPacket=false
readyForRuntimeLiveReadGate=false
executionAllowed=false
startsJavaService=false
startsMiniKvService=false
status=passed
```

## 六项 requirement

- operator approval record: Java 侧 record 存在，但仍需跨项目关联签核。
- concrete loopback ports: Java `8080` 存在，mini-kv 仍缺。
- GET-only smoke command: Java 侧 GET-only commands 存在。
- cleanup proof: Java 侧 cleanup proof reference 存在，未执行 cleanup。
- service owner confirmation: Java 侧 owner 已确认。
- process cleanup rules: Java 侧 stop-only-owned-process rules 存在。

## 测试

- `OpsShardReadinessRuntimeExecutionPacketContributionServiceTests`：验证 Java v163 contribution 内容。
- `OpsShardReadinessRuntimeExecutionPacketContributionIntegrationTests`：验证 live endpoint 和 fixture。
- `OpsShardReadinessEvidenceEndpointsTests`：验证 endpoint helper 顺序。
- `OpsEvidenceServiceTests`：验证 ops evidence 登记 v163 endpoint 和 fixture。

定向测试命令：

```text
mvn -q "-Dtest=OpsShardReadinessRuntimeExecutionPacketContributionServiceTests,OpsShardReadinessRuntimeExecutionPacketContributionIntegrationTests,OpsShardReadinessRuntimeExecutionArtifactCandidateServiceTests,OpsShardReadinessRuntimeExecutionArtifactCandidateIntegrationTests,OpsShardReadinessEvidenceEndpointsTests,OpsEvidenceServiceTests" test
```

定向测试和全量 `mvn -q test` 均已通过；测试期间仍会出现既有 Testcontainers Docker 探测日志和 Mockito/JDK 动态 agent 警告，但 Maven 退出码为 0。
