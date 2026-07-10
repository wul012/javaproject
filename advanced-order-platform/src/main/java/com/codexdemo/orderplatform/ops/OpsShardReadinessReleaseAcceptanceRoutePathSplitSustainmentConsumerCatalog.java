package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ConsumerHandoff>
      handoffs(OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source) {
    boolean ready =
        "passed".equals(source.status())
            && source.closeoutItems().stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.CloseoutItem
                        ::passed)
            && source.boundaryAssertions().stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse
                            .BoundaryAssertion
                        ::locked);
    return List.of(
        handoff(
            "future-release-acceptance-services",
            "Import route constants through the release acceptance route group.",
            "extend catalogs before adding a new endpoint",
            ready),
        handoff(
            "ops-reviewer",
            "Review the sustainment scorecard before approving route split changes.",
            "read-only checklist",
            ready),
        handoff(
            "ci-maintainer",
            "Keep focused, related, full, diff, and remote CI gates aligned.",
            "test budget and CI traceability",
            ready),
        handoff(
            "archive-curator",
            "Archive source closeout, sustainment response, and CI run together.",
            "release evidence package",
            ready),
        handoff(
            "route-owner",
            "Do not add route path literals in the delegate class.",
            "stable public route surface",
            ready));
  }

  private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ConsumerHandoff
      handoff(String consumer, String handoffRule, String expectedUse, boolean ready) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ConsumerHandoff(
        consumer, handoffRule, expectedUse, ready);
  }
}
