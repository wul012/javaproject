package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCleanupReceiptService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CLEANUP_RECEIPT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-approval-preflight-cleanup-receipt.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse receipt() {
        return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
                "Java v702",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(22, 23),
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.policies(16, 17),
                List.of(
                        "value-supply-approval-preflight-cleanup-receipt-id-required",
                        "value-supply-approval-preflight-cleanup-receipt-no-file-write",
                        "value-supply-approval-preflight-cleanup-receipt-no-process-start",
                        "value-supply-approval-preflight-cleanup-receipt-import-still-locked"
                )
        );
    }
}
