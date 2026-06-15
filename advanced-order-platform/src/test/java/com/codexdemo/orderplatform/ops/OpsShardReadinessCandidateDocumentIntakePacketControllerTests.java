package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketResponse;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentIntakePacketService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentRequestPackageService;
import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentSubmissionPrecheckService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentIntakePacketControllerTests {

  @Test
  void intakePacketRouteExposesReadOnlyPacket() {
    assertThat(OpsShardReadinessRoutePaths.CANDIDATE_DOCUMENT_INTAKE_PACKET)
        .isEqualTo("/candidate-document-intake-packet");

    var controller = new OpsShardReadinessCandidateDocumentIntakePacketController(service());
    var response = controller.intakePacket();

    assertThat(response.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/candidate-document-intake-packet");
    assertThat(response.profile())
        .isEqualTo("java-shard-readiness-candidate-document-intake-packet.v1");
    assertThat(response.version()).isEqualTo("Java v1142");
    assertThat(response.readOnly()).isTrue();
    assertThat(response.executionAllowed()).isFalse();
    assertThat(response.artifacts())
        .extracting(OpsShardReadinessCandidateDocumentIntakePacketResponse.Artifact::reference)
        .contains("e/1142/routes/candidate-document-intake-packet-route.json");
  }

  private OpsShardReadinessCandidateDocumentIntakePacketService service() {
    var requestPackageService = new OpsShardReadinessCandidateDocumentRequestPackageService();
    var handoffService =
        new OpsShardReadinessCandidateDocumentHandoffService(requestPackageService);
    var precheckService =
        new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
            requestPackageService, handoffService);
    return new OpsShardReadinessCandidateDocumentIntakePacketService(precheckService);
  }
}
