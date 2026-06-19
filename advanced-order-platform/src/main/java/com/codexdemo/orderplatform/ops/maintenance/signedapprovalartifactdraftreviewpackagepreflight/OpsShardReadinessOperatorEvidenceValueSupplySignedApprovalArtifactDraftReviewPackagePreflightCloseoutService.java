package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-closeout.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      closeout() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
        .response(
            "Java v854",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                .allSlots(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .allGuards(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .allGates(),
            List.of(
                "signed-approval-artifact-draft-review-package-preflight-closeout-versions-v835-v859",
                "signed-approval-artifact-draft-review-package-preflight-closeout-slot-count-25",
                "signed-approval-artifact-draft-review-package-preflight-closeout-guard-count-25",
                "signed-approval-artifact-draft-review-package-preflight-closeout-gate-count-20",
                "signed-approval-artifact-draft-review-package-preflight-closeout-source-node-v1161",
                "signed-approval-artifact-draft-review-package-preflight-closeout-source-node-readiness-v1136",
                "signed-approval-artifact-draft-review-package-preflight-closeout-source-java-readiness-v834",
                "signed-approval-artifact-draft-review-package-preflight-closeout-no-review-artifact-creation",
                "signed-approval-artifact-draft-review-package-preflight-closeout-no-signed-draft-text",
                "signed-approval-artifact-draft-review-package-preflight-closeout-no-signature-capture",
                "signed-approval-artifact-draft-review-package-preflight-closeout-no-approval-grant",
                "signed-approval-artifact-draft-review-package-preflight-closeout-no-value-import-runtime-sibling-mutation",
                "signed-approval-artifact-draft-review-package-preflight-closeout-next-step-human-draft-artifact-plan"));
  }
}
