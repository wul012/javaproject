package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessDigestPinService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_DIGEST_PINS;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-authoring-readiness-digest-pins.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      digestPins() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
        .response(
            "Java v872",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog
                .requirements(0, 4),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .blockers(0, 4),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .gates(0, 2),
            List.of(
                "signed-approval-artifact-draft-authoring-readiness-digest-pins-source-review-package",
                "signed-approval-artifact-draft-authoring-readiness-digest-pins-no-instruction-generation"));
  }
}
