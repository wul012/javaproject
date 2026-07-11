# v1850 ReleaseAcceptanceArchiveVerificationHandoff 提取

## 目标

本版把 ReleaseAcceptanceArchive 的验证交接层迁入
`com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff`。25 个实现和 6 个包内测试移动，
Spring 控制器与控制器 Markdown 测试留根。该层只消费 v1849 Archive 的公开 Service/Response，
下游既有 RoutePathSplit 只消费本层公开 Service/Response。

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| Handoff 实现形成闭包 | 25 个实现精确迁入 `releasearchivehandoff` | v1850 exact-list guard | implemented |
| Web 适配器留根 | Controller 与 controller Markdown test 不移动 | 源/目标存在性检查 | implemented |
| 包内测试跟随实现 | 6 个行为测试与 TestSupport 迁入 | 目录精确计数 | implemented |
| 上游边界唯一 | 只导入 v1849 `ciarc` Service/Response | service import + 编译器 | implemented |
| 下游边界唯一 | RoutePathSplit 只导入 Handoff Service/Response | 三个下游文件守卫 | implemented |
| 路由字节不变 | 继续使用公共 ReleaseAcceptance route owner | 历史 route tests + source guard | implemented |
| 响应结构不变 | Handoff Response 原样移动 | 原行为测试 + SpotBugs 双镜像 | implemented |
| 路径可维护 | 直译包 `222/234`，选定包 `200/212` | 文档与 v1850 守卫 | implemented |
| 根包继续收缩 | Direct root 525 -> 500；movable 420 -> 395 | live ratchets + endgame census | implemented |
| 独立 bucket 清零 | Handoff bucket 25 -> 0，unassigned 0 | census script + v1828 guard | implemented |
| 中文解释先于验证 | v1850 中文长篇写入五版续写目录 | walkthrough compliance gate | implemented |
| 完整质量门 | focused、Spotless、`mvnw verify` | 回执验证后补录 | pending |

## 依赖链闭合

```text
operatorcidossier v1847
  -> ciaccept v1848
  -> ciarc v1849
  -> releasearchivehandoff v1850
  -> releaseacceptanceroutepathsplit v1840 (already extracted consumer)
```

Handoff 把 Archive 的来源、要求、制品、路由、操作员、CI、边界、保留和收尾证据转换为可交接
Response。它不读取 Archive 内部 catalog，也不绕过 Archive 回到 ReleaseAcceptance。RoutePathSplit
只读取 Handoff 的公开返回值与服务。

## 路径与公开面

完整语义包 `releaseacceptancearchiveverificationhandoff` 的最长主源码/测试路径为 `222/234`，已低于
传统边界；选用仍清晰的 `releasearchivehandoff` 后进一步降为 `200/212`。生产公开面保持 Service 与
Response；测试源集公开 TestSupport。其余 catalog、renderer、support 维持包私有。

## 普查变化

```text
Direct root 525 -> 500
Retained root 105 -> 105
movable 420 -> 395
Handoff bucket 25 -> 0
Unassigned 0 -> 0
Total ops main Java <= 1352
```

v1828 endgame census 的当前值与 bucket 收紧，并追加 v1850 历史段；v1828 原始基线和 v1843-v1849
历史数列保持不变。

## 失败条件

控制器被迁出、Handoff 绕过 v1849 Archive、RoutePathSplit 读取包私有实现、路由字节变化、Response
字段变化、根包高于 500、Handoff bucket 非零、unassigned 非零、总文件数超过 1352、讲解不足
3000 汉字，或 focused、Spotless、verify 任一失败，都禁止关账。

## 验证命令

```powershell
.\mvnw.cmd "-Dtest=*ReleaseAcceptanceArchiveVerificationHandoff*Tests,*ReleaseAcceptanceRoutePathSplit*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1849Tests,ReadabilityUpkeepOpsConsolidationExtractionV1850Tests,ReadabilityUpkeepOpsExtractionEndgameCensusV1828Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepOpsConsolidationExtractionV1809Tests,OpsCodeWalkthroughArchiveComplianceTests" test
.\mvnw.cmd spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```

## 验证回执（完整 verify 前）

- 中文讲解实测 3,289 个汉字，并通过中文占比与十章节门。
- census：DirectRoot 500、Retained 105、Remaining 395、Operator-CI 0、Handoff 0、
  Unassigned 0。
- 首次编译准确暴露 RoutePathSplit 三个生产文件和一个测试工厂同时保留新旧 import；仅删除旧根包
  import，逻辑未改。
- 首次 focused 行为测试均通过，但中文占比差 113 字符；补充真实中文审查机理而未降低门槛。
- 最终 focused test：75 tests，0 failures，0 errors，耗时 1m00s。
- 本文档和讲解均在完整 verify 前写入，不预写通过结论。

## 最终本地回执

- `spotless:check`：BUILD SUCCESS，45 个受影响 Java 文件全部干净。
- `mvnw verify`：BUILD SUCCESS，耗时 12m33s；1,766 tests，0 failures，0 errors，
  0 skipped。
- JaCoCo：分析 2,227 个类，all coverage checks met。
- SpotBugs：BugInstance 0、Error 0、no errors/warnings found。
- 文档和 3,289 汉字讲解均先于完整 verify；本段只在成功后补录。
