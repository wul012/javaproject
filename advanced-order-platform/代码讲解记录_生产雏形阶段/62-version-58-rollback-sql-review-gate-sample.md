# 62-version-58-rollback-sql-review-gate-sample

## 本版主题

v58 增加 Java rollback SQL review gate sample。它不是 SQL 执行入口，而是把回退 SQL 执行前必须确认的 review owner、migration direction、operator approval placeholder 和生产库访问边界固化成只读证据。

## 动态证据入口

`OpsEvidenceResponse` 在 v57 的 `rollbackApprovalHandoff` 后新增 `rollbackSqlReviewGate`：

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
        RollbackSqlReviewGate rollbackSqlReviewGate,
        FailedEventReplay failedEventReplay,
        Outbox outbox,
        ApprovalExecution approvalExecution,
        List<String> blockers,
        List<String> warnings,
        List<String> evidenceEndpoints
) {
```

这表示 SQL review gate 仍属于 ops evidence。它只让控制面知道回退 SQL 审查需要哪些人工确认，不改变订单、库存、支付、Outbox 或 replay 执行路径。

## RollbackSqlReviewGate record

```java
public record RollbackSqlReviewGate(
        String gateVersion,
        String gateEndpoint,
        String gateMode,
        String reviewOwner,
        List<String> requiredReviewFields,
        List<String> migrationDirectionOptions,
        String operatorApprovalPlaceholder,
        boolean nodeMayConsume,
        boolean nodeMayTriggerRollback,
        boolean sqlExecutionAllowed,
        boolean requiresProductionDatabase,
        boolean changesOrderTransactionSemantics
) {
}
```

字段设计重点：

- `reviewOwner` 记录数据库发布或回退 SQL 的人工责任位。
- `migrationDirectionOptions` 把迁移方向限制为 `forward-only`、`rollback-script-reviewed`、`no-database-change`。
- `operatorApprovalPlaceholder` 明确这里不是实际审批记录，只是占位。
- `sqlExecutionAllowed=false` 保证样本不能变成执行入口。

## 服务层组装

`OpsEvidenceService.evidence()` 把 `rollbackSqlReviewGate()` 加入响应：

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
        rollbackSqlReviewGate(),
        failedEventReplay(failedEventSummary),
        outbox(pendingOutboxEvents, outboxBlockers),
        approvalExecution(executionBlockers),
        blockers,
        warnings(failedEventSummary, pendingOutboxEvents),
        evidenceEndpoints()
);
```

这里仍然保持 `readOnly=true` 和 `executionAllowed=false`，新增 gate 不授权任何真实 SQL 或回退动作。

## gate 内容

```java
private OpsEvidenceResponse.RollbackSqlReviewGate rollbackSqlReviewGate() {
    return new OpsEvidenceResponse.RollbackSqlReviewGate(
            ROLLBACK_SQL_REVIEW_GATE_VERSION,
            ROLLBACK_SQL_REVIEW_GATE_ENDPOINT,
            "READ_ONLY_SQL_REVIEW_GATE",
            "database-release-owner",
            List.of(
                    "rollback-sql-review-owner",
                    "migration-direction",
                    "operator-approval-placeholder",
                    "rollback-sql-artifact-reference",
                    "production-database-access-boundary"
            ),
            List.of(
                    "forward-only",
                    "rollback-script-reviewed",
                    "no-database-change"
            ),
            "operator-approval-required-before-any-sql-execution",
            true,
            false,
            false,
            false,
            false
    );
}
```

这段代码把 Node 的角色压在只读消费：`nodeMayConsume=true`，但 `nodeMayTriggerRollback=false`、`sqlExecutionAllowed=false`、`requiresProductionDatabase=false`。

## 静态契约样本

新增文件：

```text
src/main/resources/static/contracts/rollback-sql-review-gate.sample.json
```

它包含四组信息：

- `requiredReviewFields`：SQL review owner、migration direction、operator approval placeholder、SQL artifact reference、生产库访问边界。
- `migrationDirectionOptions`：固定允许值，避免控制面自由编写迁移方向。
- `nodeConsumption`：Node 可以消费和渲染 preflight，但不能触发 rollback 或执行 SQL。
- `boundaries` / `forbiddenOperations`：固定不能连接生产库、不能嵌入带 secret 的 SQL、不能执行回退 SQL。

## 证据链联动

`staticContractEndpoints()` 把 SQL review gate 加入统一静态契约清单：

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
            ROLLBACK_APPROVAL_HANDOFF_ENDPOINT,
            ROLLBACK_SQL_REVIEW_GATE_ENDPOINT
    );
}
```

因此 `/api/v1/ops/evidence`、release verification manifest、release bundle manifest、rollback approval handoff 和 field guide 都能指向同一个 review gate 样本。

## 测试覆盖

`OpsEvidenceServiceTests` 验证动态 `rollbackSqlReviewGate` 的版本、端点、review owner、required fields、migration direction options 和禁止执行边界。

`OpsOverviewIntegrationTests` 新增静态端点测试：

```java
mockMvc.perform(get("/contracts/rollback-sql-review-gate.sample.json"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.gateVersion").value("java-rollback-sql-review-gate.v1"))
        .andExpect(jsonPath("$.nodeConsumption.nodeMayTriggerRollback").value(false))
        .andExpect(jsonPath("$.boundaries.sqlExecutionAllowed").value(false));
```

同时动态 `/api/v1/ops/evidence`、只读样本、字段说明、release bundle、handoff 和 release verification manifest 都同步断言了新端点。

## 本版成熟度变化

v58 把 Java 回退准备从“人工确认事项”推进到“SQL review gate 可验证”。项目仍不执行真实回退，但已经能给后续跨项目 rollback execution preflight contract 提供明确 Java 输入。

一句话总结：v58 给 Java 回退 SQL 补上只读 review gate，让控制面能检查 SQL 审查责任和迁移方向，但不能执行 SQL。
