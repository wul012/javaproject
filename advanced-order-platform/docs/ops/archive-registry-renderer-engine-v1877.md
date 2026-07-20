# v1877 Archive-Registry Renderer 收敛

## Family design

- Abstraction: 复用 `MarkdownSections.counted`，不新增第二套 section engine。
- Data boundary: 九类 response record 与九个 Catalog 继续拥有业务数据。
- Behavior boundary: 一个短 `ReportRenderer` 只声明标题、顺序与 typed line mapper。
- Compatibility boundary: 先冻结旧实现九段 57 行输出，再让同一 oracle 约束新实现。
- Migration boundary: 删除十个 Renderer 与一个 support，不改 service、response 或 route。
- Dependency boundary: 上游 `ciaccept`、下游 `releasearchivehandoff` 的公开边界保持不变。

## 需求证据矩阵

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 九段标题、顺序与 57 条 Markdown 内容行不变 | `ReportRenderer` | `ArchiveMarkdownTests` 先约束旧实现，再约束新实现 | 已通过 |
| 每段固定计数首行由共享抽象生成 | `MarkdownSections.counted` | engine 测试与逐行 oracle | 已通过 |
| 删除一次性 renderer 壳 | `ciarc` | v1849 历史结构门与 elegance census | `23 -> 13` |
| 测试工厂改为短名 | `ArchiveTestData` | 全量 test compile 与 exact name baseline | 已通过 |
| 上游 release acceptance 与下游 handoff 依赖不变 | service 与 handoff 测试 | 聚焦上下游回归 | 已通过 |
| route、response、Catalog、事务和权限不变 | service/controller/response | 行为、控制器与全量门 | 聚焦通过 |

## 实现结果

旧家族由一个聚合 Renderer、九个 section Renderer 与一个 RendererSupport 组成。
新实现只保留 203 行 `ReportRenderer`：九个私有方法分别声明标题、计数字段和从
typed response record 到文本行的 mapper，`flag` 与 `status` 只表达家族内部稳定的
字段格式。不可变列表快照和 section 构造继续由 `MarkdownSections.counted` 负责。
RegistryService 只替换最终组合调用；九个 Catalog、response record、公开 service、
只读事务与 route 常量均未改变。

## 当前 census

- 生产 Java `1446 -> 1436`，ops `1314 -> 1304`，目标家族 `23 -> 13`。
- Renderer `86 -> 77`，Renderer 总行数 `4586 -> 4376`，长 Renderer 文件名 `80 -> 70`。
- 生产长文件 stem `1254 -> 1243`，长标识符出现次数 `20929 -> 20851`，唯一长名 `2813 -> 2802`。
- 测试 Java `895 -> 896`，长文件 stem `791 -> 790`、出现次数 `10171 -> 10156`、唯一长名 `3829 -> 3828`。
- Catalog `332`、Service `375`、超过 500 行文件 `32`、最大文件 `738` 均未上升。

## 测试证据

九段 57 行 oracle 在任何生产改动前先对旧实现通过 `1/1`。替换后，同一 oracle、
archive-registry 五组行为测试、两个根控制器与下游 release-archive handoff 回归均已
通过；短测试工厂替换后同一组测试再次通过。加入 v1847-v1849/v1866 结构、census、
长名和 change gate 后，核心选择通过 `65/65`。没有修改 fixture 字节、Catalog 数据或
oracle 期望来迁就新实现。中文讲解为 3,281 汉字/10 个标准标题，归档精确集合为
1,688 文件/19,983,220 原始字节；加入归档、讲解、closeout 与文档诚实性门后通过
`80/80`。最终 `mvnw -B verify` 在 17:10 内通过 1,947 个测试，失败/错误/跳过均为
0；JaCoCo 分析 2,183 个类且所有覆盖门满足，SpotBugs 为 0 bugs / 0 errors，并完成
可执行 jar 打包。

## 明确不做

- 不修改 Markdown 标题、顺序、计数字段、分隔符、布尔标签或状态文本。
- 不修改 Catalog 数据、response component、JSON 形状、API 路径或只读事务属性。
- 不修改 Node、mini-kv 或 aiproj，不开放执行、写路由、credential value 或部署能力。
- 不移动、改写或删除历史归档，只新增本版本获授权的一篇中文讲解。

## 失败条件

- 任一标题、内容行、顺序、计数或不可变语义变化，整版回退。
- 修改 oracle、fixture 或 Catalog 数据让新实现变绿，整版回退。
- `ReportRenderer` 超过 300 行，或为本家族新增第二套 section engine，整版回退。
- Renderer、长名、文件数、热点或 SpotBugs 豁免任一指标上升，整版回退。
- 中文讲解晚于最终 verify，或 push、canonical CI、closeout、tag 未闭环，不开始下一版写入。
