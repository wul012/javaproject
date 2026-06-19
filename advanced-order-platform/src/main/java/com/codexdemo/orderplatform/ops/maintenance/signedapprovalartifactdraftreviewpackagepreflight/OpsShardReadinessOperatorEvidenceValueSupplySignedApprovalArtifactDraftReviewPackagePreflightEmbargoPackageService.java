package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_EMBARGO_PACKAGE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-embargo.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      embargoPackage() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
        .response(
            "Java v852",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                .slots(19, 24),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .guards(19, 24),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .gates(9, 19),
            List.of(
                "signed-approval-artifact-draft-review-package-preflight-raw-secret-embargo-ready",
                "signed-approval-artifact-draft-review-package-preflight-approval-grant-embargo-ready",
                "signed-approval-artifact-draft-review-package-preflight-zero-value-import-embargo-ready",
                "signed-approval-artifact-draft-review-package-preflight-write-route-embargo-ready",
                "signed-approval-artifact-draft-review-package-preflight-sibling-non-mutation-ready"));
  }
}
