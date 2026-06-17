package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSourceEvidenceMirrorService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_SOURCE_EVIDENCE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-source-evidence.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
      mirror() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
        .response(
            "Java v728",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .inputs(12, 15),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .attestations(12, 15),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .policies(11, 12),
            List.of(
                "signed-approval-capture-preflight-source-evidence-version-mirrored",
                "signed-approval-capture-preflight-source-evidence-file-id-mirrored",
                "signed-approval-capture-preflight-source-evidence-snippet-id-mirrored",
                "signed-approval-capture-preflight-source-evidence-not-imported"));
  }
}
