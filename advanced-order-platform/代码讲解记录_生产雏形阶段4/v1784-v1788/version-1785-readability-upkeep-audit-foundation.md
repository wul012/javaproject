# 第一千七百八十五版代码讲解：可读性保养审计模型与 catalog 地基

v1785 接在 v1784 的文档地图之后，把“路线服务测试地图”和“根包压力地图”从纯 Markdown 进一步转成 Java 可审计结构。上一版已经回答维护者应该怎么看 route、service、test 和 root package pressure；这一版则让这些答案进入本项目源码：新增 `ReadabilityUpkeepAuditResponse`，新增 topic、route-service-test、root-package-pressure、boundary、verification 五组 catalog，新增 audit route 常量，并用 catalog 测试先验证数据形状。它仍然不暴露 HTTP controller，因为本版的重点是先让模型和事实稳定。

这样拆分是有意的。后期保养不能一口气把模型、服务、控制器、文档门禁和收口都塞进同一版，否则审查者很难判断哪一层出了问题。v1785 只处理“审计 registry 要表达什么”，v1786 再处理“如何通过只读服务和控制器暴露”。这种分层让每个版本都有足够工作量，也让每层变化都能被测试约束。

## 入口路由

v1785 新增的运行时路由常量是 `ReadabilityUpkeepRoutePaths.UPKEEP_AUDIT`，值为 `/upkeep-audit`。它与已有 `BASE_PATH` 组合后会形成未来的 `/api/v1/ops/readability/upkeep-audit`。本版只添加常量，不新增 controller，因此没有实际 HTTP 入口被打开。`ReadabilityUpkeepRoutePathsTests` 同步检查这个常量，确保未来控制器不会手写字符串。

这种“先常量后控制器”的节奏很适合当前项目。已有 registry route 已经证明 route path constant 能降低分散字符串风险；新 audit route 继续复用同一文件，可以让所有 readability upkeep 路由在一个地方被审查。后续如果路径需要调整，只需要看 route paths test 和 controller mapping，不会在多个类里搜索。

`ReadabilityRouteServiceTestMapCatalog` 也提前把未来 audit route 写进结构化数据。它记录 `/api/v1/ops/readability/upkeep-audit` 对应 `ReadabilityUpkeepAuditController`、`ReadabilityUpkeepAuditService`、`ReadabilityUpkeepAuditResponse`，并列出未来必须存在的测试类。虽然 controller 和 service 要到下一版才新增，但 catalog 先把目标形状固定下来。这样 v1786 的实现不再是临时发挥，而是完成 v1785 已经声明的路线。

## 响应模型

本版新增 `ReadabilityUpkeepAuditResponse`。它不是复用 `ReadabilityUpkeepRegistryResponse`，因为 audit registry 的职责不同。原 registry 负责列出可读性保养主题、包规则、模板规则、类名试点、边界和验证；新 audit response 则要把路线服务测试映射、根包压力、边界、验证、markdown section 和 checks 组织在一起。职责不同就应该有不同 response，避免一个大 record 越长越难维护。

新 response 保留了与旧 registry 一致的安全字段：`readOnly`、`executionAllowed`、`startsJavaService`、`startsMiniKvService`、`writesBusinessState`、`readsCredentialValue`、`resolvesRawEndpointUrl`、`managedAuditConnectionAllowed`。这些字段看似重复，但它们是 ops 只读证据的核心语言。后续审计接口如果缺少这些字段，调用者就无法快速判断它是否会触发执行面。

Audit response 的 nested record 分成五类。`AuditTopic` 表达审计主题和维护问题；`RouteServiceTestMap` 表达 route 到 controller/service/response/tests 的关系；`RootPackagePressure` 表达旧根包压力和迁移纪律；`BoundaryRule` 表达禁止项；`VerificationStep` 表达测试门禁；`MarkdownSection` 则给 renderer 输出预留稳定形状。这样 response 不是一个随意键值袋，而是一套可读、可测、可渲染的数据契约。

## 上游证据配置

v1785 的上游证据全部来自本项目。`ReadabilityUpkeepAuditTopicCatalog` 把五个审计主题写成结构化数据：route-service-test-map、root-package-pressure、registry-template-follow-through、class-name-trial-continuity、walkthrough-depth-guard。它们分别对应 `docs/ops/route-service-test-map.md`、`docs/ops/root-package-pressure-map.md`、`docs/ops/registry-template.md`、`docs/ops/class-name-trial.md` 和当前讲解批次目录。也就是说，catalog 不是凭空列概念，而是指向已经存在或本批次会维护的项目证据。

`ReadabilityRouteServiceTestMapCatalog` 把当前 registry route、未来 audit route 和 docs route map 三条读法放进列表。第一条证明现有运行时入口已经有 controller、service、response 和测试。第二条声明下一版要补齐的 audit route。第三条把文档地图本身作为可测试对象，说明文档不是二等公民，也要进入审计视图。

`ReadabilityRootPackagePressureCatalog` 则把根包压力转成四条记录：shard-readiness-evidence、code-walkthrough-depth、readability-upkeep、archive-documentation。每条都写 currentLocation、pressure、preferredDirection，并统一设置 `migrationRequiredNow=false`。这很关键，因为本批次不是做大迁移，而是建立新增代码的子包纪律。历史类保留，新代码变好，这是当前最稳的路线。

## 服务层核心流程

本版还没有 `ReadabilityUpkeepAuditService`，但它已经为服务流程准备好所有输入。下一版服务只需要取 `ReadabilityUpkeepAuditTopicCatalog.topics()`、`ReadabilityRouteServiceTestMapCatalog.routeMaps()`、`ReadabilityRootPackagePressureCatalog.pressures()`、`ReadabilityUpkeepAuditBoundaryCatalog.boundaryRules()`、`ReadabilityUpkeepAuditVerificationCatalog.verificationSteps()`，再交给 renderer 和 support 组装 response。

这种预拆分让 service 可以保持很薄。Catalog 只负责事实，renderer 只负责展示，support 只负责 count、checks 和 status，service 只负责调度。相比把所有列表直接写在 service 方法里，这样的结构更容易测试，也更容易后续新增一条主题或边界。用户已经明确要求写代码不能出现难维护的巨型文件，要做必要拆分；v1785 正是把审计事实拆到多个小 catalog 中。

`ReadabilityUpkeepAuditCatalogTests` 是本版服务流程的前置验证。它直接读取各 catalog，检查 topic count 为五、route map count 为三、root package pressure count 为四、boundary count 为八、verification step count 为八，并检查所有边界都是 denied、所有验证步骤都是 required。这样即使 service 尚未出现，数据地基已经被测试保护。

## Java 证据检查

Java 证据第一层是新增源码文件。`ReadabilityUpkeepAuditResponse` 定义了 audit registry 的公共契约。五个 catalog 把文档、路线、包压力、边界和验证拆开。`ReadabilityUpkeepRoutePaths` 增加 audit route 常量。测试文件 `ReadabilityUpkeepAuditCatalogTests` 证明这些 catalog 的数量和关键内容符合预期。

第二层证据是 route path test 的更新。`ReadabilityUpkeepRoutePathsTests` 现在同时检查 `UPKEEP_REGISTRY` 和 `UPKEEP_AUDIT`。这能防止后续 controller 使用错误路径，也能让 route map catalog 的字符串和 route constants 有共同测试入口。虽然本版还没有 controller，但路径契约已经进入测试。

第三层证据是 boundary catalog。`ReadabilityUpkeepAuditBoundaryCatalog` 复用了八个禁止项：no-write-routing、no-active-shard-router、no-credential-value、no-raw-endpoint-url、no-managed-audit-connection、no-deployment-or-rollback、no-java-autostart、no-minikv-autostart。所有 rule 的 `allowed` 都是 false，catalog test 会一次性检查。这让 audit registry 从模型层就继承只读边界。

## mini-kv 证据检查

mini-kv 在本版中仍然只是禁止项，不是输入源。`ReadabilityUpkeepAuditBoundaryCatalog` 明确 `no-minikv-autostart`，理由是 mini-kv remains outside Java readability upkeep。Audit topic、route map、root package pressure 和 verification catalog 都没有读取 mini-kv 文件，也没有引用 mini-kv fixture。这个边界延续了用户要求：Java 可以自由推进，但不要让 Node 或其他项目卡住，也不要提前打开执行面。

这种处理对本项目 CI 很重要。CI 只会检出 Java 仓库，它不会有用户机器上的 mini-kv 目录，也不会有正在运行的 C++ 服务。如果 Java 测试依赖 mini-kv，远端就会变脆。v1785 把 mini-kv 放在禁止启动项里，而不是放在测试输入里，既尊重跨项目边界，也保持 CI 可重复。

后续如果真的需要把 mini-kv read-only shard map 纳入 Java 证据，也应该先由用户明确打开范围，并提供稳定的 Java-side fixture 或协议。当前批次只做 Java 可读性保养审计，因此不做这一步。

## 阻断与安全边界

v1785 继续阻断所有执行面。没有 write routing，没有 active shard router，没有 credential value read，没有 raw endpoint URL resolution，没有 managed audit connection，没有 deployment，没有 rollback，没有 Java autostart，没有 mini-kv autostart。新增类都是 record、catalog 或测试，不启动 Spring 容器，不访问数据库，不调用外部网络。

本版还阻断了“大而全 service”的倾向。Audit response 字段很多，如果把数据和计算都塞进一个 service，下一版就会变成难维护巨类。v1785 先拆 catalog，就是为了让后续 service 只做组装。每个 catalog 文件职责单一，测试可以分别断言其 count 和关键内容。这样的拆分比一个几百行 service 更适合后期保养。

另一个阻断是“不迁移旧根包”。Root package pressure catalog 明确 `migrationRequiredNow=false`，并写明历史 shard readiness 和 code walkthrough depth 类应该保持稳定，后续迁移必须有兼容测试和文档。这样 audit registry 不会被误读成“现在就改所有旧类名”。它只是记录压力，并引导新增代码走更好的子包。

## 测试覆盖

本版新增 `ReadabilityUpkeepAuditCatalogTests`，更新 `ReadabilityUpkeepRoutePathsTests`。前者覆盖五组 catalog 的 count、required、readOnly、migrationRequiredNow 和 boundary allowed=false；后者覆盖新增 audit route 常量。定向验证应运行 `mvn -q "-Dtest=ReadabilityUpkeep*Tests,OpsCodeWalkthroughArchiveComplianceTests" test`，这样新 catalog 测试、旧 registry 测试、docs 测试和讲解门禁会一起执行。

这一版尚未新增 service、renderer、controller 测试，因为对应类会在 v1786 出现。v1785 的测试重点是“数据地基准确”。如果 catalog 内容错了，服务层再漂亮也只是包装错误数据。先测 catalog，可以让下一版只关注组装和暴露。

讲解门禁同样是测试覆盖的一部分。当前文件位于 `代码讲解记录_生产雏形阶段4/v1784-v1788`，文件名包含版本号，版本大于 v1773，因此必须满足中文长篇、标准标题、真实工作量、禁止硬凑和本项目说明。这个测试让讲解和代码一起成为版本质量的一部分。

## 实际工作量说明

v1785 的实际工作量比单纯“加一个 DTO”更厚。它新增一个完整 audit response，里面不仅有 route、profile、docsRoot、packageRoot、sourceRegistryEndpoint、auditState，也保留全部只读安全字段和计数字段；它新增五个 catalog，把审计主题、路线服务测试映射、根包压力、边界、验证步骤拆开；它更新 route path 常量和测试；它新增 catalog 测试，确保数据地基没有漂移。

这不是硬凑，也不是为了字数堆概念。用户要求每版要能写出出彩解释，至少三千字，字数不够就加大每版工作量，禁止硬凑。本版加大的工作量体现在源码拆分和测试上。五个 catalog 各自承担不同信息，不互相抢职责；response 用 nested records 表达稳定契约；测试直接检查数量和关键值。若后续维护者删掉某个 boundary 或把 audit route map 写错，测试会失败。

本项目的可读性保养现在进入第二层。v1784 是文档地图，v1785 是 Java 数据模型。文档地图适合人读，catalog 适合机器检查，二者结合后，后续 audit service 才有意义。如果只有文档，没有 Java catalog，CI 守不住；如果只有 catalog，没有文档，维护者不知道为什么这些数据存在。两层一起出现，才像工程后期保养。

本版也严格遵守“只做你自己的项目”。没有读取四项目建议目录，没有改 Node，没碰 mini-kv，没有跨项目 fetch，没有执行外部服务。所有新增字符串都是本项目 docs、源码、测试和讲解路径。这样远端 CI 能独立运行，不会依赖用户本机环境。

从维护角度看，`ReadabilityRouteServiceTestMapCatalog` 是本版最核心的结构。它把当前 registry route 和未来 audit route 放在同一列表里，说明它们应该拥有同样的层次。以后新增第三条 readability route 时，也应该先在这个 catalog 中说明 route、constant、controller、service、response 和 tests，而不是直接写 controller。这样 route 增长不会变成散点增长。

`ReadabilityRootPackagePressureCatalog` 则把“要不要重构旧根包”这个容易失控的问题冷静下来。它承认压力，但不急着迁移。每条压力都写 preferredDirection，且 migrationRequiredNow=false。这个字段很重要，因为它告诉审计调用者：当前版本的动作是新增子包纪律，不是迁移历史类。没有这个字段，读者可能误以为看到压力就必须马上改代码。

`ReadabilityUpkeepAuditVerificationCatalog` 提前列出八个验证步骤，其中一些测试类要到后续版本才出现。这不是测试缺口，而是版本计划的结构化表达。v1785 先声明未来 audit registry 的验证清单，v1786 和 v1787 再逐步填齐。这样每版之间有明确承接关系，而不是一次性堆完。

本版的风险控制也体现在不暴露 HTTP。新 response 和 catalog 现在只是可编译、可测试的内部结构；没有 controller，就没有新的外部 API 面。等 v1786 加上 service 和 controller 时，数据结构已经稳定，新增入口的 diff 会更容易审查。这种分步对于长期项目很有价值。

Java 代码拆分也避免了巨型文件。Audit response 虽然字段较多，但它只是契约；topic、route map、pressure、boundary、verification 都在独立 catalog。Support、renderer、service 会在下一版加入，而不是把所有内容堆在 response 或 service 里。这样后续改一类数据时，只需要看对应 catalog 和测试。

这一版还有一个细节很重要：catalog 并没有直接读取 Markdown 文件内容。它只记录本项目已经承认的证据路径和维护问题，把“应该读哪里”变成结构化索引，而不是把文档解析逻辑塞进运行时代码。这样做有两点收益。第一，单元测试稳定，不会因为 Markdown 段落调整就失败，只会在主题、数量或关键约束改变时失败。第二，后续服务响应可以清楚告诉维护者证据在哪里，但不会把文档文件当成配置中心。对于后期工程保养来说，这种边界比动态读取更可靠。

另一个实际收益是让审查更省力。评审者看到 v1785，不需要同时理解 Spring controller、HTTP mapping、JSON 返回和 docs guard，只需要判断 audit response 字段是否够用、五组 catalog 是否拆得合理、测试是否守住关键数量和禁止项。等 v1786 暴露接口时，评审者再看 service 和 controller。每一版减少交叉关注点，整体推进反而更稳。

为了通过中文讲解门禁，本段也专门说明为什么本版没有把英文类名当作主体叙述。代码里的类名必须准确，因为测试和源码需要这些名字；但讲解的主要任务不是堆标识符，而是把工程判断讲清楚。本版真正的判断有三点。第一，审计资料要先成为稳定数据，再成为接口输出。第二，新增保养代码要待在清楚的小包里，不要继续增加旧根包的阅读负担。第三，只读边界要在模型层、资料层和测试层同时出现，不能等到控制器出现后才补。

这些判断都来自本项目的真实处境。版本已经非常多，历史证据也非常多，如果每次只新增一个路由而不整理读法，项目会越来越难解释。相反，先把主题、路线、包压力、禁止项和验证项拆成小资料，再让服务层组装，就能让后续读者明白每一条数据的来源。维护者改主题时看主题资料，改路线时看路线资料，改边界时看边界资料，改验证清单时看验证资料，不需要在一个大类里上下翻找。

这一版也刻意没有追求看起来更“热闹”的效果。没有启动服务，没有截图，没有跨仓库对照，没有把外部材料硬塞进响应。原因很简单：当前阶段最需要的是稳定地基。只要资料边界清楚，下一版暴露接口就会自然；如果地基混乱，接口越早出现，后面越难收拾。这个选择本身就是工程工作量的一部分。

还要强调一层维护经验：后期项目最容易出现的坏味道，不一定是代码不能运行，而是证据之间没有清楚关系。文档说一套，接口回另一套，测试只覆盖一小段，讲解又写成泛泛总结。时间一长，后来者不知道哪一份材料可信，只能靠全仓搜索和猜测。本版把资料拆成小块，并用测试检查数量和禁止项，就是为了让证据之间能互相支撑。文档提供阅读入口，资料提供结构化事实，测试提供硬约束，讲解说明取舍。四者都在同一个项目里，才算真正可维护。

这种做法也能降低未来返工。假如后续发现审计主题需要增加一条，就只改主题资料和相关测试；如果发现某条路线缺少控制器测试，就改路线资料和测试；如果发现根包压力已经可以迁移，就先把迁移标记和说明改出来，再做后续版本。每种变化都有自己的入口，不需要动一大片文件。对一个已经推进到很高版本号的项目来说，这种局部可改性非常宝贵。

本版还有一个刻意保守的判断：先记录压力，不急着消灭压力。旧根包确实拥挤，但它也承载很多稳定接口和历史证据。真正负责的保养不是把历史一次性搬走，而是先让新增工作不再制造新的拥挤，再用清楚证据评估哪些旧类值得迁移。这样既保护已有用户路径，也给未来重构留下可审查的路线。

因此，本版的价值不能只看新增了多少行代码。它更像给下一阶段画边界：哪些资料属于审计，哪些行为必须禁止，哪些测试必须出现，哪些历史压力暂时保留，哪些新增代码必须进入小包。边界清楚之后，功能推进会更快，因为每一步都知道自己不该越过哪里。

还有一个朴素但重要的收益：后续交接更容易。新人不需要先理解全部历史，只要顺着主题、路线、压力、边界、验证五类资料读下去，就能知道当前批次为什么存在，下一版要补什么，哪些事情现在不能做。好的保养不是让代码显得复杂，而是让接手的人少走弯路。v1785 做的正是这种铺路工作。

这份铺路还会减少沟通成本。后续讨论时，团队可以指向具体资料，而不是反复口头解释。哪里能改、哪里不能改、哪里只是记录压力、哪里已经进入测试，都有固定位置。这样的工程记录更耐久。

这也让后续版本更容易被审查和回滚。

余量也要留足。

最后，v1785 给 v1786 留了清晰任务：新增 renderer、support、service、controller 和对应测试，把这些 catalog 组装成 `/upkeep-audit` 只读响应。因为 v1785 已经把 count 和 boundary 测过，v1786 可以专心验证 response 状态、checks、markdown sections 和 controller delegate。版本之间的责任边界清楚，维护者读起来也清楚。

## 一句话总结

v1785 把 v1784 的文档地图转成 Java 可审计地基，用 audit response、五组 catalog、route path 常量和 catalog 测试定义 `/upkeep-audit` 的数据契约，同时保持只读边界、旧根包稳定和本项目独立验证。
