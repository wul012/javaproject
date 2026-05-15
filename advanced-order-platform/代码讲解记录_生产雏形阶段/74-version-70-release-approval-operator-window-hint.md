# 第七十版代码讲解：release approval rehearsal 只读 operator-window hint

## 本版角色

v70 按最新计划 `D:\nodeproj\orderops-node\docs\plans\v197-post-readiness-checkpoint-roadmap.md` 推进 Java 侧能力。计划要求 Java v70 给 Node v198 的 real-read window operator identity binding 增加上游只读 echo / schema hint，帮助 Node 判断真实只读窗口请求里的身份字段是否被 Java 响应看见。

本版仍然不认证 operator，不连接生产 IdP，不持久化 approval record，不写 approval ledger，不创建 approval decision，不触发 deployment、rollback 或 SQL。

## 项目进度

v66-v69 已经把 release approval rehearsal 从聚合入口推进到请求上下文、失败分类和 verification hint。v70 继续沿用同一个只读响应，只新增 `operatorWindowHint`，专门回显 Node v198 使用的 `x-orderops-*` 头。

这一步让 Java 和 Node 的真实只读窗口身份链路更容易对齐，但它不是生产认证，也不是生产窗口授权。

## 核心流程

```text
GET /api/v1/ops/release-approval-rehearsal
 -> OpsOverviewController.releaseApprovalRehearsal(...)
 -> OpsEvidenceService.releaseApprovalRehearsal(...)
 -> rehearsalRequestContext(...)
 -> rehearsalOperatorWindowHint(...)
 -> releaseApprovalRehearsalFailureTaxonomy(...)
 -> releaseApprovalVerificationHint(...)
 -> ReleaseApprovalRehearsalResponse.operatorWindowHint
```

## 多代码引用讲解

### 1. Controller 接收 Node v198 的窗口身份头

[OpsOverviewController.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java:31) 在原有 v67 三个只读 header 之外，新增四个 Node v198 header：

```java
@RequestHeader(name = "x-orderops-operator-id", required = false) String operatorWindowOperatorId,
@RequestHeader(name = "x-orderops-roles", required = false) String operatorWindowRoles,
@RequestHeader(name = "x-orderops-operator-verified", required = false) String operatorWindowVerifiedClaim,
@RequestHeader(name = "x-orderops-approval-correlation-id", required = false)
String operatorWindowApprovalCorrelationId
```

这些字段来自 Node v198 的 operator identity binding。Java 只读取并回显，不做身份校验，也不把它写入数据库。

### 2. Response 新增 operatorWindowHint 分组

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:6) 在 `requestContext` 后加入 `operatorWindowHint`：

```java
public record ReleaseApprovalRehearsalResponse(
        Instant sampledAt,
        String rehearsalVersion,
        String sourceEvidenceEndpoint,
        String rehearsalMode,
        boolean readOnly,
        boolean executionAllowed,
        RehearsalRequestContext requestContext,
        RehearsalOperatorWindowHint operatorWindowHint,
        RehearsalFailureTaxonomy failureTaxonomy,
        RehearsalVerificationHint verificationHint,
        ...
)
```

它放在 `failureTaxonomy` 前面，是因为它和 `requestContext` 一样属于“本次读取带来的上下文线索”，而不是执行结果。

### 3. operatorWindowHint 明确 echo 和安全边界

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:42) 定义了新 record：

```java
public record RehearsalOperatorWindowHint(
        String hintVersion,
        String operatorId,
        String operatorIdSource,
        String operatorRoles,
        String operatorRolesSource,
        String operatorVerifiedClaim,
        String operatorVerifiedClaimSource,
        String approvalCorrelationId,
        String approvalCorrelationIdSource,
        boolean operatorIdentityEchoed,
        boolean operatorRolesEchoed,
        boolean operatorVerifiedClaimEchoed,
        boolean approvalCorrelationEchoed,
        boolean operatorWindowContextComplete,
        boolean productionIdpVerifiedByJava,
        boolean persistedApprovalRecordByJava,
        boolean nodeMayTreatAsProductionIdentity,
        List<String> acceptedOperatorWindowHeaders,
        List<String> echoWarnings,
        List<String> nodeVerificationActions
)
```

这里最重要的是后三个布尔值：`productionIdpVerifiedByJava=false`、`persistedApprovalRecordByJava=false`、`nodeMayTreatAsProductionIdentity=false`。它们把“Java 看见 header”与“Java 完成生产认证”分开。

### 4. Service 先 normalize，再构造 hint

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:199) 先把四个窗口身份 header 做统一空白处理：

```java
String normalizedOperatorWindowOperatorId = normalizeHeaderValue(operatorWindowOperatorId);
String normalizedOperatorWindowRoles = normalizeHeaderValue(operatorWindowRoles);
String normalizedOperatorWindowVerifiedClaim = normalizeHeaderValue(operatorWindowVerifiedClaim);
String normalizedOperatorWindowApprovalCorrelationId =
        normalizeHeaderValue(operatorWindowApprovalCorrelationId);
```

随后构造 `operatorWindowHint`：

```java
ReleaseApprovalRehearsalResponse.RehearsalOperatorWindowHint operatorWindowHint =
        rehearsalOperatorWindowHint(
                normalizedOperatorWindowOperatorId,
                normalizedOperatorWindowRoles,
                normalizedOperatorWindowVerifiedClaim,
                normalizedOperatorWindowApprovalCorrelationId
        );
```

这种写法让 controller 和 service 的职责保持清楚：controller 只收 header，service 负责形成契约化响应。

### 5. 缺失字段会进入 echoWarnings

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:340) 对四个窗口身份字段分别补 warning：

```java
addMissingContextWarning(warnings, normalizedOperatorWindowOperatorId, "ORDEROPS_OPERATOR_ID_MISSING");
addMissingContextWarning(warnings, normalizedOperatorWindowRoles, "ORDEROPS_OPERATOR_ROLES_MISSING");
addMissingContextWarning(warnings, normalizedOperatorWindowVerifiedClaim, "ORDEROPS_OPERATOR_VERIFIED_CLAIM_MISSING");
addMissingContextWarning(warnings, normalizedOperatorWindowApprovalCorrelationId, "ORDEROPS_APPROVAL_CORRELATION_ID_MISSING");
```

这让 Node 能区分“Java 没启动或没返回”和“Java 返回了，但请求没有带完整 operator-window 字段”。

### 6. 完整性只表示 echo 完整，不表示生产认证

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:363) 使用四个 echo 布尔值计算 `operatorWindowContextComplete`：

```java
operatorIdentityEchoed
        && operatorRolesEchoed
        && operatorVerifiedClaimEchoed
        && approvalCorrelationEchoed
```

后面紧跟的三个 false 是边界字段：

```java
false,
false,
false,
```

它们分别对应 `productionIdpVerifiedByJava`、`persistedApprovalRecordByJava`、`nodeMayTreatAsProductionIdentity`。所以即使 `operatorWindowContextComplete=true`，Node 也不能把它解释成生产身份授权。

### 7. verificationHint 升级到 response schema v4

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:45) 将 schema version 升到 v4：

```java
static final String RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION =
        "java-release-approval-rehearsal-response-schema.v4";
```

因为顶层响应新增了 `operatorWindowHint`，Node 后续解析时可以用 v4 判断当前响应是否已经具备 v70 的窗口身份 echo 字段。

### 8. warningDigest 纳入 operatorWindowEchoWarnings

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:320) 计算 digest 时新增一行：

```java
line("operatorWindowEchoWarnings", operatorWindowHint.echoWarnings()),
```

同时 `warningDigestInputs` 也包含：

```java
"operatorWindowEchoWarnings",
```

这样当 Node v198 的四个窗口身份头从缺失变成完整时，warning digest 会发生变化，便于 Node 归档对比 closed-window 和 operator-window 两类读取。

### 9. 服务测试覆盖缺失和完整两种路径

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:806) 断言无 header 时返回占位和 warning：

```java
assertThat(rehearsal.operatorWindowHint().operatorId()).isEqualTo("orderops-operator-id-not-supplied");
assertThat(rehearsal.operatorWindowHint().echoWarnings())
        .containsExactly(
                "ORDEROPS_OPERATOR_ID_MISSING",
                "ORDEROPS_OPERATOR_ROLES_MISSING",
                "ORDEROPS_OPERATOR_VERIFIED_CLAIM_MISSING",
                "ORDEROPS_APPROVAL_CORRELATION_ID_MISSING"
        );
```

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:964) 断言完整 header 会被 trim 后回显：

```java
ReleaseApprovalRehearsalResponse headerBackedRehearsal = service.releaseApprovalRehearsal(
        " rehearsal-v67-001 ",
        " release-operator@example.test ",
        " audit-correlation-v67 ",
        " operator-198 ",
        " operator,auditor ",
        " true ",
        " approval-v198-operator-window "
);
```

随后测试要求：

```java
assertThat(headerBackedRehearsal.operatorWindowHint().operatorWindowContextComplete()).isTrue();
assertThat(headerBackedRehearsal.operatorWindowHint().productionIdpVerifiedByJava()).isFalse();
assertThat(headerBackedRehearsal.operatorWindowHint().nodeMayTreatAsProductionIdentity()).isFalse();
```

### 10. MVC 测试锁定 HTTP JSON 形状

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:878) 通过真实 MVC 请求验证 JSON 字段：

```java
.header("x-orderops-operator-id", "operator-198")
.header("x-orderops-roles", "operator,auditor")
.header("x-orderops-operator-verified", "true")
.header("x-orderops-approval-correlation-id", "approval-v198-operator-window")
```

并断言：

```java
.andExpect(jsonPath("$.operatorWindowHint.operatorWindowContextComplete").value(true))
.andExpect(jsonPath("$.operatorWindowHint.productionIdpVerifiedByJava").value(false))
.andExpect(jsonPath("$.operatorWindowHint.nodeMayTreatAsProductionIdentity").value(false))
```

这保证 Node 后续通过 HTTP 读取 Java 时，看到的是稳定的 JSON 契约，而不是仅有 service 单测覆盖。

## 本版边界

```text
readOnly=true
executionAllowed=false
operatorWindowHint.productionIdpVerifiedByJava=false
operatorWindowHint.persistedApprovalRecordByJava=false
operatorWindowHint.nodeMayTreatAsProductionIdentity=false
requestContext.approvalLedgerWritten=false
executionBoundaries.nodeMayWriteApprovalLedger=false
executionBoundaries.nodeMayTriggerDeployment=false
executionBoundaries.nodeMayTriggerRollback=false
executionBoundaries.nodeMayExecuteRollbackSql=false
```

## 一句话总结

v70 让 Java release approval rehearsal 能只读回显 Node v198 的窗口身份与审批关联字段，帮助跨项目真实只读窗口硬门槛继续落地；同时它非常明确地保持“不认证、不持久化、不写 ledger、不授权生产身份”。
