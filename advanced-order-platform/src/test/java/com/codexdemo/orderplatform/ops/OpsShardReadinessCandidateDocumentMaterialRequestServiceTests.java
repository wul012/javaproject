package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialRequestServiceTests {

    @Test
    void buildsReadOnlyMaterialRequestFromIntakePacket() {
        var response = service().materialRequest();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1152");
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

    @Test
    void requestItemsAndAcceptanceChecksCoverSlotsGuardsAndExternalMaterial() {
        var response = service().materialRequest();

        assertThat(response.requestItems())
                .extracting(OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem::category)
                .contains("intake-slot-material", "guard-attestation", "external-material");
        assertThat(response.requestItems())
                .extracting(OpsShardReadinessCandidateDocumentMaterialRequestResponse.RequestItem::code)
                .contains(
                        "material-slot-candidate-intake-slot-1",
                        "material-guard-candidate-intake-slot-1",
                        "reviewer-identity-request",
                        "source-uri-digest-request",
                        "redaction-archive-closeout-request");
        assertThat(response.acceptanceChecks())
                .allSatisfy(check -> {
                    assertThat(check.code()).endsWith("-acceptance-check");
                    assertThat(check.rejectionCode()).startsWith("reject-material-request-");
                    assertThat(check.enforcement()).isEqualTo("fail-closed");
                    assertThat(check.status()).isEqualTo("passed");
                });
    }

    @Test
    void artifactsGatesAndChecksRemainEvidenceOnly() {
        var response = service().materialRequest();

        assertThat(response.artifacts())
                .extracting(OpsShardReadinessCandidateDocumentMaterialRequestResponse.Artifact::reference)
                .allSatisfy(reference -> assertThat(reference).startsWith("e/1152/"));
        assertThat(response.gates())
                .hasSize(40)
                .first()
                .isEqualTo("candidate-document-material-request-no-material-gate-1");
        assertThat(response.checks())
                .contains(
                        "candidate-document-material-request-source-plan-Node v1446",
                        "candidate-document-material-request-source-java-intake-packet-Java v1142",
                        "candidate-document-material-request-item-count-25",
                        "candidate-document-material-request-acceptance-check-count-25",
                        "candidate-document-material-request-no-material-accepted",
                        "candidate-document-material-request-import-disabled",
                        "candidate-document-material-request-sibling-mutation-disabled",
                        "candidate-document-material-request-service-assembled-from-intake-packet");
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
