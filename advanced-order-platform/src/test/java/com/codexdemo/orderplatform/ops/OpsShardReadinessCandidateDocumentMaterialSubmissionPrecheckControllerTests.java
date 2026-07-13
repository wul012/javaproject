package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialRequestService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRequestPackageService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckControllerTests {

  @Test
  void materialSubmissionPrecheckRouteExposesReadOnlyPrecheck() {
    assertThat(
            OpsShardReadinessCandidateDocumentRoutePaths
                .CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK)
        .isEqualTo("/candidate-document-material-submission-precheck");

    var controller =
        new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckController(service());
    var response = controller.materialSubmissionPrecheck();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/candidate-document-material-submission-precheck");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-candidate-document-material-submission-precheck.v1");
    assertThat(response.version()).isEqualTo("Java v1162");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.artifacts())
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact
                ::reference)
        .contains("e/1162/routes/candidate-document-material-submission-precheck-route.json");
  }

  @Test
  void materialSubmissionPrecheckRouteCarriesCloseoutEvidence() {
    var response =
        new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckController(service())
            .materialSubmissionPrecheck();

    assertThat(response.materialSubmissionPrecheckState())
        .isEqualTo("waiting-for-reviewed-real-candidate-document-material-submission");
    assertThat(response.checks())
        .contains(
            "candidate-document-material-submission-precheck-source-route-"
                + "/api/v1/ops/shard-readiness/candidate-document-material-request",
            "candidate-document-material-submission-precheck-gate-count-41",
            "candidate-document-material-submission-precheck-material-submission-disabled",
            "candidate-document-material-submission-precheck-write-disabled");
    assertThat(response.artifacts())
        .extracting(
            OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckResponse.Artifact
                ::reference)
        .contains(
            "e/1162/routes/candidate-document-material-submission-precheck-route.json",
            "e/1162/closeout/candidate-document-material-submission-precheck-closeout.md");
  }

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService service() {
    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(
        materialRequestService());
  }

  private OpsShardReadinessCandidateDocumentMaterialRequestService materialRequestService() {
    var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
    var handoffService =
        new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
    var precheckService =
        new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
            requestPackageService, handoffService);
    var intakePacketService =
        new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
    return new OpsShardReadinessCandidateDocumentMaterialRequestService(intakePacketService);
  }
}
