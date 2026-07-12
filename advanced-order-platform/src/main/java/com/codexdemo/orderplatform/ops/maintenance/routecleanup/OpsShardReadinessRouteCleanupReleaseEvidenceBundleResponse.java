package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;

public record OpsShardReadinessRouteCleanupReleaseEvidenceBundleResponse(
    String project,
    String version,
    boolean readOnly,
    boolean executionAllowed,
    String releaseEvidenceBundleEndpoint,
    String releaseEvidenceBundleProfile,
    int sourceCount,
    List<String> sources,
    int bundleItemCount,
    List<BundleItem> bundleItems,
    String decision,
    String status) {

  public record BundleItem(String name, String evidence, String status) {}
}
