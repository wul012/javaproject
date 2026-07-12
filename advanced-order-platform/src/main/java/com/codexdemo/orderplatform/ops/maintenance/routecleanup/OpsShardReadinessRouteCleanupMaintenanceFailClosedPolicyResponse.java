package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int policyCount,
    int protectedItemCount,
    int zeroViolationCount,
    List<PolicyCheck> policies,
    List<String> checks,
    String status) {

  public record PolicyCheck(
      String operation, String guard, String source, int violationCount, String status) {}
}
