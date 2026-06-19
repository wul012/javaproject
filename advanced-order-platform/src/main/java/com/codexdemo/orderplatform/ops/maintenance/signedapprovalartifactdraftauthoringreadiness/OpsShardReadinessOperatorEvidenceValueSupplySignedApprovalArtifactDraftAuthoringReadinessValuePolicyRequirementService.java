package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessValuePolicyRequirementService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_VALUE_POLICY_REQUIREMENTS;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-authoring-readiness-value-policy-requirements.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      valuePolicyRequirements() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
        .response(
            "Java v876",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog
                .requirements(15, 19),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .blockers(15, 19),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .gates(7, 9),
            List.of(
                "signed-approval-artifact-draft-authoring-readiness-value-redacted-digest-shape-policy",
                "signed-approval-artifact-draft-authoring-readiness-value-no-raw-value"));
  }
}
