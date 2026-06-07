package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightDigestBlueprintService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_APPROVAL_PREFLIGHT_DIGEST_BLUEPRINT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-approval-preflight-digest-blueprint.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightResponse blueprint() {
        return OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupport.response(
                "Java v706",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.allItems(),
                OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightPolicyCatalog.allPolicies(),
                List.of(
                        "value-supply-approval-preflight-digest-blueprint-item-count-25",
                        "value-supply-approval-preflight-digest-blueprint-policy-count-20",
                        "value-supply-approval-preflight-digest-blueprint-no-value-hash",
                        "value-supply-approval-preflight-digest-blueprint-no-approval-capture",
                        "value-supply-approval-preflight-digest-blueprint-zero-value-counts",
                        "value-supply-approval-preflight-digest-blueprint-import-firewall-covered",
                        "value-supply-approval-preflight-digest-blueprint-runtime-locks-covered"
                )
        );
    }
}
