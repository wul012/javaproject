# v1876 Release-Acceptance Renderer 收敛代码讲解

## 入口路由

本版本处理的是“验证档案已经生成以后，Java 如何给发布评审者整理一份只读验收报告”这一段，而不是新增业务接口。外部入口仍由根包中的 `OpsShardReadiness...ReleaseAcceptanceRegistryController` 持有，控制器继续绑定原来的 ReleaseAcceptance 路径常量，并把请求直接交给 `ciaccept` 包里的公开 RegistryService。输入是一条普通 GET 请求，没有请求体、没有凭据值、没有运行命令；输出仍是原来的 RegistryResponse JSON。换句话说，调用者看见的 URL、HTTP 方法、响应字段和状态码都没有变化，变化只发生在服务内部最后一步“怎样把十组结构化记录转成 Markdown section”。

从依赖方向看，这个入口位于一条清楚的只读链路中：上游 verification dossier service 先汇总 consumer package、来源、摘要、CI lane 与边界审计；release-acceptance service 再把这些事实解释成发布门、证据链、签收 lane、重放决策和收尾检查点；下游 `ciarc` archive service 继续读取 release-acceptance 的公开 service，形成归档清单。v1876 没有把上游对象改成文件路径，也没有让下游越过 service 直接读内部 Catalog。控制器仍是 Web 边界，公开 service 仍是包间边界，`ReportRenderer` 只是包内实现细节。

用一个通俗例子说明：调用者访问原路径后，首先得到 `version=Java v1502`、来源 dossier 版本、十类列表、十段 Markdown、检查项以及 `executionAllowed=false`。过去这十段 Markdown 分别经过十个长名字 Renderer，再由一个聚合 Renderer 拼接；现在仍返回完全相同的十段，只是由一个 208 行的短名 `ReportRenderer` 统一描述。入口没有换门牌，快递内容没有换，改变的是仓库内部装箱台的组织方式。

## 响应模型

RegistryResponse 继续拥有全部公开数据定义。它包含 `SourceDossierSnapshot`、`ReleaseReadinessGate`、`EvidenceChainEntry`、`SignoffLane`、`CiReplayLane`、`BoundaryControl`、`RetentionPolicy`、`ReplayDecision`、`CloseoutCheckpoint`、`ScorecardEntry` 和 `MarkdownSection` 等 record。每个 record 都表达业务含义，Renderer 不创造也不修正这些事实。例如 readiness gate 的 `expected`、`actual` 和 `evidence` 来自 ReadinessCatalog；Renderer 只把它们按原格式写成 `code | expected=... | actual=... | evidence=... | status=...`。

本版最重要的响应约束不是“字段大致相同”，而是“每个字符所在的位置都相同”。旧实现实际产生十个标题和 56 条内容行：一条 Source Dossier、六条 Readiness Gates、六条 Evidence Chain、四条 Signoff Lanes、五条 CI Replay Lanes、八条 Boundary Controls、五条 Retention Policies、五条 Replay Decisions、六条 Closeout Checkpoints 和十条 Scorecard。新测试直接构造十个预期 `MarkdownSection`，使用 `containsExactly` 比较 record 列表，因此标题大小写、section 顺序、条目顺序、分隔符、字段标签、布尔值文本或状态文本只要有一个字符变化，测试都会失败。

`MarkdownSection` 的 lines 仍由 response record 自己执行不可变快照。共享 `MarkdownSections.mapped` 会先把传入 entries 映射为新的字符串列表，再把标题和列表交给 `MarkdownSection::new`。它不增加类似 `count=5` 的首行，因为这条 release-acceptance 旧协议从来没有计数首行。这里特意复用 v1875 已验证的 `mapped`，没有新建第二套 helper，也没有错误套用用于 consumer-package 的 `counted`。数据拥有者、文本映射者和不可变边界因此各司其职。

## 上游证据配置

ReleaseAcceptanceRegistryService 的唯一构造依赖仍是 verification dossier RegistryService。调用 `registry()` 时，它先读取一份 dossier 响应，然后依次调用十个 Catalog：SourceDossierCatalog 生成来源快照，ReadinessCatalog 比较预期与实际，EvidenceChainCatalog 记录从 consumer package 到 dossier 的来源，SignoffLaneCatalog 列出接收人与责任人，CiReplayCatalog 生成只读回归顺序，BoundaryControlCatalog 固化禁区，RetentionPolicyCatalog 声明保留窗口，ReplayDecisionCatalog 描述重放决策，CloseoutCatalog 给出收尾步骤，ScorecardCatalog 汇总数量是否吻合。

这些 Catalog 是输入到输出之间真正的业务计算层，本版没有改动它们。以 CI Replay Lanes 为例，输入是 dossier 中已经存在的 CI lane 事实，输出是五个带 order、batch、commandFamily、replayGroup、readOnly 和 status 的 record；新 Renderer 只是读取 accessor，并继续输出 `1. archive-verification-registry | command=focused | replay=focused-preflight | readOnly=true | status=passed`。如果 Catalog 将来合法改变条目，业务测试应先明确新合同，而不是让 Renderer 猜测。

配置来源也没有变。路径仍由 `OpsShardReadinessReleaseAcceptanceRoutePaths` 拥有，版本和 profile 仍由 service 的常量声明，上游版本仍从 dossier response 获取。Node 和 mini-kv 的名称只作为只读证据文字出现；Java 不读取它们的进程状态，不解析 credential value，不连接 managed audit 服务。本版的输入可以概括为“一个内存中的、已验证的 dossier 响应”，输出是“一个内存中的、不可变的 release-acceptance 响应”，中间没有网络副作用与文件副作用。

## 服务层核心流程

核心流程可以按五步理解。第一步，service 调用上游 `sourceDossierService.registry()`，得到来源事实。第二步，十个 Catalog 分别把来源事实变成类型明确的列表。第三步，ScorecardCatalog 同时接收来源和前九组列表，核对数量与状态。第四步，`ReportRenderer.render(...)` 接收十组已经算好的列表，按固定顺序调用十个私有方法。第五步，RegistrySupport 把版本、路径、profile、原始列表、Markdown 列表和 checks 组装成最终 response。

过去第四步被拆成十二个形状文件：一个 RegistryRenderer 只负责 `List.of(...)`，十个 section Renderer 各有一个 `render`，一个 RendererSupport 只负责 `new MarkdownSection`。这些文件没有独立策略、没有替换点、没有状态，也没有可复用生命周期；第三个相似文件出现后继续复制，最终形成十一条超长类名和 431 行分散代码。阅读者若要确认 section 顺序，必须先打开聚合器，再跳十个文件；若要确认所有字段标签一致，还要来回比较十段近似模板。

现在第四步集中在 208 行 `ReportRenderer`。集中并不等于堆成巨型文件：公开入口只有一个短 `render`，十个私有方法各处理一种 record，通用循环与不可变构造由 `MarkdownSections.mapped` 承担。方法名 `sourceDossiers`、`readinessGates`、`evidenceChain` 等直接对应响应概念，文件名和新增标识符都不超过 40 字符。读者从顶部的 `List.of` 一眼看到顺序，再向下查看任一 typed mapper；无需在十二个文件间切换，也不会把业务字段塞入字符串键值表。

## Java 证据检查

Java 侧先做了“旧实现自证”。新增 `ReleaseAcceptanceMarkdownTests` 后，第一次运行只打印旧实现真实输出，用它核对十个 section 和 56 条内容行；随后立即删除打印逻辑，改成显式 record 断言，并在任何生产文件删除前运行通过 `1/1`。这样预期不是根据新代码反推，也不是迁移失败后修改出来的。生产替换完成后，同一个 oracle 再次通过，证明新旧 Renderer 对可观察输出等价。

结构证据同样是机械的。v1848 历史测试仍保存当年从根包迁入 `ciaccept` 的证据，但新增当前文件清单、删除清单与短名测试清单：包内生产 Java 必须不超过 14 个，只能存在 `ReportRenderer.java`；十二个旧 Renderer/Support 在目标包和根包都必须不存在；测试包不超过八个，并必须包含 `ReleaseAcceptanceMarkdownTests` 与 `ReleaseAcceptanceTestData`。全局 ops 上限从 1325 收紧到 1314，任何把这些壳文件加回来的提交都会失败。

census 给出另一组可复现输入输出。输入是 `src/main/java/.../ops` 下全部 Java 文件，脚本按后缀识别 Renderer、Catalog 和 Service，并计算文件数、行数、长文件名和热点。v1876 的输出是 ops 1314、Renderer 86 个/4586 行/80 个长名、目标家族 14 个；Catalog 332、Service 375、超过 500 行文件 32、最大文件 738，均未上升。长名 census 还记录生产 `1254/20929/2813`、测试 `791/10171/3829`，并重生成只减不增的 identity baseline。

## mini-kv 证据检查

本节名称来自四项目统一讲解模板，但 v1876 没有修改或启动 mini-kv。Release-acceptance 报告中的 `no-mini-kv-autostart` 和 `no-mini-kv-write-admin` 是边界证据，不是执行指令。输入来自上游 dossier 已经归档的 boundary audit；Java 只把 `lockedBehavior`、`auditEvidence` 和 `status` 映射为文本。例如输出继续明确 `Node must not start mini-kv` 以及 write/admin command 保持禁止。

为什么 Renderer 重构仍需要讲 mini-kv？因为文本本身是跨项目消费者可能读取的合同。如果一次“纯重构”误删 `audit=`、调换 section 顺序，或把 `readOnly=true` 写成别的形式，Node 的只读汇总或人工评审可能把边界证据解释错。因此本版虽然不触碰 C++ 仓库，仍用精确 oracle 锁住包含 mini-kv 的每一条边界文本，并运行下游 archive 测试，证明归档层仍能消费相同 release-acceptance service。

这里的透明机理是：mini-kv 不向本版提供实时输入，Java 也不向 mini-kv 发出输出；二者之间只有已经进入 dossier 的只读证据引用。v1876 既不声称联合运行，也不把 fixture 对齐冒充实时集成。真实的跨项目运行由 env-gated capstone 负责，本版只保证 Java 单仓内这段只读报告的结构与字节级文本没有因重构漂移。

## 阻断与安全边界

本版明确保留八个 Boundary Controls：禁止 Java 自动启动、禁止 mini-kv 自动启动、禁止写路由、禁止读取 credential value、禁止解析 raw endpoint URL、禁止 managed audit HTTP/TCP、禁止 runtime shell、禁止 mini-kv write/admin。它们的业务值来自 Catalog，Renderer 不能把 `blocked` 修成 `passed`，也不能根据本地环境动态放宽。`executionAllowed` 仍为 false，service 仍有 `@Transactional(readOnly = true)`，没有新增 controller、POST/PUT/DELETE 路由或外部连接。

共享引擎也按最小权限设计：`mapped` 只接收 heading、entries、纯 line mapper 和 section factory。它不知道 credential、endpoint、部署或回滚是什么，也没有反射和字符串字段查找；因此不能越权读取未传入的数据。`ReportRenderer` 是 package-private，十个映射方法是 private，外部包只能继续依赖公开 RegistryService/Response。下游 archive 测试仍从公开 service 构建输入，未因“方便测试”而把 Renderer 或 Catalog 暴露为 public。

失败策略已经写进版本设计：任一标题、行文本、顺序或不可变语义变化，整版失败；修改 oracle、fixture 或 Catalog 来迁就新实现，整版失败；`ReportRenderer` 超过 300 行或出现第二套 section engine，整版失败；renderer、长名、文件数、热点或 SpotBugs 豁免上升，整版失败。这里禁止硬凑测试绿色，也禁止用一篇说明替代可失败的门。

## 测试覆盖

测试分四圈。第一圈是最窄 oracle：旧实现 `1/1`，新实现仍 `1/1`，覆盖全部十段和 56 行。第二圈是家族行为：Source/Readiness、Evidence/Signoff、CI/Boundary、Retention/Replay、Closeout/Scorecard、Immutability 六组测试，加根控制器标题与 checks 测试。第三圈是下游消费者：`ciarc` 的 source manifest、route/operator、retention/closeout、CI boundary、immutability 与根 archive controller 测试，证明服务边界未断。第四圈是工程门：v1847/v1848 历史结构、v1866 全局 ops、OpsElegance、JavaElegance、JavaChange 和共享 Markdown engine。

截至最终 verify 之前，生产替换与上下游聚焦选择通过 `29/29`；加入结构、census 与长名门后通过 `62/62`。这些数字不是全量结果的替代品，只说明局部故障面已经被快速覆盖。最终还必须在本讲解完成、归档 manifest 更新后运行 `mvnw -B verify`，让全部测试、JaCoCo、SpotBugs、Spotless 和 jar 打包重新从干净编译链路执行。远端还要分别验证 implementation commit 与 closeout commit 的 Docker/headless jobs，最后 annotated tag 才能闭环。

覆盖选择也刻意避免“只测自己的实现”。如果只运行 `ReleaseAcceptanceMarkdownTests`，可能漏掉 RegistrySupport checks 或 archive service 的编译边界；如果只运行全量测试而没有精确 oracle，失败时又难以判断哪一行漂移。因此本版先用窄测试确定文本等价，再用中圈测试确定上下游契约，最后用全量门确定系统性质量。每层都有明确输入、输出和失败定位。

## 实际工作量说明

生产侧删除十二个旧形状文件，新增一个 208 行短名 Renderer，并仅修改 service 的最终调用点；从 431 行旧 Renderer 代码收敛到 208 行，净减少 223 行 Renderer。测试侧新增 56 行精确内容的 oracle，将超长 RegistryTestSupport 改成 `ReleaseAcceptanceTestData`，同步根控制器和下游 archive fixture。治理侧更新 v1848 当前结构门、v1847/v1866 全局上限、ops census 脚本、优雅度测试、长名 identity baseline、版本设计、CHANGELOG、进度与最终证据，再新增本篇中文讲解及归档 manifest。

工作量的价值不只是删除文件。过去每增加一个 section，维护者往往复制 Renderer、Support 调用和长类名；现在新增 section 需要在 response/Catalog 明确业务数据，在 `ReportRenderer` 增加一个 typed mapper，并在 oracle 中明确输出，三次规则已由共享 engine 消化。查看 section 顺序从跨十一文件跳转变成阅读一个 `List.of`，定位某字段格式从全文搜索超长类名变成进入对应私有方法，测试构造也从长达一百多个字符的支持类变成短、稳定的 test data 边界。

本项目仍有大量存量长名、Catalog 和 Service，这一版不宣称“已经完美”。它做的是一刀到肉地清除一个完整重复家族，并把下降转成不可回退的机械门。它没有通过把所有逻辑塞进一个 700 行类来换文件数，也没有抽象成失去类型信息的通用 Map 渲染器；208 行处在可读、可导航和可扩展之间。后续可以沿依赖链处理 `ciarc`，但必须重新建立自己的旧输出 oracle，不能把 v1876 的成功当成自动证明。

## 一句话总结

v1876 在不改变任何路由、响应、Catalog、只读事务或权限边界的前提下，用一个 typed `ReportRenderer` 和既有 `MarkdownSections.mapped` 替代十二个一次性渲染形状文件，并以旧实现先通过的十段 56 行 oracle、上下游回归、结构 ratchet、长名 baseline 和全量门证明输出不变、维护成本实质下降。
