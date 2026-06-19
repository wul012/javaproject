package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightOperatorPackageService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_OPERATOR_PACKAGE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-operator.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      operatorPackage() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
        .response(
            "Java v848",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                .slots(4, 8),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .guards(4, 8),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .gates(2, 4),
            List.of(
                "signed-approval-artifact-draft-review-package-preflight-operator-slots-ready",
                "signed-approval-artifact-draft-review-package-preflight-capture-policy-slots-ready",
                "signed-approval-artifact-draft-review-package-preflight-no-grant-or-adapter-enable"));
  }
}
