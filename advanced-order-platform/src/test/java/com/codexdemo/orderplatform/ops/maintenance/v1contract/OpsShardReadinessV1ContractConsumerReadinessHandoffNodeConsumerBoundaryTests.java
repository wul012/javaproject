package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffNodeConsumerBoundaryTests {

  @Test
  void keepsNodeConsumersFromStartingJavaOrMiniKvAcrossConsumerReadinessChain() {
    OpsShardReadinessV1ContractConsumerVerificationChecklistResponse checklist =
        OpsShardReadinessV1ContractConsumerVerificationChecklistSnapshot.v215Checklist();
    OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
        OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(checklist.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
    assertThat(digest.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
    assertThat(handoff.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
    assertThat(handoff.startsJavaService()).isFalse();
    assertThat(handoff.startsMiniKvService()).isFalse();
  }

  @Test
  void keepsNodeConsumersFromWriteRoutingCredentialsRawEndpointAndAuditConnections() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.writeRoutingAllowed()).isFalse();
    assertThat(handoff.activeShardRouterAllowed()).isFalse();
    assertThat(handoff.credentialValueRead()).isFalse();
    assertThat(handoff.rawEndpointParsed()).isFalse();
    assertThat(handoff.managedAuditConnectionAllowed()).isFalse();
    assertThat(handoff.deploymentOrRollbackAllowed()).isFalse();
  }

  @Test
  void keepsNodeConsumerStopRuleVisibleInChecksAndBlockedOperations() {
    OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

    assertThat(handoff.handoffChecks()).contains("node-may-start-or-stop-java-or-mini-kv:false");
    assertThat(handoff.blockedOperations()).contains("node-start-or-stop-java-or-mini-kv");
  }
}
