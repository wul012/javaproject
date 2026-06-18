package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneOperatorReviewService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_OPERATOR_REVIEW;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-lane-operator.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
      operatorReview() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
        .response(
            "Java v823",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog
                .lanes(4, 8),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog
                .blockers(4, 8),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog
                .gates(2, 4),
            List.of(
                "signed-approval-artifact-draft-readiness-lane-operator-identity-role-review-ready",
                "signed-approval-artifact-draft-readiness-lane-capture-window-channel-review-ready",
                "signed-approval-artifact-draft-readiness-lane-no-approval-grant-or-write-route"));
  }
}
