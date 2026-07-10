param(
  [switch]$Json,
  [ValidateRange(1, 100)]
  [int]$Top = 20
)

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$mainRoot = Join-Path $projectRoot 'src/main/java'
$testRoot = Join-Path $projectRoot 'src/test/java'

function Get-LineCount {
  param([Parameter(Mandatory = $true)][string]$Path)

  $longPath = if ($Path.StartsWith('\\?\')) { $Path } else { '\\?\' + $Path }
  $reader = [System.IO.StreamReader]::new($longPath)
  try {
    $count = 0
    while ($null -ne $reader.ReadLine()) {
      $count++
    }
    return $count
  } finally {
    $reader.Dispose()
  }
}

function Get-JavaFileRows {
  param([Parameter(Mandatory = $true)][string]$Root)

  @(
    Get-ChildItem -LiteralPath $Root -Recurse -File -Filter '*.java' | ForEach-Object {
      [PSCustomObject][ordered]@{
        Path = $_.FullName.Substring($projectRoot.Length + 1).Replace('\', '/')
        Lines = Get-LineCount -Path $_.FullName
        Bytes = $_.Length
      }
    }
  )
}

function Get-SourceSummary {
  param(
    [Parameter(Mandatory = $true)][object[]]$Rows,
    [Parameter(Mandatory = $true)][string]$Scope
  )

  $sorted = @($Rows | Sort-Object -Property @{ Expression = 'Lines'; Descending = $true }, Path)
  [ordered]@{
    Scope = $Scope
    JavaFiles = $Rows.Count
    MaxLines = ($Rows | Measure-Object -Property Lines -Maximum).Maximum
    FilesOver500Lines = @($Rows | Where-Object { $_.Lines -gt 500 }).Count
    FilesOver750Lines = @($Rows | Where-Object { $_.Lines -gt 750 }).Count
    FilesOver1000Lines = @($Rows | Where-Object { $_.Lines -gt 1000 }).Count
    Hotspots = @($sorted | Select-Object -First $Top)
  }
}

$summary = [ordered]@{
  Production = Get-SourceSummary -Rows (Get-JavaFileRows -Root $mainRoot) -Scope 'production'
  Tests = Get-SourceSummary -Rows (Get-JavaFileRows -Root $testRoot) -Scope 'tests'
}

if ($Json) {
  $summary | ConvertTo-Json -Depth 6
  exit 0
}

foreach ($source in @($summary.Production, $summary.Tests)) {
  "$($source.Scope): files=$($source.JavaFiles), maxLines=$($source.MaxLines), over500=$($source.FilesOver500Lines), over750=$($source.FilesOver750Lines), over1000=$($source.FilesOver1000Lines)"
  $source.Hotspots | Format-Table -AutoSize
}
