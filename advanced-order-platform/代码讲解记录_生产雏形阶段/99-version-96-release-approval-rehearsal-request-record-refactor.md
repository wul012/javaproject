> 清算状态：`legacy-nonstandard-walkthrough`。这是一份历史非标准讲解，保留作追溯参考；未按 `代码讲解记录_写作规范.md` 重写前，不计为标准代码讲解。

# Java v96 说明：release approval rehearsal request record refactor

## 计划依据

本轮依据 Java 后续优化建议：

```text
建议 2: 消除 18-null overload
风险 低
收益 高
```

v95 已经把静态 release 常量收敛为 enum，本版继续处理 `OpsEvidenceService` 里更影响维护的长参数 overload 链。

## 合理性判断

合理。原实现为了兼容 rehearsal 请求头逐步增加，保留了多层 `releaseApprovalRehearsal(...)` overload，并靠大量 `null` 补位转发到最终方法。问题是：

```text
参数顺序难读
新增 header 容易放错位置
service 主类继续膨胀
builder 入口也暴露过多字符串参数
```

这些输入本质上是“请求上下文”，适合用 record 按语义分组。

## 本版目标

新增：

```text
ReleaseApprovalRehearsalRequest
```

按语义分组：

```text
Context
OperatorWindow
CiEvidence
ArtifactRetention
RuntimeReadiness
ManagedAudit
ApprovalBinding
```

`OpsOverviewController` 仍然逐个读取 HTTP header，但内部组装成 request record 传给 service。

## 代码改动

新增文件：

```text
src/main/java/com/codexdemo/orderplatform/ops/ReleaseApprovalRehearsalRequest.java
```

调整：

```text
OpsEvidenceService.releaseApprovalRehearsal()
OpsEvidenceService.releaseApprovalRehearsal(ReleaseApprovalRehearsalRequest request)
ReleaseApprovalRehearsalResponseBuilder.build(OpsEvidenceResponse evidence, ReleaseApprovalRehearsalRequest request)
OpsOverviewController.releaseApprovalRehearsal(...)
OpsEvidenceServiceTests header-backed rehearsal fixture
```

删除：

```text
多层 String 参数 releaseApprovalRehearsal overload
靠 null 补位的转发链
builder 的 33 个字符串参数入口
```

## 行数结果

```text
OpsEvidenceService.java: 966 -> 606
ReleaseApprovalRehearsalRequest.java: 112
ReleaseApprovalRehearsalResponseBuilder.java: 460
OpsOverviewController.java: 211
```

## 契约保持

保持不变：

```text
/api/v1/ops/release-approval-rehearsal URL 不变
所有 request header 名称不变
response 字段不变
trim/normalize 行为不变
warning digest 稳定性不变
read-only/no-ledger/no-SQL/no-connection 边界不变
```

## 验证记录

本版执行：

```text
mvn -q -DskipTests compile
mvn -q "-Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests" test
mvn -q -DskipTests package
git diff --check
```

## 清理记录

验证产生的 `target/` 会在最终收口前删除；不保留临时产物。

## 一句话总结

v96 用 request record 收掉 rehearsal 的长参数和 null overload，主类大幅瘦身，外部 HTTP 契约不变。
