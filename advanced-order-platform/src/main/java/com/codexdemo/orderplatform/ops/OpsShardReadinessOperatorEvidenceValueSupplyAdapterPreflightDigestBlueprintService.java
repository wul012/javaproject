package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_DIGEST_BLUEPRINT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-digest-blueprint.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse blueprint() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v680",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.allSlots(),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.allRules(),
                List.of(
                        "value-supply-adapter-preflight-digest-blueprint-slot-count-25",
                        "value-supply-adapter-preflight-digest-blueprint-rule-count-18",
                        "value-supply-adapter-preflight-digest-blueprint-no-value-hash",
                        "value-supply-adapter-preflight-digest-blueprint-source-supply-v658",
                        "value-supply-adapter-preflight-digest-blueprint-node-v986-approval-draft-boundary",
                        "value-supply-adapter-preflight-digest-blueprint-lock-flags-covered"
                )
        );
    }
}
