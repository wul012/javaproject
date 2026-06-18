package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_DIGEST_CHAIN;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-preflight-digest.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      digestChain() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
        .response(
            "Java v797",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                .fields(0, 4),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                .guards(0, 4),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog
                .gates(0, 2),
            List.of(
                "signed-approval-artifact-draft-preflight-digest-chain-request-ready",
                "signed-approval-artifact-draft-preflight-digest-chain-source-bound",
                "signed-approval-artifact-draft-preflight-digest-chain-no-manual-draft"));
  }
}
