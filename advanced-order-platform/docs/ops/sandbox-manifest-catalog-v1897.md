# v1897 Sandbox manifest catalog design

## Family design

- 抽象：`ManifestCatalog` 是一次响应所需八组 manifest 证据的唯一装配 owner。
- 数据边界：不可变 `Evidence` 只复制领域列表，不生成状态、checks 或 Markdown。
- 行为边界：`ManifestSupport` 保留通过判定与响应映射，`ManifestRenderer` 保留展示格式。
- 依赖方向：Catalog 可以读取 rehearsal，但不得反向依赖 Support 或 Renderer。
- 兼容边界：公开 endpoint、Response FQN、字段顺序、列表顺序及文本字节必须不变。
- 机械证据：旧实现先冻结完整 JSON SHA，重构后同一 oracle、结构门与 release gate 必须通过。
- 失败条件：任何输出漂移、写权限扩大、Catalog 超过 400 行或总复杂度不降均回退本版。

## Requirement Evidence Matrix

| 需求 | 实现 | 机械证据 | 状态 |
| --- | --- | --- | --- |
| 公开合同不变 | Controller、Response、route owner 与 Service 公开签名保持原样 | sorted-property 完整 JSON oracle | 新旧同值通过 |
| 收敛平行 Catalog | 8 个单列表 Catalog 合并为一个 `ManifestCatalog` | 精确 package inventory 与退休名单 | `8 -> 1` |
| 消除列表参数爆炸 | `Evidence` 成为 Service、Renderer、Support 的唯一领域集合边界 | 单次 assembly 与 typed signature 结构门 | 已通过 |
| 保持 fail-closed | `ManifestSupport` 保留 checks 与 status，Catalog 只生产证据 | safety、service、controller 与 oracle 测试 | 已通过 |
| 真实降低复杂度 | Renderer 复用本地 typed section adapter，测试 owner 改成短职责名 | 全局文件、行数、名称与 baseline census | ratchet 已收紧 |
| 可复现发布 | 十章中文讲解先于完整 verify，发布按 implementation/closeout/tag/receipt 分层 | 本地 release gate 与 canonical CI | 等待发布阶段 |

## Baseline and scope

改动前 manifest 家族为 12 个生产文件、1,124 行：8 个 Catalog、1 个 351 行长名
Support、1 个 89 行 Renderer、1 个 81 行 Service 与公开 Response。v1897 只重构
`ops.maintenance.sandboxconnection` 的 manifest 包内装配；公开 endpoint、`Java v1707`、
profile、Response 字段与顺序、8 段 Markdown、22 条 checks、status 算法和
`@Transactional(readOnly = true)` 不变。Dossier、共享 rehearsal、Node、mini-kv、凭据、
数据库、消息系统、部署与历史 archive 内容不在功能改动范围内。

## Frozen compatibility oracle

生产替换前，`ManifestResponseOracleTests` 先以零摘要在 released v1896 实现上故意失败，
再冻结实测向量 `1/12/5/7/17/6/10/4/8/22` 与 sorted-property UTF-8 JSON SHA-256
`03541a7ae5e46684151a3829458dde56453a4acc5ff1f397ad343892fc7656e2`。修正后的 oracle
先在旧实现通过；接入新装配和删除旧文件后，同一测试未改期望再次通过。该摘要覆盖顶层标量、
所有嵌套 record、列表顺序、Markdown、checks 与 status。

## Architecture

新 Service 只读取一次 rehearsal，调用一次 `ManifestCatalog.evidence(rehearsal)`，再将同一
Evidence 交给 Renderer 和 Support。Evidence 对八个顶层列表逐项 `List.copyOf`；来源回执的
warnings 与 verification actions 继续保留两次嵌套复制，所以 Catalog 中总计十次防御性复制。
Verification gate 在 source/module/reference/field/boundary/health 完成后生成，依赖方向可直接
从单一方法阅读。

Catalog 经 Spotless 格式化后的第一稿为 406 行，未放宽 400 行门；提取 boundary evidence 的
`javaExecutionBoundary.` 前缀后降为 397 行且输出文本不变。Renderer 直接展开 Evidence 时使
全局行数从 3,203 反弹到 3,209；随后由 8 个调用共享 typed `section` adapter，家族 Renderer
降为 71 行，全局收紧到 3,185。`ManifestSupport` 只保留 response、checks 与 status，降为
182 行；Catalog 不依赖 Support 或 Renderer。

## Test ownership

- `ManifestCatalogTests`：版本、计划、profile、计数、来源回执与最终状态。
- `ManifestEvidenceTests`：12 个 split module 与 5 条冻结 reference 的顺序和属性。
- `ManifestSafetyTests`：7 个 value-free 字段与 17 个运行边界。
- `ManifestServiceTests`：read-only、execution denial、gate 状态与集合不可变性。
- `ManifestResponseOracleTests`：完整公开 Response 的向量和 SHA。
- `SandboxManifestControllerTests`：真实 endpoint、checks 与 8 个 Markdown heading。
- `SandboxExtractionTests`：5-owner inventory、397 行上限、8 个顶层复制、2 个嵌套复制、
  一次 assembly、typed signatures、单向依赖与 9 个退休文件永久缺席。

结构门第一次把总 `List.copyOf` 次数误写成 8，实际为八个顶层 Evidence 字段加两个来源回执
嵌套列表。修复没有删除安全复制，也没有只把模糊数字改成 10，而是逐字段要求八个顶层赋值并
同时锁定总数十次。

## Mechanical result

- 家族生产文件 `12 -> 5`，总行数 `1,124 -> 768`，净删 7 文件与 356 行。
- 生产 Java `1,308 -> 1,301`，ops `1,176 -> 1,169`，Catalog `258 -> 251`。
- Renderer 保持 30 个且总行数 `3,203 -> 3,185`；超长 Renderer 文件名保持 0。
- 生产名称指标 `1,063/19,545/2,622 -> 1,054/19,458/2,613`；测试名称指标
  `690/9,773/3,651 -> 685/9,768/3,646`。
- exact name baseline 新增 0、删除 28；Readiness `975 -> 966`。
- 测试 Java `907 -> 908`，唯一净增是完整 response oracle；5 个触及的长测试 owner 已改为
  短职责名且原断言保留。
- 最大生产文件仍为 738 行，超过 500 行仍为 32，超过 750/1,000 行保持 0/0。
- 中文讲解为 4,520 Han、严格 10 章节、21,654 字节；归档精确为
  `1,708 files / 20,338,223 raw bytes`。

## Validation status

旧实现故意失败与冻结后通过均已完成；新实现接入、旧文件删除、Renderer 收敛和测试改名后的
focused behavior/oracle/structure 选择均已通过。统一聚焦门用 64.1 秒再次证明 behavior、
oracle、structure、名称、ops、归档、讲解与文档约束可以同时成立。完整
`scripts/verify-release.ps1` 固定前序 tag `v1896-order-platform-sandbox-dossier-catalog`
的 peeled SHA `a0be0c7808ab7684093126c60c7792a54dd69114`，在 12:23 内通过 2,030 项测试，
JaCoCo 分析 2,066 类并满足全部阈值，SpotBugs 为 0 bug / 0 error，产出
67,950,901-byte jar。Implementation commit
`010e4e43488017971f297d55933ea38bac8f1c34` 的 canonical Actions run
`30001832600` 通过：Docker job 2:06、wrapper verify 1:51；headless job 18:16、
wrapper verify 17:33、production-profile smoke 0:11、JaCoCo upload 0:03。
closeout CI、annotated tag、post-tag receipt 与第三次 CI 尚未完成，不能提前写成发布通过。

## Failure Conditions

- 完整 response SHA、数量向量、route/profile/version、8 段 Markdown、22 条 checks、status、
  read-only transaction 或任一禁止执行位漂移。
- 8 个退休 Catalog、旧长名 Support 或 5 个退休测试 owner 任一个复活。
- `ManifestCatalog` 达到 400 行，八个顶层复制、两个嵌套复制或一次 Service assembly 不再成立。
- Renderer/Support 不再只接收 typed Evidence，或 Catalog 反向依赖二者。
- manifest 生产 owner 超过 5，ops、Catalog、Readiness、renderer、名称或 archive ratchet 放宽。
- 修改 oracle、fixture、历史 archive、route 文本、测试期望或执行权限迁就实现。
- 本地完整门、canonical CI、tag push 或本地/远端 peeled SHA 核验失败。
