# v1868 README 展示与证据闭环讲解

## 实际工作量说明

v1868 是一个单主题维护版本，目标不是继续增加订单接口，而是修复仓库最外层的理解入口。此前真正的项目说明位于 `advanced-order-platform/README.md`，但 GitHub 仓库根目录是 `D:\javaproj`。访问仓库首页的人先看到的是父目录，原本没有 README，因而只能看到一个子目录、工作流和少量配置文件。项目内部已经积累了订单、库存、模拟支付、Outbox、RabbitMQ、失败事件审批重放、两千多个覆盖类和完整生产边界，却没有在第一屏回答“这是什么、强在哪里、哪些说法能验证”。这不是运行缺陷，却是严重的交付缺口：工程价值存在，但读者必须先猜目录，再打开一个接近三千行的深层 README，信息成本远高于理解收益。

本版把说明分成两层。仓库根 README 是展示层，只承担三十秒内建立正确心智模型：双语项目摘要、持续集成状态、四项可复现工程指标、系统与权限边界图、四条验证命令和深层文档入口。项目 README 仍是技术层，保留原来的运行方式、profile、API、消息、失败事件与证据接口细节，只在顶部增加同源 badge，并新增一个“自助验证”区。两层不是复制整篇文档，而是“入口摘要指向权威细节”。同时增加 `ReadmeExhibitionTests`，让根 README 不再是可以任意漂移的宣传页：路径、数字、成熟度标签和账本来源任何一项失真，测试都会失败。

工作量还包括一次 Step-0 对账。v1867 的远端 closeout 已经双绿，tag 也确实指向收口提交，但最终证据、版本账本和 closeout 文档仍停留在“CI/tag pending”。如果根 README 直接链接这些旧状态，读者会在同一页面看到“CI 绿色”和“远端待验证”两种结论。本版把运行 `29222696374`、提交 `952e4ab9` 与 canonical tag 的既成事实补回证据，同时继续保留“外部评审尚未授予 final”的限制。校正的是已发生事实，不是扩大成熟度。

## 入口路由

这个版本的“入口路由”首先不是 HTTP URL，而是读者从 GitHub 首页进入项目事实的路径。第一跳是仓库根 `README.md`，第二跳是 `advanced-order-platform/README.md`，第三跳才分流到 `PRODUCTION_READINESS.md`、Java 轨道证据、进度账本和三个 census 脚本。根 README 中的相对链接全部以 Git 仓库根为基准，例如 `advanced-order-platform/scripts/ops-root-census.ps1`；深层 README 中的相对链接则以项目目录为基准，例如 `docs/java-track-final-evidence.md`。两种基准不能混用，否则本地编辑器可能根据当前目录偶然打开成功，GitHub 页面却会得到 404。

CI badge 同样遵循根路由。工作流真正位于父仓库 `.github/workflows/maven-ci.yml`，因此链接使用 GitHub Actions 的 canonical workflow 地址，而不是项目子目录中的假路径。三个静态 badge 分别显示 `1915+`、SpotBugs `0` 和生产 Java 最大文件 `738 lines`。其中测试数使用带加号的已验证下限，不把新增的展示测试包装成新的业务能力；SpotBugs 零来自完整 verify 的实际报告；738 来自维护 census 和同值 Java 预算门。badge 是入口，不是权威数据源，所以紧邻它的 highlights 表必须链接到会失败的脚本或测试。

HTTP 入口本身没有变化。订单 Controller、库存与支付接口、failed-event 管理入口、readiness/evidence 路由以及 actuator 健康检查均保持原路径和方法。Mermaid 图只概括已有调用方向，没有新增 endpoint。图中的 evidence 侧路由明确标为 read-only，Node capstone 只从这条侧路读取；它不能从图形连线推导出 replay、部署、回滚或 SQL 的调用权。

## 响应模型

README 可以视为面向人类读者的响应模型。一个可靠响应必须区分事实字段、证据字段和权限字段。事实字段说明项目由 Java 21、Spring Boot、订单一致性、事务 Outbox、RabbitMQ 与失败事件治理组成；证据字段给出测试下限、静态分析结果、文件尺寸、root extraction 和可重跑命令；权限字段明确哪些动作没有被授权。若只展示能力列表，读者容易把“存在 replay 代码”误解为“任何 evidence 响应都能执行 replay”；若只展示测试数字，又会把工程治理误解为业务完备度。因此根 README 用一段英文和一段中文同时表达三类信息，并把成熟度标签单独固定为精确字符串。

`single-project validation + verified read-only cross-project integration (env-gated, single machine, no execution authority)` 没有被改写。这个字符串不仅是文案，也是 `JavaTrackCloseoutTests` 的常量契约。README 的中文说明可以解释它，但不能用“production ready”“fully integrated”之类更短、更强的词替代。项目 README 原有段落也没有搬动或改写，只在其前后插入 badge 和验证块，从而避免为了版式调整而破坏既有边界测试。

数字响应采用“当前值加来源”的结构。`805 → 104` 表示预登记的 direct-root 抽取阶段起点与最终绑定值，不表示总 `ops` 文件从 805 变成 104；总量仍由子包承载。`>750 / >1000 = 0 / 0` 表示生产 Java 源文件的行数分布，不表示测试数或归档数。四个连续 clean ledger cycles 引用的是连续版本行中同时记录本地 verify、提交和远端绿色运行的事实。新测试会同时读取 README、最终证据、账本和真实源码树，防止一个数字只在展示页自我证明。

## 上游证据配置

本版所用上游证据分为 GitHub、Java 本仓和 Node capstone 三类。GitHub 提供 workflow 状态与提交可达性；Java 本仓提供 census、JaCoCo、SpotBugs、归档 manifest 和进度账本；Node 提供只读跨项目 capstone 已经对真实 jar 做过验证的结论。根 README 不直接读取网络，也不把 Actions badge 当成构建命令。badge 只是 GitHub 渲染时查询当前 workflow 状态，真正的复现入口仍是 Maven wrapper 和 committed scripts。

Node capstone 的表述刻意限定为“Node-owned”“read-only”“live-verified against a real Java jar”。Java 没有因为展示页而获得启动 Node、写 Node 账本或管理 mini-kv 的能力。跨项目成熟度仍是环境变量显式开启、单机、无执行权的验证；它不等于默认 CI 中持续启动四项目，更不等于生产控制面。README Mermaid 图把 Node 放在 evidence endpoint 的消费者一侧，而不是订单服务、审批服务或数据库的调用者一侧，这个位置关系正是权限模型的一部分。

版本号和运行号也按来源分工。v1867 的修复运行 `29221687479` 证明跨平台归档哈希修复在 Linux CI 中有效；closeout 运行 `29222696374` 证明 tag 所指提交再次通过 Docker 与 headless 两路门。v1868 不篡改历史运行，只在过期文档中补记已经存在的 closeout 事实。新的 v1868 运行号要等本版提交后产生，不能提前写一个占位数字冒充证据。

## 服务层核心流程

Mermaid 图从 `HTTP clients` 进入 Spring MVC Controller，再进入 application services。服务层向下分成三个读者最关心的业务面：订单与库存、发布/重放审批、失败事件死信与重放。订单写事务会更新数据库并写入 transactional Outbox，后台发布器再把明确启用的事件交给 RabbitMQ；消息失败可以进入重试、死信与失败事件记录。审批数据和失败事件状态保存在数据库中，真正 replay 还需要操作员上下文、角色、审批状态、digest 和 readiness 重新校验。

这张图没有把所有类画出来，因为 README 的目标是解释稳定职责，不是复制代码图谱。`OrderApplicationService` 等领域门面负责事务编排，repository 负责持久化，Outbox 与消息组件负责交付，failed-event 服务负责查询、管理、审批和受控重放。v1867 已经把巨型命令与查询职责拆开；v1868 不触碰这些实现。根 README 的每个节点都能在深层 README、项目讲解或源码中继续追踪，但首页只保留能帮助读者判断项目价值的骨架。

图中的虚线 `Services -. evidence only .-> Ops` 表示 evidence 服务可以汇总运行与治理状态，不表示反向控制。Ops endpoint 返回 overview、readiness、approval status、execution contract、rehearsal hint 或 digest；这些响应可以让 Node 判断某个条件是否满足，却不能直接调用支付网关、执行 rollback SQL、读取 credential value 或打开 managed audit connection。把这条侧路单独画出，是为了让“可观察”与“可执行”在视觉上也不混为一谈。

## Java 证据检查

`ReadmeExhibitionTests` 是本版新增的唯一 Java 源文件，文件名和所有新标识符都在四十字符预算内。它有三个职责。第一，读取父仓库 README，要求 CI badge、三个静态 badge、成熟度原句、Mermaid 入口和关键 evidence 链接同时存在，并拒绝 `production authorized`。第二，读取深层 README，要求三个 census 和完整 Maven verify 命令逐字可发现。第三，直接扫描源码树：root `ops` 必须仍是 104 个直接 Java 文件，生产最大文件必须仍是 738 行，超过 750 和 1000 行的文件必须为零。

数字测试不是只对 README 做字符串 contains。它同时读取 `docs/java-track-final-evidence.md`，确认 1,915 测试基线、738 行和 SpotBugs 零报告确实存在；读取 progress ledger，确认四个连续版本具有已完成且远端 CI 通过的账本行；真实遍历生产源码计算最大行数。这样未来如果热点继续缩小，维护者需要同步更新 README 和证据；如果热点反弹，测试会先失败；如果有人只改 badge 而不改事实，交叉检查同样失败。

原有 `ProductionReadinessDocumentationTests` 与 `JavaTrackCloseoutTests` 继续运行，成熟度、CHANGELOG、生产边界、E1-E10 和 workflow action major 均不放宽。新测试没有复制这些断言，而是补上父仓库展示层此前无人负责的缺口。维护预算门仍限制生产和测试热点，优雅门仍检查长名、根 route owner 读者与 SpotBugs baseline。README 版因此仍服从同一套工程规则，不是测试体系之外的一次热编辑。

## mini-kv 证据检查

mini-kv 没有在 v1868 中被修改，也没有被 Java README 描述成 Java 的内部组件。它只出现在跨项目成熟度的背景中：Node capstone 曾执行真实 `minikv_cli`，并把新鲜只读输出与 Java jar、Node evidence 和 aiproj 制品放进同一份 readiness 报告。Java 首页不需要展示 mini-kv 的命令、slot 表或归档规模，因为这会让读者误以为仓库根可以直接运维 C++ 存储。

根 README 的 Node 消费侧只连接 Java 的 read-only ops evidence。mini-kv 参与 capstone 的事实由深层 evidence 和四项目计划负责，Java 不新增跨盘绝对路径，不移动历史 fixture，也不尝试在 Maven 流程中启动 mini-kv。真实跨项目复核依旧由 Node 的一条显式命令拥有，Java 只提供固定 tag 的 jar 和既有只读接口。

这种克制也是展示质量的一部分。首页不是把所有相关项目名字堆满，而是说明当前仓库真正负责什么、其他项目怎样在只读边界消费它。若未来 capstone 契约发生变化，应先更新上游计划和机械测试，再调整 README；不能因为图看起来更完整，就提前画出尚不存在的 active shard router、写路由或审计连接。

## 阻断与安全边界

根 README 明确列出 approval、replay、rollback、SQL 和 secret access，不是为了制造安全术语密度，而是防止最常见的权限误读。审批状态是 replay 的前置条件之一，不是执行令牌；readiness 为 true 仍要经过当前操作员角色、审批 digest 和事件状态复核；rollback 相关材料是 review/evidence，不是可执行脚本入口；Flyway 提供正向迁移，不授权 rollback SQL；credential handle 只是引用，不允许读取 secret value。

支付仍是 `SIMULATED`，README 使用“simulated payments”而不写 payment integration。RabbitMQ 能力需要 profile 或配置显式启用，默认 H2 开发模式不代表生产消息集群已经就绪。actuator 只暴露 health、info 和 metrics。Node 的只读消费不会触发 Java 写操作，managed audit connection、真实 credential resolver、部署和回滚继续关闭。

新展示测试拒绝 `production authorized`，但这只是最外层的反夸大门。真正边界仍由 `PRODUCTION_READINESS.md`、profile 配置、角色与审批测试、no-write contract 和完整 verify 共同保护。首页链接威胁模型而不复制全部内容，是为了让读者能快速看到警戒线，同时把细节交给唯一权威文档，避免两份边界说明日后分叉。

## 测试覆盖

本版验证顺序分为聚焦、归档和完整三层。聚焦层先执行 Spotless、README 展示测试、生产文档测试、Java track closeout 测试、优雅门和维护预算门。它需要完整编译生产与测试源码，但只运行与本次修改直接相关的断言，因此路径错误、数字漂移或格式问题会在清晰位置失败。第一次运行只发现新测试的一处 Spotless 换行要求，使用项目格式化器修正后，同一组测试原样重跑，不改期望。

归档层在讲解冻结后执行。v1867 将历史目录固定为精确 manifest，并允许只有新的外部计划才能显式调整。README 展示简报明确要求本版讲解，所以这是被授权的一文件增量，不是普通功能提交静默抬高预算。流程必须重建 manifest、记录新文件数与原始字节数，并让 `ArchiveRetentionTests` 重算所有文本规范化摘要；除这一篇讲解外，旧归档路径和摘要不得变化。

最后执行 `mvnw -B verify`。它覆盖全部单元与集成测试、JaCoCo 各包 floor、SpotBugs、Spotless、文档门和制品打包。讲解在这一步之前完成，避免出现“测试已经结束才补写说明，说明自身未被验证”的顺序错误。实现提交推送后还要等待 headless 与 Docker-tagged 两个 CI job；运行号回填账本后再创建 v1868 tag，并要求 tag 所在 closeout 提交再次双绿。

## 一句话总结

v1868 把一个已经具备大量工程证据、却在 GitHub 首页几乎不可见的 Java 项目，改造成“先看懂、再验证、最后深入”的两层入口。根 README 用最少信息回答项目是什么、核心链路怎样流动、五类高风险权限为何仍被阻断；深层 README 保留完整技术细节；测试把 badge、数字、链接、源码 census 和账本绑定在一起。这个版本没有增加一条运行路由，却提高了项目被正确理解、独立复现和诚实评审的概率，这正是后期维护阶段有价值的工程改进。
