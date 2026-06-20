package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths;
import java.util.List;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSubmissionComparisonService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths
              .BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_COMPARED_PACKAGE_EVIDENCE_INTAKE_SUBMISSION_COMPARISON;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-evidence-intake-submission-comparison.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse
      submissionComparison() {
    var slots =
        Stream.concat(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSourceSubmissionSlotCatalog
                    .sourceSubmissionSlots()
                    .subList(1, 2)
                    .stream(),
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeComparisonIdentitySlotCatalog
                    .comparisonIdentitySlots()
                    .subList(0, 1)
                    .stream())
            .toList();
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService
        .response(
            "Java v1021",
            ENDPOINT,
            PROFILE,
            slots,
            List.of(
                "draft-text-package-compared-package-evidence-intake-submission-comparison-slots"));
  }
}
