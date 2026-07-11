# v1848 Operator-CI ReleaseAcceptance 基座提取

## 目标

本版把位于根 `ops` 包中的 Operator-CI ReleaseAcceptance 基座迁入
`com.codexdemo.orderplatform.ops.maintenance.ciaccept`。迁移对象是 25 个非控制器实现文件和
7 个包内测试；Spring 控制器与控制器 Markdown 聚合测试继续留在根包，因而 HTTP 映射、
Spring 扫描和对外类型名称均不变化。

本版不改变任何业务判断。它只把已经稳定的只读验收证据链从根包拆出，为后续
ReleaseAcceptanceArchive 和 ReleaseAcceptanceArchiveVerificationHandoff 两个依赖层提供明确的
公开边界。

## Requirement Evidence Matrix

| Requirement | Implementation | Mechanical evidence | State before final verify |
| --- | --- | --- | --- |
| 根包只保留控制器 | 25 个实现迁入 `ops.maintenance.ciaccept`，控制器留根 | `ReadabilityUpkeepOpsConsolidationExtractionV1848Tests` 精确文件清单 | implemented |
| 包内测试跟随实现 | 7 个测试迁入同包，Markdown 控制器测试留根 | 同一守卫校验源、目标目录和精确数量 | implemented |
| 保持路由字节不变 | Service 改读既有公共 `OpsShardReadinessReleaseAcceptanceRoutePaths` | 历史路由测试 + v1848 路由所有权断言 | implemented |
| 收窄跨包 API | 仅 Service、Response、TestSupport 作为跨包边界 | Archive 与根控制器的显式 import 守卫 | implemented |
| 保持响应模型 | record 名称、字段、嵌套记录及构造顺序均未修改 | 原家族测试 + SpotBugs FQN 双镜像 | implemented |
| 路径可维护 | 直译包路径预算为 `339/347`，采用 `ciaccept` 后为 `247/255` | v1848 文档与守卫固定测量值 | implemented |
| 只收紧根包预算 | Direct root 573 -> 548；movable 468 -> 443 | 三处历史 ratchet + `ops-root-census.ps1 -Json` | implemented |
| 不扩大总文件数 | 总 ops 主源码上限仍为 1352 | 治理守卫与 v1848 守卫 | implemented |
| 中文解释先于验证 | 新建 v1848-v1852 续写目录和本版长篇讲解 | walkthrough archive compliance gate | implemented |
| 完整质量门 | focused tests、Spotless、`mvnw verify` | 命令回执在验证后补录 | pending |

## 边界选择

实际依赖方向是：

```text
operatorcidossier (v1847)
  -> ciaccept (v1848)
  -> ReleaseAcceptanceArchive (queued v1849)
  -> ReleaseAcceptanceArchiveVerificationHandoff (queued v1850)
```

因此先迁 ReleaseAcceptance 基座。它只消费 v1847 已公开的 dossier Service/Response；仍在根包的
Archive 层则改为显式导入 v1848 的 Service/Response。这样每版只跨越一条已知依赖边，编译器可以
直接证明不存在隐式包私有耦合。

## 路径预算

若把完整类名前缀原样翻译为包名，最长主源码路径和测试路径分别达到 `339/347` 字符，明显超过
本项目在 Windows 上已经验证过的稳妥区间。语义缩写 `ciaccept` 表示 Operator-CI release
acceptance，最长路径降为 `247/255`；类名、公开 API 和文件名保持原样，因此缩写只作用于内部
包地址。

## 路由与响应保持

迁移前 Service 通过根聚合器 `OpsShardReadinessRoutePaths` 取得 BASE_PATH 与后缀；迁移后直接读取
早在 v1840 已公开的 `OpsShardReadinessReleaseAcceptanceRoutePaths`。根聚合器本身仍委托同一个常量，
控制器仍通过根聚合器映射，所以最终字符串继续是：

```text
/api/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry
```

Response record 没有改字段、顺序、嵌套类型或集合语义。SpotBugs 排除表中的 Response 与
`MarkdownSection` 两组镜像只更新 FQN，规则内容没有放宽。

## 文件和测试范围

25 个主源码包含十类证据目录及其 renderer、聚合 renderer、response、service、support。7 个测试
覆盖 CI/边界、closeout/scorecard、evidence/signoff、不可变性、retention/replay、source/readiness
和测试工厂。根控制器继续作为 Web 适配层，根 Markdown 测试继续从外部包视角组装 Service，避免
迁移后只在包内自证。

## 预算变化

预期普查结果：

```text
Direct root 573 -> 548
Retained root 105 -> 105
movable 468 -> 443
Operator-CI bucket 48 -> 23
Unassigned 0 -> 0
Total ops main Java <= 1352
```

`MAX_ROOT_OPS_MAIN_JAVA_FILES`、`EXPECTED_ROOT_OPS_MAIN_JAVA_FILES` 和 v1809 精确根包断言均从
573 收紧到 548，没有修改历史快照 1183，也没有提高任何上限。

## 失败条件

以下任一情况都使本版失败：控制器被移出根包；Archive 依赖包私有实现；路由字符串变化；响应字段
变化；SpotBugs 通过删除规则而不是迁移 FQN；根包计数高于 548；总文件数高于 1352；出现未分类
文件；讲解少于 3000 个汉字；focused tests、Spotless 或完整 verify 任一失败。

## 验证命令

```powershell
.\mvnw.cmd -Dtest=OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistry*Tests,OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchiveRegistry*Tests,ReadabilityUpkeepOpsConsolidationExtractionV1847Tests,ReadabilityUpkeepOpsConsolidationExtractionV1848Tests,ReadabilityUpkeepGovernanceConsolidationPlanTests,ReadabilityUpkeepOpsConsolidationQualityCloseoutV1806Tests,ReadabilityUpkeepOpsConsolidationExtractionV1809Tests,OpsCodeWalkthroughArchiveComplianceTests test
.\mvnw.cmd spotless:check
.\mvnw.cmd verify
.\scripts\ops-root-census.ps1 -Json
```

## 验证回执（完整 verify 前）

- 中文讲解实测 3,844 个汉字，十个标准章节齐全。
- `ops-root-census.ps1 -Json`：DirectRoot 548、Retained 105、Remaining 443、
  Operator-CI 23、Unassigned 0。
- focused test：52 tests，0 failures，0 errors，耗时 1m04s。
- 首次 focused run 正确阻断 v1847 活棘轮仍为 573；断言仅收紧到 548，v1847 文档中的
  历史 `598 -> 573` 快照未改，随后同一测试集通过。
- 首次 full verify 执行完 1,754 项测试后，正确阻断 v1828 endgame census 的活计数仍为
  573/468、Operator-CI bucket 仍为 48；当前值仅收紧到 548/443/23，并新增 v1848 历史进度段，
  原 v1828 基线与 v1843-v1847 历史数列保持不变。

## 最终本地回执

- `spotless:check`：BUILD SUCCESS，51 个受影响 Java 文件全部干净。
- 最终 `mvnw verify`：BUILD SUCCESS，耗时 11m42s；1,754 tests，0 failures，0 errors，
  0 skipped。
- JaCoCo：分析 2,227 个类，all coverage checks met。
- SpotBugs：BugInstance 0、Error 0、no errors/warnings found。
- 本文档和 3,844 汉字讲解均在最终 verify 之前创建；最终结果只在命令成功后补录。
