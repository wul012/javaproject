# Production Excellence Progress — Java

Source playbook: `D:\nodeproj\orderops-node\docs\plans\production-excellence-java-playbook.md`.

This file is the Java repo-local progress tracker. The Node playbook is
read-only for Java sessions; do not write progress back into `D:\nodeproj`.

## Deviations

- The playbook's 2026-06-12 starting fact said no CI existed. Current Java repo
  already had root `.github/workflows/maven-ci.yml`; J0 updates that workflow
  instead of creating a duplicate file.
- `target/` is ignored and is not tracked by git.
- The first local wrapper command timed out; the successful rerun fixed Maven
  Wrapper distribution to Maven 3.9.9.
- J7 v1797 closeout was completed by Claude acting as executor (operating-model
  change: the user asked Claude to take over the three projects on 2026-06-14).
  Codex had staged the extraction but left `spotless:check` failing on one moved
  file; the executor ran `spotless:apply` to fix it, then a full `mvnw verify`
  passed all gates (tests + JaCoCo + SpotBugs + Spotless). J7 is a new milestone
  beyond the playbook's J0–J6: J6 produced the inventory/ratchet scaffolding;
  J7 is the first actual ops-class extraction batch.

## Progress

| Milestone | Version(s) | State | Evidence |
| --------- | ---------- | ----- | -------- |
| J0 | v1790 | completed; Claude reviewed PASS | `mvnw` generated for Maven 3.9.9; Docker tests tagged; Surefire excludes `docker` by default; `docker-tests` profile selects Docker-tagged tests; local wrapper verifies passed; GitHub Actions run `27397723739` succeeded; Claude reviewed J0 as PASS |
| J1 | v1791 | completed; remote CI passed | Maven Enforcer requires Java 21 and Maven 3.9.9; SpotBugs first scan found 2602 legacy findings collapsed into a shrink-only baseline; Spotless ratchet defaults to the Java canonical remote locally and CI overrides the baseline explicitly; temporary local formatting violation proved `spotless:check` fails; local Spotless, SpotBugs, focused tests, default verify, and docker profile verify passed; GitHub Actions run `27408969138` succeeded |
| J2 | v1792 | completed; remote CI passed | JaCoCo 0.8.15 added with `jacocoArgLine` + `test.jvm.argLine` split; baseline recorded in `docs/coverage/jacoco-baseline-v1792.md`; global line baseline 98.18% with floor 0.96; package floors cover root/catalog/common/inventory/notification/ops/readability/order/outbox/payment; outbox temporary floor 0.99 proved the check fails; docker profile sets `jacoco.skip=true` because it is not a coverage representative suite; GitHub Actions run `27412812905` succeeded and uploaded the JaCoCo report |
| J3 | v1793 | completed; remote CI passed | Added `application-prod.yml` with H2 console and SQL debug output disabled, graceful shutdown enabled, and health probes retained; CI prod profile boot smoke starts the packaged jar and checks `/actuator/health`; `compose.yaml` and `docker-compose.yml` now use env substitution plus `.env.example`; order and failed-event write request DTOs now have Bean Validation boundaries; focused prod/config/order/failed-event validation tests, walkthrough compliance, Spotless ratchet check, default verify, Java 21 prod smoke, docker profile verify, and GitHub Actions run `27415924092` passed |
| J4 | v1794 | completed; remote CI passed | Added Micrometer Tracing Brave bridge, trace/span logging pattern, explicit health/info/metrics exposure in default and prod profiles, request log correlation helper, ApiExceptionHandler trace/span logs, actuator info/metrics tests, tracing configuration tests, MDC fallback tests, and a real HTTP validation-error trace log test; focused J4 tests, walkthrough compliance, Spotless ratchet check, default verify, Java 21 prod smoke, docker profile verify, and GitHub Actions run `27418381630` passed |
| J5 | v1795 | completed; remote CI passed | Added CHANGELOG version policy, PRODUCTION_READINESS boundary centralization, README release discipline pointers, ProductionReadinessDocumentationTests, and v1795 Chinese walkthrough; focused docs tests, walkthrough compliance, Spotless ratchet check, default verify, Java 21 prod smoke, docker profile verify, and GitHub Actions run `27420759966` passed |
| J6 | v1789+, v1796 | completed; remote CI passed | v1789 added ops consolidation roadmap and ratchet; v1796 adds ops route-family inventory, load-bearing archive boundary, first reduction candidate list, docs pointer, inventory guard tests, and aligns the local Spotless ratchet default with `javaproject/master` before any class movement; local Spotless, focused tests, full verify, docker profile verify, prod smoke, and GitHub Actions run `27425536260` passed |
| J7 | v1797 | completed (Claude executor closeout 2026-06-14) | First contract-preserving ops extraction moves eleven code walkthrough compliance implementation files into `ops.maintenance.walkthrough.compliance`, lowers direct root `ops` Java files from 1,330 to 1,319, keeps the root controller and shared route aggregation stable, adds extraction docs/tests, and preserves archive boundaries. Executor closeout: `test-compile` clean; focused tests (`OpsShardReadinessCodeWalkthroughCompliance*`, `ReadabilityUpkeep*`, `ProductionReadinessDocumentationTests`) exit 0; **full `mvnw verify` BUILD SUCCESS in 514s — JaCoCo "all coverage checks met" (per-package floors survived the move), SpotBugs check passed (no new findings), Spotless clean**. Codex had left one moved file (`OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.java`) with a Spotless violation; fixed via `spotless:apply` before commit. Tagged `v1797-order-platform-production-excellence-ops-walkthrough-compliance-extraction`. |
| J8 | v1798 | completed (Claude executor closeout 2026-06-14) | Second contract-preserving ops extraction moves ten code walkthrough quality gate registry implementation files into `ops.maintenance.walkthrough.qualitygate`, lowering direct root `ops` Java files from 1,319 to 1,309, keeping the root controller + shared route aggregation stable. Followed the J7 recipe: made the family `OpsShardReadinessCodeWalkthroughQualityGateRoutePaths` public with its own `BASE_PATH` and repointed the moved service to it (route byte-identical); made `ENDPOINT` public; package-local service/renderer/boundary/immutability/test-support tests moved into the subpackage; root controller + route-path tests construct the service directly and import the public route-path class. Relocated the moved Response's accepted `EI_EXPOSE_REP/REP2` exclusions in `config/spotbugs-exclude.xml` to the new FQN (same accepted findings, not new — shrink-only baseline preserved). Added `docs/ops/quality-gate-registry-extraction-v1798.md` + `ReadabilityUpkeepOpsConsolidationExtractionV1798Tests`; lowered the ratchet `MAX_ROOT_OPS_MAIN_JAVA_FILES` 1319→1309. Executor closeout: **full `mvnw verify` BUILD SUCCESS in 6m23s — 1492 tests 0 failures, JaCoCo "all coverage checks have been met" (per-package floors survived the move), SpotBugs check passed (16 EI_EXPOSE findings re-covered by the relocated exclusions, 0 new), Spotless clean**. Tagged `v1798-order-platform-production-excellence-ops-quality-gate-registry-extraction`. |
