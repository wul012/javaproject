package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int archiveEntryCount,
    int retentionDays,
    int nextReviewVersion,
    List<ArchiveRetentionEntry> entries,
    List<String> checks,
    String status) {

  public record ArchiveRetentionEntry(
      String itemName,
      String evidencePath,
      int sourceRouteVersion,
      int retentionDays,
      String reviewCadence,
      String status) {}
}
