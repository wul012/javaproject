package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightRedactionProvenanceBindingService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_REDACTION_PROVENANCE;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-redaction-provenance.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
      binding() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
        .response(
            "Java v730",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .inputs(15, 19),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .attestations(15, 19),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .policies(12, 15),
            List.of(
                "signed-approval-capture-preflight-redacted-digest-reference-only",
                "signed-approval-capture-preflight-value-shape-no-body",
                "signed-approval-capture-preflight-redaction-policy-mirrored",
                "signed-approval-capture-preflight-provenance-policy-mirrored"));
  }
}
