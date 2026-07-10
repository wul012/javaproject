package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerRenderer {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerRenderer() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection render(
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ConsumerHandoff>
          handoffs) {
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.section(
        "Consumer Handoffs",
        handoffs.stream()
            .map(
                handoff ->
                    "- "
                        + handoff.consumer()
                        + " use="
                        + handoff.expectedUse()
                        + " ready="
                        + handoff.ready())
            .toList());
  }
}
