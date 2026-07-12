package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupEvidenceAnalyzer;
import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceBoundaryReportServiceTests {

  @Test
  void keepsMaintenanceBoundaryBlockedForRuntimeCapabilities() {
    OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse report =
        OpsShardReadinessRouteCleanupPostCompletionServiceFixtures
            .maintenanceBoundaryReportService()
            .report();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(401);
    assertThat(report.project()).isEqualTo("advanced-order-platform");
    assertThat(report.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(report.readOnly()).isTrue();
    assertThat(report.executionAllowed()).isFalse();
    assertThat(report.maintenanceBoundaryReportEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-boundary-report");
    assertThat(report.maintenanceBoundaryReportProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-boundary-report.v1");
    assertThat(report.archiveHandoffReceiptEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-archive-handoff-receipt");
    assertThat(report.boundaryRuleCount()).isEqualTo(7);
    assertThat(report.boundaryRules())
        .allSatisfy(
            rule -> {
              assertThat(rule.allowed()).isFalse();
              assertThat(rule.status()).isEqualTo("blocked");
            });
    assertThat(report.decision()).isEqualTo("maintenance-boundary-held");
    assertThat(report.status()).isEqualTo("passed");
  }
}
