# Changelog

本项目的版本化证据以 git tag 为权威来源。Maven artifact 当前保持
`0.1.0-SNAPSHOT`，因为本仓库仍处在高频工程演进阶段，尚未切换到语义化制品发布。
每个可追溯版本必须有对应 git tag、提交、测试证据和必要的中文代码讲解。

## v1795 - Production readiness documentation discipline

- 新增 `PRODUCTION_READINESS.md`，集中记录生产边界、运行 profile、消息、支付、
  failed-event replay、release approval rehearsal、credential、SQL、部署和回滚限制。
- 新增 changelog 版本策略，明确 git tag `vNNNN-*` 是当前权威版本号，pom 仍保持
  `0.1.0-SNAPSHOT`。
- 新增文档守卫测试，防止 CHANGELOG、PRODUCTION_READINESS 和 README 指针漂移。

## v1794 - Production observability tracing

- 增加 Micrometer Tracing Brave bridge、trace/span 日志 pattern 和异常处理器日志相关性。
- 明确 actuator 只暴露 health、info、metrics，并补真实 HTTP trace/span 日志测试。

## v1793 - Production profile and request validation hardening

- 新增 `application-prod.yml`，关闭 H2 console 和 SQL debug 输出，启用 graceful shutdown。
- compose 凭据改为环境变量覆盖，新增 `.env.example`。
- 订单与 failed-event 写请求补充 Bean Validation 边界和 ProblemDetail 映射。

## v1792 - Coverage ratchet

- 新增 JaCoCo 基线和 package-level coverage floors。
- CI 上传 JaCoCo artifact，docker profile 不再代表覆盖率门。

## v1791 - Static analysis ratchets

- 新增 Maven Enforcer、Spotless ratchet 和 SpotBugs baseline。
- CI 开始阻断新增格式和静态分析问题。

## v1790 - CI bootstrap

- 新增 Maven wrapper。
- Docker/Testcontainers 测试与默认 headless suite 分离。
- GitHub Actions 工作流开始运行默认 verify 和 docker profile verify。

## v1789 - Java ops governance consolidation roadmap

- 新增 Java ops package 整合路线图和 ratchet 方向。
- 明确不得移动 `a/` 到 `f/` 历史归档及 evidence JSON。

## v1788 - Readability upkeep audit closeout

- 完成 readability upkeep audit closeout 证据。
- 记录 v1784-v1788 可读性保养周期结果。

## v1787 - Readability docs guard

- 增加可读性文档守卫，确保维护地图、归档布局和讲解规则可追踪。

## v1786 - Readability audit registry

- 增加 readability upkeep audit registry，让后期维护入口、边界和测试证据集中可查。
