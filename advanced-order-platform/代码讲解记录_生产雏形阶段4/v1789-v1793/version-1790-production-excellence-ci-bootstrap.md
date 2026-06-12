# 第一千七百九十版代码讲解：生产卓越 J0 CI 基线启动

本版承接 Claude 新给出的生产卓越并行模型，继续只做 Java 本项目。Claude 的建议很明确：三个 Codex 会话可以并行，各自读自己的 playbook，但 Java 和 mini-kv 不能回写 Node 仓库；Java 进度要记录在本仓库自己的 `docs/production-excellence-progress.md`。因此 v1790 的目标不是继续做业务功能，也不是立刻重构 ops 根包，而是执行 Java playbook 的第一个里程碑 J0：Maven Wrapper、Docker 依赖测试隔离、CI workflow 使用 wrapper、target 跟踪审计和本地进度记录。

这个版本非常基础，但很关键。Java 项目已经有大量测试和版本证据，过去也已经能在本机跑完整 Maven 测试；但没有 Maven Wrapper 时，CI 和新机器会依赖外部 Maven 安装。Docker 依赖测试没有统一标签时，默认测试会在无 Docker 环境里打印一堆 Testcontainers 错误日志，虽然很多情况下能跳过，但边界不够清晰。J0 要解决的就是“让普通 CI 默认跑 headless suite，让 Docker 集成测试进入明确 profile”。这不是新功能，而是后续静态分析、覆盖率和重构的底座。

## 入口路由

v1790 不新增任何 HTTP 路由。当前业务和 ops endpoint 都保持不变，`/api/v1/ops/readability/upkeep-registry`、`/api/v1/ops/readability/upkeep-audit` 也没有改路径。J0 的入口是构建入口：本地和 CI 都应该通过 Maven Wrapper 执行，而不是依赖机器上恰好安装了哪个 Maven。

CI workflow 的入口仍然位于仓库根 `.github/workflows/maven-ci.yml`。Claude playbook 原文建议创建 `.github/workflows/java-ci.yml`，但本项目已经有 root workflow。为了避免重复 CI 文件造成两个 workflow 同时跑同一套测试，本版选择更新已有 workflow：保留 Java Maven CI 的位置，把 job 改成 `build-test` 和 `docker-tests` 两个入口。第一个入口执行 headless verify，第二个入口执行 `docker-tests` profile，初期设置 `continue-on-error: true`，符合 playbook 对 Docker job 可选和渐进收紧的要求。

本版也新增了 `docs/production-excellence-progress.md` 作为进度入口。由于 playbook 文件在 Node 仓库里，Java 会话不能回写 Node，所以 Java 自己记录偏差和进度。这个入口后续会告诉 Claude：J0 是否完成、J1 是否开始、覆盖率基线是否测出、J6 为什么仍然 blocked。

## 响应模型

本版不新增响应模型，不改业务 DTO，不改任何 JSON schema。生产卓越 J0 属于构建与测试分层，不应该影响运行时响应。如果为了记录 CI 状态新增一个 ops response，反而会违背当前目标：停止不必要的治理链增长。

配置层面的“模型”体现在 Maven POM。`pom.xml` 新增 Surefire 配置属性：默认 `surefire.excludedGroups=docker`，默认 `surefire.groups` 为空；`docker-tests` profile 则设置 `surefire.groups=docker` 并清空 excluded groups。这样 JUnit 5 的 `@Tag("docker")` 成为正式测试分层信号。默认 verify 跑非 Docker 测试，显式 profile 才跑 Docker 集成测试。

这个配置选择比用 `-Dtest=!ClassName` 更稳。旧 workflow 通过排除四个类名来绕过 Docker 测试，这种方式脆弱：新增第五个 Testcontainers 测试时，很容易忘记更新排除列表。标签模型则相反，测试自己声明依赖 Docker，构建系统根据标签分组。后续新增 Docker 测试只要加 `@Tag("docker")`，默认 CI 就不会误跑它。

## 上游证据配置

本版上游证据来自 `D:\nodeproj\orderops-node\docs\plans\production-excellence-java-playbook.md`。这个 playbook 规定 Java 会话只在 `D:\javaproj\advanced-order-platform` 工作，Node playbook 只读，进度写到 Java 自己的 `docs/production-excellence-progress.md`。J0 的要求包括 Maven Wrapper、Docker dependent tests tagging、CI workflow、target 是否被 git 跟踪审计，以及 J0 绿灯后让 Claude 做第一次 review。

实际检查发现一个偏差：playbook 的 verified starting facts 说没有 CI，但当前 Java 仓库根目录已经有 `.github/workflows/maven-ci.yml`，并且之前多次远端 CI 已经成功。v1790 没有强行新增第二个 workflow，而是在 progress 文件的 Deviations 里记录这个事实，并更新既有 workflow。这样做比照字面新建更稳，因为它尊重当前仓库现实。

另一个上游事实是 `target/` 未被 git 跟踪。J0 要求检查 target 是否被 git tracked；本版用 `git ls-files` 检查，没有发现 `target/` 跟踪文件。项目内 `.gitignore` 也已经忽略 `target/`。因此本版不需要执行 `git rm -r --cached target`，只在 progress 里记录审计结果。

## 服务层核心流程

服务层代码没有变化。v1790 的核心流程是构建流程：开发者或 CI 调用 Maven Wrapper，Surefire 根据标签选择测试集合，默认 verify 不跑 Docker 标签，docker profile 才跑 Docker 标签。Workflow 的 `build-test` job 使用 `./mvnw -B -q verify` 加 JVM 参数，`docker-tests` job 使用 `./mvnw -B -q -P docker-tests verify`。

四个 Testcontainers 测试类被加上 `@Tag("docker")`：Postgres migration、RabbitMQ outbox publisher、RabbitMQ notification consumer、RabbitMQ notification failure。它们仍然保留 `@Testcontainers(disabledWithoutDocker = true)`，因为标签负责 Maven 分组，Testcontainers 注解负责运行时 Docker 可用性。两层并存，边界更清楚。

Maven Wrapper 生成时也出现一个实际过程问题。第一次 `mvn -q wrapper:wrapper` 超时，并留下一个 Maven Java 进程；清理后用带输出的命令重跑，发现 PowerShell 把未加引号的 `-Dmaven=3.9.9` 后半段当成 lifecycle phase。最终用正确引号 `"-Dmaven=3.9.9"` 成功生成 wrapper，并把 distribution 固定到 Maven 3.9.9。这个过程写进 progress 的 Deviations，方便后续复盘。

## Java 证据检查

Java 证据第一层是 Maven Wrapper 文件：`mvnw`、`mvnw.cmd` 和 `.mvn/wrapper/maven-wrapper.properties`。properties 固定 wrapperVersion 和 Maven distribution。CI 会对 `mvnw` 执行 `chmod +x`，避免 Windows 提交后 Linux runner 没有执行权限的问题。后续本地验证也会优先使用 `mvnw.cmd`。

第二层是 POM。新增 Surefire plugin configuration 和 `docker-tests` profile，构建层面明确了默认 suite 与 Docker suite 的边界。这个改动比 workflow 排除类名更内聚，因为同一个 Maven 配置同时服务本地和 CI，不需要每个调用者手动记住排除列表。

第三层是测试标签。四个 Docker 依赖测试类都添加 `org.junit.jupiter.api.Tag` import 和 `@Tag("docker")`。这样测试依赖变成类自身的声明，不再藏在 workflow 的 `-Dtest=!ClassName` 中。后续 review 一眼就能看出哪些测试需要 Docker。

## mini-kv 证据检查

mini-kv 在本版中不参与。生产卓越 playbook 虽然是三项目并行模型的一部分，但每个会话只改自己的仓库。Java J0 不读取 mini-kv 代码，不修改 mini-kv 文档，不启动 mini-kv，也不新增任何跨项目证据链。

保留这个说明是为了避免误解。CI bootstrap 是 Java 本项目的质量底座，不是四项目联合验证。mini-kv 会有自己的 playbook 和自己的 progress 文件，Java 不替它记录进度。Claude 需要 review 时，会分别看每个仓库自己的成果。

本版也不打开 Node 工作区写入。虽然 playbook 位于 Node 仓库，Java 只读它，并把进度写到 `advanced-order-platform/docs/production-excellence-progress.md`。这正是 Claude 刚才补充的 refinement：三会话并行，但不要写同一个 repo。

## 阻断与安全边界

v1790 阻断第一类风险是默认 CI 误跑 Docker 测试。默认 Surefire 排除 `docker` 标签，workflow 的 headless job 不需要 Docker。Docker job 单独 profile，且初期 continue-on-error，避免因为 runner Docker 波动阻塞主线。等 Docker job 稳定后，后续可以取消 continue-on-error。

第二类风险是 workflow 继续用系统 Maven。Maven Wrapper 确保 CI 和本地使用同一 Maven 发行版，减少“本机能跑、远端不一致”的问题。CI 现在用 `./mvnw`，不再直接调用 `mvn`。这对后续 Enforcer、SpotBugs、JaCoCo 都是基础。

第三类风险是误操作 Node 或 mini-kv。Progress 文件明确 source playbook 是 Node repo 文件，但 Java progress 在本仓库；本版没有改 Node，也没有改 mini-kv。既有 archive 禁令仍然有效，本版不移动 `a/` 到 `f/`、不移动 `e/<version>/`、不改 evidence JSON。

## 测试覆盖

本版需要三层验证。第一层是 wrapper 自身：运行 `.\mvnw.cmd -B -q verify`，证明默认 headless suite 在无 Docker profile 下通过。第二层是 Docker profile 切分：运行 `.\mvnw.cmd -B -q -P docker-tests ...` 在本机如果 Docker 不可用，应只运行 Docker 标签测试并由 Testcontainers 跳过或报告可用性；远端 docker-tests job 初期 continue-on-error。第三层是 CI：push 后 GitHub Actions 的 build-test 必须通过，docker-tests 作为独立 job 观察。

本版还会跑原有可读性讲解门禁，确保 `version-1790-production-excellence-ci-bootstrap.md` 仍满足中文长文、标准标题、实际工作量、本项目和禁止硬凑要求。CI 基建版本也不能短写，因为它影响后续所有版本质量。

如果 wrapper verify 暴露某些 Docker 测试仍在默认 suite，说明标签或 Surefire 配置不正确；如果 docker profile 跑不到任何测试，说明 groups 配置不正确；如果 CI 找不到 `mvnw` 或无执行权限，说明 workflow 需要补 chmod 或 git file mode。J0 的价值就在于提前把这些问题找出来。

## 实际工作量说明

v1790 的实际工作量包括读取生产卓越 Java playbook、确认本仓库已有 CI 与 playbook 事实的偏差、生成 Maven Wrapper、修正 PowerShell wrapper 参数问题、配置 Surefire 默认排除 Docker 标签、添加 `docker-tests` Maven profile、给四个 Testcontainers 测试类加标签、更新 GitHub Actions workflow 为 wrapper 双 job、审计 target 是否被 git 跟踪、新增 Java 本地 progress 文件，并编写本篇中文讲解。

这不是硬凑。用户要求继续项目工作并说明何时让 Claude review，Claude 明确说第一个 review checkpoint 是 Java CI milestone landed。J0 的真实工作就是让 CI milestone 具备可 review 的证据：wrapper、profile、workflow、progress、测试标签和远端 CI。没有这些，后续 J1 静态分析、J2 覆盖率和 J6 ops consolidation 都缺少稳定底座。

本项目的后续重构压力很大，尤其 ops 包已经有一千多个类。没有 CI 和测试分层，直接开始拆包很危险。J0 先把默认无 Docker suite 稳住，把 Docker 测试放到明确 profile，后续改动才能更快判断是代码坏了，还是环境缺 Docker。这个边界对长期推进非常关键。

本版也没有把 Docker 测试删除或弱化。它只是把它们分层。Docker 测试仍然存在，仍然可以通过 `-P docker-tests` 执行。默认 CI 不跑它们，是为了让 headless regression 成为可靠主门。Docker job 初期 continue-on-error，是为了先观察稳定性；后续如果稳定，再升级为必过门禁。

Progress 文件的新增也很重要。因为 Java 不能回写 Node playbook，若没有本地 progress，Claude review 时只能靠聊天记录判断。现在 Java repo 自己记录 J0 状态、偏差和证据，后续每个里程碑都可以更新同一个文件。这让三会话并行模型真正可执行。

这一版还有一个更深的工程意义：它把“能在我机器上跑”改成“能被仓库自己说明并重复执行”。过去本地机器安装了 Maven，测试也能跑，远端也有一个已有流程，所以看起来好像没有问题。但生产卓越要求的不是偶然可用，而是让新环境、新会话、新审查者都知道应该怎样跑。Wrapper 解决工具版本，标签解决测试分层，进度文件解决跨会话交接，workflow 解决远端复现。四个点合在一起，才算 J0 的真实完成。

如果没有这一版，后续 J1 静态分析会很别扭。比如 SpotBugs 或格式检查失败时，我们首先要确认是不是工具版本不同；覆盖率失败时，要确认是不是 Docker 测试在默认套件里误跑；重构 ops 包时，要确认失败来自业务测试还是环境问题。J0 把这些基础疑问提前压下去，后续每个里程碑才能把注意力放在真实代码质量上。

默认排除 Docker 标签并不代表降低测试标准。恰恰相反，它让测试标准更明确。普通提交必须先过无外部依赖的主回归；需要容器的测试进入单独通道，后续可以观察稳定性，再逐步变成必过门禁。这样既保护主线速度，也没有把集成测试删除掉。对于一个已经有很多历史治理测试的项目来说，清晰分层比把所有测试混在一起更稳。

进度文件中的偏差记录也不是形式。Claude 的 playbook 是从某个时间点观察得到的，仓库已经继续前进，事实发生变化很正常。重要的是不要假装完全一致，而是把偏差写入本仓库。已有根目录工作流、目标目录没有被跟踪、第一次 wrapper 命令因为参数和超时失败，这些都是真实执行过程。记录下来，后续 review 就能看到我们是按实际情况调整，而不是机械照抄。

本版还给“什么时候请 Claude review”定了标准答案：不是代码刚写完，不是本地某个 focused test 刚过，而是 J0 头提交已经推送，远端工作流使用 Wrapper 成功完成，Java progress 文件写明 J0 完成证据。Claude 要 review 的应该是可复现的仓库状态，而不是半成品工作树。这个判断能减少来回沟通，也能让 review 更有质量。

从维护角度看，J0 也保护了后续并行模型。Node、Java、mini-kv 三个会话同时跑时，最怕一个会话的成果只存在本地，另一个 planner 无法判断状态。Java 本地 progress 文件让 Java 会话自给自足，Node playbook 只读，中央 planner 需要同步时再读取 Java 进度。这样三方不会互相写文件，也不会争抢同一个 repo。

本项目后续还有很多硬任务：静态分析、覆盖率、配置安全、观测性、发布纪律、ops 包收敛。任何一个任务都可能触发大量测试。J0 的价值就是先把测试运行方式固定住。等到后续真正拆 `OpsEvidenceService` 或整理 readiness 家族时，CI 能告诉我们合同是否仍然稳定，而不是让我们先花时间猜构建环境。

还需要说明的是，基础设施版本最容易被低估。它不像业务功能那样有新页面或新接口，也不像重构那样能马上减少文件数量，但它决定了后面每一次修改是否可信。一个项目如果没有统一的构建入口、没有清楚的测试分层、没有可追踪的进度记录，那么后续再多功能也会变成个人机器上的经验。v1790 把这些经验写进仓库，让后来者可以按同样方式复现，这就是工程化的价值。

这版也让审查边界更清楚。审查者不需要猜哪些测试需要容器，不需要问本地装了哪个构建工具，不需要翻聊天记录确认进度，也不需要担心 Java 会话去修改 Node 文件。所有信息都在本项目里：构建脚本在仓库内，测试标签在测试类上，进度在文档里，远端流程在工作流里。这样的结构虽然朴素，但非常适合长期并行推进。

最后，这一版给后续节奏定下了稳态：先让主线构建可靠，再加静态分析，再量覆盖率，再谈更大的重构。顺序不能反。没有主线构建，静态分析只是本地命令；没有测试分层，覆盖率会混入环境噪音；没有进度文档，外部审查会反复追问状态。v1790 把这些问题提前处理掉。

这个顺序本身就是质量保障。

余量必须充足可靠。

这很重要。

最后，本版会在 push 后观察 GitHub Actions。只有 wrapper workflow 在远端成功，才算 J0 landed。到那时，就应该让 Claude 做第一次 review checkpoint。也就是说，不是在本地提交后立刻叫 Claude，而是在远端 CI 对 J0 头提交完成后再叫。这样 Claude review 的对象是可复现的远端状态。

## 一句话总结

v1790 执行生产卓越 Java playbook 的 J0，把 Maven Wrapper、Docker 测试标签、Surefire profile、wrapper CI workflow、本地进度记录和 target 跟踪审计落到本项目，为后续静态分析、覆盖率和 ops 收敛建立可 review 的 CI 基线。
