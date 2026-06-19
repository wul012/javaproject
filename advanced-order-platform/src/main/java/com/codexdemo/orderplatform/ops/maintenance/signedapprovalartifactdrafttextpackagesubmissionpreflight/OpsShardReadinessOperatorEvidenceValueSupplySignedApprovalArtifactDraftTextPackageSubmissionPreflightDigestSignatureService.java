package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightDigestSignatureService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftTextPackageSubmissionPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_TEXT_PACKAGE_SUBMISSION_PREFLIGHT_DIGEST_SIGNATURE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-submission-preflight-digest-signature.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse
      digestSignature() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService
        .response(
            "Java v967",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightSlotCatalog
                .slots(4, 11),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog
                .controls(4, 11),
            List.of("draft-text-package-submission-preflight-digest-signature-compare-only"));
  }
}
