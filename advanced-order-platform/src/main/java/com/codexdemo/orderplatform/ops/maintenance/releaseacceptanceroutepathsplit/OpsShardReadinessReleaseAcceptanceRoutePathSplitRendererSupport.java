package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection section(
      String heading, List<String> lines) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection(
        heading, List.copyOf(lines));
  }
}
