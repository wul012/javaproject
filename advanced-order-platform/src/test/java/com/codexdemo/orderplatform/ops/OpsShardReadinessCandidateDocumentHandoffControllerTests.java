package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentHandoffControllerTests {

    @Test
    void handoffRouteUsesShortReadOnlyEndpoint() {
        assertThat(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_REQUEST_PACKAGE_HANDOFF)
                .isEqualTo("/candidate-document-request-package-handoff");

        var controller = new OpsShardReadinessCandidateDocumentHandoffController(service());
        var response = controller.handoff();

        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/candidate-document-request-package-handoff");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-candidate-document-request-package-handoff.v1");
        assertThat(response.readOnly()).isTrue();
        assertThat(response.executionAllowed()).isFalse();
    }

    private OpsShardReadinessCandidateDocumentHandoffService service() {
        return new OpsShardReadinessCandidateDocumentHandoffService(
                new OpsShardReadinessCandidateDocumentRequestPackageService());
    }
}
