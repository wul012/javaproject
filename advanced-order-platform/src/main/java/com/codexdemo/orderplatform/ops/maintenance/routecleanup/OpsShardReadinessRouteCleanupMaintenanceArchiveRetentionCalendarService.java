package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService {

  public static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_ARCHIVE_RETENTION_CALENDAR;
  static final String PROFILE =
      "java-shard-readiness-route-cleanup-maintenance-archive-retention-calendar.v1";
  private static final int RETENTION_DAYS = 365;
  private static final int REVIEW_VERSION_INTERVAL = 20;

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse calendar() {
    List<
            OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse
                .ArchiveRetentionEntry>
        entries =
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().stream()
                .map(this::entry)
                .toList();
    int nextReviewVersion =
        OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.latestRouteVersion()
            + REVIEW_VERSION_INTERVAL;
    List<String> checks =
        List.of(
            "archive-entry-count-" + entries.size(),
            "retention-days-" + RETENTION_DAYS,
            "next-review-version-" + nextReviewVersion,
            "calendar-does-not-touch-archive-files",
            "archive-retention-calendar-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse(
        "advanced-order-platform",
        "Java v526",
        true,
        false,
        ENDPOINT,
        PROFILE,
        entries.size(),
        RETENTION_DAYS,
        nextReviewVersion,
        entries,
        checks,
        status(entries, nextReviewVersion));
  }

  private OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse
          .ArchiveRetentionEntry
      entry(OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item) {
    return new OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse
        .ArchiveRetentionEntry(
        item.name(),
        item.evidencePath(),
        item.routeVersion(),
        RETENTION_DAYS,
        "every-" + REVIEW_VERSION_INTERVAL + "-java-versions",
        item.status());
  }

  private String status(
      List<
              OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse
                  .ArchiveRetentionEntry>
          entries,
      int nextReviewVersion) {
    boolean passed =
        entries.size() == OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().size()
            && entries.stream().allMatch(entry -> entry.evidencePath().startsWith("e/"))
            && entries.stream().allMatch(entry -> entry.retentionDays() == RETENTION_DAYS)
            && nextReviewVersion
                > OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.latestRouteVersion()
            && entries.stream().allMatch(entry -> "passed".equals(entry.status()));
    return passed ? "passed" : "blocked";
  }
}
