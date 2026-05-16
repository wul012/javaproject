# 75. v71 release approval CI evidence hint 代码讲解

## 本版目标

v71 对齐 `D:\nodeproj\orderops-node\docs\plans\v200-post-ci-artifact-manifest-roadmap.md` 中的 Java v71 建议：在 release approval rehearsal 只读响应里补一个 CI evidence hint，让 Node v201 后续可以把 Node v200 的 CI archive artifact manifest 摘要字段回显到 Java 响应中做交叉校验。

本版仍然不做真实 CI artifact 上传、不访问 GitHub secret、不写 approval ledger、不创建审批决定、不打开生产窗口。

## 响应结构

`ReleaseApprovalRehearsalResponse` 新增 `ciEvidenceHint` 字段，位置放在 `operatorWindowHint` 后面，表示它是 v70 operator window hint 之后的下一层只读证据。

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
        RehearsalCiEvidenceHint ciEvidenceHint,
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

`RehearsalCiEvidenceHint` 的字段分成四组：

```java
public record RehearsalCiEvidenceHint(
        String hintVersion,
        String manifestProfileVersion,
        String manifestProfileVersionSource,
        String manifestDigest,
        String manifestDigestSource,
        String manifestEndpoint,
        String manifestEndpointSource,
        String artifactRecordCount,
        String artifactRecordCountSource,
        String approvalCorrelationId,
        String approvalCorrelationIdSource,
        boolean manifestProfileVersionEchoed,
        boolean manifestDigestEchoed,
        boolean manifestEndpointEchoed,
        boolean artifactRecordCountEchoed,
        boolean approvalCorrelationEchoed,
        boolean ciEvidenceContextComplete,
        String noLedgerWriteProof,
        boolean noLedgerWriteProved,
        boolean ciArtifactUploadedByJava,
        boolean githubArtifactAccessedByJava,
        boolean productionWindowAllowedByJava,
        boolean nodeMayTreatAsCiArtifactPublication,
        List<String> acceptedCiEvidenceHeaders,
        List<String> echoWarnings,
        List<String> nodeVerificationActions
)
```

字段含义：

- `manifestProfileVersion / manifestDigest / manifestEndpoint / artifactRecordCount`：回显 Node v200 manifest 摘要。
- `approvalCorrelationId`：把 CI evidence hint 和 operator window approval correlation 关联起来。
- `*Echoed` 与 `ciEvidenceContextComplete`：告诉 Node 哪些 header 被 Java 看见。
- `ciArtifactUploadedByJava=false`、`githubArtifactAccessedByJava=false`、`productionWindowAllowedByJava=false`：明确 Java 不做 CI 上传、不访问 GitHub、不授权生产窗口。

## Controller 入口

`OpsOverviewController.releaseApprovalRehearsal(...)` 增加 5 个可选 header：

```java
@RequestHeader(name = "x-orderops-ci-manifest-version", required = false) String ciManifestVersion,
@RequestHeader(name = "x-orderops-ci-manifest-digest", required = false) String ciManifestDigest,
@RequestHeader(name = "x-orderops-ci-manifest-endpoint", required = false) String ciManifestEndpoint,
@RequestHeader(name = "x-orderops-ci-artifact-record-count", required = false)
String ciArtifactRecordCount,
@RequestHeader(name = "x-orderops-ci-approval-correlation-id", required = false)
String ciApprovalCorrelationId
```

这些 header 被原样传给服务层，不做认证、不写库、不触发上传：

```java
return opsEvidenceService.releaseApprovalRehearsal(
        requestId,
        operatorIdentity,
        auditCorrelationId,
        operatorWindowOperatorId,
        operatorWindowRoles,
        operatorWindowVerifiedClaim,
        operatorWindowApprovalCorrelationId,
        ciManifestVersion,
        ciManifestDigest,
        ciManifestEndpoint,
        ciArtifactRecordCount,
        ciApprovalCorrelationId
);
```

## 服务层生成 hint

`OpsEvidenceService` 增加版本常量，并把 response schema 从 v4 提升到 v5：

```java
static final String RELEASE_APPROVAL_REHEARSAL_CI_EVIDENCE_HINT_VERSION =
        "java-release-approval-rehearsal-ci-evidence-hint.v1";

static final String RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION =
        "java-release-approval-rehearsal-response-schema.v5";
```

服务层先 trim header：

```java
String normalizedCiManifestVersion = normalizeHeaderValue(ciManifestVersion);
String normalizedCiManifestDigest = normalizeHeaderValue(ciManifestDigest);
String normalizedCiManifestEndpoint = normalizeHeaderValue(ciManifestEndpoint);
String normalizedCiArtifactRecordCount = normalizeHeaderValue(ciArtifactRecordCount);
String normalizedCiApprovalCorrelationId = normalizeHeaderValue(ciApprovalCorrelationId);
```

然后构造 `ciEvidenceHint` 并放进总响应：

```java
ReleaseApprovalRehearsalResponse.RehearsalCiEvidenceHint ciEvidenceHint =
        rehearsalCiEvidenceHint(
                normalizedCiManifestVersion,
                normalizedCiManifestDigest,
                normalizedCiManifestEndpoint,
                normalizedCiArtifactRecordCount,
                normalizedCiApprovalCorrelationId
        );

return new ReleaseApprovalRehearsalResponse(
        evidence.sampledAt(),
        RELEASE_APPROVAL_REHEARSAL_VERSION,
        "/api/v1/ops/evidence",
        "READ_ONLY_RELEASE_APPROVAL_REHEARSAL",
        true,
        false,
        requestContext,
        operatorWindowHint,
        ciEvidenceHint,
        failureTaxonomy,
        releaseApprovalVerificationHint(
                requestContext,
                operatorWindowHint,
                ciEvidenceHint,
                failureTaxonomy,
                executionBoundaries
        ),
        releaseApprovalInputs(evidence),
        liveSignals(evidence),
        executionBoundaries,
        releaseApprovalRehearsalBlockers(evidence),
        evidence.readOnlyWindow().requiredNodeEnvironment(),
        releaseApprovalNextEvidenceActions()
);
```

## 缺失 header 的稳定占位

`rehearsalCiEvidenceHint(...)` 沿用 v67-v70 的模式：缺失字段返回稳定 placeholder，同时给出 warning。

```java
addMissingContextWarning(
        warnings,
        normalizedCiManifestVersion,
        "ORDEROPS_CI_MANIFEST_VERSION_MISSING"
);
addMissingContextWarning(
        warnings,
        normalizedCiManifestDigest,
        "ORDEROPS_CI_MANIFEST_DIGEST_MISSING"
);
addMissingContextWarning(
        warnings,
        normalizedCiManifestEndpoint,
        "ORDEROPS_CI_MANIFEST_ENDPOINT_MISSING"
);
addMissingContextWarning(
        warnings,
        normalizedCiArtifactRecordCount,
        "ORDEROPS_CI_ARTIFACT_RECORD_COUNT_MISSING"
);
addMissingContextWarning(
        warnings,
        normalizedCiApprovalCorrelationId,
        "ORDEROPS_CI_APPROVAL_CORRELATION_ID_MISSING"
);
```

构造返回值时也保留 `acceptedCiEvidenceHeaders`，方便 Node v201 明确知道 Java 接受了哪些只读字段：

```java
List.of(
        "x-orderops-ci-manifest-version",
        "x-orderops-ci-manifest-digest",
        "x-orderops-ci-manifest-endpoint",
        "x-orderops-ci-artifact-record-count",
        "x-orderops-ci-approval-correlation-id"
)
```

## 验证提示和 digest

`releaseApprovalVerificationHint(...)` 新增 `ciEvidenceHint` 参与 schema、digest input 和 proof claim：

```java
List<String> warningDigestInputs = List.of(
        "contextWarnings",
        "operatorWindowEchoWarnings",
        "ciEvidenceEchoWarnings",
        "failureCategories",
        "taxonomyWarnings",
        "executionAllowed",
        "approvalLedgerWritten",
        "nodeMayWriteApprovalLedger"
);
```

proof claims 继续强调“不写 ledger、不上传 CI artifact、不访问 GitHub artifact、不打开生产窗口”：

```java
List<String> proofClaims = List.of(
        "executionAllowed=false",
        "requestContext.approvalLedgerWritten=false",
        "ciEvidenceHint.noLedgerWriteProved=true",
        "ciEvidenceHint.ciArtifactUploadedByJava=false",
        "ciEvidenceHint.githubArtifactAccessedByJava=false",
        "ciEvidenceHint.productionWindowAllowedByJava=false",
        "executionBoundaries.nodeMayCreateApprovalDecision=false",
        "executionBoundaries.nodeMayWriteApprovalLedger=false",
        "executionBoundaries.nodeMayTriggerDeployment=false",
        "executionBoundaries.nodeMayTriggerRollback=false",
        "executionBoundaries.nodeMayExecuteRollbackSql=false"
);
```

`warningDigest(...)` 也加入 CI hint 的 warning 和边界布尔值：

```java
return digest(List.of(
        line("digestKind", "releaseApprovalRehearsalWarning"),
        line("hintVersion", RELEASE_APPROVAL_REHEARSAL_VERIFICATION_HINT_VERSION),
        line("responseSchemaVersion", RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION),
        line("contextWarnings", requestContext.contextWarnings()),
        line("operatorWindowEchoWarnings", operatorWindowHint.echoWarnings()),
        line("ciEvidenceEchoWarnings", ciEvidenceHint.echoWarnings()),
        line("failureCategories", failureTaxonomy.failureCategories()),
        line("taxonomyWarnings", failureTaxonomy.taxonomyWarnings()),
        line("executionAllowed", false),
        line("approvalLedgerWritten", requestContext.approvalLedgerWritten()),
        line("ciArtifactUploadedByJava", ciEvidenceHint.ciArtifactUploadedByJava()),
        line("githubArtifactAccessedByJava", ciEvidenceHint.githubArtifactAccessedByJava()),
        line("nodeMayWriteApprovalLedger", executionBoundaries.nodeMayWriteApprovalLedger())
));
```

这样 Node v201 能区分“没有传 CI manifest 字段”和“传入了完整 CI manifest 字段”两种状态，且 digest 不受 `sampledAt` 影响。

## 测试覆盖

`OpsEvidenceServiceTests` 覆盖服务层：

```java
assertThat(rehearsal.ciEvidenceHint().manifestProfileVersion())
        .isEqualTo("ci-manifest-profile-version-not-supplied");
assertThat(rehearsal.ciEvidenceHint().echoWarnings())
        .containsExactly(
                "ORDEROPS_CI_MANIFEST_VERSION_MISSING",
                "ORDEROPS_CI_MANIFEST_DIGEST_MISSING",
                "ORDEROPS_CI_MANIFEST_ENDPOINT_MISSING",
                "ORDEROPS_CI_ARTIFACT_RECORD_COUNT_MISSING",
                "ORDEROPS_CI_APPROVAL_CORRELATION_ID_MISSING"
        );
```

完整 header 路径则验证 trim、source、complete 和安全边界：

```java
assertThat(headerBackedRehearsal.ciEvidenceHint().manifestProfileVersion())
        .isEqualTo("real-read-window-ci-archive-artifact-manifest.v1");
assertThat(headerBackedRehearsal.ciEvidenceHint().manifestDigest())
        .isEqualTo("sha256:node-v200-manifest-digest");
assertThat(headerBackedRehearsal.ciEvidenceHint().ciEvidenceContextComplete()).isTrue();
assertThat(headerBackedRehearsal.ciEvidenceHint().ciArtifactUploadedByJava()).isFalse();
assertThat(headerBackedRehearsal.ciEvidenceHint().githubArtifactAccessedByJava()).isFalse();
```

`OpsOverviewIntegrationTests` 覆盖 HTTP JSON：

```java
.andExpect(jsonPath("$.ciEvidenceHint.hintVersion")
        .value("java-release-approval-rehearsal-ci-evidence-hint.v1"))
.andExpect(jsonPath("$.ciEvidenceHint.ciArtifactUploadedByJava").value(false))
.andExpect(jsonPath("$.verificationHint.responseSchemaVersion")
        .value("java-release-approval-rehearsal-response-schema.v5"))
```

## 本版边界

- Java 只回显 CI evidence hint，不上传 artifact。
- Java 不访问 GitHub artifact store，不读取 GitHub secret。
- Java 不写 approval ledger，不创建 approval decision。
- `nodeMayTreatAsCiArtifactPublication=false`，所以 Node 不能把这个响应当成真实 artifact 发布完成。
- 生产窗口仍然关闭，`executionAllowed=false`。
