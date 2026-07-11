package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceEndpointsTestSupport;
import com.codexdemo.orderplatform.ops.maintenance.readonlyevidence.OpsShardReadinessReadOnlyEvidenceCatalogService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffOpsEvidenceAlignmentTests {

  @Test
  void keepsReadinessHandoffEndpointPairVisibleInOpsEvidenceInventory() {
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.liveEndpoints())
        .contains(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.fixtureEndpoints())
        .contains(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
  }

  @Test
  void keepsReadinessHandoffProbePairVisibleInOpsEvidenceInventory() {
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.liveProbeEndpoints())
        .contains("GET " + OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.fixtureProbeEndpoints())
        .contains(
            "GET " + OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
  }

  @Test
  void keepsOpsEvidenceOrderingAlignedWithV1ConsumerChain() {
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.liveEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.fixtureEndpoints())
        .containsSubsequence(
            OpsShardReadinessV1ContractConsumerVerificationChecklistService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerEvidenceDigestService.FIXTURE_ENDPOINT,
            OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT,
            OpsShardReadinessReadOnlyEvidenceCatalogService.FIXTURE_ENDPOINT);
  }
}
