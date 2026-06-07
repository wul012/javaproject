package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceControllerTests {

    @Test
    void exposesAssuranceRoutesWithoutOpeningExecution() {
        var controller = controller();

        assertThat(controller.evidencePackage().version()).isEqualTo("Java v850");
        assertThat(controller.valuePolicyPackage().version()).isEqualTo("Java v851");
        assertThat(controller.embargoPackage().version()).isEqualTo("Java v852");
        assertThat(controller.draftAuthoringGate().version()).isEqualTo("Java v853");
        assertThat(controller.closeout().version()).isEqualTo("Java v854");
        assertThat(controller.closeout().readyForHumanDraftAuthoring()).isFalse();
        assertThat(controller.closeout().readyForRuntimePayload()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEvidencePackageService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightValuePolicyPackageService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightEmbargoPackageService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightDraftAuthoringGateService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReviewPackagePreflightCloseoutService()
        );
    }
}
