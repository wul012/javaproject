# Java 三分优雅度提升路线图

## 背景与授权

用户于 2026-07-20 明确授权持续创造、重构和优化 Java 项目，并允许后续再对关联项目做
对齐。本路线图只修改 `D:\javaproj`；Node、mini-kv、aiproj 工作区保持只读。历史归档目录、
已发布 tag 和 Node 已固定引用的证据路径不移动、不改写。外部路由若需迁移，必须先保留兼容
入口并形成独立对齐清单，不得在内部重构中静默改变。

## 起点核对

v1872 tag、本地 `master` 与 `javaproject/master` 同指 `07505752`，最终 receipt CI
`29695354262` 成功。生产 Java 1,484 个，其中 `ops` 1,352 个；Renderer 为 121 个、
5,355 行，119 个 Renderer 文件名超过 40 字符。生产长文件 stem 1,297 个、长标识符出现
21,167 次，超过 500 行的生产文件 32 个，最大 738 行。这些数字来自提交脚本或同口径
长路径安全 census，后续只能收紧。

## 三分目标

| 指标 | 起点 | 目标 | 机械证据 |
| --- | ---: | ---: | --- |
| `ops` 生产 Java 文件 | 1,352 | <= 650 | `ops-elegance-census.ps1` + Maven gate |
| Renderer 文件 | 121 | <= 30 | 同上 |
| 超长 Renderer 文件名 | 119 | 0 | 同上 |
| 生产长文件 stem | 1,297 | <= 550 | `java-maintainability-census.ps1` |
| 长标识符出现次数 | 21,167 | <= 9,000 | 同上 |
| 超过 500 行生产文件 | 32 | <= 8 | 同上 |
| 最大生产文件 | 738 | <= 600 | 同上 |

目标表达的是本轮计划的结构终点，不是允许一次批量放宽的预算。每个版本必须比前一版更严，
最终分数由外部评审判断，本仓库只报告可复现指标。

## 执行阶段

1. Renderer engine：建立共享 Markdown section engine，按依赖链把一次性 Renderer 类收敛
   为每个家族一个短名报告组合器，同时保留响应字节与只读路由。
2. Catalog engine：把只返回固定列表或字段投影的 Catalog 改成声明式规格和共享映射器，删除
   重复工厂类；第三个相似 Catalog 不再允许独立成文件。
3. Service boundary：将只负责拼接十余个 Catalog 的 Service 收敛为明确 aggregate pipeline，
   保留 Spring 入口与跨家族依赖方向。
4. Release approval：拆分剩余 500 行以上文件，提取短名领域概念，并删除被替代的长名构建器。
5. 收尾：重跑 exact census、完整 verify、远端 CI 和跨项目只读 capstone；请求外部评审，
   不自授“提高三分”结论。

## v1881 检查点

Renderer engine 已连续覆盖八个真实输出家族。当前 ops 为 1,266，Renderer 为 45 个、
3,616 行、30 个超长文件名；生产长 stem / 长标识符使用为 1,197 / 20,544。相对 v1872
起点，Renderer 已减少 76 个、1,739 行，超长 Renderer 名减少 89 个，但尚未达到
Renderer <=30、超长 Renderer=0、ops<=650 和全局长名目标，因此不能把阶段或九分目标写成
完成。下一步继续处理剩余高收益 Renderer 家族，再进入 Catalog engine；每个家族仍须先冻结
完整输出，且不能用合并成大文件代替抽象收敛。

## v1882 检查点

Release-acceptance sustainment 报告把九个一次性 renderer/support 收敛为一个 118 行的
`ReportRenderer`，同一份 7 节 / 38 行 oracle 在旧实现与新实现上先后通过。当前 ops 为
1,258，Renderer 为 38 个、3,521 行、22 个超长文件名；生产长 stem / 长标识符使用为
1,188 / 20,495。相对 v1872 起点，Renderer 已减少 83 个、1,834 行，超长 Renderer 名
减少 97 个。测试文件仍为 900，但六个长职责名被短名替代，名称 baseline 新增 0、删除 35。

本检查点仍未达到 Renderer <=30、超长 Renderer=0、ops<=650、生产长 stem<=550 或长标识符
使用<=9000，因此不自称九分。下一步按输出所有权处理相邻 route-path-split 主报告，不把独立
closeout 输出塞进同一组合器；Renderer 阶段达到目标后再进入 Catalog engine。

## v1883 检查点

Route Split 保留五个公共兼容边界，把十九个长内部壳替换为十二个短领域 owner，并删除七个
纯转发文件。主报告 6 节 / 43 行、closeout 3 节 / 15 行由同一组 oracle 在旧实现和新实现上
先后通过。当前 ops 为 1,251，Renderer 为 32 个、3,448 行、14 个超长文件名；生产长
stem / 长标识符使用为 1,169 / 20,417，测试使用降到 9,999。精确名称 baseline 删除
66 项、新增 0 项。

Renderer 数量距离 <=30 只差两个，但超长 Renderer、ops、生产长 stem 和长标识符目标仍远未
完成，因此仍不自称九分。下一步优先处理三个 ProfileSection 重复渲染族：先提取真正共享的
typed section engine，再让第三个 case 数据化，不能为了跨过 Renderer 数量门而机械合并文件。
同时进入 Catalog engine 前，继续要求每个改动家族先有旧实现精确 oracle 和直接下游回归。

## v1884 检查点

三个 Profile Section 家族先在旧实现冻结 5 + 5 + 9 个完整 `RenderedSection`，再用一个
领域中立的不可变 `ProfileSections` 索引引擎和三个包内短适配器替换五个长 renderer 与
三份重复字段聚合算法。公共 Response、路由、controller 和只读事务边界不变；Text Package
特有的 group 白名单和 order 排序保留在本地适配器。新旧实现使用同一组六项 exact oracle，
十九个完整输出原样通过。

当前 ops 为 1,249，Renderer 首次达到阶段目标 30 个，总行数 3,372，超长 Renderer 文件名
降到 9。生产长 stem / 长标识符使用为 1,163 / 20,334，测试为 758 / 9,995；exact baseline
删除 24 项、新增 0 项。Renderer 数量目标达成不等于整体九分：超长 Renderer=0、ops<=650、
生产长 stem<=550、长标识符使用<=9000 和大文件目标均未完成。下一阶段先清完剩余九个长
Renderer，再进入 Catalog engine；每一刀继续要求旧输出 oracle、窄共享抽象与领域政策留在
适配器，禁止把重复代码简单搬进万能类。

## v1885 检查点

四份 Code Walkthrough 报告先在旧实现上冻结全部二十二个 heading、一百六十八行正文、逐节
行数和全文 UTF-8 SHA-256，再由四个包内短 `ReportRenderer` 复用既有
`MarkdownSections.counted/mapped`。领域 record 和行格式仍归本包所有，公共 Response、路由、
Controller、Service 事务与 Catalog 数据不变；同一组四项 exact oracle 在替换后原样通过。

四个目标 renderer 从 541 行降到 458 行。当前 ops 仍为 1,249，Renderer 保持 30 个但总行数
从 3,372 收紧到 3,289，超长 Renderer 文件名从 9 降到 5。生产长 stem / 长标识符使用为
1,159 / 20,277，测试为 754 / 9,970；exact baseline 删除 16 项、新增 0 项。Renderer 数量
目标虽然保持达成，超长 Renderer=0、ops<=650、生产长 stem<=550、长标识符使用<=9000 与
大文件目标仍未完成，因此仍不自称九分。下一步处理剩余五个长 renderer，继续遵守旧输出
先冻结、共享机制与领域政策分离、每刀总复杂度实际下降的要求，然后进入 Catalog engine。

## v1886 检查点

最后五份长名 Renderer 先在 v1885 实现上冻结完整输出：总计三十三个输出块、二百零二行
正文、逐块行数以及五个全文 UTF-8 SHA-256。Credential archive、Sandbox dossier、
Sandbox manifest、Screenshot archive 分别由短职责 owner 复用 `MarkdownSections.mapped`
或 `counted`；Profile handoff 因为是五条一对一领域记录，保留直接映射，不为表面统一引入
错误抽象。同一组 oracle 在新实现上原样通过。

Renderer 仍为 30 个，总行数从 3,289 收紧到 3,246，超长 Renderer 文件名从 5 精确归零。
生产长 stem / 长标识符使用 / 唯一值收紧到 1,154 / 20,240 / 2,713，测试收紧到
746 / 9,916 / 3,763；exact baseline 删除 28 项、新增 0 项。v1801、v1802、v1803、
v1829 的历史门同时升级为“当前短 owner 必须存在、退休长名在窄包与根包都不存在”。

这一版完成的是 Renderer 专项，不代表整体代码已经达到九分。ops<=650、生产长 stem<=550、
长标识符使用<=9000 与大文件目标仍未完成。下一阶段进入 Catalog engine：先按输出协议、
依赖方向和领域概念做 census，再选至少三个真实重复 case 建立数据化共享机制；禁止把大量
常量机械塞进万能 Catalog，也禁止仅靠改名制造进度。

## v1887 检查点

CandidateDocument 的两条 handoff 链原先各用七个单列表 Catalog，把一次响应组装拆成十四个
超长 owner。v1887 先在旧实现上冻结两份完整规范 JSON、两组集合尺寸和两个 SHA-256，随后
分别以 `HandoffCatalog`、`PrecheckHandoffCatalog` 收拢领域内的来源、模块、归档、策略、
制品、消费者规则和 gate。两个 bundle 都只暴露一个 `from(source)`，返回七组防御性复制的
类型化 `Evidence`；Support 仍独立负责响应状态与 check，Service 仍保持只读事务。

这里没有制造跨两种 Response 的泛型万能引擎：两条链只是生命周期形状相似，条目类型、编号、
投影规则和归档语义并不相同。共享到 bundle 边界后停止，反而让数据政策仍能在本领域直接阅读。
新 owner 分别为 235 与 182 行，结构门把二者都锁在 300 行以内，并要求旧十四个文件永不复活、
每个 Service 只调用一次组装入口、每个 Evidence 精确复制七组列表。

本版把 ops `1,249 -> 1,237`、Catalog `332 -> 320`；生产长 stem/使用/唯一值收紧为
`1,140/20,178/2,699`，测试收紧为 `737/9,898/3,741`，exact baseline 删除 46 项、新增
0 项。相关行为、冻结输出、结构、优雅与变更门通过 56/56。Renderer 专项目标继续保持
30 个、3,246 行、0 个长文件名，但整体 ops<=650、生产长 stem<=550、长标识符使用<=9000
和大文件目标仍未完成，所以仍不自称九分。下一刀继续从 CandidateDocument 中寻找能被完整
响应 oracle 覆盖的 Catalog 簇，只有真实总复杂度下降才推进。

本检查点的最终 release gate 通过 1,998 个测试，JaCoCo 分析 2,121 类并满足全部阈值，
SpotBugs 为 0/0，打包 jar 为 68,017,026 字节；远端双 CI 与 tag 仍按发布纪律后置完成。

## v1888 检查点

CandidateDocument submission、intake 与 profile 三个核心响应原先分别依赖 3、5、6 个单职责
Catalog。v1888 先在旧实现上冻结三份完整规范 JSON、集合向量与 SHA-256，再用
`SubmissionCatalog`、`IntakeCatalog`、`ProfileCatalog` 三个包内 owner 收拢一次响应所需的数据。
三个 Service 各调用一次 `from(...)`；三个 `Evidence` 精确复制 4/6/6 组列表；Support 仍拥有状态
和 checks，`ProfileRenderer` 仍单独拥有 Markdown 行为。

本版没有建立跨 Response 万能映射器。共享停在“一个响应一个不可变 bundle”的生命周期边界，
checkpoint、slot、field、route lock 与 gate 的具体政策仍留在各领域。三个 owner 为 131、190、
197 行，结构门把它们锁在 300 行内，并要求旧十四个文件永久缺失、三个 Service 各只有一次组装、
Profile Catalog 不得吸收 renderer。原七个 Profile Catalog 测试壳合并为一个当前语义 owner，五个
被触及测试职责改成短名，所有原断言保留。

本版把 ops `1,237 -> 1,226`、Catalog `320 -> 309`、测试 Java `909 -> 905`；生产长
stem/使用/唯一值收紧为 `1,126/20,107/2,685`，测试收紧为 `725/9,866/3,719`，exact
baseline 删除 58 项、新增 0 项。三份完整响应在替换前后保持相同向量和摘要，相关行为、结构、
优雅与变更门通过 51/51。讲解 3,742 Han/10 headings，归档 1,699 files/20,179,335 bytes。
整体 ops<=650、生产长 stem<=550、长标识符使用<=9000 与大文件目标仍未完成，所以仍不自称九分；
最终 release gate 通过 2,005 个测试，JaCoCo 分析 2,113 类并满足全部阈值，SpotBugs 0/0，
jar 为 68,010,007 字节。Implementation commit `abb82a98` 的 Actions run `29879782402`
通过 Docker 1:42 与 headless 19:31，包含 prod smoke 和 JaCoCo 上传。Closeout
`15ad48bd` 的 run `29880876879` 通过 Docker 2:09 与 headless 18:37；tag
`v1888-order-platform-candidate-core-catalogs` 在本地和 canonical remote 均 peel 到该
closeout。

## v1889 检查点

MinimalReadOnlyGateExecution 基础 Registry 的七组静态证据原由七个长名 Catalog 分散提供。
本版先在 v1888 实现上冻结完整响应：集合向量 `5/5/20/10/4/6/5/6/20`，sorted-property
UTF-8 JSON SHA-256 为
`8f33da2c1ed32695ef245c69cbf4a90d4b5b62324bb98e13c115ebec26df0b36`。随后七个 owner
收敛为一个 284 行 `RegistryCatalog`；类型化 `Evidence` 接管七组不可变快照，Service 只装配
一次，Support、Renderer、公共 route/Response/Controller 与只读事务不变。同一完整 oracle
在替换后继续通过。

生产 Java `1,358 -> 1,352`，ops `1,226 -> 1,220`，Catalog `309 -> 303`，execution package
`23 -> 17`，测试 Java `905 -> 904`。生产名称收紧到 `1,119/20,072/2,678`，测试收紧到
`721/9,856/3,710`，exact baseline 删除 23 项、新增 0 项。三个长名 Catalog 测试合成一个
短语义 owner，被触碰的 v1843 历史门也改为短名且新增当前收敛约束。相关行为、oracle、结构、
不可变性、优雅和 staged-change 核心门通过 39/39，扩展 execution/archive/controller/docs
选择通过 70/70。讲解 3,247 Han/10 headings，归档精确为
1,700 files/20,194,403 bytes。完整 release gate 固定 v1888 commit `15ad48bd`，通过
2,007 个测试（7:23）、JaCoCo 2,108 类/all floors、SpotBugs 0/0，并打包 68,005,806-byte
jar。Implementation commit `dc73b52c` 的 run `29883341547` 通过 Docker 2:04 与 headless
19:10，包含 prod smoke 和 JaCoCo 上传。整体九分目标仍以路线图 DONE 条件和外部复核为准；
Closeout `99e1afd2` 的 run `29884385641` 通过 Docker 2:19 与 headless 19:14；tag
`v1889-order-platform-execution-registry-catalog` 在本地和 canonical remote 均 peel 到该
closeout，v1889 发布链已闭合。

## v1890 检查点

MinimalReadOnlyGateExecution Archive Registry 的八组投影原由八个长名 Catalog 分散生成，
service 随后把八个列表分别传给 renderer 和 Support。v1890 先在 v1889 实现上冻结完整响应：
集合向量 `1/6/5/20/10/4/5/7/6/20`，sorted-property UTF-8 JSON SHA-256 为
`d5e75e352cee97a6f2c30111e0af57bb39af770b31cd420a018994b003e05859`。随后八个 owner
收敛为一个 183 行 `ArchiveCatalog`；类型化 `Evidence` 复制八组列表，service 只生成一次，
renderer 与 Support 通过同一聚合值读取但继续分别拥有展示和状态职责。

生产 Java `1,352 -> 1,345`，ops `1,220 -> 1,213`，Catalog `303 -> 296`，execution package
`17 -> 10`，测试 Java 保持 904。生产名称收紧到 `1,111/20,032/2,670`，测试收紧到
`716/9,846/3,697`，exact baseline 删除 29 项、新增 0 项；renderer 数量保持 30，总行数
`3,246 -> 3,241`。两个旧 Catalog 测试收敛为一个当前 owner，三个被触及的 archive 测试改为
短职责名，结构门要求八次复制、一次装配、200 行上限和八个退休文件永久缺席。

旧实现和新实现通过同一完整 oracle；初始行为、结构、优雅联合门已绿。讲解 3,273 Han/10
headings，归档精确为 1,701 files/20,209,891 bytes。整体九分目标仍以路线图 DONE 条件和外部
复核为准。第一次完整门仅暴露设计说明的精确标签协议，按标准标签修复后 11/11 通过；第二次
完整 release gate 固定 v1889 commit `99e1afd2`，通过 2,009 个测试（10:33）、JaCoCo
2,102 类/all floors、SpotBugs 0/0，并打包 67,998,687-byte jar。远端双 CI、closeout 与
canonical tag 尚未执行，不能提前记为完成。

## DONE 与失败条件

- 每版都有变更前后 census、行为测试、完整 `mvnw -B verify`、提交、tag、push 和绿色 CI。
- 讲解在最终 verify 前完成，中文不少于 3,000 字且严格使用十个标准章节。
- 删除实现形状测试时，必须以更严格的当前结构 ratchet 和行为 oracle 替代；不得只删测试。
- 修改测试期望、fixture 字节、路由字符串或响应文本来迁就重构，整版回退。
- Renderer/Catalog 数量、长名 baseline、文件大小或 SpotBugs 豁免上升，整版回退。
- 新共享引擎只被一个 case 使用、或共享引擎比被替代代码更难读，整版回退。
- 历史 archive 路径、已发布 tag、credential value、写路由和执行权限不属于本计划。

## 对抗性自审

最强质疑是“把很多小文件塞进一个大文件只是换一种难看”。因此每个组合器必须低于 300 行，
通过短名、类型导入和声明式 section mapping 降低总行数与长标识符次数；只降低文件数而不降低
总复杂度的版本不算有效进展。
