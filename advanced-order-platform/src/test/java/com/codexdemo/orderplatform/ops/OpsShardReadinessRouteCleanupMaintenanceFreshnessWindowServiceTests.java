package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowServiceTests {

    @Test
    void buildsFreshnessWindowFromVersionedCatalog() {
        OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse window =
                new OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService().window();

        assertThat(window.version()).isEqualTo("Java v516");
        assertThat(window.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-freshness-window");
        assertThat(window.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-freshness-window.v1");
        assertThat(window.evidenceCount()).isEqualTo(9);
        assertThat(window.maxVersionLag()).isEqualTo(20);
        assertThat(window.staleEvidenceCount()).isZero();
        assertThat(window.entries().getFirst().versionLag()).isEqualTo(16);
        assertThat(window.entries().getLast().versionLag()).isZero();
        assertThat(window.entries()).allSatisfy(entry -> {
            assertThat(entry.withinWindow()).isTrue();
            assertThat(entry.status()).isEqualTo("passed");
        });
        assertThat(window.checks()).contains("freshness-window-remains-read-only");
        assertThat(window.status()).isEqualTo("passed");
    }
}
