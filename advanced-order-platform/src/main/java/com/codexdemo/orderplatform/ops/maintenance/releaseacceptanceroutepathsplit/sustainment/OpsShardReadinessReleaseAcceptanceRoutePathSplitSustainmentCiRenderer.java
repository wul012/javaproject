package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiRenderer {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiRenderer() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection render(
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate> gates) {
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.section(
        "CI Gates",
        gates.stream()
            .map(
                gate ->
                    "- " + gate.gate() + " scope=" + gate.scope() + " required=" + gate.required())
            .toList());
  }
}
