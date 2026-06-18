package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_SIGNATURE_STATEMENT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-preflight-signature.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      signatureStatement() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
        .response(
            "Java v799",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                .fields(8, 13),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                .guards(8, 13),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog
                .gates(4, 7),
            List.of(
                "signed-approval-artifact-draft-preflight-signature-fields-ready",
                "signed-approval-artifact-draft-preflight-statement-placeholder-ready",
                "signed-approval-artifact-draft-preflight-no-signature-material"));
  }
}
