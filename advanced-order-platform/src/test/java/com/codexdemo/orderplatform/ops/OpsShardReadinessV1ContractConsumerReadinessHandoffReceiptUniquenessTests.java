package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReceiptUniquenessTests {

    @Test
    void keepsConsumerReadinessChainReceiptsUnique() {
        List<String> receiptIds = List.of(
                OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle().receiptId(),
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist().receiptId(),
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest().receiptId(),
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff().receiptId()
        );

        assertThat(receiptIds).doesNotHaveDuplicates();
        assertThat(receiptIds)
                .allSatisfy(receiptId -> assertThat(receiptId)
                        .startsWith("java-shard-readiness-")
                        .contains("-receipt-v"));
    }

    @Test
    void keepsConsumerReadinessChainEvidencePathsUnique() {
        List<String> evidencePaths = List.of(
                OpsShardReadinessV1ContractConsumerHandoffBundleSnapshot.v211Bundle().evidencePath(),
                OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist().evidencePath(),
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest().evidencePath(),
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff().evidencePath()
        );

        assertThat(evidencePaths)
                .doesNotHaveDuplicates()
                .containsExactly(
                        "e/211/evidence/java-shard-readiness-v1-contract-consumer-handoff-bundle-v211.json",
                        "e/215/evidence/java-shard-readiness-v1-contract-consumer-verification-checklist-v215.json",
                        "e/220/evidence/java-shard-readiness-v1-contract-consumer-evidence-digest-v220.json",
                        "e/225/evidence/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.json"
                );
    }

    @Test
    void keepsReadinessHandoffReceiptAndEvidencePathAligned() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-readiness-handoff-receipt-v225");
        assertThat(handoff.evidencePath())
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.EVIDENCE_PATH);
    }
}
