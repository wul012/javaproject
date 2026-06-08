package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceControllerTests {

    @Test
    void exposesAssuranceReviewPreflightRoutes() {
        var controller = controller();

        assertThat(controller.sourceEvidence().version()).isEqualTo("Java v950");
        assertThat(controller.operatorValueHandle().version()).isEqualTo("Java v951");
        assertThat(controller.policyReviewState().version()).isEqualTo("Java v952");
        assertThat(controller.executionLockControls().version()).isEqualTo("Java v953");
        assertThat(controller.archiveCloseout().version()).isEqualTo("Java v954");
        assertThat(controller.archiveCloseout().readyForDraftTextPackageAcceptance()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightSourceEvidenceService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightOperatorValueHandleService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightPolicyReviewStateService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightExecutionLockControlsService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageReviewPreflightArchiveCloseoutService()
        );
    }
}
