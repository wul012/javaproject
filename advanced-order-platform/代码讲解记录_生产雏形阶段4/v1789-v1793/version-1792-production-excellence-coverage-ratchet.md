# 第一千七百九十二版代码讲解：生产卓越 J2 覆盖率基线与分包地板

## 入口路由

本版没有新增业务 HTTP 入口，也没有改变订单、库存、支付、outbox、notification 或 ops readiness 的请求路径。v1792 的入口是 Maven verify 生命周期和 CI workflow。J1 已经把 Enforcer、SpotBugs、Spotless 接进质量门，J2 在这个基础上加入 JaCoCo，让“测试跑了”进一步变成“测试覆盖了多少代码、覆盖率能不能后退”的可审查事实。

入口文件主要有三处。第一处是 `pom.xml`，它新增 `jacoco-maven-plugin`、覆盖率地板属性、`prepare-agent`、`report` 和 `check` 三段执行。第二处是 `.github/workflows/maven-ci.yml`，它把原来的 `-DargLine` 改成 `-Dtest.jvm.argLine`，并上传 JaCoCo HTML/XML report artifact。第三处是 `docs/coverage/jacoco-baseline-v1792.md`，它把本次真实测量结果记录下来，避免以后只看到一个阈值却不知道阈值从哪里来。

这个入口设计看起来比“加一个 jacoco 插件”麻烦，但麻烦是必要的。JaCoCo 依靠 Java agent 采集覆盖率，agent 参数通常通过 Maven `argLine` 注入；J1 为了节省测试时间，CI 又要传 `-XX:TieredStopAtLevel=1 -Xmx512m`。如果二者都抢同一个 `argLine`，后写入的一方会覆盖前一方。本版把 JaCoCo agent 放在 `jacocoArgLine`，把测试 JVM 参数放在 `test.jvm.argLine`，Surefire 再用 `@{jacocoArgLine} ${test.jvm.argLine}` 做 late evaluation。这样 coverage agent 和性能参数能共存。

## 响应模型

本版没有运行时 JSON 响应模型，响应模型体现在覆盖率报告和 Maven check 结果中。JaCoCo report 产出 `target/site/jacoco/index.html`、`jacoco.xml`、`jacoco.csv` 和 package 页面。HTML 给人看，XML 给 CI 和脚本看，CSV 方便后续轻量统计。CI artifact 会上传 `target/site/jacoco/**`，因此远端失败或成功后都能下载报告，不必只看控制台摘要。

覆盖率响应分成两类。第一类是 baseline response：本次 `clean verify` 跑完 1470 个测试，0 failures、0 errors、0 skipped，得到全局 line coverage 98.18%。第二类是 ratchet response：`jacoco:check` 读取同一份 exec 数据和 POM 中的地板，如果覆盖率低于地板就失败。本版还做了一个临时失败证明，把 outbox 包地板临时提高到 0.99，Maven 明确报出 `com.codexdemo.orderplatform.outbox` 实际 line ratio 0.71，不满足 0.99。这证明包级规则不是文档装饰，而是真的参与构建。

响应模型里最重要的是“全局高分不能替代分包地板”。本项目全局 98.18% 很漂亮，但它主要来自 `com.codexdemo.orderplatform.ops`：该包有 27895 行 covered line，只 miss 92 行，单包覆盖率 99.67%。如果只设全局 0.96，后续 order、outbox 或 notification 退化一点，可能被 ops 包巨大体量掩盖。J2 因此保留 global floor，同时给 root、catalog、common、inventory、notification、ops、readability、order、outbox、payment 都设置单独地板。

## 上游证据配置

本版上游证据来自 Java playbook 的 J2 段落。它要求使用 JaCoCo 0.8.x，绑定 verify，先测真实 baseline，再把 line floor 设置在 baseline 减两个百分点左右，不能以后再降低；还要求 CI 上传报告 artifact，并在 progress table 记录 baseline。如果 ops 包生成式代码主导覆盖率，就要设置有意义的分包地板，而不是只靠一个全局数字。本版逐条执行这些要求。

插件版本来自 Maven Central metadata，`jacoco-maven-plugin` 使用 0.8.15。这个版本写入 POM property，和 J1 的 Enforcer、SpotBugs、Spotless 一样集中管理。集中管理的好处是后续升级不需要在插件块里到处找版本；也方便 Claude 或后续 Codex review 时确认每个质量工具的版本来源。

J1 的远端 CI 结果也是 J2 的上游条件。v1791 的 GitHub Actions run `27408969138` 已经成功，说明 Enforcer、SpotBugs、Spotless 和 Docker-tagged split 在远端成立。J2 因此可以在 progress 中把 J1 改为 completed，再开始记录自己的 coverage baseline。这样版本链是顺的：J0 先有 wrapper 和 CI，J1 再有静态分析，J2 再有覆盖率地板。

## 服务层核心流程

J2 的核心流程从 Maven initialize 阶段开始。`jacoco:prepare-agent` 设置 `jacocoArgLine`，它包含 `-javaagent` 和 `destfile=target/jacoco.exec`。Surefire 执行测试时通过 late evaluation 把这个 agent 参数带进测试 JVM，同时保留 `test.jvm.argLine` 中的性能参数。测试结束后，forked JVM 退出，JaCoCo 才把 exec 数据完整写出。这也是为什么中途观察 `jacoco.exec` 可能看到 0 字节，不能误判为失败。

verify 后半段先执行 `jacoco:report`。它读取 `target/jacoco.exec` 和编译后的 classes，生成 HTML、XML、CSV 报告。本版第一次接入时就遇到一个真实问题：普通 `${jacocoArgLine}` 太早解析，导致 report 阶段提示 missing execution data file。修复为 `@{jacocoArgLine}` 后，重新跑 `clean verify`，`jacoco.exec` 变成 1.1MB，report 正常生成。这段调试本身也是 J2 的实际工程量，因为它确保 CI 上不会出现“测试全跑了但覆盖率报告为空”的假成功。

`jacoco:check` 接在 report 后面执行。它包含一个 BUNDLE 规则和多个 PACKAGE 规则。BUNDLE 全局线覆盖率地板是 0.96；package 地板按本次 baseline 约减两个百分点设置：inventory 0.89、payment 0.90、order 0.87、outbox 0.69、notification 0.77、ops 0.97 等。这样后续如果某个关键包新增未测分支，即使全局数字仍然好看，包级 rule 也会让 verify 失败。

## Java 证据检查

Java 证据第一组是 POM 的属性。`jacoco.bundle.line.minimum`、`jacoco.order.line.minimum`、`jacoco.outbox.line.minimum` 等阈值都在 properties 中，而不是硬塞在深层 XML 里。这样临时证明可以用 `-Djacoco.outbox.line.minimum=0.99` 覆盖，后续提升地板也只改 property。属性命名直观，review 时能看出每个地板对应哪个范围。

第二组证据是覆盖率基线文档。`docs/coverage/jacoco-baseline-v1792.md` 记录了命令、测试汇总、全局和每个包的 missed/covered/line coverage/floor。比如 global 是 missed 559、covered 30215、baseline 98.18%、floor 0.96；outbox 是 missed 38、covered 97、baseline 71.85%、floor 0.69；order 是 missed 33、covered 296、baseline 89.97%、floor 0.87。这些数字能让后续维护者判断地板是否合理。

第三组证据是本地验证。`clean verify` 在 JaCoCo agent 修正后跑完 1470 个测试，0 失败，生成 report。`verify -DskipTests` 复用 exec 数据，确认正常地板全部通过。临时 outbox 0.99 失败证明则确认 package rule 生效。最后再撤掉临时参数跑 `verify -DskipTests`，确认最终配置仍然通过。这个顺序避免了“只看报告不看 check”或“只写 check 不证明会失败”的空洞质量门。

第四组证据是对数字口径的解释。全局 missed 559、covered 30215，看起来本项目已经接近满分，但这个数字不能直接代表所有业务区域。`ops` 包本身 covered 27895 行，占全局 covered 行的绝大多数，且很多类属于 readiness 证据、长响应模型、catalog 和 echo receipt。它们确实被测试覆盖得很充分，但它们的体量会稀释 order、outbox、notification 等业务包的变化。J2 因此把基线文档写成表格，而不是只写一句“覆盖率 98.18%”。维护者以后看到某个包 floor 低，比如 outbox 0.69，就能知道这是当前真实起点，不是允许长期停留的满意线。

第五组证据是 CI artifact 的可复核性。覆盖率报告如果只留在本地 `target`，远端 review 就只能相信提交说明。workflow 上传 `jacoco-report` 后，Claude 或用户可以直接下载 HTML/XML 报告，核对包级数字、类级 missed lines 和 session 信息。这样 J2 不只是“我说测了”，而是把测量结果留给后续 review。对后期工程保养来说，这种可复核性很关键，因为覆盖率地板以后可能要提升，提升依据必须来自可追踪报告。

## mini-kv 证据检查

本版不消费 mini-kv 证据，也不读取、修改或启动 mini-kv。覆盖率是 Java 本项目内部构建质量，不涉及 mini-kv 的 RESP 命令、WAL、snapshot、slot table、shard readiness、fixture 或 archive。按照四项目统筹规则，只有契约、路由、证据 schema 或跨项目 fixture 发生变化时，才需要 mini-kv 同步；J2 没有这些变化。

同样，本版不移动 `a/` 到 `f/` 归档，不移动 `e/<version>/`，不改 evidence JSON。Node 曾经硬编码大量 sibling archive 路径和 digest，所以 Java 质量版本必须克制。J2 新增的是 `docs/coverage` 文档和 Maven/CI 配置，属于 Java 自己的维护平面，不会影响 Node 或 mini-kv 的历史路径。

这也是“只做本项目”的具体体现。我们没有为了让讲解显得跨项目而去 mini-kv 找无关数据；没有启动服务截图；没有把 coverage report 解释成分布式 readiness 证据。J2 的边界就是 Java coverage，边界越清楚，后续 Claude review 越容易核实。

## 阻断与安全边界

本版阻断运行时副作用。没有 write routing，没有 active shard router，没有 credential value，没有 raw endpoint，没有 managed audit connection，没有 deployment/rollback，也没有 Node 自动启动、Java 长驻服务或 mini-kv 自动启动。全量测试会启动 Spring 测试上下文和 H2 内存库，但这是 Maven 测试生命周期的一部分，不是对外运行服务。

安全边界还包括 CI 参数处理。把 `argLine` 改成 `test.jvm.argLine` 不是小修辞，而是防止覆盖率 agent 被外部参数覆盖。后续如果有人想调整 JVM 参数，应改 `test.jvm.argLine`，而不是直接覆盖 `argLine`。这条规则保护 JaCoCo agent 常驻测试 JVM，也保护 coverage check 的可信度。

覆盖率地板本身也有安全边界：它可以升，不能降。`docs/coverage/jacoco-baseline-v1792.md` 明确写了 floors should never be lowered。后续如果某个包重构导致覆盖率提高，应提高对应 floor；如果覆盖率下降，应补测试或解释真实删除逻辑，而不是为了过 CI 下调数字。J2 把这条规则写入文档和 POM，使它成为工程约束。

还有一条边界是“跳过测试不能伪造覆盖率”。本版使用 `verify -DskipTests` 复用已有 `jacoco.exec` 只用于快速验证规则语法和失败证明，不把它当作 baseline 来源。真正 baseline 来源仍然是完整 `clean verify`。这两类命令在文档中分开记录：一个负责测量，一个负责证明地板生效。这样后续维护者不会误以为 `-DskipTests` 可以生成新覆盖率，也不会把旧 exec 数据当成新测试结果。

## 测试覆盖

本版覆盖验证分六层。第一层是 J1 既有门禁仍然存在：Enforcer 继续验证 Java 21 和 Maven 3.9.9，SpotBugs baseline 继续归零，Spotless 仍由 CI ratchet 检查。第二层是 JaCoCo agent 注入：`clean verify` 日志显示 `jacocoArgLine set to -javaagent...target/jacoco.exec`。第三层是测试结果：1470 个测试全绿，无 skipped。第四层是 report 生成：`target/site/jacoco/jacoco.xml` 和 HTML report 存在。第五层是正常 check 通过。第六层是临时高阈值失败证明。

J2 的测试覆盖不是为了追求一个看起来漂亮的百分比，而是为了防止后续版本在无意识中降低关键包测试密度。order、inventory、payment 是核心业务，outbox 是事件可靠性，notification 是 failed event/replay 管理，ops 是 readiness 证据的大包。每个包地板都代表一个维护边界。特别是 outbox 当前 baseline 只有 71.85%，因此它的地板是 0.69，不假装已经很高；但这也给后续版本明确了提高方向。

CI 覆盖也做了配套。build-test job 在 verify 成功或失败后都会尝试上传 `jacoco-report` artifact，失败诊断 artifact 也包含 `target/site/jacoco/**`。这意味着远端 run 成功时可以下载报告核对 baseline，失败时也可以看覆盖率输出，不必重新在本地复现全部二十多分钟测试。

还有一个容易误伤的 CI 边界：`docker-tests` profile 只选择 4 个 Docker-tagged Testcontainers 类，本机 Docker 不可用时这些类还会按 `disabledWithoutDocker` 跳过。因此它不是代表性覆盖率运行，不能执行 JaCoCo check。本版在 `docker-tests` profile 中设置 `jacoco.skip=true`，让 coverage gate 只属于默认 headless suite。这样 Docker job 继续验证隔离边界，build-test job 才负责覆盖率地板。

从测试策略看，J2 也为后续重构留了空间。当前 `notification` 包 missed 357 行，是最大未覆盖业务区；`outbox` 包 71.85% 也说明事件发布路径还有提高空间。J2 不在同一版里补这些测试，因为本版目标是建立测量和阻断机制。如果把补测试、调 coverage、改业务逻辑混在一起，review 会很难判断失败来自工具接入还是行为变化。先建立地板，再分版本提升低包，是更稳的后期保养路线。

后续提升覆盖率时，也要保持小步但不碎的节奏。比如 outbox 可以围绕事件序列化、发布失败、重试边界、空队列行为和幂等发布各补一组测试；notification 可以围绕搜索条件、审批状态、角色矩阵、重放阻断、管理历史和异常消息归档补测试；order 可以围绕重复请求、库存不足、支付重复、状态历史和事务回滚补测试。每一组都应该有真实业务断言，而不是为了提高数字调用几行 getter。覆盖率地板的意义不是追求漂亮百分比，而是倒逼后续版本在改动对应包时留下行为证据。只要这个原则守住，地板逐步提高才有可信度。

本版还刻意没有把覆盖率阈值设得过满。可维护的质量门应该先稳定再收紧。如果第一天就把 outbox 提到九成，当前代码会立刻失败，维护者只能在同一版里仓促补测试或降低阈值，最后反而破坏规则权威。现在的做法是先记录真实起点，再禁止低于真实起点附近的回退。下一批如果补齐 outbox 测试，就把 outbox 地板提高；如果补齐 notification 测试，就提高 notification 地板。每次提高都应该有对应测试和讲解，形成可审查的台阶，而不是一次性口号。

更细地说，覆盖率地板不是替代人工判断，而是让人工判断有稳定参照。维护者看到某个包新增逻辑时，先看这个逻辑属于核心流程、异常流程、配置边界还是展示模型，再决定应该补单元测试、集成测试还是文档说明。地板只负责提醒“不能比过去更差”，不负责判断“现在已经足够好”。这种分工能避免两个极端：一边是只迷信百分比，另一边是完全不量化。J2 选择中间路线，让数字服务于维护，而不是让维护服务于数字。

## 实际工作量说明

本版实际工作量不是简单写一个 JaCoCo 插件块。它先在 J1 远端绿之后继续推进 J2；读取 JaCoCo 最新版本；改造 Surefire `argLine`；第一次 `clean verify` 发现 report 缺 exec；定位到 Maven property early evaluation；改成 `@{jacocoArgLine}`；在被中断后检查残留 Java 进程和 surefire 产物，确认测试仍在推进；等孤立进程自然完成；再解析 coverage XML；再设计全局和包级地板；再证明 outbox 高阈值会失败；再恢复正常 check；再发现 Docker profile 会被 coverage check 误伤并用 `jacoco.skip=true` 划清边界；再写基线文档和 progress。

这不是硬凑。用户要求每版讲解要用中文、至少三千字、字数不够就加大工作量，禁止硬凑。本版的解释能展开，是因为工作本身有真实工程细节：JaCoCo agent 注入、CI JVM 参数隔离、package coverage weighting、ops 包主导全局指标、PowerShell 参数转义、Surefire XML 解析限制、artifact 上传和 ratchet 失败证明。如果没有这些真实工作，只靠“加入覆盖率插件”几个字，既不配做中大版本，也无法支撑后续维护。

本项目在后期保养阶段需要的是这种可复核的质量门。J0 让 CI 能跑，J1 让静态问题不再扩散，J2 让测试覆盖率不再凭感觉。后续 J3/J4/J5 可以继续做 prod profile、安全配置、observability 和 release discipline；J6 再回到 ops 包收敛时，coverage 与 static analysis 会一起保护重构。

## 一句话总结

v1792 把 Java 本项目的测试从“1470 个测试全绿”推进到“JaCoCo 可报告、可上传、可按包阻断回退”的覆盖率质量门，并明确告诉后续维护者：全局 98.18% 不能掩盖包级退化，地板只能升不能降。
