package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogCompletionReadinessTests {

    @Test
    void keepsThirdGroupQualityReceiptsCatalogedThroughV258() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions())
                .containsSubsequence(254, 255, 256, 257, 258);
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .extracting(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                        .Receipt::scope)
                .containsSubsequence(
                        "read only adjacency",
                        "fixture contract boundary",
                        "receipt id uniqueness",
                        "validation command coverage",
                        "catalog completion readiness"
                );
    }

    @Test
    void keepsCatalogCompletionReadinessEvidencePathVersionedToV258() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_CATALOG_COMPLETION_READINESS_EVIDENCE_PATH)
                .isEqualTo(
                        "e/258/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "catalog-completion-readiness-v258.json"
                );
    }
}
