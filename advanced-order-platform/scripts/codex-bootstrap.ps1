# Codex session bootstrap. Run at session start: .\scripts\codex-bootstrap.ps1
# Read-only orientation that keeps failures visible instead of masking a stale checkout or CLI.
$ErrorActionPreference = 'Stop'

function Invoke-OptionalCommand {
  param(
    [Parameter(Mandatory)] [string] $Name,
    [Parameter(Mandatory)] [scriptblock] $Command
  )

  if (Get-Command $Name -ErrorAction SilentlyContinue) {
    & $Command
    if ($LASTEXITCODE -ne 0) {
      Write-Warning "$Name exited with code $LASTEXITCODE."
    }
  } else {
    Write-Warning "$Name is not available; skipping this section."
  }
}

Write-Output '=== git: last 3 commits / status ==='
Invoke-OptionalCommand git { git log --oneline -3 }
Invoke-OptionalCommand git { git status -sb | Select-Object -First 8 }
Write-Output '=== latest tag ==='
Invoke-OptionalCommand git { git tag --sort=-creatordate | Select-Object -First 1 }
Write-Output '=== CI: last 3 runs (do not block-watch intermediates) ==='
Invoke-OptionalCommand gh { gh run list --limit 3 }
Write-Output '=== pointers ==='
Write-Output 'Active program : D:\C\四项目理解统筹\AGENTS.md -> Current Active Program'
Write-Output 'Progress ledger: docs\production-excellence-progress.md'
Write-Output 'Active brief   : D:\nodeproj\orderops-node\docs\plans\production-excellence-java-final-push.md (read-only)'
Write-Output 'Reminder       : write the walkthrough BEFORE the final mvnw verify; prep next batch read-only while verify runs.'
