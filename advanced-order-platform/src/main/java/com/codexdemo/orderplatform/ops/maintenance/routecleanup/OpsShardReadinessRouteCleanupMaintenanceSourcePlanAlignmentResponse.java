package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    String sourcePlan,
    String sourcePlanPath,
    int segmentCount,
    int upstreamAlignmentCount,
    List<String> checks,
    String status) {}
