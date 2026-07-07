# version-1830 production excellence operator evidence value supply adapter preflight extraction

## 实际工作量说明

本版 v1830 做的是 Java 本项目 `ops` 根包后期维护中的一刀中等偏大的拆分：把 `OperatorEvidenceValueSupplyAdapterPreflight` 这一组实现，从 `com.codexdemo.orderplatform.ops` 根包移到 `com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight`。这不是为了追求目录漂亮，而是为了把“证据值供应适配器预检”从根包里剥离出来，让根包只继续承担入口 controller 和全局路由聚合的职责。上一版结束后，根包还有 864 个 Java 文件，其中 105 个被 endgame census 明确为最终可保留项，剩下 759 个仍然是需要逐步迁走或折叠的非 controller 文件。本版完成后，根包降到 848 个 Java 文件，剩余可移动文件降到 743 个。

这刀的实际工作量不只是移动文件。第一，服务、响应模型、slot catalog、support helper 都换了包名，controller 仍留在根包，所以所有根 controller 和 controller test 都要显式 import 新包边界。第二，原来这一组没有自己的 RoutePaths owner，服务直接读根 `OpsShardReadinessRoutePaths`，迁出后不能再依赖根包内可见的聚合器，所以新增了 `OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths`，并让根聚合器委托给它。第三，为了不让总文件数因为新增 route owner 而增长，旧的 `RuleCatalog` 没有跟着搬家，而是并入 `SlotCatalog`，这样“新增一个 route owner”和“删除一个独立 rule catalog”互相抵消，总 `ops` Java 文件仍保持 1,352。第四，下游 `ApprovalPreflight` 还要读取 AdapterPreflight 的 closeout endpoint，这个入边必须改成新包 import，否则下一次清理会留下根包幽灵引用。

这里特别强调“禁止硬凑”。如果只是把文件搬到新目录，然后为了凑字说一堆泛泛而谈，后续维护者依然不知道为什么这刀有价值。本版的价值在于：根包压力真实下降；路由所有权从根包常量转成家族常量；跨包 endpoint 读写关系被显式暴露；旧 catalog 被折叠，避免“拆分一次、多一个文件”的治理反噬；而且这些结论都用测试、census 脚本、SpotBugs FQN 和文档索引绑定。

## 入口路由

对调用方来说，本版的入口没有变化。HTTP 请求仍然进入 `/api/v1/ops/shard-readiness` 下的十二个 AdapterPreflight 只读端点，例如 catalog、compatibility matrix、redaction boundary、provenance binding、missing value rejection、source evidence snapshot、payload firewall、runtime submission lock、operator rehearsal checklist、digest blueprint、archive plan、closeout。这些路径的字符串没有改，controller 的 `@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)` 也没有改。变化发生在路由常量的所有权：以前根聚合器直接持有 `"/operator-evidence-value-supply-adapter-preflight-closeout"` 这类 suffix；现在新 route owner 持有 suffix，根聚合器只做 delegation。

这层设计可以用一个简单例子理解。外部请求 closeout 时，输入仍是 `GET /api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-closeout`。Spring 仍然先命中根包里的 `OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController`。这个 controller 不搬，是因为它是 HTTP 入口，放在根包能让维护者快速看见所有 ops 入口。controller 再调用新包里的 `OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService`。服务返回的 `endpoint` 字段来自新 route owner 的 `BASE_PATH + OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CLOSEOUT`。根 route 聚合器和新 route owner 指向同一个 suffix，所以外部路径、响应 endpoint 字段、controller test 三者保持一致。

这种处理比“所有东西一起搬走”更稳。controller 如果也搬走，根包入口扫描会突然丢失一组公开 HTTP 入口，后续审查者得跨目录找 controller；route 常量如果继续留在根包，服务层就仍然被根包可见性绑住，拆包只是表面动作。现在的输入输出关系更透明：输入是固定 HTTP route；中间层是根 controller；输出是只读 response；route ownership 属于家族 route owner；根聚合器保留兼容桥。

## 响应模型

本版响应模型是 `OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse`，它随服务层一起进入新包。响应模型没有改字段，没有改 record 结构，也没有改变 slot/rule 的语义。它仍然表达“适配器预检是否只读、安全、禁止提交值、禁止执行、禁止导入”的状态。典型输出里会包含版本号、endpoint、profile、slot 列表、rule 列表、若干 ready/allowed 布尔值、计数和 status。调用方看到的是同一个 evidence packet，只是 Java 内部的 FQN 从根包变成了维护包。

为什么响应模型应该跟服务一起搬？因为它是这个家族的内部语义载体。controller 只负责把请求转到服务，不应该拥有 response 的业务含义；根 route 聚合器只负责兼容入口，不应该拥有 response 的结构；response record 跟 slot catalog、support helper、各个 service 放在同一包，维护者才能在一个局部目录里看清“这个端点返回什么、为什么这些字段只读、哪些规则让它 fail-closed”。这也会减少后续 class name 的认知负担。长类名还在，但包名已经承载了上下文，下一阶段如果做类名瘦身，就有更明确的安全边界。

本版还同步移动了 SpotBugs 的 FQN 排除项。这个响应 record 包含不可变拷贝后的列表语义，历史上有镜像 SpotBugs exclusion。如果只移动 Java 文件而不改 `config/spotbugs-exclude.xml`，全量 verify 会在后段失败，或者更糟糕的是留下旧 FQN 让审查者误以为根包仍有 response。v1830 的测试明确要求新 FQN 存在、旧 FQN 不存在，这个检查不是形式主义，它防的是“源码搬了，质量配置没搬”的半拆分状态。

## 上游证据配置

AdapterPreflight 的 slot catalog 不是孤立数据。它的 25 个 adapter slot 会引用上游 `OperatorEvidenceValueSupply` base 家族的 endpoint，例如 catalog、envelope template、redaction policy、missing value policy、provenance requirement、source evidence guard、validation matrix、side effect gate、closeout。迁包前这些 endpoint 常量在同一个根包内，包内可见就能访问；迁包后，如果还想从新包读取这些 endpoint，就必须把真正跨包使用的常量公开。

本版只公开了必要的 endpoint 常量：`CatalogService.ENDPOINT`、`MissingValuePolicyService.ENDPOINT`、`SourceEvidenceGuardService.ENDPOINT`、`SideEffectGateService.ENDPOINT`。其他已经 public 的保持不变，没有为了省事把整组类全部扩大可见性。这里的输入是已有 value-supply base evidence endpoint；输出是 AdapterPreflight response 中的 `sourceEndpoint` 字段。这个字段说明每个 adapter slot 对应哪一条上游只读证据，而不是引入真实值、凭证值或 raw endpoint。

这也是本版和未来 ValueSupply base 抽取之间的衔接点。AdapterPreflight 现在先迁出，暴露了它对 base 的读取边。下一版如果移动 `OperatorEvidenceValueSupply base`，维护者可以直接看到：哪些 base endpoint 已经跨包公开，哪些仍然只在根包内部，哪些下游包读取它们。换句话说，v1830 不是孤立拆目录，而是在为下一刀减少不确定性。

如果反过来先抽 base，再回头抽 AdapterPreflight，风险会更大。因为 AdapterPreflight 的 slot catalog 目前集中引用 base 的多个端点，它本身又被 ApprovalPreflight 读取 closeout endpoint。先抽 base 时，维护者必须同时判断“base 被谁读”“AdapterPreflight 是否还在根包”“ApprovalPreflight 的读边是否会穿过两个不同层级”。这会把一次迁移变成三角关系。v1830 先把 AdapterPreflight 固定成独立包，相当于先把中间层钉牢：向上，它只读 base 的公开 endpoint；向下，它只给 ApprovalPreflight 暴露 closeout endpoint；向外，它通过自己的 route owner 保持 endpoint 字节不变。这样下一版抽 base 时，判断标准就简单许多，只要看 base 的 public endpoint 是否仍服务于已迁出的 adapter package，以及剩余 controller 是否仍在根包提供 HTTP 入口。这个顺序上的选择，是本项目后期维护里很重要的工程判断。

## 服务层核心流程

服务层的核心流程保持读操作。每个 service 都构造一个 response：先选择 slot 范围或全部 slot，再选择 rule 范围或全部 rule，然后通过 support helper 生成不可变响应。比如 compatibility matrix 只取前四个 slot 和前四条 rule，用来说明适配器只能做 metadata-only 兼容检查；redaction boundary 取 redaction 相关 slot 和 rule，说明凭证值、raw endpoint、secret material 都不能进入预检；closeout 读取全部 slot 和全部 rule，用来收束“适配器仍禁用、提交仍禁用、导入仍禁用、运行仍禁用、生产执行仍禁用”的最终状态。

旧结构里 slot 和 rule 分在两个 catalog 文件里。这个拆法早期可以接受，但到了 endgame extraction 阶段，每新增一个 route owner 都会增加一个文件，如果不主动折叠，就会出现“根包少了，总代码文件却越来越多”的反向膨胀。本版把 18 条规则并入 `SlotCatalog`，并保留 `RULE_COUNT`、`allRules()`、`rules(from, to)` 和私有 `rule(...)` helper。服务调用点只从 `RuleCatalog` 改为 `SlotCatalog`，业务数据不变，规则顺序不变，切片语义不变。

这层输出仍是 evidence response，不是 adapter 实现。名字里有 AdapterPreflight，但它不创建 adapter，不解析真实 operator value，不接受手工输入，不连接外部端点，不启动 Java/Node/mini-kv 运行时，不做 managed audit connection，也不触发部署或回滚。服务层的作用是把“将来如果要设计适配器，必须先满足哪些只读证据和阻断条件”讲清楚。

## Java 证据检查

Java 侧的证据链分成三层。第一层是编译证据：迁包后先执行 `.\mvnw.cmd -q -DskipTests compile`，它验证 main source 的 import、包名、public endpoint 和 route owner 能被 javac 接受。这个检查已经说明核心依赖边没有断。第二层是 focused test 证据：本版新增 `ReadabilityUpkeepOpsConsolidationExtractionV1830Tests`，它会检查 docs 索引、实现文件迁移、controller 留根、route owner delegation、RuleCatalog 折叠、ApprovalPreflight 下游 import、SpotBugs FQN、base endpoint public 可见性、root count 848、total ops Java 不超过 1,352，以及本中文讲解是否存在并包含标准章节。第三层是 full verify：最终仍要跑 `.\mvnw.cmd verify`，让单元测试、JaCoCo、SpotBugs、Spotless 一起兜底。

census 也是 Java 证据的一部分。本版继续使用 `scripts/ops-root-census.ps1 -Json`，而不是人工数文件。脚本输出 848 个 direct-root Java files、105 个 retained-root files、743 个 remaining direct-root non-controller files、0 个 unassigned files，且 `OperatorEvidenceValueSupplyAdapterPreflight` bucket 为 0。这些数字写入 `docs/ops/extraction-endgame-census-v1828.md` 和 readability tests。这样下一版如果误把实现类放回根包，或者忘记更新 bucket，测试会失败。

## mini-kv 证据检查

本版没有修改 mini-kv，也不应该修改 mini-kv。mini-kv 在四项目统筹里是 Java 的上游只读证据来源之一，但 v1830 的工作属于 Java 内部维护拆分，不改变跨项目 contract、fixture、archive path 或 digest。这里提 mini-kv 的目的，是明确边界：Java 的 AdapterPreflight 只保存“将来适配器必须遵守的只读阻断规则”，不去读取 mini-kv 工作区，不启动 `minikv_cli`，不生成新的 C++ fixture，不改变 mini-kv 的归档策略。

如果未来进入真正的跨项目 capstone，mini-kv 的输出应该通过单独的 env-gated 命令读取，并生成新的联合 readiness report；但那不是本版。v1830 的输入来自 Java 仓库已有源码、测试、census 和文档；输出也是 Java 仓库内的包结构、route owner、测试和讲解。保持这个边界很重要，因为四项目协作规则要求非 contract 内部工作可以并行，但不能偷偷移动上游 archive 或改写 fixture 来让 Java 端测试通过。

## 阻断与安全边界

AdapterPreflight 这个名字容易让人误会为“马上要做 active adapter”，所以本版特别强调阻断边界。所有 endpoint 仍是 read-only evidence。它们不打开 write routing，不打开 active shard router，不接收 credential value，不解析 raw endpoint，不建立 managed audit connection，不做 deployment/rollback，不启动 Node，也不启动 mini-kv。响应里的 `readyForAdapterImplementation`、`readyForOperatorValueSubmission`、`readyForEvidenceImport`、`readyForLiveExecution`、`readyForProductionExecution` 等布尔值仍然用于表达禁用状态，而不是给执行系统放行。

本版公开的 endpoint 常量也不是权限扩大。公开字符串常量只是让另一个 Java 包可以引用“只读证据端点的位置”。它不会暴露凭证，不会暴露真实端点值，不会绕过 controller，不会让服务变成写路径。测试里把 public endpoint 的范围收得很窄，就是为了避免维护者以后把“为了迁包公开常量”误解成“这组功能可以运行了”。一句话：这刀是在清理结构，不是在提前打开执行。

从维护经验看，越到工程后期，越不能把“能跑”当成“好维护”。很多问题不是一次提交立刻爆炸，而是三四版之后才让人看不懂边界：某个类还留在根包，某个测试还引用旧名字，某个文档还写着旧数字，某个配置还指向旧响应模型。到那时再补，就会分不清是业务缺陷、测试缺陷还是治理缺陷。本版把这些小边界一起收紧，是为了让下一位维护者打开目录时能自然形成判断：入口在根包，证据在家族包，路由有家族所有者，规则和插槽同处一地，下游读取只经过公开只读常量。这样读代码的人不需要靠猜，也不需要翻十几个历史版本才能理解为什么这组接口只能读不能写。

还有一个容易被忽略的点：阻断边界本身也需要维护。只要项目继续演进，总会有人想把预检结果接到真正的执行链路上，或者把只读证据当成已经授权的配置。若文档和测试没有反复说明“这里还不是执行入口”，未来改动就可能绕过审批、绕过凭证遮蔽、绕过来源证明。本版把禁止提交、禁止导入、禁止执行、禁止生产放在服务响应、规则目录、讲解文档和测试断言里同时出现，就是要让这个边界变成多层证据，而不是某个人脑中的口头约定。

从后期保养角度看，这类拆分最怕两种偏差。第一种偏差是只追求数字好看，把文件搬走却没有说明调用关系，结果根包数字下降了，维护者反而更难判断入口在哪里、证据在哪里、规则在哪里。第二种偏差是只追求功能推进，一边说未来要开放适配器，一边把预检、审批、导入、执行几个概念混在同一层里，最后谁也说不清哪个检查只是说明书，哪个检查已经具有放行能力。本版避免这两种偏差：入口继续留在根包，证据实现进入窄包，规则和插槽合并在同一个目录，向上读取基础证据，向下只暴露收尾端点，所有执行性能力继续关闭。

这件事的价值不在于多一个目录，而在于让维护者能用很短的路径完成判断。打开根包，能看到还有两个公开入口；进入维护包，能看到完整的只读证据模型；查看收尾服务，能看到所有禁用条件仍然成立；查看统计脚本，能看到这刀确实减少了根包压力；查看讲解，能知道为什么先抽预检再抽基础供应。这样的结构会降低未来改错概率。一个项目到了这个阶段，真正昂贵的不是写一段新代码，而是每次修改都要重新确认旧承诺有没有被破坏。本版把旧承诺重新固定在代码、测试和文档里，后续版本就可以在更清楚的地基上继续前进。

还要说明，中文讲解本身也是维护资产。它不是提交后的装饰，也不是为了满足字数而附带的说明。后续有人检查这一版时，可以直接从这里看到输入是既有只读证据和根路由，输出是迁包后的服务、响应、路由所有者、统计数字和测试门；也可以看到哪些事情没有做，例如没有打开写路由、没有连接外部系统、没有改动其他项目、没有把历史归档挪位置。这样，讲解就能帮助人判断版本边界，而不是只重复提交标题。

后续维护时可以沿着同一个判断顺序继续做。先确认本版留下的入口是否仍然只负责分发请求，再确认维护包里的证据是否仍然只描述状态，然后确认统计脚本是否仍然能复现根包数量，最后确认讲解和测试是否仍然说同一件事。只要这四步一致，项目就不会因为连续拆分而丢失方向。若其中任何一步出现矛盾，例如文档说已经迁走而根包还存在实现类，或者测试说禁止执行而服务响应暗示可以执行，就应该优先停下来修证据，而不是继续推进下一版。

这种校准也会保护评审效率。评审者不必重新翻完整历史，只要看入口、证据、统计、讲解四处是否互相吻合，就能判断本版是否可信。可信之后再谈下一刀，才不会把旧问题带进新版本。

因此，本版的解释重点始终围绕维护判断，而不是围绕提交清单。清单告诉人改了什么，维护判断告诉人为什么这样改、怎样复查、哪些边界不能越过。

复查时只要记住一点：凡是不能用证据复现的结论，都不应该写成完成事实。

证据越清楚，后续修改越安心。
边界越明确，团队协作越稳。
维护先看证据。

## 测试覆盖

本版测试覆盖遵循计划书的顺序：讲解先写，再跑 focused gate，再跑 full verify。focused gate 预计包含 AdapterPreflight 自身的 service/catalog/support/controller/route tests、ApprovalPreflight 的下游 import 相关 tests、v1830 readability tests、endgame census tests、walkthrough compliance tests，以及 Spotless check。full verify 再覆盖全仓库单元测试、JaCoCo coverage、SpotBugs 和格式检查。

更具体地说，AdapterPreflight 自身测试会保护每个 endpoint 的版本号、endpoint 字段、slotCount、ruleCount、status 和关键布尔值；route paths test 会保护 root aggregator 与服务 endpoint 的一致性；readability test 会保护这次拆分的工程性质；census test 会保护根包数字；SpotBugs 配置会保护 response FQN；中文 walkthrough compliance 会保护代码讲解不是空泛补丁。这样的组合比单独跑一个服务测试更可靠，因为它把“能编译”“能返回响应”“没有漏旧包名”“数字真的下降”“文档说的是事实”放在同一条验收链里。

还要注意，本版没有把测试期望改成迁移想要的样子来绕过验证。比如 root count 的 848 来自实际 census 脚本，不是先写一个理想数字再强迫代码贴合；AdapterPreflight bucket 为 0 来自文件真实离开根目录，不是通过排除规则把它藏掉；中文讲解的长度要求也不是为了堆字，而是要求每一版必须解释清楚输入、输出、边界和验收。只有当这些检查都能独立复现，这个版本才算对维护者真正有用。

## 一句话总结

v1830 的核心价值是：在不改变任何外部路由、响应语义、写边界、凭证边界、部署回滚边界和跨项目 archive 的前提下，把 AdapterPreflight 从 Java 本项目根 `ops` 包中拆成独立维护包，顺手折叠一个旧 catalog 来抵消 route owner 文件增长，并用 census、测试、SpotBugs、文档和中文讲解把这刀锁成可复查的工程事实。
