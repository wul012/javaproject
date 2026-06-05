package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupServiceTests {

    @Test
    void buildsTestEvidenceRollupForSustainmentRoutes() {
        OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse rollup =
                new OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService().rollup();

        assertThat(rollup.version()).isEqualTo("Java v528");
        assertThat(rollup.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-test-evidence-rollup");
        assertThat(rollup.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-test-evidence-rollup.v1");
        assertThat(rollup.evidenceEntryCount()).isEqualTo(5);
        assertThat(rollup.coveredEntryCount()).isEqualTo(5);
        assertThat(rollup.entries())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse
                        .TestEvidenceEntry::coverageType)
                .containsExactly("service", "service", "service", "contract", "integration");
        assertThat(rollup.entries().get(4).testClass())
                .isEqualTo("OpsShardReadinessRouteCleanupMaintenanceSustainmentIntegrationTests");
        assertThat(rollup.entries()).allSatisfy(entry -> assertThat(entry.status()).isEqualTo("covered"));
        assertThat(rollup.checks()).contains("test-evidence-rollup-remains-read-only");
        assertThat(rollup.status()).isEqualTo("passed");
    }
}
