package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String endpoint,
    String profile,
    int firstJavaVersion,
    int latestJavaVersion,
    int entryCount,
    int liveSmokeEntryCount,
    List<String> sourceNodePlans,
    List<String> evidencePaths,
    List<String> checks,
    String status) {}
