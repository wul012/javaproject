package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_ARCHIVE_PLAN;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-archive-plan.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse plan() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v682",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(20, 25),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.allRules(),
                List.of(
                        "value-supply-adapter-preflight-archive-plan-external-capture",
                        "value-supply-adapter-preflight-archive-plan-no-file-write",
                        "value-supply-adapter-preflight-archive-plan-no-runtime-process",
                        "value-supply-adapter-preflight-archive-plan-route-output-only",
                        "value-supply-adapter-preflight-archive-plan-lock-summary-required"
                )
        );
    }
}
