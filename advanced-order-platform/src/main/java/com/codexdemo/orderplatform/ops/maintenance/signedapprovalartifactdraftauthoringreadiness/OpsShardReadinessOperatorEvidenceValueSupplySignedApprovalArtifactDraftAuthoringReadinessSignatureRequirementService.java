package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftauthoringreadiness;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSignatureRequirementService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftAuthoringReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_AUTHORING_READINESS_SIGNATURE_REQUIREMENTS;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-authoring-readiness-signature-requirements.v1";

  @Transactional(readOnly = true)
  public
  OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessResponse
      signatureRequirements() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessSupport
        .response(
            "Java v874",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessRequirementCatalog
                .requirements(8, 13),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .blockers(8, 13),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftAuthoringReadinessBlockerCatalog
                .gates(4, 7),
            List.of(
                "signed-approval-artifact-draft-authoring-readiness-signature-policy-only",
                "signed-approval-artifact-draft-authoring-readiness-signature-no-detached-payload"));
  }
}
