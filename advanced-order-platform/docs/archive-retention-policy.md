# Archive Retention Policy

Java 的历史证据目录是跨项目只读依赖，不是可以随意整理的临时输出。
`a/`、`b/`、`c/`、`d/`、`d_runtime_screenshot_archive_next/`、`e/`、
`f/` 与所有 `代码讲解记录*` 目录中的现有文件，在 v1867 进入精确保留集合。

## Mechanical Contract

- `scripts/archive-retention-census.ps1` 是唯一 census 口径，默认只读。
- 显式传入 `-WriteManifest` 才会重建 `docs/archive-retention-manifest.txt`。
- manifest 每行记录仓库相对路径与 SHA-256，按 ordinal 路径排序。
- `.md`、`.json`、`.html` 在计算摘要前只将 CRLF 规范为 LF，使 Windows 与 Linux
  checkout 得到同一内容摘要；`.png` 等二进制文件仍按原始字节计算。
- `ArchiveRetentionTests` 要求实际文件集合与 manifest 完全相等，并重算每个摘要。
- 文件数和原始总字节数是只减不增的上限；缺失、正文篡改或未索引新增都会使构建失败。
  文本换行规范化不是内容豁免，除 CRLF/LF 外的任何字节变化都会改变摘要。

## Boundary

本策略不移动、不重命名、不压缩、不删除历史文件，也不修改 Node 已固定的绝对路径。
它不读取 credential value，不启动 Java、Node 或 mini-kv，不连接网络服务，不执行部署、
回滚或 SQL。若未来确有新的归档需求，必须先由新的外部计划明确调整保留策略；不得在普通
功能提交中静默放宽计数或摘要门。

## Authorized v1868 Extension

外部授权的 `docs/readme-exhibition-brief.md` 要求 v1868 按仓库规范产出讲解。
本次只新增
`代码讲解记录_生产雏形阶段8/v1868-v1872/v1868-readme-exhibition.md`，没有移动、
改写或删除任何 v1867 历史文件。重建后的精确基线是
`1,679 files / 19,834,662 raw bytes`；在下一份外部计划明确授权前，这两个上限继续
只减不增。

## Authorized v1869 Extension

用户于 2026-07-19 明确授权持续优化 Java 项目，v1869 首先修复优雅门可被总量交换
绕过的问题。本次只新增
`代码讲解记录_生产雏形阶段8/v1868-v1872/v1869-elegance-gate-convergence.md`，
没有移动、改写或删除既有归档。重建后的精确基线是
`1,680 files / 19,849,915 raw bytes`；后续优化版本若需要新增讲解，仍须逐版显式记录
授权文件并重新计算精确集合，不得用批量预算替代逐文件证据。

## Authorized v1870 Extension

用户于 2026-07-19 授权持续优化 Java 项目。v1870 只新增
`代码讲解记录_生产雏形阶段8/v1868-v1872/v1870-core-error-boundary.md`，用于解释核心错误边界、
状态迁移去重和显式过期查询；没有移动、重写或删除历史归档。重建后的精确集合为
`1,681 files / 19,864,889 raw bytes`。本次上调严格等于这一份 14,974 字节讲解的增量，
不是后续版本的预留预算。

## Authorized v1871 Extension

用户于 2026-07-19 持续授权 Java 项目优化。v1871 只新增
`代码讲解记录_生产雏形阶段8/v1868-v1872/v1871-spotbugs-waiver-governance.md`，用于解释
SpotBugs 豁免身份、结构化 XML、安全边界与负向证明；没有移动、重写或删除历史归档。
重建后的精确集合为 `1,682 files / 19,878,770 raw bytes`。本次上调严格等于这一份
13,881 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1872 Extension

用户于 2026-07-19 继续授权 Java 项目深度优化。v1872 只新增
`代码讲解记录_生产雏形阶段8/v1868-v1872/v1872-immutable-dto-boundary.md`，用于解释
公开 DTO 的列表所有权、null 校验顺序、不可变快照与六条 SpotBugs 债务删除；没有移动、
重写或删除历史归档。重建后的精确集合为 `1,683 files / 19,891,423 raw bytes`。本次上调
严格等于这一份 12,653 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1873 Extension

用户于 2026-07-20 授权持续执行 Java 三分优雅度提升计划。v1873 只新增
`代码讲解记录_生产雏形阶段9/v1873-v1877/v1873-declarative-renderer-engine.md`，用于解释
声明式 Markdown section 引擎、archive-digest 家族收敛、精确行为 oracle 与长名门语义；
没有移动、重写或删除任何历史归档。重建后的精确集合为
`1,684 files / 19,908,542 raw bytes`。本次上调严格等于这一份 17,119 字节讲解的增量，
不是后续版本的预留预算。

## Authorized v1874 Extension

用户于 2026-07-20 继续授权 Java 三分优雅度提升计划。v1874 只新增
`代码讲解记录_生产雏形阶段9/v1873-v1877/v1874-consumer-renderer-engine.md`，用于解释
consumer-package 九段 Markdown 的输入、聚合、逐行冻结、边界约束与回归机制；没有移动、
重写或删除任何历史归档。重建后的精确集合为
`1,685 files / 19,929,927 raw bytes`。本次上调严格等于这一份 21,385 字节讲解的增量，
不是后续版本的预留预算。

## Authorized v1875 Extension

用户于 2026-07-20 继续授权 Java 三分优雅度提升计划。v1875 只新增
`代码讲解记录_生产雏形阶段9/v1873-v1877/v1875-dossier-renderer-engine.md`，用于解释
verification dossier 的十段输入、无计数映射引擎、逐行 oracle、下游消费与只读边界；
没有移动、重写或删除任何历史归档。重建后的精确集合为
`1,686 files / 19,952,332 raw bytes`。本次上调严格等于这一份 22,405 字节讲解的增量，
不是后续版本的预留预算。

## Authorized v1876 Extension

用户于 2026-07-20 继续授权 Java 三分优雅度提升计划。v1876 只新增
`代码讲解记录_生产雏形阶段9/v1873-v1877/v1876-release-acceptance-renderer-engine.md`，
用于解释 release-acceptance 十段输入、56 行旧实现 oracle、上下游 service 边界、
共享无计数映射与结构 ratchet；没有移动、重写或删除任何历史归档。重建后的精确集合为
`1,687 files / 19,967,858 raw bytes`。本次上调严格等于这一份 15,526 字节讲解的增量，
不是后续版本的预留预算。

## Authorized v1877 Extension

用户于 2026-07-20 授权持续推进 Java coding brilliant and elegant 九分计划。v1877
只新增 `代码讲解记录_生产雏形阶段9/v1873-v1877/v1877-archive-registry-renderer-engine.md`，
用于解释 archive-registry 九段输入、57 行旧实现 oracle、上下游只读边界、共享计数映射
与结构 ratchet；没有移动、重写或删除任何历史归档。重建后的精确集合为
`1,688 files / 19,983,220 raw bytes`。本次上调严格等于这一份 15,362 字节讲解的增量，
不是后续版本的预留预算。

## Authorized v1878 Extension

用户于 2026-07-20 继续授权 Java coding brilliant and elegant 九分计划。v1878 只新增
`代码讲解记录_生产雏形阶段9/v1878-v1882/v1878-release-archive-handoff-renderer-engine.md`，
用于解释 release-archive handoff 的十组输入、十节 67 行旧实现 oracle、上下游依赖、
mini-kv 零执行边界与只减不增的结构门；没有移动、改写或删除任何历史归档。重建后的
精确集合为 `1,689 files / 20,003,703 raw bytes`。本次上调严格等于这一份 20,483 字节
讲解的增量，不是后续版本的预留预算。

## Authorized v1879 Extension

用户于 2026-07-20 继续授权 Java coding brilliant and elegant 九分计划。v1879 只新增
`代码讲解记录_生产雏形阶段9/v1878-v1882/v1879-release-acceptance-package-renderers.md`，
用于解释 release acceptance package 的三阶段输入输出、九节 47 行主报告、7 行收据、
五节 22 行归档索引、改前改后精确 oracle 与只减不增的结构门；没有移动、改写或删除任何
历史归档。重建后的精确集合为 `1,690 files / 20,025,298 raw bytes`。本次上调严格等于
这一份 21,595 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1880 Extension

用户于 2026-07-21 继续授权 Java coding brilliant and elegant 九分计划。v1880 只新增
`代码讲解记录_生产雏形阶段9/v1878-v1882/v1880-operator-ci-handoff-renderers.md`，用于解释
operator CI handoff 的五节三十三行主报告、六节三十六行归档报告、改前改后精确 oracle、
共享计数引擎、上下游只读边界和 shrink-only 结构门；没有移动、改写或删除任何历史归档。
重建后的精确集合为 `1,691 files / 20,041,344 raw bytes`。本次上调严格等于这一份
16,046 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1881 Extension

用户于 2026-07-21 继续授权 Java coding brilliant and elegant 九分计划。v1881 只新增
`代码讲解记录_生产雏形阶段9/v1878-v1882/v1881-minimal-read-only-gate-execution-renderers.md`，
用于解释 execution 六节四十行、archive verification 六节四十一行、共享有序分组引擎、
改前改后精确 oracle、Java/mini-kv 只读边界和 shrink-only 结构门；没有移动、改写或删除
任何历史归档。重建后的精确集合为 `1,692 files / 20,059,203 raw bytes`。本次上调严格等于
这一份 17,859 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1882 Extension

用户于 2026-07-21 继续授权 Java coding brilliant and elegant 九分计划。v1882 只新增
`代码讲解记录_生产雏形阶段9/v1878-v1882/v1882-release-sustainment-renderer.md`，用于解释
sustainment 七节三十八行报告、改前改后精确 oracle、真实 closeout/acceptance-package
依赖方向、测试职责收敛、无效 route 断言修复和 shrink-only 结构门；没有移动、改写或删除
任何历史归档。重建后的精确集合为 `1,693 files / 20,076,290 raw bytes`。本次上调严格等于
这一份 17,087 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1883 Extension

用户于 2026-07-21 继续授权 Java coding brilliant and elegant 九分计划。v1883 只新增
`代码讲解记录_生产雏形阶段9/v1883-v1887/v1883-route-split-internals.md`，用于解释
Route Split 五个公共兼容边界、主报告六节四十三行、closeout 三节十五行、改前改后精确
oracle、内部短命名、下游 sustainment 方向与固定 tag 发布门；没有移动、改写或删除任何
历史归档。重建后的精确集合为 `1,694 files / 20,092,216 raw bytes`。本次上调严格等于
这一份 15,926 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1884 Extension

用户于 2026-07-21 继续授权 Java coding brilliant and elegant 九分计划。v1884 只新增
`代码讲解记录_生产雏形阶段9/v1883-v1887/v1884-profile-section-rendering-engine.md`，
用于解释三个 Profile Section 只读入口、十九个完整输出、共享不可变字段索引、领域适配器、
改前改后精确 oracle 与 shrink-only 结构门；没有移动、改写或删除任何历史归档。重建后的
精确集合为 `1,695 files / 20,107,763 raw bytes`。本次上调严格等于这一份 15,547 字节
讲解的增量，不是后续版本的预留预算。

## Authorized v1885 Extension

用户于 2026-07-21 继续授权 Java coding brilliant and elegant 九分计划。v1885 只新增
`代码讲解记录_生产雏形阶段9/v1883-v1887/v1885-code-walkthrough-renderers.md`，用于解释
四份 Code Walkthrough 报告的二十二节一百六十八行输入输出、共享 `counted/mapped` 机制、
完整 UTF-8 摘要、领域映射与 shrink-only 结构门；没有移动、改写或删除任何历史归档。
重建后的精确集合为 `1,696 files / 20,125,898 raw bytes`。本次上调严格等于这一份
18,135 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1886 Extension

用户于 2026-07-21 继续授权 Java coding brilliant and elegant 九分计划。v1886 只新增
`代码讲解记录_生产雏形阶段9/v1883-v1887/v1886-renderer-closeout.md`，用于解释五类只读
证据报告的三十三个输出块、二百零二行正文、五组完整摘要、共享 `mapped/counted` 机制、
Handoff 的局部映射、历史提取边界和 shrink-only 命名门；没有移动、改写或删除任何历史归档。
重建后的精确集合为 `1,697 files / 20,146,559 raw bytes`。本次上调严格等于这一份
20,661 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1887 Extension

用户于 2026-07-21 继续授权 Java coding brilliant and elegant 九分计划。v1887 只新增
`代码讲解记录_生产雏形阶段9/v1883-v1887/v1887-candidate-handoff-catalogs.md`，用于解释
两条 CandidateDocument handoff 的输入输出、十四个 Catalog 到两个不可变领域 bundle 的
收敛、两份完整响应摘要、只读安全边界和 shrink-only 结构门；没有移动、改写或删除任何
历史归档。重建后的精确集合为 `1,698 files / 20,160,868 raw bytes`。本次上调严格等于
这一份 14,309 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1888 Extension

用户于 2026-07-22 继续授权 Java coding brilliant and elegant 九分计划。v1888 只新增
`代码讲解记录_生产雏形阶段9/v1888-v1892/v1888-candidate-core-catalogs.md`，用于解释
CandidateDocument submission、intake 与 profile 三条只读链的输入输出、十四个 Catalog 到
三个不可变领域 bundle 的收敛、三份完整响应摘要、渲染分界和 shrink-only 结构门；没有移动、
改写或删除任何历史归档。重建后的精确集合为 `1,699 files / 20,179,335 raw bytes`。本次
上调严格等于这一份 18,467 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1889 Extension

用户于 2026-07-22 继续授权 Java coding brilliant and elegant 九分计划。v1889 只新增
`代码讲解记录_生产雏形阶段9/v1888-v1892/v1889-execution-registry-catalog.md`，用于解释
MinimalReadOnlyGateExecution 基础 Registry 的七组数据、七个 Catalog 到一个不可变领域
owner 的收敛、完整响应摘要、Java/mini-kv 只读边界和 shrink-only 结构门；没有移动、改写
或删除任何历史归档。重建后的精确集合为 `1,700 files / 20,194,403 raw bytes`。本次上调
严格等于这一份 15,068 字节讲解的增量，不是后续版本的预留预算。

## Authorized v1890 Extension

用户于 2026-07-22 继续授权 Java coding brilliant and elegant 九分计划。v1890 只新增
`代码讲解记录_生产雏形阶段9/v1888-v1892/v1890-archive-registry-catalog.md`，用于解释
MinimalReadOnlyGateExecution archive registry 的八组投影、八个 Catalog 到一个不可变
`ArchiveCatalog.Evidence` 的收敛、完整响应摘要、Java/mini-kv 只读边界和 shrink-only
结构门；没有移动、改写或删除任何历史归档。重建后的精确集合为
`1,701 files / 20,209,891 raw bytes`。本次上调严格等于这一份 15,488 字节讲解的增量，
不是后续版本的预留预算。

## Authorized v1891 Extension

用户于 2026-07-22 继续授权 Java coding brilliant and elegant 九分计划。v1891 只新增
`代码讲解记录_生产雏形阶段9/v1888-v1892/v1891-handoff-registry-catalog.md`，用于解释
MinimalReadOnlyGateOperatorCiHandoff 基础 registry 的五组投影、四个 Catalog 与 service
内 scorecard 到一个不可变 `HandoffCatalog.Evidence` 的收敛、完整响应摘要、Java/mini-kv
只读边界和 shrink-only 结构门；没有移动、改写或删除任何历史归档。重建后的精确集合为
`1,702 files / 20,228,272 raw bytes`。本次上调严格等于这一份 18,381 字节讲解的增量，
不是后续版本的预留预算。

## Authorized v1892 Extension

用户于 2026-07-22 继续授权 Java coding brilliant and elegant 九分计划。v1892 只新增
`代码讲解记录_生产雏形阶段9/v1888-v1892/v1892-handoff-archive-catalog.md`，用于解释
MinimalReadOnlyGateOperatorCiHandoff archive registry 的六组投影、六个 Catalog 到一个
不可变 `ArchiveCatalog.Evidence` 的收敛、完整响应摘要、Java/mini-kv 只读边界和
shrink-only 结构门；没有移动、改写或删除任何历史归档。重建后的精确集合为
`1,703 files / 20,244,957 raw bytes`。本次上调严格等于这一份 16,685 字节讲解的增量，
不是后续版本的预留预算。
