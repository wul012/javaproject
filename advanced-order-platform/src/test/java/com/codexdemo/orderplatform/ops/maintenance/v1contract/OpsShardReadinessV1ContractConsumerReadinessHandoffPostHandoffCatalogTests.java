package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertEvidencePath;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.assertVersionRun;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.evidencePaths;
import static com.codexdemo.orderplatform.ops.maintenance.v1contract.OpsShardReadinessV1ContractConsumerReadinessHandoffCatalogTestSupport.scopes;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffPostHandoffCatalogTests {

  @Test
  void keepsPostHandoffCatalogSeededThroughV241() {
    assertVersionRun(226, 241);
    assertThat(scopes())
        .contains("post handoff catalog", "legacy registry alignment", "completion");
  }

  @Test
  void keepsPostHandoffCatalogOutsideFrozenV225Handoff() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.digestEvidence()).doesNotContainAnyElementsOf(evidencePaths());
    assertThat(handoff.handoffGuardEvidence()).doesNotContainAnyElementsOf(evidencePaths());
  }

  @Test
  void keepsPostHandoffCatalogEvidencePathVersionedToV241() {
    assertEvidencePath(
        OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
            .CONSUMER_READINESS_HANDOFF_POST_HANDOFF_CATALOG_EVIDENCE_PATH,
        241,
        "post-handoff-catalog");
  }
}
