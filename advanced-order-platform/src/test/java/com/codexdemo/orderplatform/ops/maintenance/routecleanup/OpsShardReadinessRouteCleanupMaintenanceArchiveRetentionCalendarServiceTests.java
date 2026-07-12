package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarServiceTests {

  @Test
  void buildsArchiveRetentionCalendarFromVersionedEvidencePaths() {
    OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse calendar =
        new OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService().calendar();

    assertThat(calendar.version()).isEqualTo("Java v526");
    assertThat(calendar.endpoint())
        .isEqualTo(
            "/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-retention-calendar");
    assertThat(calendar.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-archive-retention-calendar.v1");
    assertThat(calendar.archiveEntryCount()).isEqualTo(9);
    assertThat(calendar.retentionDays()).isEqualTo(365);
    assertThat(calendar.nextReviewVersion()).isEqualTo(508);
    assertThat(calendar.entries().get(0).reviewCadence()).isEqualTo("every-20-java-versions");
    assertThat(calendar.entries())
        .allSatisfy(
            entry -> {
              assertThat(entry.evidencePath()).startsWith("e/");
              assertThat(entry.status()).isEqualTo("passed");
            });
    assertThat(calendar.checks()).contains("archive-retention-calendar-remains-read-only");
    assertThat(calendar.status()).isEqualTo("passed");
  }
}
