package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightArchivePlanService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_ARCHIVE_PLAN;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-approval-preflight-archive-plan.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse plan() {
        return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
                "Java v708",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.items(20, 25),
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.allPolicies(),
                List.of(
                        "value-supply-approval-preflight-archive-plan-external-capture",
                        "value-supply-approval-preflight-archive-plan-route-output-only",
                        "value-supply-approval-preflight-archive-plan-no-file-write",
                        "value-supply-approval-preflight-archive-plan-no-process-start",
                        "value-supply-approval-preflight-archive-plan-no-approval-capture",
                        "value-supply-approval-preflight-archive-plan-no-import-or-runtime"
                )
        );
    }
}
