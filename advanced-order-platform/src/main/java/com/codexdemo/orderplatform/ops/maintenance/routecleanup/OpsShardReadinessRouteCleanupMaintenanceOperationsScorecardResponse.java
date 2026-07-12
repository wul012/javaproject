package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int score,
    int dimensionCount,
    int passedDimensionCount,
    List<ScorecardDimension> dimensions,
    List<String> checks,
    String status) {

  public record ScorecardDimension(
      String name, String sourceEndpoint, int weight, String evidence, String status) {}
}
