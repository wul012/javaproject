# 66-version-62-release-handoff-checklist-fixture

## 本版角色

v62 做的是 `release handoff checklist fixture`：给后续 Node v175 release handoff readiness review 一份 Java 侧发布执行前人工 checklist 样本。

它不是部署执行器，不执行 deployment，不执行 rollback，不执行 SQL，不连接生产数据库，也不读取 secret value。它只把 release operator、rollback approver、artifact target、迁移方向、secret source confirmation 和 no-secret-value boundary 固化成可验证的只读 fixture。

## 项目进度

做到 v62 后，Java 的生产雏形从“回退审批记录”继续推进到“发布前人工交接 checklist”：

```text
release verification
 -> deployment rollback evidence
 -> release bundle manifest
 -> production secret source contract
 -> production deployment runbook contract
 -> rollback approval record fixture
 -> release handoff checklist fixture
```

成熟度变化：

```text
发布前人工交接证据：增强
Node v175 handoff review 输入：增强
静态 contract endpoint 维护：更集中
真实部署 / 回退执行：仍未授权
```

## 核心流程

```text
OpsEvidenceService.evidence()
 -> releaseHandoffChecklistFixture()
 -> /api/v1/ops/evidence 动态返回
 -> /contracts/release-handoff-checklist.fixture.json 静态返回
 -> release verification / release bundle / handoff / runbook 清单引用
 -> Node 后续只读消费，不触发 Java deployment 或 rollback
```

## 多代码引用讲解

### 1. 顶层 evidence 新增 release handoff checklist fixture

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:18) 在顶层新增：

```java
ReleaseHandoffChecklistFixture releaseHandoffChecklistFixture
```

这让 v62 的发布交接 checklist 不只存在于静态 JSON，也能在动态 `/api/v1/ops/evidence` 中被 Node 读取。

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:126) 定义 checklist 字段：

```java
releaseOperator
rollbackApprover
artifactTarget
selectedMigrationDirection
secretSourceConfirmation
requiredChecklistFields
noSecretValueBoundaries
```

关键边界是：`nodeMayTriggerDeployment=false`、`nodeMayTriggerRollback=false`、`deploymentExecutionAllowed=false`、`rollbackSqlExecutionAllowed=false`。

### 2. Service 固定版本、endpoint 和动态组装

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:40) 固定 v62 版本与 endpoint：

```java
java-release-handoff-checklist-fixture.v1
/contracts/release-handoff-checklist.fixture.json
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:126) 把它接入动态 evidence：

```java
releaseHandoffChecklistFixture()
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:316) 的 `releaseHandoffChecklistFixture()` 固定：

```java
releaseOperator = "release-operator-placeholder"
rollbackApprover = "rollback-approver-placeholder"
artifactTarget = "release-tag-or-artifact-version-placeholder"
selectedMigrationDirection = "no-database-change"
secretSourceConfirmation = PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
```

这些值故意是 placeholder。真实 operator、approver 和 artifact target 必须由人工在发布窗口外部确认，Java fixture 不自动推断。

### 3. 静态 contract endpoint helper 收口

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:477) 新增统一 helper：

```java
staticContractEndpoints(boolean includeFieldGuide)
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:498) 又派生出：

```java
staticContractProbeEndpoints(boolean includeFieldGuide)
```

v62 之前，`healthProbe`、`readOnlyWindow`、`releaseVerification`、`releaseBundle`、`evidenceEndpoints` 都有一串相似 endpoint。v62 把静态 contract 清单集中生成，后续新增 fixture 时更不容易漏掉某个入口。

### 4. 静态 fixture 样本

[release-handoff-checklist.fixture.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-handoff-checklist.fixture.json:1) 是本版新增样本。

核心 checklist：

```json
"releaseChecklist": {
  "releaseOperator": "release-operator-placeholder",
  "rollbackApprover": "rollback-approver-placeholder",
  "artifactTarget": "release-tag-or-artifact-version-placeholder"
}
```

密钥来源确认：

```json
"secretSourceConfirmation": {
  "endpoint": "/contracts/production-secret-source-contract.sample.json",
  "secretValueRecorded": false,
  "nodeMayReadSecretValues": false
}
```

执行边界：

```json
"deploymentExecutionAllowed": false,
"rollbackSqlExecutionAllowed": false,
"requiresProductionDatabase": false
```

### 5. 清单联动

[release-verification-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-verification-manifest.sample.json:56) 把新 endpoint 加入 HTTP smoke 预期和静态 contract 校验清单。

[release-bundle-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-bundle-manifest.sample.json:16) 把 `releaseHandoffChecklistFixture` 加入 bundle input。

[deployment-rollback-evidence.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/deployment-rollback-evidence.sample.json:57) 把新 fixture 加入静态 contract rollback 清单。

[rollback-approval-handoff.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/rollback-approval-handoff.sample.json:61) 把 `release-handoff-checklist-fixture` 加入人工确认字段。

[production-deployment-runbook-contract.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/production-deployment-runbook-contract.sample.json:76) 把新 fixture 加入 runbook artifacts。

这些引用让 Node v175 后续可以从 release bundle 和 handoff 链路自然找到 Java v62 的 checklist，而不是靠路径猜测。

### 6. 测试锁边界

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:258) 验证动态字段：

```java
fixtureVersion = java-release-handoff-checklist-fixture.v1
releaseOperator = release-operator-placeholder
deploymentExecutionAllowed = false
rollbackSqlExecutionAllowed = false
```

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:310) 验证 HTTP 动态 evidence 会返回 `releaseHandoffChecklistFixture`。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:900) 验证静态 endpoint：

```text
GET /contracts/release-handoff-checklist.fixture.json
```

并检查禁止事项：

```text
Executing Java deployment from this fixture
Reading production secret values from this fixture
Triggering Java deployment from Node
```

## 验证与归档

本版运行调试归档：

```text
c/62/解释/说明.md
c/62/图片/
```

验证覆盖：

```text
JSON parse all contracts
OpsEvidenceServiceTests + OpsOverviewIntegrationTests
non-Docker regression tests
mvn -DskipTests package
HTTP smoke
```

## 一句话

v62 把“谁交接发布、谁能审批回退、发布到哪个 artifact、迁移方向是什么、secret 来源如何确认”固化成只读 release handoff checklist fixture，并顺手收口静态 contract endpoint 维护，但仍然不授权任何真实部署或回退执行。
