package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int evidenceEntryCount,
    int coveredEntryCount,
    List<TestEvidenceEntry> entries,
    List<String> checks,
    String status) {

  public record TestEvidenceEntry(
      String name, String testClass, String coverageType, String evidence, String status) {}
}
