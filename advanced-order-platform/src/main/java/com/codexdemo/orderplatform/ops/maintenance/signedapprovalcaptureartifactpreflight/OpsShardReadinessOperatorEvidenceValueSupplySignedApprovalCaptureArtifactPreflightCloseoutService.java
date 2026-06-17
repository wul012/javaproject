package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-artifact-preflight-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      closeout() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
        .response(
            "Java v754",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                .allFragments(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                .allSeals(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
                .allGates(),
            List.of(
                "signed-approval-capture-artifact-preflight-closeout-versions-v735-v759",
                "signed-approval-capture-artifact-preflight-closeout-fragment-count-25",
                "signed-approval-capture-artifact-preflight-closeout-seal-count-25",
                "signed-approval-capture-artifact-preflight-closeout-gate-count-20",
                "signed-approval-capture-artifact-preflight-closeout-source-node-v1086",
                "signed-approval-capture-artifact-preflight-closeout-source-capture-node-v1061",
                "signed-approval-capture-artifact-preflight-closeout-source-java-v734",
                "signed-approval-capture-artifact-preflight-closeout-no-artifact-materialization",
                "signed-approval-capture-artifact-preflight-closeout-no-signed-approval-capture",
                "signed-approval-capture-artifact-preflight-closeout-no-approval-grant",
                "signed-approval-capture-artifact-preflight-closeout-no-value-import-runtime-or-sibling-mutation",
                "signed-approval-capture-artifact-preflight-closeout-next-step-requires-separate-draft-plan"));
  }
}
