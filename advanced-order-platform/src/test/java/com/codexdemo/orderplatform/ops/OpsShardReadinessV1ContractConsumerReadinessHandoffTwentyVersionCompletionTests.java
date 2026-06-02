package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffTwentyVersionCompletionTests {

    @Test
    void keepsV240ThroughV259CatalogedAsTheCurrentTwentyVersionRun() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions())
                .containsSubsequence(IntStream.rangeClosed(240, 259).boxed().toArray(Integer[]::new));
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions())
                .containsExactlyElementsOf(IntStream.rangeClosed(226, 259).boxed().toList());
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .hasSize(34);
    }

    @Test
    void keepsFrozenV225HandoffSeparateFromV240ThroughV259Receipts() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.version()).isEqualTo("Java v225");
        assertThat(handoff.evidencePath()).isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .EVIDENCE_PATH);
        assertThat(handoff.handoffGuardEvidence()).doesNotContainAnyElementsOf(
                OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.evidencePaths());
        assertThat(handoff.digestEvidence()).doesNotContainAnyElementsOf(
                OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.evidencePaths());
    }

    @Test
    void keepsTwentyVersionCompletionEvidencePathVersionedToV259() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_TWENTY_VERSION_COMPLETION_EVIDENCE_PATH)
                .isEqualTo(
                        "e/259/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "twenty-version-completion-v259.json"
                );
    }
}
