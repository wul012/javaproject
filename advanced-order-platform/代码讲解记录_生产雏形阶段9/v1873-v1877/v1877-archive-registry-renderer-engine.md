# v1877 Archive-Registry Renderer 收敛代码讲解

## 入口路由

本版本处理的是 release-acceptance archive registry，也就是“发布验收结果如何被整理成只读归档包”这一段。它的 HTTP 入口仍由根 `ops` 包中的 Controller 暴露，路径仍然由 `OpsShardReadinessReleaseAcceptanceRoutePaths` 持有。调用者发起一次 GET 后，Controller 不解释业务规则，只把请求交给 `ciarc` 包里的 RegistryService；因此入口层的输入可以理解为“请求当前归档视图”，输出则是一份包含结构化字段、检查清单和 Markdown 分段的 response。v1877 没有移动 Controller、没有改 route 常量，也没有增加参数，这一点很重要：本轮目标是改善内部表达方式，而不是借重构机会制造新的外部契约。

这条入口处在一条明确的只读链上。上游 `ciaccept` 已经把 verification dossier 转换为 release-acceptance 结果；当前服务读取这个结果，形成 archive snapshot、artifact manifest、route package、operator pack 等归档视角；下游 `releasearchivehandoff` 再读取当前公开 service，生成交接材料。请求从入口进入后不会写数据库、不会发送消息、不会启动 Java 或 mini-kv，也不会解析 credential value。入口的机理因此很简单：定位一个已经冻结的上游证据快照，经过纯内存映射，返回一个不可变的当前视图。

可以把这次调用想成档案管理员收到“请给我本次发布验收归档目录”的请求。管理员不会重新执行验收，也不会改动被归档内容，只会读取已签收的验收单，按固定顺序列出来源、材料、接收人、CI 证明、安全封条、保留期限和收尾账本。v1877 改的是管理员内部如何排版目录，不是档案内容，更不是验收权限。

## 响应模型

响应类型仍是原有的长名 RegistryResponse record。它同时承载三类信息。第一类是版本、endpoint、profile、只读状态和执行权限等顶层元数据；第二类是九组 typed record 列表，例如 `SourceArchiveSnapshot`、`ArtifactManifestEntry`、`RoutePackageEntry`、`BoundarySealEntry`；第三类是根据前两类信息生成的九个 `MarkdownSection`。JSON component、字段顺序、列表类型和状态文本全部保持不变，因此现有消费者不需要知道 renderer 已被替换。

每个 MarkdownSection 只有 heading 与 lines。旧实现为每一种 section 建一个独立 Renderer 文件，再由 RegistryRenderer 按顺序拼起来；这造成十个超长类名文件和一个 support 壳。新实现保留完全相同的数据边界，但把“标题、计数字段、单条记录如何转成一行”放进一个 203 行的 `ReportRenderer`。Source Archive 仍输出两行，Artifact Manifest 仍输出八行，随后依次是 Route Packages 五行、Operator Packs 五行、CI Attestations 六行、Boundary Seals 九行、Retention Windows 六行、Closeout Ledger 七行、Scorecard 九行，总计九段五十七条内容行。

这里没有把结构化 response 降级为字符串 map。相反，`ReportRenderer` 直接导入九个嵌套 record 的短类型名，每个私有方法接受 `List<具体类型>`。如果某个字段以后被删除或改名，Java 编译器会在对应 mapper 处直接报错；如果使用 `Map<String,Object>`，错误就会推迟到运行时。优雅不只是文件少，还包括让类型系统继续承担约束责任。

## 上游证据配置

RegistryService 的唯一上游是 v1876 已收敛的 release-acceptance RegistryService。当前服务先调用 `sourceRegistryService.registry()` 得到上游 response，然后由九个 Catalog 逐组投影。SourceCatalog 提取版本、endpoint、profile 和 release-acceptance 状态；ArtifactManifestCatalog 把关键计数与状态写成七项材料清单；RoutePackageCatalog 与 OperatorPackCatalog从 signoff lane 形成接收方和负责人视角；CiAttestationCatalog、BoundarySealCatalog、RetentionWindowCatalog、CloseoutLedgerCatalog 分别保持 CI、边界、保留期和收尾语义；ScorecardCatalog 最后核对实际数量与期望数量。

这一配置关系说明为什么 v1877 只动 renderer 而不动 Catalog。Catalog 决定“有哪些业务事实”，renderer 只决定“这些事实如何在 Markdown 中呈现”。若同时改两层，即使最终文本相同，也难以判断是数据源变化被格式化掩盖，还是格式化本身正确。把两类责任分开后，需求到证据的路径变得透明：输入是 release-acceptance response，Catalog 输出九组 typed entries，ReportRenderer 输出九段文本，RegistrySupport 再把全部字段封装为最终 response。

上游版本字符串 `Java v1502`、来源 plan `Node v367`、required archive plan `Node v368` 与 operator handoff plan `Node v369` 都是既有只读证据。v1877 不改这些值，也不读取 Node 工作区或 mini-kv 运行时。历史 fixture 和关联项目引用仍能按原路径找到同一份证据；本项目只优化自己的 Java 表达，不把跨项目对齐偷偷塞进内部重构。

## 服务层核心流程

服务方法的主流程是一个清楚的 aggregate pipeline。第一步读取上游 release acceptance；第二步顺序构造九组列表；第三步把九组列表同时交给 RegistrySupport 和 ReportRenderer；第四步返回不可变 response。`@Transactional(readOnly = true)` 仍在原位置，依赖注入仍通过构造器完成，公开 service 类型与方法签名均未改变。真正的生产改动只有最终 renderer 调用从旧的超长 RegistryRenderer 切换为短名 `ReportRenderer.render(...)`。

`ReportRenderer` 自己不保存状态，也不依赖 Spring。它的 `render` 方法只声明 section 顺序，九个私有方法只声明一段映射。所有 section 都有固定计数首行，所以统一调用共享的 `MarkdownSections.counted`：这个 engine 先验证 heading、countName、entries、mapper 和 factory 均非空，再用 `Stream.concat` 把计数行与条目行合成不可变列表，最后调用 `MarkdownSection::new`。这使不可变快照、计数格式和空值失败位置集中在一个已测试算法里。

家族内部只保留 `flag` 与 `status` 两个短辅助方法。它们不是第二套 engine，只是把 `required=true`、`ready=true`、`locked=true`、`status=passed` 这类稳定词法集中表达。复杂字段仍在各自 typed mapper 中显式排列，所以读者打开一个文件便能看到全部九段标题、顺序和字段格式，不必在十一个超长文件之间来回跳转。新增第十个归档 section 时，开发者只需新增一个 typed 方法并在 `List.of` 中放到正确位置，而不是复制 Renderer、Support、构造器和聚合调用四层样板。

## Java 证据检查

Java 侧首先用编译器检查全部嵌套 record 的字段访问。新 renderer 不使用反射、不使用字符串字段键，也不绕开访问修饰符；因此 response contract 与 renderer 的连接是静态可验证的。其次，`ArchiveMarkdownTests` 把旧实现实际产生的九段五十七行完整写成 `containsExactly` oracle。这个测试在任何生产文件删除前先通过一次，证明期望来自旧行为而不是根据新实现猜写；替换后同一份期望未经修改再次通过，证明标题、顺序、计数和每个分隔符都没变。

结构检查同样不是自述。v1849 历史门原来要求 23 个迁移文件存在，本版把它升级为当前 13 文件的允许集合，并单独列出 11 个必须不存在的旧 renderer/support 文件。全局 elegance census 把 ops 文件上限从 1314 收到 1304，把 renderer 从 86 收到 77、总行数从 4586 收到 4376、超长 renderer 文件名从 80 收到 70。`JavaEleganceGateTests` 还把生产长 stem、长标识符出现次数与唯一集合收紧到 1243、20851、2802；测试侧收紧到 790、10156、3828。旧类名虽然仍会作为历史 baseline 删除项出现，但不允许重新进入当前源码。

聚焦门合计通过六十五个测试，覆盖当前 archive 行为、两个根控制器、下游 release-archive handoff、v1847 至 v1849 结构、v1866 总量、elegance、change gate 和精确 Markdown oracle。最终还会在本讲解写完后运行完整 Maven verify，让 JaCoCo、SpotBugs、Spotless、全部 Spring 集成测试和 jar 打包共同给出结论。

## mini-kv 证据检查

mini-kv 在这条 Java 路径中只作为被保护的关联系统概念出现，不是当前服务的运行依赖。Boundary Seals 中保留 `no-mini-kv-autostart` 与 `no-mini-kv-write-admin`，其含义是 Node 或 Java 不得在读取归档证据时自动启动 mini-kv，也不得开放写入或管理命令。当前 archive registry 只是把上游已经确认的边界控制投影为归档封条，不连接 mini-kv 端口、不执行 CLI，也不读取 C++ 仓库文件。

因此 mini-kv 证据检查不是伪造一次跨项目运行，而是验证 Java 输出仍准确表达既有边界。逐行 oracle 固定了 `Node must not start mini-kv`、`mini-kv write/admin commands remain forbidden` 与对应 `locked=true | status=passed` 文本；CI 和下游 handoff 测试继续消费这些封条。若 renderer 在重构中漏掉某条边界、改变顺序或丢失 locked 状态，oracle 会直接失败。

这种处理符合四项目协作规则：内部非契约优化可以并行，但不能重写历史证据或越权启动关联项目。v1877 没有修改 `D:\C\mini-kv`，也没有声称重新验证其运行能力。等未来真正做跨项目对齐时，可以使用相同公开 response；本轮先保证 Java 自己的输出契约稳定、依赖方向单向、边界语言诚实。

## 阻断与安全边界

最终 response 继续明确 `readOnly=true`、`executionAllowed=false`，并保留 startsJavaService、startsMiniKvService、readsCredentialValue、resolvesRawEndpointUrl、managedAuditHttpAllowed 等否定状态。renderer 只能读取已构造的 record 并生成字符串，没有 repository、HTTP client、process builder、socket 或消息发布依赖。即便有人直接调用 `ReportRenderer.render`，它也没有能力改变业务状态。

八个 Boundary Seal 覆盖 Java 自动启动、mini-kv 自动启动、写路由、credential value、raw endpoint URL、managed audit HTTP/TCP、runtime shell 和 mini-kv write/admin。新代码逐项输出 code、lockedBehavior、auditEvidence、locked 与 status，不用一个笼统的 `safe=true` 代替具体约束。这样审阅者能看出被禁止的动作是什么、证据来自哪里、封条是否锁定，而不是相信无法复现的安全宣言。

失败条件也写进版本设计：任一标题、内容行、顺序、计数或不可变语义改变就回退；不得修改 oracle、fixture 或 Catalog 数据让实现变绿；ReportRenderer 超过 300 行或出现第二套 section engine 就回退；renderer、长名、热点或 SpotBugs 豁免上升也不接受。安全边界与代码优雅因此由同一套机械门保护，而不是只写在说明里。

## 测试覆盖

测试顺序本身就是本版本最关键的证据。第一阶段先新增 oracle，只运行 `ArchiveMarkdownTests`，它在旧十一文件 renderer 体系上通过。第二阶段新增 `ReportRenderer`、替换 RegistryService 调用并删除旧文件，再运行完全相同的 oracle；若任何空格、竖线、字段顺序或计数首行不同，AssertJ 会显示第一处差异。第三阶段把超长测试工厂改为 `ArchiveTestData`，同时更新五组 package 行为测试、根 Controller 测试和下游 handoff fixture，再次执行整组回归。

行为测试分别检查来源与 manifest、route 与 operator、CI 与 boundary、retention 与 closeout、不可变列表；根 Controller 测试检查 route 常量、版本、只读状态和 Markdown headings；下游 handoff 测试证明公开 service 与 response 仍可被后继链路使用。结构测试则证明旧文件确实删除、新文件集合受限、根 Controller 仍可见、包依赖仍从 handoff 指向 ciarc，而没有出现反向引用。

核心选择已通过六十五个测试且零失败、错误、跳过。讲解、归档 manifest、CHANGELOG、进度账本和 final evidence 更新后，还要追加 archive retention、current walkthrough、closeout 与 docs honesty 门，最后才运行全量 verify。讲解被安排在最终 verify 之前，是为了让最终构建验证真实的交付形态，而不是先拿到绿灯再补一篇未被门覆盖的文档。

## 实际工作量说明

本版不是简单把十个文件复制到一个文件。先用 CodeGraph 和直接边检查确认上游只有 `ciaccept`、下游是 `releasearchivehandoff`；再通过 JShell 从已编译旧实现抓取实际 Markdown，人工复核九段行数；随后把完整五十七行写成 oracle 并在旧代码上运行。生产替换后，新增 203 行 typed renderer，删除十个 renderer 与一个 support，RegistryService 只改最终调用；测试侧新增 oracle、把超长 fixture 改为短名，并更新所有直接消费者。

结构治理同步覆盖脚本、当前门和历史门。`ops-elegance-census.ps1` 新增 archive family 指标；v1849 不再把旧实现形状永久冻结；v1847、v1848 与 v1866 的全局文件上限继续下降；精确名称 baseline 只删除旧身份，不新增豁免。净结果是生产 Java 少十个、renderer 少九个、renderer 代码少二百一十行，长标识符出现次数少七十八次，同时核心聚焦回归增至六十五项。

这些工作量服务于一个清楚的优雅判断：旧设计把“每段有不同业务数据”误解成“每段必须有独立类文件”；新设计保留业务数据的九个类型边界，却把完全相同的组装算法提升为共享 engine，把同一家族的排版决策放回一个文件。读者的跳转次数、文件名噪声和复制扩展成本都下降，类型安全、输出精度和审计证据反而增强。禁止硬凑的要求也体现在这里：篇幅来自实际依赖、数据流、测试顺序和失败面，而不是重复说“代码更好”。

## 一句话总结

v1877 把 release-acceptance archive registry 从“一个聚合 renderer、九个 section renderer、一个 support”收敛为“一个 203 行 typed ReportRenderer 加一个已复用的 MarkdownSections engine”，并用先对旧实现通过的九段五十七行 oracle 证明输出没有变化。外部 route、response、Catalog、只读事务、上游 release acceptance、下游 handoff 和跨项目禁区全部保持原样；内部则获得更少文件、更短命名、更清楚的单向数据流和更低的新 section 扩展成本。

对使用者来说，输入仍是一条只读 GET，输出仍是同一份 archive registry；对维护者来说，排版规则终于能在一个文件里完整阅读，算法规则在一个共享 engine 中统一验证；对审阅者来说，旧行为、当前结构、长名基线和安全边界都有会失败的机械证据。这正是本项目把 coding brilliant and elegant 向九分推进时需要的改进：不是追求表面短小，而是在不牺牲契约和证据的前提下，让抽象贴合真实变化轴。
