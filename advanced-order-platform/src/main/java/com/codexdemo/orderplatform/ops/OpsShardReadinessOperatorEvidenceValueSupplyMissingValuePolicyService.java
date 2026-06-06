package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_MISSING_VALUE_POLICY;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-missing-value-policy.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse policy() {
        return OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
                "Java v642",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(8, 12),
                List.of(
                        "value-supply-missing-policy-slice-9-12",
                        "value-supply-missing-values-not-defaulted",
                        "value-supply-blank-values-rejected",
                        "value-supply-manual-entry-still-locked"
                )
        );
    }
}
