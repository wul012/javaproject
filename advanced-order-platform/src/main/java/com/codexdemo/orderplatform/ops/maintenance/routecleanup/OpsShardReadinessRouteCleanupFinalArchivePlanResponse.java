package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupFinalArchivePlanResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String finalArchivePlanEndpoint,
    String finalArchivePlanProfile,
    String finalVerificationEndpoint,
    int sourceEndpointCount,
    int archiveStepCount,
    List<ArchiveStep> archiveSteps,
    String decision,
    String status) {

  public record ArchiveStep(String name, String owner, String evidence, String status) {}
}
