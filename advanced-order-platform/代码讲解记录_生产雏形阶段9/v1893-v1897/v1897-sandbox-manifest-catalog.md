# v1897：Sandbox 上游回执校验清单的 Catalog 收敛

## 入口路由

本版本处理的是本项目只读运维面中的一条历史证据入口：`/api/v1/ops/shard-readiness/sandbox-connection-precheck-upstream-receipt-verification-manifest`。入口由根 `ops` 包中的公开 Controller 暴露，Controller 继续依赖公开的长名 Service；这两个公开类型没有改名，是因为它们已经属于 Spring 装配边界和历史兼容边界。真正需要优化的是 Service 背后的包内实现，而不是为了短名字破坏外部引用。路由后缀仍由 `OpsShardReadinessSandboxConnectionRoutePaths` 持有，Service 的 `ENDPOINT` 仍由统一 `BASE_PATH` 与原后缀拼接，因此 HTTP 字节没有变化。

一次请求的直接输入并不是浏览器提交的命令参数，而是 `OpsEvidenceService.releaseApprovalRehearsal()` 生成的只读排练快照。Service 标有 `@Transactional(readOnly = true)`，没有 Repository 写调用、消息发布、连接创建或部署操作。请求进入后只发生四步：读取一次 rehearsal，交给 `ManifestCatalog.evidence(rehearsal)` 装配一次证据快照，让 `ManifestRenderer.render(evidence)` 生成展示段落，再由 `ManifestSupport.response(...)` 形成公开 Response。旧实现需要在 Service 中依次点名八个超长 Catalog，随后把八个列表重新展开传给 Renderer 和 Support；入口虽然可用，但读者必须横跨十二个生产文件才能知道一次请求到底做了什么。

本版保留 Controller、Service、Response、路由常量和版本 `Java v1707`，因此上游调用者不需要迁移。CodeGraph 影响检查显示运行时调用者只有公开 Controller，其他命中项是测试和已迁移历史索引；不存在业务写服务反向依赖该 Service。这个结果决定了本版可以在包内部自由收敛，却不能修改公开 FQN、endpoint、响应字段或列表顺序。入口验证既检查真实字符串，也通过完整 JSON 摘要验证整个对象图，避免出现“路由没变但内容悄悄漂移”的假兼容。

可以把这条入口理解成一张封存清单的查询窗口。它回答的是：Node 历史拆分所依据的 Java 回执是什么，哪些字段已经回显，哪些危险动作保持关闭，哪些验证门已通过，以及交接方接下来只能做什么。它不回答“现在是否允许连接生产审计库”，更不会执行连接。这个语义在 v1897 前后完全一致，优化购买的是可读性、不可变性和依赖方向，而不是新权限。

## 响应模型

公开 Response 仍是 `OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse`。它包含项目、版本、只读标志、执行标志、Node 计划版本、冻结 Java 与 mini-kv 证据版本、来源回执 schema、endpoint、profile、九个数量字段、九组列表、checks 和 status。嵌套 record 仍分别表示 `SourceReceipt`、`SplitModule`、`EvidenceReference`、`PrecheckField`、`BoundaryGuard`、`CodeHealthGate`、`VerificationGate`、`HandoffNote` 与 `MarkdownSection`。没有合并 record，也没有把领域含义压成通用键值 Map，因为那会牺牲编译期类型和字段所有权。

旧实现的完整响应在删除文件前先被序列化为属性排序的 UTF-8 JSON。实测数量向量是 `1/12/5/7/17/6/10/4/8/22`：一条来源回执、十二个拆分模块、五条证据引用、七个预检字段、十七个边界 guard、六个代码健康 gate、十个验证 gate、四条交接说明、八段 Markdown 和二十二条 checks。对应 SHA-256 是 `03541a7ae5e46684151a3829458dde56453a4acc5ff1f397ad343892fc7656e2`。测试最初故意写入全零摘要并在旧实现上失败，拿到真实摘要后冻结，再让旧实现通过；因此这个值不是重构完成后为了通过测试临时计算出来的。

新 `ManifestCatalog.Evidence` 是包内 record，只拥有前八组领域列表，不拥有 Markdown、checks 或 status。构造器对八个顶层列表逐项执行 `List.copyOf`，来源回执内部的 warnings 与 nodeVerificationActions 继续各自复制，所以 Catalog 中总共有十次防御性复制。第一次结构测试只数了八次而失败，这个失败揭示了测试假设过粗；修复后的门不再只比较一个模糊数字，而是逐个要求八个 Evidence 字段存在精确复制，同时保留两个嵌套列表复制。这样既证明生命周期边界，也不会为了迎合门而删掉安全行为。

Response 自身没有变成内部 Evidence 的别名。`ManifestSupport` 从 Evidence 读取各列表，Markdown 另外复制一次，然后用原有构造参数顺序创建公开 Response。因为 Evidence 已经不可变，Support 不再重复复制同一批顶层列表；数据只在 Catalog 边界购买一次不可变性。最终调用者仍拿到相同的公开 record、相同的嵌套类型、相同数量与相同顺序，完整 JSON 摘要在旧实现、新实现和删除旧文件之后均保持一致。

## 上游证据配置

Catalog 的唯一动态输入是 `ReleaseApprovalRehearsalResponse`。第一组来源证据读取 `managedAuditSandboxConnectionPrecheckPacketEchoReceipt()`，保留 receiptVersion、receiptDigest、Node v245 消费版本与 profile、Node v246 下一阶段版本与 profile、是否可消费、是否可做回执校验、是否可连接 managed audit、是否可视为生产审计记录，以及 warnings 和 verification actions。来源仍明确标为 `managedAuditSandboxConnectionPrecheckPacketEchoReceipt`，没有重新解释或补造上游字段。

第二组是十二个 Node 拆分模块，从 v1983 的 entrypoint boundary 到 v1994 的 entrypoint orchestration。每个模块继续携带版本、模块名、职责、公开契约是否保持、是否只消费冻结 Java v99、是否允许运行时执行。这里的数据本质是历史迁移路线图，不是 Java 当前执行计划。Catalog 把它们放在一个可连续阅读的方法中，比十二个分散调用更容易审核顺序，也能看出所有模块共同满足 `publicContractPreserved=true`、`consumesFrozenJavaV99Only=true` 和 `runtimeExecutionAllowed=false`。

第三组五条 EvidenceReference 把 Node v1983-v2002 计划、Node v245 预检包、Java v99 回执、mini-kv v108 不参与证据和 Node v247 校验报告串起来。`SOURCE_PLAN` 仍为 `Node v2002`，`NODE_OWNER_PLAN` 仍为 `Node v1983-v2002`，冻结版本仍是 `Java v99` 与 `mini-kv v108`。所有 reference 都保持 accepted 与 frozen 为真。它们的作用是说明来源和归档责任，不会触发网络读取；本项目没有去 Node 或 mini-kv 仓库打开运行时文件。

第四组七个 PrecheckField 从 rehearsal 的 fieldEcho 中逐项投影 owner approval artifact、credential handle review、schema migration rehearsal、operator window、rollback path、abort marker 和 timeout policy。每项保留 id、字段名、显示值与 echoed 标志，`carriesCredentialValue` 仍固定为 false。超时预算继续通过 `String.valueOf` 输出原数值文本。这个显式投影很重要：凭据句柄的名字可以被审核，但凭据值不能进入响应，Catalog 也没有访问凭据解析器。

配置常量现在归 `ManifestCatalog` 所有，包括项目名、计划版本、profile 与九个期望数量。Support 只读取这些定义，不反向成为 Catalog 的依赖。这个方向意味着“证据是什么、应有多少”属于数据 owner，“如何判断 passed、如何形成 checks”属于行为 owner。若未来证据集合需要变化，编译器和数量门会同时指出影响点，而不是让两个巨型类互相引用。

## 服务层核心流程

重构前 Service 有八段近乎相同的局部变量装配：分别调用 Source、Split、Reference、Field、Boundary、CodeHealth、Verification 与 Handoff Catalog；Verification 又依赖前六组列表，Renderer 和 Support 随后各接收八到九个参数。阅读者必须检查每个变量是否来自同一次 rehearsal、是否被调换顺序、是否在传递中漏项。长类型名还迫使格式化器把简单调用拆成多行，81 行 Service 的主要内容是结构噪声。

重构后 Service 为 31 行。核心只有 `var rehearsal`、`var evidence`、`var markdownSections` 和一次 response 调用。`ManifestCatalog.evidence(rehearsal)` 内先生成 source、module、reference、field、boundary 和 health，再用这六组数据生成 verification，最后加入 handoff 并构造 Evidence。这个顺序直接表达依赖图：Verification 只能在被验证的证据完成后生成；Renderer 和 Support 只能读取已经封闭的快照。结构测试要求 Service 中该装配表达式恰好出现一次，防止以后为了不同消费者重复生成不一致快照。

Catalog 格式化后为 397 行，低于本版预先写入的 400 行失败上限。第一稿曾达到 406 行，没有放宽门，而是识别出十七个 boundary guard 重复书写 `javaExecutionBoundary.` 前缀。最终 helper 接收字段名并在唯一位置拼接该前缀，输出 evidence 文本保持逐字相同，Catalog 回到 397 行。这不是通过压缩空白规避门：Spotless 已重新格式化全部变更，缩短来自真实重复概念的提取。

Renderer 也没有被动接受行数反弹。直接把 Evidence accessor 填入八次 `mapped` 后，全局 renderer 行数从 3203 增到 3209。随后提取类型安全的本地 `section` adapter，统一补入 `MarkdownSection::new`，八个领域段落仍以各自的 formatter 方法表达。最终 `ManifestRenderer` 从旧 89 行降到 71 行，全局 renderer 行数降到 3185。该 helper 有八个真实调用者，满足三次规则，不是只服务一个 case 的抽象。

`ManifestSupport` 从 351 行长名 owner 收敛为 182 行。它只负责三件事：按原字段顺序建立 Response，按原文本建立二十二条 checks，以及按原谓词计算 passed 或 blocked。Support 不再接收九个平行列表参数，而是接收一个 Evidence 和一组 Markdown。Catalog 不依赖 Support 或 Renderer，结构门会扫描源码拒绝这种反向依赖。由此形成单向流程：rehearsal 到 Evidence，到 Markdown 与状态，再到公开 Response。

## Java 证据检查

Java 侧最强证据是完整响应 oracle，而不是几个抽样断言。`ManifestResponseOracleTests` 使用 Jackson 开启属性名排序和 Map key 排序，把整个公开 Response 序列化后计算 SHA-256。它既锁定所有标量，也锁定嵌套 record 字段、列表顺序、Markdown 文本、checks 文本和 status。向量断言额外给出每组集合的尺寸，使失败时能先判断是结构数量变化还是内容变化。摘要 `03541a7a...56e2` 已在旧代码、新代码和删除旧 owner 三个阶段通过。

既有行为测试继续分工。`ManifestCatalogTests` 检查版本、Node 计划、冻结 Java/mini-kv 版本、profile、九个计数、二十二条 checks 与 passed 状态，并核对 Node v245 到 v246 的来源回执。`ManifestEvidenceTests` 锁定 v1983 到 v1994 的模块顺序和五条 reference id。`ManifestSafetyTests` 检查七个字段 id 及十七个关闭边界。`ManifestServiceTests` 检查 readOnly、executionAllowed、所有 gate 状态和列表不可修改。`SandboxManifestControllerTests` 验证真实 endpoint、Controller 返回和八个 Markdown heading。

历史结构门 `SandboxExtractionTests` 已从 v1803 的“文件搬到窄包”升级为当前结构约束。它要求 sandbox 包由五个 dossier owner、五个 manifest owner 和共享 RoutePaths 精确组成；要求 `ManifestCatalog` 小于 400 行；要求八个顶层 Evidence 字段逐项 `List.copyOf`；要求 Service 只装配一次；要求 Renderer 与 Support 都接收同一 Evidence；要求 Catalog 不引用二者；还要求九个退休长名文件在窄包与根包永久缺席。旧版只证明“搬过”，新版证明“当前抽象仍然成立”。

全局机械指标同步收紧。生产 Java 文件从 1308 降到 1301，ops 从 1176 降到 1169，Catalog 从 258 降到 251。生产长文件 stem、长标识符出现次数和唯一长名从 `1063/19545/2622` 降到 `1054/19458/2613`；测试侧从 `690/9773/3651` 降到 `685/9768/3646`。精确 baseline 删除二十八项且没有新增。Readiness 名称文件从 975 降到 966。所有 ratchet 只收紧，没有为本版提高上限。

## mini-kv 证据检查

响应中的 mini-kv 只是一条冻结的历史引用，版本仍为 `mini-kv v108`，profile 仍为 `mini-kv-non-participation-reference.v1`。Reference 的 role 明确说明它是 sibling evidence，Java 不得请求 mini-kv 写入或启动。HandoffNote 再次把受众写为 `mini-kv`，内容要求把 v108 当作不参与证据，不启动、不写入。重构没有读取 `D:\C\mini-kv` 工作区，没有修改其文件，也没有运行 `minikv_cli`。

边界 guard 中保留 `mini-kv-write-permission-requested`，实际值来自上游 rehearsal 的 JavaExecutionBoundary，期望值为 false，只有实际值为 false 才 passed。这个 guard 与 `upstream-service-auto-start-requested` 一起证明 Java 不会把归档引用误解成运行许可。Support 的 status 还要求所有 boundary guard passed；因此即使其他计数正确，只要上游快照显示请求了 mini-kv 写权限，最终状态就会变成 blocked。

为什么不在本版做跨项目实时测试？因为这条 endpoint 的合同就是历史只读 manifest，它记录 frozen reference，而不是 capstone 的实时进程协调器。主动启动 mini-kv 反而会越过该接口声明的非参与边界。跨项目运行验证有独立的 Node capstone 入口；本版本只保证 Java 输出的引用、版本、禁止动作与既有合同逐字一致。完整响应 SHA 同样覆盖 mini-kv 版本、reference、handoff 文本和 guard 状态，所以重构不能静默改掉这些限定。

这也是“关联项目以后再对齐”前提下可以自由重构的原因：本版没有更改证据 schema、route、profile 或冻结版本，只改变 Java 包内数据装配方式。Node 若继续消费该 Response，会看到相同 JSON；mini-kv 无需感知 Java 内部文件减少。若未来确实要升级 mini-kv 证据版本，应作为新的合同版本处理，不能顺手改本版 oracle。

## 阻断与安全边界

Response 顶层继续给出 `readOnly=true`、`executionAllowed=false`。十七个 BoundaryGuard 覆盖携带凭据值、Java 读取凭据、Java 存储凭据、实际连接、外部 managed audit 连接、请求 schema migration、执行 migration SQL、写 approval ledger、请求或写 managed audit state、执行任意 SQL、触发部署、触发回滚、执行恢复、请求自动启动上游服务、请求 mini-kv 写权限以及打开生产窗口。每项都保存 expected=false、actual=上游真实布尔值、passed=!actual。

十个 VerificationGate 在更高层组合这些事实。它们要求 Node 计划固定、来源回执仍连接 Node v245 与 v246、digest 仍以 `sha256:` 开头、十二个拆分模块保持公开合同、只消费冻结 Java v99、五条 reference 保持 frozen、七个字段已经回显且不携带凭据值、所有运行边界关闭、六个代码健康门通过，以及 adapter connection 与 production audit 仍被阻断。Verification 不是新的授权器，只是对已有只读证据做纯函数判断。

Support 的最终 status 又执行一次完整闭合检查。它要求所有数量等于 Catalog 的期望值，来源版本精确匹配 Node v245/v246，来源不可连接 adapter、不可用于生产审计，模块不允许运行，references accepted/frozen，fields value-free，所有边界、健康、验证和 handoff 均通过。任何一项不满足都返回 `blocked`。这个重复不是无意义复制：VerificationGate 是返回给操作者的逐项说明，status 是响应整体的 fail-closed 汇总，两者拥有不同输出职责。

本版没有触碰凭据值、原始 endpoint 解析、managed audit 连接、schema SQL、部署或回滚代码，也没有新增 Spring Bean、HTTP 客户端、数据库 Repository 或消息发布器。新增类型全部是 package-private 的纯数据装配与展示 helper。Catalog 的方法只读取 rehearsal 并创建 record/list；Renderer 只创建字符串；Support 只创建 Response。静态结构和完整测试共同证明优化没有把“证据清单”变成“执行入口”。

## 测试覆盖

测试策略按失败定位分层。第一层是 manifest 家族的行为测试和完整 response oracle，关注字节与业务语义。第二层是 `SandboxExtractionTests`，关注文件所有权、单次装配、不可变复制、依赖方向和退休 owner 永久缺席。第三层是 `JavaEleganceGateTests`、`OpsEleganceCensusTests`、`ReadabilityUpkeepGovernanceConsolidationPlanTests` 与 staged-change gate，关注全局文件数、Catalog 数、renderer 行数、名称预算、Readiness 数量和新增声明上限。第四层才是 Maven 完整 release gate，覆盖整个项目、JaCoCo、SpotBugs、Spotless 和打包。

开发过程中的失败都保留了信息价值。最初 oracle 用全零摘要在旧实现上按预期失败，给出真实 SHA；冻结后旧实现通过。第一版 Catalog 经 Spotless 后是 406 行，违反预先写明的 400 行门，随后提取 boundary evidence 前缀降到 397 行。删除旧文件后的首次结构门又因十次 `List.copyOf` 与粗略八次预期不符而失败；检查确认多出的两次是来源回执 warnings/actions 的既有嵌套防御复制，于是门被改成逐字段验证八个顶层复制并保留总数十，而不是删安全代码。

renderer 的第一次 census 也没有被忽略。把 Evidence 直接展开后全局行数从 3203 增到 3209，说明文件数虽降但展示复杂度上升。提取八调用者共享的 `section` adapter 后，家族 Renderer 从 89 降到 71 行，全局降到 3185。这个过程体现本版的判定标准：不接受“测试绿但结构更差”，也不接受仅靠修改 cap 把回归变成成功。

最终 focused 选择至少包含 `Manifest*Tests`、`SandboxManifestControllerTests`、`SandboxExtractionTests`、名称门、ops census、归档门、讲解门和文档诚实性门。完整 `scripts/verify-release.ps1` 还会把 Spotless 前驱基线固定到已发布 v1896 tag，执行所有单元与集成测试、JaCoCo 全阈值、SpotBugs 零发现和生产 jar 构建。任何真实失败都必须定位根因，不得修改旧 fixture 字节、route 文本或 oracle 摘要迁就实现。

## 实际工作量说明

本版不是把九个文件机械粘贴进一个更大的文件。旧 manifest 家族共有十二个生产文件、1124 行，其中八个单列表 Catalog 与一个 351 行长名 Support 造成生命周期分裂。新形态只有五个生产 owner、768 行：397 行 `ManifestCatalog`、182 行 `ManifestSupport`、71 行 `ManifestRenderer`、31 行公开 Service 与 87 行公开 Response。净删除七个生产文件和 356 行，同时保留清晰的“数据、状态、展示、入口、合同”五层边界。

生产全局净减七个 Java 文件，Catalog 净减七个；原因是八个旧 Catalog 被一个新 Catalog 替代。测试净增一个文件，用于购买旧实现欠缺的完整响应 oracle；与此同时五个被触及的超长测试 owner 改为短职责名，断言一条未删。精确长名 baseline 删除十四个旧文件条目和十四个旧标识符条目，共二十八项，不产生新增长名。新增 `ManifestCatalog`、`ManifestSupport`、`ManifestResponseOracleTests` 与设计文档名字都在预算内。

工作还包括 CodeGraph 上下文与影响半径审计、旧实现 response 冻结、两轮 focused 行为验证、删除后结构验证、Spotless 格式化、两次 census、全局 ratchet 更新、历史 v1803 结构门升级、归档 manifest 与精确字节预算更新、中文十章讲解、版本设计说明、CHANGELOG、证据总表和进度账本。讲解在最终 verify 前完成，避免测试结果倒灌成事后叙事。

“禁止硬凑”在本版本中的具体含义是：如果真实重构不足以解释三千汉字，就应继续扩大有价值的证据和结构工作，而不是重复结论。本篇之所以能展开，是因为本项目确实经历了输入输出冻结、依赖方向重建、不变量设计、两次结构性失败修正、renderer 复杂度回收、安全边界核对和全局 ratchet 收紧。每个章节都能落到文件、调用或机械检查，而不是泛泛赞美代码优雅。

发布仍采用三阶段纪律：实现提交经 canonical CI，通过后写 closeout；closeout 经 CI 后创建 annotated tag；tag 固定后再用纯文档 receipt 记录远端事实并等待第三次 CI。tag 必须 peel 到 closeout，不得移动到 receipt。只有这三段和本地完整门全部闭合，v1897 才能说版本生命周期完成；“coding brilliant and elegant 九分”仍只能由外部复核授予，本仓库只报告可复现指标。

## 一句话总结

v1897 把 sandbox 上游回执校验 manifest 从八个分散 Catalog 和一个长 Support 收敛为一次装配的不可变证据快照，在 endpoint、完整 JSON、只读边界与跨项目非参与合同不变的前提下，将家族从十二个文件一千一百二十四行压到五个文件七百六十八行，并用会失败的 oracle、结构门和全局 ratchet 证明这次优雅不是自述。
