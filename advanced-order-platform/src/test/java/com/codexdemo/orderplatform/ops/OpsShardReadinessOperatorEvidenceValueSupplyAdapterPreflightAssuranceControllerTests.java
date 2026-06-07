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

    @Test
    void exposesOperatorRehearsalChecklistThroughAssuranceControllerWithoutApprovalGrant() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse checklist =
                controller.operatorRehearsalChecklist();

        assertThat(checklist.version()).isEqualTo("Java v678");
        assertThat(checklist.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService.ENDPOINT);
        assertThat(checklist.readyForAdapterImplementation()).isFalse();
        assertThat(checklist.readyForOperatorValueSubmission()).isFalse();
        assertThat(checklist.slotCount()).isEqualTo(13);
        assertThat(checklist.ruleCount()).isEqualTo(18);
        assertThat(checklist.status()).isEqualTo("passed");
    }

    @Test
    void exposesDigestBlueprintThroughAssuranceControllerWithoutValueHash() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse blueprint =
                controller.digestBlueprint();

        assertThat(blueprint.version()).isEqualTo("Java v680");
        assertThat(blueprint.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService.ENDPOINT);
        assertThat(blueprint.readyForEvidenceImport()).isFalse();
        assertThat(blueprint.readyForRuntimePayload()).isFalse();
        assertThat(blueprint.slotCount()).isEqualTo(25);
        assertThat(blueprint.ruleCount()).isEqualTo(18);
        assertThat(blueprint.status()).isEqualTo("passed");
    }

    @Test
    void exposesArchivePlanThroughAssuranceControllerWithoutWritingFiles() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse plan =
                controller.archivePlan();

        assertThat(plan.version()).isEqualTo("Java v682");
        assertThat(plan.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService.ENDPOINT);
        assertThat(plan.readOnly()).isTrue();
        assertThat(plan.executionAllowed()).isFalse();
        assertThat(plan.slotCount()).isEqualTo(5);
        assertThat(plan.ruleCount()).isEqualTo(18);
        assertThat(plan.status()).isEqualTo("passed");
    }

    @Test
    void exposesCloseoutThroughAssuranceControllerWithAllLocksHeld() {
        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller = controller();

        OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse closeout =
                controller.closeout();

        assertThat(closeout.version()).isEqualTo("Java v684");
        assertThat(closeout.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.ENDPOINT);
        assertThat(closeout.readyForOperatorValueSubmission()).isFalse();
        assertThat(closeout.readyForEvidenceImport()).isFalse();
        assertThat(closeout.readyForProductionExecution()).isFalse();
        assertThat(closeout.slotCount()).isEqualTo(25);
        assertThat(closeout.ruleCount()).isEqualTo(18);
        assertThat(closeout.status()).isEqualTo("passed");
    }

    private OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService(),
                new OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService()
        );
    }
}
