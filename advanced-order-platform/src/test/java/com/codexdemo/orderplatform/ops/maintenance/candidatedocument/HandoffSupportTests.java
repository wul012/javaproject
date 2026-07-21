package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class HandoffSupportTests {

  @Test
  void marksIncompleteHandoffAsBlocked() {
    var sourcePackage =
        new OpsShardReadinessCandidateDocumentRequestPackageService().packageCatalog();
    var evidence = HandoffCatalog.from(sourcePackage);

    var response =
        OpsShardReadinessCandidateDocumentHandoffSupport.response(
            "Java v1102",
            OpsShardReadinessCandidateDocumentHandoffService.ENDPOINT,
            OpsShardReadinessCandidateDocumentHandoffService.PROFILE,
            sourcePackage,
            evidence.sourceLineage(),
            List.of(),
            evidence.artifactHandles(),
            evidence.policyLocks(),
            evidence.archiveEntries(),
            evidence.consumerRules(),
            evidence.gates(),
            List.of("candidate-document-handoff-negative-coverage"));

    assertThat(response.status()).isEqualTo("blocked");
    assertThat(response.moduleCount()).isZero();
    assertThat(response.checks()).contains("candidate-document-handoff-module-count-0");
  }
}
