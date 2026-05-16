# version 76 release approval handoff verification marker

## 1. 本版目标

Java v76 继续沿用 `/api/v1/ops/release-approval-rehearsal` 只读端点，新增 `approvalHandoffVerificationMarker`。

它的作用不是创建审批，而是把 Node v211 已经消费 Java v75 `approvalRecordHandoffHint` 的事实做成一个 Java 可读的 marker，方便后续 Node v213 在 restore drill plan 之前检查：

```text
Java v75 approvalRecordHandoffHint
 -> Node v211 managed audit identity approval provenance dry-run packet
 -> Java v76 approvalHandoffVerificationMarker
 -> Node v213 restore drill plan precheck
```

## 2. Response record 新增字段

`ReleaseApprovalRehearsalResponse` 顶层在 `approvalRecordHandoffHint` 后新增：

```java
RehearsalApprovalHandoffVerificationMarker approvalHandoffVerificationMarker
```

这个位置表示它依赖 v75 的 handoff hint，但仍早于 `failureTaxonomy` 和 `verificationHint`，因为后两者需要把 marker warning 和 no-write proof 一并纳入响应校验。

新增 record 的核心字段分为四类：

```text
markerVersion / sourceApprovalRecordHandoff*
 -> Java v76 marker 自身版本，以及它承认消费的是 Java v75 handoff schema。

consumedByNode*
 -> Node v211 packet profile、endpoint、requestId、packetVersion、.tmp dry-run 文件名。

nodeV211*
 -> Node v211 已覆盖 append/query/digest/cleanup，且没有 Java write、mini-kv write、外部 audit、真实 approval decision、真实 ledger、生产 audit record。

readyForNodeV213RestoreDrillPlan
 -> 只有 handoff 上下文完整且 no-write 边界成立时才为 true。
```

## 3. Marker 生成逻辑

`OpsEvidenceService.releaseApprovalRehearsal(...)` 先生成 v75 的 `approvalRecordHandoffHint`，再调用：

```java
rehearsalApprovalHandoffVerificationMarker(approvalRecordHandoffHint)
```

marker 的接受条件有两层：

```java
nodeV211HandoffAccepted =
    hintVersion == java-release-approval-rehearsal-approval-record-handoff-hint.v1
    && approvalBindingContractVersion == managed-audit-identity-approval-binding-contract.v1
    && approvalRecordHandoffContextComplete;

nodeV211NoWriteBoundaryAccepted =
    approvalRecordFixtureReadOnly
    && !javaApprovalDecisionCreated
    && !javaApprovalLedgerWritten
    && !javaApprovalRecordPersisted
    && !nodeMayTreatAsProductionApprovalRecord;
```

默认不传 header 时，v75 handoff 的上下文不完整，所以：

```text
nodeV211HandoffAccepted=false
readyForNodeV213RestoreDrillPlan=false
markerWarnings=[NODE_V211_APPROVAL_HANDOFF_CONTEXT_INCOMPLETE]
```

传入完整 Node v210 approval binding header 时：

```text
nodeV211HandoffAccepted=true
nodeV211NoWriteBoundaryAccepted=true
readyForNodeV213RestoreDrillPlan=true
markerWarnings=[]
```

## 4. VerificationHint 也纳入 marker

`releaseApprovalVerificationHint(...)` 增加 `approvalHandoffVerificationMarker` 参数，并把 marker 放进三处：

```text
schemaFields
 -> approvalHandoffVerificationMarker

warningDigestInputs
 -> approvalHandoffVerificationMarkerWarnings
 -> nodeV211ProductionAuditRecordAllowed
 -> nodeV211RealApprovalDecisionCreated

proofClaims
 -> approvalHandoffVerificationMarker.nodeV211ProductionAuditRecordAllowed=false
 -> approvalHandoffVerificationMarker.nodeV211RealApprovalDecisionCreated=false
 -> approvalHandoffVerificationMarker.nodeV211RealApprovalLedgerWritten=false
 -> approvalHandoffVerificationMarker.javaApprovalRecordPersisted=false
```

同时 no-ledger proof 继续要求这些字段为 false，避免 Node 把 marker 误解成生产审批、生产审计或真实 ledger 证据。

## 5. 边界

本版没有新增 controller header、没有新增接口、没有写数据库、没有调用外部系统。

明确禁止：

```text
Java approval decision creation
Java approval ledger write
Java approval record persistence
Java managed audit write
external audit system access
production audit record creation
restore execution
deployment / rollback / rollback SQL
secret access
```

## 6. 测试覆盖

`OpsEvidenceServiceTests` 覆盖：

```text
默认读取：
 -> markerVersion
 -> Node v211 packet profile/endpoint/requestId/file labels
 -> nodeV211HandoffAccepted=false
 -> nodeV211NoWriteBoundaryAccepted=true
 -> readyForNodeV213RestoreDrillPlan=false
 -> marker warning
 -> verificationHint schema/warning/proof action

完整 header 读取：
 -> nodeV211HandoffAccepted=true
 -> readyForNodeV213RestoreDrillPlan=true
 -> markerWarnings=[]
 -> no real approval decision / ledger / production audit record
```

`OpsOverviewIntegrationTests` 覆盖 JSON 对外形状，保证 Node 后续读取字段名稳定。

## 7. 版本关系

```text
Java v75
 -> 提供 approvalRecordHandoffHint

Node v211
 -> 消费 Java v75 handoff，写本地 .tmp dry-run packet，并验证 append/query/digest/cleanup

Java v76
 -> 暴露 approvalHandoffVerificationMarker，说明 Node v211 消费结果和 no-write 边界

Node v213
 -> 可以把该 marker 作为 restore drill plan 的前置检查之一，但仍不能执行 restore
```
