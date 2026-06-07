package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationControllerTests {

    @Test
    void exposesFoundationRoutesWithoutDraftCreation() {
        var controller = controller();

        assertThat(controller.catalog().version()).isEqualTo("Java v796");
        assertThat(controller.digestChain().version()).isEqualTo("Java v797");
        assertThat(controller.operatorWindow().version()).isEqualTo("Java v798");
        assertThat(controller.signatureStatement().version()).isEqualTo("Java v799");
        assertThat(controller.catalog().readyForManualDraft()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightFoundationController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightDigestChainService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightOperatorWindowService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftPreflightSignatureStatementService()
        );
    }
}
