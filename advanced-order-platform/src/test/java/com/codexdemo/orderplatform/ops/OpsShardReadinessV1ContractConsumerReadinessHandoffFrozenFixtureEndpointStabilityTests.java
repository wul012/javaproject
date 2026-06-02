package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractConsumerReadinessHandoffFrozenFixtureEndpointStabilityTests {

    @Test
    void keepsFrozenV225HandoffEndpointAndFixtureEndpointStable() {
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.readinessHandoffEndpoint())
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.ENDPOINT);
        assertThat(handoff.readinessHandoffFixtureEndpoint())
                .isEqualTo(OpsShardReadinessV1ContractConsumerReadinessHandoffService.FIXTURE_ENDPOINT);
        assertThat(handoff.readinessHandoffFixtureEndpoint())
                .startsWith("/contracts/")
                .endsWith("java-shard-readiness-v1-contract-consumer-readiness-handoff-v225.fixture.json");
    }

    @Test
    void keepsFrozenDigestFixtureEndpointPairedWithTheFrozenDigestEndpoint() {
        OpsShardReadinessV1ContractConsumerEvidenceDigestResponse digest =
                OpsShardReadinessV1ContractConsumerEvidenceDigestSnapshot.v220Digest();
        OpsShardReadinessV1ContractConsumerReadinessHandoffResponse handoff =
                OpsShardReadinessV1ContractConsumerReadinessHandoffSnapshot.v225Handoff();

        assertThat(handoff.evidenceDigestEndpoint()).isEqualTo(digest.evidenceDigestEndpoint());
        assertThat(handoff.evidenceDigestFixtureEndpoint()).isEqualTo(digest.evidenceDigestFixtureEndpoint());
    }

    @Test
    void keepsFrozenFixtureEndpointStabilityPathVersionedToV281() {
        assertThat(OpsShardReadinessV1ContractConsumerReadinessHandoffService
                .CONSUMER_READINESS_HANDOFF_FROZEN_FIXTURE_ENDPOINT_STABILITY_EVIDENCE_PATH)
                .isEqualTo(
                        "e/281/evidence/"
                                + "java-shard-readiness-v1-contract-consumer-readiness-handoff-"
                                + "frozen-fixture-endpoint-stability-v281.json"
                );
    }
}
