# version-1840：ReleaseAcceptanceRoutePathSplit 基础层、收口层与双所有者校验

v1840 开始处理 endgame census 中 78 个可移动文件的 ReleaseAcceptanceRoutePathSplit 大 family，但没有把全部文件塞进一个版本。第一刀只迁基础 registry、closeout registry 和共享 route owner，共二十四个生产文件；sustainment 与 acceptance-package 留给后两版。本项目借此从“一个超大根包中的长前缀类群”转成“按依赖方向逐层移动”的结构，同时保留路由双所有者比对这一核心证据。全文禁止硬凑，每个结论都能回到源码、测试或 census 复现。

## 实际工作量说明

生产侧移动十七个 base 文件、六个 closeout 文件和一个 `OpsShardReadinessReleaseAcceptanceRoutePaths`。base 包含 source、route、compatibility、boundary、consumer、scorecard 六组 catalog 及对应 renderer，共享 renderer support、总 renderer、response、support 与 service。closeout 包含 item catalog、boundary catalog、renderer、response、support 和 service。测试侧移动九个 catalog、immutability、renderer、markdown 与 test-support 文件，两个 controller 及 controller 测试仍在 root。

root 生产文件从 775 降到 751，可移动积压从 670 降到 646；RoutePathSplit bucket 从 78 降到 55，单独的 root route-owner bucket 从 1 归零，总 `ops` 文件数不增长。除了移动，还处理了二十多个 root 读者的显式 import、八个 SpotBugs response 镜像、跨包 test support、全局 route aggregator 的窄 public 化、精确 ratchet、抽取说明与汉字深度门。工作量不在复制文件，而在保持每条跨层证据仍可编译、可追踪、可失败。

## 入口路由

本版本直接涉及两条 GET：`release-acceptance-route-path-split-registry` 和 `release-acceptance-route-path-split-closeout-registry`。两个 controller 继续位于 root，继续使用 moved route owner 的 `BASE_PATH` 与对应后缀。Spring 映射得到的完整字符串不变，controller 只导入迁出的 service、response 和 route owner。remaining sustainment、acceptance package、closeout receipt 与 archive index controller 也显式导入同一个 owner，下一版迁移时不会再依赖同包偶然可见性。

这个 route owner 不只保存两条本版路径，还保存 MinimalReadOnlyGate execution、operator CI handoff、archive digest、release acceptance archive handoff 以及后三层路径。它本来就是这组 split 常量的候选所有者，因此整文件迁移比复制两条后缀更诚实。所有字段只从 package-private 变为 public immutable string，不增加 setter、动态路由表或配置写入口。根 `OpsShardReadinessRoutePaths` 继续保留，作为历史稳定值的聚合者。

## 响应模型

base response 由 source snapshot、route path entry、compatibility check、boundary guard、consumer handoff、scorecard entry 和 markdown section 组成。每个 route entry 同时保存 symbol、candidate path、stable path、split path、两个 owner 名称以及 matched/status。它的价值不是返回一串路由，而是把“旧聚合器值”和“新 split owner 值”并列呈现，让迁移是否字节一致成为数据而非口头保证。

closeout response 引用 base response 的版本、endpoint 与通过状态，再增加 closeout item、boundary assertion、markdown section、计数和最终 status。它回答“这次 split 是否具备继续维护的交接条件”，而不是修改路由。两个 response 都继续复制集合，嵌套 record 顺序与 JSON 字段没有变化。SpotBugs 中 `EI_EXPOSE_REP` 与 `EI_EXPOSE_REP2` 对顶层 response 和 MarkdownSection 的八个匹配全部迁到新 FQN，旧 FQN 必须为零。

## 上游证据配置

base service 的上游是 `ReleaseAcceptanceArchiveVerificationHandoffService`。它读取 archive verification handoff response，再由 SourceCatalog 摘取来源计划、归档摘要、验证要求、交接字段和通过状态。这里仍然只是 Java 内部的只读 service 调用，没有访问真实对象存储、外部审计系统或生产 archive。上游 service/response 留在 root，本版只给迁出文件添加明确 import，不把它们一起混入当前主题。

route catalog 则有一组特殊上游：保留的全局 `OpsShardReadinessRoutePaths`。它对十一项 symbol 分别读取 stable value 与 moved owner value，交给 `entry()` 做 `stablePath.equals(splitPath)`。这是一条真正需要跨包访问的合同，因此全局 aggregator 类、BASE_PATH 和十一项比较常量被窄 public 化。其他上千行字段仍是 package-private，不能因为一次迁移把全部根路由变成公共 API。

## 服务层核心流程

base `registry()` 在只读事务中先调用 archive handoff source service，构造 source snapshots；随后列出十一条 route path entry，计算 compatibility checks，加载 boundary guards 与 consumer handoffs；scorecard catalog 综合各组 passed 数量，renderer 把六组数据转成 markdown sections；support 复制所有集合、统计通过项、追加固定检查，最终返回 response。每一步都是纯读取和组合，没有 repository save、消息发送或网络写入。

closeout `closeout()` 调用 base service 取得刚才的 response，从中生成 closeout items 与 boundary assertions，再通过 renderer/support 返回 closeout response。closeout 和 base 同版迁移，原因正是这条直接依赖：如果只迁 base，closeout 留在 root 就要跨包访问；如果只迁 closeout，它仍依赖 root base。两者作为一个 23 文件实现层共同移动，减少临时 public 面，下一层 sustainment 只需要消费稳定的 public closeout service/response。

## Java 证据检查

编译器首先验证三类边。第一类是 retained root controller 到 moved service/response/owner；第二类是 moved base 到 root archive handoff 和 public global aggregator；第三类是仍在 root 的 sustainment 到 moved closeout service/response/owner。测试编译还验证 moved BaseTestSupport 能调用公开的 archive handoff test support、root controller tests 能调用 moved test support、root SustainmentTestSupport 能调用 moved CloseoutTestSupport。只在确有跨包消费者时公开 test helper。

行为测试继续检查 route catalog 十一项、stable/split 完全匹配、compatibility 全部 passed、boundary 不开放写路由、consumer handoff 只允许读取、scorecard 计数、response 集合不可变、renderer section 顺序和 controller 返回。结构测试精确检查二十四个文件新位置与旧位置缺失、两 controller 留根、route owner/public aggregator、sustainment import、SpotBugs 新旧 FQN、751 与 1352 ratchet、census 55/0 bucket 和中文讲解。

## mini-kv 证据检查

路由 split 名称容易让人误以为它会改 mini-kv 分片，本版本恰恰证明相反。RoutePathEntry 只是比较 Java HTTP suffix；BoundaryGuard 和 consumer handoff 明确说明没有 active shard router、没有 KV 写入、没有 endpoint credential，也没有 sibling mutation。没有启动 `minikv_cli`，没有发送 RESP 命令，没有读取 C++ WAL 或 snapshot，更没有修改 mini-kv 仓库。

Java 全量 verify 只能证明 Java 内部的旧值与新 owner 值一致。它不能证明 Node 已经实时读取 Java，也不能证明真实 mini-kv 对新的请求作出响应。按照共享计划，集成 capstone 才会做真实 jar 与 CLI 联合测试。在此之前，v1840 的成熟度仍是单项目验证加跨项目合同对齐。把这个限制写清楚，可以防止一个漂亮的 route scorecard 被误当成部署授权。

## 阻断与安全边界

base response 的 matched/passed 只表示字符串一致；closeout passed 只表示迁移证据齐备。它们不授予 write routing、active shard selection、credential resolution、raw endpoint access、managed audit connection、deployment、rollback 或 SQL execution。所有 service 保持 `@Transactional(readOnly = true)`，controller 只有 GET，route owner 只有 final string，响应中不存在可执行 callback 或命令。

失败条件具体可测：十一项任一路径不同则 compatibility blocked；route owner 回到 root 则 census bucket 失败；迁移后 root 超过 751 则精确 pin 失败；总文件超过 1352 则复制迁移失败；公开整个 global aggregator 字段面则 v1840 source guard 失败；旧 response FQN 残留则 SpotBugs 配置守卫失败；为了过门提高行数或 root 上限则违反 ratchet；改变 fixture 或 response 字段则整刀回退。

## 测试覆盖

包内 catalog、compatibility、immutability、renderer 与 markdown 测试随实现移动，能继续访问 package-private helper。controller 测试留在 root，从真实跨包视角使用 public TestSupport 和 response，避免测试因同包权限掩盖生产装配问题。archive handoff test support 只公开 service/registry 构造，CloseoutTestSupport 只公开后续 sustainment 所需方法；这是一条受消费者驱动的测试边界，不是把所有 helper 公开。

全量 `mvnw verify` 必须在 walkthrough 与文档最终完成后运行，覆盖全部 JUnit、Spring integration、JaCoCo floor、SpotBugs 0 和 Spotless。v1839 的精确 775 守卫改成历史上限，当前全局 pin 收紧为 751；v1840 新守卫钉住 751。这样旧版本仍防止自己迁出的文件回流，新版本负责今天的精确现实，不通过改历史数字伪造进度。

### 双所有者为什么不是重复事实

乍看 global aggregator 和 moved owner 都保存同一批字符串，似乎违反单一事实源。区别在于 moved owner 是候选事实源，global aggregator 是兼容聚合面；后者的字段已经被大量历史 controller 和测试引用，不能在一个版本删除。v1840 让 global 字段委托 moved owner，同时 RouteCatalog 仍读取两个公开面并比较。值的字面量只有 moved owner 一份，引用面暂时有两个；这叫兼容委托，不是复制字符串。

未来若所有消费者都迁到 family owner，可以在独立版本减少 global aliases；在此之前，RouteCatalog 明确记录两个 owner 名称和 matched 结果。维护者看到的是“兼容面与事实源一致”，不是两份不可追踪常量。若有人在 global 侧重新写字面量或在 owner 侧改后缀，compatibility 测试立即失败。这个过渡设计把迁移风险变成可观察状态。

### 分层迁移的维护收益

若 v1840 一次移动 78 个文件，base、closeout、sustainment、acceptance package、receipt 和 archive index 的错误会混在同一编译批次，讲解也只能罗列文件。现在 base+closeout 建立稳定 package；v1841 只处理消费 closeout 的 sustainment；v1842 只处理消费 sustainment 的 acceptance package 及两个子收口。每版都有单向输入、明确输出和可预测 root 降幅。

评审者最强的质疑是“为什么把 global aggregator 设为 public”。答案不是为了省 import，而是 RouteCatalog 的业务就是从不同 package 比较 stable owner 与 split owner。只公开类、BASE_PATH 和十一项不可变比较字段，并用守卫禁止扩散，风险小于复制 stable 字符串或保留 family catalog 在 root。若未来 RouteCatalog 不再需要双面比较，这些字段可在单独版本收回。

### 十一项路由逐类理解

十一项比较不是随意抽样。前两项覆盖最小只读门执行目录及其归档验证，确认最基础的执行前证据入口没有漂移；接着七项沿 operator CI handoff、archive verification、archive digest、consumer package、verification dossier、release acceptance 到 release acceptance archive 逐层展开，确认一条长证据链的每个节点仍可由历史聚合面访问；第十项是 archive verification handoff，本版 base service 正是从这里取输入；第十一项是 route-path-split registry 自己，验证迁移工具不会漏掉自身入口。

每一项 entry 都保存 symbol，而不是只保存下标。stable owner 和 split owner 的类名也进入 response，维护者能在报告里直接看出哪一侧是谁。`matched` 来自严格字符串相等，不使用尾缀匹配、忽略大小写或路径正规化；因为对 HTTP 合同而言，多一个斜杠、少一个单词都可能让消费者访问不同地址。status 只由 matched 决定，避免 renderer 用展示逻辑改写判断。

### 从输入到输出的透明示例

以 archive verification handoff 为例，base service 先调用上游 registry 得到包含归档摘要、验证要求、artifact cross-check、route handoff、operator instruction、CI proof、retention guard 和 closeout handoff 的响应。SourceCatalog 不把整个对象原样塞入结果，而是提取少量来源快照；RouteCatalog 同时验证该上游入口在两个 owner 中相同；CompatibilityCatalog 把 matched 转成兼容检查；BoundaryCatalog 追加“不写路由、不触发执行”等保护；ScorecardCatalog 统计所有组是否通过。

Renderer 随后按固定顺序生成来源、路由、兼容、边界、消费和评分六个 section。Support 再复制原始结构化列表与 markdown 列表，计算通过数并形成最终 response。调用者既能机器读取结构化字段，也能给维护者展示文字，但两者来自同一批输入。closeout service 则把 base response 转成“已迁项目”和“边界断言”，再次保留来源版本与 endpoint。整个过程没有隐藏状态，任何一步都能在对应 catalog 测试中单独复现。

### 测试支持为何也要分边界

生产代码跨包后，若测试仍全部留在 root，它们会失去访问 package-private catalog 的能力；若为了省事把所有生产 helper 设为 public，又会污染真正 API。因此 catalog、renderer、immutability 测试跟实现移动，controller 测试留在入口侧。迁出的 BaseTestSupport 需要构造 root 的 archive handoff service，于是只把上游 TestSupport 的 service/registry 方法公开；仍在 root 的 SustainmentTestSupport 需要 closeout service，于是 CloseoutTestSupport 只公开这两个构造结果。

这些 public 仅存在于 test source，不进入生产 jar。v1841 迁走 sustainment 后，可以再次检查哪些测试 helper 仍需跨包；v1842 完成 acceptance 层后，若没有外部消费者，公开范围还可收回。测试可见性也采用消费者驱动和 shrink-only 思路，而不是一次永久放开。编译器在这里不仅找错误，也帮助识别真实测试架构边界。

### 失败后的定位顺序

若 compatibility 测试失败，先打印具体 symbol 的 stable/split 值，检查 moved owner 字面量和 global 委托，不要改成宽松匹配；若 controller 测试编译失败，检查 service、response、owner 的 import 与 public 可见性；若 sustainment 编译失败，检查 closeout service/response 边界，不要提前迁移下一层；若 SpotBugs 失败，检查顶层 response 与 MarkdownSection 的两类镜像；若 census 报 752 或 unassigned，按脚本输出定位遗留或新增 root 文件。

若维护预算失败，则用仓库规定的 StreamReader 计数确认真实行数，优先合并重复委托或删除无语义空行，不提高阈值。若全量 Spring 测试出现 JSON 差异，比较 response record 参数与 renderer section 顺序，不修改 fixture 迎合新输出。这个排查顺序把路径合同、依赖装配、静态分析、结构预算和响应兼容分开，能快速找到责任层。

### 为什么这一刀有独立价值

二十四个生产文件的下降只是表面结果。更深的价值是 route owner 真正离开 root、global aggregator 与 family owner 的关系从隐式同包访问变为显式兼容委托、closeout 获得稳定输入、后续 sustainment 只依赖窄 public 边界。census 同时把 owner bucket 清零和 family bucket 减到 55，说明计划合同不仅“文件少了”，还知道剩下哪一层。

对抗性地看，如果撤掉 v1840 新增的 route comparison、public 范围守卫和 exact census，只留下 package move，未来维护者确实难以判断价值。现在这三类机械证据分别回答行为是否相同、暴露是否克制、进度是否真实。版本可以被外部评审独立复跑，而不必相信执行者的说明。

### 基础层各职责的中文解剖

来源目录负责把上游归档交接响应压缩成可审阅快照。它保留来源计划、归档版本、摘要、验证状态与交接状态，却不把上游所有嵌套对象再次复制一遍。这样输出仍能回答“本次路由拆分依据哪份材料”，又不会让基础层变成另一个归档响应镜像。来源渲染器只负责把快照转成人类可读行，不参与通过判定。

路由目录负责十一项稳定值与候选值配对。它是本 family 最关键的事实检查点：输入是符号名和两个 owner 的字符串，输出是包含匹配布尔值的条目。路由渲染器只展示符号、稳定路径、候选路径与结果。若展示文本出现差异但结构化值相同，应修渲染；若结构化值不同，应修所有权或回退路径，不能混为一类故障。

兼容目录读取路由条目并形成兼容检查，确保“新所有者可替代旧聚合面”的判断独立于原始列表。兼容渲染器展示每一项的结果与原因。这个中间层看似重复，实则把纯字符串相等转成维护语义：后续评分只消费兼容结果，不必再次知道两个路径字段放在哪里，也不会在多个地方复制相等算法。

边界目录列出不允许因路由拆分而打开的能力，包括写路由、主动分片选择、凭据读取、真实执行和 sibling 控制。边界渲染器把这些否定条件明确展示。它让“所有路径都 matched”不能单独决定整体 ready；即使字符串完全一致，只要某个边界被改成允许，评分仍应阻断。这避免把兼容性和权限混成一个绿色灯。

消费交接目录描述下游可以读取哪些证据、应该核对哪些字段以及不能据此执行什么。它面向的是消费责任，而不是服务调用本身。消费渲染器把交接内容按固定顺序输出，使 Node 或维护者能够看到阅读步骤。这里没有远程调用代码，只有消费合同，因此不会因文案中出现下游名称就获得跨进程能力。

评分目录汇总来源、路由、兼容、边界和消费五组结果，形成可重算的条目。它不读取 controller，也不信任 renderer 文本，而是基于结构化列表统计通过数量。评分渲染器只呈现总数和各组结果。把评分放在独立目录的好处是：任何一组规则新增时，测试可以明确要求总数变化，避免 support 中藏着难以发现的魔法数字。

总渲染器按来源、路由、兼容、边界、消费、评分的顺序组装章节。共享渲染支持负责标题、行列表与不可变复制。总支持类负责复制所有结构化列表、追加检查项、计算整体状态并构造响应。服务只负责调用这些组件。职责沿“采集、判断、展示、封装、编排”分开后，每个文件都能在几十到一百余行内保持单一目的，没有形成新的巨型类。

### 收口层各职责的中文解剖

收口条目目录从基础响应中提取必须完成的维护动作，例如确认 owner 已转移、兼容检查已通过、边界仍关闭、测试与文档可复现。它不重新计算十一条路径，而是引用基础层的结构化结论。这样基础判断只有一处，收口层负责“是否具备交接条件”。若基础状态阻断，收口条目应跟随阻断，而不是绕过来源重新给出通过。

收口边界目录把关键否定条件再次表达为断言，目的不是复制边界清单，而是证明交接完成后仍没有执行权限。基础层的边界回答“迁移过程中不应发生什么”，收口层回答“交付给下一维护者时仍保证什么”。两者处于不同时间视角，测试分别校验，可以发现有人只在中途保持安全、却在最终响应中错误开放能力。

收口渲染器把条目与断言形成两组章节；收口支持类复制列表、统计通过数、保留基础版本和 endpoint，构造最终状态。收口服务只读调用基础服务，再调用两个目录、渲染器和支持类。它没有直接依赖上游 archive handoff，因为来源已经由基础响应封装；这正是两层职责边界，也是后续持续维护层只需依赖收口 service/response 的原因。

### 三种备选方案为何没有采用

第一种备选是把全部七十八个实现一次迁进同一包。优点是少做两次 import，缺点是编译错误范围巨大、测试支持边界混乱、单版讲解难以说明输入输出，且任何失败都会让整批无法归因。计划书要求一个 family 或一个内聚 cluster；本次选择的是基础与收口这一条直接依赖链，工作量足够但边界仍清晰。

第二种备选是只移动文件，不移动 route owner，让新包继续访问 root 的 package-private owner。Java 不允许这种访问；把 owner 留 root 并改成 public 又违反 census 明确的“route owner 不豁免”。复制一个新 owner 更糟，会产生两份字面量。因此整 owner 迁移、global 委托、窄字段 public 化是同时满足编译、census 与单一字面量的方案。

第三种备选是删除 global aliases，让所有 controller 一次改用新 owner。技术上可行，但影响 MinimalReadOnlyGate、archive handoff、后三层 controller 和大量历史测试，会把兼容清理混入当前抽取。保留 aliases 并机械比较，能先证明新 owner 正确；等所有消费方迁稳后，再用独立版本决定是否缩减 aliases。分阶段不是拖延，而是让每次行为面可验证。

### 评审者可复现的检查矩阵

要复核文件所有权，运行 census 并检查根计数、剩余计数、拆分 bucket 与 owner bucket；要复核路由字节，运行兼容测试并抽查十一项稳定值和候选值；要复核入口，运行两个 controller 测试；要复核集合安全，运行两个 response 的不可变测试；要复核展示顺序，运行 renderer 与 markdown 测试；要复核静态分析，检查八个新 FQN 与零旧 FQN。

要复核 public 面，读取 global aggregator，确认只有基础路径和十一项比较字段因本版公开，其他字段仍保持包内；读取 moved owner，确认只有 final string，没有可变容器；读取 service，确认只读事务和无 repository；读取 controller，确认只有 GET；读取测试支持，确认 public 只存在测试源码且方法只构造已知 service。每项都有明确文件和失败现象。

要复核跨项目诚实性，搜索本版 diff，确认没有 Node、mini-kv、archive fixture 或证据 JSON 变更；检查响应边界仍禁止执行；检查讲解没有声称真实联合运行。最后用全量验证证明这些文档和守卫与最终树同时存在。完成这套矩阵后，评审者可以独立判断本刀，而不是依赖版本标题或执行者自述。

### 维护值班场景

设想值班人员发现下游报告里某条归档交接路径变成阻断。第一步不是重启服务，也不是手工改报告，而是查看结构化条目的符号、稳定值、候选值和 owner。若两个字符串不同，就沿对应常量回到 moved owner 与 global 委托；若字符串相同而兼容项阻断，就检查兼容目录的转换；若兼容通过而总评分阻断，则继续查看来源、边界或消费组。每一步都有确定输入，避免在长类名之间盲查。

若问题来自来源归档响应，基础服务会在来源快照或评分中体现，而路由 matched 仍可保持通过。此时不能为了让总状态变绿而跳过来源组，因为 route split 的可接受性不仅要求字符串一致，还要求来源证据完整。值班人员应修复或补齐上游只读证据，再重新生成响应；不能伪造归档摘要，不能把缺失来源写成默认通过，也不能把 closeout 当成真实执行记录。

若所有 Java 门都通过但 Node 展示异常，应把 Java response 与 Node 消费解析分开排查。先用本仓库 controller 测试和新鲜 HTTP 响应确认 Java 输出，再在 Node 侧核对消费合同；仍不能据此操作 mini-kv 或生产环境。这个场景再次说明本版输出是诊断证据，不是运维命令。清晰的责任边界让值班处理更快，也减少“为了恢复绿色而越权”的风险。

值班结论必须记录所依据的路径条目、来源快照、边界断言和验证命令，不能只写“已经恢复”。若尚未执行真实联合检查，应明确标注只完成单仓验证。若发现合同之外的问题，应另开独立修复版本，不把临时改动混进当前抽取。这样交接记录本身也能被下一位维护者复核，避免同一问题反复从头判断。

所有判断都应先保留原始证据，再说明推导过程和未覆盖范围；看不到证据时宁可保持阻断，也不以经验猜测替代检查。

最终结论还要由外部评审复现，执行者不能以自述替代验收。

## 一句话总结

v1840 以双所有者字节比对为机械核心，把 ReleaseAcceptanceRoutePathSplit 的基础与 closeout 实现及共享 owner 迁出 root，在所有只读与无执行边界不变的情况下将根包从 775 收紧到 751，并为 sustainment 两层留下稳定、窄而可验证的输入。
