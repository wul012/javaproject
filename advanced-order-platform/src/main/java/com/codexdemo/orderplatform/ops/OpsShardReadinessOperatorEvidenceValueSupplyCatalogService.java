package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyCatalogService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_CATALOG;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-catalog.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyResponse catalog() {
        return OpsShardReadinessOperatorEvidenceValueSupplySupport.response(
                "Java v636",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.allSlots(),
                List.of(
                        "value-supply-catalog-slot-count-25",
                        "value-supply-catalog-source-draft-v633",
                        "value-supply-catalog-node-v936-disabled-envelope"
                )
        );
    }
}
