package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalGateInputService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionArtifactCandidateService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionLiveReadGateService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPacketContributionService;
import com.codexdemo.orderplatform.ops.maintenance.runtimeexecution.OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService;
import java.util.List;

final class OpsShardReadinessReadOnlyEvidenceCatalogSnapshot {

  private OpsShardReadinessReadOnlyEvidenceCatalogSnapshot() {}

  static List<String> v175LiveEndpoints() {
    return List.of(
        OpsShardReadinessService.ENDPOINT,
        OpsShardReadinessHardeningService.ENDPOINT,
        OpsShardReadinessEchoService.ENDPOINT,
        OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT,
        OpsShardReadinessEvidenceIndexService.ENDPOINT,
        OpsShardReadinessEvidenceVerificationService.ENDPOINT,
        OpsShardReadinessEvidenceHandoffService.ENDPOINT,
        OpsShardReadinessActiveShardPlanHandoffService.ENDPOINT,
        OpsShardReadinessLiveReadGatePlanService.ENDPOINT,
        OpsShardReadinessOperatorServiceLifecycleService.ENDPOINT,
        OpsShardReadinessDeclaredOperatorLifecycleService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionArtifactCandidateService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionPacketContributionService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalGateInputService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionLiveReadGateService.ENDPOINT,
        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.ENDPOINT);
  }

  static List<String> v175FixtureEndpoints() {
    return List.of(
        OpsShardReadinessService.FIXTURE_ENDPOINT,
        OpsShardReadinessHardeningService.FIXTURE_ENDPOINT,
        OpsShardReadinessEchoService.FIXTURE_ENDPOINT,
        OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT,
        OpsShardReadinessEvidenceIndexService.FIXTURE_ENDPOINT,
        OpsShardReadinessEvidenceVerificationService.FIXTURE_ENDPOINT,
        OpsShardReadinessEvidenceHandoffService.FIXTURE_ENDPOINT,
        OpsShardReadinessActiveShardPlanHandoffService.FIXTURE_ENDPOINT,
        OpsShardReadinessLiveReadGatePlanService.FIXTURE_ENDPOINT,
        OpsShardReadinessOperatorServiceLifecycleService.FIXTURE_ENDPOINT,
        OpsShardReadinessDeclaredOperatorLifecycleService.FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionArtifactCandidateService.FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionPacketContributionService.FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalGateInputService.FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService.FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService.FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
            .FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService.FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionLiveReadGateService.FIXTURE_ENDPOINT,
        OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService.FIXTURE_ENDPOINT);
  }
}
