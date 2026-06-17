package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightTimingWindowService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_TIMING_WINDOW;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-timing-window.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
      window() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
        .response(
            "Java v722",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .inputs(5, 7),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .attestations(5, 7),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .policies(5, 6),
            List.of(
                "signed-approval-capture-preflight-timestamp-source-placeholder",
                "signed-approval-capture-preflight-manual-window-placeholder",
                "signed-approval-capture-preflight-timing-does-not-open-runtime"));
  }
}
