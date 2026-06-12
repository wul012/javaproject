# 第一千七百九十三版代码讲解：生产配置与输入边界硬化

## 入口路由

本版本的入口不是新增业务路由，而是把已有入口的生产运行边界补齐。入口分成三条线。第一条线是运行入口，也就是 `application-prod.yml` 和 GitHub Actions 的 prod profile 启动冒烟。它要求本项目在 `--spring.profiles.active=prod` 下可以启动 jar，并且能够通过 `/actuator/health` 给出健康响应。第二条线是本地基础设施入口，也就是 `compose.yaml` 和 `docker-compose.yml`。过去两个 compose 文件里都直接写了 `order_app` 密码，虽然这是本地开发默认值，但它让运行方式和凭据边界混在一起。本版改成 `${VAR:-default}` 形式，并增加 `.env.example` 和 `.gitignore` 规则，让本地覆盖值进入 `.env`，不要进入 git。第三条线是 HTTP 写请求入口，主要包括 `/api/v1/orders`、`/api/v1/failed-events/management-status`、`/api/v1/failed-events/{id}/replay`、`/api/v1/failed-events/{id}/replay-approval` 和 `/api/v1/failed-events/{id}/replay-approval/review`。

订单入口原本已经有 `@Valid @RequestBody CreateOrderRequest`，但边界还不够细。`CreateOrderRequest` 有客户编号和订单行列表，订单行也有商品编号和数量。旧版本能拒绝空客户、空列表和非正数量，但没有限制订单行数量，也没有对 `Idempotency-Key` 做 Bean Validation。幂等键是创建订单的关键业务边界，如果为空白字符串，后续服务层仍可能把它当作普通字符串处理，最后错误会变成业务异常或数据异常，而不是明确的输入错误。本版给 `OrderController` 增加 `@Validated`，并把 `Idempotency-Key` 标记为非空且长度不超过 128。这样调用方在进入应用服务前就能收到统一的 `VALIDATION_FAILED`。

失败事件写请求入口更值得加固。此前失败事件的查询条件已经有不少服务层校验，但管理状态批量标记、重放申请、重放审批、真实重放这些写请求的 DTO 本身没有 Bean Validation。服务层确实会再次检查 `ids`、`status`、`note` 和 `reason`，但如果只靠服务层抛 `ResponseStatusException`，Web 边界没有办法给出统一字段错误集合，也不容易被控制器级测试覆盖。本版把 `MarkFailedEventManagementRequest`、`ReplayFailedEventRequest`、`RequestFailedEventReplayApprovalRequest` 和 `ReviewFailedEventReplayApprovalRequest` 都补上约束，再在控制器参数上加 `@Valid`。这不是替换服务层保护，而是在入口处提前挡住显而易见的坏请求。

## 响应模型

本版本的响应模型有两类。第一类是 prod profile 启动响应。CI 在主 job 的 `verify` 之后复用已经打出来的 jar，用 `--spring.profiles.active=prod`、`--server.port=18080`、`--order.expiration.enabled=false` 和 `--outbox.publisher.enabled=false` 启动应用，然后请求 `/actuator/health`。成功时响应是健康检查 JSON，失败时 CI 会上传 `prod-smoke.log` 和 `prod-health.json`。这里没有新增业务 JSON，也没有新增 Node 可消费的 evidence schema。它只证明生产 profile 能在无 Docker、H2 内存库的 CI 环境中启动，并且健康探针能响应。

第二类是输入校验响应。`ApiExceptionHandler` 原来能处理 `MethodArgumentNotValidException` 和缺失 header。订单请求体错误会进入 `MethodArgumentNotValidException`，缺少 `Idempotency-Key` 会进入 `MissingRequestHeaderException`。但当控制器方法参数上的 `@NotBlank` 或 `@Size` 生效时，Spring Boot 3.5 对应的异常可能是 `HandlerMethodValidationException`，老路径也可能出现 `ConstraintViolationException`。如果不显式接住，空白 header 的响应标题、错误类型和字段错误结构可能跟请求体校验不一致。本版把这两类异常也映射为同一个 ProblemDetail：状态码 400，标题 `VALIDATION_FAILED`，type 指向 `https://advanced-order-platform/errors/VALIDATION_FAILED`，并输出 `fieldErrors` 列表。

这种响应模型的价值在于减少调用方猜测。无论是订单行数量超过上限、商品编号为 0、幂等键为空白、失败事件批量标记 ids 为空、重放申请 reason 为空，调用方都会看到同一种错误标题和同一类字段错误信息。业务层仍然保留自己的保护，例如失败事件服务仍会检查是否存在对应消息、操作者角色是否允许、审批状态是否正确、RabbitMQ outbox 是否启用。Web 校验只管格式和显然的边界，不负责判断业务状态。这样职责清楚，后续扩展也不容易把输入错误和业务冲突混成一类。

compose 的响应模型则体现在配置展开结果上。`POSTGRES_DB`、`POSTGRES_USER`、`POSTGRES_PASSWORD`、`RABBITMQ_DEFAULT_USER`、`RABBITMQ_DEFAULT_PASS`、端口变量都可以由 `.env` 或环境变量覆盖。默认值仍保留，方便本地一条命令启动；但真实值不再需要写进 compose 文件。`docker-compose.yml` 的 Postgres healthcheck 也改为读取容器内的 `POSTGRES_USER` 和 `POSTGRES_DB`，避免账号或库名覆盖后 healthcheck 仍然盯着旧默认值。这是小改动，但它让配置行为和运行状态一致。

## 上游证据配置

本版本读取的上游计划仍然是 Java production excellence playbook 的 J3 段。J3 要求增加 `application-prod.yml`，关闭 H2 console、关闭 SQL 展示、关闭 hibernate format_sql，配置优雅停机，记录 prod profile 激活方式，并在 CI 中用 prod profile 启动一次应用。它还要求 docker compose 不再硬编码本地凭据，增加 `.env.example`，观察 scheduler 在 graceful shutdown 下的行为，并审计请求 DTO 的输入校验。实际执行时发现一个偏差：项目已经有 `spring-boot-starter-actuator`，并且已有 `/actuator/health` 集成测试，所以 CI smoke 不需要新增根路径或临时探针。直接请求健康端点更可靠，也更符合后续 observability 路线。

上游证据没有要求改 Node，也没有要求改 mini-kv。本版本属于 Java 本项目内部生产配置和 Web 边界维护，不改变跨项目契约，不改变 readiness JSON schema，不改变历史归档路径，不改变 Node 硬编码引用的 archive 文件。此前已经明确 Java 仓库以后只用 `javaproject` 远端，本版延续这个边界，不使用 `git fetch --all --tags`，不触碰 Node 工作区，也不修改 mini-kv。J3 的证据全部保存在 Java 仓库内部：配置文件、测试、README 说明、`docs/production-hardening-v1793.md` 和本篇讲解。

`docs/production-hardening-v1793.md` 是本版新增的工程证据文档。它把 prod profile 的每一项配置、CI prod smoke 的启动参数、compose credential 边界、scheduler graceful shutdown 观察和输入校验边界集中记录。这样后续 review 不需要只读 diff 猜意图，也不需要从 workflow、YAML、DTO 和测试之间来回跳。文档里明确说明：`OrderExpirationScheduler` 和 `OutboxPublisherScheduler` 都是 Spring 管理的 `@Scheduled` 组件，没有手写线程池、外部进程或无限循环。启用 `server.shutdown=graceful` 后，它们跟随 Spring 应用上下文生命周期停止，30 秒 shutdown phase 是有界等待，而不是无限阻塞。

## 服务层核心流程

prod profile 的核心流程从配置覆盖开始。默认 `application.yml` 面向本地开发，H2 console 开启，hibernate format_sql 开启。J3 不能把默认本地体验直接改掉，因为大量测试和开发命令依赖 H2 快速启动。更合适的做法是在 `application-prod.yml` 中覆盖生产 profile 的行为。运行时激活 prod profile 后，Spring 会先加载默认配置，再加载 prod 覆盖。最终环境中的 `spring.h2.console.enabled=false`、`spring.jpa.show-sql=false`、`spring.jpa.properties.hibernate.format_sql=false`、`server.shutdown=graceful` 和 `spring.lifecycle.timeout-per-shutdown-phase=30s` 都来自 prod profile。新增的 `ProductionProfileConfigurationTests` 直接读取 `Environment` 断言这些值，避免只靠肉眼看 YAML。

CI prod smoke 的核心流程是先通过 `verify` 构建 jar，再启动 jar。这里没有重新运行 Maven，也没有启动 Docker。命令显式关闭 `order.expiration.enabled` 和 `outbox.publisher.enabled`，因为 smoke 的目标是验证 prod profile 能启动和健康检查能响应，不是等待后台扫描任务运行。脚本把应用输出写到 `prod-smoke.log`，记录进程号，循环最多 60 次请求 `http://localhost:18080/actuator/health`。如果健康检查成功，脚本退出 0，并通过 trap 结束后台进程；如果应用提前退出或超过等待次数，脚本打印最后 200 行日志并失败。这个流程把 CI 风险控制在一个短而可诊断的范围内。

输入校验的核心流程分两层。第一层是 Web 参数和请求体的 Bean Validation。订单创建会先检查 header，再检查 request body。失败事件写请求会先检查 body 中的 ids、status、note 或 reason。第二层是服务层业务校验。以失败事件重放为例，DTO 只强制 `reason` 非空，并限制可选字符串长度；`eventId`、`eventType`、`aggregateType`、`aggregateId` 和 `payload` 仍可省略，让服务层回退到已记录的失败事件字段。这保留了旧业务语义。服务层继续检查审批状态、消息是否存在、角色是否允许、RabbitMQ outbox 是否启用。Web 层提前挡住空理由，不会取代服务层对真实状态的判断。

异常处理也做了职责分层。`MethodArgumentNotValidException` 处理请求体字段错误，`ConstraintViolationException` 和 `HandlerMethodValidationException` 处理方法参数错误，`MissingRequestHeaderException` 保持缺失 header 的专门响应。这样空白 header 和缺失 header 是两类问题：缺失 header 是 `MISSING_HEADER`，空白 header 是 `VALIDATION_FAILED`。这个区分对调用方有帮助，因为前者表示没有传字段，后者表示传了但值无效。订单幂等键正好需要这种细分，不能把 `" "` 当成缺失，也不能让它进入业务层。

## Java 证据检查

Java 证据第一组是配置文件本身。`application-prod.yml` 明确关闭 H2 console 和 SQL 调试展示，并打开 graceful shutdown。`management.endpoint.health.probes.enabled=true` 保留健康探针，`management.health.rabbit.enabled=false` 避免基础 prod profile 在未启用 RabbitMQ profile 时被 RabbitMQ 健康状态拖成 DOWN。这个选择延续了默认本地模式的设计：RabbitMQ 业务能力默认关闭，只有激活 rabbitmq profile 才让 RabbitMQ 成为运行依赖。

第二组证据是 compose 文件。`compose.yaml` 和 `docker-compose.yml` 都从硬编码账号密码改成环境变量替换。两个文件都保留默认值，保证本地开发体验不被突然打断，但真实覆盖值可以放进 `.env`。`.gitignore` 新增 `.env` 和 `.env.*`，同时允许 `.env.example` 被提交。这个组合很常见，但对后期维护很重要。没有 `.env.example`，使用者不知道应该设置哪些变量；没有 ignore，真实 `.env` 容易被误提交；只改一个 compose 文件，另一个文件会继续残留硬编码。三者要一起做才完整。

第三组证据是 DTO 和控制器。`CreateOrderRequest` 增加订单行上限，`CreateOrderLineRequest` 增加商品编号正数校验，`OrderController` 增加 `@Validated` 和幂等键约束。失败事件的四个写请求 DTO 增加非空、长度、列表大小和正数约束，`FailedEventMessageController` 对对应 request body 增加 `@Valid`。这批改动没有改返回字段，没有改数据库表，没有改服务方法签名，也没有改历史 fixture。它只把已经存在的业务输入边界前移到 Web 层。

第四组证据是测试。`ProductionProfileConfigurationTests` 检查 prod profile 的关键属性。`OrderIdempotencyBoundaryIntegrationTests` 新增空白幂等键、空订单行、非法数量和订单行过多四个 HTTP 400 场景。`FailedEventOperatorContextIntegrationTests` 新增失败事件管理 ids 为空、重放申请 reason 为空、审批 review status 缺失、重放 reason 为空四个 HTTP 400 场景。这些测试不是只测 Java Bean Validation 注解是否存在，而是通过 MockMvc 真实走控制器和异常处理链，验证调用方最终看到统一 ProblemDetail。

第五组证据是格式和编译质量。初次聚焦测试通过后，编译器提示 `getAllValidationResults()` 已过时。本版没有把这个警告留下，而是用 Spring 6.2 的 `getParameterValidationResults()` 替代。这个细节很小，但符合本项目后期保养原则：新增代码不要一进仓库就带维护债。随后运行 Spotless apply，让改动文件按 google-java-format 整理，再补充 `ApiExceptionHandlerTests` 覆盖 `ConstraintViolationException` 分支。该测试第一次绑定英文校验消息，实际本机返回中文“不能为空”，因此改成只断言字段路径，不依赖 locale。最终 8 个聚焦测试全部通过，且 deprecated warning 消失。

## mini-kv 证据检查

本版本不消费 mini-kv 证据，也不启动 mini-kv。J3 的范围是 Java production profile、compose 环境变量化、Java Web 输入校验和 CI prod smoke。它没有引入 shard readiness catalog，没有改 slot table preview，没有改 read-only shard map，也没有请求 mini-kv 的任何运行时能力。按照四项目统筹规则，这类非契约内部维护可以在 Java 仓库独立推进，不需要 mini-kv 同步。

这条边界很重要，因为过去 Node 曾经硬编码 Java 和 mini-kv 的历史 archive 路径和 digest。Java 这边做生产硬化时，如果顺手整理 `e/<version>/` 或移动旧证据文件，就会让下游校验失效。本版本没有移动 `a/` 到 `f/` 任何历史目录，没有改 evidence JSON，没有把 `.env.example` 或 prod profile 写进历史 archive。新增文档都在 Java 当前维护目录下，新增配置都在应用资源和仓库根文件中，属于本项目自己的运行面。

mini-kv 以后可以做自己的生产硬化，例如 archive retention、C++ 文件拆分、WAL 和 snapshot 验证、命令解析测试等，但那不是本版目标。本版讲解提到 mini-kv，只是为了说明不触碰的范围，而不是为了硬凑跨项目内容。用户之前明确要求主要做自己的项目，禁止硬凑，所以这里保持克制：没有证据就说不涉及，有边界就说明边界，不用无关项目给 Java 版本凑篇幅。

## 阻断与安全边界

本版本的阻断边界可以概括为五个“不打开”。第一，不打开写路由。本版没有新增会改变业务状态的新接口，也没有让现有只读 readiness 接口变成写接口。第二，不打开 credential value。`.env.example` 使用占位值，真实 `.env` 被 ignore；compose 默认值仍是本地开发默认，不是生产秘密。第三，不打开 raw endpoint 或 managed audit connection。CI smoke 只请求本地进程的 `/actuator/health`，没有访问外部系统。第四，不打开 deployment 或 rollback。启动 jar 只是 CI 冒烟，不是部署。第五，不自动启动 Node、Java 长驻服务或 mini-kv。CI 和本地测试启动的进程都在任务生命周期内结束。

输入校验也有安全边界。我们只校验调用方能在请求层明确给出的字段，不把业务状态判断塞进 DTO。比如 replay request 的 `reason` 必须非空，因为没有理由就不应进入真实重放流程；但 `eventType`、`aggregateType`、`aggregateId` 和 `payload` 可以继续省略，因为服务层原本支持从已记录的失败事件取值。这个选择避免了 J3 悄悄收紧老接口，导致合法调用被拒绝。安全加固应当挡住坏输入，而不是借加固之名改业务合同。

prod profile 也保持谨慎。它关闭 H2 console 和 SQL 展示，但不在同一版本引入认证、TLS、外部 secret manager、生产数据库、真实 RabbitMQ 强依赖或云部署配置。那些都是后续更大边界的事情，需要单独版本、单独测试和单独 review。J3 先把最基本的生产 profile 行为做实，把 CI 启动证明补上，再把本地 compose 凭据从文件中抽成变量。这样版本目的单一，失败时也容易定位。

对于 scheduler，本版没有新增自定义线程池。`OrderExpirationScheduler` 和 `OutboxPublisherScheduler` 都是 Spring `@Scheduled` 组件，启动和关闭跟随应用上下文。开启 graceful shutdown 后，Spring 会停止接收新请求并给生命周期组件一个有界停机窗口。CI smoke 显式关闭两个 scheduler，是为了让短生命周期检查聚焦启动和健康检查。文档中说明了这个选择，也留下后续扩展规则：如果以后引入专用 `TaskScheduler` 或手写 executor，就必须补 shutdown/await 测试。

## 测试覆盖

本版本已经完成聚焦测试。最终命令是 `.\mvnw.cmd -B "-Dtest=ApiExceptionHandlerTests,ProductionProfileConfigurationTests,OrderIdempotencyBoundaryIntegrationTests,FailedEventOperatorContextIntegrationTests" test`。第一次运行通过 7 个测试，但暴露出 `HandlerMethodValidationException` 处理中使用了 deprecated API。修复后又运行 Spotless apply，并补充 common 包异常处理分支测试。新增单元测试第一次因为本机中文校验消息和英文断言不一致而失败，随后改为 locale 无关断言。最终结果是 8 个测试、0 failures、0 errors、0 skipped。这个过程说明新配置和新校验不是只写了文件，而是至少通过了启动上下文、MockMvc、异常处理链和本地化消息差异。

订单边界测试覆盖四个新增场景。空白 `Idempotency-Key` 证明方法参数校验和 `HandlerMethodValidationException` 处理路径生效。空 `items` 证明请求体校验仍然工作。数量为 0 证明嵌套订单行校验通过 `List<@NotNull @Valid CreateOrderLineRequest>` 进入内部 record。101 个订单行证明 `@Size(max = 100)` 能阻断过大请求。四个请求都在进入业务处理前返回 400 和 `VALIDATION_FAILED`，这比让服务层在更深处失败更清晰。

失败事件写请求测试覆盖四个新增场景。批量管理请求 ids 为空会被 `@NotEmpty` 拦住。重放申请 reason 为空会被 `@NotBlank` 拦住。审批 review 缺少 status 会被 `@NotNull` 拦住。真实 replay reason 为空也会被 `@NotBlank` 拦住。这些场景都通过真实 HTTP 路由执行，并带有操作者 header，因此也证明新增 DTO 校验没有破坏原来的操作者上下文解析。旧测试里 SRE 和 ORDER_SUPPORT 的角色矩阵仍然存在，说明本版没有改变授权规则。

后续还需要跑完整 verify、Spotless check、SpotBugs、JaCoCo check、Docker profile，以及本地 prod smoke 或远端 CI prod smoke。聚焦测试只能证明 J3 改动本身，不能替代全量质量门。尤其本版改了 workflow，最终仍要以 GitHub Actions 的真实 run 为准。如果远端 CI 报错，我会直接拉日志修，不把失败留给用户处理。只有远端全绿以后，这一版才能算真正收尾。

## 实际工作量说明

本版本的实际工作量不是只加一个 YAML。先读了 Java playbook 的 J3 要求，再用 CodeGraph 查 `OrderExpirationScheduler` 和 `OutboxPublisherScheduler`，确认它们是 Spring `@Scheduled` 组件而不是手写线程池。然后检查 `application.yml`、两个 compose 文件、README、控制器、DTO、异常处理、已有 actuator 测试和失败事件校验测试。实现时新增 prod profile、新增 `.env.example`、同步修改两个 compose 文件、修改 `.gitignore`、给订单和失败事件 DTO 加约束、给控制器加 `@Valid` 和 `@Validated`、扩展统一异常处理、增加 prod profile 配置测试、增加订单输入边界测试、增加失败事件写请求边界测试、补 CI prod smoke、补 README 说明、补 J3 证据文档。

中途还处理了一个维护细节：Spring 6.2 的方法校验异常有新 API，第一次聚焦测试虽然过了，但编译出现 deprecated warning。这个 warning 如果留着，后续升级 Spring 时会变成真实迁移成本，所以本版立即改为 `getParameterValidationResults()`。这体现了本项目后期维护规则：不要为了赶版本把新债务直接带进仓库。用户要求每版要有工程范式，不能小粒度凑数，这里就把配置、校验、测试、CI、文档和证据统一成一版完整硬化，而不是拆成几个只有一两行的小版本。

本篇讲解也遵守中文长篇要求。它不是为了字数而堆词，而是解释每个改动为什么存在、风险在哪里、没有改什么、如何验证、后续怎么维护。禁止硬凑的核心不是少写，而是写的内容必须来自真实工程工作。本版能写出这些内容，是因为确实做了生产 profile、凭据边界、输入边界、异常模型、CI 冒烟和测试覆盖。如果某一版只有一个无关字段或一行注释，就不应该强行写成长篇，而应该继续加大工作量，直到改动本身值得解释。

再从维护者角度补充一点。本版最容易被低估的价值，是把“开发方便”和“生产约束”分开。很多项目早期为了让本地跑得快，会打开调试入口、打印详细语句、使用固定账号、允许较宽松的请求形态。到了后期，如果这些东西仍然混在默认运行方式里，维护者每次上线前都要靠人工记忆去关闭，风险就会随着人员和版本增加而累积。本版没有追求宏大的安全体系，而是先把最基础、最容易验证、最容易遗忘的边界变成配置和测试。这样后续任何人看到生产配置，都能知道哪些能力必须关闭，哪些探针必须保留，哪些后台任务在短生命周期验证里需要暂时停用，哪些请求字段不能再放任进入业务层。

这种处理也能减轻后续重构压力。后面如果继续拆分控制器、拆分失败事件服务，或者整理庞大的运维证据包，输入校验和生产配置不应随着文件移动而丢失。现在这些规则已经落在注解、测试、配置和文档里，迁移时只要测试继续通过，就能证明边界还在。反过来，如果未来某次重构误删了幂等键校验、误开了生产调试入口、误把真实环境变量样例写成可提交文件，相关测试和文档会立刻暴露不一致。后期工程保养要的正是这种可重复提醒，而不是每次靠人重新审一遍所有细节。

本项目当前还有大量历史运维类代码，后续必然会继续拆分和收敛。在这种背景下，生产配置和请求边界看似不是最显眼的功能，却是所有后续重构的地基。没有稳定启动方式，远端质量门就不能可信；没有统一输入错误，前端和调用方就会面对混乱响应；没有凭据边界，任何本地便利都可能被误读成生产做法；没有讲清楚后台任务的停机行为，短生命周期验证和真实运行就会互相干扰。v1793 先把这些地基压实，是为了让后续版本可以更大胆地做结构调整，而不是每次调整都担心运行边界被顺手破坏。

本项目下一步可以在 J4 做 observability，也可以继续把 J3 的远端 CI 结果补到 progress。当前 J3 的本地证据还需要全量验证和远端验证收口。收口后，如果你说不需要 Claude review，我会继续推进下一个里程碑；如果 CI 或契约边界出现我无法独立判定的问题，我会明确告诉你需要 review。

## 一句话总结

v1793 把 Java 本项目从“默认本地配置可运行”推进到“prod profile 可启动、健康检查可验证、compose 凭据可覆盖、写请求坏输入可在 Web 边界统一阻断”的生产硬化阶段，同时保持不触碰 Node、mini-kv 和历史 evidence archive 的安全边界。
