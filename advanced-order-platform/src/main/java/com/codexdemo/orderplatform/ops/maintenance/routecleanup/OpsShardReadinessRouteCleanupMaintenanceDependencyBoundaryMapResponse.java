package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int boundaryEntryCount,
    int forbiddenOperationCount,
    List<BoundaryEntry> boundaries,
    List<String> forbiddenOperations,
    List<String> checks,
    String status) {

  public record BoundaryEntry(
      String itemName,
      String owner,
      String boundary,
      String sourceEndpoint,
      String allowedScope,
      String status) {}
}
