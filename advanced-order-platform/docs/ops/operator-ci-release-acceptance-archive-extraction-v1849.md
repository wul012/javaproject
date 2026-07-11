# v1849 Operator-CI ReleaseAcceptanceArchive 提取

## 目标

本版把 ReleaseAcceptance 的归档登记层从根 `ops` 包迁入
`com.codexdemo.orderplatform.ops.maintenance.ciarc`。23 个非控制器实现和 6 个包内测试跟随移动，
Spring 控制器与控制器 Markdown 测试留根。路由、响应字段、检查顺序、状态词和安全边界不变。

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| 归档实现归属单包 | 23 个实现精确迁入 `ops.maintenance.ciarc` | v1849 exact-list guard | implemented |
| 控制器保持根可见 | Controller 与 Markdown controller test 不移动 | 源/目标存在性断言 | implemented |
| 包内测试跟随实现 | 6 个行为测试与 TestSupport 迁入 | 精确测试清单和目录计数 | implemented |
| 依赖方向单向 | Archive 只消费 v1848 Service/Response；Handoff 只消费 Archive Service/Response | import guard + 编译器 | implemented |
| 路由字节不变 | Service 直接读取既有公共 ReleaseAcceptance route owner | 历史 route tests + v1849 owner guard | implemented |
| 响应结构不变 | Archive response 原样移动 | 家族行为测试 + SpotBugs 双镜像 | implemented |
| Windows 路径受控 | 直译包 `354/361`，`ciarc` 为 `252/259` | 文档与守卫固定预算 | implemented |
| 收紧根包预算 | Direct root 548 -> 525；movable 443 -> 420 | 三处 live ratchet + endgame census | implemented |
| 清空 Operator-CI bucket | bucket 23 -> 0，unassigned 仍为 0 | census script + v1828 guard | implemented |
| 中文讲解先于验证 | v1849 中文长篇写入同一五版续写目录 | walkthrough compliance gate | implemented |
| 完整质量门 | focused、Spotless、`mvnw verify` | 回执验证后补录 | pending |

## 依赖边界

```text
operatorcidossier (v1847)
  -> ciaccept (v1848)
  -> ciarc (v1849)
  -> ReleaseAcceptanceArchiveVerificationHandoff (v1850 queued)
```

Archive 的运行时输入只有 v1848 ReleaseAcceptanceRegistryService 返回的 Response。它把发布验收
结果投影为来源快照、artifact manifest、route packages、operator packs、CI attestations、boundary
seals、retention windows、closeout ledger 和 scorecard。下一层 Handoff 只得到 Archive 的公开
Service/Response，不得读取内部 catalog、renderer 或 support。

## 路径预算

完整业务前缀直译为包名时，最长主源码/测试路径达到 `354/361`。采用 `ciarc` 后降为
`252/259`。`ciarc` 在本项目上下文中表示 CI acceptance archive；缩写只改变内部 FQN，公开类名、
JSON 字段和 URL 均保持原样。

## 路由和响应

Service 从根聚合器改为直接读取 v1840 公共
`OpsShardReadinessReleaseAcceptanceRoutePaths`。根控制器仍通过根聚合器映射，而聚合器委托同一常量，
所以最终 URL 字节不变。Archive Response record 的字段、嵌套记录和列表构造顺序全部原样移动；
SpotBugs 中 Response 与 MarkdownSection 的两组镜像仅更新 FQN。

## 普查变化

```text
Direct root 548 -> 525
Retained root 105 -> 105
movable 443 -> 420
Operator-CI bucket 23 -> 0
ReleaseAcceptanceArchiveVerificationHandoff bucket 25 -> 25
Unassigned 0 -> 0
Total ops main Java <= 1352
```

endgame census 的当前值、bucket 和历史进度新增 v1849 记录；v1828 原始 874/769 基线及
v1843-v1848 历史数列均保留。所有 ratchet 只下降。

## 失败条件

控制器被迁移、Handoff 导入包私有类型、路由字节变化、响应字段变化、根包高于 525、Operator-CI
bucket 非零、出现未分类文件、总文件数超过 1352、讲解不足 3000 汉字，或者 focused、Spotless、
完整 verify 任一失败，都不得关账。

## 验证命令

```powershell
.\mvnw.cmd "-Dtest=*ReleaseAcceptanceArchiveRegistry*Tests,*ReleaseAcceptanceArchiveVerificationHandoff*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1848Tests,ReadabilityUpkeepOpsConsolidationExtractionV1849Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepOpsConsolidationExtractionV1809Tests,OpsCodeWalkthroughArchiveComplianceTests" test
.\mvnw.cmd spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```

## 验证回执（完整 verify 前）

- 中文讲解实测 3,283 个汉字，十个标准章节齐全。
- census：DirectRoot 525、Retained 105、Remaining 420、Operator-CI 0、Handoff 25、
  Unassigned 0。
- focused test：54 tests，0 failures，0 errors，耗时 1m11s。
- 本文档和讲解均在完整 verify 前写入，不预写通过结论。

## 最终本地回执

- `spotless:check`：BUILD SUCCESS，50 个受影响 Java 文件全部干净。
- `mvnw verify`：BUILD SUCCESS，耗时 14m27s；1,760 tests，0 failures，0 errors，
  0 skipped。
- JaCoCo：分析 2,227 个类，all coverage checks met。
- SpotBugs：BugInstance 0、Error 0、no errors/warnings found。
- 文档和 3,283 汉字讲解均先于完整 verify；本段只在命令成功后补录。
