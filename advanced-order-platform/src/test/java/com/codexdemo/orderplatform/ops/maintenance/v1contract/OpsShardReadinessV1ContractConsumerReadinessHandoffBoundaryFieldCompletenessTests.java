package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffBoundaryFieldCompletenessTests {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  private static final List<String> FALSE_BOUNDARY_FIELDS =
      List.of(
          "writeRoutingAllowed",
          "activeShardRouterAllowed",
          "credentialValueRead",
          "rawEndpointParsed",
          "managedAuditConnectionAllowed",
          "deploymentOrRollbackAllowed",
          "nodeMayStartOrStopJavaOrMiniKv");

  @Test
  void keepsEveryCatalogEvidenceJsonBoundaryCompleteAndFalse() throws IOException {
    Path root = Path.of("").toAbsolutePath();

    for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
        receipt :
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .receipts()) {
      JsonNode boundary =
          OBJECT_MAPPER.readTree(root.resolve(receipt.evidencePath()).toFile()).path("boundary");

      assertThat(boundary.isObject()).as(receipt.evidencePath()).isTrue();
      FALSE_BOUNDARY_FIELDS.forEach(
          field ->
              assertThat(boundary.path(field).asBoolean(true))
                  .as(receipt.evidencePath() + " -> " + field)
                  .isFalse());
    }
  }

  @Test
  void keepsBoundaryFieldCompletenessPathVersionedToV261() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_BOUNDARY_FIELD_COMPLETENESS_EVIDENCE_PATH)
        .isEqualTo(
            "e/261/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "boundary-field-completeness-v261.json");
  }
}
