> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# version 85 release approval rehearsal builder split

## 1. 版本目标

v85 是纯重构版本：继续拆 `OpsEvidenceService`，把 release approval rehearsal 的响应组装和早期 hint / handoff / taxonomy 构造迁到专用 builder。
本次不新增响应字段，不升级 schema，不改变 warning digest、proof claims 或只读边界。

## 2. 新增类

```java
ReleaseApprovalRehearsalResponseBuilder
ReleaseApprovalRehearsalHintBuilder
ReleaseApprovalRehearsalHandoffHintBuilder
ReleaseApprovalRehearsalFailureTaxonomyBuilder
```

职责划分：

- `ReleaseApprovalRehearsalResponseBuilder` 是 release approval rehearsal 的组装入口，接收 `OpsEvidenceResponse` 和 header 原值，内部完成归一化和 response record 组装。
- `ReleaseApprovalRehearsalHintBuilder` 构造 request context、operator window、CI evidence、artifact retention、live readiness。
- `ReleaseApprovalRehearsalHandoffHintBuilder` 构造 managed audit persistence handoff、approval record handoff、approval handoff marker。
- `ReleaseApprovalRehearsalFailureTaxonomyBuilder` 构造 upstream/auth/audit correlation taxonomy。

## 3. 主服务变化

`OpsEvidenceService.releaseApprovalRehearsal(...)` 现在只做一件事：获取 `evidence()`，然后委托给 `ReleaseApprovalRehearsalResponseBuilder`。

```java
return new ReleaseApprovalRehearsalResponseBuilder()
        .build(evidence(), requestId, operatorIdentity, auditCorrelationId, ...);
```

这样对外 public overloads 保持不变，controller 和测试调用方式不变，但主 service 不再承载几百行 rehearsal constructor 链。

## 4. 代码规模

```text
OpsEvidenceService.java: 2605 -> 1443 (-1162)
ReleaseApprovalRehearsalResponseBuilder.java: 367
ReleaseApprovalRehearsalHintBuilder.java: 468
ReleaseApprovalRehearsalHandoffHintBuilder.java: 407
ReleaseApprovalRehearsalFailureTaxonomyBuilder.java: 60
```

这轮已经达到 1500 行左右目标，并且新拆出的类都保持在可继续阅读和继续拆分的范围内。

## 5. 契约保护

保持不变：

- release approval rehearsal URL
- response record shape
- schema version
- warning digest input order
- no-ledger-write proof
- Node verification actions
- all read-only/no-write/no-connection/no-SQL/no-credential flags

## 6. 验证

已验证：

```text
mvn -q -DskipTests compile
mvn -q '-Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests' test
mvn -q -DskipTests package
git diff --check
```
