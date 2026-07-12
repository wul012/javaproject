package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int riskCount,
    int highRiskCount,
    int mitigatedRiskCount,
    List<RiskEntry> risks,
    List<String> checks,
    String status) {

  public record RiskEntry(
      String name, String mitigation, String owner, String severity, String status) {}
}
