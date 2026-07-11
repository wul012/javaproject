# version-1848：Operator-CI 发布验收基座提取讲解

## 实际工作量说明

本版只做本项目 Java 仓库内的结构治理，核心工作不是新增一个看得见的接口，而是把已经存在的
发布验收证据链从拥挤的根 `ops` 包中完整迁出。禁止硬凑篇幅，也禁止为了让测试变绿而修改业务
期望。本版实际处理了二十五个主源码、七个包内测试、一个留根控制器、一个留根控制器聚合测试、
十一个下游 Archive 源文件、两组 SpotBugs 镜像、三处根包收缩棘轮、一份机械守卫和两份说明文档。
每个修改都服务于一个可检查的目的：让依赖方向从“同包内什么都能看见”变成“下游只能通过公开
Service 和 Response 读取上游结果”。

迁移前，ReleaseAcceptance 的目录、渲染器、服务、响应和帮助类都直接放在
`com.codexdemo.orderplatform.ops`。这个根包同时承担控制器入口、全局路由聚合、共享核心和大量
历史证据实现，阅读者很难仅凭目录判断哪些类属于一个完整功能。迁移后，二十五个实现统一位于
`ops.maintenance.ciaccept`，根包只留下 Spring 控制器。七个真正依赖包私有目录和渲染器的测试
跟随实现移动，控制器 Markdown 测试留在根包，从包外继续验证公开边界。这不是把文件换个位置就
结束，而是重新建立编译器可以执行的所有权规则。

包名也经过路径预算。若把完整业务前缀机械翻译成包名，最长主源码和测试路径达到三百三十九和
三百四十七字符，在 Windows、Git、Spotless、编译输出和测试报告生成环节都容易触发长路径问题。
最终采用 `ciaccept` 这个短语义名，分别把最长路径压到二百四十七和二百五十五字符。类名、文件名、
Bean 类型、接口路径和响应字段完全不改，所以缩写只承担内部目录定位职责，不会泄漏到用户协议。

## 入口路由

本版对应的 HTTP 输入是一个无请求体的只读 GET。调用方访问 shard-readiness 基础路径下的
`minimal-read-only-gate-operator-ci-handoff-archive-digest-consumer-package-verification-dossier-release-acceptance-registry`。
输入中没有账号、密钥、原始端点、部署参数或可执行指令，也没有查询参数可以改变证据判断。入口
控制器继续留在根 `ops` 包，由 Spring 扫描方式、类名和方法签名都保持不变。控制器收到请求后只做
一件事：调用注入的 ReleaseAcceptance RegistryService，再把返回的 Response 原样交给 Web 层。

路由所有权没有重新发明。v1840 已经把 ReleaseAcceptance 相关后缀集中到公共
`OpsShardReadinessReleaseAcceptanceRoutePaths`。迁移前服务通过根聚合器取 BASE_PATH 和后缀，迁移
后服务直接读取这个公共所有者；根控制器仍读取根聚合器，而根聚合器仍委托同一常量。两个读取路径
最终拼出相同字节，因此“内部依赖更清楚”和“外部地址不变化”可以同时成立。这里没有复制字符串，
没有创建第二个真相来源，也没有放宽路由行数上限。

用通俗例子理解：根聚合器像车站总导览牌，ReleaseAcceptanceRoutePaths 像负责某条线路的正式时刻
表。控制器仍可以看总导览牌，迁出的服务则直接看所属线路的时刻表。两块牌子引用同一班车数据，
乘客访问的站点名称不变，但维护者终于知道应该去哪里修改线路定义。机械测试同时读取控制器、服务
和历史路由断言，只要某人把一个字符改错，编译或测试就会立即失败。

## 响应模型

服务输出是公开的 ReleaseAcceptanceRegistryResponse record。它先给出项目、版本、只读标志和执行
禁用状态，再记录入口、profile、来源计划、归档验证计划和操作员交接计划；随后给出来源 dossier 的
版本、端点和状态，以及十组验收证据的数量、通过数量和结构化条目；最后提供 Markdown sections、
检查字符串和总状态。调用方既可以按 record 字段做机器判断，也可以读取 Markdown 供人工复核。

十组证据分别回答不同问题。SourceDossierSnapshot 说明这次验收消费的是哪一份上游 dossier；
ReleaseReadinessGate 检查发布所需条件是否齐备；EvidenceChainEntry 证明证据链没有断点；SignoffLane
记录谁以什么只读方式签收；CiReplayLane 说明 CI 重放只做观察而不触发执行；BoundaryControl 明确
凭据、原始端点、托管审计连接等边界仍关闭；RetentionPolicy 说明证据保留方式；ReplayDecision
记录允许或阻断的重放决策；CloseoutCheckpoint 固定收尾条件；ScorecardEntry 汇总预期和实际数量。

迁移没有改变 record 的名字、字段顺序、嵌套 record、集合类型、构造参数或状态词。为什么强调字段
顺序？因为即使 Java 调用侧能按访问器读取，序列化后的 JSON 仍可能被快照、证据摘要或下游解析器
使用。结构迁移若顺手整理字段，看似整洁，却会把“包治理”扩大成“契约变更”。本版严格把这种
变化排除在外。SpotBugs 中 Response 与 MarkdownSection 的双份镜像只更换完整类名，既保留暴露
集合的既有风险决策，也确保扫描工具能继续定位迁移后的真实类型。

## 上游证据配置

ReleaseAcceptance 的唯一运行时上游是 v1847 已迁出的 VerificationDossier RegistryService。输入不是
用户临时提交的数据，而是该上游服务生成的不可变证据响应。v1847 已经公开 Service 和 Response，
所以 v1848 的二十五个实现能够通过一条明确 import 消费 dossier，而不需要回到根包访问帮助类。
这正是依赖安全顺序的价值：先迁被依赖者，再迁依赖者，每一刀都由编译器验证公开边界是否足够。

上游 dossier 汇总了 consumer package 的来源、段落摘要、受众路由、CI lane、验收门、边界审计、
发布检查单和交接回执。本版不会重新读取文件系统、网络或凭据，也不会绕过 dossier 自己重算来源。
ReleaseAcceptance 只把 dossier 中已经固定的事实映射成更接近发布决策的十组视图。例如，dossier
中的某个边界审计项会被转成 BoundaryControl；某个 CI lane 会被转成只读 CI replay lane；来源版本
和端点会进入 SourceDossierSnapshot。每个目录方法都是确定性映射，相同上游输入必然产生相同输出。

下游是仍在根包等待 v1849 的 ReleaseAcceptanceArchive。它需要 ReleaseAcceptanceRegistryService
取得完整响应，并在多个 catalog 中读取嵌套 record。迁移后，下游只显式导入 Service 和 Response，
没有导入任何 package-private catalog、renderer 或 support。这个约束很重要：如果 Archive 能直接
调用基座内部帮助类，两层就会重新黏在一起，下一次重构仍需成片修改。v1848 守卫遍历 Archive 源
文件，只要出现 Response 使用却缺少新包 import，测试就会指出具体文件。

## 服务层核心流程

服务方法 `registry()` 在只读事务中执行。第一步调用上游 dossier service 得到 source。第二步依次
生成 source dossiers、readiness gates、evidence chain、signoff lanes、CI replay lanes、boundary
controls、retention policies、replay decisions、closeout checkpoints。第三步把 source 与这些集合交给
scorecard catalog，计算每组的 expected、actual 和 passed 状态。第四步让对应 renderer 把十组结构化
数据变成十段稳定 Markdown。最后由 RegistrySupport 统一组装 Response，并根据检查项决定总状态。

每一步的输入和输出都可以具体说明。SourceDossierCatalog 的输入是 dossier response，输出是来源
快照列表；ReadinessCatalog 的输入仍是同一 response，输出是发布门列表；EvidenceChainCatalog 输出
证据链条；SignoffLaneCatalog 输出签收通道；CiReplayCatalog 输出只读重放通道；BoundaryControlCatalog
输出锁定边界；RetentionPolicyCatalog 输出保留策略；ReplayDecisionCatalog 输出重放决定；
CloseoutCatalog 输出收尾检查点。Renderer 不产生新业务事实，只把对应列表投影成人可读行；Support
不重新判断目录内容，只聚合数量、检查字符串和最终状态。

举一个完整例子。假设上游 dossier 版本为 Java v1467，包含九个段落摘要、五个 CI lane、八个边界
审计和若干交接回执。服务首先保留这个来源版本；随后 readiness catalog 检查发布所需证据数量；CI
catalog 把五个 lane 标记为只读；boundary catalog 将八项控制转成 locked 状态；scorecard 对每组
比较预期与实际。所有项目通过时，Response 的 releaseAcceptanceState 与 status 为 passed，同时
executionAllowed、startsJavaService、startsMiniKvService、readsCredentialValue、resolvesRawEndpointUrl
和 managedAuditHttpAllowed 仍为 false。输出表达“证据足够进入发布验收”，不等于“现在执行发布”。

## Java 证据检查

Java 侧证据分成结构、行为和治理三层。结构层由 v1848 新守卫固定：二十五个文件必须只存在于
`maintenance/ciaccept`，根目录不得残留同名实现；七个包内测试必须跟随移动；控制器和控制器聚合
测试必须留根；目标目录的 Java 文件数必须精确匹配。这样以后任何人把一个 catalog 随意移回根包，
或者把无关文件塞进该包，都会得到可定位的失败。

行为层复用原家族测试。CI boundary 测试验证只读和禁执行标志；closeout/scorecard 测试验证每组
计数；evidence/signoff 测试验证证据链与签收通道；immutability 测试验证集合不可被调用方修改；
retention/replay 测试验证保留和重放决定；source/readiness 测试验证上游版本与发布门。控制器测试从
根包导入公开 TestSupport，真实构造 Controller 并调用 registry，证明跨包装配成立，而不是只在
目标包内部互相访问后自称通过。

治理层继续执行三个收缩棘轮。根 `ops` 直接文件数从五百七十三降至五百四十八，可迁文件从四百
六十八降至四百四十三，Operator-CI bucket 从四十八降至二十三；保留根文件仍为一百零五，未分类
文件仍为零，总 ops 主源码上限仍是一千三百五十二。三处历史根包断言一起改到五百四十八，其中
v1806 文档内的 1183 是冻结快照，不能误改。棘轮只能下降，不能为了迁移方便向上调。

最终验证顺序也有意义。先跑受影响家族、v1847 历史守卫、v1848 新守卫、三处棘轮和讲解合规测试，
快速发现 import、路径或说明问题；再跑 Spotless，消除机械迁移造成的 import 顺序与缩进差异；最后
运行完整 `mvnw verify`，让所有单元测试、集成测试、JaCoCo、SpotBugs、Spotless 和架构门共同复核。
只有完整命令结束并返回成功，文档才能补写“通过”。

## mini-kv 证据检查

本版没有修改 mini-kv 仓库，也没有启动其进程。ReleaseAcceptance 响应中的 mini-kv 相关信息来自
上游冻结证据和只读边界描述，不是本版实时连接 C++ 服务得到的结果。因此本版能证明的是 Java 内部
对既有 mini-kv 证据的确定性消费，以及 `startsMiniKvService=false`、无原始端点解析、无托管审计
连接；它不能声称完成 Java 与 mini-kv 的实时联合测试。

这一限定是机理透明的一部分。静态契约证据适合证明“预期字段和摘要仍一致”，真实联合测试才适合
证明“当前二进制可以启动并返回新鲜结果”。两者不能互相冒充。跨项目 capstone 由 Node 窗口负责
协调，若未来 C1 明确要求 Java 提供启动或只读端点支持，Java 会优先处理该请求；在收到请求前，
本版只守住自身边界，不越权修改 Node、mini-kv 或 aiproj。

测试中的 mini-kv 检查主要观察负面能力：服务不能启动 mini-kv，不能读取 credential value，不能
解析 raw endpoint URL，不能发起 managed audit HTTP，也不能把 release acceptance 状态解释为部署
授权。Response 中保留这些布尔字段，使审查者不必从“代码里没看到调用”推断安全性，而能直接读取
机器可检查的否定声明。若未来任何字段意外变为 true，现有 CI boundary 测试会阻止版本关账。

## 阻断与安全边界

本版首先阻断写操作。Service 标注只读事务，只调用只读上游和纯目录函数；没有 Repository save、
消息发布、文件写入、进程启动、部署或回滚代码。其次阻断秘密输入。入口无请求体与参数，响应明确
声明不读取凭据值、不解析原始端点。再次阻断网络扩张。没有新增 HTTP client、RabbitMQ 调用或托管
审计连接。最后阻断契约漂移。路由字面量由既有唯一所有者提供，Response record 原样移动，历史
fixture 与归档目录完全不动。

可见性也是安全边界。二十五个实现中，大多数 catalog、renderer 和 support 继续保持包私有，只有
Service 与 Response 原本就是公开类型。TestSupport 因留根控制器测试需要从包外构造服务而公开，
且只存在测试源集，不进入生产 jar。Archive 只能看见明确导入的 Service 和 Response。若编译器提示
某个下游需要更多 package-private 类型，正确做法是检查边界设计，而不是把整个包的类都改成 public。

失败条件写得很具体：路由任一字节变化失败；控制器被迁出失败；目标包不是精确二十五个实现失败；
根包高于五百四十八失败；总文件数超过一千三百五十二失败；出现未分类文件失败；SpotBugs 通过删除
排除规则而不是更新 FQN 失败；讲解不足三千汉字或缺少规定章节失败；focused、Spotless、verify 任一
不成功都失败。这样的失败定义把“看起来没问题”变成可复现结论。

## 测试覆盖

本版的测试组合既覆盖迁移自身，也覆盖迁移前后的相邻版本。v1847 守卫原先从根目录读取本家族的
十二个 dossier import；v1848 将读取位置改为新包，但保留“必须依赖 operatorcidossier”的原断言。
这叫历史守卫路径迁移，不是降低期望。v1848 新守卫再检查 Archive 是否只导入新边界。两个版本的
守卫连起来，能证明依赖链是 dossier 到 ciaccept，再到 archive，而不是某层绕过前一层。

Focused 测试还包含根控制器聚合测试和全部六类包内行为测试，Archive 测试则作为下游编译与装配
探针。三处棘轮测试防止只改一个数字；walkthrough compliance 统计汉字数量、中文占比、实际工作量
说明和十个标准标题；Spotless 检查迁移后的 import 排序；SpotBugs 读取新 FQN；JaCoCo 确认代码移动
没有让有效覆盖率下跌。完整 verify 进一步运行项目全部历史测试，捕获文本检索未发现的 Spring 注入、
序列化和架构依赖。

输入与输出证据也会写入版本回执。输入包括起始提交、最新绿色 CI、根包普查和精确文件清单；输出
包括 focused 测试数与耗时、完整测试总数与耗时、JaCoCo 结果、SpotBugs findings、最终普查、提交
哈希、tag 和 GitHub Actions run。实现提交先推送，关账提交记录本地回执，再创建带说明 tag；中间
CI 不空等，下一版开头检查，五版检查点再统一 block-watch 到全绿。

## 一句话总结

v1848 把 ReleaseAcceptance 从根包里的隐式同伴变成有明确输入、明确输出和明确公开面的只读发布验收
模块：它只消费 v1847 dossier，只向 v1849 Archive 暴露 Service 与 Response，路由和响应字节不变，
根包减少二十五个实现，所有结论由编译、精确清单、收缩棘轮和完整质量门共同证明。
