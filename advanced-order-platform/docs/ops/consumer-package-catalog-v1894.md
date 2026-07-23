# MinimalReadOnlyGateOperatorCiHandoff Consumer Package Catalog 收敛（v1894）

## Family design

- Abstraction: `PackageCatalog` 表示 archive digest 到 consumer package evidence 的唯一纯投影边界。
- Data boundary: 九组有序列表由 `PackageCatalog.Evidence` 一次持有，并以 `List.copyOf` 建立不可变快照。
- Behavior boundary: catalog 生成 source、manifest、audience、section、acceptance、CI、lock、checklist 与 scorecard，不负责 HTTP、事务、Markdown、状态或 checks。
- 调用边界：service 每次只调用一次 `evidence(sourceDigest)`，不再逐项协调九个 catalog。
- 渲染边界：`ReportRenderer` 只读取完整 evidence 生成九段 Markdown，不拥有或修改证据。
- 汇总边界：`PackageSupport` 计算计数、passed/blocked 与二十八条 checks，只接收一个 typed evidence。
- 兼容边界：公开 Response、Controller、route、profile、字段与列表顺序、只读事务保持不变。
- 尺寸边界：新 owner 必须低于 300 行，九个退役 catalog 与旧长名 Support 不得复活。

## Requirement Evidence Matrix

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 完整 consumer package response 不得漂移 | `PackageResponseOracleTests` | 十一段数量向量 + canonical JSON SHA-256 | released v1893 基准与新实现一致 |
| 九个同形 Catalog 收敛 | `PackageCatalog` | 精确退役清单 + 当前文件清单 | `9 -> 1` |
| service 只装配一次 | registry service | `PackageCatalog.evidence(...)` occurrence | 恰好 1 次 |
| 九组列表所有权明确 | `PackageCatalog.Evidence` | 9 次 copy + 清空来源 + 修改拒绝 | 已覆盖 |
| 数据依赖保持单向 | Catalog / `PackageSupport` | Catalog 禁止引用 Support | 已覆盖 |
| 测试按职责拆分 | consumer-package test package | 精确六文件清单 + 退休清单 | 6 个当前 owner |
| 当前家族只减不增 | `ConsumerPackageExtractionTests` | production/test file cap | 生产 `13 -> 5`，测试 `5 -> 6` |
| 全局债务立即收紧 | elegance/name ratchets | 可复现 census + exact baseline | 31 项删除，新增 0 项 |

## Scope

v1894 只重构
`ops.maintenance.minimalreadonlygateoperatorciconsumerpackage` 内部的证据投影、汇总与测试
所有权。公开 Controller、Response record、route、profile、version、JSON 字段、列表顺序、
Markdown、checks、最终状态算法和 `@Transactional(readOnly = true)` 均不改变。上游 archive
digest 与下游 verification dossier 继续通过既有公开类型连接；没有修改 Node、mini-kv、历史
archive 路径、fixture 字节或运行时执行能力。

## Frozen Compatibility Oracle

删除旧生产实现前，`PackageResponseOracleTests` 在 released v1893 上先通过十一段数量向量
`1/5/4/5/5/5/8/5/8/9/28`，并冻结 sorted-property UTF-8 JSON SHA-256：
`1ae92cfe8926ecb9ae772c8eec70dd8cddfbc1b0654e11685ef6304249803c60`。

oracle 在生产重构后保持通过。因此 source、manifest、audience、section、acceptance、CI、
lock、checklist、scorecard、Markdown 与 checks 的完整内容和顺序均未漂移。这一测试与局部
语义测试分离，避免为了适配实现而只保留不完整的字段断言。

## Implementation

九个旧 Catalog 共约 500 行，全部读取同一个 source digest，具有同一生命周期和状态词汇。
它们由格式化后 262 行的 `PackageCatalog` 替代。`evidence(sourceDigest)` 按依赖顺序生成九组
列表，再由 package-private `Evidence` record 一次持有；紧凑构造器逐组执行 `List.copyOf`，
建立不可变快照。

registry service 从九次 Catalog 协调收敛为一次 evidence 装配；`ReportRenderer` 只读取 typed
evidence 生成九段 Markdown；`PackageSupport` 只读取同一 evidence，计算计数、二十八条 checks
和最终 passed/blocked。九个 shape constant 由 Catalog 所有，Support 只读，Catalog 禁止反向
依赖 Support。

原 346 行长名 package-private Support 同时收短为 203 行 `PackageSupport`。八个同形
stream/filter/count helper 由一个 `<T> count(List<T>, Predicate<T>)` 表达，共同机制被复用，
各调用点仍显式保留 passed、ready、readOnly 或 locked 的领域谓词。`ReportRenderer` 从 176
行收紧为 167 行，service 从 61 行收紧为 44 行。

## Test Ownership

- `PackageCatalogTests` 验证九组投影的名称、顺序、状态与九组不可变所有权。
- `PackageRegistryServiceTests` 验证 route、profile、来源、最终状态及全部只读安全位。
- `PackageChecksTests` 验证二十八条 checks 和关键禁止边界。
- `ConsumerPackageMarkdownTests` 保留九段 Markdown 的内容与顺序。
- `PackageResponseOracleTests` 锁住完整公开响应，而非只锁局部字段。
- `ConsumerPackageExtractionTests` 锁生产/测试 inventory、退休文件、尺寸、一次装配、单向依赖及上下游 import。

历史两个重复综合测试与旧 source 测试 owner 退出；source 测试按真实职责改名为 service 测试。
历史 v1846 结构门也改名为当前语义 owner，但继续保护当时的移动、SpotBugs 路径、旧文档和
下游 dossier 边界。

## Mechanical Result

- 生产 Java `1332 -> 1324`，ops Java `1200 -> 1192`，Catalog `283 -> 275`，当前包
  `13 -> 5`；测试 Java `906 -> 907`，当前包测试 `5 -> 6`。
- `Readiness` 文件精确为 996；全局 renderer 数量保持 30，总行数 `3228 -> 3219`。
- 生产名称指标 `1094/19898/2653 -> 1084/19785/2643`；测试名称指标
  `705/9816/3679 -> 701/9807/3672`。
- exact-name baseline 删除 31 项、新增 0 项；新 Catalog 262 行，Support 203 行。
- 中文讲解 4,317 Han、10 个标准章节、20,671 字节；精确归档为
  1,705 files / 20,282,267 raw bytes。

## Validation Status

focused behavior/oracle/structure/elegance 相关选择通过 47/47，零失败、错误或跳过。最终
`scripts/verify-release.ps1` 固定 predecessor tag
`v1893-order-platform-handoff-archive-digest-catalog` 的 peeled commit
`9518c20313054471e1065231e602d1be572ecea0`；Spotless 检查 14 个改动 Java，零文件需要
修复；Maven verify 通过 2,023/2,023，零失败、错误或跳过，耗时 10:32；JaCoCo 分析
2,085 类并满足全部 floor；SpotBugs 0/0；可执行 jar 为 67,976,640 字节。

Implementation commit `f6c75927692a48f437808b709d705a51a18ccee6` 通过 canonical
Actions run `29978023171`：Docker-tagged job 2:24，其中 wrapper verify 2:06；headless
job 18:10，其中 wrapper verify 17:23、production-profile smoke 0:11、JaCoCo artifact
上传 0:03。Closeout commit `105271fb2a0b7e714e61f2e2a71d6ce5ab3376fe` 通过 run
`29978927253`：Docker-tagged job 2:14，其中 wrapper verify 1:58；headless job 18:02，
其中 wrapper verify 17:18、production-profile smoke 0:10、JaCoCo artifact 上传 0:03。
Annotated tag `v1894-order-platform-consumer-package-catalog` 在本地与 `javaproject` 均 peel
到该完整 closeout，v1894 发布链已经闭合。

## Failure Conditions

- 完整 response SHA、十一段数量向量、公开 route/profile/version、二十八条 checks 或任一安全位漂移。
- 九个退休 Catalog、旧长名 Support、三个退休测试 owner 中任一个复活。
- `PackageCatalog` 达到 300 行、`List.copyOf` 不等于 9、service assembly 不等于 1，或 Catalog 重新依赖 Support。
- 当前包生产文件超过 5、测试文件超过 6，或任一 ops/Catalog/renderer/命名 ratchet 被放宽。
- 修改测试期望、fixture 字节、历史 archive、只读事务或执行权限来迁就重构。
- 最终 verify、任一 canonical CI job、tag push 或本地/远端 peeled SHA 核验失败。
