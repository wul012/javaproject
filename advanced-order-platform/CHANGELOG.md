# Changelog

本项目的版本化证据以 git tag 为权威来源。Maven artifact 当前保持
`0.1.0-SNAPSHOT`，因为本仓库仍处在高频工程演进阶段，尚未切换到语义化制品发布。
每个可追溯版本必须有对应 git tag、提交、测试证据和必要的中文代码讲解。

## v1805 - Candidate document registry package extraction

- Moved the entire candidate-document registry family — 57 non-controller
  implementation files plus the family route-path class
  `OpsShardReadinessCandidateDocumentRoutePaths` — into the new
  `ops.maintenance.candidatedocument` subpackage, reducing direct root `ops`
  Java files from 1,240 to 1,183 (the largest single reduction in the
  consolidation program). The eight public `@RestController` classes and the
  global `OpsShardReadinessRoutePaths` aggregator stay in root.
- The family route-path class was made public with a public `BASE_PATH` and
  public suffix constants; the relocated services were repointed from the
  package-private aggregator to the family route-path class. Dependency injection
  is intra-family, so the family moved as one unit with no cross-package wiring.
- Handled the one genuine cross-family edge: two candidate-document catalogs
  reference the compared-evidence candidate-intake-preflight catalog route, which
  the aggregator previously defined inline. That constant now lives in the
  candidate-document route-path class and the aggregator delegates to it, so the
  compared-evidence family keeps the same value through the aggregator.
- Relocated 19 SpotBugs EI_EXPOSE_REP/REP2 exclusions across 9 candidate-document
  response classes to the new fully-qualified names. Two shared test-support
  classes used by retained root tests were made public.
- Added `docs/ops/candidate-document-extraction-v1805.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1805Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1240 to 1183.

## v1804 - Signed approval route-path consolidation

- Moved three signed-approval route-path classes
  (`OpsShardReadinessSignedApproval{ArtifactDraftReadiness,CaptureArtifactPreflight,CapturePreflight}RoutePaths`)
  into the new `ops.maintenance.signedapproval` subpackage, reducing direct root
  `ops` Java files from 1,243 to 1,240 while keeping the total ops file count
  stable. This is the first pure route-path leaf consolidation (no service,
  controller, or response moves) and stands up the signedapproval subpackage for
  later migration of the operator-evidence-value-supply signed-approval registry
  families.
- Made the three route-path classes and their
  `OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_*` suffix constants public
  (behaviour-neutral; values unchanged). Each class is referenced directly only
  by the root `OpsShardReadinessRoutePaths` aggregator (which still delegates the
  matching public suffix) and its single `...RoutePathsTests` guard; both were
  repointed by import only. The registry services/controllers that own those
  routes stay in root and continue to read the suffixes through the aggregator,
  so every endpoint string is byte-identical.
- Added `docs/ops/signed-approval-route-path-consolidation-v1804.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1804Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1243 to 1240.

## v1803 - Sandbox connection registry package extraction

- Moved twenty-six sandbox connection implementation files (two sibling registry
  sub-clusters — the blocked-execution-context dossier and the precheck
  upstream-receipt verification manifest — that share one route-path class) into
  `ops.maintenance.sandboxconnection`, reducing direct root `ops` Java files from
  1,269 to 1,243 while keeping the total ops file count stable. This is the
  second dependency-injected "evidence" registry family extracted and the largest
  single root-pressure reduction so far.
- Made the family route-path class public with its public `BASE_PATH`/suffixes;
  both root controllers and the aggregator import it; the moved
  services/catalogs/support import the public `OpsEvidenceService` and
  `ReleaseApprovalRehearsalResponse` types they previously referenced same-package.
  The routes
  `/api/v1/ops/shard-readiness/sandbox-connection-blocked-execution-context-normalization-dossier`
  and
  `/api/v1/ops/shard-readiness/sandbox-connection-precheck-upstream-receipt-verification-manifest`,
  both response shapes, and read-only flags are byte-identical.
- Made the single shared schema-version constant
  `RELEASE_APPROVAL_REHEARSAL_MANAGED_AUDIT_SANDBOX_CONNECTION_PRECHECK_PACKET_ECHO_RECEIPT_SCHEMA_VERSION`
  on `OpsEvidenceService` public (immutable string, behaviour-neutral) so the
  moved support can read it; the moved test supports reuse the already-public
  `OpsEvidenceServiceTestFixtures`, and both route/controller tests stay in root
  and construct their service directly through that fixture.
- Relocated the moved Responses' accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` (eleven entries) to the new FQN (same accepted
  findings, none new); the `ReleaseApproval*SandboxConnection*Records` exclusions
  stay in root because those records do not move.
- Added `docs/ops/sandbox-connection-extraction-v1803.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1803Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1269 to 1243.

## v1802 - Credential resolver disabled fake harness evidence archive package extraction

- Moved eleven credential resolver disabled fake harness evidence archive
  implementation files into `ops.maintenance.credentialresolver`, reducing direct
  root `ops` Java files from 1,280 to 1,269 while keeping the total ops file
  count stable. First dependency-injected "evidence" registry extracted (second
  family outside CodeWalkthrough).
- Made the family route-path class public with its public `BASE_PATH`/suffix;
  the root controller and aggregator import it; the moved service/catalogs import
  the public `OpsEvidenceService` and `ReleaseApprovalRehearsalResponse` types
  they previously referenced same-package. The route
  `/api/v1/ops/shard-readiness/credential-resolver-disabled-fake-harness-evidence-archive`,
  response shape, and read-only flags are byte-identical.
- Made the shared test helper `OpsEvidenceServiceTestFixtures` public so the
  moved package-local test support can reuse it; the root service/controller test
  now constructs the service directly through that fixture.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/credential-resolver-disabled-fake-harness-evidence-archive-extraction-v1802.md`
  plus `ReadabilityUpkeepOpsConsolidationExtractionV1802Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1280 to 1269.

## v1801 - Screenshot explanation archive registry package extraction

- Moved ten screenshot explanation archive registry implementation files into
  `ops.maintenance.screenshotexplanationarchive`, reducing direct root `ops`
  Java files from 1,290 to 1,280 while keeping the total ops file count stable.
  This is the first extraction outside the CodeWalkthrough family.
- Mirrored the v1797–v1800 recipe: made the screenshot explanation archive
  route-path class public (with its own `BASE_PATH`), repointed the moved service
  to it, made `ENDPOINT` public, moved the package-local service/renderer/
  boundary/immutability/closeout/f-root-policy/test-support tests into the
  subpackage; the segmentation docs, controller, and route-path tests stay in
  root (controller/route-path tests construct the service directly). The route
  `/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/screenshot-explanation-archive-extraction-v1801.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1801Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1290 to 1280.

## v1800 - Code walkthrough depth registry package extraction

- Moved eight code walkthrough depth registry implementation files into
  `ops.maintenance.walkthrough.depth`, reducing direct root `ops` Java files
  from 1,298 to 1,290 while keeping the total ops file count stable. This
  completes moving all four CodeWalkthrough registry families (compliance,
  quality gate, quality audit, depth) out of the root package.
- Mirrored the v1797–v1799 recipe: made the depth route-path class public (with
  its own `BASE_PATH`), repointed the moved service to it, made `ENDPOINT`
  public, moved the package-local service/renderer/boundary/test-support tests
  into the subpackage; the root controller and route-path tests construct the
  service directly. The route
  `/api/v1/ops/shard-readiness/code-walkthrough-depth-registry`, response
  version, read-only flags, and root controller entry point are byte-identical.
- Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in
  `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, none new).
- Added `docs/ops/depth-registry-extraction-v1800.md` plus
  `ReadabilityUpkeepOpsConsolidationExtractionV1800Tests`, and lowered the
  governance ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` from 1298 to 1290.

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
