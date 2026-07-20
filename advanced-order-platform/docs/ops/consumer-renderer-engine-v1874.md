# v1874 Consumer Package Renderer 收敛

## Family design

- Abstraction: 继续复用 v1873 的 `MarkdownSections`，不再创建第二套 section 算法。
- Data boundary: 家族内 `ReportRenderer` 只描述九种 record 到文本行的映射。
- Behavior boundary: Registry service 仍按原顺序生产相同九组数据，响应 record 不变。
- Test boundary: 新 oracle 在旧实现上先通过，再由同一断言验收新实现。
- Migration boundary: 删除十个一次性 Renderer 和一个 Support，保留九个 Catalog。
- Naming boundary: 公共测试夹具改名为 `ConsumerPackageTestData`，生产与测试新文件均为短名。

## 需求证据矩阵

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 九段 Markdown 逐行、顺序、标题不变 | `ReportRenderer` | `ConsumerPackageMarkdownTests` 在旧实现和新实现各通过一次 | 已通过 |
| 共享算法出现第二个真实消费者 | `MarkdownSections` | archive-digest 与 consumer package 两个家族测试 | 已通过 |
| 删除一次性 Renderer 壳 | consumer package | v1846 当前结构门禁 + census | 23 -> 13 |
| 测试夹具短名化 | `ConsumerPackageTestData` | 全量 test compile + exact name baseline | 已通过 |
| 下游 verification dossier 继续消费同一服务 | operator-ci dossier test data | 聚焦编译与完整 verify | 聚焦通过 |
| 路由、响应、Catalog、权限不变 | service/controller/response | 家族、controller、全量门禁 | 聚焦通过 |

## 实现结果

旧实现由一个 Registry Renderer、九个 section Renderer 和一个 Renderer Support
组成，总计十一份形状文件。新实现只保留一个 `ReportRenderer`：公开组合入口按原顺序
返回九个 `MarkdownSection`，九个私有方法分别映射 source digest、manifest、audience、
package section、acceptance、CI matrix、boundary、checklist 与 scorecard。计数首行、不可变
列表和 section 构造都交给共享 `MarkdownSections.counted`。

服务层只替换最后一个组合调用；九个 Catalog 的输入、输出和执行顺序均未变化。测试侧把
长名 `RegistryTestSupport` 缩短为 `ConsumerPackageTestData`，并同步直接 controller 测试、
三个家族测试和下游 dossier 测试数据入口。v1846 历史测试没有删除，而是从“旧 23 文件
必须存在”改成“当前 13 文件上限、十一份旧壳必须不存在、五份当前测试证据必须存在”。

## 当前 census

- 生产 Java `1478 -> 1468`，ops `1346 -> 1336`，目标家族 `23 -> 13`。
- Renderer `115 -> 106`，Renderer 总行数 `5236 -> 5032`，长 Renderer 文件名 `112 -> 102`。
- 生产长文件 stem `1289 -> 1278`，长标识符出现次数 `21124 -> 21063`，唯一长名 `2848 -> 2837`。
- 测试 Java `892 -> 893`，但长文件 stem `794 -> 793`、长标识符出现次数 `10216 -> 10206`。
- Catalog `332`、Service `375`、超过 500 行文件 `32`、最大文件 `738` 均未上升。

## 测试证据

`ConsumerPackageMarkdownTests` 在任何生产改动前先对旧实现通过 `1/1`；替换完成后，
oracle、三个家族行为测试、controller aggregate、v1846/v1866 结构门和共享引擎测试合计
`25/25` 通过。加入优雅 census、长名、变更、归档、讲解与 track closeout 门后，扩大聚焦集
通过 `47/47`。最终 `mvnw -B verify` 在 16 分 20 秒内通过 1,940 个当前测试，失败、错误、
跳过均为 0；JaCoCo 分析 2,215 个类且全部 floor 满足，SpotBugs 为 0/0，并生成可执行 jar。
实现提交 `912820c1` 的 canonical Actions run `29727976943` 也已通过：Docker 作业 2:27，
headless 作业 18:26，包含 prod profile smoke 与 JaCoCo 上传。closeout 和 tag 仍需闭环。

## 明确不做

- 不修改 API 路径、response component、JSON 字段、状态字符串或 Markdown 字节。
- 不修改九个 Catalog 的数据内容，也不把 Catalog 数据塞入通用引擎。
- 不修改 Node、mini-kv 或 aiproj，不启动外部服务，不打开任何写路由或执行权限。
- 不移动历史归档，不改写既有 walkthrough，只新增本版获授权的一篇中文讲解。

## 失败条件

- 任一标题、计数行、条目顺序、分隔符、布尔文本或状态文本改变，整版回退。
- 通过修改 oracle 期望、fixture 内容或 Catalog 数据让新实现变绿，整版回退。
- `ReportRenderer` 超过 300 行，或引入第二套 counted-section engine，整版回退。
- Renderer、长名、文件数或 SpotBugs 豁免任一指标上升，整版回退。
- 讲解晚于最终 verify，或 tag、push、canonical CI 未闭环，不开始下一版写入。
