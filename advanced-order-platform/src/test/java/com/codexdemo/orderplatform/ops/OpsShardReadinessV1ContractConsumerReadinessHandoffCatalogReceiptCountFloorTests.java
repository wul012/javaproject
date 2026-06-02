package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertReceiptCountAtLeast;
import static com.codexdemo.orderplatform.ops.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertVersionRun;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogReceiptCountFloorTests {

    @Test
    void keepsPostHandoffCatalogAtSixtyOrMoreReceiptsAfterV285() {
        assertReceiptCountAtLeast(60);
        assertVersionRun(275, 285);
    }

    @Test
    void keepsCatalogReceiptCountFloorPathVersionedToV285() {
        assertEvidencePath(
                OpsShardReadinessV1ContractConsumerReadinessHandoffService
                        .CONSUMER_READINESS_HANDOFF_CATALOG_RECEIPT_COUNT_FLOOR_EVIDENCE_PATH,
                285,
                "catalog-receipt-count-floor"
        );
    }
}
