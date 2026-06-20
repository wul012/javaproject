package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightIdentityRequestService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeComparisonIdentitySlotCatalog {

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeComparisonIdentitySlotCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
              .EvidenceSlot>
      comparisonIdentitySlots() {
    return List.of(
        slot(
            "compared-package-evidence-slot-offline-comparison-result",
            "v1324",
            "offline comparison result evidence slot",
            "Does the future artifact provide a result reference without treating comparison as acceptance?",
            "reject-missing-offline-comparison-result-evidence",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightCatalogService
                .ENDPOINT),
        slot(
            "compared-package-evidence-slot-identity-binding",
            "v1325",
            "identity binding evidence slot",
            "Does the future artifact bind identity evidence to compared lanes without parsing text?",
            "reject-missing-identity-binding-evidence",
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightIdentityRequestService
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
