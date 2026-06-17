package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCloseoutService {

  public static final String ENDPOINT =
      OpsShardReadinessRoutePaths.BASE_PATH
          + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLOSEOUT;
  static final String PROFILE =
      "java-shard-readiness-operator-evidence-value-supply-approval-preflight-closeout.v1";

  @Transactional(readOnly = true)
  public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse closeout() {
    return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
        "Java v709",
        ENDPOINT,
        PROFILE,
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.allItems(),
        OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.allPolicies(),
        List.of(
            "value-supply-approval-preflight-closeout-versions-v685-v709",
            "value-supply-approval-preflight-closeout-item-count-25",
            "value-supply-approval-preflight-closeout-policy-count-20",
            "value-supply-approval-preflight-closeout-foundation-and-assurance-split",
            "value-supply-approval-preflight-closeout-source-node-v986",
            "value-supply-approval-preflight-closeout-source-envelope-node-v961",
            "value-supply-approval-preflight-closeout-source-java-v658",
            "value-supply-approval-preflight-closeout-source-adapter-v684",
            "value-supply-approval-preflight-closeout-no-approval-capture",
            "value-supply-approval-preflight-closeout-no-approval-grant",
            "value-supply-approval-preflight-closeout-values-not-accepted",
            "value-supply-approval-preflight-closeout-import-runtime-live-production-locked",
            "value-supply-approval-preflight-closeout-no-file-write-or-process-start",
            "value-supply-approval-preflight-closeout-all-locks-held"));
  }
}
