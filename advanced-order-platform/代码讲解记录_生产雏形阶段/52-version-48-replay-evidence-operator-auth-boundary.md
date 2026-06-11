> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# 第四十八版代码讲解：replay evidence operator/auth boundary

本版目标是增强 v47 的 replay evidence index。

v47 已经把 Java 失败事件重放链路的 live endpoint、静态样本、审计身份字段和执行安全规则汇总成只读目录。v48 继续靠近生产雏形阶段：把当前 operator/auth 边界也放进这个目录，让上游控制面能明确区分“Java 已有 Header 身份演练和动作级角色门禁”与“还没有完整生产级认证”。

## 本版所处项目进度

最新计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v103-production-auth-audit-roadmap.md
```

计划里对 Java v48 的要求是：

```text
增强 replay evidence index，补 operator/auth boundary 说明字段。
```

这个目标合理，因为 Node v104-v106 已经在推进 access guard、operator identity 和 file audit restart evidence，Java 侧需要提供更清楚的上游边界证据，方便后续 Node v107 做 readiness summary v4。

## 响应模型新增边界字段

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayEvidenceIndexResponse.java
```

v48 在响应模型里新增了：

```java
OperatorAuthBoundary operatorAuthBoundary
```

对应 record 是：

```java
public record OperatorAuthBoundary(
        String identitySource,
        List<String> requiredHeaders,
        boolean anonymousAllowed,
        boolean javaAuthenticatesCredentials,
        String enforcementMode,
        List<String> globalAllowedRoles,
        Map<FailedEventOperatorAction, List<String>> allowedRolesByAction,
        List<String> normalizationRules,
        List<String> productionAuthGaps
) {
}
```

这里最关键的是两个布尔值：

```text
anonymousAllowed=false
javaAuthenticatesCredentials=false
```

前者说明失败事件写操作不能匿名；后者说明当前 Java 只校验 Header 派生身份和角色策略，还没有校验 JWT、session 或外部身份系统签名。

## evidenceVersion 升级到 v2

改动文件：

```text
src/main/java/com/codexdemo/orderplatform/notification/FailedEventReplayEvidenceIndexService.java
```

版本号从 v1 升为：

```java
static final String EVIDENCE_VERSION = "failed-event-replay-evidence-index.v2";
```

这次不是只补文档，而是改变了接口契约的响应结构，所以升版本是合理的。旧字段仍保留，新字段只增强说明能力。

## operator 身份来源

v48 直接引用现有操作员上下文解析器里的 Header 常量：

```java
List.of(
        FailedEventOperatorContextResolver.OPERATOR_ID_HEADER,
        FailedEventOperatorContextResolver.OPERATOR_ROLE_HEADER
)
```

这对应实际代码里的常量：

```java
public static final String OPERATOR_ID_HEADER = "X-Operator-Id";
public static final String OPERATOR_ROLE_HEADER = "X-Operator-Role";
```

也就是说，Java 当前的 operator identity 来源不是登录态，而是：

```text
X-Operator-Id
X-Operator-Role
```

这正是 v48 要说明清楚的生产边界。

## 动作级角色策略

v48 不是手写一份角色表，而是复用已有解析器：

```java
operatorContextResolver.allowedRoles()
operatorContextResolver.allowedRolesByAction()
```

`allowedRolesByAction()` 会根据 `FailedEventOperatorAction` 枚举逐个生成动作级角色表：

```java
for (FailedEventOperatorAction action : FailedEventOperatorAction.values()) {
    rolesByAction.put(action, normalizeRoles(failedEventReplayProperties.rolesFor(action)));
}
```

当前动作枚举是：

```java
MANAGE_FAILED_EVENT,
REQUEST_REPLAY_APPROVAL,
REVIEW_REPLAY_APPROVAL,
REPLAY_FAILED_EVENT
```

这样 `operatorAuthBoundary.allowedRolesByAction` 返回的是 Java 真实使用的角色策略，不是 README 里的重复描述。

## 生产认证缺口

v48 新增的 `productionAuthGaps` 很重要：

```java
List.of(
        "Java does not validate JWT, session cookies, or external identity-provider signatures yet.",
        "Upstream gateway or control plane must prevent client-side spoofing of X-Operator-* headers.",
        "Header-derived identity is suitable for rehearsal and audit evidence, not final production authentication.",
        "Real replay still requires approval status, action role, non-blank reason, and RabbitMQ outbox readiness."
)
```

这不是在新增限制，而是在把已有边界写成机器可读证据：

```text
Java 有角色门禁
Java 有 Header 必填
Java 有动作级策略
Java 没有完整认证
Header 防伪必须由上游保证
```

对于生产 readiness 来说，这比只说“有 operatorId/operatorRole 字段”更清楚。

## 安全规则也同步补强

v48 在执行安全规则里新增：

```java
"OPERATOR_HEADERS_ARE_REQUIRED_BUT_NOT_CREDENTIAL_AUTHENTICATION",
"UPSTREAM_MUST_PREVENT_X_OPERATOR_HEADER_SPOOFING",
```

这样控制面读取 index 时，不需要再从说明文字里推断 auth 风险，而是能直接看到两条机器可读规则。

## 测试覆盖

改动文件：

```text
src/test/java/com/codexdemo/orderplatform/FailedEventReplayEvidenceIndexIntegrationTests.java
```

测试把证据版本锁定为 v2：

```java
.andExpect(jsonPath("$.evidenceVersion").value("failed-event-replay-evidence-index.v2"))
```

同时锁定关键边界：

```java
.andExpect(jsonPath("$.operatorAuthBoundary.requiredHeaders", hasItem("X-Operator-Id")))
.andExpect(jsonPath("$.operatorAuthBoundary.requiredHeaders", hasItem("X-Operator-Role")))
.andExpect(jsonPath("$.operatorAuthBoundary.javaAuthenticatesCredentials").value(false))
```

还验证动作角色策略确实出现在响应里：

```java
.andExpect(jsonPath("$.operatorAuthBoundary.allowedRolesByAction.REVIEW_REPLAY_APPROVAL",
        hasItem("SRE")))
.andExpect(jsonPath("$.operatorAuthBoundary.allowedRolesByAction.REPLAY_FAILED_EVENT",
        hasItem("SYSTEM")))
```

这能防止以后误删 Header 说明、误改版本号或丢失动作角色证据。

## 一句话总结

v48 把 replay evidence index 从“证据目录”推进成“证据目录 + operator/auth 边界说明”，让 Java 在生产雏形阶段更诚实地暴露自己已有的服务端门禁和还没完成的真实认证缺口。
