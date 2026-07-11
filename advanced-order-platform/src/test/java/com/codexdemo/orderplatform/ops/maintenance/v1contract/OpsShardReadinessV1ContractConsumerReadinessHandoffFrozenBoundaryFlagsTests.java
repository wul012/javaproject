package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffFrozenBoundaryFlagsTests {

  @Test
  void keepsFrozenV225HandoffDangerousBoundaryFlagsFalse() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.upstreamActionsAllowed()).isFalse();
    assertThat(handoff.startsJavaService()).isFalse();
    assertThat(handoff.startsMiniKvService()).isFalse();
    assertThat(handoff.writeRoutingAllowed()).isFalse();
    assertThat(handoff.activeShardRouterAllowed()).isFalse();
    assertThat(handoff.credentialValueRead()).isFalse();
    assertThat(handoff.rawEndpointParsed()).isFalse();
    assertThat(handoff.managedAuditConnectionAllowed()).isFalse();
    assertThat(handoff.deploymentOrRollbackAllowed()).isFalse();
    assertThat(handoff.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
  }

  @Test
  void keepsFrozenBoundaryFlagsPathVersionedToV280() {
    assertThat(
            OpsShardReadinessV1ContractConsumerReadinessHandoffEvidencePaths
                .CONSUMER_READINESS_HANDOFF_FROZEN_BOUNDARY_FLAGS_EVIDENCE_PATH)
        .isEqualTo(
            "e/280/evidence/"
                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                + "frozen-boundary-flags-v280.json");
  }
}
