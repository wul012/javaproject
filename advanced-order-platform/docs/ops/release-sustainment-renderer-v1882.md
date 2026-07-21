# v1882 Release-Acceptance Sustainment Renderer 收敛

## Family design

- Abstraction: `ReportRenderer` 拥有 sustainment registry 的七节 Markdown 报告。
- Data boundary: Catalog 与 Response 继续拥有事实、计数和公开 JSON 契约。
- Behavior boundary: renderer 只做有序、不可变的强类型条目到文本映射。
- Compatibility: 替换前由真实旧服务图冻结完整 heading 与 lines，替换后复用同一 oracle。
- Visibility: renderer 保持 package-private，公开 Service、Response 与根 Controller 不变。
- Test data: 一个短名工厂继续构造真实 v1840 closeout 上游并供下游 acceptance-package 复用。
- Ratchet: 精确禁止九个旧 renderer/support 文件回流，并同步收紧全局与 family 上限。
- Failure: 文本、顺序、路由、下游消费、安全边界或任一只减不增基线变化都使版本失败。

## 需求证据矩阵

| 需求 | 实现 | 可复现证据 | 当前状态 |
| --- | --- | --- | --- |
| 七节 Markdown 逐字符兼容 | `ReportRenderer` 使用 `MarkdownSections.mapped` | `ReportMarkdownTests` 在旧实现和新实现上复用同一组 7 节 / 38 行 oracle | 已满足 |
| 一份报告只有一个输出所有者 | 九个旧 renderer/support 由 `ReportRenderer` 替代 | `SustainmentStructureTests` 固定 11 个生产文件并逐个禁止九个旧文件 | 已满足 |
| 上游和下游方向不变 | Service 仍读取 v1840 closeout；acceptance-package 仍消费公开 Service/Response | 本包、根 Controller、下游 acceptance-package 选择 27/27 | 已满足 |
| 测试职责与命名同步收敛 | 六个长测试/工厂/结构名改成短职责名 | Java change/elegance gate；名称 baseline 新增 0、删除 35 | 已满足 |
| 路由断言能够真实失败 | 根 Controller 测试断言精确 suffix 与最终 endpoint | `SustainmentControllerTests` | 已满足 |
| 收益只能继续缩小 | 全局、family、renderer、行数和名称 ratchet 全部下调 | 两个 census 脚本与 Maven 结构门 | 已满足 |
| 发布证据完整 | 讲解、归档、全量 verify、两轮 CI、annotated tag | 本文与进度账本 | 实现 CI 已绿；closeout CI 与 tag 待完成 |

## 实现结果

旧包有一个聚合 renderer、七个章节 renderer 和一个 renderer support。八个以
`Renderer` 结尾的文件共 213 行，连同 support 共九个文件；每个章节类只有一个调用者，
support 也只是复制 lines 后构造 `MarkdownSection`。新结构保留一个 118 行、package-private
的 `ReportRenderer`：顶层 `render` 明确七节顺序，七个 private 方法用强类型条目表达各自
文本映射，共享的列表映射与不可变 section 构造继续由 `MarkdownSections.mapped` 提供。

Service 只替换最后一跳委托。公开 Service、Response、根 Controller、route owner、七个
Catalog、Support、`@Transactional(readOnly = true)` 与下游 acceptance-package 均未改变。
renderer 不成为 Spring bean，不读取 Service，不拥有 status 规则，也不把 Response 中旧
Markdown 未展示的 endpoint、rule、command 或 handoffRule 擅自加入文本。

测试侧将旧 CatalogTests、ImmutabilityTests、RendererTests、TestSupport、根
ControllerTests 与 v1841 历史结构门分别替换为短职责名。`SustainmentTestData` 仍从真实
v1840 closeout fixture 构造 Service，并被根 Controller 与下游 `PackageTestData` 复用。
旧 Controller 测试把 route 常量与自身比较的问题被修正为精确 suffix 断言。

## 兼容性证据

临时 `LegacyMarkdownProbeTests` 在旧实现仍存在时调用真实 Service 图并打印完整结果，确认
章节顺序为 Source Closeout、Ownership Rules、Drift Guards、Boundary Guards、CI Gates、
Consumer Handoffs、Sustainment Scorecard，正文数量为 1/6/6/7/5/5/8。探针随后删除，输出
被逐行写入 `ReportMarkdownTests`。正式 oracle 在旧实现上先通过 3/3；删除九个旧文件并接入
新 renderer 后，不修改 expected，同一测试再次通过 3/3。

兼容范围包括 heading、条目顺序、空格、等号、布尔值、三十八行总数和刻意省略的结构化字段。
另两项测试固定三十条 checks、五个 CI gate 名称、required 文本，并证明命令字符串不进入
Markdown。替换后的本包、根 Controller、历史结构和下游 acceptance-package 选择通过
27/27；名称、变更、v1866 与当前结构组合通过 28/28；扩展历史、优雅、变更、讲解、归档、
closeout 与 README 证据选择通过 111/111。

## 当前机械普查

- 目标包生产文件：`19 -> 11`；全局 `ops`：`1266 -> 1258`。
- renderer：`45 -> 38`；renderer 总行数：`3616 -> 3521`。
- 超过 40 字符的 renderer 文件名：`30 -> 22`。
- 生产 Java 文件：`1398 -> 1390`；长 stem / 使用 / 唯一名为 `1188 / 20495 / 2747`。
- 测试 Java 文件保持 900；脚本口径长 stem / 使用 / 唯一名为 `776 / 10039 / 3801`。
- Java scanner 的测试使用上限为 10040，保留与脚本 token 口径已知的一处差异。
- 超过 500 行的生产文件仍为 32，最大 738 行，750/1000 以上均为 0。
- `config/java-name-baseline.txt` 新增条目 0，真实删除 35。

`scripts/ops-elegance-census.ps1` 新增 `SustainmentJavaFiles`，`OpsEleganceCensusTests`
要求包内不超过 11 个生产文件并恰好只有 `ReportRenderer.java`。v1842、v1843、v1844、
v1847 至 v1850、v1866 和当前结构门的全局 cap 同步收紧到 1258。所有数字来自长路径安全
脚本或同口径 Java 扫描器，不接受手工近似值。

## 本地全量验证

讲解、精确归档和所有 ratchet 落盘之后执行 `mvnw.cmd -B verify`。该次运行在 12 分 10 秒内
通过 1,963 个测试，失败、错误和跳过均为 0。JaCoCo 分析 2,137 个类并满足全部覆盖率下限；
SpotBugs 报告 0 个 bug、0 个 error；Spring Boot 可执行 jar 完成重打包。实现提交、closeout
提交的两轮远端 Actions 与 annotated tag 仍是发布完成条件，不能由本地绿色替代。

## 实现 CI

初始实现提交 `4ced994e` 的 Actions run `29799487464` 中，Docker job 成功，headless 在
Spotless ratchet 提前失败。原因不是行为或覆盖率，而是三个手工修改测试文件混入不同换行符，
其中 `CurrentWalkthroughTests` 还缺少一次 Google Java Format 折行。普通本地命令默认比较
已经移动到当前提交的 `javaproject/master`，因而未覆盖本版差异；使用上一 canonical tag
v1881 的 peeled commit `f0db6641` 后可精确复现。

修复提交 `d525524b` 只落下一处格式折行，另两文件的行尾归一化不会形成 Git 内容差异。
同一 prior-SHA Spotless 门与 30/30 发布门在本地通过。canonical run `29799705965` 随后
通过：Docker-tagged verification 2:03，headless 19:50；后者包含精确 Spotless、完整 wrapper
verify、生产 profile smoke 与 JaCoCo 上传。该结果关闭实现 CI，但不能替代 closeout 自身 CI。

## 非目标与安全边界

本版本不修改 endpoint、HTTP 方法、response record、Catalog 事实、checks、status 条件、
transaction、fixture、SpotBugs 豁免、跨项目 digest、部署或回滚。它不启动 Java、Node 或
mini-kv，不打开 write routing、active shard router、credential value read、raw endpoint
resolution、managed audit connection、deployment/rollback 或 sibling autostart。Node 版本
和 mini-kv 只出现在既有协调元数据与边界文本中，不形成运行时依赖。

公开长名 Service、Response 与 Controller 是兼容边界，本版不做破坏式改名。新增
`ReportRenderer` 与六个测试职责名均低于 40 字符；被触碰但必须保留的公开边界通过精确
行为和依赖门约束，而不是把新名字写进长名 baseline。

## 失败条件

以下任一情况都判定 v1882 失败：同一 oracle 在替换前后任一字符或顺序变化；七节/三十八行
数量变化；九个旧 renderer/support 任一回流；目标包超过 11 个文件或出现第二个 renderer；
旧长测试工厂回流；route suffix、公开 Service/Response、上游 closeout 或下游
acceptance-package 方向变化；全局 ops、renderer、行数、长名、文件大小或 baseline 任一
放宽；通过改测试期望、fixture、SpotBugs 豁免或只读标志制造绿色；中文讲解、精确归档、
Spotless、JaCoCo、SpotBugs、jar packaging、实现 CI、closeout CI 或 annotated tag 任一缺失。
