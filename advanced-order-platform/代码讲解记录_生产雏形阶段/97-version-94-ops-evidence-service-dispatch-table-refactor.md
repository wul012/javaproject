# 第九十四版代码讲解：OpsEvidenceService dispatch table refactor

本版是契约不变的质量重构。目标是把 `OpsEvidenceService` 里继续膨胀的 release/static evidence 构建逻辑移出主类，改成 dispatch table 模式，让主类只负责采样、运行时依赖读取和最终 response 组装。

## 本版所处项目进度

当前 Java 已完成 v93：

```text
managedAuditSandboxConnectionOperatorWindowChecklistEchoReceipt
```

Node 计划仍要求 Java 侧继续避免把新逻辑堆回 `OpsEvidenceService`。本版属于这个质量要求下的主类瘦身，不改变任何 HTTP 字段、schema version、receipt version、只读边界或测试断言。

## 合理性判断

这次拆分合理，原因有三点：

```text
1. OpsEvidenceService 的主要膨胀点已经不是业务执行，而是静态 release evidence 构建。
2. 这些 release/static evidence 条目之间互相独立，天然适合表驱动注册。
3. 重构可以做到契约不变：外部 response 字段和值不变，只移动构建位置。
```

本版没有继续拆 release approval rehearsal receipt 链，因为那部分已经有独立 builder；真正需要收口的是 `/api/v1/ops/evidence` 的静态证据清单。

## 新增 dispatch table

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceStaticReleaseDispatchTable.java
```

它维护一个表：

```text
DISPATCH_TABLE
```

表项按 section 注册：

```text
RELEASE_VERIFICATION
DEPLOYMENT_ROLLBACK
RELEASE_BUNDLE
RELEASE_HANDOFF_CHECKLIST_FIXTURE
RELEASE_AUDIT_RETENTION_FIXTURE
RELEASE_OPERATOR_SIGNOFF_FIXTURE
ROLLBACK_APPROVER_EVIDENCE_FIXTURE
ROLLBACK_APPROVAL_HANDOFF
ROLLBACK_APPROVAL_RECORD_FIXTURE
ROLLBACK_SQL_REVIEW_GATE
PRODUCTION_SECRET_SOURCE_CONTRACT
PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT
```

每个表项绑定一个 builder supplier，最终汇总成 typed snapshot：

```text
StaticReleaseEvidence
```

`OpsEvidenceService` 现在只做：

```text
OpsEvidenceStaticReleaseDispatchTable.build()
```

然后把 snapshot 里的各段 evidence 填进 `OpsEvidenceResponse`。

## 主文件瘦身

本版删除了 `OpsEvidenceService` 内部 500 多行静态构建方法，包括：

```text
releaseVerification()
deploymentRollback()
releaseBundle()
releaseHandoffChecklistFixture()
releaseAuditRetentionFixture()
releaseOperatorSignoffFixture()
rollbackApproverEvidenceFixture()
rollbackApprovalHandoff()
rollbackApprovalRecordFixture()
rollbackSqlReviewGate()
productionSecretSourceContract()
productionDeploymentRunbookContract()
staticContractEndpoints()
staticContractProbeEndpoints()
```

这些逻辑迁移到 dispatch table 后，主文件行数：

```text
1568 -> 1032
```

新增 dispatch table 文件：

```text
638 行
```

这说明本版不是把复杂度藏起来，而是把“静态证据注册表”从“运行时 service 入口”里分离出来。

## 契约保持

保持不变：

```text
OpsEvidenceResponse 字段不变
release approval rehearsal schema 不变
static contract endpoint 顺序不变
readOnly=true
executionAllowed=false
nodeMayTriggerWrites=false
nodeMayTriggerRollback=false
requiresProductionSecrets=false
```

所有原来的 endpoint 清单仍由同一组常量产生：

```text
RELEASE_VERIFICATION_MANIFEST_ENDPOINT
DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT
RELEASE_BUNDLE_MANIFEST_ENDPOINT
RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT
RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT
RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT
ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT
ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT
ROLLBACK_SQL_REVIEW_GATE_ENDPOINT
PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT
```

## 验证重点

本版重点验证：

```text
OpsEvidenceServiceTests
OpsOverviewIntegrationTests
```

因为这两组测试覆盖 `/api/v1/ops/evidence` 和 `/api/v1/ops/release-approval-rehearsal` 对静态 release evidence 的读取。

## 边界

本版不做：

```text
不改变 JSON 字段名
不改变 response schema version
不新增业务能力
不连接 managed audit
不读取 credential value
不执行 SQL
不写 approval ledger
不启动外部服务
```

## 一句话总结

v94 把 `OpsEvidenceService` 中的 release/static evidence 构建改成 dispatch table，主文件从 1568 行降到 1032 行，外部契约保持不变。
