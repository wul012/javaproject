> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 64-version-60-production-deployment-runbook-contract

## 本版角色

v60 做的是 `production deployment runbook contract`：把生产部署窗口 owner、rollback approver、数据库迁移方向和密钥来源确认固化成 Java 侧只读部署 runbook。

它不是自动部署工具，不执行 SQL，也不连接生产库。它的价值是给后续 Node v170 的 deployment evidence intake gate 一个稳定、可验证的 Java 上游输入。

## 项目进度

做到 v60 后，Java 的生产雏形从“环境前置检查”继续前移到“部署证据入口”：

```text
release verification
 -> deployment rollback evidence
 -> release bundle manifest
 -> rollback approval handoff
 -> rollback SQL review gate
 -> production secret source contract
 -> production deployment runbook contract
```

成熟度变化：

```text
部署窗口证据：增强
回退审批入口：增强
Node 消费边界：更清晰
真实生产执行：仍未授权
```

## 核心流程

```text
OpsEvidenceService.evidence()
 -> productionDeploymentRunbookContract()
 -> /api/v1/ops/evidence 动态返回
 -> /contracts/production-deployment-runbook-contract.sample.json 静态返回
 -> release / rollback / bundle / handoff 清单引用
 -> Node 后续只读消费，不触发部署或回退
```

## 多代码引用讲解

### 1. 顶层 evidence 新增 runbook contract

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:21) 在顶层新增：

```java
ProductionDeploymentRunbookContract productionDeploymentRunbookContract
```

这意味着 v60 的 runbook 不只是静态样本，也会出现在动态 `/api/v1/ops/evidence` 中。

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:190) 定义字段：

```java
deploymentWindowOwner
rollbackApprover
databaseMigrationDirectionOptions
selectedDatabaseMigrationDirection
secretSourceConfirmation
nodeMayTriggerDeployment
nodeMayTriggerRollback
sqlExecutionAllowed
```

关键边界是：`nodeMayTriggerDeployment=false`、`nodeMayTriggerRollback=false`、`sqlExecutionAllowed=false`。

### 2. Service 固定版本与 endpoint

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:55) 新增：

```java
java-production-deployment-runbook-contract.v1
/contracts/production-deployment-runbook-contract.sample.json
```

这个 endpoint 被加入 health probe、read-only window、release verification、release bundle 和 evidence endpoints。

### 3. 动态 evidence 组装 runbook

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:442) 的 `productionDeploymentRunbookContract()` 固定：

```java
deploymentWindowOwner = "release-window-owner"
rollbackApprover = "rollback-approval-owner"
selectedDatabaseMigrationDirection = "no-database-change"
secretSourceConfirmation = PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
```

它引用 v59 的 secret source contract，而不是读取 secret value。

### 4. 静态 runbook 样本

[production-deployment-runbook-contract.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/production-deployment-runbook-contract.sample.json:1) 是本版新增 contract。

关键段落：

```json
"deploymentWindow": {
  "owner": "release-window-owner",
  "rollbackApprover": "rollback-approval-owner",
  "nodeMayTriggerDeployment": false
}
```

数据库迁移段落：

```json
"selectedDirection": "no-database-change",
"rollbackSqlExecutionAllowed": false,
"requiresProductionDatabase": false
```

这说明它只是部署窗口证据，不执行迁移、不执行回滚 SQL。

### 5. 清单联动

[release-verification-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-verification-manifest.sample.json:58) 把 v60 endpoint 放进 HTTP smoke 预期。

[deployment-rollback-evidence.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/deployment-rollback-evidence.sample.json:65) 把 `production-deployment-runbook-contract` 加入人工确认项。

[release-bundle-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-bundle-manifest.sample.json:21) 把它放入 bundle input。

[rollback-approval-handoff.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/rollback-approval-handoff.sample.json:50) 把它放入回退审批交接清单。

这些引用让 Node v170 可以只读消费 Java v60，而不是猜测部署 runbook 是否存在。

### 6. 测试锁边界

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:356) 验证动态字段：

```java
contractVersion = java-production-deployment-runbook-contract.v1
deploymentWindowOwner = release-window-owner
rollbackApprover = rollback-approval-owner
nodeMayTriggerDeployment = false
```

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:368) 验证 HTTP 动态 evidence。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:931) 验证静态 endpoint：

```text
GET /contracts/production-deployment-runbook-contract.sample.json
```

并检查禁止事项：

```text
Executing Java deployment from this runbook contract
Executing rollback SQL from this runbook contract
Reading production secret values from this runbook contract
```

## 验证与归档

本版运行调试归档：

```text
c/60/解释/说明.md
c/60/图片/*.png
```

验证闭环包含：

```text
JSON parse
focused Maven tests
non-Docker regression tests
maven package
HTTP smoke
```

## 一句话总结

v60 把 Java 的生产部署窗口信息从“后续计划项”推进成“Node 可消费但不可执行的只读 runbook contract”。
