# v1886：五类只读证据报告的渲染收官

## 入口路由

本版本处理的不是五个新接口，而是五条已经长期存在的只读证据链末端。它们分别属于凭据解析器禁用假执行归档、沙箱连接阻断上下文档案、沙箱连接预检上游收据核验清单、截图讲解归档注册表，以及签名审批草稿 Profile Section 交接。调用者仍然从原有 Controller 和原有路由进入，Controller 仍把请求交给原有 Service，Service 仍返回原有 Response。换句话说，外部看到的路径、HTTP 方法、字段名称、字段顺序和状态语义都没有变化，变化只发生在 Service 组装完领域数据之后的最后一步：由哪个包内类把这些数据表达成 Markdown。

第一条链的输入是凭据解析器归档所需的边界、交接、要求、运行时保护、来源和核验记录，输出是六个有序 Markdown section。第二条链输入的是被阻断执行上下文的边界、上下文、下游接收、执行保护、交接、前置证据、来源、核验和警告，输出九个 section。第三条链输入预检清单中的边界、代码健康、字段、交接、引用、拆分、来源和核验记录，输出八个 section。第四条链输入截图归档的边界、当前状态、命名规则、分段和核验记录，输出五个带计数前缀的 section。第五条链输入五个签名审批草稿交接记录，输出五个一对一的 rendered handoff。五条链合计三十三个输出块和二百零二行正文。

这轮没有把路由改成一个所谓统一入口，也没有把五个 Service 合并。入口属于领域契约，渲染属于输出实现，两者的变化频率不同。若为了减少文件名而合并路由，调用方就会被迫理解不属于自己的参数和状态；若让 Controller 直接调用共享引擎，事务边界和领域组装过程又会泄漏到 Web 层。因此调用方向保持为“Controller 到领域 Service，再到本包短 Renderer，必要时才到领域中立的 MarkdownSections”。这条单向依赖让读者从入口向下跟踪时，每一层只承担一种职责。

五个旧 Renderer 的名字都把完整业务句子搬进了类型名，最长名字超过百字符。它们虽然能描述来源，却不能帮助读者理解当前位置的职责。新 owner 改为 `ArchiveRenderer`、`DossierRenderer`、`ManifestRenderer`、`ReportRenderer` 和 `HandoffRenderer`。包名已经提供领域上下文，类名只回答“在这个包里我负责什么输出”。调用点因此从一整行长标识符收敛为 `ArchiveRenderer.render(...)` 之类的清楚动作。短名不是删掉语义，而是让目录、类型和方法共同承担语义，避免一个标识符重复说三遍相同背景。

## 响应模型

五个 Response 都保持原样，这是本轮最重要的兼容边界。归档、档案、清单和截图报告仍持有各自的嵌套 section record；每个 record 仍包含原有 heading、不可变正文行和领域状态。Handoff Response 仍持有五个 `RenderedHandoff`，每个对象仍由 `markdownHeading`、`markdownBody` 和 `status` 组成。没有为了共享渲染器而创建一个公共万能 Response，也没有让不同领域的 record 互相继承。外部 JSON 结构和 Java 调用签名因此都不需要迁移。

四份 section 型报告共享的是“如何把一组领域元素稳定地变成不可变 section 列表”这一行为，而不是共享领域数据。`MarkdownSections.mapped` 接收标题、输入集合和行映射函数，按输入顺序生成 section；`MarkdownSections.counted` 在相同流程上增加固定格式的计数行。领域 owner 仍决定标题、每一行如何拼接、哪些字段可见以及 section 顺序。引擎不知道凭据、沙箱、截图或审批是什么，它只保证空集合、顺序、拷贝和不可变性的一致处理。这一边界使公共代码足够小，也让领域政策仍能在对应包内被直接读懂。

Handoff 没有强行套用 `MarkdownSections`。它的输入和输出都是五个位置一一对应的领域对象，正文由六个固定键组成，没有“一个 section 包含多条元素”的聚合语义。如果把它包装成通用 section，只会增加类型转换和标题回填，读者反而需要跨文件才能知道五个交接为什么对应五个输出。因此 `HandoffRenderer` 仅缩短 owner 名称，并保留直接流式映射。这是有意的不统一：共享抽象必须消除真实重复，不能为了表面整齐抹掉领域形状。

输出兼容不是凭肉眼判断。旧实现还存在时，测试先读取真实 Service Response，冻结每个 heading、每块正文行数和整份报告的规范化 UTF-8 SHA-256。Archive 的行数序列为 `1/4/4/1/10/8`，摘要为 `f9f498cb1e6cb70f21eabe5b6d5b9c2459df84193c57d48df328fece62ac6165`；Dossier 为 `1/3/6/5/12/4/5/10/4`，摘要为 `6343820c1f3bda7b2574e17515fa949713cd8ebfe819797e727ca320fce57aff`；Manifest 为 `1/12/5/7/17/6/10/4`，摘要为 `bfe109f24df2475a13c61621fd81a4732b68241cf27edfe6563299c7902976fd`。这三组数字既约束内容，也约束边界。

Screenshot 的五块正文行数为 `4/6/7/9/6`，摘要为 `205b7c2d1d84604b31f35a1ec6d3993c9e702a99ed122dbc58edf287f16a58f8`；Handoff 五块都为六行，摘要为 `2cfaf4917eaecff8e5d09dc9f787c785d3067f56f2fa16baa3699f9ccc508d9a`。替换实现后，同一批测试不改期望再次通过。只要多一个空格、少一个连字符、调换一行、改变计数前缀或跨错 section，摘要就会不同。这样“Response 没改”不再是一句开发者自述，而是可以反驳错误实现的机械事实。

## 上游证据配置

每份报告的上游输入仍由原 Catalog、Support 和领域 Service 生成。Credential Resolver 的归档继续从边界、要求、运行时保护、来源、核验和交接 Catalog 取得只读值；Sandbox Dossier 与 Manifest 继续读取各自独立的目录数据；Screenshot Registry 继续消费当前归档、命名规则、分段与核验信息；Profile Handoff 继续从五条已存在的签名审批草稿链取得版本、端点、profile、Node marker、路由字段数和消费者边界。这些 Catalog 没有被搬进共享引擎，也没有因为 Renderer 改名而重排。

这一选择保护了数据与行为的分界。Catalog 表达“有哪些被批准的事实”，Renderer 表达“怎样把事实呈现给人和下游只读消费者”。如果把标题和格式写回 Catalog，每次展示调整都会污染证据配置；如果让 Renderer 自己重新推导 Catalog 数据，同一事实会出现两个来源。当前结构只允许 Service 把已组装的领域值交给 Renderer，Renderer 不访问网络、不读环境变量、不重新请求数据库，也不生成执行凭据。输入是完整值对象，输出是完整不可变文本对象，数据流在方法签名上可见。

本版本还保留了所有 fixture 字节。冻结摘要建立在真实现有 fixture 和 Catalog 上，迁移期间禁止修改 fixture 来让新实现“看起来一致”。这项限制很重要，因为测试期望若和实现同时变化，摘要再精确也无法证明兼容。这里采用的是先在 v1885 实现上记录事实，再替换生产实现，最后用完全相同的测试重新判断。测试成为跨实现的裁判，而不是新实现的附属说明。

上游也包括 Node 和 mini-kv 已知的只读消费约束。Java 报告可以提及 Node marker、版本和只读消费者边界，但不因此获得启动 Node、连接真实凭据或驱动 mini-kv 写入的权限。跨项目字段仍是证据，不是执行指令。v1886 没有修改 route 常量、schema、fixture 路径或 digest 协议，所以不要求关联项目同步改版，也不会破坏 Node 已固定的绝对归档引用。

## 服务层核心流程

五个 Service 的核心流程都保持三段式。第一段从 Catalog 或内部 builder 取得领域记录；第二段构造原 Response 所需的原始列表和状态；第三段调用本包 Renderer 生成展示字段，再把结果装回 Response。v1886 只替换第三段的 owner 名称和内部实现。事务注解、参数、异常传播、返回类型以及 Controller 调用方式都没有移动。服务层仍是领域编排者，Renderer 仍是纯函数式输出适配器。

`ArchiveRenderer` 使用六次声明式映射，对应六种嵌套 record。每次映射在调用点写明 heading 和行生成函数，因此读者可以在一个文件内看到完整输出顺序；共享引擎只完成遍历、不可变复制和 section 构造。`DossierRenderer` 对九组输入采用相同结构，删除旧实现中重复的列表分配、逐项循环和 section 包装。`ManifestRenderer` 对八组输入复用相同机制。三者的领域行模板并未抽到一个公共字符串工具中，因为同名字段在不同报告中的意义和审计责任并不相同。

`ReportRenderer` 使用 `counted`，原因不是名字更统一，而是 Screenshot 报告的旧协议本来就要求每个 section 首行携带特定计数键。计数键依旧由本包提供，引擎只把数量放到确定位置。它不能自行重命名键，也不能忽略空集合。这样既去掉手写 `ArrayList` 与重复 section 构造，又保留截图归档协议的可见差异。新文件为一百零九行，仍低于单个 owner 的维护预算，而且每组映射都能直接对应到一个 Response 字段。

`HandoffRenderer` 只有三十四行。它把五条 handoff 逐条变成标题、六行正文和 `passed` 状态，不创建第二套 Markdown engine。这里的简洁来自删掉重复上下文和长类型前缀，不来自隐藏业务。五个新 owner 的行数分别为七十三、九十二、八十九、一百零九和三十四，总体让全局 Renderer 行数从三千二百八十九降到三千二百四十六。数量仍为三十，因为本轮目的不是把不相关报告合并，而是让每个现有 owner 更短、更明确并复用已经证明合适的机制。

## Java 证据检查

第一层证据是输出 oracle。五组测试在旧实现上先通过，再在新实现上原样通过；总共三十三块、二百零二行的 heading、逐块行数和全文摘要没有变化。测试调用真实 TestData 和真实 Service 链，不直接复制 Renderer 内部算法。这样即使实现从循环改为声明式映射，只要最终协议相同就通过；若仅在测试中复制新算法，两边犯同一个错误也可能通过，因此本轮没有采用那种做法。

第二层是结构门 `RendererCloseoutStructureTests`。它递归扫描生产 `ops/maintenance` 下所有 Renderer 文件，并要求文件 stem 长度不超过四十。这个断言不是“最多五个”之类可继续积债的宽松上限，而是精确要求长 Renderer 为零。五个 case 用数据记录表达，统一验证短 owner 存在、退休 owner 不存在、Service 调用短 owner、短 TestData 存在、旧 TestSupport 不存在。四个 section 报告必须导入 `MarkdownSections` 且不得出现手写 `new ArrayList` 或直接构造 section；Handoff 则必须不依赖该引擎。机械门同时保护共享和不共享两种正确选择。

第三层是历史边界。v1801、v1802、v1803 和 v1829 当年建立的提取测试原先把长 Renderer 文件名列为“窄包中必须存在”的证据。如果只删除这些断言，历史测试会失去一部分意义；如果完全不改，它们又会阻止合理重命名。本轮把它们升级为当前短 owner 必须位于原窄包，退休长名在窄包与根包都必须不存在，其余 Controller 和路由聚合断言保持不变。这保留并加强了“实现已经离开根包”的原始承诺。

第四层是全局优雅 ratchet。`OpsEleganceCensusTests` 把 Renderer 总数上限保持为三十，把总行数上限从三千二百八十九收紧到三千二百四十六，并把长 Renderer 从上限五改为必须为空。`JavaEleganceGateTests` 同步冻结生产长 stem、长标识符出现次数和唯一值为 `1154/20240/2713`，测试侧为 `746/9916/3763`。重新生成的 exact name baseline 新增零项、删除二十八项。后续版本若重新引入任何已删除长名，聚合门和身份门至少有一层会失败。

## mini-kv 证据检查

mini-kv 在这轮不是被修改对象，但它仍出现在证据解释中，因为部分 Java 报告描述跨项目只读准备度。正确的检查不是启动 mini-kv 或伪造一次写入，而是确认 Java 输出继续把它当成外部证据来源和只读边界。Renderer 只格式化上游已经提供的版本、profile、endpoint 或 digest 字段，不打开 socket，不调用 CLI，不访问 WAL，也不修改 snapshot。Java 端的纯渲染变化因此不会改变 mini-kv 的运行状态。

如果后续需要真正的跨项目联合验证，应由 Node 拥有的 env-gated capstone 显式启动真实 Java jar 和真实 `minikv_cli`，并在独立检查中证明无写入边界。v1886 不冒充那类集成测试。本轮能证明的是 Java 自身输出在重构前后字节兼容，且没有新增任何执行通道；不能证明一个未启动的 mini-kv 实例健康，也不能把冻结 fixture 当作实时实例响应。把可证明与不可证明的范围写清楚，比在讲解里声称“跨项目都正常”更可靠。

mini-kv 的历史归档路径也没有移动。Node 已有大量绝对路径和摘要引用，Java 这轮仅增加本项目自己的讲解文件，不重命名 sibling archive，不改跨项目 fixture，也不要求 C++ 仓库追随 Java 类型名。`ArchiveRenderer` 等短名只在 Java 包内部可见，跨项目契约依旧由 HTTP 字段、固定证据和独立 capstone 约束，而不是由 Java 私有类名约束。

因此 mini-kv 证据检查的输入是既有跨项目边界声明、冻结 fixture 和 Java 输出中的只读标记，输出是“这些内容未因内部重构发生漂移”的结论。它不是一次运行时健康检查。这个区分让维护者知道何时应该运行 Java 单仓门，何时必须切换到 Node capstone，也避免一个内部重构版本无端控制另一个仓库的发布节奏。

## 阻断与安全边界

本版本首先阻断输出漂移。任何 heading、正文字符、顺序、计数键、section 边界或全文摘要变化都会使精确测试失败。其次阻断架构回退：重新引入长 Renderer、第二套 Markdown engine、手写重复 section 组装、旧 TestSupport 或退休 owner 都会触发结构门。再次阻断历史失真：短 owner 若回到根包，或历史提取测试被删而没有更强替代，版本不成立。

安全边界没有因为“只读报告”而放松。Renderer 不接收 credential value，不解析原始 endpoint，不建立 managed audit connection，不执行 deployment 或 rollback，不触发 payment、库存、消息重放或 mini-kv 写命令。它接收的只是已经由领域层批准展示的值。即使正文中出现 endpoint、profile 或 approval 字样，也只是证据文本，不是可执行句柄。调用者不能通过修改 Markdown 让 Service 获得新的业务权限。

公共 route、Response、Controller 和 Catalog 被列为禁止迁就实现的对象。若新抽象要求改变它们，正确动作是回退抽象，而不是把兼容破坏包装成重构。fixture 与测试期望也不能同时修改。这个版本明确遵守“禁止硬凑”：篇幅不足时增加真实分析和机械证据，代码不足以支撑共享时保留局部实现，不以复制段落、虚构风险或制造无意义类来满足形式。

本轮也不宣称整个代码库已经达到 coding brilliant and elegant 九分。长 Renderer 已经归零，是一个完成的专项；但 ops 总量、Catalog 重复、生产长 stem、长标识符使用和若干大文件仍有明显债务。真实进度必须由 census 与失败门表达。把阶段目标说成全局完成会削弱后续优化的判断力，因此路线图明确把下一阶段指向 Catalog engine，并要求先找真实重复、再决定抽象。

## 测试覆盖

测试分为五层。第一层是每个报告已有的领域行为测试，检查标题、状态、关键字段、边界标记和不可变性。第二层是本轮新增或加强的精确 Markdown 测试，检查逐块行数与 SHA-256，覆盖微小字符漂移。第三层是 Service 与 Controller 的既有测试，确认短 owner 接入后调用链和返回类型不变。第四层是四个历史提取版本的结构测试，确认包边界没有被重命名操作破坏。第五层是全局结构、命名 baseline、Renderer census 和 staged-change 门，防止以后反弹。

Handoff 原有两份 Markdown 测试文件职责重叠，一份检查输出内容与摘要，一份检查顺序和字段可见性。本轮把四个测试合并进 `HandoffMarkdownTests`，删除重复的稳定性文件。测试数量没有被当成价值指标，关注的是一个输出协议由一个清楚 owner 维护。五个长 TestSupport 同样改为 `ArchiveTestData`、`DossierTestData`、`ManifestTestData`、`ScreenshotTestData` 和 `HandoffTestData`，调用方仍复用真实服务链，不构造与生产脱节的假对象。

旧实现冻结测试先通过八项，因为简单类名选择同时匹配到仓库中同名的其他短测试；新实现同一选择仍八项通过。随后格式化后的扩大选择加入历史、结构和优雅门，共执行五十三项，失败、错误和跳过均为零。这个数字不代替完整发布门，它只证明本轮最相关的风险已被快速定位覆盖。最终仍需运行 `scripts/verify-release.ps1`，让全部单元、集成、JaCoCo、SpotBugs、Spotless、归档和打包门共同判断最终树。

覆盖策略刻意避免绑定私有实现细节。精确测试绑定输出协议，结构测试只绑定长期维护边界，领域测试绑定业务含义。`mapped` 内部如何迭代不是测试目标，Service 是否委托本包短 owner 才是；局部变量名不是目标，长文件名归零才是。这样后续可以继续优化引擎内部，而不能在不知情时改变消费者看到的结果。

## 实际工作量说明

这不是五次文件重命名。工作从变更前盘点开始：确认全仓只剩五个超长 Renderer，逐一读取 Service、Response、Catalog、测试支撑和历史提取门，判断四个 case 可复用既有引擎、一个 case 应保持局部映射。随后在旧实现上新增三份精确测试并加强两份已有测试，冻结三十三块、二百零二行和五个摘要。只有旧实现证据通过后才移动生产文件、替换实现和更新 Service 调用点。

实现阶段为四份 section 报告去除重复列表分配和包装逻辑，同时保留每个领域的行 mapper；Handoff 只做职责收敛。测试阶段机械迁移五个 TestData owner，合并 Handoff 重复测试，升级四代历史边界，新增一个数据驱动结构门，并重跑格式化后的五十三项聚焦测试。度量阶段执行 ops elegance 与 Java maintainability census，收紧两组聚合门，重建 exact name baseline并确认新增零、删除二十八。

可量化结果包括：Renderer 数量保持三十，总行数减少四十三，超长 Renderer 文件名从五归零；生产长 stem 减少五，长标识符出现减少三十七，唯一长标识符减少五；测试长 stem 减少八，出现次数减少五十四，唯一值减少十。五个新 owner 行数分别为七十三、九十二、八十九、一百零九和三十四。代码规模的下降不是通过删除输出或测试取得，而是在完整 oracle 不变、历史门更严格的条件下取得。

文档工作同样属于交付：先写六行 Family design，明确数据与行为边界；更新 Requirement Evidence Matrix、CHANGELOG、生产卓越账本、最终证据候选和三分优雅路线图；本篇讲解在最终 verify 前完成，并将进入精确 archive manifest。后续完整发布门、实现提交与 CI、closeout 提交与 CI、annotated tag 仍是版本完成条件。没有这些机械边界，代码看起来更短也只能称为候选。

## 一句话总结

v1886 用五组不可篡改输出证据把最后五个长 Renderer 收敛为职责短名，让四份真正同构的 section 报告共享既有引擎，让 Handoff 保留更清楚的直接映射，并以历史门、全局零长名门和只减不增 baseline 证明本项目是在降低复杂度，而不是把复杂度藏起来。
