> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# v75 release approval approval-record handoff hint 代码讲解

## 本版目标

v75 按最新 Node 计划 `D:\nodeproj\orderops-node\docs\plans\v209-managed-audit-dry-run-roadmap.md` 推进 Java 侧小版本：在 release approval rehearsal 只读响应中新增 `approvalRecordHandoffHint`，给后续 Node v211 managed audit dry-run packet 标注哪些审批上下文字段可以进入 Node audit record。

本版保持只读边界：Java 不创建 approval decision，不写 approval ledger，不持久化 approval record，不做生产身份认证，不连接生产 approval store，也不触发 deployment / rollback / rollback SQL。Java 只提供可被 Node 读取和校验的 handoff hint。

## 响应结构入口

`src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java`

`ReleaseApprovalRehearsalResponse` 顶层新增：

```java
RehearsalApprovalRecordHandoffHint approvalRecordHandoffHint
```

它和 `auditPersistenceHandoffHint` 同级。Node 读取 `/api/v1/ops/release-approval-rehearsal` 时，可以在同一个只读响应里拿到 request/operator/CI/runtime/managed-audit 上下文，以及本版新增的 approval record handoff 字段。

## approvalRecordHandoffHint 字段含义

第一类字段来自 Java 现有 rollback approval record fixture：

```java
sourceApprovalRecordFixtureVersion
sourceApprovalRecordFixtureEndpoint
reviewerPlaceholder
approvalTimestampPlaceholder
rollbackTarget
selectedMigrationDirection
sourceRecordArtifacts
```

这些字段说明 Java 提供的是只读 fixture 和 placeholder，不是生产 approval record。

第二类字段回显 Node v210 approval binding contract 上下文：

```java
approvalBindingContractVersion
approvalBindingContractDigest
approvalRequestId
approvalDecisionState
approvalRecordCorrelationId
```

对应 controller 新增的 5 个可选 header：

```java
x-orderops-approval-binding-contract-version
x-orderops-approval-binding-contract-digest
x-orderops-approval-request-id
x-orderops-approval-decision-state
x-orderops-approval-record-correlation-id
```

缺省时进入 `echoWarnings`，完整传入时 `approvalRecordHandoffContextComplete=true`。所有 header 只做 trim 和 echo，不认证、不授权、不入库。

第三类字段是边界证明：

```java
approvalRecordFixtureReadOnly=true
javaApprovalDecisionCreated=false
javaApprovalLedgerWritten=false
javaApprovalRecordPersisted=false
javaApprovalRecordAuthenticated=false
productionApprovalStoreRequired=false
nodeMayUseAsAuditApprovalInput=true
nodeMayTreatAsProductionApprovalRecord=false
```

这里的重点是区分“Node 可以把这些字段作为 dry-run audit approval 输入”和“这不是生产 approval record”。Java 不把 placeholder reviewer 或 timestamp 写入真实审批表，也不对 decision state 做生产授权解释。

第四类字段是 handoff 清单：

```java
acceptedApprovalRecordHeaders
handoffFieldPaths
nodeVerificationActions
```

`handoffFieldPaths` 明确列出 Node 后续可以纳入 dry-run audit packet 的字段，例如 `requestContext.requestId`、`operatorWindowHint.operatorId`、`operatorWindowHint.operatorRoles`、`approvalRecordHandoffHint.approvalRequestId`、`approvalRecordHandoffHint.approvalDecisionState`、`approvalRecordHandoffHint.approvalRecordCorrelationId` 和 `verificationHint.warningDigest`。

## Service 组装逻辑

`src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java`

`releaseApprovalRehearsal(...)` 在 v74 28 参数主重载基础上新增 5 个 approval-record header 参数。旧重载继续保留，并向新主重载传 `null`，保证已有调用不用同步改动。

核心构造方法是：

```java
private ReleaseApprovalRehearsalResponse.RehearsalApprovalRecordHandoffHint
        rehearsalApprovalRecordHandoffHint(...)
```

该方法做四件事：

1. 检查 Node v210 approval binding 上下文是否完整，缺少字段时写入 `ORDEROPS_APPROVAL_*_MISSING` warning。
2. 复用 rollback approval record fixture 的 version、endpoint、reviewer placeholder、approval timestamp placeholder、rollback target 和 migration direction。
3. 固定声明 Java 不创建审批决定、不写 ledger、不持久化 approval record、不要求生产 approval store。
4. 列出 Node v211 应比对的 contract version/digest 和 dry-run audit packet 字段路径。

## verificationHint 变化

`RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION` 从：

```text
java-release-approval-rehearsal-response-schema.v8
```

升级为：

```text
java-release-approval-rehearsal-response-schema.v9
```

`verificationHint.schemaFields` 新增：

```text
approvalRecordHandoffHint
```

`warningDigestInputs` 新增：

```text
approvalRecordHandoffEchoWarnings
javaApprovalRecordPersisted
nodeMayTreatAsProductionApprovalRecord
```

`proofClaims` 新增：

```text
approvalRecordHandoffHint.approvalRecordFixtureReadOnly=true
approvalRecordHandoffHint.javaApprovalDecisionCreated=false
approvalRecordHandoffHint.javaApprovalLedgerWritten=false
approvalRecordHandoffHint.javaApprovalRecordPersisted=false
approvalRecordHandoffHint.nodeMayTreatAsProductionApprovalRecord=false
```

这让 Node 后续比对 warning digest 时，可以把 approval record handoff 的缺省 warning 和 no-write 边界一起纳入稳定摘要。

## Controller header 接入

`src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java`

`GET /api/v1/ops/release-approval-rehearsal` 新增 5 个可选 `x-orderops-approval-*` header。Controller 只转发 header，不做鉴权、不写数据库、不创建审批记录。

## 测试覆盖

`src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java`

- 缺省调用：断言 `approvalRecordHandoffHint` 使用 placeholder，出现 5 个 missing warning，schema 升级为 v9。
- 完整 header 调用：断言 Node v210 approval binding 字段被 trim 后回显，所有 echoed flag 为 true，`approvalRecordHandoffContextComplete=true`。
- digest 稳定性：同一组 header 重复调用，`verificationHint.warningDigest` 保持一致。
- no-write proof：断言 Java 不创建 decision、不写 ledger、不持久化 approval record。

`src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java`

- HTTP 缺省调用：断言 JSON 出现 `approvalRecordHandoffHint`、schema v9、missing warning 和 no-write proof。
- HTTP 完整 header 调用：断言 controller 新 header 能进入 JSON 响应，并且 Java 仍保持 read-only、no-ledger-write、no-approval-record-persist 边界。

## 边界结论

v75 是 managed audit dry-run packet 的 approval record 前置 handoff 版本。它只说明 Java 哪些只读审批字段未来可被 Node audit record dry-run 消费，不把 Java 变成 approval record 写入方，也不把响应当作生产审批记录。真正的 packet 存储、digest 比对和 dry-run 写入验证应由 Node v211 继续推进。
