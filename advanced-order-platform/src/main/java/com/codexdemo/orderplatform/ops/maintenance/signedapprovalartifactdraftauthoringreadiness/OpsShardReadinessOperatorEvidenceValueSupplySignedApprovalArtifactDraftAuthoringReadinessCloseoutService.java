package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-authoring-readiness-closeout.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      closeout() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
        .response(
            "Java v879",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog
                .allRequirements(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .allBlockers(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .allGates(),
            List.of(
                "signed-approval-artifact-draft-authoring-readiness-closeout-all-requirements",
                "signed-approval-artifact-draft-authoring-readiness-closeout-all-blockers",
                "signed-approval-artifact-draft-authoring-readiness-closeout-separate-human-draft-package-required",
                "signed-approval-artifact-draft-authoring-readiness-closeout-no-runtime-or-sibling-write"));
  }
}
