package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractOperatorChecklistSnapshotTests {

    @Test
    void freezesV196ChecklistInputsWithoutReadingCurrentPacketServiceOrRegistryState() {
        OpsShardReadinessV1ContractOperatorChecklistResponse checklist =
                OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196Checklist();
        OpsShardReadinessV1ContractEvidencePacketResponse packet =
                OpsShardReadinessV1ContractEvidencePacketSnapshot.v193Packet();

        assertThat(checklist.version()).isEqualTo("Java v196");
        assertThat(checklist.checklistEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/v1-contract-operator-checklist");
        assertThat(checklist.packetEndpoint()).isEqualTo(packet.packetEndpoint());
        assertThat(checklist.operatorChecklistItems())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196OperatorChecklistItems()
                );
        assertThat(checklist.requiredReadOnlyEvidence())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196RequiredReadOnlyEvidence(packet)
                );
        assertThat(checklist.nodeResponsibilities())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196NodeResponsibilities()
                );
        assertThat(checklist.javaResponsibilities())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196JavaResponsibilities()
                );
        assertThat(checklist.verificationChecks())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractOperatorChecklistSnapshot.v196VerificationChecks(packet)
                );
        assertThat(checklist.packetFrozen()).isTrue();
        assertThat(checklist.historicalSnapshotsProtected()).isTrue();
        assertThat(checklist.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-operator-checklist-receipt-v196");
        assertThat(checklist.status()).isEqualTo("passed");
    }
}
