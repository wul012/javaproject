package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceEndpointsTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import java.util.List;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffEndpointAdjacencyTests {

  @Test
  void keepsReadinessHandoffAdjacentAfterEvidenceDigestInV1ConsumerPairs() {
    List<String> liveEndpoints = OpsShardReadinessV1ContractEndpointPairs.liveEndpoints();
    List<String> fixtureEndpoints = OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints();

    assertThat(
            liveEndpoints.indexOf(
                OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT))
        .isEqualTo(
            liveEndpoints.indexOf(OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT)
                + 1);
    assertThat(
            fixtureEndpoints.indexOf(
                OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT))
        .isEqualTo(
            fixtureEndpoints.indexOf(
                    OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT)
                + 1);
  }

  @Test
  void keepsConsumerContractChainOrderedBeforeReadOnlyCatalog() {
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.liveEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractEndpointCatalogService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.fixtureEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractEndpointCatalogService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerHandoffBundleService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT);
  }

  @Test
  void keepsReadinessHandoffAsTheLastV1ContractPairBeforeReadOnlyGroup() {
    assertThat(OpsShardReadinessV1ContractEndpointPairs.liveEndpoints())
        .last()
        .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
    assertThat(OpsShardReadinessV1ContractEndpointPairs.fixtureEndpoints())
        .last()
        .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
  }
}
