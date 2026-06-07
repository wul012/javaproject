package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceControllerTests {

    @Test
    void exposesPayloadFirewallThroughAssuranceControllerWithoutRuntimePayload() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller =
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController(
                        new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService()
                );

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse firewall =
                controller.payloadFirewall();

        assertThat(firewall.version()).isEqualTo("Java v674");
        assertThat(firewall.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService.ENDPOINT);
        assertThat(firewall.readyForRuntimePayload()).isFalse();
        assertThat(firewall.readyForEvidenceImport()).isFalse();
        assertThat(firewall.slotCount()).isEqualTo(2);
        assertThat(firewall.ruleCount()).isEqualTo(2);
        assertThat(firewall.status()).isEqualTo("passed");
    }
}
