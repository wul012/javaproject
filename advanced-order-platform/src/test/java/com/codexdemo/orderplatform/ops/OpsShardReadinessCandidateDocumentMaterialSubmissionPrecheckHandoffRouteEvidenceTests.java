package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffRouteEvidenceTests {

  @Test
  void controllerRouteCarriesClosedHandoffEvidence() {
    var response =
        new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffController(
                OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffTestSupport
                    .handoffService())
            .handoff();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/candidate-document-material-submission-precheck-handoff");
    assertThat(response.checks())
        .contains(
            "candidate-document-material-submission-precheck-handoff-source-route-"
                + "/api/v1/ops/shard-readiness/candidate-document-material-submission-precheck",
            "candidate-document-material-submission-precheck-handoff-gate-count-42",
            "candidate-document-material-submission-precheck-handoff-write-disabled",
            "candidate-document-material-submission-precheck-handoff-sibling-mutation-disabled");
    assertThat(response.archiveHandles())
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
                    .ArchiveHandle
                ::reference)
        .contains("e/1187/archive/closeout-archive-submission-checkpoint.json");
  }
}
