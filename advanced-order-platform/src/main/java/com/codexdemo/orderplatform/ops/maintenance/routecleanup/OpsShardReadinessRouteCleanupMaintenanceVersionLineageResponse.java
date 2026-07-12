package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int pairCount,
    int firstServiceVersion,
    int latestServiceVersion,
    int firstRouteVersion,
    int latestRouteVersion,
    int serviceVersionStep,
    int routeVersionStep,
    int gapCount,
    List<LineagePair> pairs,
    List<String> checks,
    String status) {

  public record LineagePair(
      String itemName,
      int serviceVersion,
      int routeVersion,
      boolean routeFollowsService,
      int nextServiceVersion,
      String status) {}
}
