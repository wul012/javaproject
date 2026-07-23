# MinimalReadOnlyGateOperatorCiHandoff Catalog 收敛（v1891）

## Family design

- Abstraction: `HandoffCatalog` 表示已归档执行证据到 operator-CI handoff 投影的唯一纯数据边界。
- Data boundary: 五组有序列表由 `HandoffCatalog.Evidence` 一次持有，并以 `List.copyOf` 建立不可变快照。
- Behavior boundary: catalog 生成 source、lane、batch、boundary 和 scorecard，不负责 HTTP、事务、Markdown、最终状态或 checks。
- 调用边界：service 每次只调用一次 `evidence(sourceArchive)`，不再逐个协调四个 owner 和一个私有 scorecard 方法。
- 渲染边界：`HandoffRenderer` 只读取完整 evidence 生成五段 Markdown，不拥有或修改业务事实。
- 汇总边界：Support 继续计算计数、状态和十五条 checks，只把五个散列列表替换为类型化 evidence。
- 兼容边界：公开 Response、Controller、route、profile、字段顺序、列表顺序和只读事务保持不变。
- 尺寸边界：新 owner 181 行，机械上限 200 行；四个退休 Catalog 不得复活。

## Requirement Evidence Matrix

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 完整 handoff 响应不得漂移 | `HandoffResponseOracleTests` | 七段长度向量 + canonical JSON SHA-256 | 旧实现先转绿，重构后保持一致 |
| 四个 Catalog 与 service 内 scorecard 收敛 | `HandoffCatalog` | 精确文件清单 + 结构源码断言 | 四文件和一段私有逻辑归一 |
| service 只装配一次 | handoff registry service | `HandoffCatalog.evidence(...)` occurrence | 恰好 1 次 |
| 五组列表所有权明确 | `HandoffCatalog.Evidence` | 5 次 copy + 清空来源 + 修改拒绝 | 已覆盖 |
| 投影、渲染、汇总职责分离 | Catalog / Renderer / Support | 类型边界断言 | 已覆盖 |
| 当前家族只减不增 | `HandoffExtractionTests` | exact production/test inventory | 生产 `18 -> 15` |
| 长命名债只减不增 | 短测试 owner + exact baseline | census + Git-aware gates | 删除 12 项，新增 0 项 |
| 上下游只读契约不变 | execution archive / handoff archive / digest | behavior + consumer suites | 扩大选择 77/77 |

## Scope

本版本只处理 `minimalreadonlygateoperatorcihandoff` 包内的基础 registry，不处理同包中的 archive-verification registry，也不处理后续 archive-digest 家族。旧实现把 source archive snapshot、operator lane、CI batch、boundary lock 分给四个文件，同时把 scorecard 留在 service 私有方法里。五组数据有相同输入周期、相同调用者和相同输出生命周期，却没有共同的所有权类型；service 需要知道每个构造器，renderer 和 Support 又分别接收五个列表。

新实现用 `HandoffCatalog.Evidence` 表达一次 handoff 请求的完整投影。它不是通用 map，也不隐藏字段类型；五个 accessor 仍分别返回明确的 response record 列表。archive-verification 继续通过公开 handoff service 消费结果，archive-digest 继续通过公开 archive service 消费结果，因此依赖方向仍是 execution archive -> handoff -> handoff archive -> digest，未增加反向引用。

## Frozen Response

- 在删除旧 Catalog 和移动 scorecard 前，`HandoffResponseOracleTests` 对 v1890 已发布实现执行并转绿。
- 七段集合向量固定为 `1/4/5/8/5/5/15`，依次对应 source archive snapshots、operator lanes、CI batches、boundary locks、scorecard、Markdown sections 和 checks。
- 使用属性名排序、map key 排序和 UTF-8 编码生成完整 canonical JSON，SHA-256 固定为 `4fc6dc6069cff5bc40ee0934bc1ed9133ff50bcfe7c3c5940429e83cf4287ab0`。
- 摘要覆盖 route、version、profile、Node 计划引用、source archive lineage、所有只读布尔值、状态、列表顺序和文案。禁止修改摘要、fixture 或公开字段来迁就重构。

## Projection Flow

service 仍先调用 `sourceArchiveService.registry()` 获取 execution archive 的只读响应，然后只调用一次 `HandoffCatalog.evidence(sourceArchive)`。catalog 在内部按原顺序创建一个 source snapshot、四条 operator lane、五个 CI batch、八条 boundary lock 和五项 scorecard，并在 record 紧凑构造器中复制每个列表。

同一个 evidence 随后交给 `HandoffRenderer.render(evidence)` 与 registry Support。renderer 生成原有五段 Markdown；Support 从 evidence 读取五组列表，计算 ready lane、passed batch、locked boundary 与 passed scorecard 计数，生成原有十五条 checks 和最终状态。service 只负责调用顺序与事务边界，catalog 只负责投影，renderer 只负责展示，Support 只负责汇总，三个职责之间通过一个编译期类型连接。

## Structural Outcome

- 删除四个长名生产 Catalog，新增一个 181 行 `HandoffCatalog`；生产 Java `1345 -> 1342`，ops Java `1213 -> 1210`，Catalog `296 -> 293`，当前包 `18 -> 15`。
- service 删除五列表装配和私有 scorecard 逻辑，只保留 source 获取、单次 evidence 构造和 response 编排；renderer 的五列表参数收敛为一个 evidence；Support 不再重复复制这五组列表。
- 新增 `HandoffCatalogTests` 与完整 response oracle；原长名 service 测试收短为 `HandoffRegistryServiceTests`，v1844 历史结构 owner 收短为 `HandoffExtractionTests`。测试 Java `904 -> 906`，新增测试不是复制旧断言，而是补足完整摘要和列表所有权空白。
- 生产长 stem/使用次数/唯一值从 `1111/20032/2670` 收紧为 `1107/20002/2666`；测试从 `716/9846/3697` 收紧为 `714/9844/3695`。精确 baseline 删除 12 项、新增 0 项。
- `HandoffExtractionTests` 精确锁定 15 个生产文件与 10 个包内测试，永久禁止四个旧 Catalog，要求 owner 小于 200 行、`List.copyOf` 恰好 5 次、service 装配恰好 1 次，并约束 renderer/Support 只接收 evidence。

## Behavioral Coverage

`HandoffCatalogTests` 分别验证 source snapshot、四条 lane 的 focused/grouped/build/smoke 顺序、五个 CI batch 的 command-family 顺序、八条锁定边界、五项全绿 scorecard，以及五组列表的快照所有权。所有权测试从可修改副本构造 evidence，清空每个来源后确认快照仍存在，再确认对输出列表写入会抛出 `UnsupportedOperationException`。

`HandoffResponseOracleTests` 冻结整个公开响应；`HandoffRegistryServiceTests` 继续验证项目、版本、route、profile、Node 计划引用、只读禁止项和状态；`HandoffMarkdownTests` 继续约束五段展示。扩大选择同时覆盖 handoff archive、archive digest、根 controller、v1889 execution 结构门、当前 handoff 结构门、Git change gate、精确名称 baseline 与 ops census，共 77/77 通过。

## Verification

- 旧实现 oracle：最终断言形式 1/1 通过，向量和 SHA-256 在生产重构前固定。
- 重构后 focused behavior + oracle + renderer + service 通过；结构、上下游消费者和优雅扩大选择 25 份报告、77/77 通过。
- `HandoffCatalog` 经 Spotless 后 181 行；生产/测试名称三项指标均下降，exact baseline diff 为新增 0、删除 12。
- 第一次 docs 联合门运行 97 个测试，仅发现“实际工作量说明”章节顺序不符合标准；不改测试、不删内容，移动完整章节并重建 manifest 后，同一联合门 31 份报告、97/97 通过。
- 最终 `scripts/verify-release.ps1` 固定 predecessor tag `v1890-order-platform-archive-registry-catalog` 与 commit `9069d54e`；完整运行通过 2,015/2,015，Maven 8:34，JaCoCo 2,100 类/全部阈值，SpotBugs 0/0，jar 67,997,219 字节。
- Implementation commit `be7bd5c1` 通过 canonical Actions run `29892031685`：Docker-tagged job 2:19，headless job 19:26，其中 wrapper verify 18:38、prod-profile smoke 0:12、JaCoCo artifact 上传 0:05。
- Closeout `cf0b1d87` 通过 canonical Actions run `29893092335`：Docker-tagged job
  2:13，headless job 19:38，其中 wrapper verify 18:51、prod-profile smoke 0:13、
  JaCoCo artifact 上传 0:04。Annotated tag
  `v1891-order-platform-handoff-registry-catalog` 已推送，本地与 `javaproject` 的 peeled SHA
  均为 `cf0b1d87c00979001c275041c9fa493ff4c208fb`，发布链闭合。

## Failure Conditions

- 任一公开字段、字段值、集合尺寸、顺序、Markdown、check、七段向量或 canonical SHA-256 变化，版本失败。
- 为使迁移通过而修改 frozen 摘要、fixture、公开 Response 或测试期望，版本失败。
- 四个旧 Catalog 任一复活、scorecard 回到 service、出现第二个同形基础 handoff owner、`HandoffCatalog` 达到 200 行或复制次数不是 5，版本失败。
- service 再次逐项装配、renderer 或 Support 重获五个散列列表、catalog 吞并 HTTP/事务/Markdown/最终状态职责，版本失败。
- 生产或测试名称指标上升、exact baseline 新增、文件数/Catalog ratchet 放宽，版本失败。
- read-only 事务、禁止 Java/mini-kv 自启动、禁止写路由、禁止凭据值、禁止原始 URL、禁止 managed-audit HTTP/TCP 或 runtime shell 的边界变化，版本失败。
- 最终 verify、双 CI、canonical tag 和本地/远端 peel 任一缺少可复现证据，版本不得宣称完成。
