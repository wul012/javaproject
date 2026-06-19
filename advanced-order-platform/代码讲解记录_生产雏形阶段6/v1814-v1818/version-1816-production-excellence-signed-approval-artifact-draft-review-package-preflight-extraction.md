# v1816 签批工件草稿审查包预检注册表迁出讲解

本版本处理的是 `OperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflight`
这一组 Java ops 注册表。它在业务含义上不是执行审查包生成，也不是签批文本落库，而是把
后续人工审查包需要看的证据槽位、阻断规则、摘要钉子和关闭条件提前排列出来。换成通俗说法，
它像一张只读检查清单：告诉维护者如果以后真的要进入审查包制作，需要先确认哪些来源已经存在，
哪些内容必须保持缺席，哪些风险仍然被锁住。v1816 做的不是扩大能力，而是把这张检查清单从根
`ops` 包里搬到自己的小房间里，同时保持门牌号、输出格式和安全边界都不变。

## 入口路由

本组入口仍然由两个根包 controller 暴露。`FoundationController` 负责基础侧的只读端点，
包括 catalog、digest pins、operator package 和 signature package；`AssuranceController`
负责保证侧的只读端点，包括 evidence package、value policy package、embargo package、
draft authoring gate 和 closeout。输入层面，调用者只需要访问既有 HTTP 路由，不需要提交
业务写入载荷，也不需要提供 credential value。输出层面，每个端点返回同一个 response record
结构，只是其中的 profile、endpoint、slot 列表、guard 列表、gate 列表和 warning 列表按端点
主题有所不同。

这次迁移最关键的路由处理，是新增
`OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths`。它只拥有这组
review-package-preflight 的九个 suffix 和公共 `BASE_PATH`。原来的
`OpsShardReadinessRoutePaths` 没有删除，也没有改变常量名，而是继续作为兼容聚合器，把同名
常量委托给新的 split owner。这样外部看见的输入路径仍是原来的
`/api/v1/ops/shard-readiness/...`，内部维护者却能从新 owner 看出这组路由归哪个家族。路由测试
同时比较三件事：根聚合器常量、split owner 常量、service `ENDPOINT`。只要有人以后误改其中
一个字符串，测试会直接暴露不一致。

## 响应模型

响应模型仍然是
`OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse`。
它的主要输出可以分成几类。第一类是版本和 profile 信息，告诉读者这是 Java 当前阶段的哪一份
只读证据。第二类是 `PackageSlot`，表达审查包预检需要观察的槽位，例如请求 manifest、工件预检
digest、模板 digest、审查 digest、操作者身份、签名算法、证据版本、脱敏值 digest、embargo 等。
第三类是 `PackageGuard`，表达每个槽位背后的阻断规则，例如原始签名不能出现、审批授权不能发出、
写路由不能打开、兄弟状态不能被修改。第四类是 `ReviewPackageGate`，它把一组更高层的 gate 条件
列成清单，例如审查包仍然只是槽位地图，签名捕获不存在，运行时 payload 锁住，服务启动不属于本步。

v1816 没有改变 record 字段，没有新增 JSON 字段，也没有删掉旧字段。它只改变 record 类所在的
Java package。因此 controller、route-path 测试、ProfileSection registry、AuthoringReadiness
requirement catalog 都改成显式 import 新包里的 response 或 service。对外部消费者来说，输出数据
的形状没有变化。对维护者来说，这个 response 不再混在一千多个根包类里，而是和它的 support、
slot catalog、guard catalog、service 放在同一个窄包下，定位成本明显降低。

## 上游证据配置

这一组预检不是凭空写死字符串，它读取上一环节 `ArtifactDraftReadinessLane` 的只读 endpoint
常量。也就是说，review-package-preflight 的 slot 并不是直接启动上游服务，而是把上游就绪通道
已经公布的 endpoint 作为来源指针写入自己的证据槽位。这里的输入是上一版 v1815 已经公开的
immutable `ENDPOINT` 字符串，输出是本版 response 里的 `sourceEndpoint`。这条链路的价值在于
它让审查包预检知道自己的证据来自哪里，同时仍然不跨越到执行层。

从依赖方向看，v1816 是一个很适合切的点。它向外只读依赖 v1815 已经搬好的
`signedapprovalartifactdraftreadinesslane` 包，所以迁移时不需要再把上游一起拖动。它向内被
`AuthoringReadiness` 和 `SignedApprovalDraftProfileSection` 读取，因此本版只把本组 service 的
`ENDPOINT` 常量变成 public static final，并在保留根包的读取方里显式 import。这样后继家族仍然
能读到只读路线，不会因为包迁移被迫回到根包，也不会暴露 service 的业务方法。

## 服务层核心流程

服务层的流程很稳定。每个 service 先声明一个 `ENDPOINT`，这个 endpoint 由新 route owner 的
`BASE_PATH` 加上对应 suffix 拼出。随后 service 方法调用 support 的 `response(...)` 工厂，把
版本、endpoint、profile、slot 子集、guard 子集、gate 子集和 warning 列表装配成响应。catalog
service 返回全量 slot、guard、gate，digest pin service 只返回 digest 相关部分，operator package
service 只返回操作者包相关部分，signature package service 聚焦签名规则，evidence package service
聚焦证据来源，value policy package service 聚焦脱敏和价值字段策略，embargo package service 聚焦
禁发禁写，draft authoring gate service 聚焦草稿写作前置阻断，closeout service 给出本步结束条件。

本版的代码拆分不是简单移动文件。原来 `GateCatalog` 和 `GuardCatalog` 是两个 package-private
类，各占一个 Java 文件。新增 route owner 会增加一个文件，如果原样搬迁所有文件，总 `ops` Java
文件数会从 1,352 增到 1,353。为了不放松护栏，v1816 把 gate 数据合并进 GuardCatalog 同一个文件。
这不是硬凑文件数，因为 guard 和 gate 都是 fail-closed 审查包边界数据，二者共同表达“能看什么、
不能做什么、下一步必须另开计划”的安全条件。合并后服务层只从 GuardCatalog 读取 `allGuards()`、
`guards(...)`、`allGates()` 和 `gates(...)`，语义更集中，文件总量也保持不变。

## Java 证据检查

Java 侧证据分为编译证据、路径证据、计数证据和文档证据。编译证据先由 `test-compile` 检查，确认
迁移后的 package 声明、controller import、测试 import、public endpoint 可见性都能通过。路径证据
由 route-path 测试维护：它检查每个 service 的 `ENDPOINT` 等于根 `BASE_PATH` 加根聚合器 suffix，
并额外检查根聚合器 suffix 与新 split owner suffix 完全相等。计数证据由可读性测试维护：
`ReadabilityUpkeepGovernanceConsolidationPlanTests`、v1809 精确计数守卫、v1806 closeout 当前基线
都从 1,041 收到 1,025；新增的 v1816 测试还确认代表性实现文件已经进入新包，两个 controller 和
`OpsShardReadinessRoutePaths` 仍留在根包。

还有一个容易忽略的 Java 证据点是 SpotBugs exclude。这个 response record 包含列表字段，历史上
已经在 `spotbugs-exclude.xml` 有两处镜像 FQN。包迁移以后，如果不把 FQN 改到新路径，全量 verify
会在静态分析阶段留下陈旧配置。v1816 同步迁移这两处 FQN，保持静态检查配置与源码包结构一致。
这种工作看起来细，但它能防止后续维护者以为代码迁移完了，实际上质量工具仍然指向旧类。

## mini-kv 证据检查

本版本不启动 mini-kv，也不修改 C++ 仓库。mini-kv 在这条链路里只是被 Java 只读证据提到的上游
系统之一，表示审查包预检不会启动它、不会读取运行时值、不会打开写路由。response 的 warning 和
gate 会继续表达这些边界，例如服务启动不属于本步，运行时 payload 仍然锁住，写路由仍然关闭。
从输入输出角度看，mini-kv 的输入为零，因为没有进程启动、没有端口连接、没有 WAL 或 snapshot
读取；输出也为零，因为本版只产出 Java 仓库内的源码、测试、文档和 git 版本化证据。

这个说明很重要，因为四项目协作里 mini-kv、Java、Node 有依赖顺序，但本次改动不是契约变更。
没有 evidence schema 变化，没有 route 字符串变化，没有归档路径变化，也没有把 mini-kv 的文件
搬来搬去。因此它属于 Java 内部维护性推进，可以独立完成，不要求 C++ 仓库同步发版。后续如果要
动 mini-kv 的 archive retention、命令拆分或只读证据 schema，那会是另一类工作，需要单独审查。

## 阻断与安全边界

v1816 的阻断边界可以用一句话理解：允许看见准备状态，不允许执行生产动作。它没有开启 write
routing，没有启用 active shard router，没有读取 credential value，没有解析 raw endpoint，没有
创建 managed audit connection，没有触发 deployment 或 rollback，也没有启动 Java、Node、mini-kv
进程。controller 保留在根包，是为了保持现有 HTTP 装配和 Spring 扫描边界稳定；实现层迁出根包，
是为了降低维护成本，而不是扩大权限。

归档边界也没有变化。本项目里 Node 侧仍然硬引用过 Java 和 mini-kv 的历史归档路径，Java 自身的
讲解、截图、证据 JSON、`a/` 到 `f/`、`e/<version>/` 这些目录都不能因为一次包迁移被重命名或移动。
v1816 新增的是 `docs/ops` 里的说明和当前讲解目录里的中文文档，不搬旧 archive root。这个边界能
保护跨项目证据链：新版本可以持续瘦身根包，但历史证据不能因为维护者想整理目录而失去可追溯性。

## 测试覆盖

测试覆盖分几层。第一层是迁移家族自己的 service tests，它们被搬进新包，继续验证 foundation 和
assurance 端点输出的 slot、guard、gate 数量和关键语义。第二层是 controller tests，它们留在根包，
通过显式 import 新包 service 来验证 HTTP 边界还能组装响应。第三层是 route-path tests，它们锁定
根聚合器、split owner、service endpoint 三者一致。第四层是 downstream tests，包括
AuthoringReadiness 和 SignedApprovalDraftProfileSection 的相关读者，确保它们可以继续读取本组
public endpoint。第五层是 readability tests，检查文档索引、文件位置、根包计数和总文件数护栏。

全量 verify 还会覆盖 Spotless、JaCoCo、SpotBugs 和所有已有单元测试。这里的测试策略不是为了制造
漂亮数字，而是围绕迁移风险布置：包名风险由编译和 import 测试发现，路由漂移风险由 route-path
测试发现，文档漂移风险由 readability 测试发现，静态分析配置风险由 SpotBugs 阶段发现，根包回流
风险由计数钉子发现。这样 v1816 即使是维护性版本，也有明确的输入、输出和失败信号。

## 实际工作量说明

本版本的实际工作量主要在四块。第一块是文件迁移和长路径处理。这个家族类名极长，普通 `git mv`
在 Windows 上会遇到路径长度限制，所以迁移时需要先确认未丢文件，再用长路径方式补搬剩余文件，
最后用 git status、编译和文件计数核对。第二块是引用修复。controller、controller test、
AuthoringReadiness、ProfileSection registry、route tests 都要从隐式同包访问改成显式 import，
service endpoint 也要从根 route aggregator 改到新 split owner。第三块是结构优化。GateCatalog
不是直接保留成独立文件，而是合并到 GuardCatalog，让新增 route owner 不增加总文件数，同时把审查
包阻断数据放到同一个包内聚点。第四块是证据补齐。CHANGELOG、`docs/ops` note、README 索引、
progress 表、中文讲解、readability tests、spotbugs exclude 都要同步，否则代码迁移只是表面完成。

这里特别写明“禁止硬凑”，因为本项目后期版本不能靠空洞文字或无意义拆分凑出一版。v1816 能写出
足够长的讲解，是因为它确实触碰了路由所有权、包边界、下游只读 endpoint、静态分析配置、文件数
护栏、中文讲解归档和全量测试链路。若某个未来版本只改一两个常量，却要求写三千字，那应该先扩大
实际工程工作量，找到真实的维护收益，再写讲解。本项目当前的价值不在于版本号本身，而在于每一版
都把根包压力、证据可追溯、测试护栏和跨项目边界向更可维护的方向推进一点。

对后来接手的人来说，这版还有一个直接收益：以前想理解审查包预检，必须在根包里从 controller、
service、slot catalog、guard catalog、gate catalog、support、response 之间来回搜索，而且相邻还有
ArtifactDraftPreflight、ReadinessLane、AuthoringReadiness 等名字相近的家族。迁出以后，维护者先看
根包 controller 就能知道 HTTP 入口仍在原位，再进入新 package 就能一次看完实现层；如果只关心路由，
看 signedapproval 下的新 route owner；如果只关心后继消费，看 AuthoringReadiness 的 import。这个
阅读路径更短，误改面更小，也更适合后续继续沿着链路推进下一组抽取。
维护者排查会更快，复核也更稳。

## 一句话总结

v1816 把签批工件草稿审查包预检家族从拥挤根包迁入独立只读维护包，保持路由和响应完全不变，同时
用 route owner、公开 endpoint、GuardCatalog 合并、SpotBugs FQN 迁移、计数钉子和中文证据说明把
“能看见审查包准备状态，但不能启动写入或运行时动作”的边界钉得更清楚。
