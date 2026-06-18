package com.codexdemo.orderplatform.ops.maintenance.signedapprovalartifactdraftpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalArtifactDraftPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_ARTIFACT_DRAFT_PREFLIGHT_FAIL_CLOSED_LOCKS;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-preflight-locks.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightResponse
      locks() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSupport
        .response(
            "Java v802",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFieldCatalog
                .fields(19, 24),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGuardCatalog
                .guards(19, 24),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightGateCatalog
                .gates(9, 19),
            List.of(
                "signed-approval-artifact-draft-preflight-no-raw-secret-ready",
                "signed-approval-artifact-draft-preflight-no-approval-grant-ready",
                "signed-approval-artifact-draft-preflight-zero-value-import-ready",
                "signed-approval-artifact-draft-preflight-no-write-route-ready",
                "signed-approval-artifact-draft-preflight-no-sibling-mutation-ready"));
  }
}
