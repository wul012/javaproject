package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_SOURCE_EVIDENCE_GUARD;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-source-evidence-guard.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse guard() {
        return OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
                "Java v646",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.slots(16, 20),
                List.of(
                        "value-supply-source-evidence-guard-slice-17-20",
                        "value-supply-source-evidence-fresh-sibling-read-only",
                        "value-supply-source-evidence-fallback-explicit",
                        "value-supply-source-evidence-synthetic-blocked"
                )
        );
    }
}
