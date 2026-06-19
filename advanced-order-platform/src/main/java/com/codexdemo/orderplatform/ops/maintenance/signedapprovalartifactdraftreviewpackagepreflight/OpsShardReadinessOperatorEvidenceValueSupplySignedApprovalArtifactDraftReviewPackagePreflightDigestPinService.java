package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreviewpackagepreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDigestPinService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReviewPackagePreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_REVIEW_PACKAGE_PREFLIGHT_DIGEST_PINS;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-digest.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightResponse
      digestPins() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSupport
        .response(
            "Java v847",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightSlotCatalog
                .slots(0, 4),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .guards(0, 4),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightGuardCatalog
                .gates(0, 2),
            List.of(
                "signed-approval-artifact-draft-review-package-preflight-request-manifest-slot-ready",
                "signed-approval-artifact-draft-review-package-preflight-digest-slots-ready",
                "signed-approval-artifact-draft-review-package-preflight-no-package-materialization"));
  }
}
