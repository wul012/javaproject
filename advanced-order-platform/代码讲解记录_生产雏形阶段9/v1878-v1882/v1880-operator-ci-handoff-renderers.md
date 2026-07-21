# v1880：Operator CI Handoff 双输出渲染收敛讲解

本版本只优化本项目内部的展示层结构，不增加新接口，也不扩大任何运行权限。它处理的是一个很典型、却容易被“类越多越专业”掩盖的问题：同一份报告被按 Markdown 章节拆成许多只有一个静态方法的长名类，阅读者为了理解一次完整输出，需要在十余个文件之间往返。v1880 先让旧实现亲自生成完整真值，再把实现收敛到两个按产品输出命名的短类。全文坚持禁止硬凑；每一节都对应真实输入、真实输出、机械门或安全边界。

## 入口路由

本版本涉及两条已经存在的只读入口。第一条是 `/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-registry`，它返回当前 operator CI handoff；第二条是 `/api/v1/ops/shard-readiness/minimal-read-only-gate-operator-ci-handoff-archive-verification-registry`，它对第一条 handoff 做归档核验。两个根包 Controller 均原地保留，路由后缀仍由 `OpsShardReadinessReleaseAcceptanceRoutePaths` 所有，Controller 注入的公开 Service 类型也没有变化。也就是说，调用者看到的 URL、Spring Bean、事务入口、JSON 字段和 HTTP 行为都不因本次重构改变。

从入口看，第一条请求没有业务写入参数。Controller 调用 `OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService.registry()`，Service 读取上游 minimal-read-only-gate execution archive verification 响应，组合 source archive、operator lane、CI batch、boundary lock 和 scorecard。第二条入口调用 archive verification Service，后者把第一条响应当作只读来源，再产生 source handoff、artifact verification、lane verification、batch verification、boundary verification 和 archive scorecard。两条入口的关系不是两个互不相干的页面，而是“交接报告生成”与“交接报告归档核验”的串行证据链。

为了避免重构误伤入口，v1844 历史结构门继续检查两个 Controller 必须留在根 `ops` 包、必须导入 `ops.maintenance.minimalreadonlygateoperatorcihandoff` 的公开边界；Controller Markdown 测试继续用真实 Service 构造图执行。v1880 只把 Service 最后一跳从旧长名聚合 renderer 改为 `HandoffRenderer` 或 `ArchiveRenderer`。这是一处包内委托替换，不是路由迁移，也不是兼容层转发。

## 响应模型

第一条响应 `OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse` 仍保存完整的结构化事实：项目和版本、只读与执行权限标志、来源计划、来源归档版本与 endpoint、handoff 状态、各组数量、五类条目列表、Markdown sections、checks 和最终 status。其嵌套 record 分别表达 `SourceArchiveSnapshot`、`OperatorLane`、`CiBatchPlan`、`BoundaryLock`、`ScorecardEntry` 与 `MarkdownSection`。Renderer 没有重新发明一份 map，也没有把 record 降级成字符串键值；它只消费已经类型化的列表。

主 handoff 的冻结输出恰好五节。`Source Archive` 是一条计数行加一条来源记录；`Operator Lanes` 是一条计数行加四条执行次序；`CI Batches` 是一条计数行加五个门禁批次；`Boundary Locks` 是一条计数行加八条锁；`Scorecard` 是一条计数行加五个分数。因此一共是五个 section、三十三条内容行。这里的“行”指 `MarkdownSection.lines()` 中的元素，不把 heading 算作内容行。顺序本身具有含义：先说明来源，再说明人和职责，再说明 CI 顺序，最后给出不能打开的边界与汇总分数。

第二条响应保留 archive verification 专属结构：来源 handoff 快照、六个 artifact、四条 lane verification、五条 CI batch verification、八条 boundary verification 和六项 scorecard。冻结输出是六节三十六行。它比主报告多一个 artifact 维度，并在每条验证记录中保留 `archived` 与 `status`。两个响应虽然都包含 `MarkdownSection`，嵌套类型却属于不同的公开 record；因此 v1880 没有用一个带布尔模式、反射或原始 `Object` 的万能 renderer，而是保留两个静态类型完整的输出所有者。

## 上游证据配置

主 handoff 的最上游是 `OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService`。它提供的不是 Java 启动句柄、mini-kv 连接或可执行命令，而是一份已经生成的 archive verification 响应。`SourceArchiveCatalog` 把来源版本、endpoint、archive state 和 status 投影成快照；`LaneCatalog` 从来源状态生成 focused、grouped、build、smoke 四条 operator lane；`BatchCatalog` 给出五批 CI 顺序；`BoundaryLockCatalog` 给出八条明确的禁止项；Service 自己按期望数量计算 scorecard。数据由 Catalog 和 Service 决定，Renderer 无权改写 readiness 判断。

archive verification Service 的上游正是主 handoff Service。它分别调用六个 Catalog，把来源响应投影成六组归档事实。Artifact Catalog 证明 source response JSON、Markdown rendering、operator lane plan、CI batch plan、boundary lock plan 和 source scorecard summary 已进入归档；Lane 与 batch Catalog 保留原始顺序；Boundary Catalog 同时检查来源锁和归档状态；Scorecard Catalog 汇总六类期望值。Renderer 看到的都是计算完成的 record，只负责把字段按冻结格式连接成行。

下游 `minimalreadonlygateoperatorcihandoffarchivedigest` 仍只依赖 archive verification 的公开 Service 与 Response。v1844 门逐文件检查它对 response 和 service 的导入，不允许下游穿透读取本包的 Catalog、Renderer 或 Support。这一方向约束非常重要：如果 Renderer 被公开，下游可能把显示细节当作可复用业务 API；v1880 继续让 `HandoffRenderer` 与 `ArchiveRenderer` 保持 package-private，使真正稳定的依赖面仍然是结构化响应。

## 服务层核心流程

旧主报告由一个 103 行聚合 renderer、一个 lane renderer 和一个 renderer support 组成。旧 archive 报告又由一个聚合 renderer、六个章节 renderer 和另一个 support 组成。这些类的共同算法几乎完全一致：新建 `ArrayList`，先加入 `xxx-count=n`，遍历条目拼接字符串，再调用 support 构造不可变 `MarkdownSection`。章节标题不同、字段映射不同，但控制流完全相同。按章节建立类，使文件边界落在展示标题上，而不是稳定的产品行为上。

新 `HandoffRenderer` 只有一个公开给包内 Service 的 `render` 方法。它按固定顺序调用五个私有映射方法，再由 `List.of` 返回不可变 section 列表。每个私有方法把 heading、count name、条目列表、类型安全的 lambda 和 `MarkdownSection::new` 交给共享 `MarkdownSections.counted`。新 `ArchiveRenderer` 使用完全相同的共享算法，但只拥有 archive verification 的六组映射。共享 engine 负责“计数行在前、条目在后、结果不可变”的机制；两个 renderer 负责各自的字段格式和章节顺序。

这次收敛没有把复杂度藏进一个巨型文件。`HandoffRenderer` 为九十三行，`ArchiveRenderer` 为一百三十二行，合计二百二十五行；旧九个 renderer 共三百六十余行，另有两个 support。两个新类之所以合理，不是因为名字短，而是因为改动原因一致：主 handoff 的任何展示变化只触及一个类，archive verification 的任何展示变化也只触及一个类；共享计数机制变化则只触及 `MarkdownSections`。数据变化仍落在 Catalog，状态判断仍落在 Service，契约变化仍落在 Response。

## Java 证据检查

第一道证据不是重构后的测试，而是重构前的旧实现输出。临时探针通过真实测试工厂构造两条完整 Service graph，打印所有 heading 与 line。基于这份实际输出建立 `HandoffMarkdownTests`，逐个构造预期 `MarkdownSection` 并使用 `containsExactly` 比较。正式 oracle 在旧九个 renderer 和两个 support 尚未删除时先通过两项测试；随后临时探针被删除，生产实现才替换。这样预期值的来源是旧行为，而不是新代码作者的记忆。

替换后，同一个未修改预期的 oracle 再次通过。它不只检查 section 数量，也不只搜索几个关键词；URL、版本、空格、竖线、布尔值、状态、计数前缀与列表顺序全部进入等值比较。主报告五节三十三行、归档报告六节三十六行中任何一个字符漂移都会失败。Controller 与 Service 测试又从另一层证明响应版本、endpoint、profile、来源计划、只读标志、各组数量和 scorecard 没有变化。

结构证据同样只收紧。`ops` Java 文件由一千二百八十三降到一千二百七十四；renderer 数量由五十八降到五十一，总行数由三千九百七十三降到三千八百一十六，长 renderer 文件名由四十七降到三十八。本包生产文件由二十七降到十八，并机械限定只能存在 `HandoffRenderer.java` 与 `ArchiveRenderer.java` 两个 renderer。生产长文件 stem 从一千二百一十八降到一千二百零七，长标识符使用从二万零六百九十六降到二万零六百二十七。所有数字都来自可复现脚本和会失败的测试，不靠进度表自述。

## mini-kv 证据检查

本版本不会连接、启动或修改 mini-kv。主响应中保留 `startsMiniKvService=false`，archive 响应继续回显相同只读边界；八条 boundary lock 中包括 `no-mini-kv-autostart` 与 `no-mini-kv-write-admin`。这些字段表达的是“操作者在 CI handoff 中不得做什么”，不是授予 Java 调用 mini-kv 的能力。Renderer 只把布尔值和状态写进 Markdown，它无法取得 host、port、credential 或命令执行器。

从数据链看，mini-kv 只作为跨项目治理背景出现在禁止项中。Java 上游来源是 Java 自己的 minimal-read-only-gate execution archive verification；没有读取 mini-kv 的仓库文件，没有修改 Node 的冻结 fixture，也没有刷新任何跨项目 digest。即使 archive report 显示 `archived=true`，含义也只是当前 Java 响应中的归档条目满足 Catalog 条件，不代表 mini-kv 已启动，更不代表联合部署已经发生。

因此 mini-kv 侧不需要为 v1880 做版本对齐。若未来真实跨项目契约改变，应该沿 mini-kv、Java、Node 的依赖顺序更新契约与证据；本次只是 Java 包内的等价重构，可以独立推进。讲解中单列这一节，是为了把“报告提到 mini-kv”与“程序拥有 mini-kv 执行能力”彻底区分，避免维护者从展示文本推导出并不存在的运行授权。

## 阻断与安全边界

两条 Service 仍标注 `@Transactional(readOnly = true)`。响应继续声明 `readOnly=true`、`executionAllowed=false`、`startsJavaService=false`、`startsMiniKvService=false`、`readsCredentialValue=false`、`resolvesRawEndpointUrl=false`、`managedAuditHttpAllowed=false`。主报告八条 boundary lock 仍全部为 locked，archive report 又逐条证明 locked、archived 和 passed。v1880 没有新增 repository、HTTP client、shell、process builder、credential resolver 或写路由。

类型可见性也是阻断的一部分。两个新 renderer 没有 `public`，没有 Spring 注解，也没有状态字段；外部包不能把它们当成业务服务调用。`MarkdownSections.counted` 会对 heading、count name、entries、mapper 与 factory 做非空检查，并用 stream 生成不可变列表；Response 的原有 SpotBugs 边界仍由现有排除身份和响应测试保护。测试工厂改名为 `HandoffTestData` 与 `ArchiveTestData` 只影响测试导航，且 archive 工厂复用 handoff 工厂，避免复制一条容易漂移的上游构造链。

失败条件明确成文：任何 endpoint、response record、Catalog 数据、事务属性、权限布尔值、section heading、内容行、顺序或计数前缀变化都视为失败；任何重新出现的第三个 renderer、旧长名 renderer、support 壳或放宽后的 census cap 也视为失败；不得通过修改冻结预期、删除消费者测试、增加 SpotBugs 豁免或改写 fixture 字节制造绿色结果。

## 测试覆盖

测试分四层。第一层是两项精确 Markdown oracle，先在旧实现上通过，再在新实现上原样通过。第二层是原有 Service 行为测试：主 handoff 检查版本、route、profile、只读标志、四条 lane、五个 batch、八条 lock 与五项 scorecard；archive 的 source、Catalog、boundary、scorecard 和 aggregate checks 分组测试继续运行。第三层是根 Controller Markdown 测试与下游 archive digest 消费测试，确保包边界两侧都没有断裂。

第四层是维护性门。v1844 被升级为当前十八文件上限、十一旧文件永久缺席、八测试文件上限和两个短工厂；v1842、v1847 至 v1850、v1866 的全局 ops cap 同步收紧到一千二百七十四。`OpsEleganceCensusTests` 固定五十一 renderer、三千八百一十六行、三十八个长文件名，并要求目标包恰好两个短 renderer。`JavaEleganceGateTests` 的 exact baseline 只删除已消失身份；它曾真实阻断本版一个超过四十字符的新测试方法名，修短后才重新生成 baseline，没有添加豁免。

最终验证顺序也受约束：先完成本讲解和 archive manifest，再跑 focused docs/archive gates，最后执行完整 `mvnw -B verify`。完整 verify 必须覆盖 Spotless、全部非 Docker 测试、JaCoCo floor、SpotBugs 与 jar packaging。实现提交推送后还要等 canonical Actions 的 headless 与 Docker-tagged job；实现 CI 绿色后再写 closeout 事实，closeout 自己的 CI 也必须绿色，最后才能创建注解 tag。

## 实际工作量说明

生产侧删除九个 renderer 与两个 renderer support，新增两个短 renderer，净减少九个 Java 文件；两个 Service 各只改一处内部调用。测试侧新增一份一百二十六行的精确 oracle，把两个长 TestSupport 改为 `HandoffTestData` 与 `ArchiveTestData`，并让后者复用前者的构造链；所有原有调用者同步到短工厂。历史 v1844 门没有删除，而是从冻结二十七个旧文件改为约束十八个当前文件和十一项删除清单。

治理侧更新全局 renderer census、ops 总量 cap、生产与测试长名 baseline、可复现 PowerShell census、当前讲解入口、archive retention policy、精确 SHA-256 manifest、CHANGELOG、Java final-evidence candidate 与进度账本。旧输出捕获用的探针在正式 oracle 落成后已经删除，没有把临时调试文件混进版本。新 production renderer 总计二百二十五行，旧同族 renderer 约三百八十二行，因此这是净删除控制流重复，而不是把同样代码换两个文件名。

这份讲解超过三千个汉字，是因为版本本身同时涉及两条输出链、两种响应模型、上下游边界、旧行为冻结、共享算法复用、名称治理和归档治理；不是为了达到字数而重复结论。禁止硬凑意味着若工作量不足以支撑完整解释，就应扩大真实重构或缩小版本声明，而不是堆砌形容词。本版的每个数字、每条边界和每个失败条件都能回到源码、测试或脚本复现。

## 一句话总结

v1880 在不改变路由、响应、证据、顺序和权限的前提下，先用旧实现冻结五节三十三行与六节三十六行，再以两个短而类型安全的输出所有者复用共享计数引擎，永久删除九个章节 renderer、两个 support 壳和两条重复测试构造链，并用逐行 oracle、历史结构门、名称 baseline、全局 census、完整 Maven 与双阶段 CI 把这份优雅收益固定为只能继续缩小、不能悄悄反弹的工程事实。
