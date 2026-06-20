package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonpreflight.OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService;
import java.util.List;

final
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeDigestSignatureSlotCatalog {

  private
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeDigestSignatureSlotCatalog() {}

  static List<
          OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
              .EvidenceSlot>
      digestSignatureSlots() {
    return List.of(
        slot(
            "compared-package-evidence-slot-digest-match-summary",
            "v1326",
            "digest match summary evidence slot",
            "Does the future artifact provide digest match evidence without hashing submitted text here?",
            "reject-missing-digest-match-summary-evidence"),
        slot(
            "compared-package-evidence-slot-detached-signature-observation",
            "v1327",
            "detached signature envelope observation evidence slot",
            "Does the future artifact observe signature envelope evidence without parsing payload?",
            "reject-missing-detached-signature-observation-evidence"));
  }

  private static
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
          .EvidenceSlot
      slot(
          String code,
          String sourceVersion,
          String evidenceSlot,
          String evidenceQuestion,
          String missingEvidenceGuard) {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport
        .slot(
            code,
            sourceVersion,
            evidenceSlot,
            evidenceQuestion,
            missingEvidenceGuard,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonPreflightDigestSignatureService
                .ENDPOINT);
  }
}
