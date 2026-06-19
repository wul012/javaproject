package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightEvidenceValueService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_EVIDENCE_VALUE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-submission-preflight-evidence-value.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      evidenceValue() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService
        .response(
            "Java v968",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog
                .slots(11, 16),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog
                .controls(11, 16),
            List.of("draft-text-package-submission-preflight-evidence-value-no-import"));
  }
}
