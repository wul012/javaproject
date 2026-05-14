# 61-version-57-rollback-approval-handoff-sample

## 本版主题

v57 增加 Java rollback approval handoff sample。它不是回退执行器，而是把 Java 回退窗口前必须人工确认的 artifact version、runtime config、secret source、database migration direction、release bundle 和 rollback evidence 固化成只读交接样本。

## 动态证据入口

`OpsEvidenceResponse` 在 v56 的 `releaseBundle` 后新增 `rollbackApprovalHandoff`：

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
        RollbackApprovalHandoff rollbackApprovalHandoff,
        FailedEventReplay failedEventReplay,
        Outbox outbox,
        ApprovalExecution approvalExecution,
        List<String> blockers,
        List<String> warnings,
        List<String> evidenceEndpoints
) {
```

这说明 handoff 属于 ops evidence 的一部分，和订单核心业务模型分离。它只给控制面和人工窗口 checklist 提供证据，不授予执行权限。

## RollbackApprovalHandoff record

```java
public record RollbackApprovalHandoff(
        String handoffVersion,
        String handoffEndpoint,
        String approvalMode,
        List<String> requiredConfirmationFields,
        List<String> handoffArtifacts,
        boolean nodeMayConsume,
        boolean nodeMayTriggerRollback,
        boolean rollbackSqlExecutionAllowed,
        boolean requiresProductionDatabase,
        boolean requiresProductionSecrets,
        boolean changesOrderTransactionSemantics
) {
}
```

字段里最关键的是三类边界：

- `approvalMode=OPERATOR_CONFIRMATION_REQUIRED`：回退窗口必须由人工确认。
- `nodeMayTriggerRollback=false`：Node 可以展示或汇总 handoff，但不能触发 Java 回退。
- `rollbackSqlExecutionAllowed=false`：handoff 样本不能执行数据库回退 SQL。

## 服务层组装

`OpsEvidenceService.evidence()` 把 `rollbackApprovalHandoff()` 加入响应：

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
        rollbackApprovalHandoff(),
        failedEventReplay(failedEventSummary),
        outbox(pendingOutboxEvents, outboxBlockers),
        approvalExecution(executionBlockers),
        blockers,
        warnings(failedEventSummary, pendingOutboxEvents),
        evidenceEndpoints()
);
```

这一段继续保持 `readOnly=true` 和 `executionAllowed=false`。新增 handoff 没有改变订单、支付、库存、Outbox 或失败事件 replay 的执行路径。

## handoff 内容

```java
private OpsEvidenceResponse.RollbackApprovalHandoff rollbackApprovalHandoff() {
    return new OpsEvidenceResponse.RollbackApprovalHandoff(
            ROLLBACK_APPROVAL_HANDOFF_VERSION,
            ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
            "OPERATOR_CONFIRMATION_REQUIRED",
            List.of(
                    "artifact-version-target",
                    "runtime-config-profile",
                    "configuration-secret-source",
                    "database-migration-direction",
                    "release-bundle-manifest",
                    "deployment-rollback-evidence"
            ),
            List.of(
                    RELEASE_BUNDLE_MANIFEST_ENDPOINT,
                    DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
                    RELEASE_VERIFICATION_MANIFEST_ENDPOINT
            ),
            true,
            false,
            false,
            false,
            false,
            false
    );
}
```

这组字段把人工确认拆得比较细：包版本、运行配置、密钥来源、数据库迁移方向、发布 bundle 和回退证据都必须明确。Node 后续可以把这些字段渲染成 checklist，但不能越过 operator 去执行回退。

## 静态契约样本

新增文件：

```text
src/main/resources/static/contracts/rollback-approval-handoff.sample.json
```

它包含四组信息：

- `requiredConfirmationFields`：人工确认字段列表，每个字段说明是否必填、Node 是否可推断或读取。
- `handoffArtifacts`：引用 release bundle manifest、deployment rollback evidence 和 release verification manifest。
- `nodeConsumption`：说明 Node 可以消费和渲染 checklist，但不能触发 rollback、执行 SQL 或修改运行配置。
- `boundaries` / `forbiddenOperations`：固定生产库、生产密钥、订单交易语义和 mini-kv 连接边界。

## 证据链联动

`staticContractEndpoints()` 把 handoff 加入所有静态契约清单：

```java
private List<String> staticContractEndpoints() {
    return List.of(
            "/contracts/ops-read-only-evidence.sample.json",
            "/contracts/ops-evidence-field-guide.sample.json",
            "/contracts/order-idempotency-boundary.sample.json",
            "/contracts/order-idempotency-store-abstraction.sample.json",
            RELEASE_VERIFICATION_MANIFEST_ENDPOINT,
            DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
            RELEASE_BUNDLE_MANIFEST_ENDPOINT,
            ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
    );
}
```

因此 `/api/v1/ops/evidence`、release verification manifest、release bundle manifest 和 field guide 都能指向同一个 handoff 样本，后续 Node v166 消费时不需要猜路径。

## 测试覆盖

`OpsEvidenceServiceTests` 验证动态 `rollbackApprovalHandoff` 的版本、端点、人工确认字段、handoff artifacts 和禁止执行边界。

`OpsOverviewIntegrationTests` 新增静态端点测试：

```java
mockMvc.perform(get("/contracts/rollback-approval-handoff.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.handoffVersion").value("java-rollback-approval-handoff.v1"))
        .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
        .andExpect(jsonPath("$.boundaries.rollbackSqlExecutionAllowed").value(false));
```

同时动态 `/api/v1/ops/evidence`、只读样本、字段说明、deployment rollback evidence、release bundle manifest 和 release verification manifest 都同步断言了新端点。

## 本版成熟度变化

v57 把 Java 的回退准备从“知道有哪些回退边界”推进到“知道回退窗口前人工必须确认哪些事项”。项目仍然不执行真实回退，但已经能给后续跨项目 rollback window readiness checklist 提供稳定 Java 输入。

一句话总结：v57 给 Java 回退窗口补上人工审批交接样本，让控制面能读懂确认项，但不能替人执行回退。
