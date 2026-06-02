package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCatalogGrowthCloseoutTests {

    @Test
    void keepsV275ThroughV288PostHandoffGrowthRunCatalogedInOrder() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .hasSizeGreaterThanOrEqualTo(63);
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions())
                .containsSubsequence(
                        275, 276, 277, 278, 279,
                        280, 281, 282, 283, 284,
                        285, 286, 287, 288
                );
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .extracting(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog
                        .Receipt::scope)
                .containsSubsequence(
                        "catalog receipt count floor",
                        "archive artifact byte floor",
                        "receipt scope uniqueness",
                        "post handoff catalog growth closeout"
                );
    }

    @Test
    void keepsPostHandoffCatalogGrowthCloseoutPathVersionedToV288() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_POST_HANDOFF_CATALOG_GROWTH_CLOSEOUT_EVIDENCE_PATH)
                .isEqualTo(
                        "e/288/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "post-handoff-catalog-growth-closeout-v288.json"
                );
    }
}
