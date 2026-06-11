> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# v74 release approval audit-persistence handoff hint 代码讲解

## 本版目标

v74 按 `D:\nodeproj\orderops-node\docs\plans\v207-post-hardening-triage-roadmap.md` 推进 Java 侧小版本：在 release approval rehearsal 只读响应中新增 `auditPersistenceHandoffHint`，给后续 Node v208 managed audit persistence boundary candidate 提供 Java 侧只读字段清单、retention 对齐提示和 no-write 边界证明。

本版不写 Java approval ledger，不创建 approval decision，不写 managed audit store，不连接外部审计系统，不读取生产密钥，也不授权 deployment / rollback / SQL。Java 只提供可被 Node 读取的 handoff hint。

## 响应结构入口

`src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java`

`ReleaseApprovalRehearsalResponse` 顶层新增：

```java
RehearsalAuditPersistenceHandoffHint auditPersistenceHandoffHint
```

它和 `operatorWindowHint`、`ciEvidenceHint`、`artifactRetentionHint`、`liveReadinessHint` 同级。Node 读取 `/api/v1/ops/release-approval-rehearsal` 时，可以在同一个响应里看到此前的审批、CI、retention、runtime smoke 上下文，以及本版新增的 managed audit 交接信息。

## auditPersistenceHandoffHint 字段含义

第一类字段来自 Java 现有 release audit retention fixture：

```java
sourceRetentionFixtureVersion
sourceRetentionFixtureEndpoint
javaRetentionDays
javaAuditSourceReadOnly
```

这些字段说明 Java 侧可供 handoff 的来源仍是只读 fixture，当前 retention days 是 180，且不会触发 deployment、rollback 或 rollback SQL。

第二类字段回显 Node v208 managed audit candidate 上下文：

```java
managedAuditCandidateVersion
managedAuditCandidateDigest
managedAuditSinkMode
managedAuditRetentionDays
managedAuditRotationPolicy
```

对应 controller 新增的 5 个可选 header：

```java
x-orderops-managed-audit-candidate-version
x-orderops-managed-audit-candidate-digest
x-orderops-managed-audit-sink-mode
x-orderops-managed-audit-retention-days
x-orderops-managed-audit-rotation-policy
```

缺省时进入 `echoWarnings`，完整传入时 `auditPersistenceHandoffContextComplete=true`。`managedAuditRetentionWithinJavaRetention` 会复用现有 retention days 校验，要求 Node 候选 retention 天数大于 0 且不超过 Java retention fixture 的 180 天。

第三类字段是边界证明：

```java
javaLedgerWriteAllowed=false
javaManagedAuditWriteAllowed=false
javaExternalAuditSystemAccessed=false
productionAuditStoreRequired=false
nodeMayUseAsManagedAuditInput=true
nodeMayTreatAsProductionAuditRecord=false
```

这里的重点是区分“Node 可以把这些字段作为 managed audit dry-run 输入”和“这不是生产审计记录”。Java 不写审计存储，Node v208/v209 后续如果做 dry-run，也只能写 Node 本地测试临时目录。

第四类字段是 handoff 清单：

```java
acceptedAuditPersistenceHeaders
handoffFieldPaths
readOnlySourceEndpoints
nodeVerificationActions
```

`handoffFieldPaths` 明确列出未来可进入 Node managed audit 的只读字段，例如 `requestContext.requestId`、`operatorWindowHint.operatorId`、`ciEvidenceHint.manifestDigest`、`liveReadinessHint.runtimeSmokeSessionId`、`verificationHint.warningDigest` 和 `executionBoundaries.nodeMayWriteApprovalLedger`。

## Service 组装逻辑

`src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java`

`releaseApprovalRehearsal(...)` 保留原有 23 参数重载兼容路径，并新增 28 参数主重载接收 managed-audit header。进入 service 后，所有 header 仍统一经过 `normalizeHeaderValue(...)` 去空白。

核心构造方法是：

```java
private ReleaseApprovalRehearsalResponse.RehearsalAuditPersistenceHandoffHint
        rehearsalAuditPersistenceHandoffHint(...)
```

该方法做四件事：

1. 检查 Node v208 managed-audit 候选上下文是否完整，缺少字段时写入 `ORDEROPS_MANAGED_AUDIT_*_MISSING` warning。
2. 复用 release audit retention fixture 的 version、endpoint 和 retention days。
3. 校验 Node 候选 retention days 是否落在 Java retention fixture 范围内。
4. 固定声明 Java 不写 ledger、不写 managed audit、不连接外部审计系统。

## verificationHint 变化

`RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION` 从：

```text
java-release-approval-rehearsal-response-schema.v7
```

升级为：

```text
java-release-approval-rehearsal-response-schema.v8
```

`verificationHint.schemaFields` 新增：

```text
auditPersistenceHandoffHint
```

`warningDigestInputs` 新增：

```text
auditPersistenceHandoffEchoWarnings
javaManagedAuditWriteAllowed
nodeMayTreatAsProductionAuditRecord
```

`proofClaims` 新增：

```text
auditPersistenceHandoffHint.javaAuditSourceReadOnly=true
auditPersistenceHandoffHint.javaLedgerWriteAllowed=false
auditPersistenceHandoffHint.javaManagedAuditWriteAllowed=false
auditPersistenceHandoffHint.javaExternalAuditSystemAccessed=false
auditPersistenceHandoffHint.nodeMayTreatAsProductionAuditRecord=false
```

这让 Node 后续比对 warning digest 时，可以把 managed-audit handoff 的缺省 warning 和 no-write 边界一起纳入稳定摘要。

## Controller header 接入

`src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java`

`GET /api/v1/ops/release-approval-rehearsal` 新增 5 个可选 `x-orderops-managed-audit-*` header。Controller 只转发 header，不做鉴权、不写数据库、不创建审计记录。

## 测试覆盖

`src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java`

- 缺省调用：断言 `auditPersistenceHandoffHint` 使用 placeholder，出现 5 个 missing warning，schema 升级为 v8。
- 完整 header 调用：断言 Node v208 managed-audit 候选字段被 trim 后回显，所有 echoed flag 为 true，`auditPersistenceHandoffContextComplete=true`。
- digest 稳定性：同一组 header 重复调用，`verificationHint.warningDigest` 保持一致。

`src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java`

- HTTP 缺省调用：断言 JSON 出现 `auditPersistenceHandoffHint`、schema v8、missing warning 和 no-write proof。
- HTTP 完整 header 调用：断言 controller 新 header 能进入 JSON 响应，并且 Java 仍保持 read-only、no-ledger-write、no-managed-audit-write 边界。

## 边界结论

v74 是一个 managed audit 前置 handoff 版本。它只说明 Java 哪些 release approval rehearsal 字段未来可被 Node managed audit dry-run 消费，不把 Java 变成审计存储写入方，也不把响应当作生产审计记录。真正的 adapter contract、file/sqlite candidate、retention/rotation 和 dry-run 写入验证应由 Node v208/v209 继续推进。
