package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CATALOG;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-catalog.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse catalog() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v662",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.allSlots(),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.allRules(),
                List.of(
                        "value-supply-adapter-preflight-catalog-slot-count-25",
                        "value-supply-adapter-preflight-catalog-rule-count-18",
                        "value-supply-adapter-preflight-catalog-source-supply-v658",
                        "value-supply-adapter-preflight-catalog-node-v986-approval-draft-boundary"
                )
        );
    }
}
