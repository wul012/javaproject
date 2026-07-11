# v1851 RuntimeExecution 证据链提取

## 目标

本版把 RuntimeExecution 从候选制品到证据收尾的九级只读链迁入
`com.codexdemo.orderplatform.ops.maintenance.runtimeexecution`。十八个生产类型与九个包内行为测试移动，
Spring 控制器继续留在根 `ops` 包。测试源集新增一个窄 TestSupport，并让根测试工厂委托该唯一构造
入口，消除两套完整服务图装配逻辑。路由、fixture、evidence 路径、响应字段与安全布尔值不变。

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| 九级运行证据链形成闭包 | 9 组 Service/Response 精确迁入 `runtimeexecution` | v1851 exact-list guard | implemented |
| Web 入口留根 | `OpsShardReadinessRuntimeExecutionController` 不移动，只导入公开边界 | 源/目标存在性检查 + 编译器 | implemented |
| 包内测试跟随实现 | 9 个行为测试迁入，新增 1 个公共 TestSupport | 精确目录计数 10 | implemented |
| 测试图只有一个装配所有者 | 根工厂委托 RuntimeExecution TestSupport | source guard + 行为测试 | implemented |
| 上游边界唯一 | ArtifactCandidate 只消费根包 DeclaredOperatorLifecycle Service/Response | import 审计 + 编译器 | implemented |
| 下游边界明确 | ReadOnlyEvidence 与人工证据工作表只读公开常量/Service | import 审计 + focused tests | implemented |
| 路由字节不变 | 继续保留 9 个既有 ENDPOINT 字面量，不新增 route owner | 历史接口测试 + source guard | implemented |
| 归档定位不变 | FIXTURE_ENDPOINT 与 EVIDENCE_PATH 原值保留 | ReadOnlyEvidence 行为测试 | implemented |
| 响应结构不变 | 9 个 Response 原样移动 | 原行为测试 + SpotBugs FQN 镜像 | implemented |
| 公开面最小 | 仅既有 Service/Response 与三类不可变路径常量公开 | v1851 visibility guard | implemented |
| 路径可维护 | 最长主源码/测试绝对路径为 197/201 | 路径测量回执 | implemented |
| 根包继续收缩 | Direct root 500 -> 482；movable 395 -> 377 | live ratchets + endgame census | implemented |
| RuntimeExecution 清零 | RuntimeExecution buckets 18 -> 0，unassigned 0 | census script + v1828 guard | implemented |
| 中文解释先于验证 | v1851 长篇写入五版续写目录 | walkthrough compliance gate | implemented |
| 完整质量门 | focused、Spotless、`mvnw verify` | 回执验证后补录 | pending |

## 依赖链闭合

```text
DeclaredOperatorLifecycle
  -> ArtifactCandidate
  -> PacketContribution
  -> ApprovalGateInput
  -> ApprovalInputContractHandoff
  -> ApprovalInputTemplateCompatibility
  -> ApprovalInputTemplateCompatibilityIntake
  -> ApprovalInputValueValidation
  -> LiveReadGate
  -> PassEvidenceCloseout
  -> ReadOnlyEvidence (v1852 candidate)
```

九个服务按历史版本 v162 至 v170 严格串联。每一级读取前一级不可变 Response，补充本级证据，再输出
新的不可变 Response；不存在数据库写入、消息发布、进程启动、凭据读取或任意地址访问。迁移后包内
依赖仍保持同一方向，根控制器只负责 HTTP 适配，人工证据工作表和只读证据目录只能读取公开端点或
路径常量。下一版 ReadOnlyEvidence 因而可以只消费一个已经稳定的 RuntimeExecution 包。

## 路径与公开面

新包最长主源码路径 197 字符，最长测试路径 201 字符，均给 Windows 编译输出和测试报告保留余量。
生产公开类型仍是九组既有 Service/Response。过去在同一根包内可见的 `ENDPOINT`、
`FIXTURE_ENDPOINT`、`EVIDENCE_PATH` 现在只有跨包只读目录确实需要，因此改为 `public static final`；
没有公开计算方法、构造辅助器、执行开关或可变集合。SpotBugs 只迁移原有八个 Response 排除项的
完整类名，两份镜像规则都保留，没有扩大通配范围。

测试源集中新增的 TestSupport 是唯一公共测试装配边界。它构造从证据索引到 PassEvidenceCloseout
的完整只读链；根测试工厂改为委托该入口，不再复制九级构造代码。这样 v1852 的 ReadOnlyEvidence
测试也能复用同一可信图，而生产代码不会因此增加任何测试 API。

## 普查变化

```text
Direct root 500 -> 482
Retained root 105 -> 105
movable 395 -> 377
RuntimeExecution buckets 18 -> 0
Unassigned 0 -> 0
Total ops main Java <= 1352
```

v1828 endgame census 的当前值和两个 RuntimeExecution bucket 同步收紧，并追加 v1851 历史段；
v1828 原始基线与 v1843-v1850 历史变化保持不变。三处全局 live ratchet、v1848-v1850 活守卫和
v1851 精确守卫全部收紧到 482，任何回迁根包的文件都会使测试失败。

## 失败条件

控制器被迁出、十八个实现不完整、测试仍散落根包、测试图出现第二套九级装配、下游读取包私有
实现、任一端点或归档路径变化、Response 字段变化、根包高于 482、任一 RuntimeExecution bucket
非零、unassigned 非零、总文件数超过 1352、讲解不足 3000 汉字，或 focused、Spotless、verify 任一
失败，都禁止关账。不得通过降低计数、删除断言、放宽 SpotBugs 或改写 fixture 来让门通过。

## 验证命令

```powershell
.\mvnw.cmd "-Dtest=*RuntimeExecution*Tests,*ReadOnlyEvidence*Tests,*ManualEvidenceWorksheet*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1850Tests,ReadabilityUpkeepOpsConsolidationExtractionV1851Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepOpsConsolidationExtractionV1809Tests,OpsCodeWalkthroughArchiveComplianceTests" test
.\mvnw.cmd spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```

## 验证回执（完整 verify 前）

- `test-compile`：BUILD SUCCESS；1,483 个主源码与 848 个测试源码编译通过。
- census：DirectRoot 482、Retained 105、Remaining 377、两个 RuntimeExecution bucket 均为 0、
  Unassigned 0。
- focused test：97 tests，0 failures，0 errors，耗时 1m52s。
- 首次完整 verify 运行 1,771 tests，其中 1,770 通过；唯一失败是 v1847 历史守卫仍把当前
  shrink-only 根计数钉在 500。保留其 v1847 历史变化文字，只把活计数收紧到 482 后重跑。
- 首次机械 import 重映射受既有超长路径影响而超时；改用 Windows 扩展路径执行同一幂等转换后，
  编译一次通过，未改业务逻辑。
- 文档和 4,837 汉字中文讲解在完整 verify 前写入，不预写完整门通过结论。

## 最终本地回执

- `spotless:check`：BUILD SUCCESS，62 个受影响 Java 文件全部干净。
- `mvnw verify`：第二次完整运行 BUILD SUCCESS，耗时 9m29s；1,771 tests，0 failures，
  0 errors，0 skipped。
- JaCoCo：分析 2,227 个类，all coverage checks met。
- SpotBugs：BugInstance 0、Error 0、no errors/warnings found。
- 最终 census：DirectRoot 482、Retained 105、Remaining 377、两个 RuntimeExecution bucket
  均为 0、Unassigned 0；总 `ops` 主源码仍不超过 1,352。
- 文档和 4,837 汉字讲解均先于完整 verify；本段只在成功后补录。
