> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# Java v126: EvidenceService echo support catalog 止血

本版执行 Node v287 plan 中的 Java v126: EvidenceService catalog 化止血。目标不是大改 `OpsEvidenceService`，而是把高重复、高漂移风险的 echo support 常量先收口到现有 catalog。

## 改动

- `ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog` 新增 `ApprovalRequiredImplementationTemplate`。
- approval-required implementation readiness 的 owner、required artifacts、Java v116 echo hint、mini-kv v122 receipt hint、Node v282 verification hint、prohibited runtime actions 统一从 catalog 读取。
- production readiness blocked decision 的 missing requirement list 改为复用 catalog requirement codes。
- receipt/response record 形状、字段名、断言语义保持不变。

## 维护收益

- `ReleaseApprovalSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoSupport` 从 441 行降到 334 行，删除本类内重复 switch。
- `ReleaseApprovalSandboxEndpointCredentialResolverProductionReadinessBlockedDecisionEchoSupport` 从 340 行降到 330 行，missing requirement 不再维护第二份硬编码列表。
- catalog 从 119 行扩展到 234 行，作为后续 echo support 继续收口的统一入口。
- 本版没有新增运行时入口，没有 fake harness runtime，没有 credential value/raw endpoint/managed audit/ledger/SQL 行为变化。

## 验证

```text
mvn -q -DskipTests test-compile
mvn -q "-Dtest=OpsEvidenceServiceCredentialResolverProductionReadinessBlockedDecisionEchoTests,OpsEvidenceServiceApprovalRequiredImplementationReadinessEchoTests,OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests" test
```
