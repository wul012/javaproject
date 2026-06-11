> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# version 80 release approval managed audit adapter implementation guard receipt

## 1. 版本目标

Java v80 给 release approval rehearsal 增加 `managedAuditAdapterImplementationGuardReceipt`。它是 Node v220 disabled adapter shell 之后、Node v221 local file/sqlite candidate dry-run 之前的 Java 侧只读 guard。

这版不做真实 adapter wiring。Node v220 已经有 `ManagedAuditAdapter` interface 和 `DisabledManagedAuditAdapter`，但 Java 仍要在自己的 evidence response 里确认：adapter shell 出现不等于 Java 可以创建 approval decision、写 ledger、写 managed audit store、执行 SQL 或打开生产审计窗口。

## 2. Response record

`ReleaseApprovalRehearsalResponse` 顶层新增：

```java
RehearsalManagedAuditAdapterImplementationGuardReceipt managedAuditAdapterImplementationGuardReceipt
```

它记录三类信息：

```text
sourceQualitySplit*
 -> 来源是 Java v79 quality split receipt，source schema 固定为 v13

consumedByNodeDisabledShell*
 -> 消费 Node v220 disabled shell 的 profile、endpoint 和 disabled-shell-ready 状态

nextNodeCandidate*
 -> 下一跳是 Node v221 local adapter candidate dry-run
```

关键 no-write 字段全部保持关闭：

```text
nodeV220AppendWritten=false
nodeV220QueryReturnedRecords=false
nodeV220ExternalManagedAuditAccessed=false
nodeV220LocalDryRunWritePerformed=false
javaApprovalDecisionCreated=false
javaApprovalLedgerWritten=false
javaApprovalRecordPersisted=false
javaManagedAuditStoreWritten=false
javaSqlExecuted=false
javaDeploymentTriggered=false
javaRollbackTriggered=false
javaRestoreExecuted=false
```

## 3. Service 生成顺序

`OpsEvidenceService.releaseApprovalRehearsal(...)` 的顺序变成：

```java
approvalHandoffVerificationMarker
 -> managedAuditAdapterBoundaryReceipt
 -> managedAuditProductionAdapterPrerequisiteReceipt
 -> opsEvidenceServiceQualitySplitReceipt
 -> managedAuditAdapterImplementationGuardReceipt
 -> failureTaxonomy
 -> verificationHint
```

v80 guard 的 ready 条件依赖 v79 receipt ready，并再次确认 Java 和 Node v220 shell 都没有打开写入或真实外部连接：

```java
readyForNodeV221LocalAdapterCandidateDryRun =
    sourceReceiptAccepted
        && nodeV220SelectedAdapterDisabled
        && nodeV220LocalDryRunOnlyDeclared
        && !nodeV220AppendWritten
        && !nodeV220ExternalManagedAuditAccessed
        && !javaApprovalLedgerWritten
        && !javaSqlExecuted;
```

默认缺少完整 header 时，v79 receipt 不 ready，因此 v80 guard 也不 ready，并返回：

```text
NODE_V221_SOURCE_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_RECEIPT_NOT_READY
```

## 4. Guard digest

`guardDigest` 使用项目已有 `digest(List<String>)` helper 生成，输入包括：

```text
receiptVersion
sourceQualitySplitReceiptVersion
sourceQualitySplitSchemaVersion
consumedByNodeDisabledShellVersion
consumedByNodeDisabledShellProfile
consumedByNodeDisabledShellState
nodeV220SelectedAdapterDisabled
nodeV220LocalDryRunOnlyDeclared
nodeV220AppendWritten
nodeV220ExternalManagedAuditAccessed
nodeV220LocalDryRunWritePerformed
javaApprovalLedgerWritten
javaManagedAuditStoreWritten
javaSqlExecuted
readyForNodeV221LocalAdapterCandidateDryRun
```

这个 digest 给 Node v221 做消费前校验；它不是生产审计记录 digest，也不证明真实外部审计可用。

## 5. VerificationHint 更新

schema 升级为：

```text
java-release-approval-rehearsal-response-schema.v14
```

`verificationHint` 同步纳入 v80：

```text
schemaFields
 -> managedAuditAdapterImplementationGuardReceipt

warningDigestInputs
 -> managedAuditAdapterImplementationGuardReceiptWarnings
 -> implementationGuardDigest
 -> implementationGuardJavaApprovalLedgerWritten
 -> implementationGuardJavaManagedAuditStoreWritten
 -> implementationGuardJavaSqlExecuted
 -> implementationGuardNodeV220AppendWritten
 -> implementationGuardNodeV220ExternalManagedAuditAccessed
 -> implementationGuardNodeV220LocalDryRunWritePerformed

proofClaims
 -> managedAuditAdapterImplementationGuardReceipt.nodeV220SelectedAdapterDisabled=true
 -> managedAuditAdapterImplementationGuardReceipt.nodeV220AppendWritten=false
 -> managedAuditAdapterImplementationGuardReceipt.javaApprovalLedgerWritten=false
 -> managedAuditAdapterImplementationGuardReceipt.javaSqlExecuted=false
```

## 6. 测试覆盖

更新了：

- `OpsEvidenceServiceTests`
- `OpsOverviewIntegrationTests#releaseApprovalRehearsalReturnsReadOnlyLiveAggregation`

覆盖点：

- 默认缺 header 时，v80 guard 带 `NODE_V221_SOURCE_OPS_EVIDENCE_SERVICE_QUALITY_SPLIT_RECEIPT_NOT_READY`
- 完整 header 时，`readyForNodeV221LocalAdapterCandidateDryRun=true`
- `guardDigest` 稳定，重复相同 header 得到相同 digest 和 warning digest
- Node v220 disabled shell 字段保持 disabled/no-write/no-external-access
- Java approval decision、ledger、managed audit store、SQL、deployment、rollback、restore 全部保持 `false`
- `verificationHint.responseSchemaVersion` 升级到 schema v14

## 7. 边界

本版没有：

- 创建 approval decision
- 写 approval ledger
- 持久化 approval record
- 写 managed audit store
- 执行 SQL
- 部署、回滚或 restore
- 连接真实 managed audit
- 从 Java 选择 Node local-dry-run adapter
- 改变 release approval rehearsal API path
