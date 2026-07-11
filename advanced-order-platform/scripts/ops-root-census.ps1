param(
  [switch]$Json
)

$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
$opsRoot = Join-Path $projectRoot 'src/main/java/com/codexdemo/orderplatform/ops'
$sharedRootKeep = @(
  'OpsEvidenceResponse.java',
  'OpsEvidenceService.java',
  'OpsShardReadinessEvidenceEndpoints.java',
  'OpsShardReadinessRoutePaths.java'
)

$bucketRules = @(
  @{ Name = 'Keep-root controllers'; Pattern = '.*Controller\.java$' },
  @{ Name = 'Keep-root shared core and global route aggregator'; Names = $sharedRootKeep },
  @{ Name = 'OpsEvidence static release support'; Pattern = '^OpsEvidenceStaticRelease' },
  @{ Name = 'MinimalReadOnlyGateOperatorCiHandoff'; Pattern = '^OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoff' },
  @{ Name = 'MinimalReadOnlyGateExecution'; Pattern = '^OpsShardReadinessMinimalReadOnlyGateExecution' },
  @{ Name = 'RouteCleanup web'; Pattern = '^OpsShardReadinessRouteCleanup' },
  @{ Name = 'ReleaseAcceptanceRoutePathSplit'; Pattern = '^OpsShardReadinessReleaseAcceptanceRoutePathSplit' },
  @{ Name = 'ReleaseAcceptanceArchiveVerificationHandoff'; Pattern = '^OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoff' },
  @{ Name = 'ReleaseAcceptance root route owner'; Pattern = '^OpsShardReadinessReleaseAcceptanceRoutePaths\.java$' },
  @{ Name = 'ReleaseApprovalSandboxEndpointCredentialResolver records'; Pattern = '^ReleaseApprovalSandboxEndpointCredentialResolver' },
  @{ Name = 'ReleaseApprovalManagedAuditSandboxEndpointCredentialResolver builders'; Pattern = '^ReleaseApprovalManagedAuditSandboxEndpointCredentialResolver' },
  @{ Name = 'ReleaseApprovalManagedAuditSandboxConnection builders'; Pattern = '^ReleaseApprovalManagedAuditSandboxConnection' },
  @{ Name = 'ReleaseApprovalManagedAudit adapter/quality builders'; Pattern = '^ReleaseApprovalManagedAudit|^ReleaseApprovalOpsEvidenceServiceQualitySplit' },
  @{ Name = 'ReleaseApprovalSandboxConnection records'; Pattern = '^ReleaseApprovalSandboxConnection|^ReleaseApprovalRehearsalSandboxConnection' },
  @{ Name = 'ReleaseApprovalRehearsal shared hints/request/builders'; Pattern = '^ReleaseApprovalRehearsal' },
  @{ Name = 'ReleaseApprovalVerification hints'; Pattern = '^ReleaseApprovalVerification' },
  @{ Name = 'ReleaseApproval shared support'; Pattern = '^ReleaseApprovalContextHeaderField|^ReleaseApprovalContractConstants|^ReleaseApprovalDigestSupport|^ReleaseApprovalUpstreamContractConstants' },
  @{ Name = 'OperatorEvidenceValueSupplyAdapterPreflight'; Pattern = '^OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflight' },
  @{ Name = 'OperatorEvidenceValueSupply base'; Pattern = '^OpsShardReadinessOperatorEvidenceValueSupply' },
  @{ Name = 'ComparedEvidenceCandidateBlueprint'; Pattern = '^OpsShardReadinessComparedEvidenceCandidateBlueprint' },
  @{ Name = 'ComparedEvidenceCandidateIntakePreflight'; Pattern = '^OpsShardReadinessComparedEvidenceCandidateIntakePreflight' },
  @{ Name = 'ComparedEvidenceEvaluationPreflight'; Pattern = '^OpsShardReadinessComparedEvidenceEvaluationPreflight' },
  @{ Name = 'ComparedPackageReview'; Pattern = '^OpsShardReadinessComparedPackageReview' },
  @{ Name = 'SignedApprovalDraftProfileSection'; Pattern = '^OpsShardReadinessSignedApprovalDraftProfileSection' },
  @{ Name = 'V1Contract consumer/alignment snapshots'; Pattern = '^OpsShardReadinessV1Contract' },
  @{ Name = 'ReadOnlyEvidence catalog snapshots'; Pattern = '^OpsShardReadinessReadOnlyEvidence|^OpsShardReadinessReadOnlyEndpoint' },
  @{ Name = 'RuntimeExecutionApprovalInputTemplate'; Pattern = '^OpsShardReadinessRuntimeExecutionApprovalInputTemplate' },
  @{ Name = 'RuntimeExecutionApproval/Input residuals'; Pattern = '^OpsShardReadinessRuntimeExecutionApproval|^OpsShardReadinessRuntimeExecutionArtifact|^OpsShardReadinessRuntimeExecutionLive|^OpsShardReadinessRuntimeExecutionPacket|^OpsShardReadinessRuntimeExecutionPass' },
  @{ Name = 'ActiveShardPlanHandoff'; Pattern = '^OpsShardReadinessActiveShardPlanHandoff' },
  @{ Name = 'OpsOverview mini-family'; Pattern = '^OpsOverview' },
  @{ Name = 'PrototypeConsumerGate'; Pattern = '^OpsShardReadinessPrototypeConsumerGate' },
  @{ Name = 'Prototype catalog/evidence/handoff residuals'; Pattern = '^OpsShardReadinessPrototype' },
  @{ Name = 'Readiness core simple endpoints'; Pattern = '^OpsShardReadiness(DeclaredOperatorLifecycle|Echo|EvidenceHandoff|EvidenceIndex|EvidenceVerification|Hardening|LiveReadGatePlan|OperatorServiceLifecycle|Service|Response)' }
)

$files = Get-ChildItem -LiteralPath $opsRoot -File -Filter '*.java' | Sort-Object Name
$assigned = [ordered]@{}
$unassigned = New-Object System.Collections.Generic.List[string]

foreach ($file in $files) {
  $matched = $false
  foreach ($rule in $bucketRules) {
    $matchesName = $false
    if ($rule.ContainsKey('Names')) {
      $matchesName = $rule.Names -contains $file.Name
    } elseif ($file.Name -match $rule.Pattern) {
      $matchesName = $true
    }
    if ($matchesName) {
      if (-not $assigned.Contains($rule.Name)) {
        $assigned[$rule.Name] = New-Object System.Collections.Generic.List[string]
      }
      $assigned[$rule.Name].Add($file.Name)
      $matched = $true
      break
    }
  }
  if (-not $matched) {
    $unassigned.Add($file.Name)
  }
}

$controllerCount = ($files | Where-Object { $_.Name -like '*Controller.java' }).Count
$sharedCount = ($files | Where-Object { $sharedRootKeep -contains $_.Name }).Count
$summary = [ordered]@{
  DirectRootJavaFiles = $files.Count
  TargetFinalDirectRootJavaFiles = 104
  RetainedDirectRootFiles = $controllerCount + $sharedCount
  RemainingDirectRootNonControllers = $files.Count - ($controllerCount + $sharedCount)
  UnassignedFiles = @($unassigned)
  Buckets = @(
    foreach ($rule in $bucketRules) {
      [ordered]@{
        Bucket = $rule.Name
        Count = $(if ($assigned.Contains($rule.Name)) { $assigned[$rule.Name].Count } else { 0 })
      }
    }
  )
}

if ($Json) {
  $summary | ConvertTo-Json -Depth 5
  exit 0
}

"DirectRootJavaFiles: $($summary.DirectRootJavaFiles)"
"TargetFinalDirectRootJavaFiles: $($summary.TargetFinalDirectRootJavaFiles)"
"RetainedDirectRootFiles: $($summary.RetainedDirectRootFiles)"
"RemainingDirectRootNonControllers: $($summary.RemainingDirectRootNonControllers)"
"UnassignedFiles: $($summary.UnassignedFiles.Count)"
$summary.Buckets | Format-Table -AutoSize
