package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractEvidencePacketServiceTests {

  @Test
  void buildsNodeConsumableReadOnlyEvidencePacketWithoutOpeningExecution() {
    OpsShardReadinessV1ContractEvidencePacketResponse packet =
        new OpsShardReadinessV1ContractEvidencePacketService().packet();

    assertThat(packet.project()).isEqualTo("advanced-order-platform");
    assertThat(packet.version()).isEqualTo("Java v193");
    assertThat(packet.contractName()).isEqualTo("shard-readiness.v1");
    assertThat(packet.readOnly()).isTrue();
    assertThat(packet.executionAllowed()).isFalse();
    assertThat(packet.shardEnabled()).isFalse();
    assertThat(packet.packetEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-evidence-packet");
    assertThat(packet.sourceReadinessEndpoint()).isEqualTo("/api/v1/ops/shard-readiness");
    assertThat(packet.alignmentEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-alignment");
    assertThat(packet.handoffEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-alignment-handoff");
    assertThat(packet.evidenceChain())
        .containsExactly(
            "e/187/evidence/java-shard-readiness-v1-contract-alignment-v187.json",
            "e/188/evidence/java-shard-readiness-v1-contract-alignment-snapshot-freeze-v188.json",
            "e/189/evidence/java-shard-readiness-v187-historical-snapshot-compatibility-v189.json",
            "e/190/evidence/java-shard-readiness-v1-contract-alignment-handoff-v190.json",
            "e/191/evidence/java-shard-readiness-v190-handoff-snapshot-freeze-v191.json",
            "e/192/evidence/java-shard-readiness-v190-handoff-historical-snapshot-compatibility-v192.json");
    assertThat(packet.nodeConsumableEndpoints())
        .containsExactly(
            "/api/v1/ops/shard-readiness",
            "/api/v1/ops/shard-readiness/v1-contract-alignment",
            "/api/v1/ops/shard-readiness/v1-contract-alignment-handoff",
            "/api/v1/ops/shard-readiness/v1-contract-evidence-packet");
    assertThat(packet.nodeConsumableFixtureEndpoints())
        .containsExactly(
            "/contracts/java-shard-readiness-v153.fixture.json",
            "/contracts/java-shard-readiness-v1-contract-alignment-v187.fixture.json",
            "/contracts/java-shard-readiness-v1-contract-alignment-handoff-v190.fixture.json",
            "/contracts/java-shard-readiness-v1-contract-evidence-packet-v193.fixture.json");
    assertThat(packet.blockedOperations())
        .contains(
            "write-routing",
            "active-shard-router",
            "credential-value-read",
            "node-start-or-stop-java-or-mini-kv");
    assertThat(packet.verificationChecks())
        .contains(
            "contract-name:shard-readiness.v1",
            "evidence-chain-count:6",
            "node-consumable-endpoint-count:4",
            "execution-allowed:false");
    assertThat(packet.minimalFieldsFrozen()).isTrue();
    assertThat(packet.historicalSnapshotsProtected()).isTrue();
    assertThat(packet.writeRoutingAllowed()).isFalse();
    assertThat(packet.activeShardRouterAllowed()).isFalse();
    assertThat(packet.credentialValueRead()).isFalse();
    assertThat(packet.rawEndpointParsed()).isFalse();
    assertThat(packet.managedAuditConnectionAllowed()).isFalse();
    assertThat(packet.deploymentOrRollbackAllowed()).isFalse();
    assertThat(packet.nodeMayStartOrStopJavaOrMiniKv()).isFalse();
    assertThat(packet.receiptId())
        .isEqualTo("java-shard-readiness-v1-contract-evidence-packet-receipt-v193");
    assertThat(packet.evidencePath())
        .isEqualTo("e/193/evidence/java-shard-readiness-v1-contract-evidence-packet-v193.json");
    assertThat(packet.status()).isEqualTo("passed");
  }
}
