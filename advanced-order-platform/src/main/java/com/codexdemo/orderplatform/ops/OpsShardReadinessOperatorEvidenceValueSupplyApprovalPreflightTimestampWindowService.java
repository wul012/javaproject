package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightTimestampWindowService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_TIMESTAMP_WINDOW;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-approval-preflight-timestamp-window.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse window() {
        return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
                "Java v692",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(5, 8),
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(5, 8),
                List.of(
                        "value-supply-approval-preflight-timestamp-slice-6-8",
                        "value-supply-approval-preflight-timestamp-issued-at-required",
                        "value-supply-approval-preflight-timestamp-expiry-window-required",
                        "value-supply-approval-preflight-timestamp-replay-nonce-required",
                        "value-supply-approval-preflight-timestamp-no-secret-material",
                        "value-supply-approval-preflight-timestamp-capture-still-locked"
                )
        );
    }
}
