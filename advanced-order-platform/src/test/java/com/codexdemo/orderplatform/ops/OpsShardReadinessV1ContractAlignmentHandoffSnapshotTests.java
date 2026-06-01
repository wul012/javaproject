package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessV1ContractAlignmentHandoffSnapshotTests {

    @Test
    void freezesV190HandoffInputsWithoutReadingCurrentRegistryOrRuntimeState() {
        OpsShardReadinessV1ContractAlignmentResponse alignment =
                OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190SourceAlignment();

        assertThat(alignment.version()).isEqualTo("Java v187");
        assertThat(alignment.sourceReadinessVersion()).isEqualTo("Java v153");
        assertThat(alignment.sourceEndpoint()).isEqualTo("/api/v1/ops/shard-readiness");
        assertThat(alignment.sourceFixtureEndpoint()).isEqualTo("/contracts/java-shard-readiness-v153.fixture.json");
        assertThat(alignment.minimalFields()).hasSize(10);
        assertThat(alignment.minimalFieldsFrozen()).isTrue();
        assertThat(alignment.receiptId())
                .isEqualTo("java-shard-readiness-v1-contract-alignment-receipt-v187");

        assertThat(OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190SnapshotFreezeVersion())
                .isEqualTo("Java v188");
        assertThat(OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190HistoricalCompatibilityVersion())
                .isEqualTo("Java v189");
        assertThat(OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190RegistryContainsAlignment()).isTrue();
        assertThat(OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190OlderSnapshotsRemainUnbackfilled())
                .isTrue();
        assertThat(OpsShardReadinessV1ContractAlignmentHandoffSnapshot.v190HistoricalSnapshotsProtected())
                .isTrue();
    }
}
