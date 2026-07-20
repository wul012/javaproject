# v1875 Dossier Renderer 收敛

## Family design

- Abstraction: 在共享 `MarkdownSections` 增加 `mapped`，表达“只有条目映射、没有计数首行”的 section。
- Data boundary: 十类 response record 与十个 Catalog 继续拥有数据，通用引擎不读取业务字段。
- Behavior boundary: 家族内一个短 `ReportRenderer` 只声明标题、顺序和 typed line mapper。
- Compatibility boundary: 先让完整十段 oracle 对旧实现通过，随后同一断言约束新实现。
- Migration boundary: 删除十一个 Renderer 与一个 RendererSupport，不改 service 输入、response 或 route。
- Test boundary: 长名 TestSupport 缩短为 `DossierTestData`，下游只消费同一公开 service。

## 需求证据矩阵

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 十段 Markdown 标题、顺序和 51 条内容行不变 | `ReportRenderer` | `DossierMarkdownTests` 先在旧实现通过，再约束新实现 | 已通过 |
| 无计数 section 复用共享抽象 | `MarkdownSections.mapped` | engine 不可变快照测试 + dossier oracle | 已通过 |
| 删除一次性 renderer 壳 | `operatorcidossier` | v1847 当前结构门 + elegance census | 25 -> 14 |
| 测试夹具短名化 | `DossierTestData` | 全量 test compile + exact name baseline | 已通过 |
| 下游 release acceptance 继续消费同一 service | `ciaccept` 测试夹具 | 聚焦编译与完整 verify | 全量通过 |
| route、response、Catalog 与权限不变 | service/controller/response | 行为、controller、全量门 | 全量通过 |

## 实现结果

旧实现由一个聚合 Renderer、十个 section Renderer 和一个 RendererSupport 组成，共十二个
形状文件。新实现只保留一个 `ReportRenderer`，十个私有方法分别描述 record 到文本行的
typed mapper；标题与组合顺序仍由家族持有，条目映射与不可变列表构造交给共享
`MarkdownSections.mapped`。`mapped` 不注入计数首行，因此不会把 consumer-package 的
`counted` 格式错误传播到 dossier。Registry service 只替换最终组合调用，十个 Catalog、
response record、公开 service、事务属性和 route 常量都未改变。

## 当前 census

- 生产 Java `1468 -> 1457`，ops `1336 -> 1325`，目标家族 `25 -> 14`。
- Renderer `106 -> 96`，Renderer 总行数 `5032 -> 4809`，长 Renderer 文件名 `102 -> 91`。
- 生产长文件 stem `1278 -> 1266`，长标识符出现次数 `21063 -> 20996`，唯一长名 `2837 -> 2825`。
- 测试 Java `893 -> 894`，但长文件 stem `793 -> 792`、出现次数 `10206 -> 10189`、唯一长名 `3831 -> 3830`。
- Catalog `332`、Service `375`、超过 500 行文件 `32`、最大文件 `738` 均未上升。

## 测试证据

完整十段 oracle 在任何生产改动前先对旧实现通过 `1/1`。替换后，同一个 oracle、六组
dossier 行为测试、根 controller aggregate 与 engine 测试合计通过 `17/17`；加入历史
结构、全局 census、下游 release-acceptance、归档、讲解与 closeout 门后通过 `79/79`。
中文讲解完成后执行的最终 `mvnw -B verify` 在 17:50 内通过 `1943/1943`，失败、错误、
跳过均为 0；JaCoCo 分析 2204 个类且所有覆盖率阈值满足，SpotBugs 为 0/0，可执行 jar
完成重打包。实现提交 `93f7d6b8` 随后通过 canonical Actions run `29733600319`：Docker
测试 2:13，headless 回归 19:06，并包含生产配置启动检查与 JaCoCo 报告上传。

## 明确不做

- 不新增计数首行，不修改任何 Markdown 标题、分隔符、字段名或状态文本。
- 不修改 Catalog 数据、response component、JSON 形状、API 路径或事务只读属性。
- 不修改 Node、mini-kv 或 aiproj，不启动外部服务，不开放执行、写路由或 credential value。
- 不移动、改写或删除历史归档，只新增本版本获得授权的一篇中文讲解。

## 失败条件

- 任一 Markdown 标题、内容行、顺序或不可变语义变化，整版回退。
- 通过修改 oracle、fixture 或 Catalog 数据让新实现变绿，整版回退。
- `ReportRenderer` 超过 300 行，或为 dossier 新建第二套 section engine，整版回退。
- Renderer、长名、文件数、热点或 SpotBugs 豁免任一指标上升，整版回退。
- 中文讲解晚于最终 verify，或 push、canonical CI、closeout、tag 未闭环，不开始下一版写入。
