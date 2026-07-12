package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int stepCount,
    List<TestStep> steps,
    List<String> forbiddenOperations,
    String status) {

  public record TestStep(
      String name,
      String commandScope,
      boolean startsJavaService,
      boolean startsMiniKvService,
      boolean startsNodeService,
      String expectedResult) {}
}
