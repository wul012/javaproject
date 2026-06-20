package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeAssuranceSlotCatalog {

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeAssuranceSlotCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
              .EvidenceSlot>
      assuranceSlots() {
    return List.of(
        slot(
            "compared-package-evidence-slot-source-value-handles",
            "v1328",
            "source evidence and operator value handle evidence slot",
            "Does the future artifact include handle evidence without importing values or credentials?",
            "reject-missing-source-value-handle-evidence",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightEvidenceValuePolicyService
                .ENDPOINT),
        slot(
            "compared-package-evidence-slot-policy-execution-lock",
            "v1329",
            "policy and execution lock evidence slot",
            "Does the future artifact preserve policy and execution locks without runtime payload?",
            "reject-missing-policy-execution-lock-evidence",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightExecutionCloseoutService
                .ENDPOINT),
        slot(
            "compared-package-evidence-slot-approval-grant-separation",
            "v1330",
            "approval grant separation evidence slot",
            "Does the future artifact prove approval grant review remains separate?",
            "reject-missing-approval-grant-separation-evidence",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckPolicyExecutionArchiveService
                .ENDPOINT),
        slot(
            "compared-package-evidence-slot-archive-closeout",
            "v1331",
            "archive closeout evidence slot",
            "Does the future artifact provide archive closeout evidence without writing files?",
            "reject-missing-archive-closeout-evidence",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckPolicyExecutionArchiveService
                .ENDPOINT));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
          .EvidenceSlot
      slot(
          String code,
          String sourceVersion,
          String evidenceSlot,
          String evidenceQuestion,
          String missingEvidenceGuard,
          String sourceEndpoint) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport
        .slot(
            code,
            sourceVersion,
            evidenceSlot,
            evidenceQuestion,
            missingEvidenceGuard,
            sourceEndpoint);
  }
}
