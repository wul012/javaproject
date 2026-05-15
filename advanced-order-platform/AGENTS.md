# advanced-order-platform Agent Notes

## Four-Project Research Rules

For cross-project research, quality evaluation, and planning, use `[MODE: RESEARCH]`.
Prefer read-only inspection, evidence-backed summaries, and clear separation between facts, judgments, and recommendations.

Developer capability baseline across Node/Java/mini-kv/aiproj:

| Dimension | Rating |
|---|---|
| Architecture design | A- |
| Multi-language engineering | A |
| Test engineering | B+ |
| Output speed | S |
| Refactoring and debt management | B |
| Security and compliance awareness | A |
| Engineering discipline and restraint | C+ |

Core judgment: high-output, strong-design, multi-stack developer with good safety awareness; main growth area is knowing when to stop, refactor, and trade about 30% speed for restraint.

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

- Read the latest plan under `D:\nodeproj\orderops-node\docs\plans`.
- If the latest plan is reasonable and the current recommended step is Java, execute it.
- If the plan is unreasonable, dependencies are not satisfied, or the next recommended step belongs to Node or mini-kv, stop and explain instead of forcing Java work.
- Keep each version right-sized. Prefer one small, valuable capability, refactor, or hardening step rather than a broad mixed change.

Version completion expectations:

- Preserve explicit read-only, approval, ledger, rollback, SQL, secret, and deployment boundaries.
- Run focused tests and enough regression/package/smoke validation for the change size.
- Commit, tag, and push completed Java versions to the Java remote.
- If the version direction becomes unclear, pause and ask for confirmation.

## Runtime Archive Folder

For future Java version work, put run/debug explanations and screenshots in `c/`, which is a sibling of the old `a/` and `b/` archive folders.

Use this shape:

```text
c/<version>/解释/说明.md
c/<version>/图片/*.png
```

Keep the old `a/` and `b/` folders as historical archives for earlier versions. Do not move old `a/<version>` or `b/<version>` records unless the user explicitly asks.

When finishing a version, the final report should mention the `c/<version>` archive path.

## Code Explanation Folder

For new Java code explanation notes, use the stage-specific sibling folder instead of the older crowded folder:

```text
代码讲解记录_生产雏形阶段/
```

Keep the existing explanation style and numbered Markdown naming pattern. Do not move older `代码讲解记录/` files unless the user explicitly asks.

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
