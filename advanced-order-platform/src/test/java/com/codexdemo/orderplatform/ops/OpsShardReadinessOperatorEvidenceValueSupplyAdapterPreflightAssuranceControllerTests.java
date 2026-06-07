package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceControllerTests {

    @Test
    void exposesPayloadFirewallThroughAssuranceControllerWithoutRuntimePayload() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller = controller();

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

    @Test
    void exposesRuntimeSubmissionLockThroughAssuranceControllerWithoutLiveExecution() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse lock =
                controller.runtimeSubmissionLock();

        assertThat(lock.version()).isEqualTo("Java v676");
        assertThat(lock.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService.ENDPOINT);
        assertThat(lock.readyForOperatorValueSubmission()).isFalse();
        assertThat(lock.readyForEvidenceImport()).isFalse();
        assertThat(lock.readyForLiveExecution()).isFalse();
        assertThat(lock.slotCount()).isEqualTo(4);
        assertThat(lock.ruleCount()).isEqualTo(2);
        assertThat(lock.status()).isEqualTo("passed");
    }

    private OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService()
        );
    }
}
