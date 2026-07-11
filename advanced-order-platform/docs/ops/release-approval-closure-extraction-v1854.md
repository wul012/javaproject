# v1854 ReleaseApproval closure extraction

## Scope

v1854 将根目录中完整的 ReleaseApproval 实现闭包迁入
`com.codexdemo.orderplatform.ops.maintenance.releaseapproval`。迁移对象包括 118 个
`ReleaseApproval*.java` 生产文件、5 个包内行为测试、1 个共享 rehearsal 测试支持，
以及已经只服务于该家族的 `ContextHeaderField`。Spring 入口仍由根目录
`OpsOverviewController` 暴露，根级 `OpsEvidenceService` 仍负责组合公开的 request、
response 和 response builder；路由、响应字段、证据字符串、fixture 字节和只读边界均
不改变。

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| 整体迁出 ReleaseApproval 实现 | 118 个根级家族文件迁入 `ops.maintenance.releaseapproval`，controller 不随实现下沉 | `OpsExtractionV1854Tests.completeReleaseApprovalClosureMovesOutOfDirectRoot` | implemented |
| 收紧根目录和最终目标 | Direct root 429 -> 310；movable 324 -> 206；最终保留目标 105 -> 104 | 三组 live ratchet、`scripts/ops-root-census.ps1 -Json`、v1828 census guard | implemented |
| 清空家族桶 | ReleaseApproval buckets 118 -> 0，unassigned 保持 0 | census 脚本和 `ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests` | implemented |
| 撤销过期共享豁免 | ContextHeaderField waiver 1 -> 0；类型改名为包私有 `ReleaseApprovalContextHeaderField` | `extraction-waivers.md` 和 v1854 guard 同时禁止根文件回归 | implemented |
| 消除反向根服务依赖 | 316 个不同常量由两个家族目录持有；迁入包对 `OpsEvidenceService` 的引用为 0 | v1854 guard 扫描全部 119 个包内生产文件 | implemented |
| 控制目录文件体积 | Java 契约目录 89 项、上限 400 行；Node 上游目录 227 项、上限 800 行 | `constantsAndHeaderNormalizationBelongToTheExtractedFamily` | implemented |
| 总文件数不反弹 | 纯转发 execution-denied builder 收进对应 support 的嵌套 `ReceiptBuilder`，抵消第二个目录文件 | v1854 guard 检查旧文件消失、新嵌套类型存在；总 ops main <= 1,352 | implemented |
| 测试边界归位 | `ReleaseApprovalRehearsalTestSupport` 随家族移动；28 个根测试显式导入它 | test compile 和 `forwardingBuilderAndSharedTestFixtureStayNarrow` | compile passed |
| 拆除测试热点 | 1,340/995 行的两个单方法 overview 按证据阶段拆成 7 个测试文件 | maintainability 门保持 854 / 8 / 2 / 0 原上限 | implemented |
| 静态分析路径同步 | 180 个 SpotBugs ReleaseApproval FQN 指向新包；6 个 maintainability 路径只改路径不放宽上限 | v1854 guard 对新旧路径作正反断言 | implemented |
| 最终质量门 | Spotless、聚焦测试、完整 `mvnw verify`、实现 CI、closeout CI | 本地 1,799 tests、JaCoCo 2,228 classes、SpotBugs 0；实现 run `29155544134` 已绿，closeout run 由标签提交触发 | complete |

## Ownership design

原实现把 rehearsal 自身的版本、schema 和 endpoint，以及 Node v210-v329 的上游证据
标识，全部放在 `OpsEvidenceService`。只要 builder 与 service 同包，这种依赖不会被
编译器暴露；一旦家族迁出，数百个包私有字段就成为真实的反向边。v1854 没有把这些
字段批量改成 `public`，而是把 89 个 Java 侧契约字段放入
`ReleaseApprovalContractConstants`，把 227 个 Node 上游证据字段放入
`ReleaseApprovalUpstreamContractConstants`。两者均为无状态、不可实例化、仅含
`public static final String` 的目录边界。

`OpsEvidenceService` 过去已经公开的
`RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION`
仍保留为公开兼容别名，但真实值由家族目录持有。这样已有源码和可能存在的外部编译
依赖不失效，新的家族实现也不再依赖根服务。其余原包私有字段没有公共兼容义务，直接
从根服务移除。

## Maintainability changes

原 `ReleaseApprovalEchoMarkerSupport` 的列表、warning 和 workflow 小工具与 digest
规范共同服务于同一证据摘要过程，因此并入 `ReleaseApprovalDigestSupport`。释放出的
文件位成为 Java 契约目录。为避免再增加一个生产文件，原 execution-denied receipt
builder 这个只转发静态 support 方法的薄壳被折叠成
`ReleaseApprovalSandboxEndpointCredentialResolverExecutionDeniedEchoSupport.ReceiptBuilder`；
释放出的文件位成为 Node 上游目录。两个动作都不改变调用顺序和返回值，只让物理文件
与职责边界更接近。

迁包触发当前 Spotless 规则后，两个历史 overview 测试分别显露为 1,340 行和 995 行。
v1854 没有提高测试源码预算，而是按 context、managed-audit guard、external boundary、
sandbox handoff、precondition 和 checklist 等证据阶段拆成 7 个测试文件。原断言和值
全部保留；最终全项目测试源码仍满足最大 854 行、超过 500 行最多 8 个、超过 750 行
最多 2 个、超过 1,000 行为 0 的既定预算。

## Compatibility and safety boundary

- `OpsOverviewController` 的 HTTP 入口和方法签名不变。
- `ReleaseApprovalRehearsalRequest`、`ReleaseApprovalRehearsalResponse` 及其嵌套 record
  的字段顺序不变。
- 所有常量初始化表达式按原文本迁移，不重写 fixture，不改 digest 输入，不改 warning
  顺序。
- `OpsEvidenceStaticReleaseArtifact` 仅为已存在的三个不可变 enum 值提供跨包读取能力；
  不新增运行时写操作。
- 不读取 credential value，不解析 raw endpoint，不建立 managed-audit 连接，不执行
  SQL，不启动 Node、Java 或 mini-kv 进程。
- 不改部署、回滚、归档目录和历史 tag。

## Reproducible checks

```powershell
.\scripts\ops-root-census.ps1 -Json
.\mvnw.cmd -q -DskipTests test-compile
.\mvnw.cmd -q -Dtest='*ReleaseApproval*,Release*Tests,OpsExtractionV1854Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepOpsConsolidationExtractionV1809Tests,JavaMaintainabilityBudgetTests' test
.\mvnw.cmd verify
```

本文件在最终 verify 之前写入。任何 focused 或 full gate 失败都必须修复实现、导入、
路径或证据账本；禁止放宽 ratchet、修改 fixture 字节或降低测试期望来获得绿色结果。
