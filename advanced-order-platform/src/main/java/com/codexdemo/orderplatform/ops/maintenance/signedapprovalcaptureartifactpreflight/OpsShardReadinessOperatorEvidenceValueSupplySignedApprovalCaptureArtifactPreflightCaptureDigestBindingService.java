package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCaptureDigestBindingService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_CAPTURE_DIGEST;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-artifact-preflight-capture-digest.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      binding() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
        .response(
            "Java v745",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                .fragments(0, 2),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                .seals(0, 2),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
                .gates(0, 2),
            List.of(
                "signed-approval-capture-artifact-preflight-request-id-sealed",
                "signed-approval-capture-artifact-preflight-capture-digest-bound",
                "signed-approval-capture-artifact-preflight-capture-digest-no-artifact-materialization"));
  }
}
