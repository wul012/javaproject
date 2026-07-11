package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffAuditabilityCloseoutTests {

  @Test
  void keepsV275ThroughV279AuditabilityScopesCatalogedInOrder() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .versions())
        .containsSubsequence(275, 276, 277, 278, 279);
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                .receipts())
        .extracting(
            OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.Receipt
                ::scope)
        .containsSubsequence(
            "validation artifact depth",
            "readme description alignment",
            "walkthrough filename alignment",
            "evidence path structure stability",
            "auditability closeout");
  }

  @Test
  void keepsAuditabilityCloseoutPathVersionedToV279() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_AUDITABILITY_CLOSEOUT_EVIDENCE_PATH)
        .isEqualTo(
            "e/279/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "auditability-closeout-v279.json");
  }
}
