package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightCatalogService {

    public static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_CATALOG;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-approval-preflight-catalog.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse catalog() {
        return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
                "Java v688",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.allItems(),
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.allPolicies(),
                List.of(
                        "value-supply-approval-preflight-catalog-item-count-25",
                        "value-supply-approval-preflight-catalog-policy-count-20",
                        "value-supply-approval-preflight-catalog-source-node-v986",
                        "value-supply-approval-preflight-catalog-source-java-v658",
                        "value-supply-approval-preflight-catalog-source-adapter-v684"
                )
        );
    }
}
