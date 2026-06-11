> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# version 79 release approval OpsEvidenceService quality split receipt

## 1. 版本目标

Java v79 给 release approval rehearsal 增加 `opsEvidenceServiceQualitySplitReceipt`。它是一个只读质量回执，用来承接 Node v218 的 audit route / managed audit helper quality pass，并给 Node v219 implementation precheck 暴露 Java 侧后续拆分边界。

这版刻意没有做大重构。`OpsEvidenceService` 已经很大，但 v79 处在真实 managed audit adapter wiring 前，贸然抽类容易让 Node 消费的 schema、warning digest 顺序和 proof claims 一起抖动。所以本版先把拆分计划和禁止操作边界纳入 schema v13，等 Node v219 消费稳定后再做更小步的 helper 拆分。

## 2. Response record

`ReleaseApprovalRehearsalResponse` 顶层新增：

```java
RehearsalOpsEvidenceServiceQualitySplitReceipt opsEvidenceServiceQualitySplitReceipt
```

它记录三类信息：

```text
sourceProductionAdapterPrerequisite*
 -> 来源是 Java v78 production adapter prerequisite receipt，source schema 固定为 v12

consumedByNodeQualityPass* / nextNodePrecheck*
 -> Node v218 quality pass 已完成，下一跳是 Node v219 implementation precheck

responsibilityBoundaries / safeSplitSequence / deferredSplitReasons
 -> receipt、digest、hint、render、record 五类职责的拆分边界和暂缓原因
```

关键 no-write 字段全部是 `false`：

```text
apiShapeChanged=false
approvalDecisionCreated=false
approvalLedgerWritten=false
approvalRecordPersisted=false
managedAuditStoreWritten=false
sqlExecuted=false
deploymentTriggered=false
rollbackTriggered=false
restoreExecuted=false
```

## 3. Service 生成顺序

`OpsEvidenceService.releaseApprovalRehearsal(...)` 的顺序变成：

```java
approvalHandoffVerificationMarker
 -> managedAuditAdapterBoundaryReceipt
 -> managedAuditProductionAdapterPrerequisiteReceipt
 -> opsEvidenceServiceQualitySplitReceipt
 -> failureTaxonomy
 -> verificationHint
```

v79 receipt 的 ready 条件依赖 v78 receipt ready，并再次确认 v78 没有打开真实生产动作：

```java
readyForNodeV219ImplementationPrecheck =
    sourceReceiptAccepted && responsibilitiesDocumented;
```

默认缺少完整 header 时，v78 receipt 不 ready，因此 v79 receipt 也不 ready，并返回：

```text
NODE_V219_SOURCE_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_NOT_READY
```

## 4. VerificationHint 更新

schema 升级为：

```text
java-release-approval-rehearsal-response-schema.v13
```

`verificationHint` 同步纳入 v79：

```text
schemaFields
 -> opsEvidenceServiceQualitySplitReceipt

warningDigestInputs
 -> opsEvidenceServiceQualitySplitReceiptWarnings
 -> qualitySplitApiShapeChanged
 -> qualitySplitApprovalDecisionCreated
 -> qualitySplitApprovalLedgerWritten
 -> qualitySplitManagedAuditStoreWritten
 -> qualitySplitSqlExecuted

proofClaims
 -> opsEvidenceServiceQualitySplitReceipt.apiShapeChanged=false
 -> opsEvidenceServiceQualitySplitReceipt.approvalLedgerWritten=false
 -> opsEvidenceServiceQualitySplitReceipt.sqlExecuted=false
```

这样 Node v219 不只看到一个描述字段，也能用 warning digest 和 proof claims 检查质量回执是否仍然保持 no-write/no-SQL/no-adapter-connect。

## 5. 测试覆盖

更新了：

- `OpsEvidenceServiceTests`
- `OpsOverviewIntegrationTests#releaseApprovalRehearsalReturnsReadOnlyLiveAggregation`

覆盖点：

- 默认缺 header 时，v79 receipt 带 `NODE_V219_SOURCE_PRODUCTION_ADAPTER_PREREQUISITE_RECEIPT_NOT_READY`
- 完整 header 时，`readyForNodeV219ImplementationPrecheck=true`
- 所有 approval decision、ledger、managed audit store、SQL、deployment、rollback、restore 字段仍为 `false`
- `verificationHint.responseSchemaVersion` 升级到 schema v13
- v79 warning digest inputs、proof claims、node verification actions 都能被 HTTP JSON 消费

## 6. 边界

本版没有：

- 创建 approval decision
- 写 approval ledger
- 持久化 approval record
- 写 managed audit store
- 执行 SQL
- 部署、回滚或 restore
- 连接真实 managed audit
- 改变 release approval rehearsal API path
