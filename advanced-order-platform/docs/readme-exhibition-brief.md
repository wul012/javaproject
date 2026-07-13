# README Exhibition Brief — one maintenance version (authorized 2026-07-13)

Goal: make the GitHub landing page (wul012/javaproject) communicate to a human visitor
in 30 seconds what this repo is, how strong it is, and how to verify every claim.
Repo description and topics are already set on GitHub.

**Critical gap found by the reviewer: the GitHub repo ROOT (`D:\javaproj`) has NO
README at all** — the landing page shows a bare directory. The project README lives at
`advanced-order-platform/README.md` where visitors never see it. This version fixes
both layers.

Hard constraints:
- `ProductionReadinessDocumentationTests` (and the closeout docs gate) pin
  maturity/boundary phrases — pinned sentences move VERBATIM, never reworded; run the
  docs tests + focused gates locally, then full `mvnw verify` before push.
- Every number cited comes from a committed census/report with its command linked.
- Elegance gates apply (new file names ≤40 chars). No new capability claims.

## Deliverable 1 — root `README.md` (new file at the repo root, the actual landing page)

1. Badge row:
   `[![Maven CI](https://github.com/wul012/javaproject/actions/workflows/maven-ci.yml/badge.svg)](https://github.com/wul012/javaproject/actions/workflows/maven-ci.yml)`
   plus shields.io static badges: tests `1915+`, SpotBugs `0`, max file `738 lines`.
2. Hero (EN 3–4 sentences + 中文 mirror): Spring Boot order & failed-event governance;
   explicit authority boundaries (approval, replay, rollback, SQL, secrets); every
   boundary tested; consumed read-only by the Node capstone (live-verified). Include
   the exact maturity label line.
3. Highlights table: full-verify gate stack (JaCoCo floors / SpotBugs 0 shrink-only /
   Spotless / Testcontainers) · extraction 805→104 exactly on the preregistered census
   · maintainability max 738, >750/1000 = 0/0, zero size waivers · 4 consecutive clean
   ledger cycles — each row links to its script or doc.
4. Mermaid diagram: controllers → services → (orders / release approvals / failed-event
   dead-letter+replay) → RabbitMQ+Outbox → DB; side: ops evidence endpoints (read-only)
   → consumed by Node capstone.
5. Pointer: "full project docs live in `advanced-order-platform/`" with links to the
   final evidence doc, progress ledger, and census scripts.

## Deliverable 2 — refresh `advanced-order-platform/README.md`

Keep it as the deep-dive; add the badge row + a short "verify it yourself" block
(`ops-root-census.ps1`, `java-maintainability-census.ps1`,
`archive-retention-census.ps1`, `mvnw verify`). Pinned phrases verbatim.

## Ritual

One version: walkthrough before final verify, commit/tag/push, headless + Docker CI
green, ledger row closed in-session. Manual follow-up for the user: GitHub Settings →
Social preview image.

## Fail conditions

- A docs test loosened to fit rewording = fail. Numbers without committed sources =
  fail. CI red on push = closeout violation.
