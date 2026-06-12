# 第一千七百七十六版代码讲解：中文长篇讲解合规测试

本版目标是把用户提出的“以后用中文书写、每个版本一篇讲解至少三千字”变成真正会失败的测试。v1774 和 v1775 已经建立了深度 registry 的路由、模型、目录、服务和控制器，但如果合规测试不扫描文件并统计内容，规则仍然只是一个对外展示的声明。v1776 因此修改 `OpsCodeWalkthroughArchiveComplianceTests`，让它对 v1774 之后的代码讲解执行中文长篇门槛。

这一版不是为了“用测试惩罚文档”，而是为了保护维护者。短讲解最大的问题不是字少，而是后续读者无法从中恢复当时的实现意图：入口在哪里、模型为什么这样设计、服务怎么组合目录、测试跑了哪些、边界为什么没有打开。如果每次都要回到 diff 或聊天记录里猜，代码讲解就没有发挥作用。v1776 的测试让这种退化尽早暴露。

## 入口路由

v1776 不新增运行时路由，它的入口是测试入口。主要入口文件是 `OpsCodeWalkthroughArchiveComplianceTests`，这个测试已经负责扫描所有 `代码讲解记录*` 目录下的 Markdown 文件，并检查历史文件是否标准或带 legacy marker、未来文件是否有必需章节、未来文件是否禁止 legacy marker。v1776 在这个测试类里新增一条更严格的规则：`requiresChineseLongformDepthForNewWalkthroughs()`。

这条测试不是通过 controller 暴露给 HTTP，而是通过 Maven 测试生命周期执行。换句话说，它的入口是 `mvn -q "-Dtest=OpsCodeWalkthroughArchiveComplianceTests" test` 或包含它的定向测试命令。这个入口更适合讲解质量，因为讲解文件本身是仓库内容，不需要运行服务才能检查。

为了保持和 v1774/v1775 的 registry 一致，测试中的常量与 registry 的语义相同：`CHINESE_DEPTH_CUTOFF_VERSION = 1773`，表示 v1774 以及之后开始执行；`MINIMUM_CHINESE_CHARACTER_COUNT = 3000`，表示每篇独立讲解至少三千个汉字。测试不会追溯历史遗留，也不会强迫 v1769-v1773 的已发布短讲解回改，这符合版本化证据链。

测试入口还保留原有扫描机制：`walkthroughFiles()` 会从工作根目录列出所有以 `代码讲解记录` 开头的目录，递归查找 `.md` 文件，排除 `README.md`。这意味着新规则会自然覆盖 `代码讲解记录_生产雏形阶段4/v1774-v1778/` 下的五篇讲解，不需要为每个目录手工登记路径。

## 响应模型

v1776 主要改测试，没有新增 response record，但它和 v1775 的响应模型形成互证关系。深度 registry 的响应说 `minimumChineseCharacterCount=3000`，合规测试则真的用 `MINIMUM_CHINESE_CHARACTER_COUNT=3000` 去统计文件。registry 的响应说 `effectiveFromVersion=1774`，合规测试则通过 `CHINESE_DEPTH_CUTOFF_VERSION=1773` 对版本号大于 1773 的文件生效。

测试中的“模型”可以理解为它构造的违规列表。`requiresChineseLongformDepthForNewWalkthroughs()` 创建 `shallowWalkthroughs`，遍历每个讲解文件，读取版本号和文本内容。如果版本号存在且大于 1773，就检查两个条件：汉字数量是否少于 3000，以及中文是否不是主体。失败时把相对路径、汉字数量和文本字母/汉字总量加入列表，最后断言列表为空。

这个违规模型很实用。它不是只说“某个文件不合格”，而是附带 `cjk=` 和 `text=` 两个数值，让维护者能知道到底是篇幅不够还是中文占比不够。比如一个文件有大量英文类名、命令和路径是允许的，但如果中文主体太少，`hasChineseMajority` 会失败；如果中文比例够但汉字数不够，`cjkCharacterCount` 会失败。

测试新增的三个辅助方法也属于模型的一部分：`cjkCharacterCount()` 使用 UnicodeScript.HAN 统计汉字；`letterOrCjkCount()` 统计字母或汉字，用于估算正文语言主体；`hasChineseMajority()` 要求汉字数至少达到字母/汉字总量的一半。这样既允许 Java 类名、Maven 命令、endpoint、profile 等英文技术名存在，也阻止整篇讲解变成英文短说明。

## 上游证据配置

v1776 仍然以 Node v367 为上游边界。Node v367 的计划没有要求 Java 做 runtime feature，所以这版继续保持仓库内部治理。合规测试不读取 Node 文件，也不依赖 Node 运行产物；它只依据 Java 仓库自己的讲解文件和版本号。这样测试可以在任何 CI 环境中稳定运行，不会因为 Node 工作区缺失而失败。

同时，v1776 的直接上游是 v1774/v1775 建立的 depth registry。registry 把门槛写成对外证据，测试把门槛写成执行规则。二者的关系是“声明”和“执行”，缺一不可。只有 registry 没有测试，规则可能被忽略；只有测试没有 registry，规则来历不清。v1776 把这两条线接起来。

本版还尊重历史清算逻辑。已有的遗留标记规则仍然只允许历史文件带旧版清算标识，不允许新版本继续使用这个标记。新增中文长篇规则从 v1774 开始，而不是从 v290 或 v1728 开始，是因为用户这次明确指出“从今以后”要中文和三千字。测试使用版本号门槛把这个时间点固定下来。

需要注意的是，测试不会因为 README 不够三千字而失败。`walkthroughFiles()` 排除了 README，这是合理的：README 是目录导航，不是单版本代码讲解。真正需要三千汉字的是 `version-1774-...md` 这类单版文件。这个边界能防止目录说明被迫写成长文，也保证“每版一篇讲解”才是门槛对象。

## 服务层核心流程

v1776 的服务层核心流程体现在测试扫描流程，而不是 Spring service。第一步，`walkthroughRoots()` 从工作根目录找所有 `代码讲解记录*` 目录。第二步，`walkthroughFiles()` 递归收集所有 Markdown 讲解文件，并排除 README。第三步，`version(path)` 用 `VERSION_TOKEN = Pattern.compile("version-(\\d+)-")` 从文件名提取版本号。第四步，新测试读取文本并执行中文长篇判断。

这条流程的好处是完全基于仓库文件，不需要维护一个手工列表。如果未来新增 `代码讲解记录_生产雏形阶段5/v1800-v1804/`，只要目录名以 `代码讲解记录` 开头，测试就会自动扫描。如果文件名遵循 `version-<java-version>-<scope>.md`，版本号就会被提取并套用 v1774 以后的规则。

新增的 `cjkCharacterCount()` 使用 `text.codePoints()` 而不是 `char` 遍历，避免 Unicode 处理粗糙。它只把 Unicode script 为 HAN 的 code point 计为汉字。这样标点、数字、英文、路径、代码块不会被算进三千汉字门槛。这个选择比简单 `text.length()` 更严格，也更符合“中文书写”的要求。

`hasChineseMajority()` 使用汉字数和字母/汉字总数比较，而不是要求全文所有字符大多是汉字。原因是 Markdown 文件有大量换行、标点、反引号、路径分隔符和命令参数，这些不应该稀释中文比例。只比较 alphabetic 与 HAN，可以更准确地判断讲解主体是不是中文。这个实现让 Java 类名和命令存在，但不能让它们替代解释主体。

这个测试还刻意没有用“文件大小”或“总字符数”作为门槛，因为那样很容易被代码块、日志、路径、英文清单或重复命令撑大。用户要求的是中文讲解，不是 Markdown 字节数。用 Han 字符计数会迫使作者真正写出中文解释，把实现缘由、维护风险、边界判断和验证路径讲清楚。它也让后续审查更公平：技术名可以出现，但不能取代正文。

## Java 证据检查

Java 侧最直接的证据是 `OpsCodeWalkthroughArchiveComplianceTests` 的新增测试方法。它会对 v1774 之后的所有标准讲解生效，因此本批五篇长文自身就是第一批被检查的样本。如果这些讲解少于三千汉字，定向测试会失败；如果它们大段英文，测试也会失败。这个自我约束比事后口头保证更可靠。

第二个证据是常量位置。`CHINESE_DEPTH_CUTOFF_VERSION` 和 `MINIMUM_CHINESE_CHARACTER_COUNT` 放在合规测试类顶部，和 `LEGACY_MARKER_CUTOFF_VERSION`、`LEGACY_MARKER` 相邻。这样后来者打开测试文件就能看到历史 legacy 规则和新中文长篇规则是同一类档案治理门槛，而不是临时脚本。

第三个证据是失败信息。断言描述写明“walkthroughs after v1773 must be Chinese longform explanations with at least 3000 CJK characters”。这句话直接告诉维护者失败原因，而不是让他们从测试名猜。失败列表还附带相对路径和计数，定位成本低。

第四个证据是没有删除旧测试。原有的 `keepsHistoricalWalkthroughsEitherStandardOrMarkedLegacy()`、`rejectsFutureWalkthroughsWithoutRequiredStructure()`、`rejectsLegacyMarkerOnFutureWalkthroughs()` 都保留。v1776 是加严，不是替代。也就是说，新讲解既要有标准章节，又要中文长篇，还不能用 legacy marker 逃避。

第五个证据是测试的门槛从文件名版本号触发，而不是从目录名触发。这样即使将来 v1774 之后的讲解被放进新的续写目录，只要文件名保持 `version-<版本>-<范围>.md`，规则仍然会生效。这个设计比硬编码 `v1774-v1778` 目录更稳，也符合项目长期分段归档的习惯。

## mini-kv 证据检查

本版不消费 mini-kv 证据，也不启动 mini-kv。测试只读本地 Markdown 文件，与 mini-kv 的健康检查、统计命令、写命令、压缩恢复、数据权威状态都没有关系。把 mini-kv 排除在外，可以让合规测试在任意开发环境和 CI 环境中运行。

这并不意味着 mini-kv 在整个项目里不重要。mini-kv 是 Node 真实只读 gate 的上游之一，后续如果 Node v368 或 v369 要求新的 Java/mini-kv 对齐，应该单独开版本。但本版处理的是“讲解如何写”，它不应该要求上游服务存在。

讲解里仍然要写 mini-kv 不消费，是因为用户要求“讲解部分详细”。详细不是把所有系统都拉进来，而是把不参与的系统讲明原因：没有读取窗口、没有命令调用、没有端口依赖、没有状态变更。本版这样写，就是为了给后来者一个清晰判断，不让他们误以为测试漏掉了 mini-kv。

`startsMiniKvService=false` 仍由 depth registry 体现，虽然合规测试本身不访问 response。两个证据面互相补充：registry 说本批无 mini-kv autostart，测试通过纯文件扫描落实规则。最终汇报也要说明没有留下 mini-kv 进程。

## 阻断与安全边界

v1776 的安全边界集中在“测试只读文件，不执行外部动作”。它不会启动 Java 应用，不会打开端口，不会访问网络，不会查 credential，不会解析 endpoint，也不会修改讲解文件。它只是读取 Markdown 文本并计算字符数量。即使测试失败，也只是让 Maven 失败，不会自动重写文档或提交修复。

write routing 和 active shard router 与本版无关，仍然保持关闭。合规测试在 `src/test/java` 下运行，不接触订单服务、数据库写入、outbox、replay、approval ledger 或 shard router。这样讲解质量门禁不会污染业务路径。

credential 和 raw endpoint 边界也保持关闭。测试只处理仓库中的 Markdown 内容，如果讲解里写了“credential value”这样的英文词，它只会被当作普通文本统计，不会触发任何解析或读取。它也不会访问环境变量、secret provider、resolver client 或 managed audit endpoint。

部署和回滚边界同样关闭。测试失败时需要开发者修正文档或实现，不会自动部署、回滚、生成 release，也不会启动 Docker。v1776 的价值在于把错误提前暴露在 CI，而不是在发布环节临时补救。

## 测试覆盖

本版本身新增的核心测试就是 `requiresChineseLongformDepthForNewWalkthroughs()`。它需要和旧的 archive compliance tests 一起运行，才能同时覆盖结构、legacy marker、中文和篇幅。定向命令可以使用 `mvn -q "-Dtest=OpsCodeWalkthroughArchiveComplianceTests,OpsShardReadinessCodeWalkthroughDepth*Tests" test`。

v1776 还需要通过 v1775 的 depth registry tests，因为服务响应中的 `minimumChineseCharacterCount=3000` 必须和合规测试的实际门槛一致。如果有人只改测试不改 registry，或者只改 registry 不改测试，相关断言会暴露不一致。

整批完成后必须跑全量 Maven。原因是 `OpsCodeWalkthroughArchiveComplianceTests` 扫描路径较广，虽然改动集中在文档目录，但任何历史文件命名或编码问题都可能被触发。全量测试还会确保新增测试类、controller、service 没有影响 Spring 编译和包扫描。

CI 也必须等待终态。上一次本地全量测试曾出现过 10 分钟窗口超时但重跑成功的情况，因此本批最终汇报要区分“超时重跑”和“失败修复”。如果 CI 失败，需要拉日志定位；不能只说本地过了。

## 实际工作量说明

本版的实际工作量是把讲解要求推进到本项目测试门禁里，而不是只在规范文档里写一句提醒。用户要求中文书写、每版一篇、至少三千字、禁止硬凑，这些要求如果没有测试，就会在后续快速推进中退化成口头约定。v1776 通过归档合规测试扫描所有讲解目录，对 v1774 之后的新文件增加汉字数量、中文主体和实际工作量说明检查。这样以后提交新讲解时，本地测试和远端持续集成都能直接发现不合格文件。

禁止硬凑在本版里不是抽象口号。测试新增了实际工作量章节要求，要求新讲解必须出现“本项目”和“禁止硬凑”等信号，目的是迫使作者说明这一版到底改了哪些本项目代码、测试、文档或验证流程。这个检查不能完全替代人工判断，但可以挡住最粗糙的凑字行为：只有长段空话、没有项目上下文、没有说明实际工程面，就无法通过归档门禁。更重要的是，测试失败会把具体文件名列出来，维护者能马上回到对应版本补足真实说明。

本项目采用这个测试还有一个实际收益：历史文件不被突然要求全部重写。已有讲解数量很大，很多早期文件处在旧规则下。如果一次性要求所有历史文件达到新三千字标准，就会把当前功能推进变成无边界的文档返工。v1776 通过版本门槛把责任切清楚：历史按照旧清算规则处理，新版本按照中文长篇规则处理。这样既回应用户对以后质量的要求，也避免破坏既有归档。

这一版没有增加运行时功能，但工程价值很直接。它让写作规范有了执行者，让 registry 有了外部验证，让每个未来版本的讲解都成为提交质量的一部分。以后如果某个版本实际工作量不足以写出三千字，测试会迫使我们继续做本项目的源码拆分、测试补强、文档索引或边界证明，而不是靠重复句子混过门槛。

## 一句话总结

v1776 把 v1774 之后的 Java 代码讲解中文三千字要求接入仓库合规测试，用可失败的扫描规则替代口头承诺，同时保持所有运行时、密钥、端点、部署和上游启动边界关闭。
