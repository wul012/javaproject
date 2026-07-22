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

class CoreCatalogResponseOracleTests {

  private static final ObjectMapper JSON =
      JsonMapper.builder()
          .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
          .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
          .build();

  @Test
  void submissionOutputIsFrozen() throws Exception {
    var response = services().submission().precheck();

    assertThat(
            List.of(
                response.checkpoints().size(),
                response.validators().size(),
                response.artifacts().size(),
                response.gates().size(),
                response.checks().size()))
        .containsExactly(25, 25, 8, 40, 19);
    assertThat(sha256(response))
        .isEqualTo("920742a06cdbe7f0502abeb4c4b38d2f772088677aabdc5a2eb594f2bc0ce0fa");
  }

  @Test
  void intakeOutputIsFrozen() throws Exception {
    var response = services().intake().intakePacket();

    assertThat(
            List.of(
                response.sourceLineage().size(),
                response.modules().size(),
                response.intakeSlots().size(),
                response.intakeGuards().size(),
                response.artifacts().size(),
                response.gates().size(),
                response.checks().size()))
        .containsExactly(5, 5, 10, 10, 8, 35, 23);
    assertThat(sha256(response))
        .isEqualTo("cb0b888fcc190b1272834cabf7c1bb414471d486da55212cc562cdd6af4c4e95");
  }

  @Test
  void profileOutputIsFrozen() throws Exception {
    var response = services().profile().registry();

    assertThat(
            List.of(
                response.modules().size(),
                response.sources().size(),
                response.sections().size(),
                response.fieldEntries().size(),
                response.renderedSections().size(),
                response.routeFieldLocks().size(),
                response.gates().size(),
                response.checks().size()))
        .containsExactly(5, 5, 5, 25, 5, 5, 43, 21);
    assertThat(sha256(response))
        .isEqualTo("d3cbe7af21f604737121aa8a5e4d9e05f5dd9ed3e1c7013ec2757b8d60dbc660");
  }

  private static Services services() {
    var request = new OpsShardReadinessCandidateDocumentRequestPackageService();
    var handoff = new OpsShardReadinessCandidateDocumentHandoffService(request);
    var submission =
        new OpsShardReadinessCandidateDocumentSubmissionPrecheckService(request, handoff);
    var intake = new OpsShardReadinessCandidateDocumentIntakePacketService(submission);
    var material = new OpsShardReadinessCandidateDocumentMaterialRequestService(intake);
    var materialPrecheck =
        new OpsShardReadinessCandidateDocumentMaterialSubmissionPrecheckService(material);
    var profile =
        new OpsShardReadinessCandidateDocumentProfileSectionRegistryService(
            request, submission, intake, material, materialPrecheck);
    return new Services(submission, intake, profile);
  }

  private static String sha256(Object response) throws Exception {
    byte[] json = JSON.writeValueAsString(response).getBytes(StandardCharsets.UTF_8);
    return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(json));
  }

  private record Services(
      OpsShardReadinessCandidateDocumentSubmissionPrecheckService submission,
      OpsShardReadinessCandidateDocumentIntakePacketService intake,
      OpsShardReadinessCandidateDocumentProfileSectionRegistryService profile) {}
}
