# v1878 Release-Archive Handoff Renderer 收敛

## Family design

- Abstraction: 复用 `MarkdownSections.counted`，不扩展共享 engine。
- Data boundary: 十类 response record 与十个 Catalog 继续拥有业务数据。
- Behavior boundary: 一个短 `ReportRenderer` 只声明标题、顺序与 typed line mapper。
- Compatibility boundary: 先冻结旧实现十节 67 行输出，再让同一 oracle 约束新实现。
- Migration boundary: 删除十一种 Renderer 与一个 support，不改 service、response 或 route。
- Dependency boundary: 上游 `ciarc` 与下游 route-path-split 的公开边界保持不变。

## 需求证据矩阵

| 需求 | 实现位置 | 机械证据 | 当前状态 |
| --- | --- | --- | --- |
| 十节标题、顺序与 67 条 Markdown 内容行不变 | `ReportRenderer` | `HandoffMarkdownTests` 先约束旧实现，再约束新实现 | 已通过 |
| 每节固定计数首行由共享抽象生成 | `MarkdownSections.counted` | engine 测试与逐行 oracle | 已通过 |
| 删除一次性 renderer 壳 | `releasearchivehandoff` | v1850 历史结构门与 elegance census | `25 -> 14` |
| 测试工厂改为短名 | `HandoffTestData` | 全量 test compile 与 exact name baseline | 已通过 |
| 上游 archive registry 与下游 route-path-split 依赖不变 | service 与下游测试 | 聚焦上下游回归 | 68 项聚焦门通过 |
| route、response、Catalog、事务和权限不变 | service/controller/response | 行为、控制器与全量门 | 完整 verify 通过 |

## 实现结果

旧家族由一个聚合 Renderer、十个 section Renderer 与一个 RendererSupport 组成。
新实现只保留 230 行 `ReportRenderer`：十个私有方法分别声明标题、计数字段和从 typed
response record 到文本行的 mapper；不可变列表快照和 section 构造继续交给
`MarkdownSections.counted`。Service 只替换最终组合调用，十个 Catalog、response record、
公开 service、只读事务与 route 常量均未修改。跨包测试工厂从 82 字符长名收敛为
`HandoffTestData`，同时保留供下游 route-path-split fixture 使用的公开 `service()`。

## 当前 census

- 生产 Java `1436 -> 1425`，ops `1304 -> 1293`，目标家族 `25 -> 14`。
- Renderer `77 -> 67`，Renderer 总行数 `4376 -> 4211`，长 Renderer 文件名 `70 -> 59`。
- 生产长文件 stem `1243 -> 1231`，长标识符出现次数 `20851 -> 20765`，唯一长名 `2802 -> 2790`。
- 测试 Java `896 -> 897`，长文件 stem `790 -> 789`、出现次数 `10156 -> 10139`、唯一长名 `3828 -> 3827`。
- Catalog `332`、Service `375`、超过 500 行文件 `32`、最大文件 `738` 均未上升。

## 测试证据

十节 67 行 oracle 在任何生产改动前先对旧实现通过 `1/1`。删除十二个一次性类并改用
共享 engine 后，同一个 oracle 在不修改期望的情况下再次通过 `1/1`；旧测试工厂引用为
0。家族行为、控制器、上下游 route-path-split、v1847-v1850/v1866 历史结构、优雅、
change 与 census 聚焦门通过 `68/68`，零失败、零错误、零跳过。中文讲解为 4,541 个汉字、
恰好十个标准标题；归档集合为 1,689 文件/20,003,703 原始字节。完整 Maven verify、
执行 1,949 项测试，零失败、零错误、零跳过；JaCoCo 分析 2,172 个类且所有 floor 满足，
SpotBugs 0 bugs / 0 errors，可执行 jar 完成打包，总时长 9:16。canonical Actions、
closeout 与 annotated tag 仍待后续机械闭环。

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
