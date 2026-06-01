package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshotTests {

    @Test
    void freezesV225ConsumerReadinessHandoffSnapshot() {
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.version()).isEqualTo("Java v225");
        assertThat(handoff.evidenceDigestEndpoint()).isEqualTo(digest.evidenceDigestEndpoint());
        assertThat(handoff.evidenceDigestReceiptId()).isEqualTo(digest.receiptId());
        assertThat(handoff.digestEvidence())
                .containsExactlyElementsOf(digest.digestEvidence());
        assertThat(handoff.handoffGuardEvidence())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot
                                .v225HandoffGuardEvidence()
                );
        assertThat(handoff.handoffChecks())
                .containsExactlyElementsOf(
                        OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot
                                .v225HandoffChecks(digest)
                );
        assertThat(handoff.evidencePath())
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.EVIDENCE_PATH);
    }

    @Test
    void serviceReturnsTheFrozenV225Handoff() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse serviceResponse =
                new OpsShardReadinessV1ContractConsumerReadinessHandoffService().handoff();

        assertThat(serviceResponse)
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff());
    }
}
