# version 78 release approval managed audit production adapter prerequisite receipt

## 1. 版本目标

Java v78 在 release approval rehearsal 里继续只读扩展 managed-audit 证据链，新增 `managedAuditProductionAdapterPrerequisiteReceipt`。它不是生产权限，不是写入能力，也不是执行入口，只是把 Node v216 archive verification 之后，Node v217 production-hardening readiness gate 之前必须公开的前置条件和禁写边界钉死。

```text
Java v77 managedAuditAdapterBoundaryReceipt
 -> Node v216 managed-audit dry-run adapter archive verification
 -> Java v78 managedAuditProductionAdapterPrerequisiteReceipt
 -> Node v217 production-hardening readiness gate
```

## 2. Response record 新增字段

`ReleaseApprovalRehearsalResponse` 新增：

```java
RehearsalManagedAuditProductionAdapterPrerequisiteReceipt managedAuditProductionAdapterPrerequisiteReceipt
```

字段分成几组：

```text
sourceManagedAuditAdapterBoundary*
 -> 证明它消费的是 v77 boundary receipt，而不是更早的 marker

consumedByNodeArchiveVerification*
 -> 固定 Node v216 dry-run adapter archive verification 的 profile/state/endpoint

nextNodeGate*
 -> 指向 Node v217 production-hardening readiness gate

nodeV217* / java*
 -> 全部真实生产动作继续保持 false
```

## 3. 生成逻辑

实现落在 `OpsEvidenceService.rehearsalManagedAuditProductionAdapterPrerequisiteReceipt(...)`。

核心判断是先检查 v77 boundary receipt 是否真的 ready，然后才给 v78 receipt 放行：

```java
sourceReceiptAccepted =
    boundaryReceiptVersion == java-release-approval-rehearsal-managed-audit-adapter-boundary-receipt.v1
    && readyForNodeV215DryRunAdapterCandidate
    && !nodeV215MayConnectManagedAudit
    && !nodeV215MayCreateApprovalDecision
    && !nodeV215MayWriteApprovalLedger
    && !nodeV215MayPersistApprovalRecord
    && !nodeV215MayExecuteSql
    && !nodeV215MayTriggerDeployment
    && !nodeV215MayTriggerRollback
    && !nodeV215MayExecuteRestore
    && !javaApprovalDecisionCreated
    && !javaApprovalLedgerWritten
    && !javaApprovalRecordPersisted
    && !javaManagedAuditWriteExecuted
    && !nodeMayTreatAsProductionAuditRecord;
```

如果 source receipt 不满足，就返回：

```text
NODE_V217_SOURCE_MANAGED_AUDIT_ADAPTER_BOUNDARY_RECEIPT_NOT_READY
```

这一步只是在生产硬化前把前置条件说完整，不会打开任何真实 managed audit/ledger/SQL/deployment/rollback/restore 能力。

## 4. verificationHint 扩展

`releaseApprovalVerificationHint(...)` 把 v78 receipt 纳入：

```text
schemaFields
 -> managedAuditProductionAdapterPrerequisiteReceipt

warningDigestInputs
 -> managedAuditProductionAdapterPrerequisiteReceiptWarnings
 -> nodeV217MayConnectManagedAudit
 -> nodeV217MayWriteApprovalLedger
 -> nodeV217MayExecuteSql
 -> nodeV217MayTriggerDeployment
 -> nodeV217MayTriggerRollback
 -> nodeV217MayExecuteRestore

proofClaims
 -> managedAuditProductionAdapterPrerequisiteReceipt.javaCreatesApprovalDecision=false
 -> managedAuditProductionAdapterPrerequisiteReceipt.javaWritesApprovalLedger=false
 -> managedAuditProductionAdapterPrerequisiteReceipt.javaPersistsApprovalRecord=false
 -> managedAuditProductionAdapterPrerequisiteReceipt.javaWritesManagedAuditStore=false
 -> managedAuditProductionAdapterPrerequisiteReceipt.javaExecutesSql=false
 -> managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayConnectManagedAudit=false
 -> managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayWriteApprovalLedger=false
 -> managedAuditProductionAdapterPrerequisiteReceipt.nodeV217MayExecuteSql=false
```

这样 Node 在后续 production-hardening gate 里看到的仍然是一个只读、可核验、不可执行的 prerequisite record。

## 5. 测试覆盖

`OpsEvidenceServiceTests` 和 `OpsOverviewIntegrationTests` 都补了 v78 断言：

- 默认缺 header 时，`readyForNodeV217ProductionHardeningReadinessGate=false`
- 完整 header 时，`readyForNodeV217ProductionHardeningReadinessGate=true`
- 但所有真实生产动作字段都仍为 `false`
- `verificationHint.responseSchemaVersion` 已升级为 `java-release-approval-rehearsal-response-schema.v12`

## 6. 验证结果

已跑通过：

```text
mvn -q '-Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests' test
mvn -q '-Dtest=!PostgresMigrationIntegrationTests,!RabbitMqNotificationConsumerIntegrationTests,!RabbitMqNotificationFailureIntegrationTests,!RabbitMqOutboxPublisherIntegrationTests' test
mvn -q -DskipTests package
```

并做了本地 HTTP smoke，确认：

- `health=UP`
- `readOnlyEndpointVersion=v12`
- v78 receipt ready 时仍然全是禁写/禁执行

## 7. 清理

- 已停止 smoke 进程
- 已删除临时 smoke 日志
- 未保留临时目录

