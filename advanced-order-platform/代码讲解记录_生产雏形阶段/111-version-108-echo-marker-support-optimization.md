> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# Java v108 代码讲解：echo marker support 优化

## 本版所处项目进度

最新计划仍来自：

```text
D:\nodeproj\orderops-node\docs\plans\v263-post-disabled-resolver-echo-roadmap.md
```

v107 已经把 Node v264 test-only shell echo marker 固化出来。v108 不再新增业务 marker，而是针对前面几版 echo marker 的重复写法做一个小型 support 优化。

## 为什么现在做

因为 v104-v107 里有三类明显重复：

```text
warning 条件收集
warning input name 包装
warning line 包装
```

这类重复继续堆下去，后面每新增一版 marker 都会把同样的 if / List.of / List.copyOf 再铺一遍。v108 先抽一个很小的 support，收益不大，但很稳，也不会碰 JSON 契约。

## 新增文件

```text
src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalEchoMarkerSupport.java
```

## 复用位置

以下 builder 复用这个 support：

```text
ReleaseApprovalManagedAuditSandboxEndpointHandlePreflightEchoMarkerBuilder
ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDecisionEchoMarkerBuilder
ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverDisabledPrecheckEchoMarkerBuilder
ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverTestOnlyShellEchoMarkerBuilder
```

这次只收口三件事：

```text
warningInputNames
warningLines
warnings(conditions...)
```

## 为什么这版合理

合理，因为它不改业务，不改字段，不改 schema，不改 route，不改 warning 顺序，只是在 Java 内部把同类辅助逻辑抽成一个很小的 support。

不做的部分也很明确：

```text
不拆 ReleaseApprovalRehearsalResponse
不改 managed-audit 证据契约
不引入新的 echo marker 版本
不启动真实 resolver / secret provider / external request
```

## 验证

已运行并通过：

```text
mvn -q -DskipTests compile
mvn -q \"-Dtest=OpsEvidenceServiceTests#buildsReadOnlyEvidenceForControlPlane,OpsEvidenceServiceTests#releaseApprovalRehearsalAddsSandboxEndpointHandlePreflightEchoMarker,OpsEvidenceServiceTests#releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverDecisionEchoMarker,OpsEvidenceServiceTests#releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,OpsEvidenceServiceTests#releaseApprovalRehearsalAddsSandboxEndpointCredentialResolverTestOnlyShellEchoMarker,OpsOverviewIntegrationTests#releaseApprovalRehearsalReturnsReadOnlyLiveAggregation,OpsOverviewIntegrationTests#releaseApprovalRehearsalExposesSandboxEndpointHandlePreflightEchoMarker,OpsOverviewIntegrationTests#releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverDecisionEchoMarker,OpsOverviewIntegrationTests#releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverDisabledPrecheckEchoMarker,OpsOverviewIntegrationTests#releaseApprovalRehearsalExposesSandboxEndpointCredentialResolverTestOnlyShellEchoMarker\" test
mvn -q test
mvn -q -DskipTests package
```

## 本版总结

v108 把 echo marker 的一小层公共样板收住了，属于“往回理顺”，不是“继续往前加戏”。
