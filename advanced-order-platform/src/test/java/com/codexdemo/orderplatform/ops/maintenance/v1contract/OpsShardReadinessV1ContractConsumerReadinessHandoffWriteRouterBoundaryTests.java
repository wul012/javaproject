package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffWriteRouterBoundaryTests {

  @Test
  void keepsWriteRoutingAndActiveShardRouterDisabledInReadinessHandoff() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.writeRoutingAllowed()).isFalse();
    assertThat(handoff.activeShardRouterAllowed()).isFalse();
    assertThat(handoff.shardEnabled()).isFalse();
    assertThat(handoff.blockedOperations()).contains("write-routing", "active-shard-router");
  }

  @Test
  void keepsWriteRouterBoundaryEvidencePathVersionedToV252() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_WRITE_ROUTER_BOUNDARY_EVIDENCE_PATH)
        .isEqualTo(
            "e/252/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "write-router-boundary-v252.json");
  }
}
