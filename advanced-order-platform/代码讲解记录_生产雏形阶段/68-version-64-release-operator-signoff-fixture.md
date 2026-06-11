> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 68-version-64-release-operator-signoff-fixture

## 本版角色

v64 做的是 `release operator signoff fixture`：给后续 Node v180 approval decision prerequisite gate 一份 Java 侧审批决定前置证据。

它不创建 approval decision，不写 approval ledger，不执行 deployment，不执行 rollback，不执行 SQL，不连接生产数据库，也不读取 secret value。它只把 release operator、rollback approver、release window、artifact target 和 operator signoff placeholder 固化成可验证的只读 fixture。

## 项目进度

做到 v64 后，Java 的生产雏形从“证据保留”继续推进到“审批决定前的人工作业签署”：

```text
release verification
 -> release bundle manifest
 -> release handoff checklist fixture
 -> release audit retention fixture
 -> release operator signoff fixture
```

成熟度变化：

```text
审批决定前置证据：增强
Node v180 prerequisite gate 输入：增强
人工 signoff 字段：更清楚
真实 approval decision / ledger / deployment：仍未授权
```

## 核心流程

```text
OpsEvidenceService.evidence()
 -> releaseOperatorSignoffFixture()
 -> /api/v1/ops/evidence 动态返回
 -> /contracts/release-operator-signoff.fixture.json 静态返回
 -> release verification / bundle / handoff / retention / runbook 清单引用
 -> Node 后续只读消费，不创建 approval decision，不触发 Java deployment 或 rollback
```

## 多代码引用讲解

### 1. 顶层 evidence 新增 release operator signoff fixture

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:20) 在顶层新增：

```java
ReleaseOperatorSignoffFixture releaseOperatorSignoffFixture
```

这让 v64 的 signoff 证据不只存在于静态 JSON，也能在动态 `/api/v1/ops/evidence` 中被 Node 读取。

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:194) 定义了 signoff fixture 的核心字段：

```java
releaseOperator
rollbackApprover
releaseWindow
artifactTarget
operatorSignoffPlaceholder
requiredSignoffFields
signoffArtifacts
noSecretValueBoundaries
```

关键边界是：`nodeMayCreateApprovalDecision=false`、`nodeMayTriggerDeployment=false`、`nodeMayTriggerRollback=false`、`deploymentExecutionAllowed=false`、`rollbackSqlExecutionAllowed=false`。

### 2. Service 固定版本、endpoint 和动态组装

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:52) 固定 v64 版本与 endpoint：

```java
java-release-operator-signoff-fixture.v1
/contracts/release-operator-signoff.fixture.json
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:140) 把它接入动态 evidence：

```java
releaseOperatorSignoffFixture()
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:417) 的 `releaseOperatorSignoffFixture()` 固定：

```java
releaseOperator = "release-operator-placeholder"
rollbackApprover = "rollback-approver-placeholder"
releaseWindow = "release-window-placeholder"
artifactTarget = "release-tag-or-artifact-version-placeholder"
operatorSignoffPlaceholder = "operator-signoff-placeholder"
```

这些值故意是 placeholder。真实 operator、release window 和 signoff reference 必须由人工在发布审批流程外部确认，Java fixture 不自动推断。

### 3. 证据链引用新 fixture

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:570) 的 `staticContractEndpoints()` 把 `/contracts/release-operator-signoff.fixture.json` 加入全局静态 contract 清单。

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:613) 的 `releaseAuditRetentionEndpoints()` 加入 signoff fixture，说明 retention gate 能看到 signoff 前置证据。

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:636) 的 `releaseOperatorSignoffArtifacts()` 单独列出 Node v180 可消费的 signoff 支撑产物：

```java
release handoff checklist fixture
release audit retention fixture
release bundle manifest
release verification manifest
production deployment runbook contract
rollback approval handoff
```

这也是本版的轻量质量优化：把 signoff 产物清单集中到 helper，避免后续 approval prerequisite gate 分散维护引用。

### 4. 静态 fixture 样本

[release-operator-signoff.fixture.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-operator-signoff.fixture.json:1) 是本版新增样本。

核心 signoff record：

```json
"signoffRecord": {
  "releaseOperator": "release-operator-placeholder",
  "rollbackApprover": "rollback-approver-placeholder",
  "releaseWindow": "release-window-placeholder",
  "artifactTarget": "release-tag-or-artifact-version-placeholder",
  "operatorSignoffPlaceholder": "operator-signoff-placeholder"
}
```

它同时声明：

```text
nodeMayCreateApprovalDecision=false
nodeMayWriteApprovalLedger=false
nodeMayTriggerDeployment=false
nodeMayTriggerRollback=false
rollbackSqlExecutionAllowed=false
```

所以这个 fixture 只是 approval decision 前的证据输入，不是 approval decision 本身。

### 5. 现有发布链路引用新 fixture

[release-verification-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-verification-manifest.sample.json:57) 增加 HTTP smoke 预期：

```text
GET /contracts/release-operator-signoff.fixture.json returns release operator signoff boundaries
```

[release-bundle-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-bundle-manifest.sample.json:27) 在 `bundleInputs` 中加入：

```json
"releaseOperatorSignoffFixture": "/contracts/release-operator-signoff.fixture.json"
```

[production-deployment-runbook-contract.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/production-deployment-runbook-contract.sample.json:81) 把 `release-operator-signoff-fixture` 加入人工确认字段，说明进入真实发布前还需要人工 signoff 证据，但 runbook contract 不创建真实审批决定。

### 6. 测试锁定动态和静态契约

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:389) 断言动态 `releaseOperatorSignoffFixture` 的版本、endpoint、release window、required fields、signoff artifacts 和 no-secret 边界。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:414) 断言 `/api/v1/ops/evidence` 动态返回新增字段。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:1234) 新增静态 endpoint 测试：

```java
staticReleaseOperatorSignoffFixtureExplainsNoApprovalDecisionBoundary()
```

这个测试确保 `/contracts/release-operator-signoff.fixture.json` 不会悄悄变成 approval decision、approval ledger 或 deployment 的执行入口。

## 本版边界

```text
nodeMayConsume=true
nodeMayRenderApprovalPrerequisiteGate=true
nodeMayCreateApprovalDecision=false
nodeMayWriteApprovalLedger=false
nodeMayTriggerDeployment=false
nodeMayTriggerRollback=false
nodeMayExecuteRollbackSql=false
deploymentExecutionAllowed=false
rollbackSqlExecutionAllowed=false
requiresProductionDatabase=false
requiresProductionSecrets=false
connectsMiniKv=false
```

## 记忆点

v64 的关键不是“批准发布”，而是把“批准发布之前，Java 侧需要人工确认哪些 signoff 字段、哪些证据产物必须可读、哪些动作绝对不能被 Node 或 Java 自动执行”固定成 contract。这样 Node v180 做 approval decision prerequisite gate 时，有稳定输入可以消费。
