# advanced-order-platform Agent Notes

## Current State (single source of truth)

Update this table instead of appending relative-time rules below. Where an older section conflicts with this table, this table wins.

| Item | Current |
|---|---|
| Active screenshot archive root | `f/v<start>-v<end>/<version>/images/` + `.../explanations/summary.md` (range segment first) |
| Active walkthrough volume | newest `代码讲解记录_生产雏形阶段N/` sibling (currently `代码讲解记录_生产雏形阶段6/`) |
| Active cross-project program | `D:\C\四项目理解统筹\AGENTS.md` → Current Active Program |
| Progress ledger | `docs/production-excellence-progress.md` |
| Session bootstrap | run `.\scripts\codex-bootstrap.ps1` at session start (git/tag/CI/pointers in one command) |
| Frozen history (never move) | `a/`, `b/`, `c/`, `d/`, `d_runtime_screenshot_archive_next/`, older walkthrough volumes |

## Four-Project Research Rules

For cross-project research, quality evaluation, and planning, use `[MODE: RESEARCH]`.
Prefer read-only inspection, evidence-backed summaries, and clear separation between facts, judgments, and recommendations.

The capability baseline table lives ONLY in `D:\C\四项目理解统筹\AGENTS.md` (single source of truth) — read it there instead of keeping a copy here. One-line summary: high-output, strong-design, multi-stack developer; the growth area is restraint.

Evaluation rules:
- Praise speed, architecture, multi-language execution, and safety awareness, but do not overstate production readiness.
- Distinguish contract/evidence chain from live runtime integration.
- Treat large files, long names, repeated report renderers, string-based fixture checks, and delayed refactoring as real debt signals.
- Separate latest committed version from dirty working tree changes.
- Until real integration exists, describe the state as `single-project validation + cross-project contract alignment`.

Java-specific focus:
- Java is the business and failure-event governance side, not plain CRUD.
- Ops evidence, release audit retention, rollback/secret/runbook contracts, approval, audit, and failed-event replay boundaries must remain explicit.
- Node may consume Java evidence, but Java evidence does not authorize deployment, rollback, SQL execution, or secret access by itself.

Refactoring rhythm:
- After 3-4 feature versions, prefer 1 version of contract-preserving refactor, deduplication, or test hardening.
- Split large evidence service/response/contract catalog code before it becomes emergency refactoring.

## Java Advancement Rules

Default to working on this Java repository: `D:\javaproj\advanced-order-platform`.
When the user says to continue, advance, or push the project, treat it as Java work unless they explicitly ask for another repository.

Before starting a new Java version:

- First check `D:\C\四项目理解统筹\AGENTS.md` → Current Active Program: if a program brief is active there, it takes precedence. Otherwise read the latest active Node plan under `D:\nodeproj\orderops-node\docs\plans2`, falling back to `docs\plans`.
- If the user names a Node version such as `v282`, locate and read that Node plan directly in the Node
  repository instead of relying only on Java-side mentions of that version.
- If the latest plan is reasonable and the current recommended step is Java, execute it.
- If the plan is unreasonable, dependencies are not satisfied, or the next recommended step belongs to Node or mini-kv, stop and explain instead of forcing Java work.
- Keep each version right-sized. Prefer one small, valuable capability, refactor, or hardening step rather than a broad mixed change.

Version completion expectations:

- Preserve explicit read-only, approval, ledger, rollback, SQL, secret, and deployment boundaries.
- Run focused tests and enough regression/package/smoke validation for the change size.
- Commit, tag, and push completed Java versions unless the user explicitly asks to keep the result local.
- If the version direction becomes unclear, pause and ask for confirmation.

## Runtime Archive Folder

For Java version work after v115 and through the historical v152 runtime evidence set,
run/debug explanations and screenshots live in `d/`, which is a sibling of the old `a/`,
`b/`, and `c/` archive folders. The v1759-v1763 `d_runtime_screenshot_archive_next/`
folder is a preserved transition record only. For new screenshot/explanation work, continue
in the canonical same-level `f/` root:

```text
f/v<start>-v<end>/<version>/explanations/summary.md
f/v<start>-v<end>/<version>/images/*.png
```

Do not place new screenshot or explanation files directly under `d/`, directly under
`d_runtime_screenshot_archive_next/`, or directly under `f/`. Use a version-range segment first,
then the version folder.

Keep the old `a/`, `b/`, and `c/` folders as historical archives for earlier versions. `c/` is closed after Java v115. Do not move old `a/<version>`, `b/<version>`, or `c/<version>` records unless the user explicitly asks.

When finishing a version, the final report should mention the `d/<version>` archive path.

## Screenshot Capture Rule

For Java version screenshots, prefer the Playwright MCP screenshot tool when it is available.
Save historical v116-v152 screenshots under:

```text
d/<version>/图片/*.png
```

For new segmented screenshot archives, save successful screenshots under:

```text
f/v<start>-v<end>/<version>/images/*.png
```

Use the sibling explanation file to describe what each screenshot proves. If MCP screenshot
capture fails, fall back to the installed screenshot skill or another local screenshot method
and record the fallback in the explanation.

## Code Explanation Folder

Write new code explanation notes to the active walkthrough volume in the Current State table (the newest `代码讲解记录_生产雏形阶段N/` sibling). Write the walkthrough BEFORE the version's final `mvnw verify`, so the walkthrough-compliance gate runs against the final text (lesson promoted from v1799). Keep the existing explanation style and numbered Markdown naming pattern. `代码讲解记录_生产雏形阶段/` is closed after `118-version-115-credential-resolver-approval-required-boundary-echo-refinement.md`. Do not move older `代码讲解记录/` or `代码讲解记录_生产雏形阶段/` files unless the user explicitly asks.

## Document Expansion Rule

For document work only, when an output folder becomes crowded, create or use a same-level
sibling folder and continue writing there instead of stuffing everything into the original
folder. Keep the original folder for older files and let the sibling folder carry the newer
sequence. For the current Java docs, this means historical runtime/screenshot explanations stay
in `d/`, the v1759-v1763 transition stays in `d_runtime_screenshot_archive_next/`, new
screenshot/explanation work continues in `f/`, and code explanation notes continue in the active
`代码讲解记录*` continuation folders.

## Program Discipline (promoted 2026-07-06)

- Remote verification policy: after push, confirm the CI run is queued/started with a quick `gh run list` (seconds), but do not block on `gh run watch` for intermediate versions of a multi-version batch. Check the previous version's run conclusion at the start of the next version; if it failed, fixing CI becomes the immediate next task. Block-watch only the final version of a batch or when the user asks.
- Evidence economy: text evidence first (logs, JSON summaries, transcripts); screenshots only where a visual actually proves something or for closeout versions. Archive growth is budgeted.
- Progress-ledger rows: at most ~3 lines per version; details live in `docs/ops/` extraction docs or archive files, the row keeps a pointer.
- Lesson promotion: when the same deviation or workaround is recorded twice, promote it into this file as a rule instead of recording it a third time.
- Ledger closes with the version: update the progress-ledger row to its final state (including CI run id) in the same session as commit/tag/push. A row left saying "pending" after the push is a closeout violation (recurred at v1826 and v1833; promoted 2026-07-07).
- Java line-count ratchets must use `scripts/java-maintainability-census.ps1` or an explicit `StreamReader.ReadLine()` loop. Do not use `Measure-Object -Line`; it omits blank lines and produced two false caps during v1835.
- Method kernel: apply the 12-rule kernel and task-brief skeleton from `D:\C\四项目理解统筹\模型使用手册\00-通用方法内核.md` (read once per session; also mirrored in the global `~/.codex/AGENTS.md`).

## Elegance Gates (promoted 2026-07-11, program-end review)

The post-capstone review judged this codebase fortified but not elegant — this repo has
the program's worst names (a 793-line class with an eight-noun name) and its largest
duplication family (the ops package). These gates buy elegance upstream. They apply to
NEW and TOUCHED code only; existing violations enter a committed baseline that may only
shrink. Each rule must become a committed mechanical check that fails verify/CI — extend
`scripts/java-maintainability-census.ps1` with a name census in the next governance
version; until then the rules bind as authoring rules enforced at review. Ratchets only
tighten.

- Name budget: no new identifier or filename over 40 characters. A name that wants more
  nouns means a missing abstraction — extract and name the concept instead. Existing
  over-budget names: baseline census, shrink-only; renames only where route strings,
  SpotBugs FQNs, and frozen fixtures allow.
- Rule of three: a third structurally-similar class in any family is a STOP condition —
  build the shared engine/builder first, then land the third case as data/config.
- Generation cap: a feature version adds at most 400 new source lines. Extraction
  batches are moves, not new code — exempt from the cap, but any NEW identifier they
  introduce (route owners, aggregators) obeys the name budget.
- Family design note: before creating a new file family, write ≤10 lines in the
  walkthrough naming the abstraction and the data-vs-behavior split — BEFORE
  implementation, and before the final verify like the walkthrough rule.
- Boy-scout rule: any touched file must leave within this section's name and size
  budgets.

## Docker Shutdown Fast Path

When Docker was started only for this Java project validation, close it quickly during cleanup.

Use this order:

```powershell
$dockerCli = 'C:\Program Files\Docker\Docker\DockerCli.exe'
if (Test-Path $dockerCli) { & $dockerCli -Shutdown 2>$null }
Start-Sleep -Seconds 8
docker info *> $null
if ($LASTEXITCODE -eq 0) {
  Get-Process |
    Where-Object {
      $_.ProcessName -in @(
        'Docker Desktop',
        'DockerCli',
        'com.docker.backend',
        'com.docker.proxy',
        'com.docker.dev-envs',
        'com.docker.extensions'
      )
    } |
    Stop-Process -Force -ErrorAction SilentlyContinue
  Start-Sleep -Seconds 8
}
docker info *> $null
$dockerStopped = $LASTEXITCODE -ne 0
```

Do not spend minutes waiting on `Docker Desktop.exe -Shutdown` alone. It can leave Docker responsive for too long.

Before stopping Docker, confirm project Testcontainers are done and no containers need to remain running:

```powershell
docker ps -a --format 'table {{.Names}}\t{{.Image}}\t{{.Status}}'
```

Do not remove Docker volumes, images, or user containers unless the user explicitly asks.
