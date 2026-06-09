package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckControllerTests {

    @Test
    void materialSubmissionPrecheckRouteExposesReadOnlyPrecheck() {
        assertThat(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK)
                .isEqualTo("/candidate-document-material-submission-precheck");

        var controller = new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckController(service());
        var response = controller.materialSubmissionPrecheck();

        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/candidate-document-material-submission-precheck");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-candidate-document-material-submission-precheck.v1");
        assertThat(response.version()).isEqualTo("Java v1162");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.artifacts())
                .extracting(OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact::reference)
                .contains("e/1162/routes/candidate-document-material-submission-precheck-route.json");
    }

    private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService service() {
        return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(materialRequestService());
    }

    private OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService() {
        var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
        var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
        var precheckService = new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
                requestPackageService,
                handoffService);
        var intakePacketService = new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
        return new OpsShardReadinessCandidateDocumentMaterialRequestService(intakePacketService);
    }
}
