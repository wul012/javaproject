# 141-version-139-runtime-shell-echo-boundary-catalog-quality

## 本版目标

Java v139 是质量优化版，不新增业务 echo，不改变 release approval rehearsal 输出语义。

根据当前 Node 计划，Java 不抢跑 Node v303；本版继续处理 Java 自身质量短板：runtime-shell 相关 echo support 已经出现重复的 side-effect boundary、warning digest boundary input、boundary line 和 no credential/write/autostart 证明逻辑，因此先抽一层共享 catalog。

## 改动内容

- 新增 `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalog`
  - 集中维护 runtime shell decision record echo 的 warning digest boundary input 名称。
  - 集中维护 post-decision plan intake echo 的 warning digest boundary input 名称。
  - 集中生成两个 echo 的 read-only side-effect boundary 默认值。
  - 集中生成两个 echo 的 warning digest boundary lines。
  - 集中维护两个 echo 的 no credential / no connection / no write / no autostart 证明判断。
- 精简 `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport`
  - 移除内联 boundary input 清单。
  - 移除内联 boundary lines 构造。
  - 移除内联 28 个布尔值的 side-effect boundary 构造。
  - 将 no credential / connection / write / autostart 证明委托给 catalog。
- 精简 `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport`
  - 与 v135 support 保持同一模式，避免 v136 support 单独复制同一套边界骨架。
- 新增 `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalogTests`
  - 锁住两个 echo 的 boundary input 顺序。
  - 锁住关键 digest line 的值。
  - 锁住 read-only side-effect boundary 默认值。

## 拆分效果

- `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport`：约 575 行降到 475 行。
- `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport`：约 582 行降到 485 行。
- 新增 catalog：约 298 行。

这次不是把所有 echo support 一次性模板化，而是先把重复度最高、风险最低的 boundary 骨架抽出。后续新增 runtime-shell echo 时，固定边界可以复用 catalog，support 文件只保留场景差异。

## 边界

本版只改代码组织：

- 不新增 runtime shell implementation。
- 不读取 credential value。
- 不解析 raw endpoint URL。
- 不实例化 provider/client。
- 不调用外部 managed audit。
- 不写 approval ledger。
- 不执行 SQL/schema migration。
- 不自动启动 Java、Node、mini-kv 或外部服务。

## 验证

- `mvn -q -DskipTests test-compile`
- `mvn -q "-Dtest=ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalogTests,OpsEvidenceServiceCredentialResolverRuntimeShellDecisionRecordEchoTests,OpsEvidenceServiceCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoTests" test`
- `mvn -q "-Dtest=ReleaseApprovalVerificationWarningDigestLineCatalogTests,ReleaseApprovalVerificationHintContributionCatalogTests,OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests" test`

## 后续建议

如果继续做大优化，建议仍按 strangler 模式推进：

1. 再抽 runtime-shell echo 的 proof/action/code list catalog。
2. 再评估 `OpsEvidenceService` 常量和装配段是否需要 service/catalog 二次瘦身。
3. 不建议一次性重写全部 echo support，避免 digest 和 schema 输出出现非预期漂移。
