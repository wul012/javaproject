# v1880 Operator CI Handoff Renderer 收敛

## Family design

- Abstraction: `HandoffRenderer` owns the live handoff report; `ArchiveRenderer` owns its archive-verification report.
- Data boundary: existing catalogs and response records continue to own evidence values and public contracts.
- Behavior boundary: each renderer maps typed response entries into ordered immutable Markdown sections.
- Shared engine: every count-prefixed section delegates to `MarkdownSections.counted`.
- Compatibility: a pre-change exact oracle freezes both reports before legacy renderers are removed.
- Visibility: both renderers remain package-private; services and controllers keep their existing public surface.
- Failure condition: any heading, line, ordering, count prefix, boundary flag, route, or downstream consumer change fails the version.
- Migration boundary: remove nine legacy renderers and two renderer supports; introduce exactly two short output-owned renderers.

## 需求证据矩阵

| 需求 | 实现 | 可复现证据 | 状态 |
| --- | --- | --- | --- |
| 两份 Markdown 逐字符兼容 | `HandoffRenderer`、`ArchiveRenderer` | `HandoffMarkdownTests` 在替换前后执行相同的 2 个精确断言 | 已满足 |
| 删除按章节复制的控制流 | 九个旧 renderer 与两个 support 被两个输出所有者替代 | v1844 结构门固定 18 个当前文件和 11 个永久缺席文件 | 已满足 |
| 复用已有机制而非复制算法 | 两个 renderer 的十一节都调用 `MarkdownSections.counted` | `OpsEleganceCensusTests` 固定恰好两个短 renderer | 已满足 |
| 公开契约和上下游不变化 | Service、Response、Controller、route、Catalog 原样保留 | 行为、Controller、archive-digest 消费者测试 | 已满足 |
| 优雅收益只减不增 | 收紧 ops、renderer、行数、长名与 family cap | elegance、change、history、census gates | 已满足 |

## 实现结果

旧实现把主 handoff 拆为聚合 renderer、lane renderer 与 support，又把 archive
verification 拆为聚合 renderer、六个章节 renderer 与第二个 support。十一节都重复
“加入 count 行、遍历条目、构造 immutable section”的控制流。v1880 将稳定边界上移到
两个真正独立的产品输出：`HandoffRenderer` 拥有五节主报告，`ArchiveRenderer` 拥有六节
归档报告；计数机制统一交给共享 `MarkdownSections.counted`。

两个 renderer 保持 package-private 和静态类型完整。Catalog 继续拥有事实数据，Service
继续编排上游证据与 scorecard，Response 继续定义公开 JSON 契约，Controller 继续拥有 HTTP
适配。测试构造器改为 `HandoffTestData` 和 `ArchiveTestData`；后者复用前者的 Service graph，
删除一条重复的上游构造链，但没有引入 mock 或跳过真实依赖。

## 兼容性证明

生产替换前，临时探针通过真实 Service graph 捕获旧输出，据此建立两项完整 oracle。
主报告冻结 `5 sections / 33 lines`，归档报告冻结 `6 sections / 36 lines`；标题、计数前缀、
URL、版本、状态、布尔值、空格和顺序全部进入 `containsExactly`。oracle 先在旧九个 renderer
仍存在时 2/2 通过，临时探针随后删除；替换后不修改期望，同一 oracle 再次 2/2 通过。

## 当前机械普查

- `ops` Java 文件：`1283 -> 1274`；目标包生产文件：`27 -> 18`。
- renderer：`58 -> 51`；总行数 `3973 -> 3816`；长 renderer 文件名 `47 -> 38`。
- 两个新 renderer 分别为 93 行和 132 行，总计 225 行；没有形成新热点。
- 生产源码 1406 个 Java 文件；长 stem / 使用 / 唯一名为 `1207 / 20627 / 2766`。
- 测试源码 899 个 Java 文件；脚本口径长 stem / 使用 / 唯一名为 `784 / 10091 / 3818`。
- 超过 500 行的生产文件仍为 32 个，最大 738 行，750/1000 以上均为 0。
- 中文讲解为 3273 个汉字、10 个标准标题；归档为 `1691 files / 20041344 raw bytes`。
- 完整 `mvnw -B verify` 在 10:26 内通过 1956 个测试，零失败、零错误、零跳过；
  JaCoCo 分析 2153 个类且全部 floor 达标，SpotBugs 为 0 bugs / 0 errors，jar 已生成。

## 非目标与安全边界

本版本不改变 endpoint、HTTP 状态、事务注解、response record、Catalog 数据、fixture 字节
或跨项目 digest；不启动 Java/mini-kv，不读取 credential value 或 raw endpoint，不打开
managed audit、write routing、deployment、rollback 或 shell。两个 renderer 没有 Spring 注解、
可变状态或 public 可见性，下游仍只能依赖公开 Service 与 Response。

## 失败条件

以下任一情况都使版本失败：两项精确 oracle 任一字符变化；Service、Controller 或下游
archive-digest 消费失败；包内出现第三个 renderer、任一旧 renderer/support 或超过 18 个
生产文件；全局 ops、renderer、行数、长名 baseline 被放宽；通过改测试期望、fixture、
SpotBugs 豁免或边界标志制造绿色；Spotless、JaCoCo、SpotBugs、jar packaging、实现 CI、
closeout CI 或 annotated tag 的任一闭环缺失。
