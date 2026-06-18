package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftreadinesslane;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneEvidenceReviewService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftReadinessLaneRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_READINESS_LANE_EVIDENCE_REVIEW;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-lane-evidence.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse
      evidenceReview() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport
        .response(
            "Java v825",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalog
                .lanes(12, 15),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog
                .blockers(12, 15),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog
                .gates(6, 7),
            List.of(
                "signed-approval-artifact-draft-readiness-lane-evidence-version-review-ready",
                "signed-approval-artifact-draft-readiness-lane-evidence-file-snippet-review-ready",
                "signed-approval-artifact-draft-readiness-lane-no-evidence-import"));
  }
}
