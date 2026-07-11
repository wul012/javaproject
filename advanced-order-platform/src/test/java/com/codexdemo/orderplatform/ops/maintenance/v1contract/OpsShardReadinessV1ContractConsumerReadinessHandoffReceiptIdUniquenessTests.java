package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptIdUniquenessTests {

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  @Test
  void keepsCatalogEvidenceReceiptIdsUniqueAndVersioned() throws IOException {
    Path root = Path.of("").toAbsolutePath();
    List<String> receiptIds = new ArrayList<>();

    for (OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
        receipt :
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .receipts()) {
      String receiptId =
          OBJECT_MAPPER
              .readTree(root.resolve(receipt.evidencePath()).toFile())
              .path("receiptId")
              .asText();

      assertThat(receiptId).as(receipt.evidencePath()).endsWith("-v" + receipt.version());
      receiptIds.add(receiptId);
    }

    assertThat(receiptIds).doesNotHaveDuplicates();
  }

  @Test
  void keepsReceiptIdUniquenessEvidencePathVersionedToV256() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_RECEIPT_ID_UNIQUENESS_EVIDENCE_PATH)
        .isEqualTo(
            "e/256/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "receipt-id-uniqueness-v256.json");
  }
}
