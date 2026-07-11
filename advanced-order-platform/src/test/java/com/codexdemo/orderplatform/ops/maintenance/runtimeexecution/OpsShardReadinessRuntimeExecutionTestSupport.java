package com.codexdemo.orderplatform.ops.maintenance.runtimeexecution;

import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessActiveShardPlanHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessDeclaredOperatorLifecycleService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceIndexService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessEvidenceVerificationService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessLiveReadGatePlanService;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessOperatorServiceLifecycleService;

public final class OpsShardReadinessRuntimeExecutionTestSupport {

  private OpsShardReadinessRuntimeExecutionTestSupport() {}

  public static OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService
      passEvidenceCloseoutService() {
    OpsShardReadinessEvidenceIndexService indexService =
        new OpsShardReadinessEvidenceIndexService();
    OpsShardReadinessEvidenceVerificationService verificationService =
        new OpsShardReadinessEvidenceVerificationService(indexService);
    OpsShardReadinessEvidenceHandoffService handoffService =
        new OpsShardReadinessEvidenceHandoffService(indexService, verificationService);
    OpsShardReadinessActiveShardPlanHandoffService activeShardPlanHandoffService =
        new OpsShardReadinessActiveShardPlanHandoffService(handoffService);
    OpsShardReadinessLiveReadGatePlanService liveReadGatePlanService =
        new OpsShardReadinessLiveReadGatePlanService(activeShardPlanHandoffService);
    OpsShardReadinessOperatorServiceLifecycleService operatorLifecycleService =
        new OpsShardReadinessOperatorServiceLifecycleService(liveReadGatePlanService);
    OpsShardReadinessDeclaredOperatorLifecycleService declaredLifecycleService =
        new OpsShardReadinessDeclaredOperatorLifecycleService(operatorLifecycleService);
    OpsShardReadinessRuntimeExecutionArtifactCandidateService artifactCandidateService =
        new OpsShardReadinessRuntimeExecutionArtifactCandidateService(declaredLifecycleService);
    OpsShardReadinessRuntimeExecutionPacketContributionService packetContributionService =
        new OpsShardReadinessRuntimeExecutionPacketContributionService(artifactCandidateService);
    OpsShardReadinessRuntimeExecutionApprovalGateInputService approvalGateInputService =
        new OpsShardReadinessRuntimeExecutionApprovalGateInputService(packetContributionService);
    OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService contractHandoffService =
        new OpsShardReadinessRuntimeExecutionApprovalInputContractHandoffService(
            approvalGateInputService);
    OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService
        templateCompatibilityService =
            new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityService(
                contractHandoffService);
    OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService
        compatibilityIntakeService =
            new OpsShardReadinessRuntimeExecutionApprovalInputTemplateCompatibilityIntakeService(
                templateCompatibilityService);
    OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService valueValidationService =
        new OpsShardReadinessRuntimeExecutionApprovalInputValueValidationService(
            compatibilityIntakeService);
    OpsShardReadinessRuntimeExecutionLiveReadGateService liveReadGateService =
        new OpsShardReadinessRuntimeExecutionLiveReadGateService(valueValidationService);
    return new OpsShardReadinessRuntimeExecutionPassEvidenceCloseoutService(liveReadGateService);
  }
}
