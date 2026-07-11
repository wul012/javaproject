package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertReceiptCountAtLeast;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertVersionRun;

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
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_CATALOG_RECEIPT_COUNT_FLOOR_EVIDENCE_PATH,
        285,
        "catalog-receipt-count-floor");
  }
}
