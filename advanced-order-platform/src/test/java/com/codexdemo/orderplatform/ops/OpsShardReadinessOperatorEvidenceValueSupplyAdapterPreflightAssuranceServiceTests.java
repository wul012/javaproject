package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceServiceTests {

    @Test
    void buildsPayloadFirewallWithoutRuntimePayloadOrImportPreview() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse firewall =
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService()
                        .firewall();

        assertThat(firewall.version()).isEqualTo("Java v674");
        assertThat(firewall.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/operator-evidence-value-supply-adapter-preflight-payload-firewall");
        assertThat(firewall.profile()).isEqualTo(
                "java-shard-readiness-operator-evidence-value-supply-adapter-preflight-payload-firewall.v1");
        assertThat(firewall.readyForRuntimePayload()).isFalse();
        assertThat(firewall.readyForEvidenceImport()).isFalse();
        assertThat(firewall.slotCount()).isEqualTo(2);
        assertThat(firewall.ruleCount()).isEqualTo(2);
        assertThat(firewall.slots())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterSlot::code)
                .containsExactly(
                        "ADAPTER_PREFLIGHT_20_RUNTIME_PAYLOAD_BLOCK",
                        "ADAPTER_PREFLIGHT_21_IMPORT_PREVIEW_BLOCK"
                );
        assertThat(firewall.rules())
                .extracting(OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.AdapterRule::code)
                .containsExactly(
                        "ADAPTER_RULE_15_RUNTIME_PAYLOAD_BLOCKED",
                        "ADAPTER_RULE_16_NO_STATE_WRITE"
                );
        assertThat(firewall.checks()).contains(
                "value-supply-adapter-preflight-payload-firewall-runtime-payload-blocked",
                "value-supply-adapter-preflight-payload-firewall-import-preview-locked",
                "value-supply-adapter-preflight-payload-firewall-no-state-write"
        );
        assertThat(firewall.status()).isEqualTo("passed");
    }
}
