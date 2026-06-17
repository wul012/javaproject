package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcaptureartifactpreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightOperatorFragmentService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_ARTIFACT_PREFLIGHT_OPERATOR_FRAGMENT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-artifact-preflight-operator.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse
      fragments() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport
        .response(
            "Java v747",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog
                .fragments(4, 6),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSealCatalog
                .seals(4, 6),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightGateCatalog
                .gates(4, 5),
            List.of(
                "signed-approval-capture-artifact-preflight-operator-identity-sealed",
                "signed-approval-capture-artifact-preflight-operator-role-sealed",
                "signed-approval-capture-artifact-preflight-operator-alias-only"));
  }
}
