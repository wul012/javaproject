# Java v122：Integration Tests 第一刀拆分

本版按 Node v287 计划推进 Java v122，只做 integration tests 拆分，不改生产逻辑。

## 改动

- 新增 `OpsReleaseApprovalRehearsalLiveAggregationIntegrationTestSupport`，集中 live aggregation 集成测试的 `MockMvc`、数据清理和 replay approval fixture。
- `OpsReleaseApprovalRehearsalLiveAggregationIntegrationTests` 继续覆盖主 live aggregation response 和 failure taxonomy。
- 新增 `OpsReleaseApprovalRehearsalLiveAggregationVerificationHintIntegrationTests`，承接 `verificationHint`、`releaseApprovalInputs`、`liveSignals`、`executionBoundaries` 等尾段 contract。

## 行数

```text
OpsReleaseApprovalRehearsalLiveAggregationIntegrationTests.java: 1297 -> 976 行
OpsReleaseApprovalRehearsalLiveAggregationIntegrationTestSupport.java: 66 行
OpsReleaseApprovalRehearsalLiveAggregationVerificationHintIntegrationTests.java: 284 行
```

## 验证

```text
mvn -q "-Dtest=OpsReleaseApprovalRehearsalLiveAggregationIntegrationTests,OpsReleaseApprovalRehearsalLiveAggregationVerificationHintIntegrationTests" test
mvn -q "-Dtest=OpsReleaseApprovalRehearsalLiveAggregationIntegrationTests,OpsReleaseApprovalRehearsalLiveAggregationVerificationHintIntegrationTests,OpsReleaseApprovalRehearsalHeaderedLiveAggregationIntegrationTests,OpsReleaseApprovalCredentialResolverEndpointCandidateIntegrationTests,OpsReleaseApprovalCredentialResolverEndpointArchiveIntegrationTests,OpsReleaseApprovalCredentialResolverEndpointDecisionIntegrationTests,OpsReleaseApprovalCredentialResolverReadinessIntegrationTests" test
```
