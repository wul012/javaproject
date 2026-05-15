# 第六十九版代码讲解：release approval rehearsal 只读验证提示

## 本版角色

v69 按最新计划 `D:\nodeproj\orderops-node\docs\plans\v194-post-real-read-archive-roadmap.md` 推进 Java 侧能力。计划要求 Java v69 增强 release approval rehearsal 的只读 verification hint，给 Node v196/v197 提供更稳定的上游结果校验字段。

本版仍然不认证 operator，不持久化 request context，不写 approval ledger，不创建 approval decision，不触发 deployment、rollback 或 SQL。

## 项目进度

v66 提供 release approval rehearsal 聚合入口，v67 提供 request context，v68 提供 failure taxonomy。v69 继续把这个只读入口从“可读、可分类”推进到“可归档校验”：Node 后续导入人工窗口结果时，可以先校验 response schema、warning digest 和 no-ledger proof。

这一步不是新业务流程，而是给跨项目证据链增加稳定校验锚点。

## 核心流程

```text
GET /api/v1/ops/release-approval-rehearsal
 -> OpsEvidenceService.releaseApprovalRehearsal(...)
 -> rehearsalRequestContext(...)
 -> releaseApprovalRehearsalFailureTaxonomy(...)
 -> executionBoundaries()
 -> releaseApprovalVerificationHint(...)
 -> ReleaseApprovalRehearsalResponse.verificationHint
```

## 多代码引用讲解

### 1. Response 增加 verificationHint 分组

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:6) 在 `failureTaxonomy` 后加入 `verificationHint`：

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
        RehearsalVerificationHint verificationHint,
        ReleaseApprovalInputs releaseApprovalInputs,
        LiveSignals liveSignals,
        ExecutionBoundaries executionBoundaries,
        List<String> rehearsalBlockers,
        List<String> requiredNodeEnvironment,
        List<String> nextEvidenceActions
)
```

它放在 `releaseApprovalInputs` 前面，是因为它描述的是响应本身如何被 Node 校验，而不是 release approval 的输入证据。

### 2. verificationHint 固定 schema、digest 和 proof

[ReleaseApprovalRehearsalResponse.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java:56) 定义新 record：

```java
public record RehearsalVerificationHint(
        String hintVersion,
        String responseSchemaVersion,
        String warningDigest,
        String noLedgerWriteProof,
        boolean noLedgerWriteProved,
        boolean nodeMayTreatAsProductionAuthorization,
        List<String> schemaFields,
        List<String> warningDigestInputs,
        List<String> proofClaims,
        List<String> nodeVerificationActions
)
```

`responseSchemaVersion=java-release-approval-rehearsal-response-schema.v3` 让 Node 可以判断当前 Java 响应是否仍是 v69 预期结构。`nodeMayTreatAsProductionAuthorization=false` 则防止校验通过被误读为生产操作授权。

### 3. Service 先构造局部对象，再生成 hint

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:184) 先把上下文、分类和执行边界都构造为局部变量：

```java
ReleaseApprovalRehearsalResponse.RehearsalRequestContext requestContext = rehearsalRequestContext(...);
ReleaseApprovalRehearsalResponse.RehearsalFailureTaxonomy failureTaxonomy =
        releaseApprovalRehearsalFailureTaxonomy(...);
ReleaseApprovalRehearsalResponse.ExecutionBoundaries executionBoundaries = executionBoundaries();
```

随后 response 同时使用这些对象：

```java
requestContext,
failureTaxonomy,
releaseApprovalVerificationHint(requestContext, failureTaxonomy, executionBoundaries),
releaseApprovalInputs(evidence),
liveSignals(evidence),
executionBoundaries,
```

这样 `verificationHint` 不是重新猜测字段，而是直接从最终响应对象的组成部分生成，降低“proof 说不写 ledger，但 response 边界变了”的分叉风险。

### 4. hintVersion 与 schemaVersion 是显式常量

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:35) 增加两个版本常量：

```java
static final String RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION =
        "java-release-approval-rehearsal-verification-hint.v1";

static final String RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION =
        "java-release-approval-rehearsal-response-schema.v3";
```

hint version 表示 verification hint 自己的契约版本；schema version 表示整个 rehearsal response 的解析形状。Node 后续可以先校验 schema，再决定是否导入窗口结果。

### 5. warningDigest 只覆盖稳定校验字段

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:270) 计算 digest：

```java
return digest(List.of(
        line("digestKind", "releaseApprovalRehearsalWarning"),
        line("hintVersion", RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION),
        line("responseSchemaVersion", RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION),
        line("contextWarnings", requestContext.contextWarnings()),
        line("failureCategories", failureTaxonomy.failureCategories()),
        line("taxonomyWarnings", failureTaxonomy.taxonomyWarnings()),
        line("executionAllowed", false),
        line("approvalLedgerWritten", requestContext.approvalLedgerWritten()),
        line("nodeMayWriteApprovalLedger", executionBoundaries.nodeMayWriteApprovalLedger())
));
```

这里故意不包含 `sampledAt`，也不包含实时 replay/outbox 数量，因为 warning digest 的作用是校验上下文 warning 和 no-ledger 边界是否漂移，不是给整个 live response 做内容哈希。

### 6. SHA-256 格式沿用项目既有风格

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:324) 使用项目已有的 `sha256:` 风格：

```java
private String digest(List<String> lines) {
    String canonical = String.join("\n", lines) + "\n";
    try {
        byte[] bytes = MessageDigest.getInstance("SHA-256")
                .digest(canonical.getBytes(StandardCharsets.UTF_8));
        return "sha256:" + HexFormat.of().formatHex(bytes);
    } catch (NoSuchAlgorithmException ex) {
        throw new IllegalStateException("SHA-256 digest algorithm is not available", ex);
    }
}
```

这和 replay approval / execution contract 的 digest 风格一致，Node 不需要处理另一种摘要格式。

### 7. no-ledger proof 由响应字段共同证明

[OpsEvidenceService.java](D:/javaproj/advanced-order-platform/src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java:222) 构造 proof claims：

```java
List<String> proofClaims = List.of(
        "executionAllowed=false",
        "requestContext.approvalLedgerWritten=false",
        "executionBoundaries.nodeMayCreateApprovalDecision=false",
        "executionBoundaries.nodeMayWriteApprovalLedger=false",
        "executionBoundaries.nodeMayTriggerDeployment=false",
        "executionBoundaries.nodeMayTriggerRollback=false",
        "executionBoundaries.nodeMayExecuteRollbackSql=false"
);
```

`noLedgerWriteProved` 也不是写死 true，而是来自当前 response 对象：

```java
!requestContext.approvalLedgerWritten()
        && !executionBoundaries.nodeMayCreateApprovalDecision()
        && !executionBoundaries.nodeMayWriteApprovalLedger()
```

这让 proof 和真实响应边界保持耦合。

### 8. 测试锁定 digest 稳定性和边界

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:833) 验证 hint 基本字段：

```java
assertThat(rehearsal.verificationHint().hintVersion())
        .isEqualTo("java-release-approval-rehearsal-verification-hint.v1");
assertThat(rehearsal.verificationHint().responseSchemaVersion())
        .isEqualTo("java-release-approval-rehearsal-response-schema.v3");
assertThat(rehearsal.verificationHint().warningDigest()).startsWith("sha256:");
assertThat(rehearsal.verificationHint().noLedgerWriteProved()).isTrue();
assertThat(rehearsal.verificationHint().nodeMayTreatAsProductionAuthorization()).isFalse();
```

[OpsEvidenceServiceTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java:935) 还验证同一 header-backed warning 状态重复读取 digest 稳定：

```java
assertThat(repeatedHeaderBackedRehearsal.verificationHint().warningDigest())
        .isEqualTo(headerBackedRehearsal.verificationHint().warningDigest());
```

[OpsOverviewIntegrationTests.java](D:/javaproj/advanced-order-platform/src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java:935) 从 HTTP JSON 层验证 `verificationHint.*` 字段，确保 Node 后续真实读取时能拿到这些字段。

## 验证与归档

本版已先通过聚焦测试：

```text
mvn -Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests -DargLine="-XX:TieredStopAtLevel=1 -Xmx512m" test
```

运行记录、截图和清理记录写入：

```text
D:\javaproj\advanced-order-platform\c\69\解释\说明.md
D:\javaproj\advanced-order-platform\c\69\图片
```

## 成熟度变化

v69 后，Java release approval rehearsal 的证据链更适合被 Node 归档和导入窗口结果前校验：schema 有版本，warning 有 digest，no-ledger 边界有 proof claims。但它仍然是 rehearsal evidence，不是 production pass，也不是操作授权。

## 一句话总结

v69 给 release approval rehearsal 补上只读 verification hint，让 Node 后续能校验 schema、warning digest 和 no-ledger-write proof，同时 Java 仍不认证、不持久化、不写 ledger、不授权任何执行动作。
