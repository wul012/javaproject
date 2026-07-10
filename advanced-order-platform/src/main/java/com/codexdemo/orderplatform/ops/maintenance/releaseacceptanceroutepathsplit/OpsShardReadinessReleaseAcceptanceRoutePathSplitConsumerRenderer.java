package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerRenderer {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerRenderer() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection render(
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff> handoffs) {
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport.section(
        "Consumer Handoffs",
        handoffs.stream()
            .map(handoff -> "- " + handoff.consumer() + " status=" + handoff.status())
            .toList());
  }
}
