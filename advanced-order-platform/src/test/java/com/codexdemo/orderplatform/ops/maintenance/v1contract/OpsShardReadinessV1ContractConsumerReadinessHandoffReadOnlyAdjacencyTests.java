package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceEndpointsTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffReadOnlyAdjacencyTests {

  @Test
  void keepsReadOnlyCatalogAdjacentAfterReadinessHandoffOnlyInRollingEvidenceRegistry() {
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.liveEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
    assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
        .contains(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT)
        .doesNotContain(OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
  }

  @Test
  void keepsReadOnlyAdjacencyEvidencePathVersionedToV254() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_READ_ONLY_ADJACENCY_EVIDENCE_PATH)
        .isEqualTo(
            "e/254/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "read-only-adjacency-v254.json");
  }
}
