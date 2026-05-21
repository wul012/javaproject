# 131. Java v130 - credential resolver implementation plan catalog extension

## 本版目标

- 对齐 Node 当前计划 `v289-post-disabled-fake-harness-echo-roadmap.md` 中的 Java v130：延伸 echo catalog。
- 优先处理 `ImplementationPlanEchoReceiptBuilder` 的模板重复。
- 只做结构优化，不改变 release approval rehearsal JSON 字段、digest 输入顺序或 fake harness disabled 边界。

## 改动

扩展 `ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog`：

- 新增 `ImplementationPlanInterfaceBoundaryTemplate`。
- 新增 `ImplementationPlanUpstreamEchoRequirementTemplate`。
- 集中保存 implementation plan 的 7 个 interface boundary 模板。
- 集中保存 Java v121 / mini-kv v126 upstream echo requirement 模板。

收缩 `ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder`：

- builder 不再直接维护大段 boundary / requirement 文本。
- builder 只从 catalog 读取模板并映射成 response record。
- 保留原有 readiness、checks、digest、warning digest 行为。

## 行数变化

- `ReleaseApprovalManagedAuditSandboxEndpointCredentialResolverImplementationPlanEchoReceiptBuilder.java`：723 行降至 609 行。
- `ReleaseApprovalSandboxEndpointCredentialResolverBoundaryCatalog.java`：234 行增至 485 行。

这是有意迁移：把可复用模板从 builder 推入 catalog，让后续 echo support 不再复制大段常量。

## 验证

已执行：

```powershell
mvn -q -DskipTests test-compile
mvn -q "-Dtest=OpsEvidenceServiceApprovalRequiredImplementationReadinessEchoTests,OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests" test
```

结果：通过。测试输出仅包含 Mockito 动态 agent 的 JDK 未来兼容提示。

## 边界

- 未读取 credential value。
- 未解析 raw endpoint URL。
- 未连接 managed audit。
- 未写 approval ledger。
- 未执行 SQL / schema migration。
- 未启动 Docker、Java 或 mini-kv 后台进程。
