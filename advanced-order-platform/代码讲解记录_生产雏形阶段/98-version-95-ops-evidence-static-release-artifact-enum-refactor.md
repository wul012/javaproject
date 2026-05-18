# Java v95 说明：OpsEvidence 静态 release 字符串常量收敛为 enum

## 计划依据

本轮依据用户给出的 Java 优先级优化项：

```text
建议 1: String 常量抽 enum
工作量 1版（~150行）
风险 极低
收益 高
```

这一步接在 v94 的 dispatch table 之后，目标是继续把 `OpsEvidenceService` 从“常量仓库”里解放出来。

## 合理性判断

合理。v94 已经把静态 release evidence 的构建逻辑移出主类，但那批版本号和 endpoint 字符串还散在主类里。它们：

```text
是固定契约常量
彼此独立
不依赖运行时状态
适合用 enum 统一承载 version / endpoint
```

所以这次不碰 rehearsal 体系，也不改 response 结构，只做静态常量收敛。

## 本版目标

新增一个包级 enum：

```text
OpsEvidenceStaticReleaseArtifact
```

它统一保存：

```text
version
endpoint
```

然后 `OpsEvidenceStaticReleaseDispatchTable` 和少量 rehearsal 提示直接从 enum 读取。

## 代码改动

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/OpsEvidenceStaticReleaseArtifact.java
```

主类删除的静态常量：

```text
RELEASE_VERIFICATION_MANIFEST_VERSION
RELEASE_VERIFICATION_MANIFEST_ENDPOINT
DEPLOYMENT_ROLLBACK_EVIDENCE_VERSION
DEPLOYMENT_ROLLBACK_EVIDENCE_ENDPOINT
RELEASE_BUNDLE_MANIFEST_VERSION
RELEASE_BUNDLE_MANIFEST_ENDPOINT
RELEASE_HANDOFF_CHECKLIST_FIXTURE_VERSION
RELEASE_HANDOFF_CHECKLIST_FIXTURE_ENDPOINT
RELEASE_AUDIT_RETENTION_FIXTURE_VERSION
RELEASE_AUDIT_RETENTION_FIXTURE_ENDPOINT
RELEASE_OPERATOR_SIGNOFF_FIXTURE_VERSION
RELEASE_OPERATOR_SIGNOFF_FIXTURE_ENDPOINT
ROLLBACK_APPROVER_EVIDENCE_FIXTURE_VERSION
ROLLBACK_APPROVER_EVIDENCE_FIXTURE_ENDPOINT
ROLLBACK_APPROVAL_HANDOFF_VERSION
ROLLBACK_APPROVAL_HANDOFF_ENDPOINT
ROLLBACK_APPROVAL_RECORD_FIXTURE_VERSION
ROLLBACK_APPROVAL_RECORD_FIXTURE_ENDPOINT
ROLLBACK_SQL_REVIEW_GATE_VERSION
ROLLBACK_SQL_REVIEW_GATE_ENDPOINT
PRODUCTION_SECRET_SOURCE_CONTRACT_VERSION
PRODUCTION_SECRET_SOURCE_CONTRACT_ENDPOINT
PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_VERSION
PRODUCTION_DEPLOYMENT_RUNBOOK_CONTRACT_ENDPOINT
```

`OpsEvidenceStaticReleaseDispatchTable` 改为：

```text
version(ARTIFACT)
endpoint(ARTIFACT)
```

`ReleaseApprovalRehearsalResponseBuilder` 仅保留 3 个静态提示 endpoint 的读取。

## 行数结果

```text
OpsEvidenceService.java: 1032 -> 966
OpsEvidenceStaticReleaseDispatchTable.java: 638 -> 659
OpsEvidenceStaticReleaseArtifact.java: 56
```

## 契约保持

保持不变：

```text
/api/v1/ops/evidence 输出结构
/api/v1/ops/release-approval-rehearsal 输出结构
所有静态 contracts endpoint
readOnly=true
executionAllowed=false
```

## 验证记录

本版继续按现有 Java 验证链执行：

```text
mvn -q -DskipTests compile
mvn -q "-Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests" test
mvn -q -DskipTests package
git diff --check
```

## 清理记录

验证产生的 `target/` 会在最终收口前删除；不保留临时产物。

## 一句话总结

v95 继续把静态 release 常量从 `OpsEvidenceService` 收拢到 enum，主类更轻，契约不变。
