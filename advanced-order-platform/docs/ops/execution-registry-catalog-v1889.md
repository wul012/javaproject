# MinimalReadOnlyGateExecution Registry Catalog 收敛（v1889）

## Family design

- Abstraction：`RegistryCatalog` 是基础执行注册表七组只读证据的唯一包内数据 owner。
- Data boundary：来源计划、读取目标、门检查、禁止边界、CI 批次、归档要求、操作交接按类型保留。
- Behavior boundary：`evidence()` 一次返回类型化 `Evidence`，不负责状态、checks 或 Markdown。
- Dependency direction：Service -> `RegistryCatalog` -> Response nested records。
- Rendering boundary：`ExecutionRenderer` 继续单独拥有六段 Markdown 行为。
- Size boundary：新 owner 不超过 300 行，不引入字符串键 map 或转发 Catalog。
- Compatibility：公共 route、Response、Controller、Support、顺序与只读事务保持不变。

## Requirement Evidence Matrix

| 需求 | 实现位置 | 机械证据 | 开工状态 |
| --- | --- | --- | --- |
| 完整 Registry 响应不变 | 旧实现先冻结规范 JSON、集合向量与 SHA-256 | `RegistryResponseOracleTests` | 旧实现 1/1 通过 |
| 七个静态 Catalog 收敛 | `RegistryCatalog` | 精确文件存在/缺席清单 | `7 -> 1` 通过 |
| Service 只装配一次 | `OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService` | source structure assertion | 1 次，通过 |
| 七组输出列表归属明确 | `RegistryCatalog.Evidence` | exact copy-count + mutation rejection | 7 组，通过 |
| 数据与渲染分离 | `RegistryCatalog` / `ExecutionRenderer` | source boundary assertion | 通过 |
| 不制造新巨型文件 | `RegistryCatalog` | line-count structure gate | 284 / 300 行 |
| 被触碰测试偿还长名债 | `RegistryCatalogTests`、`ExecutionExtractionTests` | elegance baseline + exact census | 删除 23 项，无新增 |
| 只读契约不变 | 原 Response、Support、Controller 与事务保留 | service/renderer/oracle suites | 聚焦门通过 |

## Scope

本刀只处理基础 execution registry 的七个静态数据 Catalog。相邻的 archive-verification
registry 有独立 Response、Support、Renderer 和下游消费者，因此不与基础注册表混合。公共
Response 的字段、嵌套 record、route、profile、状态算法和二十条 checks 均不改名、不改序。

## Planned Owners

- `RegistryCatalog`：拥有七组固定证据，并通过一个不可变 `Evidence` 返回。
- `ExecutionRenderer`：继续负责六段 Markdown，不迁入 Catalog。
- `OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport`：继续计算计数、状态和 checks。

## Frozen Response

- 集合向量：`5/5/20/10/4/6/5/6/20`，依次对应 source plans、read targets、gate
  checks、boundary rules、CI batches、archive requirements、operator handoffs、Markdown
  sections 和 checks。
- Sorted-property canonical JSON 使用 UTF-8 编码，SHA-256 为
  `8f33da2c1ed32695ef245c69cbf4a90d4b5b62324bb98e13c115ebec26df0b36`。
- 摘要在七个旧 Catalog 仍为生产实现时捕获并硬编码；替换后不允许修改期望值。

## Explicit Non-goals

- 不收敛 archive-verification registry 的十二个数据/验证 owner。
- 不修改公共 Response schema、Controller、route path、版本/profile 字符串或事务注解。
- 不把类型化列表改成通用 map，不创建兼容转发壳，不修改 frozen 期望迁就实现。
- 不触发 Java、mini-kv、部署、回滚、SQL、凭据值或 managed-audit 连接。

## Measured Outcome

- 七个生产 Catalog 删除、一个 284 行 `RegistryCatalog` 新增；生产 Java `1358 -> 1352`，
  ops Java `1226 -> 1220`，Catalog `309 -> 303`，execution package `23 -> 17`。
- 三个按旧文件划分的长名测试合并为 `RegistryCatalogTests`，新增完整响应 oracle，并把
  v1843 历史门改名为 `ExecutionExtractionTests`；测试 Java `905 -> 904`。
- 生产长 stem/使用/唯一值 `1126/20107/2685 -> 1119/20072/2678`；测试
  `725/9866/3719 -> 721/9856/3710`；exact name baseline 删除 23 项、新增 0 项。
- 旧实现先通过硬 oracle，新实现保持同一向量与摘要；核心行为/结构/优雅选择 39/39，加入
  archive registry、双 Controller、walkthrough、retention、closeout 与 README 后 70/70 通过。
- 中文讲解 3,247 Han / 10 headings / 15,068 bytes；授权归档精确为
  1,700 files / 20,194,403 raw bytes，manifest 已按 canonical text hash 重建。
- 完整 `scripts/verify-release.ps1` 固定 v1888 commit `15ad48bd`，通过 2,007 个测试，
  Maven 耗时 7:23；JaCoCo 分析 2,108 类且全部阈值满足，SpotBugs 0/0，jar 为
  68,005,806 字节。Implementation commit `dc73b52c` 的 Actions run `29883341547`
  全绿：Docker 2:04、headless 19:10，包含 prod smoke 与 JaCoCo 上传。Closeout CI 与
  annotated tag 仍是本版完成前的绑定门。

## Failure Conditions

- 任一 JSON 字段值、集合顺序、集合尺寸、Markdown 行、check 或 SHA-256 变化，版本失败。
- 为通过迁移而修改旧实现捕获的摘要、fixture 或公共响应，版本失败。
- 七个旧 Catalog 任一残留、出现第二个同形数据 owner 或新 owner 超过 300 行，版本失败。
- `ExecutionRenderer` 被并入数据 Catalog、Support 状态算法迁移或只读事务改变，版本失败。
- 名称 baseline 增加、ratchet 放宽、历史清单被删除而非更新到当前事实，版本失败。
