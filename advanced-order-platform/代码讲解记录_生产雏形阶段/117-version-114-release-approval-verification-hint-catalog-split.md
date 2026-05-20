# Java v114 代码讲解：release approval verification hint catalog split

本版目标是处理计划里保留的 Java 中优先级优化项：`ReleaseApprovalVerificationHintBuilder.java` 已经继续膨胀，适合把固定清单从 builder 编排逻辑中拆出去。

## 本版所处项目进度

当前有效计划来自：

```text
D:\nodeproj\orderops-node\docs\plans\v272-post-plan-intake-echo-roadmap.md
```

Java v113 已完成 Node v273 disabled implementation candidate review 的只读 echo receipt。v114 不继续新增业务 receipt，而是做结构优化：

```text
contract-preserving refactor
不改 response schema
不改 warning digest 顺序
不改 proof claims
不改 noLedgerWriteProved
不打开 managed audit connection
```

## 为什么拆这里

`ReleaseApprovalVerificationHintBuilder` 同时拥有三类职责：

```text
构造 verification hint
拼接各 receipt builder 的动态 contribution
保存大量固定 schema/proof/action 字符串清单
```

第三类更像目录，不应该长期堆在 builder 内。继续堆下去，每次新增 echo receipt 都会把 builder 变成“编排 + 字典 + 数据载体”的混合文件。

## 新增 catalog

新增文件：

```text
ReleaseApprovalVerificationHintCatalog.java
```

它只保存固定清单：

```text
schemaFields
warningDigestWarningInputNames
warningDigestBoundaryInputNames
proofClaims
closingProofClaims
nodeVerificationActions
closingNodeVerificationActions
```

这些方法返回的仍是原来同一批字符串，顺序保持不变。builder 现在只是调用 catalog，然后把动态 contribution 插入原来的位置。

## 新增 contribution record

新增文件：

```text
ReleaseApprovalVerificationHintContribution.java
```

它承接原本 builder 内部的 supplier record：

```text
warningDigestWarningInputNames
warningDigestBoundaryInputNames
proofClaims
nodeVerificationActions
```

这一步不改变行为，只是让 builder 少维护一层数据载体。

## builder 现在负责什么

`ReleaseApprovalVerificationHintBuilder` 现在保留真正需要上下文的工作：

```text
持有各 receipt builder
组装 verificationContributions
调用 ReleaseApprovalVerificationWarningDigestBuilder
拼接 warningDigestInputs / proofClaims / nodeVerificationActions
计算 noLedgerWriteProved
返回 RehearsalVerificationHint
```

也就是说，builder 仍然是 orchestration；catalog 是固定目录；contribution 是动态扩展点。

## 行数变化

```text
ReleaseApprovalVerificationHintBuilder.java: 903 -> 648 行
ReleaseApprovalVerificationHintCatalog.java: 269 行
ReleaseApprovalVerificationHintContribution.java: 27 行
OpsEvidenceService.java: 997 行，未增长
```

这是一刀合理拆分：减少主文件阅读压力，但没有把 no-write proof 的长判断拆散到过多文件里。

## 边界保持

本版没有改变这些语义：

```text
credentialValueRead=false
rawEndpointUrlParsed=false
externalRequestSent=false
secretProviderInstantiated=false
resolverClientInstantiated=false
connectsManagedAudit=false
approvalLedgerWritten=false
managedAuditStoreWritten=false
sqlExecuted=false
schemaMigrationExecuted=false
automaticUpstreamStart=false
javaStartedNodeOrMiniKv=false
```

也没有新增 Docker、网络请求、SQL、ledger 写入或 Java 长驻进程。

## 验证

已运行：

```text
mvn -q -DskipTests compile
mvn -q "-Dtest=OpsEvidenceServiceTests,OpsOverviewIntegrationTests" test
```

这两组验证覆盖了编译、ops evidence、release approval rehearsal JSON 暴露和现有 integration path。由于本版是内部拆分，不需要启动 Docker Desktop。

## 一句话总结

Java v114 把 verification hint 的固定 schema/proof/action 目录从 builder 中抽离，让 `ReleaseApprovalVerificationHintBuilder` 回到编排职责，同时保持所有 response contract、digest 顺序和 managed-audit 安全边界不变。
