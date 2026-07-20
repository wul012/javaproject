# v1873 声明式 Markdown 渲染引擎讲解

## 入口路由

本版本处理的是一个已经对外存在的只读运维入口，它负责展示“最小只读门、操作员与持续集成交接、归档摘要”这一串证据。调用者仍然从原来的 HTTP 地址进入，Controller 的映射、请求方式、鉴权方式和返回对象都没有变化。也就是说，外部系统看到的门牌没有换，进门后的第一位接待者也没有换；变化发生在服务内部把结构化证据翻译成 Markdown 的最后一段。这样的选择很重要，因为本项目已经被 Node 的只读聚合流程消费，若为了内部代码好看而改路由，收益会立刻被跨项目兼容成本抵消。

入口收到请求后，Controller 仍只做适配：调用 registry service，拿到响应 record，再交给 Spring 序列化。它不启动 Java 之外的进程，不连接 mini-kv，不执行脚本，也不把任何字符串解释为可运行命令。服务先读取上游归档验证快照，再组织来源、摘要、消费包、重放说明、边界锁和计分卡六组数据。旧实现的最后一步是由一个总 Renderer 顺次调用六个小 Renderer；新实现仍按完全相同的顺序产出六个 section，只把“一类数据配一个类”的文件组织方式改成“一套算法配六条声明”。

可以把入口过程理解成一条固定流水线：HTTP 只读请求进入，Controller 交给服务，服务取得已存在的证据对象，Catalog 提供固定领域条目，`ReportRenderer` 声明各条目的文字投影，`MarkdownSections` 统一补上计数行并创建不可变 section，最后响应由原 record 返回。流水线中没有新增分支，也没有把数据写回任何来源。因而本版的核心验证不是“新功能看起来能用”，而是证明流水线首尾字节语义保持一致，同时中间结构显著变短。

## 响应模型

响应模型继续使用原有 registry response 以及其中的嵌套 record。最外层同时携带结构化字段和 `markdownSections`，六类 section 依次为 Source Archive、Digest Sections、Consumer Packets、Replay Instructions、Boundary Locks、Scorecard。每个 section 都由标题和行列表构成；第一行是该类条目的数量，其余行把 record 字段投影为稳定文本。外部消费者既可以读取结构化 JSON，也可以直接展示 Markdown，因此标题、空格、分隔符、布尔值、状态词和列表顺序都属于兼容表面，不能被“只是格式调整”轻率改变。

新引擎没有重新定义任何 response record，也没有把六种领域条目塞进无类型 Map。`ReportRenderer` 的六个私有方法仍分别接收明确的 `SourceArchiveSnapshot`、`DigestSection`、`ConsumerPacket`、`ReplayInstruction`、`BoundaryLock` 与 `ScorecardEntry`。这保留了编译期字段检查：例如消费包需要 owner、digest 与 boundary-lock 标记，重放说明需要 order、batch、command family 和 read-only 状态；若字段被删除或改型，编译器会在对应映射处给出精确错误，而不是等到运行时查找字符串键失败。

不可变性也没有被牺牲。共享引擎通过 `Stream.toList()` 构造最终行列表，调用者修改原始条目列表不会改变已经生成的 section，拿到行列表后也不能追加内容。专门的引擎测试先用可变 `ArrayList` 渲染两个条目，再向原列表加入第三项，断言结果仍是计数二和原先两行；随后直接尝试修改结果列表，必须得到 `UnsupportedOperationException`。空列表也有独立断言，输出恰好是一行 `item-count=0`，因此家族不需要复制空集合分支。

## 上游证据配置

本入口的内容不是临时拼出的演示文本，而是由现有上游归档验证服务与本家族 Catalog 共同提供。Source Archive 表明证据来自哪个 Java 版本、哪个只读 endpoint、归档处于什么状态；Digest Sections 汇总来源快照、制品、操作员通道、持续集成批次、边界锁和计分卡的通过数；其余 Catalog 则描述下游消费包、只读重放顺序、禁止事项和最终计分。v1873 没有修改这些 Catalog 的任何数据，因而没有借重构机会悄悄改变通过数或状态。

服务对上游的依赖仍然是构造器注入的归档验证 registry service。Spring 负责装配，测试则通过短名 `ArchiveDigestTestData` 建立相同对象图。这个测试支撑名称替代了八十多个字符的旧 TestSupport 名称，但构造顺序和实例类型没有变化。这样做不是美化表面：家族的多个测试以前每次引用超长类名，视觉噪声掩盖了真正要断言的字段；短概念名让测试正文聚焦“取哪份数据、验证什么行为”，同时生产与测试长名 census 都实质下降。

配置边界仍然严格只读。这里所谓 replay instruction 是给人工或 CI 阅读的说明 record，不是执行器；endpoint 字段是证据描述，不是由本服务发起的网络请求；boundary lock 中的 `locked=true` 是可见的禁止证明，不会转换成权限开关。尤其是 raw endpoint、credential value、managed audit HTTP、runtime shell 和 mini-kv write admin 等边界，仍由八条锁明确拒绝。本版既没有读取秘密，也没有新增可连接地址的客户端。

## 服务层核心流程

旧流程中，registry service 把六组列表交给一个长名总 Renderer；总 Renderer 又依赖 Source、Section、ConsumerPacket、ReplayInstruction、BoundaryLock、Scorecard 六个长名 Renderer；这些小类都重复相同骨架：创建行集合，先写 `xxx-count=`，遍历列表映射文本，再创建 `MarkdownSection`。加上一个只包装计数行的 Support，八个文件总计承载的主要变化点只有标题、计数名和一条字段映射。文件很多并没有形成真正的模块边界，因为这些类没有独立策略、状态或复用者。

v1873 把稳定算法抽到 `MarkdownSections.counted`。它接收标题、计数名、条目列表、条目到字符串的函数，以及 section 构造函数。算法先校验五个输入不为 null，再把计数行与映射后的条目流合并，最后交给构造函数。泛型 `T` 保留条目类型，泛型 `S` 让引擎不依赖 archive-digest 的具体响应 record，因此后续 consumer、dossier 或 CI 家族可以复用，而不会反向依赖当前家族。

变化的数据留在短名 `ReportRenderer`。六个方法一眼可以看到各自的标题、计数键和字段顺序；总 `render` 用 `List.of` 明确固定 section 顺序。它不是把旧八个文件粗暴粘进一个巨型文件：新组合器约一百二十余行，低于三百行限制；共享算法二十余行；旧 Renderer 及 Support 共二百四十余行被删除。服务只把调用从旧总 Renderer 改为 `ReportRenderer.render`，其余取数和响应构造保持原样。这样的拆分把“所有家族都会做的算法”与“本家族独有的文字规格”分开，才是可复用抽象，而不是文件数量游戏。

## Java 证据检查

行为证据先于迁移建立。现有家族测试虽然检查了来源、Catalog、消费包、重放顺序和聚合通过状态，但没有逐行冻结全部 Markdown。为避免重构后测试只证明“大概相似”，本版先在旧实现上运行一次临时探针取得真实输出，再把六个 section 的标题、计数行和每一条正文写入永久 `ArchiveDigestMarkdownTests`。永久测试在删除旧 Renderer 之前已对旧代码通过，因此它是行为 oracle，而不是照着新实现补出的自我证明。临时探针完成取证后已删除，没有进入交付物。

结构证据由两层门负责。v1845 的历史抽取测试原本把十七个文件都钉成必须存在，其中包括本次要删除的八个一次性实现。简单删掉这项测试会丢失历史边界，所以本版将它改为更严格的现状门：十个当前核心文件必须存在，八个旧 Renderer/Support 在根目录和家族目录都必须不存在，家族生产文件不得超过十个；六个必要测试必须存在，但允许未来增加更强测试。新 `OpsEleganceCensusTests` 再从全局角度限制 Renderer 数量、总行数、长文件名数量和家族文件数。

可复现脚本 `scripts/ops-elegance-census.ps1` 使用 `rg --files` 统一枚举口径，并通过长路径安全的 .NET 文件读取计算行数。它输出 ops Java、Renderer、Catalog、Service、五百行热点、最大文件和本家族文件数，支持 JSON 供后续版本与外部评审复算。本版实测 `ops` 文件从一千三百五十二降为一千三百四十六，Renderer 从一百二十一降为一百一十五，总行从五千三百五十五降为五千二百三十六，超长 Renderer 文件名从一百一十九降为一百一十二。

## mini-kv 证据检查

这个 Java 入口会描述 mini-kv 相关边界，但不会在渲染时运行 mini-kv。所谓 mini-kv 证据检查，检查的是响应仍然明确表达“不可自动启动、不可写管理、不可把证据端点当执行端点”，而不是为了让测试更真实就启动兄弟仓库。现有 Boundary Locks 的最后一项仍是 `no-mini-kv-write-admin`，原因文字仍是归档边界保持锁定；重放说明的每一项仍标注 `read-only=true`。逐行 oracle 会在这些文字、布尔值或顺序发生变化时失败。

本版没有编辑 `D:\C\mini-kv`，也没有移动 Java 的历史归档路径。Node 曾固定引用兄弟仓库中的归档文件和摘要，因而“整理目录”可能比改代码更危险；v1873 只在新的续写目录添加本版讲解，并由 archive manifest 记录摘要。真正的跨项目活体联调仍由 Node 拥有的环境开关 capstone 负责，本项目不会在单仓库重构中把冻结 fixture 假装成实时执行结果，也不会自称获得新的系统级成熟度。

如果未来 mini-kv 的证据 schema 或规范拼写发生变化，应先按依赖顺序由上游发布契约，再由 Java 以兼容读取方式消费，最后由 Node 聚合。当前改动只改变 Java 内部如何把已经取得的 record 映射为文本，不改变 record 来源、schema、摘要或路由，所以属于可并行的非契约维护。本段的价值在于把“不启动兄弟项目”说明为安全设计，而不是测试偷懒。

## 阻断与安全边界

本版本明确不触碰写路由、活动分片路由、支付、库存变更、失败事件重放授权、部署或回滚。`MarkdownSections` 只是一个纯函数式文本组合器：输入列表和映射函数，输出不可变 section；它没有 Spring 注解、网络客户端、文件写入、进程调用、反射执行或 credential 参数。`ReportRenderer` 也是包内可见，只有同一家族服务可以使用，避免把内部文本规格误当成公共平台 API。

八条旧边界锁的完整文字由 oracle 固定，包括不自动启动 Java、不自动启动 mini-kv、不开放 write routing、不暴露 credential value、不返回 raw endpoint URL、不建立 managed audit HTTP、不开放 runtime shell、不提供 mini-kv write admin。任何一条被删除、改成 false 或调整顺序，测试都会显示具体 section 的差异。Controller 与 response 未改又提供第二层保护：HTTP 映射和 JSON 字段仍由既有集成测试覆盖。

失败条件也在版本任务书中成文：修改 fixture 或测试期望迁就新实现、放宽 census 上限、保留转发壳、引入超过四十字符的新名字、在讲解前运行最终 verify，都会使本版无效。特别需要说明的是 Git 变更门的一处修正。旧门把已经删除的长文件也当作存活文件检查，并要求任何修改过的旧文件一次消灭全部历史长引用，实际会阻止渐进删债。本版把职责拆开：新增文件名必须短，精确 baseline 禁止新增长名身份，总量门禁止长名出现次数回升，删除路径则被视为债务消失。这不是放宽，而是让三个门各自检查可判定事实。

## 测试覆盖

第一轮聚焦测试包含共享引擎、完整 Markdown oracle、四个家族行为测试、Controller Markdown 测试、v1845 历史结构门和全局 census 门，共二十一个测试，全部通过。它证明了六类结构化数据与文本输出之间的映射、只读聚合结果、空集合处理、列表不可变性、旧文件消失以及当前文件上限。随后优雅、变更、维护预算与 SpotBugs 豁免身份门共同执行；门首先真实发现删除路径误判，再在语义修复后通过，过程被保留为失败面证据，而不是隐藏第一次失败。

`JavaEleganceGateTests` 会从 Git 读取上一版精确 baseline，确认当前长文件路径和长标识符集合是其子集。生产侧实测长 stem 从一千二百九十七降到一千二百八十九，长标识符出现从二万一千一百六十七降到二万一千一百二十四；测试侧通过把旧超长 TestSupport 改名为 `ArchiveDigestTestData` 并缩短新测试方法，长 stem 从七百九十五降到七百九十四，出现次数从一万零二百二十五降到一万零二百一十六。两个方向都下降，证明没有把生产债务转嫁给测试。

最终验证仍须在本文完成后运行完整 `mvnw -B verify`。它将覆盖 Spotless、全部非 Docker 测试、JaCoCo 所有 floor、SpotBugs 零发现和可执行 jar 打包。随后提交推送到唯一 canonical remote `javaproject`，检查真实 GitHub Actions 的 headless 与 Docker job；只有绿色 CI、closeout 账本、tag 和远端可达性全部闭环，本版才算完成。本地聚焦测试不能替代远端 Linux 换行、工作流配置和 Docker 环境证据。

## 实际工作量说明

本版不是把七个类删除后写一句“重构完成”。工作从 Step-0 对账开始：确认 v1872 tag、本地分支和 canonical remote 同指一个提交，检查上一版 closeout CI 绿色，读取方法内核与三分路线图，并用 CodeGraph 查看 Renderer 家族的调用边。随后建立需求证据矩阵和十行以内 Family design，先测量一千四百八十四个生产 Java 文件、一百二十一个 Renderer、五千三百五十五行 Renderer 与长名基线，再选取依赖最小的 archive-digest 家族作为第一刀。

实现前先补行为 oracle：临时探针在旧实现上运行，输出被转成永久逐行断言，探针删除；之后才写二十余行共享引擎和一百二十余行家族渲染规格，删除七个 Renderer 与一个 Support，修改服务最后一步。结构测试从“旧文件必须存在”升级为“当前核心必须存在、旧壳必须消失、包上限不得反弹”。新 census 脚本与 Java 门把全局变化固化。聚焦测试第一次二十一项绿色后，长名 census 又发现测试侧净增长，于是继续把超长 TestSupport 改成短概念并缩短三个过长方法名，直到生产与测试指标同时下降。

门禁修正同样属于实质工程量。删除文件被误判后，没有把测试排除或临时跳过，而是阅读 `GitChangeSet` 的 dirty-tree 与 clean-CI 两条路径，修正仅对磁盘存在文件读取内容；第二次失败又表明单文件词法扫描把“引用一个历史类型”误当成“声明新长名”，于是将新增文件名、精确身份集合和总出现次数分给三个独立门。修正后十项核心门绿色。整个过程坚持禁止硬凑：每段文档都对应真实代码、命令、失败或边界；如果内容不足三千字，应扩大真正的验证与设计工作，而不是重复结论。

从净结果看，生产 Java 六个文件净减少，家族从十七个文件收敛到十个，Renderer 六个净减少，Renderer 总行净减少一百一十九，生产长 stem 减少八个、长标识符出现减少四十三次，测试长 stem 减少一个、长标识符出现减少九次。新增共享引擎已有第二批复用计划，不是只服务一个 case 的装饰抽象。所有历史 route、response、Catalog 数据、归档文件和兄弟仓库保持原样。

## 一句话总结

v1873 把六份重复的“计数加映射”实现收敛成一个有不可变语义的声明式引擎和一个短名家族规格，同时用旧实现上建立的逐行 oracle、精确长名 baseline、全局 census 与完整构建证明外部行为不变。对使用者而言，入口、JSON 和 Markdown 完全如旧；对维护者而言，阅读一个报告只需看一个文件，新增 section 只需增加一条类型安全映射，第三个相似家族也不再有复制类的借口。

本项目在这一版购买的是可持续的生成规则，而不只是一次性删除数字：算法有唯一归属，家族差异以数据化声明表达，删除旧壳有负向断言，边界有逐行证据，未来反弹会机械失败。它是“三分优雅度提升”计划的第一批可量化进展，不自授最终分数，也不越权替代跨项目评审。
