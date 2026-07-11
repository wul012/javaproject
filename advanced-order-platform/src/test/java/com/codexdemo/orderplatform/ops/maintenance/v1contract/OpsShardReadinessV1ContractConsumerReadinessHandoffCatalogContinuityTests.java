package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertContinuousCatalogFrom;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePathsUniqueAndVersionScoped;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogContinuityTests {

  @Test
  void keepsPostHandoffCatalogVersionsConsecutiveFromV226() {
    assertContinuousCatalogFrom(226);
  }

  @Test
  void keepsPostHandoffCatalogPathsUniqueAndVersionScoped() {
    assertEvidencePathsUniqueAndVersionScoped();
  }

  @Test
  void keepsCatalogContinuityEvidencePathVersionedToV242() {
    assertEvidencePath(
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_CATALOG_CONTINUITY_EVIDENCE_PATH,
        242,
        "catalog-continuity");
  }
}
