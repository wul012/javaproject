package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int evidenceCount,
    int maxVersionLag,
    int staleEvidenceCount,
    List<FreshnessEntry> entries,
    List<String> checks,
    String status) {

  public record FreshnessEntry(
      String itemName,
      String evidencePath,
      int routeVersion,
      int versionLag,
      boolean withinWindow,
      String status) {}
}
