package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceRouteTopologyIndexResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int routeCount,
    int firstRouteVersion,
    int latestRouteVersion,
    List<RouteNode> routes,
    List<String> checks,
    String status) {

  public record RouteNode(
      String itemName,
      int routeVersion,
      String endpoint,
      String previousEndpoint,
      String nextEndpoint,
      String sourceEvidencePath,
      String status) {}
}
