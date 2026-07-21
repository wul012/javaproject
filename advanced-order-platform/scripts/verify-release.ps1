param()

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$maven = Join-Path $projectRoot 'mvnw.cmd'

Push-Location $projectRoot
try {
  $tag = (& git describe --tags --abbrev=0 --match 'v*-order-platform-*').Trim()
  if ($LASTEXITCODE -ne 0 -or [string]::IsNullOrWhiteSpace($tag)) {
    throw 'Cannot resolve the previous canonical Java tag.'
  }

  $base = (& git rev-list -n 1 $tag).Trim()
  if ($LASTEXITCODE -ne 0 -or $base -notmatch '^[0-9a-f]{40}$') {
    throw "Cannot peel canonical tag $tag to a commit."
  }

  "SpotlessRatchetTag=$tag"
  "SpotlessRatchetCommit=$base"
  $spotlessCommand =
    '("{0}" -B "-Dspotless.ratchetFrom={1}" spotless:check) 2>&1' -f $maven, $base
  & $env:ComSpec /d /c $spotlessCommand
  $spotlessExit = $LASTEXITCODE
  if ($spotlessExit -ne 0) {
    throw "Spotless failed against canonical base $base."
  }

  $verifyCommand = '("{0}" -B verify) 2>&1' -f $maven
  & $env:ComSpec /d /c $verifyCommand
  $verifyExit = $LASTEXITCODE
  if ($verifyExit -ne 0) {
    throw 'Maven verify failed.'
  }
} finally {
  Pop-Location
}
