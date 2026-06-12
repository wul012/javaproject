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
| J0 | v1790 | local gates passed; remote CI pending | `mvnw` generated for Maven 3.9.9; Docker tests tagged; Surefire excludes `docker` by default; `docker-tests` profile selects Docker-tagged tests; `mvnw.cmd -B -q verify` passed locally; `mvnw.cmd -B -q -P docker-tests verify` passed locally with Docker unavailable/skipped |
| J1 | — | not started | |
| J2 | — | not started | baseline: __% |
| J3 | — | not started | |
| J4 | — | not started | |
| J5 | — | not started | |
| J6 | v1789+ | blocked on J0–J2 | v1789 added ops consolidation roadmap and ratchet |
