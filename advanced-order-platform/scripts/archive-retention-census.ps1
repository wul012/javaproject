param(
  [switch]$Json,
  [switch]$WriteManifest
)

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$manifestPath = Join-Path $projectRoot 'docs/archive-retention-manifest.txt'
$fixedRoots = @('a', 'b', 'c', 'd', 'd_runtime_screenshot_archive_next', 'e', 'f')
$walkthroughPrefix = -join @(20195, 30721, 35762, 35299, 35760, 24405 | ForEach-Object { [char]$_ })
$walkthroughRoots = @(
  Get-ChildItem -LiteralPath $projectRoot -Directory |
    Where-Object Name -Like "$walkthroughPrefix*" |
    ForEach-Object Name
)
$archiveRoots = @($fixedRoots + $walkthroughRoots)

function Get-LongPath {
  param([Parameter(Mandatory = $true)][string]$Path)

  if ($Path.StartsWith('\\?\')) {
    return $Path
  }
  return '\\?\' + $Path
}

function Get-Sha256 {
  param([Parameter(Mandatory = $true)][string]$Path)

  $sha = [System.Security.Cryptography.SHA256]::Create()
  $stream = [System.IO.File]::OpenRead((Get-LongPath -Path $Path))
  try {
    $digest = [BitConverter]::ToString($sha.ComputeHash($stream))
    return $digest.Replace('-', '').ToLowerInvariant()
  } finally {
    $stream.Dispose()
    $sha.Dispose()
  }
}

$files = @(
  foreach ($root in $archiveRoots) {
    $rootPath = Join-Path $projectRoot $root
    if (-not (Test-Path -LiteralPath $rootPath -PathType Container)) {
      throw "Archive root is missing: $root"
    }
    Get-ChildItem -LiteralPath $rootPath -Recurse -File | ForEach-Object {
      [PSCustomObject][ordered]@{
        Root = $root
        Path = $_.FullName.Substring($projectRoot.Length + 1).Replace('\', '/')
        Bytes = $_.Length
        Sha256 = Get-Sha256 -Path $_.FullName
      }
    }
  }
)

$rootSummary = @(
  foreach ($root in $archiveRoots) {
    $rootFiles = @($files | Where-Object Root -EQ $root)
    [PSCustomObject][ordered]@{
      Root = $root
      Files = $rootFiles.Count
      Bytes = ($rootFiles | Measure-Object Bytes -Sum).Sum
    }
  }
)
$summary = [PSCustomObject][ordered]@{
  Roots = $rootSummary
  TotalFiles = $files.Count
  TotalBytes = ($files | Measure-Object Bytes -Sum).Sum
  Manifest = $manifestPath.Substring($projectRoot.Length + 1).Replace('\', '/')
}

if ($WriteManifest) {
  [string[]]$lines = @($files | ForEach-Object { "$($_.Path)`t$($_.Sha256)" })
  [Array]::Sort($lines, [StringComparer]::Ordinal)
  [System.IO.File]::WriteAllLines(
    $manifestPath,
    $lines,
    [System.Text.UTF8Encoding]::new($false))
}

if ($Json) {
  $summary | ConvertTo-Json -Depth 4
  exit 0
}

$rootSummary | Format-Table -AutoSize
"total: files=$($summary.TotalFiles), bytes=$($summary.TotalBytes), manifest=$($summary.Manifest)"
