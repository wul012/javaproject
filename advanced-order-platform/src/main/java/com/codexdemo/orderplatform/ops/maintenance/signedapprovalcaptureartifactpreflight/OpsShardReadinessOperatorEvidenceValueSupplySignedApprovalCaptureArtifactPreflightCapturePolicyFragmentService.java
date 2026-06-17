package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCapturePolicyFragmentService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_POLICY;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-artifact-preflight-policy.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      policy() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
        .response(
            "Java v748",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                .fragments(6, 8),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                .seals(6, 8),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
                .gates(5, 7),
            List.of(
                "signed-approval-capture-artifact-preflight-window-id-sealed",
                "signed-approval-capture-artifact-preflight-channel-policy-sealed",
                "signed-approval-capture-artifact-preflight-capture-policy-no-write-route"));
  }
}
