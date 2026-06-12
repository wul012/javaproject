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

## Progress

| Milestone | Version(s) | State | Evidence |
| --------- | ---------- | ----- | -------- |
| J0 | v1790 | completed; Claude reviewed PASS | `mvnw` generated for Maven 3.9.9; Docker tests tagged; Surefire excludes `docker` by default; `docker-tests` profile selects Docker-tagged tests; local wrapper verifies passed; GitHub Actions run `27397723739` succeeded; Claude reviewed J0 as PASS |
| J1 | v1791 | completed; remote CI passed | Maven Enforcer requires Java 21 and Maven 3.9.9; SpotBugs first scan found 2602 legacy findings collapsed into a shrink-only baseline; Spotless ratchet defaults to `origin/master`; temporary local formatting violation proved `spotless:check` fails; local Spotless, SpotBugs, focused tests, default verify, and docker profile verify passed; GitHub Actions run `27408969138` succeeded |
| J2 | v1792 | completed; remote CI passed | JaCoCo 0.8.15 added with `jacocoArgLine` + `test.jvm.argLine` split; baseline recorded in `docs/coverage/jacoco-baseline-v1792.md`; global line baseline 98.18% with floor 0.96; package floors cover root/catalog/common/inventory/notification/ops/readability/order/outbox/payment; outbox temporary floor 0.99 proved the check fails; docker profile sets `jacoco.skip=true` because it is not a coverage representative suite; GitHub Actions run `27412812905` succeeded and uploaded the JaCoCo report |
| J3 | v1793 | completed; remote CI passed | Added `application-prod.yml` with H2 console and SQL debug output disabled, graceful shutdown enabled, and health probes retained; CI prod profile boot smoke starts the packaged jar and checks `/actuator/health`; `compose.yaml` and `docker-compose.yml` now use env substitution plus `.env.example`; order and failed-event write request DTOs now have Bean Validation boundaries; focused prod/config/order/failed-event validation tests, walkthrough compliance, Spotless ratchet check, default verify, Java 21 prod smoke, docker profile verify, and GitHub Actions run `27415924092` passed |
| J4 | v1794 | completed; remote CI passed | Added Micrometer Tracing Brave bridge, trace/span logging pattern, explicit health/info/metrics exposure in default and prod profiles, request log correlation helper, ApiExceptionHandler trace/span logs, actuator info/metrics tests, tracing configuration tests, MDC fallback tests, and a real HTTP validation-error trace log test; focused J4 tests, walkthrough compliance, Spotless ratchet check, default verify, Java 21 prod smoke, docker profile verify, and GitHub Actions run `27418381630` passed |
| J5 | v1795 | local gates passed; remote CI pending | Added CHANGELOG version policy, PRODUCTION_READINESS boundary centralization, README release discipline pointers, ProductionReadinessDocumentationTests, and v1795 Chinese walkthrough; focused docs tests, walkthrough compliance, Spotless ratchet check, default verify, Java 21 prod smoke, and docker profile verify passed |
| J6 | v1789+ | blocked on J0–J2 | v1789 added ops consolidation roadmap and ratchet |
