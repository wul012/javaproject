package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int segmentCount,
    int entryCount,
    List<SegmentSummary> segments,
    List<String> forbiddenOperations,
    String status) {

  public record SegmentSummary(
      String name,
      int firstJavaVersion,
      int lastJavaVersion,
      int entryCount,
      String firstPhase,
      String lastPhase,
      List<String> sourceNodePlans,
      String status) {}
}
