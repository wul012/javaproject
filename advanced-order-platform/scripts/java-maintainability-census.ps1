param(
  [switch]$Json,
  [switch]$WriteNameBaseline,
  [ValidateRange(1, 100)]
  [int]$Top = 20
)

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$mainRoot = Join-Path $projectRoot 'src/main/java'
$testRoot = Join-Path $projectRoot 'src/test/java'
$nameBudget = 40
$triviaPattern = '(?ms)/\*.*?\*/|//[^\r\n]*|"""[\s\S]*?"""|"(?:\\.|[^"\\])*"|''(?:\\.|[^''\\])*'''
$identifierPattern = '(?<![A-Za-z0-9_$])[A-Za-z_$][A-Za-z0-9_$]*'

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

function Get-SourceText {
  param([Parameter(Mandatory = $true)][string]$Path)

  $longPath = if ($Path.StartsWith('\\?\')) { $Path } else { '\\?\' + $Path }
  return [System.IO.File]::ReadAllText($longPath, [System.Text.Encoding]::UTF8)
}

function Get-LongIdentifiers {
  param([Parameter(Mandatory = $true)][string]$Path)

  $source = Get-SourceText -Path $Path
  $code = [regex]::Replace($source, $triviaPattern, ' ')
  @(
    [regex]::Matches($code, $identifierPattern) |
      ForEach-Object Value |
      Where-Object Length -gt $nameBudget
  )
}

function Get-JavaFileRows {
  param([Parameter(Mandatory = $true)][string]$Root)

  @(
    Get-ChildItem -LiteralPath $Root -Recurse -File -Filter '*.java' | ForEach-Object {
      $stem = [System.IO.Path]::GetFileNameWithoutExtension($_.Name)
      $longIdentifiers = @(Get-LongIdentifiers -Path $_.FullName)
      $uniqueIdentifiers = [System.Collections.Generic.HashSet[string]]::new(
        [System.StringComparer]::Ordinal
      )
      foreach ($identifier in $longIdentifiers) {
        [void]$uniqueIdentifiers.Add($identifier)
      }
      [PSCustomObject][ordered]@{
        Path = $_.FullName.Substring($projectRoot.Length + 1).Replace('\', '/')
        Lines = Get-LineCount -Path $_.FullName
        Bytes = $_.Length
        FileStemLength = $stem.Length
        LongFileStem = $stem.Length -gt $nameBudget
        LongIdentifierOccurrences = $longIdentifiers.Count
        LongIdentifiers = @($uniqueIdentifiers | Sort-Object)
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
  $longNameSet = [System.Collections.Generic.HashSet[string]]::new(
    [System.StringComparer]::Ordinal
  )
  foreach ($identifier in @($Rows | ForEach-Object LongIdentifiers)) {
    [void]$longNameSet.Add($identifier)
  }
  $longNames = @($longNameSet | Sort-Object)
  $longestIdentifier = @($longNames | Sort-Object Length -Descending | Select-Object -First 1)
  [ordered]@{
    Scope = $Scope
    JavaFiles = $Rows.Count
    MaxLines = ($Rows | Measure-Object -Property Lines -Maximum).Maximum
    FilesOver500Lines = @($Rows | Where-Object { $_.Lines -gt 500 }).Count
    FilesOver750Lines = @($Rows | Where-Object { $_.Lines -gt 750 }).Count
    FilesOver1000Lines = @($Rows | Where-Object { $_.Lines -gt 1000 }).Count
    LongFileStems = @($Rows | Where-Object LongFileStem).Count
    LongIdentifierOccurrences = ($Rows | Measure-Object LongIdentifierOccurrences -Sum).Sum
    LongIdentifierUnique = $longNames.Count
    LongestFileStem = ($Rows | Sort-Object FileStemLength -Descending | Select-Object -First 1).Path
    LongestIdentifier = if ($longestIdentifier.Count -eq 0) { $null } else { $longestIdentifier[0] }
    Hotspots = @(
      $sorted |
        Select-Object -First $Top |
        Select-Object Path, Lines, Bytes, FileStemLength, LongIdentifierOccurrences
    )
    NameHotspots = @(
      $Rows |
        Where-Object { $_.LongFileStem -or $_.LongIdentifierOccurrences -gt 0 } |
        Sort-Object -Property @{ Expression = 'LongIdentifierOccurrences'; Descending = $true },
          @{ Expression = 'FileStemLength'; Descending = $true }, Path |
        Select-Object -First $Top |
        Select-Object Path, FileStemLength, LongIdentifierOccurrences,
          @{ Name = 'LongIdentifierSample'; Expression = { @($_.LongIdentifiers | Select-Object -First 5) } }
    )
  }
}

$mainRows = @(Get-JavaFileRows -Root $mainRoot)
$testRows = @(Get-JavaFileRows -Root $testRoot)

function Write-NameBaseline {
  param([Parameter(Mandatory = $true)][object[]]$Rows)

  $entries = [System.Collections.Generic.HashSet[string]]::new(
    [System.StringComparer]::Ordinal
  )
  foreach ($row in $Rows) {
    if ($row.LongFileStem) {
      [void]$entries.Add("F`t$($row.Path)")
    }
    foreach ($identifier in $row.LongIdentifiers) {
      [void]$entries.Add("I`t$identifier")
    }
  }

  $ordered = @($entries)
  [Array]::Sort($ordered, [System.StringComparer]::Ordinal)
  $header = @(
    '# Java long-name baseline seeded by v1869. Entries may only be removed.',
    '# F = long Java file path; I = long lexical identifier.'
  )
  $target = Join-Path $projectRoot 'config/java-name-baseline.txt'
  $encoding = [System.Text.UTF8Encoding]::new($false)
  [System.IO.File]::WriteAllLines($target, @($header + $ordered), $encoding)
  return $target
}

if ($WriteNameBaseline) {
  $target = Write-NameBaseline -Rows @($mainRows + $testRows)
  "wrote $target"
}

$summary = [ordered]@{
  Production = Get-SourceSummary -Rows $mainRows -Scope 'production'
  Tests = Get-SourceSummary -Rows $testRows -Scope 'tests'
}

if ($Json) {
  $summary | ConvertTo-Json -Depth 6
  exit 0
}

foreach ($source in @($summary.Production, $summary.Tests)) {
  "$($source.Scope): files=$($source.JavaFiles), maxLines=$($source.MaxLines), over500=$($source.FilesOver500Lines), over750=$($source.FilesOver750Lines), over1000=$($source.FilesOver1000Lines)"
  "names: longFileStems=$($source.LongFileStems), longIdentifierOccurrences=$($source.LongIdentifierOccurrences), longIdentifierUnique=$($source.LongIdentifierUnique), longestIdentifier=$($source.LongestIdentifier)"
  $source.Hotspots | Format-Table -AutoSize
  $source.NameHotspots | Format-Table -AutoSize
}
