package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipRenderer {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipRenderer() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection render(
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.OwnershipRule>
          rules) {
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.section(
        "Ownership Rules",
        rules.stream()
            .map(
                rule ->
                    "- "
                        + rule.component()
                        + " owner="
                        + rule.owner()
                        + " landing="
                        + rule.landingZone()
                        + " enforced="
                        + rule.enforced())
            .toList());
  }
}
