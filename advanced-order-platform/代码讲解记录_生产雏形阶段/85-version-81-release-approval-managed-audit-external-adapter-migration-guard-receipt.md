> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# version 81 release approval managed audit external adapter migration guard receipt

## 1. 版本目标

Java v81 给 release approval rehearsal 增加 `managedAuditExternalAdapterMigrationGuardReceipt`。它是 Node v222 verification report 之后、Node v223 external adapter connection readiness review 之前的 Java 侧只读 guard。

这版不做真实外部 adapter connection。Node v221 已经证明本地 JSONL adapter candidate 的 append/query/digest/cleanup，Node v222 已经只读验证归档；Java v81 的职责是把真实外部 adapter 前仍需 owner approval、schema migration review、credential review 这三类门槛写进响应，并再次确认 Java 不读取 credential、不连接外部 audit、不执行 SQL。

## 2. Response record

`ReleaseApprovalRehearsalResponse` 顶层新增：

```java
RehearsalManagedAuditExternalAdapterMigrationGuardReceipt managedAuditExternalAdapterMigrationGuardReceipt
```

它记录三类信息：

```text
sourceImplementationGuard*
 -> 来源是 Java v80 implementation guard receipt，source schema 固定为 v14

consumedByNodeVerificationReport*
 -> 消费 Node v222 verification report 的 profile、endpoint 和 local-adapter-candidate-verification-ready 状态

nextNodeReview*
 -> 下一跳是 Node v223 external adapter connection readiness review
```

关键 no-credential / no-connection / no-SQL 字段全部保持关闭：

```text
nodeV222SourceEndpointRerunPerformed=false
nodeV222AdditionalLocalDryRunWritePerformed=false
nodeV222ConnectsManagedAudit=false
nodeV222ReadyForProductionAudit=false
credentialValueReadByJava=false
credentialValueStoredByJava=false
externalManagedAuditConnectionOpened=false
externalManagedAuditSchemaMigrated=false
javaApprovalLedgerWritten=false
javaManagedAuditStoreWritten=false
javaSqlExecuted=false
```

## 3. Builder 拆分

v81 没有继续把新 receipt builder 塞进 `OpsEvidenceService`，而是新增：

```java
ReleaseApprovalManagedAuditExternalAdapterMigrationGuardReceiptBuilder
```

主服务只负责接线：

```java
managedAuditExternalAdapterMigrationGuardReceipt =
    new ReleaseApprovalManagedAuditExternalAdapterMigrationGuardReceiptBuilder()
        .build(managedAuditAdapterImplementationGuardReceipt);
```

这样避免 `OpsEvidenceService` 在每个 guard 版本继续膨胀，同时保留 response 构造顺序：

```java
opsEvidenceServiceQualitySplitReceipt
 -> managedAuditAdapterImplementationGuardReceipt
 -> managedAuditExternalAdapterMigrationGuardReceipt
 -> failureTaxonomy
 -> verificationHint
```

## 4. Ready 条件

v81 guard 的 ready 条件依赖 v80 receipt ready，并再次确认 Node v222 verification report 是只读核对，不重跑 v221 endpoint，不新增本地 dry-run write，也不连接真实 managed audit：

```java
readyForNodeV223ExternalAdapterConnectionReadinessReview =
    sourceReceiptAccepted
        && nodeV222VerificationReportReady
        && nodeV222ReadOnlyReport
        && !nodeV222SourceEndpointRerunPerformed
        && !nodeV222AdditionalLocalDryRunWritePerformed
        && !nodeV222ConnectsManagedAudit
        && ownerApprovalRequiredBeforeConnection
        && schemaMigrationReviewRequired
        && credentialReviewRequired
        && !credentialValueReadByJava
        && !externalManagedAuditConnectionOpened
        && !externalManagedAuditSchemaMigrated
        && !javaManagedAuditStoreWritten
        && !javaSqlExecuted;
```

默认缺少完整 header 时，v80 receipt 不 ready，因此 v81 guard 也不 ready，并返回：

```text
NODE_V223_SOURCE_IMPLEMENTATION_GUARD_RECEIPT_NOT_READY
```

## 5. Guard digest

`guardDigest` 使用 builder 内部的 SHA-256 helper 生成，输入包括：

```text
receiptVersion
sourceImplementationGuardReceiptVersion
sourceImplementationGuardSchemaVersion
consumedByNodeVerificationReportVersion
consumedByNodeVerificationReportProfile
consumedByNodeVerificationReportState
nodeV222ReadOnlyReport
nodeV222SourceEndpointRerunPerformed
nodeV222AdditionalLocalDryRunWritePerformed
nodeV222ConnectsManagedAudit
ownerApprovalRequiredBeforeConnection
schemaMigrationReviewRequired
credentialReviewRequired
credentialValueReadByJava
externalManagedAuditConnectionOpened
externalManagedAuditSchemaMigrated
javaApprovalLedgerWritten
javaManagedAuditStoreWritten
javaSqlExecuted
readyForNodeV223ExternalAdapterConnectionReadinessReview
```

这个 digest 给 Node v223 做消费前校验；它不是生产审计记录 digest，也不证明真实外部审计可用。

## 6. VerificationHint 更新

schema 升级为：

```text
java-release-approval-rehearsal-response-schema.v15
```

`verificationHint` 同步纳入 v81：

```text
schemaFields
 -> managedAuditExternalAdapterMigrationGuardReceipt

warningDigestInputs
 -> managedAuditExternalAdapterMigrationGuardReceiptWarnings
 -> externalAdapterMigrationGuardDigest
 -> externalAdapterMigrationCredentialValueReadByJava
 -> externalAdapterMigrationConnectionOpened
 -> externalAdapterMigrationSchemaMigrated
 -> externalAdapterMigrationJavaManagedAuditStoreWritten
 -> externalAdapterMigrationJavaSqlExecuted
 -> externalAdapterMigrationNodeV222SourceEndpointRerunPerformed
 -> externalAdapterMigrationNodeV222AdditionalLocalDryRunWritePerformed

proofClaims
 -> managedAuditExternalAdapterMigrationGuardReceipt.ownerApprovalRequiredBeforeConnection=true
 -> managedAuditExternalAdapterMigrationGuardReceipt.credentialValueReadByJava=false
 -> managedAuditExternalAdapterMigrationGuardReceipt.externalManagedAuditConnectionOpened=false
 -> managedAuditExternalAdapterMigrationGuardReceipt.javaSqlExecuted=false
```

## 7. 测试覆盖

更新了：

- `OpsEvidenceServiceTests`
- `OpsOverviewIntegrationTests#releaseApprovalRehearsalReturnsReadOnlyLiveAggregation`

覆盖点：

- 默认缺 header 时，v81 guard 带 `NODE_V223_SOURCE_IMPLEMENTATION_GUARD_RECEIPT_NOT_READY`
- 完整 header 时，`readyForNodeV223ExternalAdapterConnectionReadinessReview=true`
- `guardDigest` 稳定，重复相同 header 得到相同 digest 和 warning digest
- Node v222 verification report 字段保持 read-only/no-rerun/no-additional-write/no-connection
- Java credential read/store、external connection、schema migration、ledger、managed audit store、SQL、deployment、rollback、restore 全部保持 `false`
- `verificationHint.responseSchemaVersion` 升级到 schema v15

## 8. 边界

本版没有：

- 创建 approval decision
- 写 approval ledger
- 持久化 approval record
- 写 managed audit store
- 执行 SQL 或 schema migration
- 部署、回滚或 restore
- 读取或保存 credential value
- 连接真实外部 managed audit
- 把 Node v221 local JSONL dry-run record 当生产 audit record
