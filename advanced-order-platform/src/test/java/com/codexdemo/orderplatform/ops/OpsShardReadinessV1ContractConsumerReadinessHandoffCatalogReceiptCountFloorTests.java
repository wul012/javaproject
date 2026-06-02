package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogReceiptCountFloorTests {

    @Test
    void keepsPostHandoffCatalogAtSixtyOrMoreReceiptsAfterV285() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.receipts())
                .hasSizeGreaterThanOrEqualTo(60);
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffEvidenceCatalog.versions())
                .containsSubsequence(275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285);
    }

    @Test
    void keepsCatalogReceiptCountFloorPathVersionedToV285() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_CATALOG_RECEIPT_COUNT_FLOOR_EVIDENCE_PATH)
                .isEqualTo(
                        "e/285/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "catalog-receipt-count-floor-v285.json"
                );
    }
}
