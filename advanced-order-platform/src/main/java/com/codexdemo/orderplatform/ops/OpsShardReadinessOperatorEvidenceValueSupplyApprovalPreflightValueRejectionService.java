package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightValueRejectionService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_VALUE_REJECTION;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-approval-preflight-value-rejection.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse rejection() {
        return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
                "Java v698",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(16, 19),
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(12, 15),
                List.of(
                        "value-supply-approval-preflight-value-rejection-slice-17-19",
                        "value-supply-approval-preflight-value-envelope-reference-only",
                        "value-supply-approval-preflight-malformed-values-rejected",
                        "value-supply-approval-preflight-missing-values-rejected",
                        "value-supply-approval-preflight-no-synthetic-values",
                        "value-supply-approval-preflight-value-import-still-locked"
                )
        );
    }
}
