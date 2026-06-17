package com.codexdemo.orderplatform.ops.maintenance.signedapprovalcapturepreflight;

import com.codexdemo.orderplatform.ops.maintenance.signedapproval.OpsShardReadinessSignedApprovalCapturePreflightRoutePaths;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public
class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightCatalogService {

  public static final String ENDPOINT =
      OpsShardReadinessSignedApprovalCapturePreflightRoutePaths.BASE_PATH
          + OpsShardReadinessSignedApprovalCapturePreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_CAPTURE_PREFLIGHT_CATALOG;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-signed-approval-capture-preflight-catalog.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightResponse
      catalog() {
    return OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightSupport
        .response(
            "Java v714",
            ENDPOINT,
            PROFILE,
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightInputCatalog
                .allInputs(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightAttestationCatalog
                .allAttestations(),
            OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCapturePreflightPolicyCatalog
                .allPolicies(),
            List.of(
                "signed-approval-capture-preflight-catalog-input-count-25",
                "signed-approval-capture-preflight-catalog-attestation-count-25",
                "signed-approval-capture-preflight-catalog-policy-count-20",
                "signed-approval-capture-preflight-catalog-derived-from-node-v1061",
                "signed-approval-capture-preflight-catalog-no-capture-artifact"));
  }
}
