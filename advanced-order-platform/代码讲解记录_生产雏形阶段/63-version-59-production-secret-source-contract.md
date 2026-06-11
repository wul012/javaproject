> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 63-version-59-production-secret-source-contract

## 本版角色

v59 做的是 `production secret source contract`：把“生产密钥来自哪里、谁负责、多久复核、谁能消费、谁绝不能读取 secret value”固化成 Java 侧只读证据。

它不是密钥管理器，也不是读取生产配置的功能。它只给后续 Node v168 的 production environment preflight checklist 一个稳定输入。

## 项目进度

做到 v59 后，Java 订单平台的生产雏形又补上了一个真实生产化缺口：

```text
订单核心
 -> 失败事件治理
 -> 只读 ops evidence
 -> release verification
 -> deployment rollback evidence
 -> release bundle manifest
 -> rollback approval handoff
 -> rollback SQL review gate
 -> production secret source contract
```

当前成熟度变化：

```text
生产 readiness：继续增强
Node 消费边界：更清晰
真实 secret 安全：仍不接触 secret value
真实生产执行：仍未授权
```

## 核心流程

```text
OpsEvidenceService.evidence()
 -> 组装 ProductionSecretSourceContract
 -> /api/v1/ops/evidence 动态返回
 -> /contracts/production-secret-source-contract.sample.json 静态返回
 -> release / rollback / bundle / handoff 清单全部引用它
 -> Node 后续只读消费，不读取 secret value
```

## 多代码引用讲解

### 1. 返回结构新增独立 record

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:20) 在顶层新增：

```java
ProductionSecretSourceContract productionSecretSourceContract
```

这说明 secret source contract 已经是一等 evidence 字段，不再只是 `configuration-secret-source` 这样的字符串占位。

[OpsEvidenceResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceResponse.java:170) 定义了核心字段：

```java
contractVersion
contractEndpoint
contractMode
sourceTypes
selectedSourceType
secretManagerOwner
rotationOwner
reviewCadence
requiredConfirmationFields
secretValueBoundaries
nodeMayReadSecretValues
requiresProductionSecrets
```

重点是 `nodeMayReadSecretValues=false` 和 `requiresProductionSecrets=false`：本版只描述来源，不读取真实密钥。

### 2. Service 固定版本和 endpoint

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:50) 新增：

```java
java-production-secret-source-contract.v1
/contracts/production-secret-source-contract.sample.json
```

这让动态 evidence 和静态样本可以用同一套常量，不容易出现 Node 消费时 endpoint 拼错的问题。

### 3. evidence 主流程纳入新 contract

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:110) 在 `evidence()` 返回中加入：

```java
productionSecretSourceContract()
```

这表示每次读取 `/api/v1/ops/evidence` 都能看到同一份 secret source 边界。

### 4. contract 内容只记录元数据

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:389) 的 `productionSecretSourceContract()` 固定：

```java
selectedSourceType = "external-secret-manager"
secretManagerOwner = "platform-security-owner"
rotationOwner = "security-operations-owner"
reviewCadence = "quarterly-or-before-production-cutover"
```

它同时列出：

```java
secret-values-must-not-be-read
secret-values-must-not-be-embedded-in-static-contracts
node-may-render-checklist-only
```

这就是 v59 的安全边界：可以给控制台看“密钥来源确认项”，但不能让控制台碰密钥值。

### 5. 静态 contract 给 Node 稳定样本

[production-secret-source-contract.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/production-secret-source-contract.sample.json:1) 是本版新增的静态样本。

它的关键段落是：

```json
"secretSource": {
  "selectedSourceType": "external-secret-manager",
  "secretManagerOwner": "platform-security-owner",
  "sourceValueRecorded": false,
  "secretNamesRecorded": false
}
```

这比只写“configuration-secret-source”更具体：明确不记录 source value，也不记录 secret name。

[production-secret-source-contract.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/production-secret-source-contract.sample.json:70) 又强调：

```json
"nodeMayReadSecretValues": false
```

Node 可以渲染 checklist，但不能读取密钥值。

### 6. 其他证据清单引用新 contract

[release-verification-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-verification-manifest.sample.json:57) 把 HTTP smoke 预期加上 secret source contract。

[deployment-rollback-evidence.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/deployment-rollback-evidence.sample.json:65) 把 `production-secret-source-contract` 加入人工确认项。

[release-bundle-manifest.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/release-bundle-manifest.sample.json:20) 把它加入 release bundle 输入。

[rollback-approval-handoff.sample.json](D:/javaproj/advanced-order-platform/src/main/resources/static/contracts/rollback-approval-handoff.sample.json:31) 把它加入回退窗口人工审批交接。

这些引用让后续 Node v168 能从 Java evidence 中稳定找到 secret source 前置检查，不需要猜路径。

### 7. 测试锁住字段和边界

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:306) 验证动态对象：

```java
contractVersion = java-production-secret-source-contract.v1
nodeMayReadSecretValues = false
requiresProductionSecrets = false
```

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:337) 验证 HTTP 动态 evidence 能返回新字段。

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:831) 验证静态 endpoint：

```text
GET /contracts/production-secret-source-contract.sample.json
```

并检查禁止事项：

```text
Reading production secret values from this contract
Embedding secret values in static JSON samples
Triggering Java runtime configuration changes from Node
```

## 验证与归档

本版需要归档到：

```text
c/59/解释/说明.md
c/59/图片/*.png
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

v59 把 Java 生产环境密钥来源从“口头确认项”推进成“可被 Node 只读消费、但绝不读取 secret value 的正式 contract”。
