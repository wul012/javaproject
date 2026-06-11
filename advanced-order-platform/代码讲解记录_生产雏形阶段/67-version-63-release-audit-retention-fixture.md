> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 67-version-63-release-audit-retention-fixture

## 本版角色

v63 做的是 `release audit retention fixture`：给后续 Node v178 cross-project evidence retention gate 一份 Java 侧发布证据保留样本。

它不执行 deployment，不执行 rollback，不执行 SQL，不写审计导出文件，不连接生产数据库，也不读取 secret value。它只把 release evidence retention id、operator placeholder、artifact target、retention days、evidence endpoints、audit export 字段和 no-secret-value boundary 固化成可验证的只读 fixture。

## 项目进度

做到 v63 后，Java 的生产雏形从“发布交接 checklist”继续推进到“发布证据保留”：

```text
release verification
 -> deployment rollback evidence
 -> release bundle manifest
 -> release handoff checklist fixture
 -> release audit retention fixture
```

成熟度变化：

```text
发布证据保留字段：增强
Node v178 retention gate 输入：增强
审计导出边界：更清楚
真实部署 / 回退 / SQL：仍未授权
```

## 核心流程

```text
OpsEvidenceService.evidence()
 -> releaseAuditRetentionFixture()
 -> /api/v1/ops/evidence 动态返回
 -> /contracts/release-audit-retention.fixture.json 静态返回
 -> release verification / bundle / handoff / runbook 清单引用
 -> Node 后续只读消费，不触发 Java deployment、rollback 或 audit export 写入
```

## 多代码引用讲解

### 1. 顶层 evidence 新增 release audit retention fixture

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:19) 在顶层新增：

```java
ReleaseAuditRetentionFixture releaseAuditRetentionFixture
```

这让 v63 的保留证据不只存在于静态 JSON，也能在动态 `/api/v1/ops/evidence` 中被 Node 读取。

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:169) 定义了 retention fixture 的核心字段：

```java
retentionId
releaseOperator
artifactTarget
retentionDays
evidenceEndpoints
auditExportFields
retainedArtifacts
noSecretValueBoundaries
```

关键边界是：`nodeMayTriggerDeployment=false`、`nodeMayTriggerRollback=false`、`auditExportReadOnly=true`、`deploymentExecutionAllowed=false`、`rollbackSqlExecutionAllowed=false`。

### 2. Service 固定版本、endpoint 和动态组装

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:45) 固定 v63 版本与 endpoint：

```java
java-release-audit-retention-fixture.v1
/contracts/release-audit-retention.fixture.json
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:132) 把它接入动态 evidence：

```java
releaseAuditRetentionFixture()
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:363) 的 `releaseAuditRetentionFixture()` 固定：

```java
retentionId = "release-retention-record-placeholder"
releaseOperator = "release-operator-placeholder"
artifactTarget = "release-tag-or-artifact-version-placeholder"
retentionDays = 180
```

这些值故意是 placeholder。真实 retention id、operator、artifact target 和导出位置必须由人工在发布流程外部确认，Java fixture 不自动推断。

### 3. 只读证据端点和保留产物清单

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:532) 的 `releaseAuditRetentionEndpoints()` 固定 Node 可读取的 Java 证据入口：

```java
/api/v1/ops/evidence
/api/v1/failed-events/replay-evidence-index
/contracts/release-verification-manifest.sample.json
/contracts/release-bundle-manifest.sample.json
/contracts/release-handoff-checklist.fixture.json
/contracts/production-deployment-runbook-contract.sample.json
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:543) 的 `releaseAuditRetentionArtifacts()` 则描述要被保留的静态产物：

```java
release verification manifest
release bundle manifest
release handoff checklist fixture
production deployment runbook contract
production secret source contract
```

这两组 helper 是本版的轻量质量优化：保留端点与保留产物分开表达，后续 Node 做 retention gate 时更容易判断“读哪些证据”和“保留哪些产物”。

### 4. 静态 fixture 样本

[release-audit-retention.fixture.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-audit-retention.fixture.json:1) 是本版新增样本。

核心 retention record：

```json
"retentionRecord": {
  "retentionId": "release-retention-record-placeholder",
  "releaseOperator": "release-operator-placeholder",
  "artifactTarget": "release-tag-or-artifact-version-placeholder",
  "retentionDays": 180
}
```

它还声明 `auditExportFields`，包括：

```text
retention-id
release-operator
artifact-target
retention-days
evidence-endpoints
audit-export-location-placeholder
no-secret-value-boundary
```

注意 `audit-export-location-placeholder` 只是记录位置占位，不代表 Node 或 Java 在本 fixture 中写文件。

### 5. 现有发布链路引用新 fixture

[release-verification-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-verification-manifest.sample.json:56) 增加 HTTP smoke 预期：

```text
GET /contracts/release-audit-retention.fixture.json returns release audit retention boundaries
```

[release-bundle-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-bundle-manifest.sample.json:19) 在 `bundleInputs` 中加入：

```json
"releaseAuditRetentionFixture": "/contracts/release-audit-retention.fixture.json"
```

[production-deployment-runbook-contract.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/production-deployment-runbook-contract.sample.json:66) 把 `release-audit-retention-fixture` 加入人工确认字段，说明发布 runbook 也必须确认保留证据边界。

### 6. 测试锁定动态和静态契约

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:320) 断言动态 `releaseAuditRetentionFixture` 的版本、endpoint、retention days、evidence endpoints、audit fields 和 no-secret 边界。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:352) 断言 `/api/v1/ops/evidence` 动态返回新增字段。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:1093) 新增静态 endpoint 测试：

```java
staticReleaseAuditRetentionFixtureExplainsReadOnlyRetentionBoundary()
```

这个测试确保 `/contracts/release-audit-retention.fixture.json` 不会悄悄变成执行入口。

## 本版边界

```text
nodeMayConsume=true
nodeMayRenderRetentionGate=true
nodeMayTriggerDeployment=false
nodeMayTriggerRollback=false
nodeMayWriteAuditExport=false
auditExportReadOnly=true
deploymentExecutionAllowed=false
rollbackSqlExecutionAllowed=false
requiresProductionDatabase=false
requiresProductionSecrets=false
connectsMiniKv=false
```

## 记忆点

v63 的关键不是“保存真实审计文件”，而是先把“发布证据应该如何被保留、由谁确认、保留多久、哪些 endpoint/产物纳入保留、哪些动作绝对不能做”固定成 Java 侧 contract。这样 Node v178 做跨项目 retention gate 时，有稳定输入可以消费。
