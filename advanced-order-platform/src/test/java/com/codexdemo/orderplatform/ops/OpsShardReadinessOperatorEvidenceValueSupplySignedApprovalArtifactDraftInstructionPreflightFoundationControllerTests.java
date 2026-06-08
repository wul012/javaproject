package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightFoundationControllerTests {

    @Test
    void exposesCatalogThroughFoundationController() {
        var response = controller().catalog();

        assertThat(response.version()).isEqualTo("Java v896");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService
                        .ENDPOINT);
        assertThat(response.slotCount()).isEqualTo(25);
        assertThat(response.guardCount()).isEqualTo(25);
    }

    @Test
    void exposesDigestInstructionsThroughFoundationController() {
        var response = controller().digestInstructions();

        assertThat(response.version()).isEqualTo("Java v897");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService
                        .ENDPOINT);
        assertThat(response.readyForSignedDraftText()).isFalse();
    }

    @Test
    void exposesOperatorInstructionsThroughFoundationController() {
        var response = controller().operatorInstructions();

        assertThat(response.version()).isEqualTo("Java v898");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightOperatorInstructionService
                        .ENDPOINT);
        assertThat(response.readyForApprovalGrant()).isFalse();
    }

    @Test
    void exposesSignatureInstructionsThroughFoundationController() {
        var response = controller().signatureInstructions();

        assertThat(response.version()).isEqualTo("Java v899");
        assertThat(response.endpoint()).isEqualTo(
                OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService
                        .ENDPOINT);
        assertThat(response.readyForSignatureCapture()).isFalse();
    }

    private OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightFoundationController
    controller() {
        return new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightFoundationController(
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightOperatorInstructionService(),
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService()
        );
    }
}
