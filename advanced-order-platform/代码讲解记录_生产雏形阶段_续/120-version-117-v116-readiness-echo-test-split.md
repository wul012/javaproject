# Java v117 代码讲解：v116 readiness echo 测试拆分

本版不推进新业务计划，目标是拆分总行数最高的测试文件。上一版 v116 的功能本身合理，但专项断言继续堆在 `OpsEvidenceServiceTests` 和 `OpsOverviewIntegrationTests` 里，会让两个文件越来越难维护。

## 拆分前的问题

拆分前行数：

```text
OpsEvidenceServiceTests.java: 6758 行
OpsOverviewIntegrationTests.java: 5290 行
```

v116 新增的 readiness echo 测试有两个特点：

```text
断言很多，适合独立成主题测试
依赖固定 release rehearsal request / fixture service，可复用
```

所以这一刀优先拆测试，而不是先拆生产代码。

## 新增测试 fixture

新增：

```text
OpsEvidenceServiceTestFixtures.java
```

它集中提供：

```text
readOnlyFixtureService(...)
headerBackedRehearsalRequest()
paddedHeaderBackedRehearsalRequest()
```

旧 `OpsEvidenceServiceTests` 底部保留同名私有方法，但方法体变成一行代理调用。这样既不需要一次性改大量老测试调用点，又能把长 fixture 构造从大文件移出去。

## 新增 v116 单元测试类

新增：

```text
OpsEvidenceServiceApprovalRequiredImplementationReadinessEchoTests.java
```

它承接 v116 receipt 的核心 record 断言：

```text
Node v281 / Node v275 来源回显
6 个 approval-required boundary
18 个 required artifact
sideEffectBoundary 全部保持 false
readyForNodeV282=true
readyForManagedAuditResolverImplementation=false
verificationHint 收录 schemaFields / warningDigestInputs / proofClaims / nodeVerificationActions
receiptDigest 对 padded request 稳定
```

迁出时顺手压缩了部分重复断言，用 `assertRuntimeBlocked(...)` 表达同类 false 边界，避免新文件也变成大块布尔铺陈。

## 新增 v116 MVC 测试类

新增：

```text
OpsReleaseApprovalCredentialResolverReadinessIntegrationTests.java
```

它承接：

```text
GET /api/v1/ops/release-approval-rehearsal
managedAuditSandboxEndpointCredentialResolverApprovalRequiredImplementationReadinessEchoReceipt JSON 暴露
verificationHint 对 v116 receipt 的收录
```

原 `OpsOverviewIntegrationTests` 删除这段 v116 endpoint 测试后，`hasSize` 静态导入也可以移除。

## 行数结果

```text
OpsEvidenceServiceTests.java: 6758 -> 6367 行
OpsOverviewIntegrationTests.java: 5290 -> 5185 行
OpsEvidenceServiceApprovalRequiredImplementationReadinessEchoTests.java: 231 行
OpsReleaseApprovalCredentialResolverReadinessIntegrationTests.java: 128 行
OpsEvidenceServiceTestFixtures.java: 179 行
```

这是一刀低风险拆分：生产代码没有动，测试覆盖没有减少，后续如果继续拆，可以按“一个 release approval receipt 一个专项测试类”的方式推进。

## 验证

已运行：

```text
mvn -q "-Dtest=OpsEvidenceServiceTests,OpsEvidenceServiceApprovalRequiredImplementationReadinessEchoTests,OpsOverviewIntegrationTests,OpsReleaseApprovalCredentialResolverReadinessIntegrationTests" test
mvn -q test
```

全部通过。Testcontainers 仍打印 Docker 不可用探测日志，但退出码为 0，本版没有必要启动 Docker Desktop。

## 一句话总结

Java v117 没改 v116 的 readiness echo 语义，只把对应单元和 MVC 测试从两个巨型测试文件里拆出来，并抽出公共 fixture，为后续继续拆测试文件铺路。
