package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationControllerTests {

    @Test
    void exposesFoundationRoutesWithoutManualDraftCreation() {
        var controller = controller();

        assertThat(controller.catalog().version()).isEqualTo("Java v821");
        assertThat(controller.digestPins().version()).isEqualTo("Java v822");
        assertThat(controller.operatorReview().version()).isEqualTo("Java v823");
        assertThat(controller.signatureReview().version()).isEqualTo("Java v824");
        assertThat(controller.catalog().readyForManualDraft()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneDigestPinService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneOperatorReviewService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSignatureReviewService()
        );
    }
}
