package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCapturePreflightRoutePaths;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightFailClosedLockService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalCapturePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_FAIL_CLOSED_LOCKS;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-locks.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
      locks() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
        .response(
            "Java v732",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .inputs(19, 24),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .attestations(19, 24),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .policies(15, 20),
            List.of(
                "signed-approval-capture-preflight-raw-secret-signature-material-locked",
                "signed-approval-capture-preflight-approval-grant-not-emitted",
                "signed-approval-capture-preflight-zero-value-counts-held",
                "signed-approval-capture-preflight-no-write-route",
                "signed-approval-capture-preflight-sibling-non-mutation-held"));
  }
}
