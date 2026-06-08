package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceControllerTests {

    @Test
    void exposesEvidenceInstructionsThroughAssuranceController() {
        var response = controller().evidenceInstructions();

        assertThat(response.version()).isEqualTo("Java v900");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService
                        .ENDPOINT);
        assertThat(response.readyForEvidenceImport()).isFalse();
    }

    @Test
    void exposesValuePolicyInstructionsThroughAssuranceController() {
        var response = controller().valuePolicyInstructions();

        assertThat(response.version()).isEqualTo("Java v901");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService
                        .ENDPOINT);
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
    }

    @Test
    void exposesEmbargoInstructionsThroughAssuranceController() {
        var response = controller().embargoInstructions();

        assertThat(response.version()).isEqualTo("Java v902");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService
                        .ENDPOINT);
        assertThat(response.siblingMutationAllowed()).isFalse();
    }

    @Test
    void exposesDraftTextLockThroughAssuranceController() {
        var response = controller().draftTextLock();

        assertThat(response.version()).isEqualTo("Java v903");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService
                        .ENDPOINT);
        assertThat(response.readyForSignedDraftText()).isFalse();
    }

    @Test
    void exposesCloseoutThroughAssuranceController() {
        var response = controller().closeout();

        assertThat(response.version()).isEqualTo("Java v904");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService
                        .ENDPOINT);
        assertThat(response.slotCount()).isEqualTo(25);
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEvidenceInstructionService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightValuePolicyInstructionService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightEmbargoInstructionService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDraftTextLockService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCloseoutService()
        );
    }
}
