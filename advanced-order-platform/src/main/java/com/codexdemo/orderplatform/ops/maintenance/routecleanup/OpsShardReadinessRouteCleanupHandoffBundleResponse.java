package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupHandoffBundleResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String bundleEndpoint,
    String bundleProfile,
    int componentCount,
    List<BundleComponent> components,
    String decision,
    String status) {

  public record BundleComponent(String name, String endpoint, String status) {}
}
