# v72 release approval artifact retention hint 代码讲解

## 本版目标

v72 按 `D:\nodeproj\orderops-node\docs\plans\v200-post-ci-artifact-manifest-roadmap.md` 的最新指导推进 Java 侧小版本：在 release approval rehearsal 只读响应中补 `artifactRetentionHint`，让后续 Node v203 可以核对 Java、Node、mini-kv 三项目的 CI artifact retention 一致性。

本版不做真实上传、不读取 GitHub token、不写 audit export、不创建 approval decision、不写 approval ledger、不触发 deployment / rollback / SQL。

## 响应结构入口

`src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java`

`ReleaseApprovalRehearsalResponse` 顶层新增：

```java
RehearsalArtifactRetentionHint artifactRetentionHint
```

它和已有 `operatorWindowHint`、`ciEvidenceHint` 是同级字段。这样 Node 后续读取 `/api/v1/ops/release-approval-rehearsal` 时，可以同时看到：

- operator window 身份与审批关联字段
- Node v200 CI manifest echo
- Node v202 upload dry-run contract 的 artifact retention echo
- Java 执行边界与 no-ledger-write proof

## artifactRetentionHint 字段含义

`RehearsalArtifactRetentionHint` 主要分三类字段。

第一类是 Java 自己已有的 retention fixture 来源：

```java
sourceRetentionFixtureVersion
sourceRetentionFixtureEndpoint
retentionId
artifactTarget
javaRetentionDays
releaseEvidenceEndpoints
```

这些值来自 `OpsEvidenceResponse.ReleaseAuditRetentionFixture`，也就是 Java 早前的 `/contracts/release-audit-retention.fixture.json` 证据。

第二类是 Node v202 dry-run upload contract 的回显字段：

```java
ciUploadContractVersion
ciUploadContractDigest
ciArtifactName
ciArtifactRoot
ciRetentionDays
ciUploadMode
```

它们来自 `OpsOverviewController` 新增的 6 个可选请求头：

```java
x-orderops-ci-upload-contract-version
x-orderops-ci-upload-contract-digest
x-orderops-ci-artifact-name
x-orderops-ci-artifact-root
x-orderops-ci-retention-days
x-orderops-ci-upload-mode
```

第三类是边界证明字段：

```java
javaRetentionFixtureReadOnly=true
auditExportReadOnly=true
ciArtifactUploadedByJava=false
githubArtifactAccessedByJava=false
productionWindowAllowedByJava=false
nodeMayTreatAsRetentionAuthorization=false
```

这些字段明确说明：Java 只提供只读证据，不负责真实 artifact 上传，也不给 Node 任何生产开窗授权。

## Service 组装逻辑

`src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java`

`releaseApprovalRehearsal(...)` 的主重载从 12 个 CI manifest/operator 参数扩展到 18 个参数，并保留旧 12 参数重载向后兼容：

```java
public ReleaseApprovalRehearsalResponse releaseApprovalRehearsal(
    ...,
    String ciApprovalCorrelationId,
    String ciUploadContractVersion,
    String ciUploadContractDigest,
    String ciArtifactName,
    String ciArtifactRoot,
    String ciRetentionDays,
    String ciUploadMode
)
```

进入 service 后，所有 header 都先走 `normalizeHeaderValue(...)` 去空白。缺省值不会抛错，而是进入 `artifactRetentionHint.echoWarnings`。

核心构造方法：

```java
private ReleaseApprovalRehearsalResponse.RehearsalArtifactRetentionHint rehearsalArtifactRetentionHint(...)
```

这里做了三件事：

1. 缺 header 时加入 `ORDEROPS_CI_UPLOAD_*_MISSING` 警告。
2. 回显 Node v202 dry-run contract 的 artifact name、root、retention days、upload mode。
3. 用 `retentionDaysWithinJavaRetention(...)` 判断 Node v202 的 `ciRetentionDays` 是否在 Java retention fixture 的 `javaRetentionDays` 范围内。

`retentionDaysWithinJavaRetention(...)` 解析失败时保守返回 `false`，避免把异常 retention 字符串当成可用证据。

## verificationHint 变化

`RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION` 从：

```text
java-release-approval-rehearsal-response-schema.v5
```

升级到：

```text
java-release-approval-rehearsal-response-schema.v6
```

`verificationHint.schemaFields` 新增：

```text
artifactRetentionHint
```

`warningDigestInputs` 新增：

```text
artifactRetentionEchoWarnings
```

`proofClaims` 新增：

```text
artifactRetentionHint.javaRetentionFixtureReadOnly=true
artifactRetentionHint.ciArtifactUploadedByJava=false
artifactRetentionHint.githubArtifactAccessedByJava=false
artifactRetentionHint.nodeMayTreatAsRetentionAuthorization=false
```

这保证 Node 后续核对 digest 时，retention echo warning 与“未上传 artifact / 未访问 GitHub artifact / 未授权 retention”的边界也进入稳定摘要。

## Controller header 接入

`src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java`

`GET /api/v1/ops/release-approval-rehearsal` 新增 6 个可选 header 参数。它们只传给 service 形成 echo，不触发任何写操作。

## 测试覆盖

`src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java`

- 缺省调用：断言 `artifactRetentionHint` 使用 placeholder、出现 6 个 missing warning、`retentionDaysWithinJavaRetention=false`。
- 完整 header 调用：断言 Node v202 dry-run contract 字段被 trim 后回显，`artifactRetentionContextComplete=true`，`retentionDaysWithinJavaRetention=true`。
- digest 稳定性：同一组 header 重复调用，`verificationHint.warningDigest` 一致。

`src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java`

- HTTP 缺省调用：断言 JSON 中出现 `artifactRetentionHint`、schema v6 和 missing warning。
- HTTP 完整 header 调用：断言 controller 新 header 能进入 JSON 响应。

## 边界结论

v72 是一个只读 evidence/hint 版本。它让 Java 对 Node v202 的 artifact retention dry-run contract 有可核对回显，但不会打开真实 GitHub artifact upload，也不会让 Java 或 Node 获得任何生产执行权限。
