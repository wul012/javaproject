# 60-version-56-release-bundle-manifest

## 本版主题

v56 增加 Java release bundle manifest。它把 jar、静态 contracts、release verification manifest、deployment rollback evidence 和运行归档要求收成一份只读 bundle，给后续 Node v164 cross-project release bundle gate 消费。

## 动态证据入口

`OpsEvidenceResponse` 在 v55 的 `deploymentRollback` 后新增 `releaseBundle`：

```java
public record OpsEvidenceResponse(
        Instant sampledAt,
        String evidenceVersion,
        Service service,
        HealthProbe healthProbe,
        boolean readOnly,
        boolean executionAllowed,
        ReadOnlyWindow readOnlyWindow,
        OrderIdempotency orderIdempotency,
        ReleaseVerification releaseVerification,
        DeploymentRollback deploymentRollback,
        ReleaseBundle releaseBundle,
        FailedEventReplay failedEventReplay,
        Outbox outbox,
        ApprovalExecution approvalExecution,
        List<String> blockers,
        List<String> warnings,
        List<String> evidenceEndpoints
) {
```

这表示 release bundle 属于运维证据，不属于订单业务模型。它和发布验证、回退证据一起构成 Java 的交付证据上游。

## ReleaseBundle record

```java
public record ReleaseBundle(
        String manifestVersion,
        String manifestEndpoint,
        String bundleMode,
        String artifact,
        List<String> contractEndpoints,
        List<String> requiredEvidence,
        boolean nodeMayConsume,
        boolean nodeMayExecuteBuild,
        boolean nodeMayTriggerRollback,
        boolean requiresProductionDatabase,
        boolean changesOrderTransactionSemantics
) {
}
```

字段含义：

- `manifestVersion` 和 `manifestEndpoint` 固定静态契约版本与入口。
- `artifact` 指向 Java 打包产物。
- `contractEndpoints` 收敛所有随包静态契约。
- `requiredEvidence` 复用发布验证清单中的 focused test、非 Docker 回归、打包、HTTP smoke 和 JSON 校验。
- `nodeMayExecuteBuild=false`、`nodeMayTriggerRollback=false` 是最关键边界。

## 服务层组装

`OpsEvidenceService.evidence()` 把 `releaseBundle()` 加入响应：

```java
return new OpsEvidenceResponse(
        sampledAt,
        EVIDENCE_VERSION,
        service(sampledAt),
        healthProbe(false),
        true,
        false,
        readOnlyWindow(true),
        orderIdempotency(),
        releaseVerification(),
        deploymentRollback(),
        releaseBundle(),
        failedEventReplay(failedEventSummary),
        outbox(pendingOutboxEvents, outboxBlockers),
        approvalExecution(executionBlockers),
        blockers,
        warnings(failedEventSummary, pendingOutboxEvents),
        evidenceEndpoints()
);
```

这里仍然保持 `readOnly=true` 和 `executionAllowed=false`，bundle 只是证据聚合。

## bundle 内容

```java
private OpsEvidenceResponse.ReleaseBundle releaseBundle() {
    return new OpsEvidenceResponse.ReleaseBundle(
            RELEASE_BUNDLE_MANIFEST_VERSION,
            RELEASE_BUNDLE_MANIFEST_ENDPOINT,
            "READ_ONLY_RELEASE_BUNDLE",
            "target/advanced-order-platform-0.1.0-SNAPSHOT.jar",
            staticContractEndpoints(),
            List.of(
                    "focused-maven-tests",
                    "non-docker-regression-tests",
                    "maven-package",
                    "http-smoke",
                    "static-contract-json-validation"
            ),
            true,
            false,
            false,
            false,
            false
    );
}
```

这段代码把 Node 的角色压得很清楚：可以消费 `nodeMayConsume=true`，但不能执行 Maven、不能触发回退、不能要求生产库，也不改变订单交易语义。

## 静态契约样本

新增文件：

```text
src/main/resources/static/contracts/release-bundle-manifest.sample.json
```

它包含四组信息：

- `releaseSubject`：项目、构建工具、Java 版本、jar artifact。
- `bundleInputs`：引用 release verification manifest、deployment rollback evidence、ops evidence 和 `c/<version>` 归档。
- `verificationEvidence`：列出本地操作员需要归档的测试、打包和 smoke 证据。
- `nodeConsumption` / `boundaries`：说明 Node 只能读，不能构建、写入或回退。

## 发布验证清单联动

`release-verification-manifest.sample.json` 的 `staticContracts` 增加 release bundle manifest：

```json
{
  "endpoint": "/contracts/release-bundle-manifest.sample.json",
  "source": "src/main/resources/static/contracts/release-bundle-manifest.sample.json",
  "versionField": "manifestVersion",
  "expectedVersion": "java-release-bundle-manifest.v1"
}
```

因此以后发布验证会同时检查 release bundle manifest 自身是否能解析、版本是否正确。

## 测试覆盖

`OpsEvidenceServiceTests` 验证动态 `releaseBundle` 的版本、artifact、contract endpoints、required evidence 和 Node 禁止执行边界。

`OpsOverviewIntegrationTests` 新增静态端点测试：

```java
mockMvc.perform(get("/contracts/release-bundle-manifest.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.manifestVersion").value("java-release-bundle-manifest.v1"))
        .andExpect(jsonPath("$.nodeConsumption.nodeMayExecuteMaven").value(false))
        .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false));
```

同时动态 `/api/v1/ops/evidence`、只读样本、字段说明和 release verification manifest 都同步断言了新端点。

## 本版成熟度变化

v56 把 Java 的交付证据从“单份发布验证清单”和“回退证据样本”推进为“可被跨项目 gate 消费的发布包 bundle”。这让 Java 项目更接近生产交付流程，但仍严格停留在只读证据层，不进入真实发布或回退执行。

一句话总结：v56 把 Java 的发布交付物、验证结果和回退边界打包成一份 Node 可读但不可执行的 release bundle manifest。
