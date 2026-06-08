package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentHandoffSupportTests {

    @Test
    void marksIncompleteHandoffAsBlocked() {
        var sourcePackage = new OpsShardReadinessCandidateDocumentRequestPackageService().packageCatalog();

        var response = OpsShardReadinessCandidateDocumentHandoffSupport.response(
                "Java v1102",
                OpsShardReadinessCandidateDocumentHandoffService.ENDPOINT,
                OpsShardReadinessCandidateDocumentHandoffService.PROFILE,
                sourcePackage,
                OpsShardReadinessCandidateDocumentHandoffSourceCatalog.sourceLineage(sourcePackage),
                List.of(),
                OpsShardReadinessCandidateDocumentHandoffArtifactCatalog.artifactHandles(sourcePackage),
                OpsShardReadinessCandidateDocumentHandoffPolicyCatalog.policyLocks(sourcePackage),
                OpsShardReadinessCandidateDocumentHandoffArchiveCatalog.archiveEntries(),
                OpsShardReadinessCandidateDocumentHandoffConsumerCatalog.consumerRules(),
                OpsShardReadinessCandidateDocumentHandoffGateCatalog.gates(),
                List.of("candidate-document-handoff-negative-coverage"));

        assertThat(response.status()).isEqualTo("blocked");
        assertThat(response.moduleCount()).isZero();
        assertThat(response.checks()).contains("candidate-document-handoff-module-count-0");
    }
}
