package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceLatestSiblingServiceTests {

    @Test
    void buildsLatestSiblingMaintenanceReportFromSplitSegment() {
        OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse report =
                new OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService().report();

        assertThat(report.version()).isEqualTo("Java v475");
        assertThat(report.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-latest-sibling-report");
        assertThat(report.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-latest-sibling-report.v1");
        assertThat(report.firstJavaVersion()).isEqualTo(306);
        assertThat(report.latestJavaVersion()).isEqualTo(317);
        assertThat(report.entryCount()).isEqualTo(12);
        assertThat(report.liveSmokeEntryCount()).isEqualTo(6);
        assertThat(report.sourceNodePlans()).contains("Node v538", "Node v549");
        assertThat(report.evidencePaths()).allSatisfy(path -> assertThat(path).startsWith("e/"));
        assertThat(report.checks())
                .contains(
                        "latest-sibling-source-node-v549-present",
                        "latest-sibling-remains-read-only"
                );
        assertThat(report.status()).isEqualTo("passed");
    }
}
