package com.codexdemo.orderplatform.ops.maintenance.approvalpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRedactionDigestService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_REDACTION_DIGEST;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-approval-preflight-redaction-digest.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse digest() {
    return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
        "Java v694",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(8, 12),
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(8, 10),
        List.of(
            "value-supply-approval-preflight-redaction-digest-slice-9-12",
            "value-supply-approval-preflight-redaction-digest-id-required",
            "value-supply-approval-preflight-redaction-digest-algorithm-required",
            "value-supply-approval-preflight-redaction-credential-absence-proof",
            "value-supply-approval-preflight-redaction-raw-endpoint-absence-proof",
            "value-supply-approval-preflight-redaction-no-value-hash"));
  }
}
