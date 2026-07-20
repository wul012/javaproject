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
