package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialRequestControllerTests {

    @Test
    void materialRequestRouteExposesReadOnlyRequestPackage() {
        assertThat(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_REQUEST)
                .isEqualTo("/candidate-document-material-request");

        var controller = new OpsShardReadinessCandidateDocumentMaterialRequestController(service());
        var response = controller.materialRequest();

        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/candidate-document-material-request");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-candidate-document-material-request.v1");
        assertThat(response.version()).isEqualTo("Java v1152");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.artifacts())
                .extracting(OpsShardReadinessCandidateDocumentMaterialRequestResponse.Artifact::reference)
                .contains("e/1152/routes/candidate-document-material-request-route.json");
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
