package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupFixtureCoverageIndexResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String fixtureCoverageIndexEndpoint,
    String fixtureCoverageIndexProfile,
    String maintenanceBoundaryReportEndpoint,
    int coverageItemCount,
    List<CoverageItem> coverageItems,
    String status) {

  public record CoverageItem(String name, String target, String coverage, String status) {}
}
