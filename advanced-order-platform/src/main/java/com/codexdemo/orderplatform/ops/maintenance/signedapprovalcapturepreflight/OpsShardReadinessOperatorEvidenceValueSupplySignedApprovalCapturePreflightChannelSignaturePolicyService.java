package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCapturePreflightRoutePaths;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightChannelSignaturePolicyService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalCapturePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CHANNEL_SIGNATURE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-channel-signature.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
      policy() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
        .response(
            "Java v724",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .inputs(7, 10),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .attestations(7, 10),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .policies(6, 9),
            List.of(
                "signed-approval-capture-preflight-channel-policy-placeholder",
                "signed-approval-capture-preflight-signature-algorithm-placeholder",
                "signed-approval-capture-preflight-signature-material-redacted"));
  }
}
