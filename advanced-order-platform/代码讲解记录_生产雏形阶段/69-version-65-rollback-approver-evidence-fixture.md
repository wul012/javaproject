> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 69-version-65-rollback-approver-evidence-fixture

## 本版角色

v65 做的是 `rollback approver evidence fixture`：给后续 Node v182 release approval decision rehearsal packet 一份 Java 侧回滚审批人和 SQL/no-SQL 边界证据。

它不创建 approval decision，不写 approval ledger，不执行 rollback，不执行 rollback SQL，不连接生产数据库，也不读取 secret value。它只把 rollback approver、migration direction、rollback SQL artifact reference 和 production database boundary 固化成可验证的只读 fixture。

## 项目进度

做到 v65 后，Java 的生产雏形从“审批决定前的人工作业签署”继续推进到“审批决定演练前的回滚审批证据”：

```text
release operator signoff fixture
 -> rollback approver evidence fixture
 -> 后续 Node v182 decision rehearsal packet
```

成熟度变化：

```text
回滚审批人证据：增强
SQL/no-SQL 边界：更清楚
生产数据库边界：更明确
真实 approval decision / ledger / rollback / SQL：仍未授权
```

## 核心流程

```text
OpsEvidenceService.evidence()
 -> rollbackApproverEvidenceFixture()
 -> /api/v1/ops/evidence 动态返回
 -> /contracts/rollback-approver-evidence.fixture.json 静态返回
 -> release verification / bundle / handoff / retention / runbook 清单引用
 -> Node 后续只读消费，不创建 approval decision，不触发 Java rollback 或 SQL
```

## 多代码引用讲解

### 1. 顶层 evidence 新增 rollback approver evidence fixture

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:21) 在顶层新增：

```java
RollbackApproverEvidenceFixture rollbackApproverEvidenceFixture
```

这让 v65 的回滚审批证据可以和 release operator signoff 一样，通过动态 `/api/v1/ops/evidence` 暴露给 Node，而不是只存在于静态 JSON。

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:234) 定义了新 record：

```java
rollbackApprover
migrationDirectionOptions
selectedMigrationDirection
rollbackSqlArtifactReference
productionDatabaseBoundary
requiredEvidenceFields
evidenceArtifacts
noSecretValueBoundaries
```

关键边界是：`nodeMayCreateApprovalDecision=false`、`nodeMayTriggerRollback=false`、`rollbackExecutionAllowed=false`、`rollbackSqlExecutionAllowed=false`、`requiresProductionDatabase=false`。

### 2. Service 固定版本、endpoint 和动态组装

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:58) 固定 v65 版本与 endpoint：

```java
java-rollback-approver-evidence-fixture.v1
/contracts/rollback-approver-evidence.fixture.json
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:147) 把它接入动态 evidence：

```java
rollbackApproverEvidenceFixture()
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:466) 的 `rollbackApproverEvidenceFixture()` 固定：

```java
rollbackApprover = "rollback-approver-placeholder"
selectedMigrationDirection = "no-database-change"
rollbackSqlArtifactReference = "rollback-sql-artifact-reference-placeholder"
productionDatabaseBoundary = "production-database-connection-outside-this-fixture"
```

这些值故意是 placeholder。真实 rollback approver、SQL artifact reference 和 production database access 必须由人工在发布审批流程外部确认，Java fixture 不自动推断。

### 3. 轻量质量优化：集中 rollback approver 证据产物

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:706) 新增 `rollbackApproverEvidenceArtifacts()`：

```java
ROLLBACK_SQL_REVIEW_GATE_ENDPOINT
ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT
PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT
PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
RELEASE_BUNDLE_MANIFEST_ENDPOINT
```

这个 helper 把 v65 新 fixture 的支撑产物集中起来，避免同一组 endpoint 在动态 evidence、静态样本和后续 runbook 引用中散落维护。

### 4. 证据链引用新 fixture

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:624) 的 `staticContractEndpoints()` 把 `/contracts/rollback-approver-evidence.fixture.json` 加入全局静态 contract 清单。

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:356) 的 release handoff checklist required fields 增加：

```text
rollback-approver-evidence-fixture
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:437) 的 release operator signoff required fields 也增加同一项，说明 signoff 后进入 decision rehearsal 前还需要 rollback approver evidence。

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:766) 的 production deployment runbook contract 把 `rollback-approver-evidence-fixture` 作为人工确认字段，且 runbook artifacts 引用新 endpoint。

### 5. 静态 fixture 样本

[rollback-approver-evidence.fixture.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/rollback-approver-evidence.fixture.json:1) 是本版新增样本。

核心 approver evidence：

```json
"approverEvidence": {
  "rollbackApprover": "rollback-approver-placeholder",
  "operatorMustReplacePlaceholders": true,
  "evidenceStatus": "PENDING_OPERATOR_CONFIRMATION"
}
```

核心 database migration boundary：

```json
"selectedDirection": "no-database-change",
"rollbackSqlArtifactReference": "rollback-sql-artifact-reference-placeholder",
"rollbackSqlTextEmbedded": false,
"rollbackSqlExecutionAllowed": false,
"requiresProductionDatabase": false
```

所以这个 fixture 只是 decision rehearsal 的证据输入，不是 rollback approval decision，也不是 SQL 执行入口。

### 6. 现有发布链路引用新 fixture

[release-verification-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-verification-manifest.sample.json:58) 增加 HTTP smoke 预期：

```text
GET /contracts/rollback-approver-evidence.fixture.json returns rollback approver evidence boundaries
```

[release-bundle-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-bundle-manifest.sample.json:22) 在 `bundleInputs` 中加入：

```json
"rollbackApproverEvidenceFixture": "/contracts/rollback-approver-evidence.fixture.json"
```

[rollback-approval-handoff.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/rollback-approval-handoff.sample.json:86) 把 `rollback-approver-evidence-fixture` 加入人工确认字段，说明回滚窗口前需要明确 rollback approver 和 SQL/no-SQL 证据。

[production-deployment-runbook-contract.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/production-deployment-runbook-contract.sample.json:89) 也引用新 fixture，保持 runbook、handoff、bundle 三条线一致。

### 7. 测试锁定动态和静态契约

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:451) 断言动态 `rollbackApproverEvidenceFixture` 的版本、endpoint、rollback approver、migration direction、SQL artifact reference、production database boundary 和全部执行禁止边界。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:469) 断言 `/api/v1/ops/evidence` 动态返回新增字段。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:1397) 新增静态 endpoint 测试：

```java
staticRollbackApproverEvidenceFixtureExplainsNoRollbackExecutionBoundary()
```

这个测试确保 `/contracts/rollback-approver-evidence.fixture.json` 不会悄悄变成 approval decision、approval ledger、rollback、rollback SQL 或 production database 的执行入口。

## 本版边界

```text
nodeMayConsume=true
nodeMayRenderDecisionRehearsalInput=true
nodeMayCreateApprovalDecision=false
nodeMayWriteApprovalLedger=false
nodeMayTriggerRollback=false
nodeMayExecuteRollbackSql=false
rollbackExecutionAllowed=false
rollbackSqlExecutionAllowed=false
requiresProductionDatabase=false
requiresProductionSecrets=false
connectsMiniKv=false
```

## 记忆点

v65 的关键不是“批准回滚”，而是把“批准决策演练之前，Java 侧必须说明 rollback approver 是谁、迁移方向是什么、SQL 产物引用在哪里、生产数据库连接边界在哪里”固定成 contract。这样 Node v182 做 release approval decision rehearsal packet 时，有稳定输入可以消费，但仍不能创建真实审批决定或触发回滚。
