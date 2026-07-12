package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupHandoffChecklistResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String checklistEndpoint,
    String checklistProfile,
    int checkCount,
    List<CheckItem> checks,
    String status) {

  public record CheckItem(
      String name, String owner, boolean passed, String evidence, String status) {}
}
