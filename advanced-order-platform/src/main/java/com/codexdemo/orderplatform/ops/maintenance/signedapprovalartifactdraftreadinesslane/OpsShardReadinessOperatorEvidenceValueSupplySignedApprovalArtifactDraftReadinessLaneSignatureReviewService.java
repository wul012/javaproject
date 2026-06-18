package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSignatureReviewService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_SIGNATURE_REVIEW;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-lane-signature.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
      signatureReview() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
        .response(
            "Java v824",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog
                .lanes(8, 13),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog
                .blockers(8, 13),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog
                .gates(4, 7),
            List.of(
                "signed-approval-artifact-draft-readiness-lane-signature-review-ready",
                "signed-approval-artifact-draft-readiness-lane-statement-digest-pin-ready",
                "signed-approval-artifact-draft-readiness-lane-no-signature-capture"));
  }
}
