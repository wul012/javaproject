> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# version 77 release approval managed audit adapter boundary receipt

## 1. 本版目标

Java v77 继续使用 `/api/v1/ops/release-approval-rehearsal` 只读端点，新增 `managedAuditAdapterBoundaryReceipt`。

它不是一个新的审批、审计写入或 restore 能力，而是把 Node v214 已经完成的 managed audit restore drill archive verification 与 Java v76 `approvalHandoffVerificationMarker` 串起来，给 Node v215 的 managed audit dry-run adapter candidate 一个明确边界：

```text
Java v76 approvalHandoffVerificationMarker
 -> Node v214 managed audit restore drill archive verification
 -> Java v77 managedAuditAdapterBoundaryReceipt
 -> Node v215 managed audit dry-run adapter candidate
```

## 2. Response record 新增字段

`ReleaseApprovalRehearsalResponse` 顶层在 `approvalHandoffVerificationMarker` 后新增：

```java
RehearsalManagedAuditAdapterBoundaryReceipt managedAuditAdapterBoundaryReceipt
```

这个位置表示它依赖 v76 marker，并且仍早于 `failureTaxonomy` 和 `verificationHint`。后两者会继续把 receipt warning、schema field、warning digest input 和 no-ledger proof 统一纳入校验。

新增 record 的核心字段分四类：

```text
receiptVersion / sourceApprovalHandoff*
 -> Java v77 receipt 自身版本，以及它消费的是 Java v76 marker schema。

consumedByNodeArchiveVerification*
 -> Node v214 archive verification profile、state 和 endpoint。

nextNodeCandidate*
 -> 后续 Node v215 dry-run adapter candidate 的版本和 profile。

nodeV215* / java*
 -> 允许 Node v215 消费和写本地 dry-run/test 文件，但禁止真实 managed audit、approval decision、approval ledger、approval record、SQL、部署、回滚和 restore。
```

## 3. Receipt 生成逻辑

`OpsEvidenceService.releaseApprovalRehearsal(...)` 先生成 v76 marker，再调用：

```java
rehearsalManagedAuditAdapterBoundaryReceipt(approvalHandoffVerificationMarker)
```

receipt 的 ready 条件很克制：

```java
sourceMarkerAccepted =
    markerVersion == java-release-approval-rehearsal-approval-handoff-verification-marker.v1
    && readyForNodeV213RestoreDrillPlan
    && !nodeV211ProductionAuditRecordAllowed
    && !nodeV211RealApprovalDecisionCreated
    && !nodeV211RealApprovalLedgerWritten
    && !javaApprovalRecordPersisted
    && !javaApprovalLedgerWritten
    && !nodeMayTreatAsProductionAuditRecord;

readyForNodeV215DryRunAdapterCandidate = sourceMarkerAccepted && adapterWritesBlocked;
```

默认不传完整 approval binding header 时，v76 marker 还没有 ready：

```text
readyForNodeV215DryRunAdapterCandidate=false
receiptWarnings=[NODE_V215_SOURCE_APPROVAL_HANDOFF_MARKER_NOT_READY]
```

传入完整 Node v210 approval binding header 后：

```text
readyForNodeV215DryRunAdapterCandidate=true
receiptWarnings=[]
```

## 4. VerificationHint 纳入 receipt

`releaseApprovalVerificationHint(...)` 增加 `managedAuditAdapterBoundaryReceipt` 参数，并放进三处：

```text
schemaFields
 -> managedAuditAdapterBoundaryReceipt

warningDigestInputs
 -> managedAuditAdapterBoundaryReceiptWarnings
 -> nodeV215MayConnectManagedAudit
 -> nodeV215MayCreateApprovalDecision
 -> nodeV215MayWriteApprovalLedger
 -> nodeV215MayExecuteSql
 -> nodeV215MayTriggerDeployment
 -> nodeV215MayTriggerRollback
 -> nodeV215MayExecuteRestore

proofClaims
 -> managedAuditAdapterBoundaryReceipt.nodeV215MayConnectManagedAudit=false
 -> managedAuditAdapterBoundaryReceipt.nodeV215MayCreateApprovalDecision=false
 -> managedAuditAdapterBoundaryReceipt.nodeV215MayWriteApprovalLedger=false
 -> managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteSql=false
 -> managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerDeployment=false
 -> managedAuditAdapterBoundaryReceipt.nodeV215MayTriggerRollback=false
 -> managedAuditAdapterBoundaryReceipt.nodeV215MayExecuteRestore=false
 -> managedAuditAdapterBoundaryReceipt.javaApprovalDecisionCreated=false
 -> managedAuditAdapterBoundaryReceipt.javaApprovalLedgerWritten=false
```

no-ledger proof 也继续要求这些字段为 false，避免 Node v215 把 dry-run adapter candidate 误解成真实审计、审批、SQL、部署、回滚或 restore 授权。

## 5. 边界

本版没有新增 controller header、没有新增接口、没有写数据库、没有连接外部系统。

明确允许：

```text
Node v215 may consume Java v77 receipt
Node v215 may write local .tmp or controlled test files
```

明确禁止：

```text
Connect real managed audit storage
Create Java approval decision
Write Java approval ledger
Persist Java approval record
Execute Java SQL
Trigger Java deployment
Trigger Java rollback
Execute restore
Set UPSTREAM_ACTIONS_ENABLED=true for Node v215
```

## 6. 测试覆盖

`OpsEvidenceServiceTests` 覆盖：

```text
默认读取：
 -> receiptVersion
 -> Node v214 archive verification profile/state/endpoint
 -> Node v215 candidate version/profile
 -> nodeV215MayWriteLocalDryRunFiles=true
 -> all real audit/approval/SQL/deploy/rollback/restore flags=false
 -> readyForNodeV215DryRunAdapterCandidate=false
 -> receipt warning
 -> verificationHint schema/warning/proof action

完整 header 读取：
 -> readyForNodeV215DryRunAdapterCandidate=true
 -> receiptWarnings=[]
 -> all Java/Node write and production audit flags remain false
```

`OpsOverviewIntegrationTests` 覆盖 JSON 对外形状，保证 Node 后续读取字段名稳定。

## 7. 版本关系

```text
Java v76
 -> 暴露 approvalHandoffVerificationMarker，证明 Node v211 消费结果和 no-write 边界

Node v214
 -> 归档验证 managed audit restore drill archive，不执行 restore，不连接真实 managed audit

Java v77
 -> 暴露 managedAuditAdapterBoundaryReceipt，证明 Node v215 只能做 dry-run adapter candidate

Node v215
 -> 可消费该 receipt，但只能写本地 dry-run/test evidence，不能触发真实上游动作
```
