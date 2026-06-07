package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_PAYLOAD_FIREWALL;
    static final String PROFILE =
            "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-payload-firewall.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse firewall() {
        return OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.response(
                "Java v674",
                ENDPOINT,
                PROFILE,
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.slots(19, 21),
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.rules(14, 16),
                List.of(
                        "value-supply-adapter-preflight-payload-firewall-slice-20-21",
                        "value-supply-adapter-preflight-payload-firewall-runtime-payload-blocked",
                        "value-supply-adapter-preflight-payload-firewall-import-preview-locked",
                        "value-supply-adapter-preflight-payload-firewall-no-state-write"
                )
        );
    }
}
