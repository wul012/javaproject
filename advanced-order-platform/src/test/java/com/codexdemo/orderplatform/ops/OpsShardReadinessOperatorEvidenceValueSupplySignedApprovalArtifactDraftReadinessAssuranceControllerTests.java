package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceControllerTests {

    @Test
    void exposesAssuranceRoutesWithoutOpeningExecution() {
        var controller = controller();

        assertThat(controller.evidenceSource().version()).isEqualTo("Java v775");
        assertThat(controller.redactionProvenance().version()).isEqualTo("Java v776");
        assertThat(controller.failClosedLocks().version()).isEqualTo("Java v777");
        assertThat(controller.archivePlan().version()).isEqualTo("Java v778");
        assertThat(controller.closeout().version()).isEqualTo("Java v779");
        assertThat(controller.closeout().readyForManualArtifactDraft()).isFalse();
        assertThat(controller.closeout().readyForProductionExecution()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessAssuranceController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessEvidenceSourceService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessRedactionProvenanceService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFailClosedLockService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessArchivePlanService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCloseoutService()
        );
    }
}
