package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CLOSEOUT;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-closeout.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse closeout() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v684",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.allSlots(),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.allRules(),
                List.of(
                        "value-supply-adapter-preflight-closeout-versions-v660-v684",
                        "value-supply-adapter-preflight-closeout-support-carried-forward-v659",
                        "value-supply-adapter-preflight-closeout-slot-count-25",
                        "value-supply-adapter-preflight-closeout-rule-count-18",
                        "value-supply-adapter-preflight-closeout-foundation-and-assurance-split",
                        "value-supply-adapter-preflight-closeout-source-supply-v658",
                        "value-supply-adapter-preflight-closeout-node-v986-approval-draft-boundary",
                        "value-supply-adapter-preflight-closeout-no-approval-captured",
                        "value-supply-adapter-preflight-closeout-values-not-accepted",
                        "value-supply-adapter-preflight-closeout-import-runtime-live-production-locked",
                        "value-supply-adapter-preflight-closeout-no-file-write-or-process-start",
                        "value-supply-adapter-preflight-closeout-all-locks-held"
                )
        );
    }
}
