param(
  [switch]$Json,
  [ValidateRange(1, 100)]
  [int]$Top = 15
)

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$opsRoot = Join-Path $projectRoot 'src/main/java/com/codexdemo/orderplatform/ops'
$digestRoot = Join-Path $opsRoot 'maintenance/minimalreadonlygateoperatorcihandoffarchivedigest'
$consumerRoot = Join-Path $opsRoot 'maintenance/minimalreadonlygateoperatorciconsumerpackage'
$dossierRoot = Join-Path $opsRoot 'maintenance/operatorcidossier'
$releaseAcceptanceRoot = Join-Path $opsRoot 'maintenance/ciaccept'
$archiveRegistryRoot = Join-Path $opsRoot 'maintenance/ciarc'
$archiveHandoffRoot = Join-Path $opsRoot 'maintenance/releasearchivehandoff'
$acceptancePackageRoot = Join-Path $opsRoot 'maintenance/releaseacceptancepackage'

function Get-LineCount {
  param([Parameter(Mandatory = $true)][string]$Path)

  $longPath = if ($Path.StartsWith('\\?\')) { $Path } else { '\\?\' + $Path }
  return [System.IO.File]::ReadAllLines($longPath).Count
}

$listedFiles = @(& rg --files $opsRoot -g '*.java')
if ($LASTEXITCODE -ne 0) {
  throw 'rg could not enumerate ops Java files'
}

$rows = @(
  foreach ($listedFile in $listedFiles) {
    $path = [System.IO.Path]::GetFullPath($listedFile)
    $stem = [System.IO.Path]::GetFileNameWithoutExtension($path)
    [PSCustomObject][ordered]@{
      Path = $path.Substring($projectRoot.Length + 1).Replace('\', '/')
      Stem = $stem
      Lines = Get-LineCount -Path $path
      Renderer = $stem.EndsWith('Renderer', [System.StringComparison]::Ordinal)
      Catalog = $stem.EndsWith('Catalog', [System.StringComparison]::Ordinal)
      Service = $stem.EndsWith('Service', [System.StringComparison]::Ordinal)
    }
  }
)

$renderers = @($rows | Where-Object Renderer)
$digestFiles = @(& rg --files $digestRoot -g '*.java')
$consumerFiles = @(& rg --files $consumerRoot -g '*.java')
$dossierFiles = @(& rg --files $dossierRoot -g '*.java')
$releaseAcceptanceFiles = @(& rg --files $releaseAcceptanceRoot -g '*.java')
$archiveRegistryFiles = @(& rg --files $archiveRegistryRoot -g '*.java')
$archiveHandoffFiles = @(& rg --files $archiveHandoffRoot -g '*.java')
$acceptancePackageFiles = @(& rg --files $acceptancePackageRoot -g '*.java')
$summary = [ordered]@{
  OpsJavaFiles = $rows.Count
  RendererFiles = $renderers.Count
  RendererLines = ($renderers | Measure-Object Lines -Sum).Sum
  LongRendererFileNames = @($renderers | Where-Object { $_.Stem.Length -gt 40 }).Count
  CatalogFiles = @($rows | Where-Object Catalog).Count
  ServiceFiles = @($rows | Where-Object Service).Count
  FilesOver500Lines = @($rows | Where-Object { $_.Lines -gt 500 }).Count
  MaxLines = ($rows | Measure-Object Lines -Maximum).Maximum
  ArchiveDigestJavaFiles = $digestFiles.Count
  ConsumerPackageJavaFiles = $consumerFiles.Count
  DossierJavaFiles = $dossierFiles.Count
  ReleaseAcceptanceJavaFiles = $releaseAcceptanceFiles.Count
  ArchiveRegistryJavaFiles = $archiveRegistryFiles.Count
  ArchiveHandoffJavaFiles = $archiveHandoffFiles.Count
  AcceptancePackageJavaFiles = $acceptancePackageFiles.Count
  Hotspots = @(
    $rows |
      Sort-Object -Property @{ Expression = 'Lines'; Descending = $true }, Path |
      Select-Object -First $Top Path, Lines
  )
}

if ($Json) {
  $summary | ConvertTo-Json -Depth 4
  exit 0
}

foreach ($metric in $summary.GetEnumerator() | Where-Object Key -ne 'Hotspots') {
  "$($metric.Key)=$($metric.Value)"
}
$summary.Hotspots | Format-Table -AutoSize
