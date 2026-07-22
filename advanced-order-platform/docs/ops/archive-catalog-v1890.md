# MinimalReadOnlyGateExecution Archive Catalog 收敛（v1890）

## Family design

- Abstraction: `ArchiveCatalog`，表示 source registry 到 archive evidence 的唯一纯投影边界。
- Data boundary: 八组有序列表由 `ArchiveCatalog.Evidence` 一次性持有，并在构造时通过 `List.copyOf` 建立不可变快照。
- Behavior boundary: catalog 只做字段投影和 scorecard 计算，不负责 HTTP、事务、最终状态、checks 或 Markdown。
- 调用边界：service 每次请求只调用一次 `evidence(sourceRegistry)`，不再逐个寻找八个静态 owner。
- 渲染边界：`ArchiveRenderer` 仍只负责展示，把一个完整 evidence 作为输入，不拥有业务数据。
- 汇总边界：Support 仍计算计数、passed/blocked 状态和二十条 checks，只把散列参数替换为类型化 evidence。
- 兼容边界：公开 Response、Controller、route、profile、事务注解、字段顺序和列表顺序保持不变。
- 尺寸边界：新 owner 183 行，机械上限 200 行；不允许重新拆回同形 catalog。

## Requirement Evidence Matrix

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 完整 archive 响应不得漂移 | `ArchiveResponseOracleTests` | 十段长度向量 + canonical JSON SHA-256 | 旧实现先捕获，重构后保持一致 |
| 八个同形 catalog 收敛 | `ArchiveCatalog` | 精确文件存在/缺席清单 | `8 -> 1` |
| service 只装配一次 | Archive registry service | source occurrence assertion | `ArchiveCatalog.evidence(...)` 恰好 1 次 |
| 八组列表有明确所有权 | `ArchiveCatalog.Evidence` | 8 次 copy + 清空源列表 + 修改拒绝 | 已覆盖 |
| 数据、渲染、汇总职责分离 | Catalog / Renderer / Support | 结构源码断言 | 已覆盖 |
| 不制造新巨型文件 | `ArchiveCatalog` | line-count gate | 183 / 200 行 |
| 长命名债只减不增 | 三个 archive 测试短名 + exact baseline | Git-aware elegance gates | 删除 29 项，新增 0 项 |
| 只读运行契约不变 | 原 Service / Response / Controller | service、controller、oracle suites | 完整 release gate 与实现 CI 均通过 |

## Scope

本版本处理 `minimalreadonlygateexecution` 包中 archive-verification registry 的八个内部投影 owner：source snapshot、artifact、read target、gate check、boundary、CI batch、operator handoff 和 scorecard。它们此前拥有不同的超长类名，却都只接受同一个 source registry、只被同一个 service 调用、只输出最终响应的一组列表。这种文件划分没有形成真实边界，反而让一次请求的阅读路径跨越八个文件。

公开 controller 留在原位置；公开 response 的 record 结构和嵌套类型不改；`ArchiveRenderer`、Support 和 service 继续独立。基础 execution registry 的 `RegistryCatalog` 也不并入 archive owner，因为基础事实与归档验证是两个不同阶段：前者声明执行证据，后者验证这些事实是否被正确归档。

## Projection Flow

输入仍是 `sourceRegistryService.registry()` 返回的只读 source registry。`ArchiveCatalog.evidence(sourceRegistry)` 按原有顺序生成八组类型化列表，并构造一个不可变 `Evidence`。service 把该 evidence 同时交给 renderer 和 Support：renderer 生成六段 Markdown，Support 计算计数、状态与 checks，然后装配原公开 response。

这一传递方式消除了两个宽参数列表。旧 service 对八个 catalog 分别调用，随后又向 renderer 传八个参数，并向 Support 传同一批八个参数。新路径只传递一个能够表达完整概念的值；它不是通用 `Map`，每个 accessor 仍有编译期类型，新增或删减 evidence 组时会迫使所有消费者显式响应。

## Frozen Response

- 旧实现先执行 `ArchiveResponseOracleTests`，确认长度向量为 `1/6/5/20/10/4/5/7/6/20`。它依次对应 source snapshots、artifacts、read targets、gate checks、boundaries、CI batches、operator handoffs、scorecard、Markdown sections 和 checks。
- 使用属性名排序、map key 排序和 UTF-8 编码生成 canonical JSON；完整响应 SHA-256 为 `d5e75e352cee97a6f2c30111e0af57bb39af770b31cd420a018994b003e05859`。
- oracle 在删除旧 catalog 前捕获并转绿；新实现必须继续满足同一向量和摘要。禁止修改摘要、fixture 或输出字段来迁就重构。
- route、version、profile、source lineage、read-only flags、execution denial、原始 URL/凭据/managed-audit 禁止边界均包含在完整摘要中。

## Structural Outcome

- 八个生产 catalog 删除，新增一个 183 行 `ArchiveCatalog`；生产 Java `1352 -> 1345`，ops Java `1220 -> 1213`，Catalog `303 -> 296`，execution 包 `17 -> 10`。
- renderer 数量保持 30，因聚合参数替代宽参数列表，renderer 总行数 `3246 -> 3241`。
- 两份按旧 catalog 文件划分的测试合并为 `ArchiveCatalogTests`，新增完整响应 oracle；三个被触及的 archive 测试分别收短为 `ArchiveRegistryServiceTests`、`ArchiveRenderingTests` 和 `ArchiveMarkdownBoundaryTests`。测试 Java 总数保持 904。
- 生产长 stem/使用次数/唯一值从 `1119/20072/2678` 收紧到 `1111/20032/2670`；测试从 `721/9856/3710` 收紧到 `716/9846/3697`。exact name baseline 删除 29 项，新增 0 项。
- `ExecutionExtractionTests` 精确列出当前 10 个生产文件、13 个测试文件和 8 个不得复活的 archive catalog，并限制新 owner 不超过 200 行、`List.copyOf` 恰好 8 次、service 只装配一次。

## Behavioral Coverage

`ArchiveCatalogTests` 分别验证 source snapshot、六个 artifact、五个 read target、二十个 gate check、十个禁止边界、四个 CI batch、五个 operator handoff 和七项 scorecard。所有原有顺序和 passed/blocked 语义继续由字段级断言约束。不可变性测试从八个可修改副本构造 evidence，清空所有来源后验证快照仍有内容，并验证对每个输出列表的写入都抛出 `UnsupportedOperationException`。

`ArchiveResponseOracleTests` 约束整体输出而不是只抽查字段，原有 service、renderer、Markdown boundary 与 controller 测试继续覆盖装配和 HTTP 层。结构门额外禁止 catalog 吞并 renderer，确保收敛文件数量不会变成职责混合。

## Verification

- 旧实现 oracle：1/1 通过，取得上述向量和 SHA-256。
- 重构后 archive 行为、结构、优雅、文档与归档扩大选择：23 份报告、66/66 通过。
- 第一次完整门运行 2,009 个测试，仅设计说明标题/标签未命中 `JavaChangeGateTests` 的精确协议；不改测试，改为 `Family design` 与三项标准标签后，修复选择 11/11 通过。
- `scripts/verify-release.ps1` 固定 predecessor tag `v1889-order-platform-execution-registry-catalog` 与 commit `99e1afd2`；第二次完整运行通过 2,009/2,009，Maven 10:33，JaCoCo 2,102 类/全部阈值，SpotBugs 0/0，jar 67,998,687 字节。
- Implementation commit `d79bd028` 通过 canonical Actions run `29888181626`：Docker-tagged job 2:02，headless job 19:23，其中 wrapper verify 18:43、prod-profile smoke 0:12，JaCoCo artifact 上传成功。
- Closeout `9069d54e` 通过 canonical Actions run `29889326585`：Docker 1:45、headless 19:49，其中 wrapper verify 19:00、prod-profile smoke 0:13，JaCoCo artifact 上传成功。
- Annotated tag `v1890-order-platform-archive-registry-catalog` 在本地和 `javaproject` 均 peel 到 closeout `9069d54e`，发布链闭合。

## Failure Conditions

- 任一公开字段、字段值、列表顺序、列表尺寸、Markdown 行、check、长度向量或 canonical SHA-256 变化，版本失败。
- 为让重构通过而修改 frozen 摘要、fixture 字节、公开 Response 或测试期望，版本失败。
- 八个旧 catalog 任一复活、出现第二个同形 archive 数据 owner、新 owner 超过 200 行或复制次数不是 8，版本失败。
- service 再次逐项装配、renderer 或 Support 重新接收八个散列参数、catalog 吞并渲染或 HTTP 职责，版本失败。
- exact name baseline 增加、全局 ratchet 放宽、三个短测试名回退为超长名，版本失败。
- read-only 事务、禁止执行、禁止服务自启动、禁止凭据值/原始 URL/managed-audit 连接的边界变化，版本失败。
- 最终 verify、实现 CI、closeout CI、tag peel 任一未形成可复现证据，版本不得宣称完成。
