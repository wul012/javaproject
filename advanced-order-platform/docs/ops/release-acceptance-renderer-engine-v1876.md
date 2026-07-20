# v1876 Release-Acceptance Renderer 收敛

## Family design

- Abstraction: 复用 `MarkdownSections.mapped`，不增加第二套 section engine。
- Data boundary: 十类 response record 与十个 Catalog 继续拥有业务数据。
- Behavior boundary: 一个短 `ReportRenderer` 只声明标题、顺序和 typed line mapper。
- Compatibility boundary: 先冻结旧实现全部十段输出，再以同一 oracle 约束新实现。
- Migration boundary: 删除十一个 Renderer 与一个 RendererSupport，不改 service、response 或 route。
- Dependency boundary: 上游仍是 dossier 公开 service，下游 archive 仍消费 release-acceptance service。

## 需求证据矩阵

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 十段标题、顺序和 56 条 Markdown 内容行不变 | `ReportRenderer` | `ReleaseAcceptanceMarkdownTests` 先约束旧实现，再约束新实现 | 已通过 |
| 无计数 section 使用已有共享抽象 | `MarkdownSections.mapped` | engine 测试 + 精确 Markdown oracle | 已通过 |
| 删除一次性 renderer 壳 | `ciaccept` | v1848 当前结构门 + elegance census | `25 -> 14` |
| 测试工厂改为短名 | `ReleaseAcceptanceTestData` | 全量 test compile + exact name baseline | 已通过 |
| 上游 dossier 与下游 archive 依赖不变 | service 与 archive 测试 | 聚焦上下游回归 | 已通过 |
| route、response、Catalog、事务与权限不变 | service/controller/response | 行为、控制器与全量门 | 聚焦通过 |

## 实现结果

旧家族由一个聚合 Renderer、十个 section Renderer 和一个 RendererSupport 组成。
新实现只保留 208 行 `ReportRenderer`：十个私有方法各自声明一个 record 到文本行的
typed mapper，标题和组合顺序仍由家族持有；通用的映射、不可变列表快照与 section
构造复用 `MarkdownSections.mapped`。Registry service 只替换最终组合调用，十个 Catalog、
response record、公开 service、只读事务和 route 常量均未改变。

## 当前 census

- 生产 Java `1457 -> 1446`，ops `1325 -> 1314`，目标家族 `25 -> 14`。
- Renderer `96 -> 86`，Renderer 总行数 `4809 -> 4586`，长 Renderer 文件名 `91 -> 80`。
- 生产长文件 stem `1266 -> 1254`，长标识符出现次数 `20996 -> 20929`，唯一长名 `2825 -> 2813`。
- 测试 Java `894 -> 895`，长文件 stem `792 -> 791`、出现次数 `10189 -> 10171`、唯一长名 `3830 -> 3829`。
- Catalog `332`、Service `375`、超过 500 行文件 `32`、最大文件 `738` 均未上升。

## 测试证据

完整十段 oracle 在生产改动前先对旧实现通过 `1/1`。替换后，同一个 56 行 oracle、
release-acceptance 六组行为测试、根控制器、共享 engine 和下游 archive 回归通过
`29/29`。加入 v1847/v1848/v1866 结构、census、长名和 change gate 后通过 `62/62`；
再加入归档、讲解、closeout 与文档诚实性门后通过 `77/77`。没有修改 fixture 字节、
Catalog 数据或 oracle 期望来换取通过。中文讲解为 3,124 汉字/10 个标准标题，归档精确
集合为 1,687 文件/19,967,858 原始字节。最终 `mvnw -B verify` 在讲解完成后通过
1,945 个测试，失败/错误/跳过均为零；JaCoCo 分析 2,193 个类并满足全部覆盖率门，
SpotBugs 为 0/0，可执行 jar 已生成，总耗时 20:39。

实现提交 `52e4c7c9` 随后通过 canonical Actions run `29739016977`：Docker-tagged
job 用时 2:06，headless job 用时 18:15；后者包含 wrapper 全量 verify、生产配置
启动 smoke 与 JaCoCo 报告上传。远端没有暴露本地未覆盖的格式、路径或环境差异。

## 明确不做

- 不修改 Markdown 标题、顺序、字段标签、分隔符或状态文本，不增加计数首行。
- 不修改 Catalog 数据、response component、JSON 形状、API 路径或只读事务属性。
- 不修改 Node、mini-kv 或 aiproj，不开放执行、写路由、credential value 或部署能力。
- 不移动、改写或删除历史归档，只新增本版本获授权的一篇中文讲解。

## 失败条件

- 任一标题、内容行、顺序或不可变语义变化，整版回退。
- 修改 oracle、fixture 或 Catalog 数据让新实现变绿，整版回退。
- `ReportRenderer` 超过 300 行，或为本家族新增第二套 section engine，整版回退。
- Renderer、长名、文件数、热点或 SpotBugs 豁免任一指标上升，整版回退。
- 中文讲解晚于最终 verify，或 push、canonical CI、closeout、tag 未闭环，不开始下一版写入。
