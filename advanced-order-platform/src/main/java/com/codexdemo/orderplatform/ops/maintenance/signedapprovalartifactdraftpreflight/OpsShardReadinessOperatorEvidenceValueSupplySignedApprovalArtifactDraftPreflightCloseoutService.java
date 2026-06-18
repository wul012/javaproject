package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-preflight-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      closeout() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
        .response(
            "Java v804",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                .allFields(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                .allGuards(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog
                .allGates(),
            List.of(
                "signed-approval-artifact-draft-preflight-closeout-versions-v785-v809",
                "signed-approval-artifact-draft-preflight-closeout-field-count-25",
                "signed-approval-artifact-draft-preflight-closeout-guard-count-25",
                "signed-approval-artifact-draft-preflight-closeout-gate-count-20",
                "signed-approval-artifact-draft-preflight-closeout-source-node-v1111",
                "signed-approval-artifact-draft-preflight-closeout-source-artifact-preflight-node-v1086",
                "signed-approval-artifact-draft-preflight-closeout-source-java-readiness-v784",
                "signed-approval-artifact-draft-preflight-closeout-no-real-manual-draft",
                "signed-approval-artifact-draft-preflight-closeout-no-draft-materialization",
                "signed-approval-artifact-draft-preflight-closeout-no-signature-capture",
                "signed-approval-artifact-draft-preflight-closeout-no-approval-grant",
                "signed-approval-artifact-draft-preflight-closeout-no-value-import-runtime-sibling-mutation",
                "signed-approval-artifact-draft-preflight-closeout-next-step-requires-explicit-manual-draft-plan"));
  }
}
