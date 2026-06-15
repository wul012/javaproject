package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialRequestService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRequestPackageService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffControllerTests {

  @Test
  void handoffRouteExposesReadOnlyArchiveHandoff() {
    assertThat(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_MATERIAL_SUBMISSION_PRECHECK_HANDOFF)
        .isEqualTo("/candidate-document-material-submission-precheck-handoff");

    var response =
        new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffController(service())
            .handoff();

    assertThat(response.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/candidate-document-material-submission-precheck-handoff");
    assertThat(response.profile())
        .isEqualTo(
            "java-shard-readiness-candidate-document-material-submission-precheck-handoff.v1");
    assertThat(response.version()).isEqualTo("Java v1187");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.handoffState())
        .isEqualTo(
            "archived-read-only-material-submission-precheck-waiting-for-reviewed-real-material");
  }

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService service() {
    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService(
        sourcePrecheckService());
  }

  private OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService
      sourcePrecheckService() {
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
