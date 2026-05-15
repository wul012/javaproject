# 第六十七版代码讲解：release approval rehearsal 只读请求上下文

## 本版角色

v67 按最新计划 `D:\nodeproj\orderops-node\docs\plans\v185-post-real-read-rehearsal-roadmap.md` 推进 Java 侧真实能力落地。它没有继续新增横向 fixture，而是在 v66 的动态只读聚合入口上补充 request/audit/correlation 前置证据：

```text
GET /api/v1/ops/release-approval-rehearsal
X-Rehearsal-Request-Id
X-Operator-Identity
X-Audit-Correlation-Id
```

本版仍然不创建 approval decision，不写 approval ledger，不接真实认证系统，不写持久化审计。

## 项目进度

v66 已经把 release approval rehearsal 从多个静态证据聚合成一个 live 只读端点。v67 往“真实读取窗口”再推进一步：让 Node 或 operator 可以带上本次演练的 request id、操作者身份线索和 audit correlation id，Java 只读回显并标注来源。

这让后续 Node v191 real HTTP read adapter rehearsal 可以验证 Java 是否具备真实调用时需要的上下文字段，同时又明确保留生产认证缺口：

```text
Java 目前不认证 X-Operator-Identity
Java 不把 requestContext 写入数据库
Java 不写 approval ledger
Java 不依赖生产 IdP
```

## 核心流程

```text
Node / operator
 -> GET /api/v1/ops/release-approval-rehearsal
 -> 可选携带 X-Rehearsal-Request-Id / X-Operator-Identity / X-Audit-Correlation-Id
 -> OpsOverviewController.releaseApprovalRehearsal(...)
 -> OpsEvidenceService.releaseApprovalRehearsal(...)
 -> rehearsalRequestContext(...)
 -> ReleaseApprovalRehearsalResponse.requestContext
```

## 多代码引用讲解

### 1. Controller 只接收只读 header

[OpsOverviewController.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java:31) 的 GET 入口新增三个可选 header：

```java
@GetMapping("/release-approval-rehearsal")
public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
        @RequestHeader(name = "X-Rehearsal-Request-Id", required = false) String requestId,
        @RequestHeader(name = "X-Operator-Identity", required = false) String operatorIdentity,
        @RequestHeader(name = "X-Audit-Correlation-Id", required = false) String auditCorrelationId
) {
    return opsEvidenceService.releaseApprovalRehearsal(requestId, operatorIdentity, auditCorrelationId);
}
```

这里仍然只有 GET，没有 POST/PUT，也没有调用任何审批或审计写接口。header 只是 evidence input，不是认证结果。

### 2. Response 增加 requestContext 分组

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:6) 在顶层加入 `requestContext`：

```java
public record ReleaseApprovalRehearsalResponse(
        Instant sampledAt,
        String rehearsalVersion,
        String sourceEvidenceEndpoint,
        String rehearsalMode,
        boolean readOnly,
        boolean executionAllowed,
        RehearsalRequestContext requestContext,
        ReleaseApprovalInputs releaseApprovalInputs,
        LiveSignals liveSignals,
        ExecutionBoundaries executionBoundaries,
        List<String> rehearsalBlockers,
        List<String> requiredNodeEnvironment,
        List<String> nextEvidenceActions
)
```

它被放在 `releaseApprovalInputs` 之前，是因为 requestContext 描述“这次读取是谁发起、如何关联审计”，而不是 release approval 的业务输入本身。

### 3. requestContext 明确生产缺口

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:22) 定义了上下文字段：

```java
public record RehearsalRequestContext(
        String contextVersion,
        String requestId,
        String requestIdSource,
        String operatorIdentity,
        String operatorIdentitySource,
        String auditCorrelationId,
        String auditCorrelationSource,
        boolean operatorAuthenticatedByJava,
        boolean persistedByJava,
        boolean approvalLedgerWritten,
        boolean requiresProductionIdentityProvider,
        List<String> acceptedReadOnlyHeaders,
        List<String> contextWarnings
)
```

`contextVersion=java-release-approval-rehearsal-context.v1` 给 Node 后续 adapter 一个稳定解析版本。关键不是多几个字符串，而是四个布尔边界：`operatorAuthenticatedByJava=false`、`persistedByJava=false`、`approvalLedgerWritten=false`、`requiresProductionIdentityProvider=false`。这防止 Node 把这个只读回显误解成生产认证或审计入账。

### 4. Service 继续复用 v66 的 evidence 聚合

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:170) 保留无参方法，兼容 service 测试和旧调用：

```java
@Transactional(readOnly = true)
public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal() {
    return releaseApprovalRehearsal(null, null, null);
}
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:175) 的新重载仍然先调用 `evidence()`：

```java
OpsEvidenceResponse evidence = evidence();
return new ReleaseApprovalRehearsalResponse(
        evidence.sampledAt(),
        RELEASE_APPROVAL_REHEARSAL_VERSION,
        "/api/v1/ops/evidence",
        "READ_ONLY_RELEASE_APPROVAL_REHEARSAL",
        true,
        false,
        rehearsalRequestContext(requestId, operatorIdentity, auditCorrelationId),
        releaseApprovalInputs(evidence),
        liveSignals(evidence),
        executionBoundaries(),
        releaseApprovalRehearsalBlockers(evidence),
        evidence.readOnlyWindow().requiredNodeEnvironment(),
        releaseApprovalNextEvidenceActions()
);
```

这保持了 v66 的质量约束：live replay/outbox 信号和执行边界仍来自同一套 `OpsEvidenceResponse`，避免两个视图分叉。

### 5. header 规范化只做 trim，不做认证

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:196) 构造上下文：

```java
String normalizedRequestId = normalizeHeaderValue(requestId);
String normalizedOperatorIdentity = normalizeHeaderValue(operatorIdentity);
String normalizedAuditCorrelationId = normalizeHeaderValue(auditCorrelationId);

List<String> warnings = new ArrayList<>();
addMissingContextWarning(warnings, normalizedRequestId, "REHEARSAL_REQUEST_ID_MISSING");
addMissingContextWarning(warnings, normalizedOperatorIdentity, "OPERATOR_IDENTITY_MISSING");
addMissingContextWarning(warnings, normalizedAuditCorrelationId, "AUDIT_CORRELATION_ID_MISSING");
```

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:240) 的 `normalizeHeaderValue` 只处理空白：

```java
private String normalizeHeaderValue(String value) {
    if (value == null || value.isBlank()) {
        return null;
    }
    return value.trim();
}
```

这点很重要：本版不是认证中间件。它只是让真实读取窗口先拥有稳定上下文字段，后续要接入生产身份时再替换来源和校验方式。

### 6. 无 header 时也返回稳定结构

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:210) 对缺失值使用占位和 warning：

```java
return new ReleaseApprovalRehearsalResponse.RehearsalRequestContext(
        RELEASE_APPROVAL_REHEARSAL_CONTEXT_VERSION,
        valueOrPlaceholder(normalizedRequestId, "rehearsal-request-id-not-supplied"),
        sourceFor(normalizedRequestId, "X-Rehearsal-Request-Id"),
        valueOrPlaceholder(normalizedOperatorIdentity, "operator-identity-not-supplied"),
        sourceFor(normalizedOperatorIdentity, "X-Operator-Identity"),
        valueOrPlaceholder(normalizedAuditCorrelationId, "audit-correlation-id-not-supplied"),
        sourceFor(normalizedAuditCorrelationId, "X-Audit-Correlation-Id"),
        false,
        false,
        false,
        false,
        List.of(...),
        List.copyOf(warnings)
);
```

这让 Node 可以在未传 header 的默认关闭模式下也拿到稳定 JSON，不会因为字段缺失导致 adapter rehearsal 解析失败。

### 7. 测试锁定“只读回显”和“不写 ledger”

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:784) 先验证默认占位：

```java
assertThat(rehearsal.requestContext().requestId()).isEqualTo("rehearsal-request-id-not-supplied");
assertThat(rehearsal.requestContext().requestIdSource()).isEqualTo("NOT_SUPPLIED");
assertThat(rehearsal.requestContext().approvalLedgerWritten()).isFalse();
```

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:850) 再验证 header 被 trim 后回显：

```java
ReleaseApprovalRehearsalResponse headerBackedRehearsal = service.releaseApprovalRehearsal(
        " rehearsal-v67-001 ",
        " release-operator@example.test ",
        " audit-correlation-v67 "
);
assertThat(headerBackedRehearsal.requestContext().operatorIdentity())
        .isEqualTo("release-operator@example.test");
assertThat(headerBackedRehearsal.executionAllowed()).isFalse();
```

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:877) 从 MVC 层验证真实 JSON 中的字段，确保 Node 以后能按 HTTP 读取。

## 验证与归档

本版验证重点：

```text
mvn -Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests test
后续继续运行 non-Docker regression、package、HTTP smoke
```

运行记录、截图和清理记录写入：

```text
D:\javaproj\advanced-order-platform\c\67\解释\说明.md
D:\javaproj\advanced-order-platform\c\67\图片
```

## 成熟度变化

v67 后，Java 侧 release approval rehearsal 不只是给 Node 一个聚合结果，还开始表达“本次读取请求自身如何被标识和关联”。这比静态 fixture 更接近真实控制面读取，但仍然诚实地承认：当前还没有生产认证、没有审计持久化、没有 approval ledger 写入。

## 一句话总结

v67 给 release approval rehearsal 补上 request id、operator identity 和 audit correlation 的只读上下文，让后续 Node real-read adapter 有稳定字段可读，但 Java 仍不认证、不持久化、不写 ledger、不授权任何执行动作。
