# version-1847：Operator-CI 验证卷宗层拆分讲解

本篇解释 Java 本项目 v1847 的真实工程内容。前一版 ConsumerPackage 已把摘要组织成不同角色可消费的包，但“可消费”还不等于“可交给评审签收”。VerificationDossier 的作用是把来源身份、章节完整性、受众路由、CI 通道、验收闸门、边界审计、发布清单和交接回执组合成一份可机械检查、也可人工复核的卷宗。它不会批准发布，也不会执行 CI。全文遵守“禁止硬凑”，每一项都对应现有 Java 类型、字段、目录、测试或命令。

## 实际工作量说明

这一版迁移二十五个生产类和七个包内测试。生产类覆盖来源包快照、六项 provenance、九个 section digest、四条 audience route、五条 CI lane、五个 acceptance gate、八项 boundary audit、五项 release checklist、四份 handoff receipt、十项 scorecard、十个 Markdown 章节及其渲染器、服务、响应与支持类。这些对象共同负责“验证材料如何形成卷宗”，与后续 ReleaseAcceptance 的“是否接受卷宗”有明确边界。

Spring 控制器继续留在根 `ops` 包，控制器 Markdown 聚合测试也留根。控制器只暴露 GET 入口并委托服务，Markdown 聚合测试同时核对入口和整份可读卷宗，属于公共入口层。验收边界、受众与 CI、清单与回执、不可变性、章节摘要、来源 provenance 和测试工厂共七个测试随实现迁移。新包只公开 service、response 和测试工厂，其余目录与 renderer 都保持包内可见。

根目录文件由五百九十八降至五百七十三，可迁移非控制器由四百九十三降至四百六十八，Operator-CI 桶由七十三降至四十八，未归类仍为零。总 `ops` Java 文件没有增加，继续受一千三百五十二的上限保护。这一版同时是 v1843 到 v1847 五版检查点，所以除了本地门，还要等待五版实现与收尾 CI 全部成功才可交给外部评审。

## 路径决策与可维护性

VerificationDossier 的类名已经完整承载祖先链。若包名也照抄 `minimalreadonlygateoperatorcihandoffarchivedigestconsumerpackageverificationdossier`，最长生产路径实测三百零七字符，必然越过传统 Windows 边界。即使缩成 `minimalreadonlygateoperatorciverificationdossier`，最长仍有二百七十二字符，风险没有解除。

最终选择 `operatorcidossier`。它保留角色和产物两个核心语义：这是 Operator-CI 的 dossier；具体属于哪条最小只读闸门链，由完整类名和 v1846 import 清楚表达。采用该包后，最长移动生产路径约二百四十一字符，最长测试路径二百四十八字符。端点、profile、类名、响应字段、历史归档和 JSON 都不改。

这不是缩写偏好，而是由三组可复现测量驱动的兼容决定。证据页和终局 census 同时记录三百零七、二百七十二和二百四十八，v1847 测试要求短包存在并拒绝两个过长包段。以后若工作区路径变化，维护者仍能理解为什么包名没有重复整条祖先链。

## 入口路由

Dossier 端点是一个无请求体的只读 GET。调用者不能上传卷宗、提交签名、指定文件路径、传入凭据或要求运行某个批次。根控制器接收请求后调用新包服务 `registry()`，再返回不可变 response。外部输入只有读取意图，内部输入来自构造注入的 v1846 ConsumerPackage 服务。

服务直接使用 v1840 公开的 `OpsShardReadinessReleaseAcceptanceRoutePaths` 组成基础路径与 Dossier 相对路径。根聚合器仍向控制器提供兼容常量，但新包不复制字符串，也不反向依赖根聚合器。客户端看到的 URL 字节不变，路由所有者仍是单一来源，聚合器仍受一千一百一十一行限制。

调用步骤可以通俗描述为：评审者请求“给我当前卷宗”；控制器只转交请求；服务先读取 ConsumerPackage；目录分别检查来源、章节、受众、CI、验收、边界、清单和回执；分数卡核对数量；渲染器生成可读章节；支持类决定 passed 或 blocked。任何一步都只读内存值，不会因为查看卷宗而真正签收或发布。

## 响应模型

响应最前面仍是项目、版本和安全能力。只读为真，执行、启动 Java、启动 mini-kv、读取凭据、解析原始端点和托管审计 HTTP 全部为假。随后给出 endpoint、profile、来源计划、必需归档验证计划、Operator 交接计划、来源 ConsumerPackage 的版本、端点、状态以及当前 verification dossier 状态。

计数区记录一个来源包快照、六项 provenance 及通过数、九个 section digest 及通过数、四条受众路由及就绪数、五条 CI lane 及只读数、五个验收闸门及通过数、八项边界审计及锁定数、五项发布清单及就绪数、四份交接回执及就绪数、十项分数卡及通过数、十个 Markdown 章节。后面是对应不可变列表、检查清单和总状态。

嵌套记录让每类证据保持独立。来源快照证明消费包版本、端点、profile、状态和关键数量；provenance 证明来源链字段非空；section digest 证明九个 Markdown 章节都有标题和内容；audience route 把 packet 路由到评审通道；CI lane 保留批次、命令族、只读和来源通过状态；acceptance gate 绑定验证制品；boundary audit 为锁定行为补审计证据；release checklist 和 handoff receipt 分别面向发布复核与接收方签收准备。

## 上游证据配置

唯一生产上游是 v1846 ConsumerPackage service/response。Spring 将其服务注入 Dossier 服务。Dossier 调用公开 `registry()`，不会访问 v1846 包内的 manifest、audience、section、acceptance 或 checklist 目录。上游包可以重构内部实现，只要公开响应稳定，卷宗层无需跟随。

ConsumerPackage 已包含一个来源快照、五项 manifest、四类受众、五个章节、五条验收标准、五项只读 CI 矩阵、八个边界锁、五项清单、八项分数和九个 Markdown 章节。Dossier 不制造新的业务事实，而是把这些事实改写成评审语义。例如 provenance 从版本、端点、profile、来源摘要版本与状态、消费包状态取得；section digest 逐个检查九个 Markdown 章节是否非空；acceptance gate 直接继承五条验收标准。

测试工厂只调用 v1846 的公开测试工厂，再构造 Dossier 服务。留根控制器测试和后续 ReleaseAcceptance 测试由这个入口获取对象，不需要访问内部目录。生产边界与测试边界都保持最小公开，依赖方向从 ConsumerPackage 流向 Dossier，再流向 ReleaseAcceptance。

## 服务层核心流程

第一步读取 ConsumerPackage。第二步形成一个来源包快照，保存版本、端点、profile、消费包状态以及 manifest、章节、CI 矩阵、边界锁和交接清单数量。第三步生成六项 provenance：消费包版本、端点、profile、来源摘要版本、来源摘要状态和消费包状态。六项都是必需值，空值或空白直接 blocked。

第四步遍历上游九个 Markdown 章节，生成九个 section digest。每项保存标题、行数、必需标记和状态；标题为空或行数为零即失败。这里的 digest 是结构完整性摘要，不是密码学哈希。第五步把四类受众映射成四条 audience route，并按 packet 内容选择 CI 非 Docker 回归、operator review、archive verification 或通用只读消费评审通道。

第六步把五项 CI 矩阵映射成五条 CI lane，保留顺序、批次、命令族、只读、来源通过和 replay group。第七步把五条验收标准变成五个 acceptance gate，每个 gate 绑定一个名为“原代码加 verification-dossier”的验证制品。第八步把八个边界锁映射成边界审计，要求锁定事实与审计证据同时存在。

第九步从上游交接清单生成五项 release checklist，保留顺序、事项、负责人和发布证据。第十步生成四份 handoff receipt，接收者分别覆盖 Operator-CI 负责人、归档验证者、Operator-CI 计划和 Java 只读边界负责人；只有上游总状态通过且来源证据非空才就绪。第十一步计算十项分数卡，最后渲染十个 Markdown 章节并计算总状态。

总状态还额外检查上游所有危险能力保持关闭，并要求上游 Markdown 数恰好为九。随后要求一、六、九、四、五、五、八、五、四、十、十这些数量全部正确，且对应通过、就绪、只读、锁定数量等于总数。任何条件缺失都返回 `blocked`，不会因为大部分通过就给出模糊成功。

## Java 证据检查

文件门精确列出二十五个生产类和七个测试，要求新包存在、旧根路径不存在、包内数量完全匹配。根控制器和根 Markdown 聚合测试必须保留并导入 `operatorcidossier`。路径门要求最终包名出现，并拒绝三百零七和二百七十二字符方案对应的包段。

依赖门要求 Dossier 服务只导入 v1846 ConsumerPackage service，并使用 v1840 RoutePaths。仍在根包的十二个 ReleaseAcceptance 生产类必须导入 Dossier 的公开 service 或 response，不能引用 provenance、section、audience 等内部目录。v1846 历史门则推进到新包，继续验证 Dossier 只消费 ConsumerPackage 公开边界。

静态与数字门要求 SpotBugs 中 Dossier 响应及嵌套 Markdown 响应的四条镜像 FQN 全部转向 `operatorcidossier`，旧根 FQN 为零。全局根上限、精确根数与历史总门收紧到五百七十三；剩余非控制器为四百六十八，Operator-CI 桶为四十八，未归类为空，总 Java 文件不超过一千三百五十二。

## mini-kv 证据检查

Dossier 不启动 mini-kv、不调用 CLI、不读取 WAL 或快照、不建立 RESP 连接。`startsMiniKvService`、`executionAllowed`、凭据读取和原始 URL 解析仍为假。CI lane 只是评审证据，记录将来应在哪个通道验证哪些批次，不会在 GET 请求中执行命令。

卷宗中的四份回执也不是外部系统已签字的证明。它们表示当前来源证据是否足以交给相应接收方复核。真实 mini-kv 联调仍需独立、显式启用的跨项目套件启动真实二进制并采集新鲜输出。本版没有这些输入和输出，所以只声称 Java 单仓卷宗与边界契约一致。

从机理看，mini-kv 相关输入仍是上游只读边界；处理是把禁止启动、保持只读、先验证后接受写进 CI lane、boundary audit 和 checklist；输出是结构化条目和 blocked/passed。不会产生跨仓副作用，也不会把静态材料冒充运行证据。

## 阻断与安全边界

服务标注 `@Transactional(readOnly = true)`，所有目录只构造不可变记录。没有仓储保存、消息发布、HTTP 客户端、文件写入、进程启动、部署、回滚或审批动作。provenance 空白、章节为空、路由未就绪、CI 非只读、验收失败、边界未锁、清单未就绪、回执缺来源、分数不齐都会阻断。

工程过程也失败关闭。不能通过缩短类名改变公共类型，不能改端点，不能提高 ratchet，不能删测试，不能改 fixture，不能暴露整包内部类型。路径问题只通过包所有权缩短解决，并留下三组测量证据。ReleaseAcceptance 只获得 service/response import，本版不提前执行其接受逻辑。

作为检查点，远端门比普通中间版更严格。v1847 本地全量通过后还不算完成，必须提交、推送、等待实现 CI；写入收尾证据并推送 tag 后还要等待收尾 CI；同时核对 v1843 到 v1846 的所有运行。任一远端结论失败，都要在检查点内修复而不是交给评审猜测。

## 测试覆盖

第一次 `test-compile` 在迁移和最小 import 修复后直接通过，证明根控制器、根 Markdown 测试、ReleaseAcceptance 生产类与测试支持都能通过窄边界编译。原有七组包内测试继续覆盖来源与 provenance、章节摘要、受众与 CI、验收与边界、清单与回执、不可变性以及测试工厂。

新增 v1847 治理测试覆盖二十五加七精确清单、路径方案、上游服务、路由所有者、十二个下游消费者、SpotBugs、五百七十三根文件和讲解。聚焦套件还运行 v1846 历史门、终局 census、全局上限、维护预算和 Walkthrough 策略。讲解必须超过三千汉字、汉字占主导并包含规定标题。

最终完整 `mvnw verify` 会运行全部单元和集成测试、生产配置、架构、JaCoCo、Spotless 和 SpotBugs。通过后按两提交模式推送实现与账本，再创建注释 tag。最后清理本轮生成的 `target`，确认工作树、远端分支、tag 和 CI 证据一致，才停止等待 Claude 评审。

## 一句话总结

v1847 用路径安全的 `operatorcidossier` 收拢二十五个卷宗实现和七个测试，把 v1846 消费包转成来源可追、章节可核、受众可路由、CI 保持只读、边界可审计、回执可复核的透明卷宗，并以本地全量门和五版远端 CI 共同形成评审检查点。
