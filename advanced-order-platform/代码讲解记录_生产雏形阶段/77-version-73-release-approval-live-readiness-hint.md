> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# v73 release approval live readiness hint 代码讲解

## 本版目标

v73 按 `D:\nodeproj\orderops-node\docs\plans\v203-post-ci-artifact-retention-roadmap.md` 的最新指导推进 Java 侧小版本：在 release approval rehearsal 只读响应中补充 `liveReadinessHint`，让后续 Node v205 执行真实只读 HTTP smoke 时，可以稳定识别 Java 读目标、schema 版本、服务端采样时间和运行上下文 echo。

本版不做真实写操作，不启动 Node smoke，不记录 Node PID，不创建 approval decision，不写 approval ledger，不触发 deployment / rollback / SQL，也不把 runtime smoke 当作生产授权。

## 响应结构入口

`src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalResponse.java`

`ReleaseApprovalRehearsalResponse` 顶层新增：

```java
RehearsalLiveReadinessHint liveReadinessHint
```

它和 `operatorWindowHint`、`ciEvidenceHint`、`artifactRetentionHint` 同级。这样 Node 读取 `/api/v1/ops/release-approval-rehearsal` 时，可以在同一个响应里同时看到操作者窗口、CI artifact manifest、artifact retention contract，以及本版新增的真实只读联调 readiness 信息。

## liveReadinessHint 字段含义

`RehearsalLiveReadinessHint` 主要分成四类字段。

第一类是 Java 自身给出的只读端点信息：

```java
serverTimestamp
serverTimestampSource
readOnlyEndpointVersion
readOnlyEndpoint
healthEndpoint
readyForRuntimeSmokeRead
readOnlyEndpointReady
```

其中 `serverTimestamp` 直接来自 `sampledAt`，`readOnlyEndpointVersion` 是 `java-release-approval-rehearsal-response-schema.v7`，`readOnlyEndpoint` 固定为 `/api/v1/ops/release-approval-rehearsal`，`healthEndpoint` 固定为 `/actuator/health`。

第二类是 Node v204/v205 运行上下文 echo：

```java
sourcePreflightVersion
sourcePreflightDigest
runtimeSmokeSessionId
runtimeReadTargetId
runtimeWindowMode
```

它们来自 `OpsOverviewController` 新增的 5 个可选 header：

```java
x-orderops-runtime-preflight-version
x-orderops-runtime-preflight-digest
x-orderops-runtime-smoke-session-id
x-orderops-runtime-read-target-id
x-orderops-runtime-window-mode
```

这些字段只被 trim 后回显，不会触发写操作。缺省时进入 `echoWarnings`，完整传入时 `liveReadinessContextComplete=true`。

第三类是边界证明字段：

```java
runtimeSmokeExecutedByJava=false
nodeMustRecordPidAndCleanup=true
javaStartedProcessForNode=false
processCleanupRecordedByJava=false
nodeMayTreatAsProductionAuthorization=false
```

这些字段明确说明 Java 只提供只读目标和上下文 echo。真正的进程启动、PID 记录、HTTP smoke 执行和 cleanup 证据仍由 Node v205 负责。

第四类是 Node 可使用的检查清单：

```java
acceptedLiveReadinessHeaders
allowedReadTargets
forbiddenRuntimeOperations
nodeVerificationActions
```

`allowedReadTargets` 只包含 `GET /actuator/health` 和 `GET /api/v1/ops/release-approval-rehearsal`。`forbiddenRuntimeOperations` 明确禁止订单写入、失败事件 replay、PUT/PATCH/DELETE，以及由 Java 代替 Node 启停进程。

## Service 组装逻辑

`src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceService.java`

`releaseApprovalRehearsal(...)` 的主重载从 v72 的 artifact retention 参数继续扩展，追加 5 个 runtime header 参数：

```java
String runtimePreflightVersion
String runtimePreflightDigest
String runtimeSmokeSessionId
String runtimeReadTargetId
String runtimeWindowMode
```

进入 service 后，所有 header 先经过 `normalizeHeaderValue(...)` 去空白。核心构造方法是：

```java
private ReleaseApprovalRehearsalResponse.RehearsalLiveReadinessHint rehearsalLiveReadinessHint(...)
```

该方法的关键逻辑是：

1. 缺少 runtime header 时加入 `ORDEROPS_RUNTIME_*_MISSING` warning。
2. 使用 `sampledAt` 作为 server timestamp，避免另取时间导致响应内部不一致。
3. 使用 `readOnlyWindow.readyForReadOnlyLiveProbe()` 和 allowed probe endpoint 判断 Java 只读目标是否 ready。
4. 固定声明 `runtimeSmokeExecutedByJava=false`、`javaStartedProcessForNode=false`，把真实 smoke 执行权保留给 Node。

## verificationHint 变化

`RELEASE_APPROVAL_REHEARSAL_RESPONSE_SCHEMA_VERSION` 从：

```text
java-release-approval-rehearsal-response-schema.v6
```

升级为：

```text
java-release-approval-rehearsal-response-schema.v7
```

`verificationHint.schemaFields` 新增：

```text
liveReadinessHint
```

`warningDigestInputs` 新增：

```text
liveReadinessEchoWarnings
```

`proofClaims` 新增：

```text
liveReadinessHint.readOnlyEndpointReady=true
liveReadinessHint.runtimeSmokeExecutedByJava=false
liveReadinessHint.javaStartedProcessForNode=false
liveReadinessHint.nodeMayTreatAsProductionAuthorization=false
```

这让 Node 后续比对 warning digest 时，可以把 live readiness 的缺省 header warning 和 Java 未执行 runtime smoke 的边界一起纳入稳定摘要。

## Controller header 接入

`src/main/java/com/codexdemo/orderplatform/ops/OpsOverviewController.java`

`GET /api/v1/ops/release-approval-rehearsal` 新增 5 个可选 runtime header 参数。Controller 只负责把 header 传给 service，不做鉴权、不启动进程、不写数据库。

## 测试覆盖

`src/test/java/com/codexdemo/orderplatform/ops/OpsEvidenceServiceTests.java`

- 缺省调用：断言 `liveReadinessHint` 使用 placeholder，出现 5 个 missing warning，schema 升级为 v7。
- 完整 header 调用：断言 Node v204/v205 runtime 上下文字段被 trim 后回显，所有 echoed flag 为 true，`liveReadinessContextComplete=true`。
- digest 稳定性：同一组 runtime header 重复调用，`verificationHint.warningDigest` 保持一致。

`src/test/java/com/codexdemo/orderplatform/OpsOverviewIntegrationTests.java`

- HTTP 缺省调用：断言 JSON 中出现 `liveReadinessHint`、schema v7、allowed read target 和 missing warning。
- HTTP 完整 header 调用：断言 controller 新增 header 能进入 JSON 响应，并且 Java 仍保持 read-only 和 no-ledger-write 边界。

## 边界结论

v73 是一个只读联调友好版本。它让 Java release approval rehearsal 具备被 Node v205 真实 HTTP smoke 安全读取的上下文信息，但不替 Node 执行 smoke，不承担 Node 的 PID/cleanup 证据，也不改变订单、失败事件 replay 或生产窗口授权链路。
