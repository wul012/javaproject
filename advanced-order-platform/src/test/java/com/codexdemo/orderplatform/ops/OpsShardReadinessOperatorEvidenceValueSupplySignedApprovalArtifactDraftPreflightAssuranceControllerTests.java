package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceControllerTests {

    @Test
    void exposesAssuranceRoutesWithoutOpeningExecution() {
        var controller = controller();

        assertThat(controller.evidenceSource().version()).isEqualTo("Java v800");
        assertThat(controller.redactionProvenance().version()).isEqualTo("Java v801");
        assertThat(controller.failClosedLocks().version()).isEqualTo("Java v802");
        assertThat(controller.archivePlan().version()).isEqualTo("Java v803");
        assertThat(controller.closeout().version()).isEqualTo("Java v804");
        assertThat(controller.closeout().readyForManualDraft()).isFalse();
        assertThat(controller.closeout().readyForRuntimePayload()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightEvidenceSourceService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightRedactionProvenanceService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFailClosedLockService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightArchivePlanService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCloseoutService()
        );
    }
}
