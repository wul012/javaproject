# 第六十八版代码讲解：release approval rehearsal 只读失败分类

## 本版角色

v68 按最新计划 `D:\nodeproj\orderops-node\docs\plans\v191-post-real-read-adapter-roadmap.md` 推进 Java 侧能力。计划要求 Java v68 在 release approval rehearsal 只读入口中补失败分类和 warning 字段，让 Node 后续能区分服务就绪、请求上下文不足、审计关联缺失等情况。

本版不新增写入口，不认证 operator，不持久化 request context，不写 approval ledger，也不触发 deployment、rollback 或 SQL。

## 项目进度

v66 已经提供 release approval rehearsal 聚合入口，v67 已经补 request id、operator identity 和 audit correlation 的只读上下文。v68 继续往“真实只读 adapter 可诊断”推进：不只是告诉 Node 字段缺了，还把这些缺口整理成稳定分类。

这让后续 Node v193 real-read adapter failure taxonomy 可以直接消费 Java 的分类字段，而不是靠字符串拼接或猜测 JSON 缺口。

## 核心流程

```text
GET /api/v1/ops/release-approval-rehearsal
 -> OpsOverviewController.releaseApprovalRehearsal(...)
 -> OpsEvidenceService.releaseApprovalRehearsal(...)
 -> evidence()
 -> rehearsalRequestContext(...)
 -> releaseApprovalRehearsalFailureTaxonomy(...)
 -> ReleaseApprovalRehearsalResponse.failureTaxonomy
```

## 多代码引用讲解

### 1. Response 增加 failureTaxonomy 分组

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:6) 在 `requestContext` 后加入 `failureTaxonomy`：

```java
public record ReleaseApprovalRehearsalResponse(
        Instant sampledAt,
        String rehearsalVersion,
        String sourceEvidenceEndpoint,
        String rehearsalMode,
        boolean readOnly,
        boolean executionAllowed,
        RehearsalRequestContext requestContext,
        RehearsalFailureTaxonomy failureTaxonomy,
        ReleaseApprovalInputs releaseApprovalInputs,
        LiveSignals liveSignals,
        ExecutionBoundaries executionBoundaries,
        List<String> rehearsalBlockers,
        List<String> requiredNodeEnvironment,
        List<String> nextEvidenceActions
)
```

顺序上它紧跟 `requestContext`，因为失败分类主要解释本次读取上下文和只读上游状态，不属于审批输入，也不属于执行边界。

### 2. taxonomy 字段明确三类前置条件

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:40) 定义了 `RehearsalFailureTaxonomy`：

```java
public record RehearsalFailureTaxonomy(
        String taxonomyVersion,
        String upstreamReadiness,
        String authContextReadiness,
        String auditCorrelationReadiness,
        boolean javaReadOnlyUpstreamReady,
        boolean authContextComplete,
        boolean auditCorrelationPresent,
        boolean retryableByReadOnlyAdapter,
        boolean writeActionRequired,
        List<String> failureCategories,
        List<String> taxonomyWarnings
)
```

这里分成三条主线：

```text
upstreamReadiness -> Java 只读上游是否适合被 Node real-read adapter 读取
authContextReadiness -> request id + operator identity 是否完整
auditCorrelationReadiness -> audit correlation id 是否存在
```

`writeActionRequired=false` 是本版最关键的边界字段之一：分类结果只帮助定位问题，不要求任何写动作来“修复”当前 rehearsal。

### 3. Service 统一 normalize 后再构造上下文和分类

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:178) 在入口中只做一次 header 规范化：

```java
OpsEvidenceResponse evidence = evidence();
String normalizedRequestId = normalizeHeaderValue(requestId);
String normalizedOperatorIdentity = normalizeHeaderValue(operatorIdentity);
String normalizedAuditCorrelationId = normalizeHeaderValue(auditCorrelationId);
```

然后同一组 normalized 值同时传给 request context 和 failure taxonomy：

```java
rehearsalRequestContext(
        normalizedRequestId,
        normalizedOperatorIdentity,
        normalizedAuditCorrelationId
),
releaseApprovalRehearsalFailureTaxonomy(
        evidence,
        normalizedRequestId,
        normalizedOperatorIdentity,
        normalizedAuditCorrelationId
),
```

这样可以避免两个分组对空白 header 的理解不一致。例如 `"  "` 在两个分组里都会被视为未提供。

### 4. upstream readiness 来自现有 evidence，不另起一套判断

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:214) 的分类方法先判断 Java 只读上游是否 ready：

```java
boolean upstreamReady = evidence.readOnlyWindow().readyForReadOnlyLiveProbe()
        && evidence.healthProbe().liveProbeRequiredForPass()
        && !evidence.healthProbe().staticSampleOnly()
        && evidence.readOnly();
```

这段没有新建配置，也没有引入外部探测，而是复用 `OpsEvidenceResponse` 已经暴露的只读窗口和 health probe 语义。好处是 `/api/v1/ops/evidence` 与 `/api/v1/ops/release-approval-rehearsal` 不会出现两套互相打架的 readiness 解释。

### 5. auth 与 audit 分类只看上下文完整性

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:219) 继续保持轻量判断：

```java
boolean authContextComplete = normalizedRequestId != null && normalizedOperatorIdentity != null;
boolean auditCorrelationPresent = normalizedAuditCorrelationId != null;
```

这里故意不做身份认证。`authContextComplete=true` 只表示“Node/operator 提供了 request id 与 operator identity 线索”，不表示 Java 已经信任这个 operator。

### 6. 分类输出稳定给 Node 消费

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:223) 生成分类和 warning：

```java
if (!authContextComplete) {
    failureCategories.add("AUTH_CONTEXT_WARNING");
    taxonomyWarnings.add("REQUEST_ID_OR_OPERATOR_IDENTITY_MISSING");
}
if (!auditCorrelationPresent) {
    failureCategories.add("AUDIT_CORRELATION_WARNING");
    taxonomyWarnings.add("AUDIT_CORRELATION_ID_MISSING");
}
failureCategories.add("READ_ONLY_EXECUTION_BLOCKED");
taxonomyWarnings.add("REHEARSAL_REMAINS_READ_ONLY");
```

`READ_ONLY_EXECUTION_BLOCKED` 永远保留，是为了提醒 Node：即使上下文齐全，这个 endpoint 也只是 rehearsal evidence，不是审批执行入口。

### 7. READY/WARNING 只表示分类状态

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:260) 用小 helper 固定状态字符串：

```java
private String readinessStatus(boolean ready) {
    if (ready) {
        return "READY";
    }
    return "WARNING";
}
```

这里没有 `FAILED`，是因为 Java 当前只负责表达自身只读 evidence 和上下文缺口；连接拒绝、超时、invalid JSON 等真实 adapter 失败会由 Node v193 在调用层分类。

### 8. 测试锁定默认缺失和 header 完整两条路径

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:809) 验证默认无 header 时的分类：

```java
assertThat(rehearsal.failureTaxonomy().taxonomyVersion())
        .isEqualTo("java-release-approval-rehearsal-failure-taxonomy.v1");
assertThat(rehearsal.failureTaxonomy().authContextReadiness()).isEqualTo("WARNING");
assertThat(rehearsal.failureTaxonomy().auditCorrelationReadiness()).isEqualTo("WARNING");
assertThat(rehearsal.failureTaxonomy().failureCategories())
        .containsExactly(
                "AUTH_CONTEXT_WARNING",
                "AUDIT_CORRELATION_WARNING",
                "READ_ONLY_EXECUTION_BLOCKED"
        );
```

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:904) 验证 header 齐全时 warning 消失：

```java
assertThat(headerBackedRehearsal.failureTaxonomy().authContextReadiness()).isEqualTo("READY");
assertThat(headerBackedRehearsal.failureTaxonomy().auditCorrelationReadiness()).isEqualTo("READY");
assertThat(headerBackedRehearsal.failureTaxonomy().failureCategories())
        .containsExactly("READ_ONLY_EXECUTION_BLOCKED");
```

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:913) 从 HTTP JSON 层验证 `failureTaxonomy.*` 字段，确保 Node 后续不是只依赖 Java service 内部测试。

## 验证与归档

本版已先通过聚焦测试：

```text
mvn -Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests -DargLine="-XX:TieredStopAtLevel=1 -Xmx512m" test
```

运行记录、截图和清理记录写入：

```text
D:\javaproj\advanced-order-platform\c\68\解释\说明.md
D:\javaproj\advanced-order-platform\c\68\图片
```

## 成熟度变化

v68 后，Java release approval rehearsal 不只是返回上下文 warning，而是开始提供可被 Node 直接消费的失败分类。它让跨项目真实只读 adapter 的诊断边界更清楚：Java 负责表达自身 readiness 和上下文缺口，Node 负责网络、连接、JSON、危险写信号等 adapter 层失败分类。

## 一句话总结

v68 给 release approval rehearsal 补上只读 failure taxonomy，让 Node 后续能区分 upstream readiness、auth context 和 audit correlation warning，同时 Java 仍不认证、不持久化、不写 ledger、不授权任何执行动作。
