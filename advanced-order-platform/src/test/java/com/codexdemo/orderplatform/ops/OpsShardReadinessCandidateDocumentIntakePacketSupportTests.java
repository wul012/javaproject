package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentIntakePacketSupportTests {

    @Test
    void marksIncompleteIntakePacketAsBlocked() {
        var sourcePrecheck = sourcePrecheck();

        var response = OpsShardReadinessCandidateDocumentIntakePacketSupport.response(
                "Java v1135",
                OpsShardReadinessCandidateDocumentIntakePacketService.ENDPOINT,
                OpsShardReadinessCandidateDocumentIntakePacketService.PROFILE,
                sourcePrecheck,
                OpsShardReadinessCandidateDocumentIntakePacketSourceCatalog.sourceLineage(sourcePrecheck),
                OpsShardReadinessCandidateDocumentIntakePacketModuleCatalog.modules(),
                List.of(),
                List.of(),
                OpsShardReadinessCandidateDocumentIntakePacketArtifactCatalog.artifacts(),
                OpsShardReadinessCandidateDocumentIntakePacketArtifactCatalog.gates(),
                List.of("candidate-document-intake-packet-negative-coverage"));

        assertThat(response.status()).isEqualTo("blocked");
        assertThat(response.intakeSlotCount()).isZero();
        assertThat(response.intakeGuardCount()).isZero();
        assertThat(response.checks()).contains("candidate-document-intake-packet-slot-count-0");
    }

    private OpsShardReadinessCandidateDocumentSubmissionPrecheckResponse sourcePrecheck() {
        var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
        var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
        return new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
                requestPackageService,
                handoffService)
                .precheck();
    }
}
