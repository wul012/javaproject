package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshotTests {

    @Test
    void freezesV179RegistryCountsForHistoricalVerificationReceipt() {
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179LiveEndpoints())
                .hasSize(22)
                .contains(
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff",
                        "/api/v1/ops/shard-readiness/read-only-evidence-catalog-handoff-verification"
                )
                .doesNotContain("/api/v1/ops/shard-readiness/read-only-endpoint-registry-integrity");
        assertThat(OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.v179FixtureEndpoints())
                .hasSize(22)
                .contains(
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-v175.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-v177.fixture.json",
                        "/contracts/java-shard-readiness-read-only-evidence-catalog-handoff-verification-v179.fixture.json"
                )
                .doesNotContain("/contracts/java-shard-readiness-read-only-endpoint-registry-integrity-v184.fixture.json");
    }
}
