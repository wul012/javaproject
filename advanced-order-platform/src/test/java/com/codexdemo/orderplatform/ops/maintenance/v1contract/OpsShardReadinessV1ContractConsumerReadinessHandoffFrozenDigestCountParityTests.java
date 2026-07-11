package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffFrozenDigestCountParityTests {

  @Test
  void keepsFrozenV225HandoffDigestCountsAlignedWithFrozenV220Digest() {
    OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
        OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.digestEvidenceCount()).isEqualTo(digest.digestEvidence().size());
    assertThat(handoff.digestCheckCount()).isEqualTo(digest.digestChecks().size());
    assertThat(handoff.digestEvidence()).containsExactlyElementsOf(digest.digestEvidence());
    assertThat(handoff.handoffChecks())
        .contains("digest-evidence-count:" + digest.digestEvidence().size())
        .contains("digest-check-count:" + digest.digestChecks().size());
  }

  @Test
  void keepsFrozenDigestCountParityPathVersionedToV282() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_FROZEN_DIGEST_COUNT_PARITY_EVIDENCE_PATH)
        .isEqualTo(
            "e/282/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "frozen-digest-count-parity-v282.json");
  }
}
