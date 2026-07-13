# v1867 Java 轨道第二阶段收束讲解

## 实际工作量说明

v1867 不是新增业务接口的功能版，而是 Java Production Excellence 计划的第二阶段收束候选。第一阶段已经在 v1866 把 `ops` 根包从最初的一千三百多个实现类压缩到一百零四个保留文件，其中一百个是 HTTP Controller，另外四个是确有跨族用途的共享根。第二阶段要回答的不是“还能不能再搬文件”，而是“这套结构怎样长期不反弹、证据怎样独立复现、文档有没有夸大能力”。因此本版的工作量主要落在减少间接层、拆除测试热点、收紧机械门和统一最终证据四条线上。

路由部分先做了全量引用普查。最初的单行正则只在同一行寻找 `OpsShardReadinessRoutePaths.FIELD`，因而把跨行书写的 Controller 注解误判成无读者别名。删除后编译器立即报错，说明调查口径不完整。这里没有修改 Controller 来迁就删除，也没有把编译错误登记成“后续再修”；而是恢复根 owner，用可跨换行匹配的扫描重新统计。完整结果显示原二百六十六个字段都有读者。继续分析读者职责后，二百三十九个字段被确认只是转发到某个 family 的叶 route owner，十五个字段由根 owner 真正持有字符串，另外十二个字段属于 ReleaseAcceptance 的兼容证明面：它的 catalog 必须比较稳定根值与叶 owner 当前值。最终方案由“删死别名”改成“让普通读者直接依赖真实 owner，同时保留有验证职责的兼容面”：一百六十个生产和测试文件中的七百三十五处引用被改到叶 owner，二百三十九个纯转发字段删除，十五个根字符串与十二个兼容字段保留。

测试部分处理两个最后超过八百行的热点。`OpsEvidenceServiceTests` 的固定类名被发布脚本引用，因此保留这个入口名，把通用场景创建搬到 `OpsEvidenceContractTestSupport`，把发布契约断言移到 `OpsEvidenceReleaseContractTests`。原来的 release-approval rehearsal 提示总览测试则拆成摘要顺序和契约边界两类，前者由 `RehearsalHintDigestTests` 锁定长列表、digest 输入与证明次序，后者由 `RehearsalHintContractTests` 锁定 header 场景、贡献目录和只读边界。拆分移动的是测试职责，不是期望值；旧断言没有删减，fixture 字节没有改写。

治理部分增加命名 census、归档 SHA-256 清单、CI action 版本门、JaCoCo floor 提升和 E1-E10 证据表。生产 Java 最大文件固定为七百三十八行，测试最大文件固定为六百九十九行，两个集合都不再允许出现七百五十行以上文件。新建文件名和标识符遵守四十字符预算，存量长名进入只减不增基线。本项目不以堆砌文档行数替代工程变化，禁止硬凑；如果一项说明不能对应到实现、命令或会失败的测试，就不把它写成完成证据。

## 入口路由

Spring Controller 的入口路径通常由全局前缀、family 后缀和具体 endpoint 组合。历史上根 `OpsShardReadinessRoutePaths` 同时扮演“少量全局字符串 owner”和“所有 family 字段的转发表”两种角色。后者让任何 Controller 看起来都依赖根 owner，即使真正的字符串定义已经在 `ops.maintenance.*` 下的叶 route owner 中。这样的间接层不改变运行结果，却放大修改面：一个叶 family 的字段移动，会使根 owner、Controller、route 测试和目录扫描都同时出现 diff，审查者还要追两跳才能知道字符串从哪里来。

v1867 保留根 owner 的十五个真实字符串，普通读者直接 import 对应叶 owner。例如 signed-approval、candidate-document、read-only evidence 等入口分别指向自己的 `RoutePaths`。Controller 的 `@RequestMapping` 或 `@GetMapping` 最终常量值没有变化，变化的只是 Java 符号解析路径。原来是“Controller 读根字段，根字段再等于叶字段”，现在是“Controller 直接读叶字段”。编译后的路由字节相同，已有 MockMvc、route inventory、endpoint catalog 和静态样本测试继续锁定完整 URL。

ReleaseAcceptance 是刻意保留的例外。它的 route catalog 输出 `stablePath`、`splitPath`、两个 owner 名和 `matched`，用途就是证明迁移前稳定根面与迁移后叶 owner 仍一致。如果把 catalog 两个输入都机械替换成叶 owner，`matched` 会变成恒真，响应却继续声称比较了根 owner，这比保留少量别名更糟。第一次完整 verify 中 v1840 历史结构门发现了这个问题。本版因此恢复 `BASE_PATH` 与十一条 catalog 路由作为兼容字段，catalog 再次用根值和叶值两侧输入；其他 family 仍禁止返回根转发层。这个修复没有改变任何响应值，反而让原有兼容证明恢复真实含义。

这次调查特别强调跨行语法。Java 注解经 Spotless 格式化后可能写成 owner、点号和字段名分处三行，简单 `rg` 单行表达式看不到完整引用。新的判断先对整个文件文本做跨行匹配，再由完整编译做最终裁决；根 owner 的机械门还会提取全部 `static final String` 字段，并要求源码集合中存在 `OpsShardReadinessRoutePaths.FIELD` 形式的读者。门固定字段数为十五，因此未来新增无读者字段、重新放回叶转发字段、或悄悄删除仍在使用的根字符串都会失败。

入口边界仍然是只读与写操作分离。readiness、evidence、rehearsal、catalog、receipt 和 handoff 路由只汇总数据，不因为 owner 迁移获得服务调用、凭据解析、SQL、部署或回滚能力。failed-event replay 的写入口继续经过操作员上下文、角色矩阵、审批状态和 digest 校验。v1867 没有新建路由，没有删除公开路由，也没有改变 HTTP 方法。

## 响应模型

响应模型本身没有修改。`OpsEvidenceResponse` 仍是根 evidence 服务与多个已抽取 family 共享的只读聚合信封；发布清单、回滚证据、secret source contract、审批 fixture 和 rehearsal 提示仍使用原 record 与列表次序。测试拆分之所以围绕 scenario support，而不是再造一套 expected builder，是为了避免出现两份“正确答案”。`OpsEvidenceContractTestSupport` 只负责创建服务和返回场景对象，断言仍在各自测试中直接表达。

`OpsEvidenceServiceTests` 留下核心聚合和入口级断言，确保发布脚本使用的 `-Dtest=...OpsEvidenceServiceTests` 不失效。`OpsEvidenceReleaseContractTests` 接管 release manifest、deployment rollback、handoff checklist、audit retention、operator signoff、rollback approver、approval record、production secret source 和 deployment runbook 等契约。它验证 endpoint、contract version、枚举值、required checks、no-secret/no-execution 边界和列表顺序，仍然从真实 `OpsEvidenceService` 构造响应，而不是读取人工复制的 JSON。

rehearsal 提示拆分同样按行为分界。摘要测试关注 warning digest 的输入顺序、schemaFields、proofClaims 和 nodeVerificationActions，因为这些内容对 Node 消费端是有序契约；契约测试关注不同 header 输入如何形成 request id、operator identity、audit correlation、贡献列表和失败分类。两类测试共用已有 `ReleaseApprovalRehearsalTestSupport.defaultRehearsal()`，默认场景只定义一次。这样当构造过程变化时，场景修复只发生一处，而摘要与边界仍由两组独立断言审查。

拆分后的最大测试文件为六百九十九行，低于八百行治理线，也没有形成三个结构相似的新测试壳。`JavaMaintainabilityBudgetTests` 同时约束生产与测试的最大行数、超过五百行的文件数以及超过七百五十和一千行的文件数。新测试不是为了让统计好看而拆成空文件，每个文件都有完整、可读、可定位失败的职责。

## 上游证据配置

Java evidence 会引用 Node、mini-kv 和静态 contract 的版本、endpoint 与 digest，但这些引用是只读事实，不是远程调用指令。v1866 已把静态发布工件拆成 `StaticReleaseCatalog` 与 `StaticReleaseSections`，v1867 保持这两个内部 owner 不变。所有 `/contracts/*.json` 路径、fixture 内容、schema version 和 SHA 相关断言继续由原测试保护。路由 owner 直连不会重写这些静态资源，也不会移动任何历史归档目录。

跨项目成熟度标签更新为经过授权的精确文字：`single-project validation + verified read-only cross-project integration (env-gated, single machine, no execution authority)`。这句话的依据是 Node 所有的 C1-C4 capstone 曾用固定 Java commit 启动真实 jar，读取健康与 ops evidence，执行真实 mini-kv CLI，读取 aiproj 已登记制品，并验证无写边界。它不表示 Java 自己开始管理 Node 或 mini-kv 进程，也不表示跨项目运行进入默认 CI。本版只把已经由外部评审授予的事实同步到 README 和生产边界文档。

上游配置继续遵守保守默认值。prod profile 关闭 H2 console、SQL 显示与格式化；RabbitMQ transport 和通知消费者需要显式启用；CI 冒烟关闭短生命周期不需要的 Outbox publisher。release rehearsal 中出现的 credential handle、endpoint handle、rollback path 和 timeout budget 都是审核字段或回显值，不会触发 resolver、网络连接或 SQL。`credential value` 仍被明确列入禁止读取和禁止输出的边界。

归档也是一种上游契约。Node 中存在指向 Java 历史路径和 digest 的固定引用，所以 v1867 的 archive retention 不是“整理旧文件”，而是冻结当前文件集合。`archive-retention-census.ps1` 扫描 `a`、`b`、`c`、`d`、过渡截图根、`e`、`f` 以及全部代码讲解续写目录，使用长路径安全的 SHA-256，只有显式 `-WriteManifest` 才写清单。第一次远端实现 CI 发现 Windows working tree 的文本通常是 CRLF，而 Linux checkout 是 LF，原字节摘要会把同一 Git 内容误报为篡改。修复后 `.md`、`.json`、`.html` 只在摘要输入中把 CRLF 规范为 LF，PNG 等二进制仍按原字节；路径集合、正文变化、文件数和原始总字节上限继续严格检查。默认 census 只读，不会在普通验证中重排历史文件。

## 服务层核心流程

典型只读流程仍是 Controller 接收 HTTP 请求，Spring 从 route owner 解析映射，Controller 调用 family service，service 从 catalog、fixture 或其他只读 service 组合响应，最后由 Jackson 输出 record。v1867 只改变第一步的常量 owner 和测试组织，不改变 service 构造器、事务边界、repository 调用或响应 builder。订单、库存、支付、Outbox、通知和 failed-event 领域服务完全不在本版修改范围内。

`OpsEvidenceService` 是有意保留的跨族组装器。它同时服务根 evidence 入口、overview 以及已抽取的 release/readiness 支持，因此不是单一 family 残留。v1867 把它的巨型测试拆开，却没有为了追求更小数字继续切生产服务，因为当前四百三十九行已经低于治理线，且再拆可能制造只为测试而生的公开 seam。维护门把它的命名单文件 cap 从一千五百三十行压到四百三十九行，未来只能缩小。

release rehearsal 的内部流程从 request headers 生成规范化 request，再构建基础提示、receipt chain、failure taxonomy、warning digest 和 no-ledger-write proof。v1837 已经用一个权威 chain 消除大规模参数扇出；v1867 的测试拆分正好按这条内部流程分层。摘要测试审查“chain 产生了什么有序文本”，契约测试审查“输入上下文怎样影响 chain 与边界”。这使失败位置更接近责任，而不是在八百多行总览中搜索某一个列表差异。

failed-event 写流程继续与上述只读流程隔离。管理、申请、审批、拒绝和重放各自检查允许角色；申请人不能审批自己的申请；重放还要重新检查审批、digest 和 readiness。未经认证的写探针在 capstone 中应在业务服务前被拒绝。本版没有引入绕过入口，也没有把 evidence 中的 `ready` 字段解释成执行许可。

## Java 证据检查

Java 自身的证据分为结构、语义和运行三层。结构层由 `ops-root-census.ps1` 证明根包仍是 104 个文件、保留数 104、可移动数 0、未分类数 0；由 maintainability census 证明生产最大七百三十八行、测试最大六百九十九行；由命名 census 统计超四十字符的存量文件名与标识符，形成只减不增 baseline。新增和触碰的命名必须在四十字符内，不能靠提高 baseline 吸收坏名字。

语义层由现有 route、MockMvc、response、digest、fixture 和 no-write 测试组成。v1867 的聚焦组同时运行核心 evidence、release contracts、rehearsal digest、rehearsal contract、优雅门和维护预算门。路由迁移必须经过完整 test compile，因为跨行引用调查的教训说明纯文本搜索不能替代编译器。Spotless 负责统一 import 和换行，SpotBugs 继续要求零新发现，排除表的六百八十六个存量 Match 只能减少。

运行层由完整 `mvnw -B verify` 和远端 GitHub Actions 提供。默认 headless job 执行 Spotless ratchet、完整 verify、JaCoCo、SpotBugs 和 prod profile jar 冒烟；Docker-tagged job 单独执行 Testcontainers，并保持可识别的可选隔离。workflow 使用官方当前 major：checkout v7、setup-java v5、upload-artifact v7。closeout 测试会拒绝旧 v4 引用，真实 Actions run 再证明新 major 在仓库权限与缓存设置下可用。

E1-E10 不是文档打勾表。`docs/java-track-final-evidence.md` 为每一门列出实现证据、会失败的机械表面和候选状态。构建、分析、覆盖、安全、可观测、错误处理、文档诚实、发布纪律、代码健康和归档保留必须分别有测试或 CI 失败点。最终状态不能由本次执行者自封，外部评审要重新跑 census、verify、检查 tag/CI，并用最终 Java tag 重跑跨项目 capstone。

## mini-kv 证据检查

mini-kv 在本项目中的作用是只读上游证据源和 capstone 的真实 CLI 参与者。Java 的静态 evidence 可以描述 mini-kv readiness、slot 或 no-execution receipt，Node 的 capstone 可以执行固定路径的 `minikv_cli` 获取新鲜输出；Java 不直接启动、停止、写入或管理 mini-kv。本版没有修改 `D:\C\mini-kv`，没有移动 Java 中被 Node 固定引用的历史文件，也没有改变任何跨项目 schema。

检查 mini-kv 证据时要区分“内容被验证”和“执行被授权”。CLI 输出中的 `read_only`、`execution_allowed:false` 可以证明该次报告声明了无执行边界，但不能证明生产写路由已准备好。Java rehearsal 引用这些字段，只把它们放进 handoff 或 proof claim。任何 deployment、rollback、SQL、secret、managed audit connection 或 active shard router 都仍需独立计划、身份、审批和真实环境验证。

归档清单会保护 Java 侧历史中涉及 mini-kv 的引用。若某个旧讲解或证据文件被删、重命名或改字节，`ArchiveRetentionTests` 会先发现实际文件集合与 manifest 不相等，或 SHA-256 不匹配。文件数和总字节数还有只减不增上限，因此未来不能在普通功能版中无限堆积大截图和重复报告。真正新增归档必须先有外部计划明确用途，并在新版本中显式更新预算和索引。

这套边界让 Java、Node、mini-kv 可以继续并行维护：内部重构只改自己的仓库，契约或归档变化才按依赖顺序协调。v1867 属于 Java 内部治理，没有要求 mini-kv 配合，也没有把 Node 的计划文件写回 Java。跨仓真实复核由 Node 一条命令承担，Java 只提供固定 tag 的 jar 和既有只读接口。

## 阻断与安全边界

生产边界文档新增威胁模型，把仓库能控制和不能控制的责任拆开。伪造操作员的风险由动作级角色、申请审批分离、digest 和 readiness 复核缓解，但生产认证、令牌签发和组织权限仍属于外部身份系统。credential 泄漏风险由只回显 handle、字段名和阻断状态缓解，但 secret manager、轮换和吊销不在仓库内。证据被误当执行令牌的风险由所有 rehearsal/evidence 接口只读和 `execution_allowed=false` 缓解，但控制面仍必须实施独立审批。

支付继续使用 `SIMULATED` provider，不代表真实资金流。Outbox 与消息消费代码具备应用级幂等和失败记录，但 RabbitMQ 集群的容量、可用性、灾备和告警不是本仓库的完成项。Flyway 只提供正向 migration，rollback SQL 仍是 review 材料；真实回滚、备份恢复和 DBA 审批没有被 Java 自动化。managed audit 的连接、credential resolver 的真实实现、raw endpoint resolution 和 deployment execution 全部关闭。

E6 的“错误处理”也不能被误写成所有外部客户端都已有 timeout。当前生产候选没有打开真实 managed audit 或 secret provider client，因而它们不存在可验证的网络 timeout；这正是边界，而不是缺一行配置。仓库可验证的是 graceful shutdown、三十秒 shutdown phase、统一 API 异常、请求 trace/span、failed-event 明确失败分类，以及 rehearsal 中有限 timeout budget 的只读回显。未来一旦加入真实 client，连接、读取、重试和熔断 timeout 必须作为新功能的阻断条件。

本版自己的失败条件同样严格：根包不是 104、route owner 不是二十七个有读者字段、ReleaseAcceptance catalog 没有真实比较根与叶、route 字节或响应断言变化、任一 Java 文件超过七百五十行、命名 baseline 变宽、覆盖 floor 降低、SpotBugs Match 增加、归档集合或 hash 漂移、讲解晚于最终 verify、CI 未绿、tag 未推送，任一发生都不能宣布版本完成。外部评审未通过时，最强措辞只能是候选。

## 测试覆盖

第一轮聚焦验证已经证明四组拆分后的业务测试与两组治理测试能共同运行。它覆盖核心 evidence 聚合、发布与回滚静态契约、rehearsal 摘要顺序、header 和贡献边界、根 route reader、命名 baseline、SpotBugs baseline、生产/测试行数预算及命名热点 cap。测试拆分没有修改 assertion 值，第一次优雅门失败也被保留为口径校正证据：它发现路由直连增加了显式叶 owner 引用次数；第二次失败又发现 PowerShell `Measure-Object -Line` 与 Java `Files.lines()` 对空行口径不同。最终脚本和 JUnit 使用一致的长路径、区分大小写标识符与真实行数定义。

最终 verify 之前先完成文档、讲解和 archive manifest。原因是 walkthrough gate 必须检查最终文本，归档 manifest 又必须包含冻结后的 v1867 讲解。如果先 verify 再扩写讲解，验证的就不是提交内容；如果先写 manifest 再继续修改讲解，hash 会立刻失效。正确顺序是冻结本文、生成 manifest、加入 archive 测试，再运行 Spotless、聚焦门和完整 verify。

JaCoCo floor 从全局 0.96 提到 0.97，root 0.85 提到 0.86，catalog/common/inventory/notification/ops/order/outbox/payment 各提高一个百分点；readability 保持 0.98，因为它已接近实际覆盖率。提高依据是 v1866 完整基线有超过两个百分点余量，不能在失败后往下调。完整 verify 会重新计算 v1867 的实际 missed/covered 数，并由 `jacoco:check` 对每个 package 失败。

远端需要两轮证据。第一轮是实现提交触发的 headless 与 Docker jobs，既验证 action major 升级，也独立复现 Linux 下的格式、测试、覆盖、分析和 prod smoke。记录 run id 后再提交账本与最终证据、打 v1867 closeout tag、一次推送 commit 和 tag；第二轮 closeout CI 证明账本更新没有破坏门。只有两轮都绿、工作树干净、tag 在 canonical `javaproject` 可达，才到外部评审检查点。

## 一句话总结

v1867 的核心不是再增加一层 readiness，而是让现有工程更直接、更可复现：七百三十五处读者直接依赖真实 route owner，二百三十九个无价值转发消失，十二个有兼容证明职责的字段被明确保留，两个巨型测试按职责拆开，覆盖率、行数、命名、静态分析、归档和 E1-E10 都变成只会收紧的机械门。

它同时保留最重要的诚实边界：跨项目联调已经有真实只读复现，但仍是 env-gated、single machine、no execution authority；支付、credential value、managed audit connection、SQL、部署和回滚没有被悄悄打开。最终判断权留给外部评审，执行者只交付可独立复现的候选证据。
