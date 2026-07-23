# v1898 Release acceptance package Catalog convergence

## Family design

- Abstraction: `PackageCatalog` 是主 acceptance package 九组领域证据的唯一装配 owner。
- Data boundary: 不可变 `Evidence` 复制九个顶层列表，供 Service、Renderer、Support 共同读取。
- Behavior boundary: `PackageSupport` 继续拥有 checks、status 与最终 Response 映射。
- 展示边界：`ReportRenderer` 只把 typed Evidence 转成九段 Markdown，不参与通过判定。
- 兼容边界：公开 Service/Response/controller/route、字段和列表顺序、文本字节保持不变。
- 子家族边界：closeout receipt 与 closeout archive index 不在本次收敛范围。
- 机械证据：旧实现先冻结完整 JSON SHA；新实现、删除旧文件后均通过同一 oracle。
- 失败条件：输出漂移、Catalog 超过 330 行、Support 超过 219 行或总复杂度不降即回退。

## Requirement Evidence Matrix

| 需求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 公开合同不变 | Controller、Response、route 与 Service 公开 FQN/签名保留 | sorted-property 完整 JSON oracle | 新旧同值通过 |
| 收敛平行 Catalog | 九个单列表 Catalog 合并为 `PackageCatalog` | 18 文件精确 inventory 与十个退休 owner 清单 | `9 -> 1` |
| 消除参数爆炸 | `Evidence` 成为 Service、Renderer、Support 的领域边界 | 九次不可变复制、一次 assembly、typed signature | 已通过 |
| 保持 fail-closed | `PackageSupport` 保留 40 条 checks 和 status | catalog/service/renderer/controller/oracle 测试 | 已通过 |
| 不误伤子家族 | receipt 与 archive-index 文件、测试、路由不变 | package 精确 inventory 与既有 closeout 测试 | 已通过 |
| 真实降低复杂度 | 短 owner、共享 helper、历史测试改名 | 全局文件、行数、名称和 exact baseline census | ratchet 已收紧 |
| 可复现发布 | 十章中文讲解先于完整 verify，三层提交与 tag 分离 | release gate、两条 canonical CI、peeled SHA | 本地门通过；远端待收口 |

## Baseline and scope

改动前主 acceptance package 为 13 个生产文件、1,167 行：九个 Catalog 共 458 行，
420 行长名 Support、88 行 Service、139 行 Renderer 与 62 行公开 Response。目录另外包含
closeout receipt 和 closeout archive index 两个独立响应家族；它们只是共享目录，不共享本次
生命周期，因此不合并、不改名、不改输出。

本版只改变主响应的包内装配方式。公开 endpoint、`Java v1634`、profile、Node source plan、
上游 sustainment 版本、Response 字段和顺序、九段 Markdown、40 条 checks、status 算法、
`@Transactional(readOnly = true)` 以及禁止执行位保持不变。历史 archive、Node、mini-kv、
凭据、数据库、消息系统、部署和 rollback 不在功能改动范围内。

## Frozen compatibility oracle

生产替换前，`PackageResponseOracleTests` 以零摘要在 released v1897 实现上故意失败，实测
集合向量为 `1/3/6/5/5/5/7/6/9/9/40`，sorted-property UTF-8 JSON SHA-256 为
`2679ebdc83c27789a17d52d8d255f96ebda0cb081e9f37295b9953613ecca51a`。固定摘要后，
旧实现先通过；新装配接入时通过；九个 Catalog 与旧 Support 删除后仍通过。oracle 覆盖全部
顶层标量、嵌套 record、列表顺序、九段 Markdown、40 条 checks 与最终 status。

## Architecture

`PackageCatalog.evidence(source)` 依次生成 source snapshot、version lineage、acceptance
decision、archive item、review item、CI evidence、runtime boundary、next-change rule 和
scorecard。前八组先完成，scorecard 再基于这八组计算，随后 `Evidence` 的 compact constructor
对九组列表各执行一次 `List.copyOf`。Service 每次请求只调用一次该装配入口。

`ReportRenderer.render(evidence)` 保持九个显式 section 方法，使展示文案仍按领域可定位；
`PackageSupport.response(..., evidence, markdown)` 保持数量核对、source 版本核对、全部
predicate 核对、40 条 checks 与最终 Response 构造。依赖方向固定为 Service -> Catalog /
Renderer / Support，Catalog 不得反向引用 Renderer 或 Support。

## Mechanical result

- 主生产家族 `13 -> 5`，总行数 `1,167 -> 773`，净删 8 文件与 394 行。
- 九个 Catalog 458 行收敛为 330 行 `PackageCatalog`；Support `420 -> 219`。
- Service `88 -> 32`；Renderer `139 -> 130`；公开 Response 保持 62 行与原 FQN。
- 生产 Java `1,301 -> 1,293`，ops `1,169 -> 1,161`，Catalog `251 -> 243`。
- AcceptancePackage 目录 `26 -> 18`；测试 Java `908 -> 909`，仅新增完整 response oracle。
- Readiness `966 -> 956`；Renderer 保持 30 个且总行数 `3,185 -> 3,176`。
- 生产名称指标 `1054/19458/2613 -> 1044/19346/2603`。
- 测试名称指标 `685/9768/3646 -> 680/9763/3641`。
- exact name baseline 删除 30 项、新增 0 项。

## Test ownership

主家族测试改为 `PackageCatalogTests`、`PackageServiceTests`、`PackageRendererTests`、
`PackageResponseOracleTests`、`PackageMarkdownTests` 与 `PackageTestData`。根 controller
测试改为 `AcceptancePackageControllerTests`。原行为断言全部保留，完整 oracle 是唯一新增的
行为锁。

`AcceptancePackageHistoryTests` 取代长名 v1842 owner：历史文档与 walkthrough 断言继续
保留，同时当前生产 inventory 精确为 18，十个退休主 owner 永久缺席，Catalog 恰好九次复制，
Service 恰好一次装配，Catalog/Support/Renderer/Service 行数分别不超过
`330/219/130/32`。全局 elegance、name、readiness 与 archive 门继续只允许收紧。

## Validation status

旧实现故意失败、冻结后通过均已完成。新实现接入、删除旧文件、短测试 owner 与历史结构门接入
后，完整 response oracle、既有 package 行为、不可变性、Markdown、controller、历史结构、
exact name baseline 和全局 elegance 联合门均已通过。首次完整门在 2,032 项测试末端发现
Family design 使用中文标签而没有满足机械英文标签协议；不改测试，把同三行改为
`Abstraction`、`Data boundary`、`Behavior boundary` 后，30/30 修复选择通过。干净
`scripts/verify-release.ps1` 固定 v1897 closeout `fd803c13`，通过 2,032 项测试（9:22）、
JaCoCo 2,059 类/all floors、SpotBugs 0/0，并打包 67,941,575-byte jar。implementation CI、
closeout CI、annotated tag、post-tag receipt 与第三次 CI 尚未完成。

## Failure Conditions

- 完整 response SHA、数量向量、route/profile/version、九段 Markdown、40 条 checks、
  status、只读事务或任一禁止执行位漂移。
- 九个退休 Catalog、旧长名 Support 或五个退休测试 owner 任一个复活。
- `PackageCatalog` 不再恰好复制九组列表，或 Service 不再恰好装配一次。
- Renderer/Support 不再只接收 typed Evidence，或 Catalog 反向依赖二者。
- main family 超过 5、整个目录超过 18，或任一行数上限被放宽。
- ops、Catalog、Readiness、renderer、名称、SpotBugs waiver 或 archive ratchet 上升。
- 修改 oracle、fixture、历史 archive、route 文本、响应文案或执行权限迁就实现。
- 本地完整门、canonical CI、tag push 或本地/远端 peeled SHA 核验失败。
