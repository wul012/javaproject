package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffFrozenPayloadStabilityTests {

    @Test
    void keepsFrozenV225HandoffCoreFieldsStableAfterPostHandoffGrowth() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.project()).isEqualTo("advanced-order-platform");
        assertThat(handoff.version()).isEqualTo("Java v225");
        assertThat(handoff.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-consumer-readiness-handoff-receipt-v225");
        assertThat(handoff.evidencePath()).isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .EVIDENCE_PATH);
        assertThat(handoff.status()).isEqualTo("passed");
    }

    @Test
    void keepsFrozenV225HandoffReadOnlyAndCountedAgainstItsDigestSnapshot() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.readOnly()).isTrue();
        assertThat(handoff.executionAllowed()).isFalse();
        assertThat(handoff.shardEnabled()).isFalse();
        assertThat(handoff.digestEvidenceCount()).isEqualTo(handoff.digestEvidence().size());
        assertThat(handoff.handoffGuardEvidence()).hasSize(4);
        assertThat(handoff.handoffChecks()).hasSize(7);
    }

    @Test
    void keepsFrozenPayloadStabilityPathVersionedToV271() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_FROZEN_PAYLOAD_STABILITY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/271/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "frozen-payload-stability-v271.json"
                );
    }
}
