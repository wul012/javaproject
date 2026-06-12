# 第一千七百九十四版代码讲解：生产可观测性补齐

## 入口路由

本版本的入口路由仍然保持克制，没有新增任何会改变业务状态的接口。真正被补齐的是运行诊断入口，也就是 actuator 的 `health`、`info` 和 `metrics` 三个端点，以及所有请求进入 Web 层后能够形成的 trace/span 日志链路。J4 playbook 要求补齐 observability，本项目在执行前已经存在 `spring-boot-starter-actuator`，默认配置里也已经暴露了 `health,info,metrics`，并且早已有 `/actuator/health` 的集成测试。因此本版本没有重复做“加 actuator”这种表面动作，而是把已有入口的可验证范围补完整：默认 profile 和 prod profile 都显式声明相同暴露范围，测试覆盖 `info` 和 `metrics`，并把请求相关性接进异常日志。

入口路由可以分成两类。第一类是只读诊断入口：`/actuator/health`、`/actuator/health/liveness`、`/actuator/health/readiness`、`/actuator/info`、`/actuator/metrics`。这些入口只返回运行状态或指标清单，不接受写请求，不触发重放，不读凭据值，也不做部署或回滚。第二类是业务入口的错误链路，例如 `/api/v1/orders` 在请求 header 或请求体不合法时会进入 `ApiExceptionHandler`。过去这些错误能转成 ProblemDetail，但日志里没有统一 trace/span，排查时只能靠时间戳、线程名和猜测去对应客户端报错。本版把 Micrometer Tracing Brave bridge 接入后，真实 HTTP 请求触发校验错误时，异常日志会带上当前请求 traceId 和 spanId。

这里需要强调一个边界：J4 没有把 traceId 放进错误响应 body。这样做是有意的。错误响应一旦新增字段，下游调用方可能把它当成稳定契约使用，后续再调整就会牵动 Node 或其他消费者。本项目当前目标是运行诊断和日志排障，不是定义新的跨项目错误协议。因此 trace/span 进入日志，不进入 ProblemDetail。调用方仍然收到原来的 `VALIDATION_FAILED`、`MISSING_HEADER` 或业务错误标题，服务端运维人员则可以在日志中按 traceId 追踪这次请求。

## 响应模型

本版本的响应模型主要有三个层次。第一层是 actuator 响应。健康端点返回 `status=UP`，liveness 和 readiness 也必须返回 UP。`/actuator/info` 当前可以是空对象，但它必须稳定返回 200，因为后续如果要挂版本、构建时间、Git commit 或发布策略，应该从这个入口扩展。`/actuator/metrics` 返回 `names` 列表，测试中检查其中至少包含 `jvm.memory.used`，这不是为了绑定所有指标名称，而是证明 metrics 端点确实由 actuator 暴露并可读。

第二层是日志响应，也就是服务端写出的日志事件。`logging.pattern.level` 中增加 `traceId` 和 `spanId`，让日志级别位置带上请求相关性。这个配置对普通启动日志也生效；没有请求上下文时 trace/span 为空，这是符合预期的。真正需要关注的是请求线程中的异常日志。新增的 `ApiExceptionTraceIntegrationTests` 通过真实 HTTP 请求触发订单创建校验错误，然后断言捕获日志中有 32 位十六进制 traceId 和 16 位十六进制 spanId，并且不是 `unavailable`。这说明不是单元测试手动塞 MDC 才能通过，而是 Micrometer/Brave 在真实请求中确实建立了上下文。

第三层是异常响应本身。`ApiExceptionHandler` 原有的 ProblemDetail 结构没有扩大。业务异常仍然使用业务 code 作为 title，校验异常仍然使用 `VALIDATION_FAILED`，缺失 header 仍然使用 `MISSING_HEADER`。本版只在这些 handler 内增加日志语句，记录错误类型、错误数量、header 名称、业务 code、HTTP 状态和 trace/span。这样开发者排查问题时可以从日志里知道是哪一类错误、多少个字段错误、对应哪个请求上下文，而调用方不会因为 J4 被迫适配新的 JSON 字段。

## 上游证据配置

上游计划来自 `D:\nodeproj\orderops-node\docs\plans2\v2114-governance-consolidation-pointer.md` 指向的 Java production excellence playbook。J4 明确要求 actuator 暴露 health、info、metrics，使用 Micrometer Tracing Brave bridge 而不是已经过时的 Spring Cloud Sleuth，并确保 `ApiExceptionHandler` 的日志包含 trace id。这个要求属于 Java 本项目内部质量建设，不改变跨项目 evidence schema，不需要 mini-kv 或 Node 同步，也不会影响 Node 当前的治理渲染器整合。

执行过程中发现实际情况比 playbook 起点更新：本项目已经有 actuator dependency 和 health endpoint 测试，也已经在 J3 的 prod profile smoke 中使用 `/actuator/health`。因此 J4 的重点从“添加 actuator”调整为“确认并加固 actuator 暴露范围”。`application.yml` 和 `application-prod.yml` 都保留 `management.endpoints.web.exposure.include=health,info,metrics`，避免生产 profile 漂移。`management.tracing.sampling.probability=1.0` 放在两个 profile 中，是为了当前没有外部 tracing 后端时，日志仍然能稳定记录每个请求的 trace/span。

本版本没有修改 Node 的计划文件，没有移动 Java 的历史归档目录，也没有触碰 mini-kv。此前已经确认 Node 对 Java 和 mini-kv 的历史 evidence 路径存在硬编码引用，所以 Java 侧做任何生产卓越工作都必须避开 `a/` 到 `f/` 的旧归档移动。本版新增的工程证据只放在 `docs/production-observability-v1794.md` 和当前代码讲解续写目录中，属于 Java 仓库自己的维护记录。

## 服务层核心流程

服务层核心流程从请求进入 Tomcat 开始。Spring Boot 3.5 的 Web 观测能力会在请求生命周期中建立 observation，Micrometer Tracing Brave bridge 会提供 tracer，并把当前 span 的标识写入 MDC。日志 pattern 从 MDC 读取 `traceId` 和 `spanId`。因此只要某个请求进入应用并触发日志，日志行就能携带当前请求上下文。这个流程不需要 Zipkin、Tempo 或外部 collector；它先解决本项目最基础的本地排障能力。以后如果接入外部 tracing 后端，也可以在这个底座上继续扩展，而不是重写异常处理器。

`RequestLogCorrelation` 是本版新增的一个小工具类。它只做一件事：从 MDC 读取 `traceId` 和 `spanId`，如果没有值就返回 `unavailable`。这个类故意保持很小，包内可见，不引入业务依赖，也不把 Tracer 注入到每个异常处理方法。原因是异常处理器真正需要写日志时，日志系统已经通过 MDC 暴露了当前相关性；直接读 MDC 更接近最终日志行为，也方便测试 fallback。把这段逻辑集中起来，可以避免每个 handler 里重复写空值判断。

`ApiExceptionHandler` 的处理流程则是在原有 ProblemDetail 组装前先记录日志。业务异常记录业务 code 和 HTTP status；请求体校验异常记录 field error 数量；约束校验异常记录 violation 数量；方法参数校验异常记录参数校验结果数量；缺失 header 记录 header 名称。每条日志都带 `traceId` 和 `spanId`。这比只在日志 pattern 中显示 trace/span 更稳，因为即使未来日志格式调整，结构化消息本身仍然包含相关性字段。

actuator 流程没有被扩权。`ActuatorHealthIntegrationTests` 原本只测 health、liveness、readiness。现在新增 `infoAndMetricsEndpointsStayExposedForRuntimeDiagnostics`，请求 `/actuator/info` 和 `/actuator/metrics`。metrics 响应中必须有 `names`，并且包含 JVM 内存指标。这条测试证明暴露范围不是只写在 YAML 里，而是在真实 Spring Boot Web 环境中可访问。它没有测试写入口，因为 actuator 的这三个端点本来就是只读诊断面。

## Java 证据检查

Java 证据第一组是 Maven 依赖。`pom.xml` 新增 `io.micrometer:micrometer-tracing-bridge-brave`，版本由 Spring Boot 3.5.9 的 dependency management 管理。这里没有引入 Spring Cloud Sleuth，因为 Sleuth 已经不适合 Boot 3 体系。也没有加入 Zipkin reporter，因为 J4 目标是本地日志相关性，不是外部链路平台接入。这个选择保持了本项目当前运行成本和依赖面克制。

第二组证据是配置。`application.yml` 和 `application-prod.yml` 都有 actuator 暴露范围和 tracing sampling 配置。默认 profile 让本地开发能看到同样的日志相关性，prod profile 让 CI smoke 和未来生产启动不会因为 profile 覆盖而丢失 observability。`logging.pattern.level` 会输出 trace/span，使日志肉眼扫描时能直接看到相关性。J3 已经关闭 prod profile 的 H2 console 和 SQL 调试输出，J4 没有回退这些安全边界。

第三组证据是异常处理器。`ApiExceptionHandler` 增加 SLF4J logger，并在五类异常路径都写入 trace/span。这里没有把日志写成 error 级别，因为很多 400 类输入错误属于调用方输入问题，不应污染 error 告警。使用 warn 更合适：它足够显眼，能被排障看到，又不会把每个坏请求都当成服务崩溃。业务异常的 HTTP status 仍由 `BusinessException` 自己携带，不在 J4 中重解释。

第四组证据是测试。`ObservabilityConfigurationTests` 确认 Tracer bean 存在，sampling probability 和 log pattern 配置存在。`RequestLogCorrelationTests` 覆盖 MDC 有值和无值两条路径。`ApiExceptionHandlerTests` 在手动 MDC 下验证约束校验异常日志包含指定 trace/span。`ApiExceptionTraceIntegrationTests` 则走真实 HTTP 请求，证明请求线程中的异常日志能产生真实十六进制 trace/span。`ActuatorHealthIntegrationTests` 覆盖 health/info/metrics。五个测试组合起来，覆盖了依赖、配置、工具类、异常处理和真实 Web 入口。

## mini-kv 证据检查

本版本不消费 mini-kv 证据，也不启动 mini-kv。J4 是 Java observability 版本，目标是把本项目运行时诊断和请求错误日志相关性补齐。它没有改 read-only shard map，没有改 slot table preview，没有改 mini-kv 的 WAL、snapshot、RESP 命令或 C++ 文件结构。按照四项目统筹规则，这类非契约内部质量工作可以在 Java 仓库独立推进。

这个“不涉及”不是偷懒，而是边界控制。用户之前明确要求主要做自己的项目，讲解不能硬凑。mini-kv 未来确实也需要自己的 production excellence 工作，例如文件拆分、archive retention、C++ 单元测试和命令解析维护，但那些应当由 mini-kv 仓库自己推进。Java 本版只需要说明自己没有跨项目写入，没有移动历史 archive，没有要求 Node 或 mini-kv 配合。

## 阻断与安全边界

J4 的安全边界可以概括为“只读诊断、日志相关、不扩执行”。actuator 只暴露 `health,info,metrics`，没有暴露 `env`、`beans`、`heapdump`、`threaddump`、`logfile`、`shutdown` 等更敏感端点。`info` 当前不写敏感字段，`metrics` 只作为运行指标入口。这个暴露范围已经由配置和集成测试共同固定，未来如果有人扩大 exposure include，应该配套说明原因和风险。

trace/span 也不等于授权。它只能帮助定位请求，不能证明操作者有权限，不能绕过 failed-event replay approval，不能读取 credential value，不能打开 raw endpoint，不能触发 managed audit connection，不能执行 deployment 或 rollback。失败事件相关的 ORDER_SUPPORT、SRE、SYSTEM 角色矩阵仍然由原有配置和服务层控制。J4 没有改变任何角色列表，也没有把 traceId 当成审计凭据。

异常日志也做了克制。我们没有在每个 controller 或 service 到处加日志，而是选在 `ApiExceptionHandler` 这个统一出口。这样坏请求和业务异常都有统一相关性，而正常请求不会因为 J4 大量增加噪声。后续如果要做访问日志、审计日志或业务事件日志，应该另开版本，明确字段、采样、脱敏和保留策略。

## 测试覆盖

本版本已经通过聚焦测试命令：`.\mvnw.cmd -B "-Dtest=ApiExceptionHandlerTests,RequestLogCorrelationTests,ActuatorHealthIntegrationTests,ObservabilityConfigurationTests,ApiExceptionTraceIntegrationTests" test`。第一次新增真实 HTTP trace 测试时，我尝试用 W3C `traceparent` 固定 traceId，但实际 Brave bridge 在当前默认传播配置下生成了新的 traceId。这说明测试如果强绑传播格式会过度承诺。本版及时调整为验证“真实请求中存在合法 trace/span 且不是 unavailable”，不把跨系统传播格式混进 J4 的目标。这个修正是工程判断，不是降低质量。

聚焦测试最终结果是 7 个测试通过。它们覆盖 `/actuator/health`、`/actuator/health/liveness`、`/actuator/health/readiness`、`/actuator/info`、`/actuator/metrics`，覆盖 Tracer bean，覆盖 sampling 和 logging pattern，覆盖 MDC 读取和 fallback，覆盖手动 MDC 下的异常日志，也覆盖真实 HTTP 校验错误下的自动 trace/span。后续还需要跑 Spotless、walkthrough compliance、完整 `verify`、docker profile verify、prod smoke 和远端 GitHub Actions，这些是版本收尾门，不能因为聚焦测试通过就省略。

## 实际工作量说明

本版本的实际工作量不是“加一个依赖”这么小。真正的工作包括：识别 actuator 已存在这一事实，避免重复建设；把默认 profile 和 prod profile 的暴露范围对齐；引入 Micrometer Tracing Brave bridge；设计一个小而集中的 MDC 相关性工具；让五类异常处理路径都写入 trace/span；补 actuator info/metrics 集成测试；补 tracing 配置启动测试；补工具类 fallback 测试；补异常处理器日志捕获测试；补真实 HTTP 请求下的 trace/span 测试；再把 README、工程证据文档、进度表和本篇讲解一并更新。

这里特别说明“禁止硬凑”。本项目这一版没有为了凑字数去增加无关业务功能，也没有把 mini-kv 或 Node 的工作强行写进 Java 变更。篇幅来自真实工程动作：依赖选择、配置边界、日志相关性、异常出口、actuator 只读范围、测试失败后的传播格式修正、以及后续维护风险。J4 的价值在于排障时能从一个 400 错误日志追到具体请求上下文，同时不扩大执行面、不改变下游契约。这正是生产后期保养应该做的扎实小底座。

从维护角度看，这一版还处理了一个容易被忽略的问题：可观测性如果只停留在配置文件中，后续开发者很难判断它是否真的进入了请求线程。很多项目会写上诊断配置，却没有测试证明错误出口能拿到请求上下文；一旦线上出现调用方报错，排障人员仍然只能靠时间和接口名称去猜。本项目这次把配置、日志格式、异常出口和真实请求测试串起来，形成了一条很短但完整的证据链。请求先进入应用，应用为请求建立上下文，异常处理器在统一出口记录上下文，测试再捕获日志确认上下文存在。这个闭环比单独增加一行配置更有维护价值。

另一个维护点是控制噪声。可观测性不是日志越多越好，也不是所有端点都暴露才叫完整。后期工程最怕把诊断能力做成新的风险入口，所以本版只选择健康、信息和指标三个读入口；异常日志只放在统一处理器，不在每个业务方法里到处散落。这样一来，日常日志不会被普通成功请求淹没，坏请求又能留下足够线索。未来如果需要更细的业务审计，可以在单独版本里设计字段、脱敏、采样和保留策略，而不是在本版顺手扩张。

## 一句话总结

v1794 把本项目的 actuator 诊断面和请求错误日志相关性补成可测试能力：health/info/metrics 稳定只读暴露，Micrometer/Brave 提供 trace/span，异常处理器日志能按请求追踪，同时不打开任何部署、回滚、凭据、SQL 或失败事件执行边界。
