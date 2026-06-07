package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_MISSING_VALUE_REJECTION;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-missing-value-rejection.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse rejection() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v670",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(8, 12),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.rules(10, 12),
                List.of(
                        "value-supply-adapter-preflight-missing-policy-slice-9-12",
                        "value-supply-adapter-preflight-missing-values-rejected",
                        "value-supply-adapter-preflight-blank-values-rejected",
                        "value-supply-adapter-preflight-manual-entry-locked",
                        "value-supply-adapter-preflight-reviewer-required-before-adapter"
                )
        );
    }
}
