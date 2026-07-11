# v1852 ReadOnlyEvidence 目录链提取

## 目标

本版把 ReadOnlyEvidence 的目录、交接、交接验证和端点完整性闭包迁入
`com.codexdemo.orderplatform.ops.maintenance.readonlyevidence`。十一份生产类型与七个包内测试移动，
Spring 控制器和 ControllerSplit 测试留根。测试源集新增一个窄 TestSupport，使根历史测试继续读取
v175、v179、v184 冻结列表，同时保持三份生产 Snapshot 为 package-private。

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| ReadOnlyEvidence 形成闭包 | 11 个 Service/Response/Snapshot 精确迁入 `readonlyevidence` | v1852 exact-list guard | implemented |
| Web 适配器留根 | Controller 与 ControllerSplit test 不移动 | 源/目标存在性检查 | implemented |
| 包内测试跟随实现 | 7 个行为/快照测试迁入 | 目标测试目录精确清单 | implemented |
| 生产快照不公开 | 3 个 Snapshot 保持 package-private | source visibility guard | implemented |
| 历史测试仍可复核 | TestSupport 转发 6 个冻结 endpoint 列表 | V1 历史测试 + support guard | implemented |
| 测试服务图唯一 | 根工厂委托 ReadOnlyEvidence TestSupport | factory source guard | implemented |
| v184 pair 解耦 | integrity snapshot 自有 package-private EndpointPair | source guard + 原行为测试 | implemented |
| 上游边界明确 | 只读取根 readiness 核心与 v1851 RuntimeExecution 不可变证据 | import 审计 + 编译器 | implemented |
| 路由字节不变 | 根全局 route owner 继续持有 4 个原后缀，仅收窄公开 | route tests + source guard | implemented |
| 归档字节不变 | v175/v177/v179/v184 fixture 与 evidence 路径原值保留 | 快照测试 + 历史兼容测试 | implemented |
| 响应结构不变 | 4 个 Response 原样移动 | 行为测试 + SpotBugs 双镜像 | implemented |
| 路径可维护 | 最长主源码/测试绝对路径为 183/188 | 路径测量回执 | implemented |
| 根包继续收缩 | Direct root 482 -> 471；movable 377 -> 366 | live ratchets + census | implemented |
| ReadOnlyEvidence 清零 | ReadOnlyEvidence bucket 11 -> 0，unassigned 0 | census script + v1828 guard | implemented |
| 中文解释先于验证 | v1852 长篇写入五版续写目录 | walkthrough compliance gate | implemented |
| 完整质量门 | focused、Spotless、`mvnw verify` | 回执验证后补录 | pending |

## 依赖链闭合

```text
readiness core + runtimeexecution v1851
  -> ReadOnlyEvidenceCatalog v175
  -> CatalogHandoff v177
  -> HandoffVerification v179
  -> EndpointRegistryIntegrity v184
  -> V1Contract historical/read-only consumers
```

Catalog 冻结 v175 的二十组 endpoint、fixture 与 evidence 引用，并读取 v1851 最终运行证据收尾；
Handoff 把冻结目录转换为 Node 可消费的只读交接；Verification 比较冻结目录和当前 v179 注册表；
Integrity 以 v184 的二十三组 pair 检查数量、唯一性与成对完整性。四层都只读，不创建路由、审批、
连接、部署或回滚能力。

## 快照与测试边界

三份 Snapshot 是生产实现细节，不能因跨包测试而改成 public。本版新增测试源集 TestSupport，公开六个
`List<String>` 读取方法和四个服务工厂。根历史测试改读 TestSupport；包内测试仍直接验证快照。该
支持类不会进入生产 jar。v184 Snapshot 过去借用了根包私有 `EvidenceEndpoints.EndpointPair`；迁移后
改用自身 package-private record，Service 仍按同样 live/fixture 字段检查，响应字节不变。

根 `OpsShardReadinessServiceGraphTestFactory` 的三套 ReadOnlyEvidence 构造方法改为委托新 TestSupport。
TestSupport 又复用 v1851 RuntimeExecution TestSupport，因此从运行证据到目录交接只保留一套完整测试
装配，未来构造器变化不会要求维护第三份重复服务图。

## 路由与公开面

四个 Service 继续通过保留根包的全局 `OpsShardReadinessRoutePaths` 组合 BASE_PATH 和原后缀。该类
本来就是 endgame 明确保留的共享 route aggregator；本版只把四个不可变后缀改成 public，没有新建
重复 owner，也没有增加主源码总数。四个 Service 及十个根 readiness 核心 Service 的 endpoint、fixture、
evidence 常量只因 Snapshot 跨包读取而公开，计算方法和可变状态仍不公开。

SpotBugs 中四个 Response 的两份镜像只迁移完整类名，不删除规则、不扩大匹配。最长路径 183/188，
总 `ops` 主源码保持 1,352。

## 普查变化

```text
Direct root 482 -> 471
Retained root 105 -> 105
movable 377 -> 366
ReadOnlyEvidence bucket 11 -> 0
Unassigned 0 -> 0
Total ops main Java <= 1352
```

v1828 census 更新当前值、桶值和 v1852 历史段；v1828 原始基线与 v1843-v1851 历史数字不改。全局
活棘轮、v1847-v1851 活守卫和新 v1852 守卫全部收紧到 471。

## 失败条件

控制器被迁出、十一文件闭包不完整、ControllerSplit 测试被误迁、生产 Snapshot 变 public、历史测试
直接跨包读取 Snapshot、v184 pair 继续依赖根私有类型、路由或归档路径变化、Response 字段变化、根包
高于 471、ReadOnlyEvidence bucket 非零、unassigned 非零、总文件数超过 1,352、讲解不足 3000 汉字，
或 focused、Spotless、verify 任一失败，都禁止关账。不得通过公开全部内部类、改 fixture 或降低断言
解决编译问题。

## 验证命令

```powershell
.\mvnw.cmd "-Dtest=*ReadOnlyEvidence*Tests,*ReadOnlyEndpointRegistryIntegrity*Tests,*HistoricalEndpointSnapshotCompatibilityTests,*V1Contract*HistoricalCompatibilityTests,ReadabilityUpkeepOpsConsolidationExtractionV1851Tests,ReadabilityUpkeepOpsConsolidationExtractionV1852Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepOpsConsolidationExtractionV1809Tests,OpsCodeWalkthroughArchiveComplianceTests" test
.\mvnw.cmd spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```

## 验证回执（完整 verify 前）

- 首次 `test-compile`：1,483 个生产源码通过；唯一测试编译错误是一个旧工厂方法名未重映射。
- 修正该调用为新 TestSupport 的 `handoffVerificationService()` 后，1,483 个主源码与 850 个测试
  源码编译通过；断言未改。
- 首次 focused 运行 77 tests，其中 76 通过；唯一失败是新 v1852 守卫错误要求留根
  ControllerSplit 测试导入内部包。该测试只反射留根控制器，守卫改为核对控制器类型与四条原路由，
  业务测试和路由断言未改。
- 最终 focused test：77 tests，0 failures，0 errors，耗时 1m29s。
- 首次完整 verify 运行 1,778 tests，其中 1,777 通过；唯一失败是四个后缀公开后，Google 格式化
  使根 RoutePaths 从 1,111 增至 1,112 行。保持 1,111 行预算不变，删除同一核心常量区的一条分组
  空行后重跑；路由常量和值未改。
- 修复后 RoutePaths 实测 1,111 行；维护预算、路由、v1852、全局棘轮与 census 共 18 tests 全绿。
- census：DirectRoot 471、Retained 105、Remaining 366、ReadOnlyEvidence 0、Unassigned 0。
- 文档与 3,704 汉字中文讲解在完整 verify 前写入，不预写通过结论。

## 最终本地回执

- `spotless:check`：BUILD SUCCESS，61 个受影响 Java 文件全部干净。
- `mvnw verify`：第二次完整运行 BUILD SUCCESS，耗时 10m10s；1,778 tests，0 failures，
  0 errors，0 skipped。
- JaCoCo：分析 2,228 个类，all coverage checks met。
- SpotBugs：BugInstance 0、Error 0、no errors/warnings found。
- 最终 census：DirectRoot 471、Retained 105、Remaining 366、ReadOnlyEvidence 0、
  Unassigned 0；总 `ops` 主源码仍不超过 1,352。
- 文档和 3,704 汉字讲解均先于完整 verify；本段只在成功后补录。
