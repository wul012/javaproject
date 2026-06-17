package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
      closeout() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
        .response(
            "Java v734",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .allInputs(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .allAttestations(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .allPolicies(),
            List.of(
                "signed-approval-capture-preflight-closeout-versions-v710-v734",
                "signed-approval-capture-preflight-closeout-input-count-25",
                "signed-approval-capture-preflight-closeout-attestation-count-25",
                "signed-approval-capture-preflight-closeout-policy-count-20",
                "signed-approval-capture-preflight-closeout-source-node-v1061",
                "signed-approval-capture-preflight-closeout-source-template-node-v1036",
                "signed-approval-capture-preflight-closeout-source-review-node-v1011",
                "signed-approval-capture-preflight-closeout-source-java-v709",
                "signed-approval-capture-preflight-closeout-no-signed-approval-capture",
                "signed-approval-capture-preflight-closeout-no-approval-grant",
                "signed-approval-capture-preflight-closeout-zero-values",
                "signed-approval-capture-preflight-closeout-no-import-runtime-or-sibling-mutation",
                "signed-approval-capture-preflight-closeout-next-step-requires-separate-artifact-plan"));
  }
}
