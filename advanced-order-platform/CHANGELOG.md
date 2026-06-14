# Changelog

本项目的版本化证据以 git tag 为权威来源。Maven artifact 当前保持
`0.1.0-SNAPSHOT`，因为本仓库仍处在高频工程演进阶段，尚未切换到语义化制品发布。
每个可追溯版本必须有对应 git tag、提交、测试证据和必要的中文代码讲解。

## v1799 - Code walkthrough quality audit registry package extraction

- Moved eleven code walkthrough quality audit registry implementation files into
  `ops.maintenance.walkthrough.qualityaudit`, reducing direct root `ops` Java
  files from 1,309 to 1,298 while keeping the total ops file count stable.
- Mirrored the v1797/v1798 recipe: made the quality audit route-path class public
  (with its own `BASE_PATH`) and repointed the moved service to it; made
  `ENDPOINT` public; moved the package-local service/renderer/boundary/
  immutability/closeout/test-support tests into the subpackage; the root
  controller and route-path tests construct the service directly. The route
  `/api/v1/ops/shard-readiness/code-walkthrough-quality-audit-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/quality-audit-registry-extraction-v1799.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1799Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1309 to 1298.
- Fixed a latent gate failure inherited from v1798: the v1798 Chinese walkthrough
  had been written after that version's verify and committed without re-running,
  leaving it below the 3000-CJK / Chinese-majority threshold enforced by
  `OpsCodeWalkthroughArchiveComplianceTests`. This version's full verify caught
  it; the v1798 walkthrough was expanded to satisfy the gate and a full
  `mvnw verify` now passes (1495 tests, JaCoCo floors met, SpotBugs/Spotless
  clean).

## v1798 - Code walkthrough quality gate registry package extraction

- Moved ten code walkthrough quality gate registry implementation files into
  `ops.maintenance.walkthrough.qualitygate`, reducing direct root `ops` Java
  files from 1,319 to 1,309 while keeping the total ops file count stable.
- Made the quality gate route-path class public (with its own `BASE_PATH`) so
  the moved service builds the endpoint from its own subpackage route-path
  class; the root `OpsShardReadinessRoutePaths` table still delegates the public
  suffix, keeping the
  `/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry` route,
  response version, read-only runtime flags, and root controller entry point
  byte-identical.
- Moved the package-local service/renderer/boundary/immutability/test-support
  tests into the subpackage; the root controller and route-path tests construct
  the service directly and import the public route-path class (mirroring v1797).
- Added `docs/ops/quality-gate-registry-extraction-v1798.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1798Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1319 to 1309.

## v1797 - Code walkthrough compliance package extraction

- Moved eleven code walkthrough compliance implementation files into
  `ops.maintenance.walkthrough.compliance`, reducing direct root `ops` Java
  files from 1,330 to 1,319 while keeping the total ops file count stable.
- Preserved the existing
  `/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry` route,
  response version, read-only runtime flags, and root controller entry point.
- Added `docs/ops/code-walkthrough-compliance-extraction-v1797.md` plus
  extraction guard tests so future consolidation batches cannot silently move
  archives, reopen runtime boundaries, or grow the root package again.

## v1796 - Ops consolidation inventory baseline

- 新增 `docs/ops/ops-consolidation-inventory-v1796.md`，记录 ops 包当前
  1,352 个主源码文件、1,330 个根包直放文件、1,210 个 Readiness 命名文件。
- 固化 route family、load-bearing archive 和 reduction candidate 清单，为后续
  contract-preserving 拆分提供边界。
- 新增文档守卫测试，确保 J6 盘点、历史归档不搬迁规则和 v1796 不搬类停线可发现。
- 修正本地 Spotless ratchet 默认基准为 `javaproject/master`，与 Java canonical
  remote 规范一致；GitHub Actions 仍按 workflow 显式参数选择 CI 基准。

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
