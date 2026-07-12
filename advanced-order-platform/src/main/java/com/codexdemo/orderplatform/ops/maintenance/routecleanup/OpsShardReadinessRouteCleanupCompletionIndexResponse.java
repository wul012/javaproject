package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupCompletionIndexResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String completionIndexEndpoint,
    String completionIndexProfile,
    int completionEndpointCount,
    List<CompletionEndpoint> completionEndpoints,
    int statusSignalCount,
    List<StatusSignal> statusSignals,
    String decision,
    String status) {

  public record CompletionEndpoint(
      String name,
      String endpoint,
      String category,
      boolean readOnly,
      boolean executionAllowed,
      String status) {}

  public record StatusSignal(String name, String evidence, String status) {}
}
