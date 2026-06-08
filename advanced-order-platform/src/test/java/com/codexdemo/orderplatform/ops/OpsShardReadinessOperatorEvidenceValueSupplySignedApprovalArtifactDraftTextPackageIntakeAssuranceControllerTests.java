package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceControllerTests {

    @Test
    void exposesSourceEvidenceThroughAssuranceController() {
        var response = controller().sourceEvidence();

        assertThat(response.version()).isEqualTo("Java v925");
        assertThat(response.readyForEvidenceImport()).isFalse();
    }

    @Test
    void exposesOperatorValueHandleThroughAssuranceController() {
        var response = controller().operatorValueHandle();

        assertThat(response.version()).isEqualTo("Java v926");
        assertThat(response.readyForOperatorValueSubmission()).isFalse();
    }

    @Test
    void exposesPolicyReviewStateThroughAssuranceController() {
        var response = controller().policyReviewState();

        assertThat(response.version()).isEqualTo("Java v927");
        assertThat(response.readyForApprovalGrant()).isFalse();
    }

    @Test
    void exposesExecutionLockThroughAssuranceController() {
        var response = controller().executionLock();

        assertThat(response.version()).isEqualTo("Java v928");
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
    }

    @Test
    void exposesArchiveCloseoutThroughAssuranceController() {
        var response = controller().archiveCloseout();

        assertThat(response.version()).isEqualTo("Java v929");
        assertThat(response.fieldCount()).isEqualTo(25);
        assertThat(response.readyForDraftTextPackageReview()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSourceEvidenceService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeOperatorValueHandleService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakePolicyReviewStateService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeExecutionLockService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService()
        );
    }
}
