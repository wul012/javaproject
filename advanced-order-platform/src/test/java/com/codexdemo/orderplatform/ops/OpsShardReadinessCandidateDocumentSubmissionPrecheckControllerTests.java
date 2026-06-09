package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentSubmissionPrecheckControllerTests {

    @Test
    void precheckRouteExposesReadOnlyRouteEvidence() {
        assertThat(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_SUBMISSION_PRECHECK)
                .isEqualTo("/candidate-document-submission-precheck");

        var controller = new OpsShardReadinessCandidateDocumentSubmissionPrecheckController(service());
        var response = controller.precheck();

        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/candidate-document-submission-precheck");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-candidate-document-submission-precheck.v1");
        assertThat(response.version()).isEqualTo("Java v1117");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
        assertThat(response.artifacts())
                .extracting(OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse.Artifact::reference)
                .contains("e/1117/routes/candidate-document-submission-precheck-route.json");
        assertThat(response.checks())
                .contains(
                        "candidate-document-submission-precheck-source-plan-Node v1411",
                        "candidate-document-submission-precheck-import-disabled",
                        "candidate-document-submission-precheck-sibling-mutation-disabled",
                        "candidate-document-submission-precheck-service-assembled-from-request-package-and-handoff");
    }

    private OpsShardReadinessCandidateDocumentSubmissionPrecheckService service() {
        var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
        var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
        return new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(requestPackageService, handoffService);
    }
}
