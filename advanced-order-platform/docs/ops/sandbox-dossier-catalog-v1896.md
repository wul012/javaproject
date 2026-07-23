# Sandbox blocked-execution dossier 收敛（v1896）

## Family design

- Abstraction: `DossierCatalog` 统一产出来自 rehearsal 的直接证据投影。
- Data boundary: typed `Evidence` 只持有十组按顺序固定的不可变列表。
- Behavior boundary: Catalog 生成 boundary/guard/intake/verification 证据，不作最终状态判定。
- Assembly: service 只取一次 rehearsal，只组装一次 `Evidence`。
- Rendering: `DossierRenderer` 只把 typed evidence 映射成九个 Markdown section。
- Decision: `DossierSupport` 只构造 response、checks 与最终 status。
- Contract: route、version、profile、Response、列表顺序、21 条 checks 与安全位不变。
- Guard: 完整 JSON oracle、结构 ratchet 和全量 release gate 阻断回归。

## 需求-证据矩阵

| 需求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 公开合约完全不变 | 不改 Controller、Response、route owner 与 service 公开签名 | 旧实现先通过的 sorted-property JSON SHA-256 oracle | 新实现同值通过 |
| 消除平行 Catalog 膨胀 | 9 个单责任长名 Catalog 收敛为 1 个概念 owner | 精确文件集、退休名单与 Catalog 计数 ratchet | `9 -> 1` |
| 消除列表参数爆炸 | `Evidence` 成为 service、renderer、support 的唯一集合边界 | assembly-count 与 typed-signature 结构测试 | 恰好装配 1 次 |
| 保持阻断语义 | 安全证据与 response 判定分属 `DossierCatalog` / `DossierSupport` | boundary、checks、status、不可变与 controller 测试 | 聚焦门已通过 |
| 降低命名与维护成本 | 新增/修改 owner 使用短语义名，退休历史长名实现与测试 | 精确 name baseline 及 production/test elegance census | 新增 0、删除 28 |
| 发布可复现 | 中文讲解先于最终 verify，实现/closeout/tag/receipt 分层 | 窄门、全量 release gate、两条 canonical CI 与 peeled SHA | 本地完整门待运行 |

## 基线与失败条件

基线 family 为 13 个生产文件：9 个 Catalog、1 个 328 行 Support、1 个 88 行
service、1 个 92 行 renderer 与公开 Response；完成形状为 5 个文件且新 Catalog 低于
400 行。任一 response 字段、顺序、值、Markdown 字节、checks/status、只读事务、
Java/mini-kv 执行禁止位漂移，或新 owner 再引入长参数列表、反向 Catalog-to-Support
依赖、松动 ratchet，都必须失败。

生产改动前，旧实现已通过数量向量 `1/3/5/6/5/12/4/5/10/4/9/21`，完整
sorted-property UTF-8 JSON SHA-256 为
`f4ff835d241fd99fd1113f926f542c6954ab22f409ff43ef78b6e34f4413fad2`。

## Scope

v1896 只重构 `ops.maintenance.sandboxconnection` 中 blocked-execution dossier 的包内证据
投影、渲染入参、response 组装和测试所有权。公开 Controller、Response record、route owner、
profile、version、JSON 字段、列表顺序、九段 Markdown、二十一条 checks、最终状态算法和
`@Transactional(readOnly = true)` 都不改变。相邻 precheck verification manifest、共享
`OpsEvidenceService`、Node、mini-kv、数据库、消息系统、credential value 与历史 archive
内容均不在改动范围内。

## Frozen Compatibility Oracle

生产改动前，`DossierResponseOracleTests` 先用零摘要故意失败，从 released v1895 旧实现取得
完整 response 的真实摘要；随后固定数量向量 `1/3/5/6/5/12/4/5/10/4/9/21` 与
sorted-property UTF-8 JSON SHA-256
`f4ff835d241fd99fd1113f926f542c6954ab22f409ff43ef78b6e34f4413fad2`。修正后的 oracle 在旧实现
上通过，生产替换后又由同一测试原样通过。测试没有删字段、改 fixture 或为新实现重算期望，
因此 source、context、normalization、precondition、boundary、guard、warning、intake、
verification、handoff、Markdown、checks、status 与顶层安全位都受同一个完整快照保护。

## Architecture

旧 service 读取一次 rehearsal 后，依次调用九个 Catalog，保存十组列表，再把九组列表展开给
renderer，把十组列表连同 Markdown 展开给 328 行 Support。编排层因而知道每组 evidence 的
内部形状，renderer 具有九个列表参数，Support 继续复制一份超长参数边界；新增或调整一组合法
证据需要同步修改多个签名。

新流程只保留四步：读取一次 rehearsal，调用一次 `DossierCatalog.evidence(rehearsal)`，让
`DossierRenderer.render(evidence)` 生成 Markdown，最后由
`DossierSupport.response(evidence, markdown)` 生成二十一条 checks、status 与公开 Response。
`DossierCatalog.Evidence` 对十组列表逐一 `List.copyOf`，成为唯一不可变 ownership boundary；
Catalog 不引用 Support，数据生产到最终判定保持单向依赖。

本家族的 boundary 与 execution guard 不是纯转发数据：它们明确编码 owner approval、credential、
schema rehearsal、rollback、managed audit connection、SQL、deployment 与 service startup 的
阻断规则。把它们为了追求表面行数再拆成第二个 Catalog，会增加生命周期 owner 和依赖边，却不
消除规则复杂度。因此本版保留一个 374 行领域 Catalog，并用低于 400 行的机械上限约束；它不是
路线图所批评的纯参数组合器。家族总行数仍从 1,039 降到 740，净删 299 行。

## Test Ownership

- `DossierCatalogTests` 负责 source、context、normalization、precondition、warning、intake、
  verification 与 handoff 的顺序和语义。
- `DossierSafetyTests` 负责五个 boundary、十二个 execution guard 及全部禁止执行位。
- `DossierServiceTests` 负责 route、profile、version、状态、只读语义与集合不可变性。
- `DossierResponseOracleTests` 冻结完整公开 response，不允许局部断言漏掉字段。
- 原有 Controller 与 Markdown 测试继续保护 HTTP 入口、九段文本和输出顺序。
- `SandboxExtractionTests` 锁定精确 18 文件 package、五个 Dossier 生产 owner、退休清单、
  400 行上限、十次 copy、一次 assembly、typed signature 和单向依赖。

三个旧长名测试 owner 与 v1803 历史结构 owner 被短职责 owner 替代，原行为断言没有删除。
测试文件 `906 -> 907` 的唯一净增量是完整 response oracle，这个增量购买的是旧实现没有提供的
全字段兼容证明，而不是重复覆盖。

## Mechanical Result

- 当前 family `13 -> 5`，总行数 `1,039 -> 740`：`DossierCatalog` 374 行、
  `DossierSupport` 173 行、service 30 行、renderer 86 行，公开 Response 仍为 77 行。
- 生产 Java `1,316 -> 1,308`，ops `1,184 -> 1,176`，Catalog `266 -> 258`；测试 Java
  `906 -> 907`，Readiness `985 -> 975`，renderer 保持 30 个且总行数 `3,209 -> 3,203`。
- 生产名称指标收紧到 `1,063/19,545/2,622`，测试收紧到 `690/9,773/3,651`；exact name
  baseline 新增 0 项、删除 28 项。
- 最大生产文件仍为 738 行，超过 500 行的生产文件仍为 32，超过 750/1,000 行保持 0/0；
  本版没有通过制造新大文件交换文件数量。
- 中文讲解在最终 verify 前完成，为 3,097 Han、严格十章节、15,973 原始字节；授权归档精确
  为 1,707 files / 20,316,569 raw bytes。

## Validation Status

旧实现 oracle 与新实现 oracle 均已通过。替换后的 Controller、source、boundary、safety、
immutability、Markdown 与 response oracle 选择通过；扩大到上游/当前/历史结构、中央 census
与 elegance 的选择共通过 52/52。第一轮结构门发现 warning 列表在局部和 `Evidence` 边界被
重复 `List.copyOf`，修复生产实现只保留 boundary copy，没有把测试期望从 10 放宽到 11。

Spotless 已执行，walkthrough 合规门已通过。Canonical implementation CI、closeout CI、
annotated tag 与 post-tag receipt 尚未运行；这些结果必须由真实远端 run 与 peeled SHA 回填，
不能用当前本地门代替。

第一次完整 release gate 执行 2,026 项测试，唯一失败是 SpotBugs filter 仍引用已退休的
`PreconditionEvidenceCatalog`。修复删除该失效 `DM_CONVERT_CASE` waiver，把精确集合
`675 -> 674`；类加载门与 shrink-only identity 门保持原样，没有为通过构建增加豁免或放宽
测试。完整门必须从头重跑，第一次失败结果不计作发布通过。

删除 waiver 后的下一次完整门通过全部 2,026 项测试与 JaCoCo，再由真实 SpotBugs 分析发现
`DossierCatalog.evidenceId` 使用默认 locale 的 case-folding。生产实现改用 `Locale.ROOT`；
`DossierCatalogTests` 在土耳其 locale 下锁定 `INPUT ID -> input-id`，并以 `try/finally` 恢复
JVM locale。针对性行为与 SpotBugs 实扫均通过。

最终完整 `scripts/verify-release.ps1` 从头固定 predecessor tag
`v1895-order-platform-dossier-catalog` 的 peeled commit
`931bde5a9204e26cc8d1e400b5cdf9b8aea66da7`。Spotless 检查 16 个改动 Java，0 个需要修复；
Maven verify 在 12:46 内通过 2,027/2,027，零失败、错误或跳过；JaCoCo 分析 2,072 类并满足
全部 floor；SpotBugs 0/0；可执行 jar 为 67,957,758 字节。本地完整门由此闭合，远端发布
生命周期仍保持开放。

## Failure Conditions

- 完整 response SHA、数量向量、route/profile/version、九段 Markdown、二十一条 checks、
  status、只读事务或任一安全禁止位漂移。
- 九个退休 Catalog、旧长名 Support、旧长名 Renderer、三个退休测试 owner 或 v1803 旧结构
  owner 中任一个复活。
- `DossierCatalog` 达到 400 行、十次 `List.copyOf` 或一次 service assembly 不再精确成立，
  renderer/support 不再只接收 typed evidence，或 Catalog 反向依赖 Support。
- 当前 Dossier 生产 owner 不等于 5、sandbox package inventory 漂移，或新增/修改名称超过
  40 字符。
- ops、Catalog、Readiness、renderer、文件大小、名称或 archive ratchet 被放宽，exact name
  baseline 出现新增条目。
- 修改 oracle、fixture、历史 archive、测试期望或执行权限来迁就重构。
- 最终 verify、canonical CI、tag push 或本地/远端 peeled SHA 核验失败。
