package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightProvenanceBindingService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_PROVENANCE_BINDING;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-approval-preflight-provenance-binding.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse binding() {
    return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
        "Java v696",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(12, 16),
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(10, 12),
        List.of(
            "value-supply-approval-preflight-provenance-slice-13-16",
            "value-supply-approval-preflight-provenance-source-id-required",
            "value-supply-approval-preflight-provenance-evidence-file-required",
            "value-supply-approval-preflight-provenance-snippet-required",
            "value-supply-approval-preflight-provenance-endpoint-alias-only",
            "value-supply-approval-preflight-provenance-import-still-locked"));
  }
}
