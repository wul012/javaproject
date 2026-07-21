# v1881 Minimal Read-only Gate Execution Renderer 收敛

## Family design

- Abstraction: `ExecutionRenderer` 拥有执行注册表，`ArchiveRenderer` 拥有归档验证报告。
- Data boundary: Catalog 与 response record 继续拥有证据值和公开契约。
- Behavior boundary: 两个 renderer 只把强类型条目映射为有序、不可变的 Markdown 章节。
- 共享引擎：带总数的有序分组章节统一使用 `MarkdownSections.groupedCounted`。
- 兼容基线：替换前先由旧服务图冻结两份完整输出，再运行同一个 oracle。
- 可见性：renderer 保持 package-private，Service 与 Controller 的公开面不变。
- 失败条件：文本、顺序、路由、权限、消费者或 ratchet 任一变化都使版本失败。
- 迁移边界：八个 renderer 与两个 support 只收敛为两个按最终输出命名的所有者。

## 需求证据矩阵

| 需求 | 实现 | 可复现证据 | 当前状态 |
| --- | --- | --- | --- |
| 两份 Markdown 逐字符兼容 | `ExecutionRenderer` 与 `ArchiveRenderer` | `ExecutionMarkdownTests` 在替换前后执行同一组 81 行精确断言 | 已满足 |
| 分组算法只有一个来源 | `MarkdownSections.groupedCounted` | `MarkdownSectionsTests.groupsEntriesInEncounterOrder` 固定组序、组内序与不可变性 | 已满足 |
| 删除按章节复制的控制流 | 八个旧 renderer 和两个 support 被两个输出所有者替代 | v1843 历史结构门固定 23 个当前文件并逐个禁止 10 个旧文件 | 已满足 |
| 公开契约与依赖方向不变 | Service、Response、Controller、route、Catalog 原样保留 | 包级行为、根 Controller、下游 operator-CI 边界测试 | 已满足 |
| 测试构造不再复制服务图 | `ArchiveTestData` 复用 `ExecutionTestData.service()` | 目标包与根 Controller 测试共同编译、执行 | 已满足 |
| 优雅收益只能继续缩小 | 收紧 ops、renderer、行数、长名与 family cap | elegance、change、history、census gates | 已满足 |

## 实现结果

旧结构把 execution 报告拆成一个聚合 renderer、三个独立章节 renderer、一个四章节
renderer 和一个 support；archive verification 又有一个聚合 renderer、三个章节 renderer
和第二个 support。多个类反复执行“添加 count 行、遍历条目、复制为 immutable section”，
两个 Gate 章节还各自维护一份 `LinkedHashMap` 分组过程。理解一次完整输出需要在十个长名文件
之间跳转，而且文件名描述的是实现碎片，不是产品输出。

新结构保留两个真实输出所有者。`ExecutionRenderer` 一眼展示六个 execution 章节的顺序；
`ArchiveRenderer` 一眼展示六个 archive verification 章节的顺序。普通 count 章节复用已有
`MarkdownSections.counted`，两个 Gate 章节复用新增的 `groupedCounted`。该原语明确保证输入
遇见顺序、组内顺序、总数前缀和不可变快照，测试覆盖 beta/alpha 交错输入，防止以后改成
无序 Map 或延迟视图。CI Handoff Scorecard 有三条不同计数头，不符合单 count 模型，因此
仍由 `ArchiveRenderer` 自己表达，没有为了追求形式统一而引入布尔模式或万能参数。

两个 renderer 都是 package-private、无 Spring 注解、无可变静态状态。Service 只把最后一跳
委托从旧聚合类换成新输出所有者；Catalog 继续产生事实，Response 继续定义 JSON 和 Markdown
记录，Controller 继续做 HTTP 适配。测试侧删除两个超长 TestSupport，建立短名
`ExecutionTestData` 与 `ArchiveTestData`；后者复用前者的 source service，消除重复构造链，
同时因根 Controller 测试确有跨包调用而保留最小必要的 public 可见性。

## 兼容性证明

生产替换前，临时探针调用真实 execution Service 与 archive verification Service，完整打印
旧实现结果。由该结果建立的 `ExecutionMarkdownTests` 不依赖 renderer 类型，只比较最终
`heading + lines`。旧结构先通过 2/2：execution 固定为六节四十行，archive verification
固定为六节四十一行。随后删除旧类、切换 Service 委托，不修改任何预期，同一 oracle 再次
通过 2/2。临时探针已删除，不进入版本产物。

冻结范围不仅包括标题和 count，还包括 Java/mini-kv 的命令或 route、地址 handle、状态、
布尔标志、空格、竖线、分号、组顺序、条目顺序和 scorecard 的 `actual/expected` 方向。
结构化 response 中没有进入旧 Markdown 的字段仍然不进入新 Markdown；本版本没有借重构
机会补字、改词或增加展示字段。这样 Node 或人工操作员即使把文本当作稳定证据消费，也不会
观察到字节级语义漂移。

## 当前机械普查

- `ops` Java 文件：`1274 -> 1266`；目标包生产文件：`31 -> 23`。
- renderer：`51 -> 45`；renderer 总行数：`3816 -> 3616`。
- 超过 40 字符的 renderer 文件名：`38 -> 30`，已达到 renderer 阶段目标上限。
- 生产源码为 1398 个 Java 文件；长 stem / 使用 / 唯一名为 `1197 / 20544 / 2756`。
- 测试源码为 900 个 Java 文件；脚本口径长 stem / 使用 / 唯一名为 `782 / 10063 / 3812`。
- Java scanner 的测试使用上限为 10064，保留与脚本 token 口径已知的一处差异。
- 超过 500 行的生产文件仍为 32 个，最大 738 行，750/1000 以上均为 0。
- `config/java-name-baseline.txt` 只有 28 条真实删除，新增条目为 0。
- 扩展行为、Controller、下游、历史结构、优雅、变更、讲解、归档与 closeout 门通过 179/179。
- 完整 `mvnw -B verify` 在 9:25 内通过 1960 项测试；JaCoCo 分析 2145 个类并满足全部
  floor，SpotBugs 为 0 bugs / 0 errors，executable jar 已打包。

所有数值都来自 `scripts/ops-elegance-census.ps1` 与
`scripts/java-maintainability-census.ps1`，不是手工估算。`OpsEleganceCensusTests` 新增
`minimalreadonlygateexecution` family cap，并要求包内恰好只存在 `ExecutionRenderer.java`
与 `ArchiveRenderer.java`。v1842、v1843、v1844、v1847 至 v1850、v1866 的全局 ops cap
同步收紧到 1266，任何新文件回流都会让多个历史门同时失败。

## 非目标与安全边界

本版本不修改 endpoint、HTTP 方法或状态、transaction 注解、response record、Catalog
事实、fixture 字节、跨项目 digest、部署或回滚流程。它不启动 Java 或 mini-kv，不读取
credential value，不解析 raw endpoint URL，不建立 managed audit 连接，不打开 write
routing、active shard router、shell 或任何写命令。execution Service 与 archive verification
Service 继续标注 `@Transactional(readOnly = true)`；下游 operator-CI 仍只依赖公开 Service
与 Response，不能引用 package-private renderer。

mini-kv 在报告中只是外部启动、只读探测的目标。Java 仍只展示 `HEALTH`、`INFOJSON`、
`STATSJSON` 等既有命令证据，没有获得启动、停止、写入或管理 mini-kv 的能力。因此本版属于
Java 内部等价重构，不要求 mini-kv 或 Node 同步升级；若未来路由、response 或证据 schema
变化，仍必须按上游到下游的跨项目规则另行对齐。

## 失败条件

以下任一项都判定 v1881 失败：两份精确 oracle 任一字符、顺序或行数变化；
`groupedCounted` 丢失遇见顺序、组内顺序或不可变性；Service、根 Controller 或下游
operator-CI 消费失败；目标包出现第三个 renderer、任一旧 renderer/support、超过 23 个
生产文件或旧 TestSupport 回流；全局 ops、renderer、renderer 行数、长 renderer、长名
baseline 任一放宽；通过修改 oracle、fixture、SpotBugs 豁免或只读标志制造绿色；中文讲解、
归档 manifest、Spotless、JaCoCo、SpotBugs、jar packaging、实现 CI、closeout CI 或
annotated tag 任一闭环缺失。
