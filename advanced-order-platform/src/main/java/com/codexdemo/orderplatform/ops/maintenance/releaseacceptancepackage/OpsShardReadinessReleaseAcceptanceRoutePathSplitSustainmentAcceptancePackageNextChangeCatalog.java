package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse;
import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageNextChangeCatalog {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageNextChangeCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
              .NextChangeRule>
      rules(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    boolean ready = "passed".equals(source.status());
    return List.of(
        rule(
            "new-route-path",
            "route-path catalog then release acceptance route group",
            "route-owner",
            ready),
        rule("new-consumer", "consumer catalog", "handoff-owner", ready),
        rule("new-ci-gate", "CI catalog and renderer", "ci-owner", ready),
        rule(
            "new-boundary",
            "boundary catalog and runtime boundary package",
            "ops-boundary-owner",
            ready),
        rule(
            "source-plan-roll",
            "support constants and source catalog",
            "sibling-plan-owner",
            ready),
        rule(
            "markdown-copy-change",
            "section renderer for the affected concern",
            "renderer-owner",
            ready));
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
          .NextChangeRule
      rule(String trigger, String landingZone, String reviewer, boolean ready) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
        .NextChangeRule(trigger, landingZone, reviewer, ready);
  }
}
