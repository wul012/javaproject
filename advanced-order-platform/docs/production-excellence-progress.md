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
| J1 | v1791 | local gates passed; remote CI pending | Maven Enforcer requires Java 21 and Maven 3.9.9; SpotBugs first scan found 2602 legacy findings collapsed into a shrink-only baseline; Spotless ratchet defaults to `origin/master`; temporary local formatting violation proved `spotless:check` fails; local Spotless, SpotBugs, focused tests, default verify, and docker profile verify passed; CI now runs `spotless:check` and uploads diagnostics on failure |
| J2 | — | not started | baseline: __% |
| J3 | — | not started | |
| J4 | — | not started | |
| J5 | — | not started | |
| J6 | v1789+ | blocked on J0–J2 | v1789 added ops consolidation roadmap and ratchet |
