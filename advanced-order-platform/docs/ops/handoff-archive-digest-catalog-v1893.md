# MinimalReadOnlyGateOperatorCiHandoff Archive Digest Catalog 收敛（v1893）

## Family design

- Abstraction: `DigestCatalog` 表示 handoff archive verification 到 digest evidence 的唯一纯投影边界。
- Data boundary: 六组有序列表由 `DigestCatalog.Evidence` 一次持有，并以 `List.copyOf` 建立不可变快照。
- Behavior boundary: catalog 生成 source、digest、packet、replay、boundary 与 scorecard，不负责 HTTP、事务、Markdown、状态或 checks。
- 调用边界：service 每次只调用一次 `evidence(sourceArchive)`，不再逐项协调六个 catalog。
- 渲染边界：`ReportRenderer` 只读取完整 evidence 生成六段 Markdown，不拥有或修改证据。
- 汇总边界：`DigestSupport` 继续计算计数、passed/blocked 与二十二条 checks，只把六个散列参数替换为 typed evidence。
- 兼容边界：公开 Response、Controller、route、profile、字段与列表顺序、只读事务保持不变。
- 尺寸边界：新 owner 必须低于 260 行，六个退役 catalog 不得复活，出现第二个同形 owner 即失败。

## Requirement Evidence Matrix

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 完整 digest response 不得漂移 | `DigestResponseOracleTests` | 八段长度向量 + canonical JSON SHA-256 | 旧实现先转绿，新实现保持一致 |
| 六个同形 Catalog 收敛 | `DigestCatalog` | 精确退休清单 + 当前文件清单 | `6 -> 1` |
| service 只装配一次 | digest registry service | `DigestCatalog.evidence(...)` occurrence | 恰好 1 次 |
| 六组列表所有权明确 | `DigestCatalog.Evidence` | 6 次 copy + 清空来源 + 修改拒绝 | 已覆盖 |
| 依赖只能单向流动 | Catalog / `DigestSupport` | Catalog 禁止引用 Support | 已覆盖 |
| 当前家族只减不增 | `DigestExtractionTests` | exact production/test inventory | 生产 `10 -> 5`，测试保持 6 |
| 长命名债只减不增 | 短 owner + exact baseline | census + Git-aware gates | 删除 27 项，新增 0 项 |
| 上下游只读契约不变 | archive / digest / consumer package | behavior + consumer suites | 聚焦门 41/41 |

## Scope

v1893 只重构
`ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest` 内的证据投影边界。
公开 Controller、Response record、route 常量、profile、version、列表顺序、Markdown、checks、
最终状态算法和 `@Transactional(readOnly = true)` 均不变。上游 archive verification service 与
下游 consumer package 继续通过原公开类型连接；没有修改 Node、mini-kv、历史 archive 路径、
fixture 字节、凭据或运行时执行能力。

## Frozen Compatibility Oracle

删除旧实现前，`DigestResponseOracleTests` 在 released v1892 代码上先通过数量向量
`1/6/4/5/8/6/6/22`，并冻结 sorted-property UTF-8 JSON SHA-256：

`2c0d238ec99c234a1c679eb4b7de2d37174c0a088f31b61d6d516949a5581ba4`

oracle 在生产重构后保持通过，因此 source、digest、packet、replay、boundary、scorecard、
Markdown 与 checks 的内容和顺序均未漂移。

## Implementation

六个旧 Catalog 共 332 行，只分别返回一组列表，却共享同一 source、生命周期和状态词汇。
它们由格式化后 220 行的 `DigestCatalog` 替代。`evidence(sourceArchive)` 先生成 digest、packet、
replay 与 boundary，再基于这些真实投影生成 scorecard，最后构造 typed `Evidence`。record
紧凑构造器对六组列表逐一 `List.copyOf`，形成不可变快照。

service 从六个 Catalog 调用收敛为一次 evidence 装配；`ReportRenderer` 读取 evidence 生成原
六段 Markdown；`DigestSupport` 从同一 evidence 读取六组事实，继续独立计算计数、二十二条
checks 和最终 passed/blocked。六个 expected count 归 `DigestCatalog` 所有，Support 只能
读取，Catalog 禁止反向引用 Support。原 240 行长名 package-private Support 同时收短为
211 行 `DigestSupport`，旧 owner 被永久列入缺席约束。

## Test Ownership

- `DigestCatalogTests` 验证 source/digest、packet/replay、boundary/scorecard 及六组不可变所有权。
- `DigestRegistryServiceTests` 验证 route/profile/计划/source/state 与全部只读安全位。
- `DigestChecksTests` 验证二十二条 checks 的规模和关键禁止边界。
- `DigestResponseOracleTests` 单独锁住完整公开响应，不与局部语义断言混杂。
- `DigestExtractionTests` 精确要求五个生产 owner、六个测试 owner、220 行 Catalog 上限、六次
  copy、一次 assembly 和单向 typed 边界，并禁止全部退休文件回归。
- `HandoffExtractionTests` 更新上游 archive response 的直接消费者，避免历史门继续指向已删文件。

## Mechanical Result

- 生产 Java `1337 -> 1332`，ops Java `1205 -> 1200`，Catalog `288 -> 283`，当前包
  `10 -> 5`；测试 Java 与当前包测试分别保持 906 和 6。
- 生产名称指标 `1101/19956/2660 -> 1094/19898/2653`；测试名称指标
  `710/9829/3687 -> 705/9816/3679`。
- exact-name baseline 删除 27 项、新增 0 项；renderer 总行数 `3234 -> 3228`。
- `DigestCatalog` 220 行、`DigestSupport` 211 行，均低于 260 行失败阈值。
- 聚焦 behavior/oracle/structure/elegance 选择通过 41/41，零失败、错误或跳过。
- 中文讲解 3,401 Han、10 个标准章节、16,639 字节；精确归档为
  1,704 files / 20,261,596 raw bytes。

## Release Evidence

最终 `scripts/verify-release.ps1` 固定 predecessor tag
`v1892-order-platform-handoff-archive-catalog` 与 peeled commit
`fb49fd6e1daa1b39d6cf93674d839d1e18bc022b`。Spotless 检查 14 个变更 Java 文件，0 个需要
修复；Maven verify 通过 2,019/2,019，零失败、错误或跳过，耗时 8:25；JaCoCo 分析
2,092 类并满足全部 floor；SpotBugs 0/0；可执行 jar 为 67,986,621 字节。

Implementation commit `52c6b02d` 通过 canonical Actions run `29973533854`：Docker-tagged
job 2:17，其中 wrapper verify 2:00；headless job 19:14，其中 wrapper verify 18:29、
prod-profile smoke 0:13、JaCoCo artifact 上传 0:04。

Closeout Actions 与 annotated tag 尚未执行。它们仍是约束性完成门；在实际通过前，v1893
只能记录为 candidate，不得写成 released。

## Failure Conditions

- 完整 response SHA、八段数量向量、公开 route/profile/version 或二十二条 checks 任一漂移。
- 六个退休 Catalog、旧长名 Support 或四个退休测试 owner 任一复活。
- `DigestCatalog` 达到 260 行、`List.copyOf` 不等于 6、service assembly 不等于 1，或 Catalog
  再次依赖 Support。
- 当前包生产文件超过 5、测试文件超过 6，或任何全局 ops/Catalog/命名 ratchet 放宽。
- 修改测试期望、fixture 字节、历史 archive、只读安全位或执行权限来迁就重构。
- 最终 verify、任一 canonical CI job、tag push 或本地/远端 peeled SHA 核验失败。
