package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialRequestServiceTests {

    @Test
    void buildsReadOnlyMaterialRequestFromIntakePacket() {
        var response = service().materialRequest();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1146");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.readyForMaterialRequest()).isTrue();
        assertThat(response.sourcePlan()).isEqualTo("Node v1446");
        assertThat(response.sourceNodeIntakePacketVersion()).isEqualTo("Node v1421");
        assertThat(response.sourceJavaIntakePacketVersion()).isEqualTo("Java v1142");
        assertThat(response.sourceIntakePacketEndpoint()).endsWith("candidate-document-intake-packet");
        assertThat(response.moduleCount()).isEqualTo(5);
        assertThat(response.requestItemCount()).isEqualTo(25);
        assertThat(response.passedRequestItemCount()).isEqualTo(25);
        assertThat(response.acceptanceCheckCount()).isEqualTo(25);
        assertThat(response.passedAcceptanceCheckCount()).isEqualTo(25);
        assertThat(response.sourceIntakeSlotCount()).isEqualTo(10);
        assertThat(response.sourceIntakeGuardCount()).isEqualTo(10);
        assertThat(response.requestedMaterialFieldCount()).isEqualTo(20);
        assertThat(response.artifactCount()).isEqualTo(8);
        assertThat(response.gateCount()).isEqualTo(40);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void keepsMaterialPayloadAndMutationPathsClosed() {
        var response = service().materialRequest();

        assertThat(response.realDocumentCount()).isZero();
        assertThat(response.syntheticDocumentCount()).isZero();
        assertThat(response.stagedDocumentCount()).isZero();
        assertThat(response.importedDocumentCount()).isZero();
        assertThat(response.evaluatedDocumentCount()).isZero();
        assertThat(response.acceptedDocumentCount()).isZero();
        assertThat(response.rejectedDocumentCount()).isZero();
        assertThat(response.payloadCount()).isZero();
        assertThat(response.materialAccepted()).isFalse();
        assertThat(response.importAllowed()).isFalse();
        assertThat(response.evaluationAllowed()).isFalse();
        assertThat(response.approvalGrantAllowed()).isFalse();
        assertThat(response.signedApprovalCaptureAllowed()).isFalse();
        assertThat(response.runtimePayloadAllowed()).isFalse();
        assertThat(response.writeAllowed()).isFalse();
        assertThat(response.siblingMutationAllowed()).isFalse();
    }

    private OpsShardReadinessCandidateDocumentMaterialRequestService service() {
        var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
        var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
        var precheckService = new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
                requestPackageService,
                handoffService);
        var intakePacketService = new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
        return new OpsShardReadinessCandidateDocumentMaterialRequestService(intakePacketService);
    }
}
