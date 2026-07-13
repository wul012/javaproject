package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.OpsShardReadinessEvidenceEndpointsTestSupport;
import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths;
import com.codexdemo.orderplatform.ops.maintenance.readinesscore.OpsShardReadinessService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffRouteInventoryTests {

  @Test
  void keepsReadinessHandoffRegisteredAsFinalV1ConsumerPair() {
    assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs()).hasSize(11);
    assertThat(OpsShardReadinessV1ContractEndpointPairs.endpointPairs())
        .last()
        .satisfies(
            pair -> {
              assertThat(pair.liveEndpoint())
                  .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
              assertThat(pair.fixtureEndpoint())
                  .isEqualTo(
                      OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
            });
  }

  @Test
  void keepsReadinessHandoffRoutePathAndProbeInventoryAligned() {
    assertThat(OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF)
        .isEqualTo("/v1-contract-consumer-readiness-handoff");
    assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT)
        .isEqualTo(
            OpsShardReadinessService.BASE_PATH
                + OpsShardReadinessRoutePaths.V1_CONTRACT_CONSUMER_READINESS_HANDOFF);
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.liveProbeEndpoints())
        .contains("GET " + OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
    assertThat(OpsShardReadinessEvidenceEndpointsTestSupport.fixtureProbeEndpoints())
        .contains(
            "GET " + OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
  }

  @Test
  void keepsReadinessHandoffStaticFixtureDiscoverable() {
    assertThat(
            getClass()
                .getClassLoader()
                .getResource(
                    "static/contracts/java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json"))
        .isNotNull();
  }
}
