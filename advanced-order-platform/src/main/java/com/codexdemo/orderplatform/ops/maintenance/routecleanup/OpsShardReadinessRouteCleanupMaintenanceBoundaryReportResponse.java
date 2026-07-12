package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceBoundaryReportResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String maintenanceBoundaryReportEndpoint,
    String maintenanceBoundaryReportProfile,
    String archiveHandoffReceiptEndpoint,
    int boundaryRuleCount,
    List<BoundaryRule> boundaryRules,
    String decision,
    String status) {

  public record BoundaryRule(
      String name, String blockedCapability, boolean allowed, String status) {}
}
