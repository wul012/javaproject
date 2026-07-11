package com.codexdemo.orderplatform.ops.maintenance.v1contract;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractEvidencePacketSnapshotTests {

  @Test
  void freezesV193PacketInputsWithoutReadingCurrentHandoffOrRegistryState() {
    OpsShardReadinessV1ContractEvidencePacketResponse packet =
        OpsShardReadinessV1ContractEvidencePacketSnapshot.v193Packet();

    assertThat(packet.version()).isEqualTo("Java v193");
    assertThat(packet.packetEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-evidence-packet");
    assertThat(packet.evidenceChain())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractEvidencePacketSnapshot.v193EvidenceChain());
    assertThat(packet.nodeConsumableEndpoints())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractEvidencePacketSnapshot.v193NodeConsumableEndpoints());
    assertThat(packet.nodeConsumableFixtureEndpoints())
        .containsExactlyElementsOf(
            OpsShardReadinessV1ContractEvidencePacketSnapshot.v193NodeConsumableFixtureEndpoints());
    assertThat(packet.minimalFieldsFrozen()).isTrue();
    assertThat(packet.historicalSnapshotsProtected()).isTrue();
    assertThat(packet.receiptId())
        .isEqualTo("java-shard-readiness-v1-contract-evidence-packet-receipt-v193");
    assertThat(packet.status()).isEqualTo("passed");
  }
}
