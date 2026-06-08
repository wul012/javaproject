package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightFoundationServiceTests {

    @Test
    void exposesCatalogWithoutInstructionArtifactCreation() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightCatalogService()
                        .catalog();

        assertThat(response.version()).isEqualTo("Java v896");
        assertThat(response.sourcePlan()).isEqualTo("Node v1211");
        assertThat(response.sourceNodeAuthoringReadinessVersion()).isEqualTo("Node v1186");
        assertThat(response.sourceJavaAuthoringReadinessVersion()).isEqualTo("Java v884");
        assertThat(response.readyForInstructionPreflight()).isTrue();
        assertThat(response.readyForDraftTextPackage()).isFalse();
        assertThat(response.readyForSignedDraftText()).isFalse();
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.slotCount()).isEqualTo(25);
        assertThat(response.guardCount()).isEqualTo(25);
        assertThat(response.gateCount()).isEqualTo(20);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void exposesDigestInstructionsWithoutTextGeneration() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightDigestInstructionService()
                        .digestInstructions();

        assertThat(response.version()).isEqualTo("Java v897");
        assertThat(response.instructionPreflightState()).isEqualTo("slot-map-only");
        assertThat(response.instructionArtifactState()).isEqualTo("not-created");
        assertThat(response.slotCount()).isEqualTo(4);
        assertThat(response.guardCount()).isEqualTo(4);
        assertThat(response.gateCount()).isEqualTo(2);
    }

    @Test
    void exposesOperatorInstructionsWithoutApprovalCapture() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightOperatorInstructionService()
                        .operatorInstructions();

        assertThat(response.version()).isEqualTo("Java v898");
        assertThat(response.readyForApprovalGrant()).isFalse();
        assertThat(response.readyForRuntimePayload()).isFalse();
        assertThat(response.slotCount()).isEqualTo(4);
        assertThat(response.guardCount()).isEqualTo(4);
    }

    @Test
    void exposesSignatureInstructionsWithoutSignaturePayload() {
        var response =
                new OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftInstructionPreflightSignatureInstructionService()
                        .signatureInstructions();

        assertThat(response.version()).isEqualTo("Java v899");
        assertThat(response.readyForSignatureCapture()).isFalse();
        assertThat(response.readyForSignedDraftText()).isFalse();
        assertThat(response.slotCount()).isEqualTo(5);
        assertThat(response.guardCount()).isEqualTo(5);
    }
}
