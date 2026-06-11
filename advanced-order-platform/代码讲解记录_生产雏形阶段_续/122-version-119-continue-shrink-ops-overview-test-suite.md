> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# Java v119：继续收缩 ops overview 总表

本版继续按“测试先拆、生产不动”的方式推进，把 `OpsOverviewIntegrationTests` 里剩余的回放和静态契约内容拆到更小的测试类里。

## 拆分对象

- `OpsReleaseApprovalRehearsalLiveAggregationIntegrationTests`
- `OpsReleaseApprovalRehearsalHeaderedLiveAggregationIntegrationTests`
- `OpsReleaseApprovalSandboxConnectionEchoIntegrationTests`
- `OpsOverviewStaticEvidenceContractsIntegrationTests`
- `OpsOverviewStaticReleaseReadinessIntegrationTests`
- `OpsOverviewStaticRollbackContractsIntegrationTests`

## 结果

`OpsOverviewIntegrationTests` 回到 854 行，已经从“巨型总表”缩成偏总览的骨架测试。

## 验证

```text
mvn -q "-Dtest=OpsOverviewIntegrationTests,OpsOverviewStaticEvidenceContractsIntegrationTests,OpsOverviewStaticReleaseReadinessIntegrationTests,OpsOverviewStaticRollbackContractsIntegrationTests,OpsReleaseApprovalRehearsalLiveAggregationIntegrationTests,OpsReleaseApprovalRehearsalHeaderedLiveAggregationIntegrationTests,OpsReleaseApprovalSandboxConnectionEchoIntegrationTests" test
mvn -q test
```
