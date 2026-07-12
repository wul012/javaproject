package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int checklistItemCount,
    int acceptedItemCount,
    List<ChecklistItem> items,
    List<String> checks,
    String status) {

  public record ChecklistItem(
      String name, String sourceEndpoint, String owner, String evidence, String status) {}
}
