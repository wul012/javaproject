# v1884 Profile Section 共享渲染引擎代码讲解

## 入口路由

v1884 没有增加新的 HTTP 能力，处理的是三个已有只读登记入口背后的重复渲染实现。第一个入口属于 Candidate Document，返回候选文档的配置节、字段、来源、门禁和 Markdown；第二个入口属于 Signed Approval Draft，返回审批草稿的配置节证据；第三个入口属于 Draft Text Package，返回文本包中 submission 与 compared-evidence 两组配置节。控制器、路由常量、请求方式、响应 record 和事务属性都留在原位，调用者继续访问原 endpoint，不需要知道包内出现了共享引擎。

这条边界很重要。三个入口虽然都生成“标题加字段清单”的 Markdown，但它们不是同一个公开契约：Candidate 和 Draft 的 `RenderedSection` 没有 group，Text Package 的 `RenderedSection` 额外公开 rendererGroup；三者使用的 section 与 field record 也分别属于自己的 Response。若为了复用直接把公共 Response 改成实现某个共享接口，就会让内部重构反向污染 API，并使未来删除引擎变成契约变更。因此本版只在 package-private 的 `ProfileRenderer` 中建立适配层，公共类型完全不知道 `ProfileSections` 存在。

入口稳定性由两层证据约束。原有 controller、route-lock 和 service 测试继续穿过真实对象图，证明 endpoint 与返回状态不变；新增结构门读取三个 Response 和三个 Service 的源码，要求公共模型不导入共享引擎，Service 只调用本包 `ProfileRenderer.render`。这样既防止路由漂移，也防止后来有人为了少写几行映射，把内部 view 重新暴露到公共边界。

## 响应模型

三个响应模型的最终输出总计十九个 `RenderedSection`。Candidate Document 固定五节，Signed Approval Draft 固定五节，Draft Text Package 固定九节。每节都携带 order、code、Markdown heading、Markdown body 和 passed 状态；Text Package 还携带 rendererGroup。标题继续使用 `### ` 前缀，正文继续把字段写成 `- 字段名: 字段值`，字段之间只用一个换行连接，空字段集合仍得到空正文。这里冻结的是完整 record 列表，而不是只断言节数或抽样几个关键词。

共享层使用三个小型不可变 view。`ProfileSections.Section` 保存 order、code、group 和 heading；`Field` 保存 sectionCode、name 和 value；`Rendered` 保存渲染后的中立结果。它们只表达渲染管线真正需要的数据，不复制 source、gate、module、status 等领域属性。order 必须为正数，字符串不能为 null，输入列表进入算法时通过 `List.copyOf` 建立快照。由此，共享层既不会意外持有调用者可变列表，也不会逐渐长成另一个万能 Response。

领域适配器负责最后一公里。Candidate 和 Draft 把 section 转成无 group view，再把 `Rendered` 转回原 `RenderedSection`，补回固定的 `passed`；Text Package 保留 group，并在转回时把 group 放回原 record。这个方向让类型责任清楚：共享引擎只知道“配置节和字段如何形成 Markdown”，领域包知道“哪些配置节可见以及公共响应长什么样”。若以后某一入口增加领域字段，只需修改自己的适配器和契约测试，不必给通用引擎增加无关参数。

## 上游证据配置

每个 Registry Service 的输入仍来自本家族原有 Catalog。section catalog 决定顺序、代码和标题，field catalog 决定字段所属 section、显示名和值；其他 source、gate、module 与 boundary catalog 继续参与响应组装和最终状态判断。v1884 没有把这些 catalog 合并，也没有改变它们的静态数据，因为本刀的抽象对象只是三份重复的“按 sectionCode 聚合字段并生成 Markdown”行为。先把行为边界缩到足够窄，才能判断共享是否真实，而不是用一个大类吞掉三个领域。

输入顺序仍然是契约的一部分。Candidate 与 Draft 按 section catalog 的原顺序输出，每个 section 内按 field catalog 的原顺序写行。共享引擎使用 `LinkedHashMap` 作为分组容器，并从复制后的 field 列表顺序收集，因此同一 section 的字段不会被普通哈希表重新排列。输出又直接遍历复制后的 section 列表，所以没有隐式按 code 或 heading 排序。精确 oracle 会在任一顺序变化时失败。

Text Package 有一条领域特有规则：catalog 中并非每个 group 都属于该只读输出，旧实现只接受 `submission` 和 `compared-evidence`，最后按 order 排序。这个规则没有塞进共享引擎，而是保留在 Text Package 的本地 `ProfileRenderer`。适配器先用不可变白名单过滤 section，再交给引擎渲染，最后按 `Rendered.order` 排序并转回响应。共享行为和领域政策因此分离：引擎可以被三个家族安全复用，而不会偷偷知道审批文本包的业务分组名称。

## 服务层核心流程

旧实现表面上是五个 renderer 文件，实际包含三份同构算法。Candidate renderer 对每个 section 重新扫描完整 field 列表，筛出 sectionCode 相等的条目再连接字符串；Draft renderer 做同样的事；Text Package 则先分成 submission renderer 与 compared-evidence renderer，两者各自重复字段过滤，外层 renderer 再拼接、排序，另有一个 support 只负责标题和正文格式。阅读者要跨越多个长文件，才能确认三份格式是否真的一致。

新流程由一个共享算法和三个薄适配器组成。Service 仍先建立领域 section 与 field 列表，然后调用本包 `ProfileRenderer.render`。适配器把领域 record 映射为 view，`ProfileSections.render` 一次把所有 Field 按 sectionCode 建立索引，再逐个 Section 读取对应字段并生成 `Rendered`。适配器最后恢复领域响应。数据路径可以写成：领域 Catalog 输出 -> 包内 view 映射 -> 共享字段索引 -> 中立 Rendered -> 原公共 record。每个箭头都能在一屏代码内看到，没有反射、字符串字段名或运行时类型判断。

算法复杂度也更透明。旧写法对 S 个 section 各扫描 F 个 field，主要工作量近似 `S * F`；新写法先遍历 F 个 field 建索引，再遍历 S 个 section，近似 `S + F`。目前十九节的数据量不大，性能不是本版的首要理由，但这个形状消除了复制错误：字段格式、空正文和顺序策略只需在一个地方维护。更重要的是，共享类只有六十余行，三个适配器各自保留业务差异，没有用一个三百行以上的“大一统 renderer”换取文件数下降。

空值和孤儿字段的行为由引擎测试明确。Section 的 order 小于一会抛出异常；关键字符串为 null 会立即失败；没有字段的 section 产生空 body；section 列表中不存在对应 code 的孤儿 Field 不会凭空创建输出节。这些条件让错误在最小边界暴露，而不是在字符串连接深处形成难定位的空指针。输入列表复制还意味着调用者在渲染开始后修改原列表，不会改变当前结果。

## Java 证据检查

本版先在 v1883 旧实现上建立三个精确 oracle，再修改生产代码。Candidate 的五个完整 record、Draft 的五个完整 record、Text Package 的九个完整 record都被直接写入期望列表，包含顺序、code、group、标题、正文每一行和 status。旧结构运行六项 Markdown 测试全部通过后，才删除三个 Text Package 分支/support 文件、重命名三个主 renderer 并接入共享引擎。替换后不改期望，原六项再次全部通过，证明新算法复现的是既有行为，而不是新实现自己的解释。

引擎还有三项独立测试：一项验证输入 section 与 field 顺序被保留，一项验证孤儿字段被忽略且空正文合法，一项验证非法 order 与 null 数据立即失败。它们补足领域 oracle 不容易覆盖的通用边界。`ProfileRenderingStructureTests` 再机械检查三个家族各自恰好只有一个 `ProfileRenderer.java`、共享引擎不出现 Candidate 或 Signed Approval 等领域词、公共 Response 不依赖引擎、Service 不绕过本地适配器，以及设计文档记录冻结输出和失败条件。

扩展定向门本轮运行一百八十一项测试，零失败、零错误、零跳过。它除了覆盖十九节真实输出和共享引擎，还包含三个家族的 aggregate、catalog、field、gate、module、source、service、controller 与 route-lock 测试，并把 v1842-v1850、v1866 的历史结构 ratchet、全局 elegance census、名称门和 staged change gate 一起运行。后续完整 release verify 仍是提交前的强制门；本讲解按规范先于它完成，因此不把尚未产生的全量数字提前写成通过。

## mini-kv 证据检查

Profile Section 渲染是 Java 仓库内部的纯数据变换，不需要启动或修改 mini-kv。三个 Service 的输入均来自当前 Java 对象图中的只读 Catalog，没有网络连接、RESP 命令、外部进程或磁盘证据读取。共享引擎只接收两个内存列表并返回一个新列表，既没有 Spring 依赖，也没有基础设施适配器。因此本版不制造跨项目同步版本，也不触碰 `D:\C\mini-kv` 的历史 archive。

这并不表示系统级关系被忽略。mini-kv 的真实运行输出由既有跨项目 capstone 负责验证，Java 当前成熟度仍是“单项目验证加受控只读集成”，并不因为内部 renderer 更优雅就升级生产授权。若本版改变了 Node 消费的 route、JSON 字段或 fixture 字节，就必须回到跨项目计划重新对齐；当前 exact response 测试和公共模型隔离门共同证明这种契约变化没有发生。

从机制上看，本版与 mini-kv 的正确关系是零新输入、零新输出。Java 的输入是已存在的 Catalog record，输出是字节兼容的 Java Response；mini-kv 不在这条函数链上。把“无需交互”写清楚，可以防止维护者为了证明内部重构而擅自启动兄弟项目，也防止未来文档把单仓库绿色误写成整套系统重新验收。

## 阻断与安全边界

三个入口继续是只读登记能力。v1884 没有新增 write routing、active shard router、credential value 读取、raw endpoint 解析、managed audit connection、deployment、rollback 或兄弟项目自动启动。Registry Service 原有的 `@Transactional(readOnly = true)` 和状态判定保持不变；共享引擎是无状态 final 工具类，只有纯函数与不可变 record，没有 Spring Bean、缓存、线程或静态可变集合。

可见性也是安全边界。三个 `ProfileRenderer` 均为 package-private，只有各自 Registry Service 能自然使用；共享 `ProfileSections` 是跨包复用所需的 public 类型，但它的词汇严格领域中立。结构门明确拒绝 `candidatedocument`、`signedapproval`、`RegistryResponse`、`submission` 与 `compared-evidence` 出现在引擎源码中。若有人以后把分组白名单或公共响应类型塞进引擎，测试会失败，提示抽象已经越权。

版本失败条件比“编译成功”更严格：十九个 exact output 中任一字段、换行、顺序、group 或 passed 状态变化即失败；公共 Response 导入共享 view 即失败；任一家族重新出现第二个 renderer 即失败；renderer、ops 或长名 ratchet 放宽即失败；通过修改旧期望、fixture 或归档历史来迁就实现同样失败。只有实现、机械门、完整 verify、两阶段 CI 和不可移动 annotated tag 全部闭环，版本才可成为新的 canonical 基线。

## 测试覆盖

测试结构本身也进行了童子军式整理。三组超长 `...ProfileSectionMarkdownStabilityTests` 改为包内可辨识的 `ProfileMarkdownTests`，三组超长 `...ProfileSectionRendererTests` 改为 `ProfileRendererTests`。名称变短没有删除责任：前者保存完整旧输出 oracle，后者验证直接 renderer 行为；原 aggregate、catalog、service、controller 和 route 测试仍保留。测试文件总数因新增共享引擎边界测试而增加，但长文件名与长标识符总量继续下降。

全局结构数字被写进会失败的门。ops Java 文件上限从一千二百五十一收紧到一千二百四十九；renderer 上限从三十二收紧到三十，总行数从三千四百四十八收紧到三千三百七十二，超长 renderer 文件名从十四收紧到九。`ops-elegance-census.ps1` 新增 `ProfileRendererFiles`，当前必须等于可复现的三个本地适配器。生产名称精确上限收紧为长 stem 一千一百六十三、长标识符出现二万零三百三十四、唯一长标识符二千七百二十二；测试对应为七百五十八、九千九百九十五、三千七百七十八。

名称 baseline 相对 v1883 新增为零、删除二十四项。删除来自生产和测试的旧超长 renderer 身份，不是把长名换到新文件；新类 `ProfileSections`、`ProfileRenderer` 和新测试方法都在四十字符预算内。Spotless 仍以 v1883 annotated tag 剥离后的固定 commit 为比较基准，避免远端分支前移后漏查当前 diff。最终全量门还会覆盖 JaCoCo、SpotBugs、归档摘要、讲解格式、生产 profile smoke 和可执行 jar，从而把局部优雅与系统回归连接起来。

## 实际工作量说明

生产侧新增一个共享引擎和三个薄适配器形状，删除 Text Package 的 submission renderer、compared-evidence renderer 与 support 三个文件，并把三个长主 renderer 改成包内短名。净结果是 ops 文件减少两个、renderer 减少两个、renderer 行数减少七十六、超长 renderer 文件名减少五个。重要的不是数字本身，而是三份字段聚合算法变成一份，两个 Text Package 分支不再靠文件边界表达可数据化的 group 差异。

测试与证据侧新增共享算法三项边界测试、五项结构测试，并把三族旧输出提升为十九个完整 record 的精确 oracle。九处历史 ops 总量门同步收紧，renderer 与名称门同步收紧，可复现 census 增加 Profile 专项指标。设计说明在实现前记录抽象名、数据边界、行为边界、输出边界、依赖方向和兼容范围，满足新 family 先设计后实现的规则；本项目坚持禁止硬凑，讲解在最终 verify 前完成，避免事后根据绿色结果倒填故事。

当前可复现结果是 ops 一千二百四十九个生产 Java 文件、renderer 三十个三千三百七十二行、九个超长 renderer 文件名。生产长 stem、长标识符出现、唯一长标识符为 1163/20334/2722，测试为 758/9995/3778，exact baseline 删除二十四项且新增为零。Renderer 数量第一次达到三分路线的阶段目标，但超长 renderer、ops 总量、长名和大文件目标尚未完成，因此本版只是把一类重复真正收束，不会自称整体 coding brilliant and elegant 已达九分。

## 一句话总结

v1884 在三个公共只读 Profile Section 契约完全不变的前提下，用一个六十余行的不可变共享索引引擎和三个领域适配器替代五个长 renderer 与三份重复算法，并以旧实现先通过、新实现原样再通过的十九节精确 oracle、结构隔离门和只减不增 census 证明这次收敛既更短，也更透明。
