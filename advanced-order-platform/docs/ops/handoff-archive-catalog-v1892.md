# MinimalReadOnlyGateOperatorCiHandoff Archive Catalog 收敛（v1892）

## Family design

- Abstraction: `ArchiveCatalog` 表示 operator-CI handoff 到 archive verification evidence 的唯一纯投影边界。
- Data boundary: 六组有序列表由 `ArchiveCatalog.Evidence` 一次持有，并以 `List.copyOf` 建立不可变快照。
- Behavior boundary: catalog 生成 source、artifact、lane、batch、boundary 与 scorecard，不负责 HTTP、事务、Markdown、状态或 checks。
- 调用边界：service 每次只调用一次 `evidence(sourceHandoff)`，不再逐项协调六个 catalog。
- 渲染边界：`ArchiveRenderer` 只读取完整 evidence 生成六段 Markdown，不拥有或修改证据。
- 汇总边界：Support 继续计算计数、passed/blocked 与二十一条 checks，只把六个散列参数替换为 typed evidence。
- 兼容边界：公开 Response、Controller、route、profile、字段与列表顺序、只读事务保持不变。
- 尺寸边界：新 owner 必须低于 260 行，六个退役 catalog 不得复活，出现第二个同形 owner 即失败。

## Requirement Evidence Matrix

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 完整 archive response 不得漂移 | `ArchiveResponseOracleTests` | 八段长度向量 + canonical JSON SHA-256 | 旧实现先转绿，新实现保持一致 |
| 六个同形 Catalog 收敛 | `ArchiveCatalog` | 精确退休清单 + 当前文件清单 | `6 -> 1` |
| service 只装配一次 | archive registry service | `ArchiveCatalog.evidence(...)` occurrence | 恰好 1 次 |
| 六组列表所有权明确 | `ArchiveCatalog.Evidence` | 6 次 copy + 清空来源 + 修改拒绝 | 已覆盖 |
| 投影、渲染、汇总职责分离 | Catalog / Renderer / Support | typed boundary 源码断言 | 已覆盖 |
| 当前家族只减不增 | `HandoffExtractionTests` | exact production/test inventory | 生产 `15 -> 10`，测试保持 10 |
| 长命名债只减不增 | 短测试 owner + exact baseline | census + Git-aware gates | 删除 24 项，新增 0 项 |
| 上下游只读契约不变 | handoff / archive digest / controllers | behavior + consumer suites | 扩大选择 82/82 |

## Scope

v1892 只重构 `ops.maintenance.minimalreadonlygateoperatorcihandoff` 内的 archive verification
投影边界。公开 Controller、Response record、route 常量、profile、版本、Node 计划引用、列表
顺序、Markdown 内容、checks、最终状态算法和 `@Transactional(readOnly = true)` 均不变。
上游 handoff service 与下游 archive-digest service 继续通过原公开类型连接；没有修改 Node、
mini-kv、历史 archive 路径、fixture 字节、凭据或运行时执行能力。

## Frozen Compatibility Oracle

删除旧实现前，`ArchiveResponseOracleTests` 在 released v1891 代码上先通过数量向量
`1/6/4/5/8/6/6/21`，并冻结 sorted-property UTF-8 JSON SHA-256：

`1b9fd78f3ac4d3905d027f2c5b3d04c15a768b0b17b45497d583606ead7a5321`

oracle 在生产重构后保持通过，因此 source、artifact、lane、batch、boundary、scorecard、
Markdown 与 checks 的内容和顺序均未漂移。

## Implementation

六个旧 Catalog 共 304 行，只分别返回一组列表，却共享同一 source、生命周期、状态词汇和
scorecard。它们由格式化后 200 行的 `ArchiveCatalog` 替代。`evidence(sourceHandoff)` 先生成
artifact、lane、batch 与 boundary，再生成依赖这些真实投影的 scorecard，最后构造 typed
`Evidence`。record 紧凑构造器对六组列表逐一 `List.copyOf`，形成不可变快照。

service 从六个 Catalog 调用与一次 scorecard 协调收敛为一次 evidence 装配；
`ArchiveRenderer` 读取 evidence 生成原六段 Markdown；Support 从 evidence 读取六组事实，
继续独立计算 passed/locked 计数、二十一条 checks 和最终 passed/blocked。Markdown 仍是展示
产物，没有被塞入领域 evidence；checks 仍是汇总政策，没有被挪进 Catalog。

## Test Ownership

- `ArchiveCatalogTests` 验证 source/artifact、lane/batch 顺序、boundary/scorecard 语义，以及
  六组列表对可变来源的隔离和不可修改性。
- `ArchiveRegistryServiceTests` 验证公开 route/profile/计划/source/state 与全部只读安全位。
- `ArchiveChecksTests` 验证二十一条 checks 的规模和关键边界文本。
- `ArchiveResponseOracleTests` 单独锁住完整公开响应，不与局部语义断言混杂。
- `HandoffExtractionTests` 精确要求十个生产文件与十个测试文件，禁止六个生产旧 owner 和
  四个测试旧 owner 回归，并约束 260 行、六次 copy、一次 assembly 与 typed 边界。

## Mechanical Result

- 生产 Java `1342 -> 1337`，ops Java `1210 -> 1205`，Catalog `293 -> 288`，当前包
  `15 -> 10`；测试 Java 保持 906，当前包测试保持 10。
- 生产名称指标 `1107/20002/2666 -> 1101/19956/2660`；测试名称指标
  `714/9844/3695 -> 710/9829/3687`。
- exact-name baseline 删除 24 项、新增 0 项；renderer 总行数 `3241 -> 3234`。
- 当前 Catalog 200 行，低于 260 行失败阈值；六个退役 Catalog 共 304 行。
- 聚焦行为/oracle/结构门通过；扩大选择生成 27 份报告，82/82，零失败、错误或跳过。
- 文档联合选择生成 10 份报告，50/50，讲解顺序、归档哈希、计划一致性和变更协议全绿。
- 中文讲解 3,391 Han、10 个标准章节、16,685 字节；精确归档为
  1,703 files / 20,244,957 raw bytes。

## Release Evidence

最终 `scripts/verify-release.ps1` 固定 predecessor tag
`v1891-order-platform-handoff-registry-catalog` 与 peeled commit
`cf0b1d87c00979001c275041c9fa493ff4c208fb`。Spotless 检查 14 个变更 Java 文件，0 个需要
修复；Maven verify 通过 2,017/2,017，零失败、错误或跳过，耗时 9:52；JaCoCo 分析
2,096 类并满足全部 floor；SpotBugs 0/0；可执行 jar 为 67,992,034 字节。

Implementation commit、canonical Actions、closeout commit、closeout Actions 和 annotated tag
仍是约束性完成门。它们在实际通过前不得记录为成功，也不得提前把 v1892 标记为 released。

## Failure Conditions

- 完整 response SHA、八段数量向量、公开 route/profile/version 或二十一条 checks 任一漂移。
- 六个退休 Catalog 或四个退休测试 owner 任一复活，或出现第二个同形基础 archive owner。
- `ArchiveCatalog` 达到 260 行、`List.copyOf` 不等于 6、service assembly 不等于 1。
- 当前包生产文件超过 10、测试文件超过 10，或任何全局 ops/Catalog/命名 ratchet 放宽。
- 修改测试期望、fixture 字节、历史 archive、只读安全位或执行权限来迁就重构。
- 最终 verify、任一 canonical CI job、tag push 或本地/远端 peeled SHA 核验失败。
