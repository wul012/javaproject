# 59-version-55-deployment-rollback-evidence-sample

## 本版主题

v55 给 Java 订单平台补了一条“部署回退证据”只读链路。它不是执行回退的功能，而是把回退前需要看的边界固定成代码和 JSON 契约，方便 Node 操作台未来只读消费。

## 动态证据入口

`OpsEvidenceResponse` 在顶层新增 `deploymentRollback`：

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
        FailedEventReplay failedEventReplay,
        Outbox outbox,
        ApprovalExecution approvalExecution,
        List<String> blockers,
        List<String> warnings,
        List<String> evidenceEndpoints
) {
```

这个位置很关键：它和 `releaseVerification` 平级，表达“发布验证之后，回退边界也属于运维证据”，但不进入订单交易模型。

## 回退证据字段

```java
public record DeploymentRollback(
        String evidenceVersion,
        String evidenceEndpoint,
        String rollbackMode,
        List<String> rollbackSubjects,
        List<String> requiresOperatorConfirmation,
        boolean packageRollbackSupported,
        boolean configRollbackSupported,
        boolean databaseMigrationRollbackAutomatic,
        boolean contractsRollbackByArtifactVersion,
        boolean nodeMayTriggerRollback,
        boolean requiresProductionDatabase,
        boolean changesOrderTransactionSemantics
) {
}
```

字段分成三类：

- 能力类：`packageRollbackSupported`、`configRollbackSupported`、`contractsRollbackByArtifactVersion`。
- 风险类：`databaseMigrationRollbackAutomatic=false`、`requiresOperatorConfirmation`。
- 边界类：`nodeMayTriggerRollback=false`、`requiresProductionDatabase=false`、`changesOrderTransactionSemantics=false`。

## 服务层组装

`OpsEvidenceService.evidence()` 把新证据挂到响应里：

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
        failedEventReplay(failedEventSummary),
        outbox(pendingOutboxEvents, outboxBlockers),
        approvalExecution(executionBlockers),
        blockers,
        warnings(failedEventSummary, pendingOutboxEvents),
        evidenceEndpoints()
);
```

注意这里仍然是 `readOnly=true`、`executionAllowed=false`。新增字段只是证据，不是动作入口。

## 核心边界实现

```java
private OpsEvidenceResponse.DeploymentRollback deploymentRollback() {
    return new OpsEvidenceResponse.DeploymentRollback(
            DEPLOYMENT_ROLLBACK_EVIDENCE_VERSION,
            DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT,
            "READ_ONLY_BOUNDARY_SAMPLE",
            List.of(
                    "java-package",
                    "runtime-configuration",
                    "database-migrations",
                    "static-contracts"
            ),
            List.of(
                    "artifact-version-target",
                    "configuration-secret-source",
                    "database-migration-direction"
            ),
            true,
            true,
            false,
            true,
            false,
            false,
            false
    );
}
```

这里的几个布尔边界是本版最重要的安全判断：

- 数据库迁移不自动回退。
- Node 不能触发回退。
- 不需要生产数据库。
- 不改变订单交易语义。

## 静态契约样本

新增文件：

```text
src/main/resources/static/contracts/deployment-rollback-evidence.sample.json
```

它拆成四块：

- `packageRollback`：包版本回退需要操作员选定 artifact，并保留 smoke 证据。
- `configurationRollback`：配置回退需要确认 profile 和 secret source。
- `databaseMigrationRollback`：迁移回退不自动执行，不连接生产库。
- `staticContractRollback`：契约跟随 artifact version 回退。

## 发布验证清单联动

`release-verification-manifest.sample.json` 的 `staticContracts` 增加：

```json
{
  "endpoint": "/contracts/deployment-rollback-evidence.sample.json",
  "source": "src/main/resources/static/contracts/deployment-rollback-evidence.sample.json",
  "versionField": "evidenceVersion",
  "expectedVersion": "java-deployment-rollback-evidence.v1"
}
```

这样发布验证不仅检查测试、打包和 HTTP smoke，也检查回退证据契约是否仍然可解析。

## 测试覆盖

`OpsEvidenceServiceTests` 验证 Java 服务层返回的新 record 字段。

`OpsOverviewIntegrationTests` 覆盖四个方向：

- 动态 `/api/v1/ops/evidence` 有 `deploymentRollback`。
- 静态 `ops-read-only-evidence.sample.json` 同步暴露该字段。
- 字段说明样本包含 `deploymentRollback` field group。
- 新的 `deployment-rollback-evidence.sample.json` 能通过 Spring 静态资源访问并表达禁止动作。

## 本版成熟度变化

v55 不增加业务复杂度，而是补上“发布之后如果要回退，证据边界怎么说清楚”的一块。它让项目更像真实生产系统：不只是会发布，也开始能说明回退责任、回退权限和回退风险。

一句话总结：v55 把 Java 从“能验证发布”推进到“能只读说明回退边界”。
