package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightOperatorInputMirrorService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_OPERATOR_INPUT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-operator-input.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
      mirror() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
        .response(
            "Java v720",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .inputs(3, 5),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .attestations(3, 5),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .policies(3, 5),
            List.of(
                "signed-approval-capture-preflight-operator-alias-mirrored",
                "signed-approval-capture-preflight-operator-role-mirrored",
                "signed-approval-capture-preflight-operator-cannot-authorize-capture"));
  }
}
