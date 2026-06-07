package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_COMPATIBILITY_MATRIX;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-compatibility-matrix.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse matrix() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v664",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(0, 4),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.rules(0, 4),
                List.of(
                        "value-supply-adapter-preflight-compatibility-slice-1-4",
                        "value-supply-adapter-preflight-compatibility-metadata-only",
                        "value-supply-adapter-preflight-compatibility-no-value-body",
                        "value-supply-adapter-preflight-compatibility-no-approval-capture"
                )
        );
    }
}
