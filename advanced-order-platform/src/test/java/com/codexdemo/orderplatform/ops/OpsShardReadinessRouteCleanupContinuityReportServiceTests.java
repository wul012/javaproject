package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupPhaseSummaryService;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupContinuityReportServiceTests {

  @Test
  void reportsContinuousReadOnlyProgressionFromV326() {
    OpsShardReadinessRouteCleanupContinuityReportResponse report =
        new OpsShardReadinessRouteCleanupContinuityReportService(
                new OpsShardReadinessRouteCleanupEndpointManifestService(),
                new OpsShardReadinessRouteCleanupPhaseSummaryService())
            .report();

    int latestVersion = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();

    assertThat(latestVersion).isGreaterThanOrEqualTo(358);
    assertThat(report.project()).isEqualTo("advanced-order-platform");
    assertThat(report.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(report.readOnly()).isTrue();
    assertThat(report.executionAllowed()).isFalse();
    assertThat(report.reportEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-continuity-report");
    assertThat(report.reportProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-continuity-report.v1");
    assertThat(report.firstVersion()).isEqualTo(326);
    assertThat(report.latestVersion()).isEqualTo(latestVersion);
    assertThat(report.versionCount()).isEqualTo(latestVersion - 325);
    assertThat(report.endpointCount()).isGreaterThanOrEqualTo(17);
    assertThat(report.phaseCount()).isGreaterThanOrEqualTo(7);
    assertThat(report.versionsContinuous()).isTrue();
    assertThat(report.readOnlyBoundaryHeld()).isTrue();
    assertThat(report.status()).isEqualTo("passed");
  }
}
