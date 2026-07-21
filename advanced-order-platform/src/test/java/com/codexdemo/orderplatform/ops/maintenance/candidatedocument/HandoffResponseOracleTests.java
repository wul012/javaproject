package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.List;
import org.junit.jupiter.api.Test;

class HandoffResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void requestHandoffOutputIsFrozen() throws Exception {
    var response = requestHandoff();

    assertThat(
            List.of(
                response.sourceLineage().size(),
                response.modules().size(),
                response.artifactHandles().size(),
                response.policyLocks().size(),
                response.archiveEntries().size(),
                response.consumerRules().size(),
                response.gates().size(),
                response.checks().size()))
        .containsExactly(6, 5, 15, 15, 8, 10, 25, 20);
    assertThat(sha256(response))
        .isEqualTo("3c988b527fcf1b53946d9cab7ea91866609b2424ce981c87ad3fef8b849e13c2");
  }

  @Test
  void precheckHandoffOutputIsFrozen() throws Exception {
    var response = precheckHandoff();

    assertThat(
            List.of(
                response.sourceLineage().size(),
                response.modules().size(),
                response.archiveHandles().size(),
                response.policyLocks().size(),
                response.artifactReferences().size(),
                response.consumerRules().size(),
                response.gates().size(),
                response.checks().size()))
        .containsExactly(6, 5, 10, 10, 8, 10, 42, 26);
    assertThat(sha256(response))
        .isEqualTo("91473893363f7062af79e05237e1b43407f73bd14176efcfe844fc0331f21cf5");
  }

  private static OpsShardReadinessCandidateDocumentHandoffResponse requestHandoff() {
    return new OpsShardReadinessCandidateDocumentHandoffService(
            new OpsShardReadinessCandidateDocumentRequestPackageService())
        .handoff();
  }

  private static OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffResponse
      precheckHandoff() {
    var requestService = new OpsShardReadinessCandidateDocumentRequestPackageService();
    var handoffService = new OpsShardReadinessCandidateDocumentHandoffService(requestService);
    var submissionService =
        new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(
            requestService, handoffService);
    var intakeService =
        new OpsShardReadinessCandidateDocumentIntakePacketService(submissionService);
    var materialService =
        new OpsShardReadinessCandidateDocumentMaterialRequestService(intakeService);
    return new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckHandoffService(
            new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(
                materialService))
        .handoff();
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }
}
