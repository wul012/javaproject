# 132. Java v129 - release approval overview second split

## 本版目标

- 对齐 Node 当前计划 `v289-post-disabled-fake-harness-echo-roadmap.md` 中的 Java v129：继续拆分偏大的 overview 测试。
- 只拆测试结构，不改变生产代码、接口 JSON、record contract 或 disabled fake harness 边界。
- 不读取 credential value，不解析 raw endpoint URL，不写 ledger，不执行 SQL，不启动 Java / mini-kv / Docker 后台运行时。

## 拆分范围

原 `OpsEvidenceServiceReleaseApprovalRehearsalOverviewTests` 是单个 1666 行测试方法，包含默认请求、audit handoff、adapter guard、sandbox connection、summary/live boundary 等所有断言。

本版按响应主题拆成 5 个测试类：

- `OpsEvidenceServiceReleaseApprovalRehearsalOverviewTests`
- `OpsEvidenceServiceReleaseApprovalRehearsalAuditHandoffOverviewTests`
- `OpsEvidenceServiceReleaseApprovalRehearsalAdapterGuardOverviewTests`
- `OpsEvidenceServiceReleaseApprovalRehearsalSandboxConnectionOverviewTests`
- `OpsEvidenceServiceReleaseApprovalRehearsalSummaryOverviewTests`

每个测试类独立构造 `readOnlyFixtureService()`，不共享可变状态；断言仍直接落在测试里，没有把失败点藏进大 helper。

## 行数变化

- 原主文件：1666 行。
- 拆后：
  - `OpsEvidenceServiceReleaseApprovalRehearsalOverviewTests.java`：272 行。
  - `OpsEvidenceServiceReleaseApprovalRehearsalAuditHandoffOverviewTests.java`：437 行。
  - `OpsEvidenceServiceReleaseApprovalRehearsalAdapterGuardOverviewTests.java`：420 行。
  - `OpsEvidenceServiceReleaseApprovalRehearsalSandboxConnectionOverviewTests.java`：505 行。
  - `OpsEvidenceServiceReleaseApprovalRehearsalSummaryOverviewTests.java`：92 行。

## 验证

已执行：

```powershell
mvn -q -DskipTests test-compile
mvn -q "-Dtest=OpsEvidenceServiceReleaseApprovalRehearsalOverviewTests,OpsEvidenceServiceReleaseApprovalRehearsalAuditHandoffOverviewTests,OpsEvidenceServiceReleaseApprovalRehearsalAdapterGuardOverviewTests,OpsEvidenceServiceReleaseApprovalRehearsalSandboxConnectionOverviewTests,OpsEvidenceServiceReleaseApprovalRehearsalSummaryOverviewTests" test
```

结果：通过。测试输出仅包含 Mockito 动态 agent 的 JDK 未来兼容提示。

## 后续建议

Java v130 已继续做 echo catalog 延伸。后续若继续质量止血，可看剩余 500 行以上测试类是否仍能按场景自然切分。
