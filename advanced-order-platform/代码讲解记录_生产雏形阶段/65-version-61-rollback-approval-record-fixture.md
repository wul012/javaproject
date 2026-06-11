> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 65-version-61-rollback-approval-record-fixture

## 本版角色

v61 做的是 `rollback approval record fixture`：给后续 Node release window readiness packet 一份 Java 侧人工回退审批记录样本。

它不是回退执行器，不执行 rollback，不执行 SQL，不连接生产数据库，也不读取 secret value。它只把 reviewer、approval timestamp placeholder、rollback target、迁移方向和 no-secret-value boundary 固化成可验证的只读 fixture。

## 项目进度

做到 v61 后，Java 的生产雏形从“部署 runbook 入口”继续推进到“发布窗口人工审批记录”：

```text
release verification
 -> deployment rollback evidence
 -> release bundle manifest
 -> rollback approval handoff
 -> rollback SQL review gate
 -> production secret source contract
 -> production deployment runbook contract
 -> rollback approval record fixture
```

成熟度变化：

```text
发布窗口审批证据：增强
Node 后续 release window packet 输入：增强
secret value 边界：更明确
真实 rollback 执行：仍未授权
```

## 核心流程

```text
OpsEvidenceService.evidence()
 -> rollbackApprovalRecordFixture()
 -> /api/v1/ops/evidence 动态返回
 -> /contracts/rollback-approval-record.fixture.json 静态返回
 -> release / rollback / bundle / handoff / runbook 清单引用
 -> Node 后续只读消费，不触发 Java rollback
```

## 多代码引用讲解

### 1. 顶层 evidence 新增 approval record fixture

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:19) 在顶层新增：

```java
RollbackApprovalRecordFixture rollbackApprovalRecordFixture
```

这让 v61 的审批记录样本不只存在于静态 JSON，也能在动态 `/api/v1/ops/evidence` 中被 Node 读到。

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:158) 定义字段：

```java
reviewer
approvalTimestampPlaceholder
rollbackTarget
selectedMigrationDirection
noSecretValueBoundaries
rollbackExecutionAllowed
rollbackSqlExecutionAllowed
```

关键边界是：`nodeMayTriggerRollback=false`、`rollbackExecutionAllowed=false`、`rollbackSqlExecutionAllowed=false`。

### 2. Service 固定版本与 endpoint

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:45) 新增：

```java
java-rollback-approval-record-fixture.v1
/contracts/rollback-approval-record.fixture.json
```

这个 endpoint 被加入 health probe、read-only window、release verification、release bundle、deployment rollback、handoff、runbook 和 evidence endpoints。

### 3. 动态 evidence 组装审批记录样本

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:377) 的 `rollbackApprovalRecordFixture()` 固定：

```java
reviewer = "rollback-reviewer-placeholder"
approvalTimestampPlaceholder = "approval-timestamp-placeholder"
rollbackTarget = "release-tag-or-artifact-version-placeholder"
selectedMigrationDirection = "no-database-change"
```

这里故意使用 placeholder。真实 reviewer、审批时间和 rollback target 必须由人工在发布窗口外部确认，Java fixture 不自动推断。

### 4. 静态 fixture 样本

[rollback-approval-record.fixture.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/rollback-approval-record.fixture.json:1) 是本版新增样本。

核心记录：

```json
"approvalRecord": {
  "reviewer": "rollback-reviewer-placeholder",
  "approvalTimestampPlaceholder": "approval-timestamp-placeholder",
  "rollbackTarget": "release-tag-or-artifact-version-placeholder"
}
```

数据库迁移段落：

```json
"selectedDirection": "no-database-change",
"rollbackSqlExecutionAllowed": false,
"requiresProductionDatabase": false
```

secret 边界：

```json
"noSecretValueBoundaries": [
  "Record fixture stores metadata only",
  "Secret values must not be read by Java or Node when rendering this record"
]
```

### 5. 清单联动

[release-verification-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-verification-manifest.sample.json:56) 把 v61 endpoint 放进 HTTP smoke 预期。

[release-bundle-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-bundle-manifest.sample.json:19) 把它放入 bundle input。

[deployment-rollback-evidence.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/deployment-rollback-evidence.sample.json:75) 把 `rollback-approval-record-fixture` 加入人工确认项。

[rollback-approval-handoff.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/rollback-approval-handoff.sample.json:63) 把它加入回退审批交接清单。

[production-deployment-runbook-contract.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/production-deployment-runbook-contract.sample.json:77) 把它加入 runbook artifacts。

这些引用让后续 Node v173 可以只读汇总 Java v61，而不是猜测审批记录样本是否存在。

### 6. 测试锁边界

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:293) 验证动态字段：

```java
fixtureVersion = java-rollback-approval-record-fixture.v1
reviewer = rollback-reviewer-placeholder
rollbackExecutionAllowed = false
rollbackSqlExecutionAllowed = false
```

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:334) 验证 HTTP 动态 evidence。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:952) 验证静态 endpoint：

```text
GET /contracts/rollback-approval-record.fixture.json
```

并检查禁止事项：

```text
Executing Java rollback from this fixture
Reading production secret values from this fixture
Triggering Java rollback from Node
```

## 验证与归档

本版运行调试归档：

```text
c/61/解释/说明.md
c/61/图片/
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

v61 把“谁审批、何时审批、回退到哪里、迁移方向是什么、是否包含 secret value”这类人工审批记录固化成只读 fixture，让后续 Node release window packet 可以引用它，但仍然不授权任何真实回退执行。
