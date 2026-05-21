# 134. Java v132 - execution-denied echo quality pass

## 本版目标

把 Java v131 新增的 direct execution-denied echo 再做一轮质量收口，按项目既有的 support/builder 分层，把 heavy logic 下沉到 support，builder 保持薄壳。

## 主要改动

- 新增 `ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport`。
- `ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverExecutionDeniedEchoReceiptBuilder` 变成薄壳，只负责转调 support。
- 保留 v131 的 receipt 语义、schema 和测试断言不变。

## 结果

- 功能行为不变。
- 结构更贴近现有 `*EchoSupport` / `*Builder` 模式。
- 后续再扩类似 receipt 时，更容易复用 support 常量和构造规则。

## 验证

已通过：

```powershell
mvn -q "-Dtest=OpsEvidenceServiceCredentialResolverExecutionDeniedEchoTests,OpsEvidenceServiceApprovalRequiredImplementationReadinessEchoTests,OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests,OpsEvidenceServiceReleaseApprovalRehearsalOverviewTests" test
```
