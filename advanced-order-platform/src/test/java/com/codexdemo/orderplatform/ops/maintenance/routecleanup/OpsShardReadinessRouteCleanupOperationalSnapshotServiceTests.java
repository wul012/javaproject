package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupOperationalSnapshotServiceTests {

  @Test
  void buildsReadOnlyOperationalSnapshotWithoutRuntimeExecution() {
    OpsShardReadinessRouteCleanupOperationalSnapshotResponse snapshot =
        new OpsShardReadinessRouteCleanupOperationalSnapshotService(
                OpsShardReadinessRouteCleanupServiceFixtures.continuityReportService(),
                OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
                new OpsShardReadinessRouteCleanupAcceptanceReceiptService(
                    new OpsShardReadinessRouteCleanupAuditTrailService(),
                    OpsShardReadinessRouteCleanupServiceFixtures.extendedCloseoutService()))
            .snapshot();

    assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion())
        .isGreaterThanOrEqualTo(372);
    assertThat(snapshot.project()).isEqualTo("advanced-order-platform");
    assertThat(snapshot.version())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
    assertThat(snapshot.readOnly()).isTrue();
    assertThat(snapshot.executionAllowed()).isFalse();
    assertThat(snapshot.snapshotEndpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-operational-snapshot");
    assertThat(snapshot.snapshotProfile())
        .isEqualTo("java-shard-readiness-route-cleanup-operational-snapshot.v1");
    assertThat(snapshot.latestVersion())
        .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion());
    assertThat(snapshot.endpointCount()).isGreaterThanOrEqualTo(24);
    assertThat(snapshot.phaseCount()).isGreaterThanOrEqualTo(7);
    assertThat(snapshot.receipt()).startsWith("accepted-read-only-route-cleanup-handoff-v");
    assertThat(snapshot.boundarySignalCount()).isEqualTo(5);
    assertThat(snapshot.boundarySignals())
        .extracting(OpsShardReadinessRouteCleanupOperationalSnapshotResponse.BoundarySignal::name)
        .containsExactly(
            "versions-continuous",
            "read-only-boundary-held",
            "receipt-status",
            "manifest-status",
            "execution-allowed");
    assertThat(snapshot.boundarySignals())
        .allSatisfy(signal -> assertThat(signal.status()).isEqualTo("passed"));
    assertThat(snapshot.status()).isEqualTo("passed");
  }
}
