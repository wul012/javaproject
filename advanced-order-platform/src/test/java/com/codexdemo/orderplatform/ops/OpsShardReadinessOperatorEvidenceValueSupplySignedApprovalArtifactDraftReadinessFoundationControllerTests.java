package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationControllerTests {

    @Test
    void exposesFoundationRoutesWithoutDraftCreation() {
        var controller = controller();

        assertThat(controller.catalog().version()).isEqualTo("Java v771");
        assertThat(controller.digestChain().version()).isEqualTo("Java v772");
        assertThat(controller.operatorWindow().version()).isEqualTo("Java v773");
        assertThat(controller.signatureStatement().version()).isEqualTo("Java v774");
        assertThat(controller.catalog().readyForManualArtifactDraft()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessFoundationController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessDigestChainService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessOperatorWindowService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessSignatureStatementService()
        );
    }
}
