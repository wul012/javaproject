package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightImportFirewallService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths
              .OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_IMPORT_FIREWALL;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-approval-preflight-import-firewall.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse firewall() {
    return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
        "Java v704",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(20, 25),
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(17, 19),
        List.of(
            "value-supply-approval-preflight-import-firewall-slice-21-25",
            "value-supply-approval-preflight-import-firewall-no-import-preview",
            "value-supply-approval-preflight-import-firewall-no-evidence-import",
            "value-supply-approval-preflight-import-firewall-no-runtime-payload",
            "value-supply-approval-preflight-import-firewall-no-live-execution",
            "value-supply-approval-preflight-import-firewall-no-production-execution"));
  }
}
