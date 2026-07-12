package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupPhaseSummaryResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String summaryEndpoint,
    String summaryProfile,
    int entryCount,
    int phaseCount,
    List<PhaseSummary> phases,
    String status) {

  public record PhaseSummary(
      String segment,
      int firstJavaVersion,
      int lastJavaVersion,
      int entryCount,
      List<String> sourceNodePlans,
      boolean readOnly,
      boolean executionAllowed,
      String status) {}
}
