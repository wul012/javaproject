package com.codexdemo.orderplatform.ops.maintenance.approvalpreflight;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightZeroValueLedgerService {

  public static final String ENDPOINT =
      OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths.BASE_PATH
          + OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_ZERO_VALUE_LEDGER;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-approval-preflight-zero-value-ledger.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse ledger() {
    return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
        "Java v700",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(19, 22),
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(15, 16),
        List.of(
            "value-supply-approval-preflight-zero-ledger-slice-20-22",
            "value-supply-approval-preflight-zero-supplied-value-count",
            "value-supply-approval-preflight-zero-accepted-value-count",
            "value-supply-approval-preflight-zero-imported-value-count",
            "value-supply-approval-preflight-zero-counts-import-locked",
            "value-supply-approval-preflight-zero-counts-production-locked"));
  }
}
