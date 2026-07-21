# v1885 Code Walkthrough 报告渲染收敛代码讲解

## 入口路由

v1885 处理的是四个已经存在的只读登记入口：合规登记、讲解深度登记、质量门登记和质量审计登记。它们共同回答“当前 Java 工程如何要求、检查并审计代码讲解”，但每个入口保存的证据维度不同。合规入口关心版本谱系、标准章节、归档范围、文档规则、运行时边界和测试覆盖；深度入口关心中文篇幅、语言要求、证据密度、禁止动作和验证步骤；质量门入口关心版本粒度、解释评分、证据锚点、评审清单和运行时边界；质量审计入口则把批次、版本、评分、发现、边界审计和验证步骤汇成可追踪报告。

本版没有新增 endpoint，也没有改变请求方式、路由字符串、Controller、Service 的 Spring 事务或任何公共 Response record。外部调用者仍然进入原来的四条只读路由，Controller 仍调用原 Service，Service 仍返回同一类型的响应。改动发生在 Service 组装完领域记录以后：原先它调用一个带完整领域长名的 renderer，现在改为调用本包内的 `ReportRenderer.render`。因此路由是稳定的门面，渲染器只是门面背后的可替换实现细节。

可以把一次请求理解成一条透明管线：HTTP GET 进入原 Controller，Controller 调用只读 Registry Service，Service 从各个 Catalog 取得规则记录，计算原有状态，再把记录交给包内 `ReportRenderer` 生成 Markdown sections，最后构造原 Response 返回。输入仍是各领域 Catalog 的不可变列表，输出仍是原 Response 中的 `MarkdownSection` 列表。四个入口没有互相调用，也没有因共享渲染机制而合并成一个万能入口。

入口层的关键约束是“公共契约不为内部复用让路”。四个 Response 各自拥有嵌套记录类型，字段名称和 JSON 形状属于已经发布的读取契约。若为了让 renderer 更短而把这些 Response 改成实现一个公共接口，调用方会被迫感知内部抽象，后续重构也会变成 API 迁移。因此 v1885 只复用领域中立的 Markdown section 工厂，领域记录到文本行的映射继续留在各自包内。

## 响应模型

四个响应模型合计输出二十二个 Markdown section 和一百六十八行正文。Compliance 输出六节，逐节正文行数为 21、10、6、8、9、9，共六十三行；Depth 输出五节，行数为 5、4、5、8、5，共二十七行；Quality Gate 输出五节，行数为 7、9、7、7、9，共三十九行；Quality Audit 输出六节，行数为 3、7、9、5、9、6，同样共三十九行。这些数字不是估算值，而是旧实现运行后由测试逐节测得并固化的事实。

`MarkdownSection` 的具体 record 仍由四个 Response 分别定义。共享引擎只需要一个标题、一组已经映射好的字符串行，以及一个领域提供的构造函数。`MarkdownSections.counted` 先把 `countKey=count` 放在正文首行，再追加每个领域条目映射出的行；`MarkdownSections.mapped` 只保留映射结果，不增加计数前缀。Compliance、Quality Gate 和 Quality Audit 的旧输出带计数行，所以使用 `counted`；Depth 的旧输出本来就是以 `- ` 开头的规则行，所以使用 `mapped`。选择由旧契约决定，不是为了统一而强迫四份报告采用相同文本。

四个 `ReportRenderer` 只负责两件事：声明 section 的标题和顺序，把本领域 record 转成原格式字符串。例如 Compliance 的版本行仍按“Java 版本、tag、focus、status”连接，Quality Gate 的检查项仍保留 `blocks-release` 和 reviewer question，Quality Audit 的版本审计仍包含 surfaces、evidence-points、tests、medium 和 status。共享引擎既不知道这些字段，也没有反射读取 record；编译器会在字段改名或类型变化时直接阻断映射。

输出列表和每节 lines 都由共享引擎通过 `List.copyOf` 或不可变收集方式持有，调用者不能在返回后修改内部列表。原有 immutability 测试继续覆盖这个行为。空列表时 `counted` 仍产生一条零计数行，`mapped` 则产生空正文列表；v1885 没有用过滤或默认值偷偷抹掉空证据。响应模型的“标题、行内容、行顺序、计数前缀、不可变性”因此都保持原样。

## 上游证据配置

四个 Registry Service 的上游仍是各自已有的 Catalog，而不是新增配置文件或数据库表。Catalog 中的 record 是报告的事实输入：版本登记保存 tag 和焦点，required heading 保存顺序与意图，depth rule 保存最低中文字符数，evidence rule 保存最少证据点，quality rubric 保存必须解释的内容，audit finding 保存严重级别和阻断动作。Service 先读取这些结构化记录，再同时用于状态判断和 Markdown 展示。

这种输入方式比直接把大段 Markdown 写在 renderer 中更可靠。业务字段仍可由编译器检查，测试可以直接断言 record，renderer 只负责可读投影。v1885 没有把 Catalog 数据搬到共享引擎，因为“有哪些规则”属于领域，“怎样把一组条目安全变成 section”才是可复用行为。数据与行为的分界写进 `docs/ops/code-walkthrough-renderers-v1885.md` 的 Family design，并由结构测试读取，后续若有人把版本名或评审规则硬编码进共享引擎，门禁会立即失败。

上游列表的原始顺序就是输出顺序。`counted` 和 `mapped` 都按传入列表的 encounter order 映射，不排序、不去重、不按字符串键重新分组。每个 `ReportRenderer.render` 又使用显式 `List.of` 声明 section 顺序，所以维护者从一个文件即可看见最终报告的章节骨架。旧实现中散落在手工 `new MarkdownSection` 与局部列表构造里的顺序，现在变成了窄而直接的声明。

这里没有引入新的环境变量、外部 endpoint、credential 或运行时连接。所有输入都是 Java 进程内已经存在的只读证据记录。若未来 Catalog 的来源改为配置文件或数据库，那是另一条数据所有权变更，必须独立建立契约与安全门；本版只对“相同输入如何形成完全相同输出”负责，不把未来假设混入当前抽象。

## 服务层核心流程

旧实现由四个长名称 renderer 独立完成同一种工作：为每个 section 建立标题，手工创建行列表，把 Catalog record 逐个拼成字符串，再构造 Response 的 `MarkdownSection`。四个文件合计五百四十一行，其中大量代码不是领域规则，而是重复的列表创建、计数首行、不可变化和 section 构造。维护者若修复一个计数 section 的边界，必须人工检查另外三份是否有同样问题。

新流程复用 v1873 已经验证过的 `MarkdownSections`，没有新建第二套 section engine。三个有计数报告调用 `counted(title, countKey, items, lineMapper, sectionFactory)`，Depth 调用 `mapped(title, items, lineMapper, sectionFactory)`。泛型参数由编译器从领域列表、行映射方法引用和 `MarkdownSection::new` 推导。共享层负责稳定的算法骨架，包内 `ReportRenderer` 负责标题、count key 与领域行格式，两者之间没有继承树、注册表或字符串类型开关。

以 Quality Gate 为例，Service 把五组 Catalog record 传给 `ReportRenderer.render`；renderer 依次声明 Version Granularity Rules、Explanation Rubric、Evidence Anchors、Review Checklist 和 Runtime Boundary Rules。每组条目由一个短小的私有方法映射成文本，`counted` 加入原有计数行并复制结果，最终五个 section 按声明顺序返回。其他三个家族走同样管线，但各自的字段映射互不泄漏。

格式化后的四个新 renderer 分别为 119、87、141、111 行，合计四百五十八行，较旧实现净减八十三行。减少的正是重复算法，不是删除领域字段或把四份逻辑塞进一个大文件。最大的 Quality Audit renderer 仍只有一百四十一行，远低于三百行的组合器自审线；每个类都能在一屏到两屏内读懂自己的 section 清单和所有行格式。

Service 到 renderer 的依赖方向也更清晰：领域 Service 依赖本包短适配器，本包适配器依赖 `ops.maintenance.rendering.MarkdownSections`，共享引擎不反向依赖任何 walkthrough 包。`WalkthroughRenderingStructureTests` 逐个读取四个目录，要求恰好存在一个 `ReportRenderer.java`，Service 必须调用它，renderer 必须导入共享引擎，并禁止重新出现 `new ArrayList` 或直接手工 `new MarkdownSection`。这让架构意图成为会失败的机械门。

## Java 证据检查

本版严格按“先冻结旧实现，再替换生产代码”的顺序执行。四个 renderer 测试先在 v1884 代码上运行，记录每份报告的 headings、逐节行数和完整 UTF-8 SHA-256。规范化输入不是随意序列化对象，而是把每节写成 `heading + 换行 + lines`，节之间用固定的换行、三横线、换行连接，再对 UTF-8 字节求摘要。这样字段值、标点、空格、换行、章节顺序或正文顺序只要有一个字符变化，摘要都会不同。

四个冻结摘要分别是：Compliance 为 `c872ad0ea5388e7fff8264234a4c181f5e141b33576b3caa519e2f6decf4fe0e`，Depth 为 `bb1cec38b1735d4eb45c1cc8f144896b8837487e1e5f199cd2432195e8a651c6`，Quality Gate 为 `16bc3cc314c3b7a091444cf0ad5d220dfdbb2366d3bcecd9cd82b98661a6658d`，Quality Audit 为 `4358541d814b1b22095e049b9e1b2a314341b08e5e5ada8487eccf1271c3720d`。摘要之外仍保留原有语义抽样断言，便于失败时知道是哪类信息出错，而不是只看到一串哈希不相等。

测试侧新增 `MarkdownOracle`，它只存在于 test source，负责上述确定性规范化和 SHA-256，不进入生产 jar。它接收 heading 提取器与 lines 提取器，因此不要求四个公共 `MarkdownSection` 实现共同接口。旧实现四项 oracle 全绿后才替换生产 renderer；新实现使用完全相同的期望再次四项全绿，测试 fixture、摘要和 Response 都没有为了迁就重构而修改。

完成 Spotless 后，定向集合再次运行六十六项测试，结果为零失败、零错误。它覆盖四个真实 renderer、Service、Boundary、Immutability、Controller、RoutePaths、共享 `MarkdownSections`、新结构门、全局 elegance census、精确名称 baseline 和 staged change gate。格式化把 renderer 总行数从预格式化的三千二百八十五变为三千二百八十九，门禁据最终源码收紧到三千二百八十九；相对 v1884 的三千三百七十二仍净减八十三，绝没有把旧上限放宽。

## mini-kv 证据检查

v1885 是 Java 仓库内部的展示算法收敛，不调用 mini-kv，也不修改 `D:\C\mini-kv`。四个入口的数据来自 Java Catalog，渲染发生在内存中，输出回到 Java Response。没有 RESP 命令、WAL、snapshot、socket、子进程或文件系统证据参与这条路径，因此无需为了本版启动兄弟项目，更不能借内部重构之名改写 mini-kv 的历史归档。

系统层面的依赖关系仍然明确：mini-kv 可以作为跨项目只读 readiness 的真实证据源，但当前四份 walkthrough 报告只是 Java 自身的治理登记。若某个跨项目 capstone 读取这些 Java endpoint，它看到的 route、JSON record 和 Markdown 字节都应保持不变；四份全文摘要正是在 Java 侧提供最窄而强的兼容保证。Node 或其他消费者不需要知道 renderer 文件已经改名。

本版对 mini-kv 的输入为零，对 mini-kv 的输出也为零。Java 的输入是二十二组 section 所需的 Catalog record，Java 的输出是原样的一百六十八行正文。把这个“零交互”写进讲解不是敷衍，而是安全证据：它证明维护动作没有越过仓库所有权，没有自动启动外部系统，也没有把单仓库回归冒充跨项目联合验收。

若未来确实需要改变跨项目证据 schema，应按 mini-kv 到 Java 再到 Node 的依赖顺序推进，并重新运行 C1-C4。当前版本不包含那种契约变更，所以只运行 Java 自身的完整 release gate 和远端 CI。成熟度标签仍是“单项目验证加受控只读跨项目集成”，renderer 变得更优雅不会自动扩大执行授权。

## 阻断与安全边界

四个 Registry Service 继续只读；v1885 没有开启 write routing、active shard router、credential value、raw endpoint、managed audit connection、deployment、rollback、支付或库存执行。`ReportRenderer` 都是 package-private 的无状态 final 类，只有静态纯映射，不是 Spring Bean，没有缓存、线程、网络客户端或可变单例。共享 `MarkdownSections` 同样只做内存数据变换。

领域边界由类型和包共同保护。Compliance 的 `VersionEntry` 不能传给 Quality Audit renderer，Quality Gate 的 `ReviewChecklist` 也不会进入 Depth。每个 adapter 显式导入自己 Response 的嵌套类型，共享引擎只看泛型 item、字符串行与 section factory。这样既获得算法复用，又没有创建一个知道所有领域 record 的中央巨类。

发布阻断条件写得比“代码能编译”更严格：二十二个 heading 或一百六十八行中任一字符变化，逐节行数或全文摘要失败；Service 绕开本地 renderer，结构门失败；重新手工创建 section，结构门失败；renderer 数量、总行数、超长文件名或名称 baseline 上升，elegance 门失败；修改 fixture、历史 tag、旧 oracle 或公共 Response 来迁就实现，整版失败。

四个历史 renderer 测试类的长名称本版没有强行全部改短，其中 Compliance 报告本身把测试类名作为“测试覆盖”证据输出。若只为名称数字改测试类名，报告摘要和已发布证据会被主动改写，收益小于兼容代价。因此本版先把四个生产 renderer 和四个纯 TestSupport 工厂改短，把会影响正文的测试身份留给未来有明确契约迁移方案的版本。这是边界意识，不是遗漏。

## 测试覆盖

四个原 renderer 测试现在同时承担三层责任。第一层是可诊断的语义断言，例如某个 heading 或关键字段必须出现；第二层是每节精确行数，能快速定位某组 Catalog 是否增删；第三层是完整 canonical SHA-256，阻断任何未声明的字符级漂移。三层叠加避免两种极端：只看摘要难以排错，只看抽样又可能漏掉中间行变化。

`WalkthroughRenderingStructureTests` 提供五项源码结构检查：四个家族分别检查唯一短 renderer、Service 调用、共享引擎导入和禁止重复构造；第五项检查 v1885 设计文档必须记录 Family design、二十二节、一百六十八行、最终 census 与失败条件。它不验证业务文本本身，而是防止后续维护者在行为测试仍绿时悄悄复制回旧结构。

四个超长 `...RegistryTestSupport` 被重命名为包内 `WalkthroughTestData`，所有 Boundary、Immutability、Renderer 与 Service 测试继续复用同一份真实对象图。生产 Service 的调用链没有被 mock 成另一套数据。测试命名 baseline 因此继续下降，且新工具 `MarkdownOracle` 与新结构测试的类名均符合四十字符预算。

全局数字也由机械测试绑定：renderer 文件保持三十个，总行数收紧到三千二百八十九，超长 renderer 文件名从九个降到五个；生产长 stem、长标识符出现、唯一长标识符收紧到 1159、20277、2718，测试对应为 754、9970、3773。`config/java-name-baseline.txt` 相对上一 tag 新增零项、删除十六项，证明长名没有从被删除文件搬到别处。

## 实际工作量说明

生产侧重写四个真实 renderer，涉及 Compliance 六节、Depth 五节、Quality Gate 五节和 Quality Audit 六节。旧文件通过 git move 保留历史连续性，再改为短名 `ReportRenderer`；四个 Service 改为调用包内短类型。不是简单全局替换：每一份 line mapper 都重新核对旧字段顺序、标签文字、布尔值表达和计数 key，并由旧实现摘要兜底。

测试侧为四份输出增加 headings、逐节行数与全文摘要，引入一个通用但仅测试可见的 oracle，新增家族结构门，并把四个数据工厂改成 `WalkthroughTestData`。设计侧新增 requirement-evidence matrix、冻结报告表、测量结果和失败条件。名称 baseline、生产/测试精确上限、renderer census 脚本与门禁同步收紧，确保这次改善不会在下一版悄悄反弹。

可复现结果是：ops 生产 Java 仍为 1249 个，说明本版没有靠删除能力降低总量；renderer 数量仍为 30 个，但总行数由 3372 降到 3289，超长 renderer 文件名由 9 降到 5。四个目标 renderer 从 541 行降到 458 行。生产名称指标由 1163/20334/2722 收紧到 1159/20277/2718，测试由 758/9995/3778 收紧到 754/9970/3773。

这份工作量的价值不只是少八十三行。维护者现在能从每个 `ReportRenderer` 一眼读出“有哪些 section”和“每类 record 如何变成一行”，共享层只拥有列表不可变与 section 构造机制；输出又被字符级 oracle 锁定。它同时降低阅读路径、重复修复面和命名噪声。本项目坚持禁止硬凑：讲解的每个数字都有命令或测试来源，每个抽象都至少服务四个真实家族，没有为了达到三千字重复同一句结论。

## 一句话总结

v1885 在四条公共只读路由、四套 Response 和一百六十八行报告正文完全不变的前提下，让四个领域用各自短小 `ReportRenderer` 表达政策、复用同一个经过验证的 `MarkdownSections` 表达机制，并用旧实现先冻结、新实现原样再通过的二十二节全文摘要与只减不增门禁证明这次收敛真正更清楚、更短且可持续维护。
