package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshotTests {

    @Test
    void freezesV184RegistryCountsForHistoricalIntegrityReceipt() {
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184EndpointPairs())
                .hasSize(23);
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184LiveEndpoints())
                .hasSize(23)
                .contains(
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification",
                        "/api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity"
                );
        assertThat(OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.v184FixtureEndpoints())
                .hasSize(23)
                .contains(
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json",
                        "/contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json"
                );
    }

    @Test
    void integrityServiceReadsFrozenV184SnapshotCounts() {
        OpsShardReadinessReadOnlyEndpointRegistryIntegrityResponse integrity =
                new OpsShardReadinessReadOnlyEndpointRegistryIntegrityService().integrity();

        assertThat(integrity.pairCount()).isEqualTo(23);
        assertThat(integrity.liveEndpointCount()).isEqualTo(23);
        assertThat(integrity.fixtureEndpointCount()).isEqualTo(23);
        assertThat(integrity.verificationChecks())
                .contains(
                        "endpoint-pairs-count:23",
                        "live-endpoints-count:23",
                        "fixture-endpoints-count:23"
                );
    }
}
