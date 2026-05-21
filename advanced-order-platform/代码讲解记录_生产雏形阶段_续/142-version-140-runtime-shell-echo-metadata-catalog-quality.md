# 142-version-140-runtime-shell-echo-metadata-catalog-quality

## 本版目标

Java v140 是质量优化版，不新增业务 echo，不改变 release approval rehearsal 输出语义。

v139 已经把 runtime-shell echo 的 side-effect boundary 和 warning digest boundary line 抽到共享 catalog。本版继续同一方向，把 v135/v136 support 中剩余重复的 metadata 清单抽出：required evidence、no-go condition、continuation option、proof claim、Node warning/recommendation/action 等。

## 改动内容

- 新增 `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalog`
  - 集中维护 v135 decision record echo 的 required evidence ids。
  - 集中维护 v135 no-go condition template 和 action。
  - 集中维护 v135 proof claims、Node verification actions、warning/recommendation codes、next required echo versions。
  - 集中维护 v136 post-decision plan intake continuation options。
  - 集中维护 v136 proof claims、Node verification actions、warning/recommendation codes、next required echo versions。
- 精简 `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport`
  - 删除内联 metadata 常量清单。
  - no-go condition 构造改为 catalog 生成。
  - checks 内的稳定性比较改用 catalog 中的清单。
- 精简 `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport`
  - 删除内联 continuation option 构造。
  - 删除内联 proof/action/warning/recommendation/next echo 清单。
  - checks 内的 continuation option 顺序比较改用 catalog。
- 新增 `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalogTests`
  - 锁住 v135 required evidence、no-go、warning/recommendation/next echo 顺序。
  - 锁住 v136 continuation option 顺序和状态。
  - 锁住两个 echo 的核心 proof/action 文案仍在 catalog 中。

## 拆分效果

- `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellDecisionRecordEchoSupport`：约 475 行降到 418 行。
- `ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoSupport`：约 485 行降到 399 行。
- 新增 metadata catalog：约 224 行。

结合 v139，两份 support 已经从 575/582 行降到 418/399 行。更重要的是，新 echo 继续扩展时，不需要在 support 里反复堆 proof/action/code list。

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
- `mvn -q "-Dtest=ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoMetadataCatalogTests,ReleaseApprovalSandboxEndpointCredentialResolverRuntimeShellEchoBoundaryCatalogTests,OpsEvidenceServiceCredentialResolverRuntimeShellDecisionRecordEchoTests,OpsEvidenceServiceCredentialResolverRuntimeShellPostDecisionPlanIntakeEchoTests" test`
- `mvn -q "-Dtest=ReleaseApprovalVerificationWarningDigestLineCatalogTests,ReleaseApprovalVerificationHintContributionCatalogTests,OpsEvidenceServiceReleaseApprovalRehearsalVerificationHintOverviewTests" test`
- `mvn -q test`

## 后续建议

runtime-shell echo support 当前已经完成两次合理瘦身。继续优化可以转向更高层的 `OpsEvidenceService` 常量/装配拆分，或者先停止，等待 Node/mini-kv 链路状态明确后再推进更大结构调整。
