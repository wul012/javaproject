> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# Java v118：release approval credential resolver endpoint 测试拆分

本版继续按“先拆测试、后碰生产”的顺序推进。

## 拆分对象

从 `OpsOverviewIntegrationTests` 中拆出一组连续的 release approval credential resolver rehearsal 测试，按职责分成 3 个独立集成测试类：

- `OpsReleaseApprovalCredentialResolverEndpointDecisionIntegrationTests`
- `OpsReleaseApprovalCredentialResolverEndpointArchiveIntegrationTests`
- `OpsReleaseApprovalCredentialResolverEndpointCandidateIntegrationTests`

## 维护收益

- 原文件减少 849 行
- 每个新类只承接一组相近场景
- 以后再拆时可以继续按 credential resolver 家族向下切，不必把所有 MockMvc 断言继续堆在总表里

## 保持不变

- 生产代码不动
- 断言内容不动
- 测试覆盖不减

## 验证

```text
mvn -q "-Dtest=OpsOverviewIntegrationTests,OpsReleaseApprovalCredentialResolverEndpointDecisionIntegrationTests,OpsReleaseApprovalCredentialResolverEndpointArchiveIntegrationTests,OpsReleaseApprovalCredentialResolverEndpointCandidateIntegrationTests,OpsReleaseApprovalCredentialResolverReadinessIntegrationTests" test
mvn -q test
```
