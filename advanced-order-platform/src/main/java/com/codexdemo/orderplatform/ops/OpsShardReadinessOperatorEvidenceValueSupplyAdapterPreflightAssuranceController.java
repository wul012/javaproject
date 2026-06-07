package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController {

    private final OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService
            payloadFirewallService;

    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController(
            OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService
                    payloadFirewallService
    ) {
        this.payloadFirewallService = payloadFirewallService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_PAYLOAD_FIREWALL)
    public OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse payloadFirewall() {
        return payloadFirewallService.firewall();
    }
}
