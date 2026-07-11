package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffArchiveQualityCloseoutTests {

  @Test
  void keepsV260ThroughV269ArchiveQualityReceiptsCatalogedInOrder() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .versions())
        .containsSubsequence(IntStream.rangeClosed(260, 269).boxed().toArray(Integer[]::new));
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .receipts())
        .extracting(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
                ::scope)
        .containsSubsequence(
            "evidence scope summary",
            "boundary field completeness",
            "archive slug parity",
            "explanation archive completeness",
            "browser snapshot completeness",
            "screenshot artifact completeness",
            "html archive version alignment",
            "json guard completeness",
            "json metadata completeness",
            "archive quality closeout");
  }

  @Test
  void keepsArchiveQualityCloseoutPathVersionedToV269() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_ARCHIVE_QUALITY_CLOSEOUT_EVIDENCE_PATH)
        .isEqualTo(
            "e/269/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "archive-quality-closeout-v269.json");
  }
}
