package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int queueItemCount,
    int standbyItemCount,
    int blockedItemCount,
    List<QueueItem> items,
    List<String> checks,
    String status) {

  public record QueueItem(
      String name, String trigger, String sourceEndpoint, String action, String status) {}
}
