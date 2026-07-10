package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.OwnershipRule>
      rules(OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source) {
    boolean sourcePassed = "passed".equals(source.status());
    return List.of(
        rule(
            "stable-route-delegate",
            "OpsShardReadinessRoutePaths",
            "Keep the public constant as a delegate to the release acceptance route group.",
            "OpsShardReadinessReleaseAcceptanceRoutePaths",
            sourcePassed),
        rule(
            "response-contract",
            "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse",
            "Add fields only when the read-only evidence contract has a matching test.",
            "response record",
            sourcePassed),
        rule(
            "catalog-ownership",
            "sustainment catalog classes",
            "Land new source, ownership, drift, boundary, CI, consumer, and scorecard data in catalogs.",
            "catalog package peers",
            sourcePassed),
        rule(
            "renderer-ownership",
            "sustainment renderer classes",
            "Render one markdown section per renderer and keep section assembly in the root renderer.",
            "renderer package peers",
            sourcePassed),
        rule(
            "controller-surface",
            "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController",
            "Expose one GET route and delegate all assembly to the read-only service.",
            "controller",
            sourcePassed),
        rule(
            "test-ownership",
            "sustainment tests",
            "Pin counts, route delegation, markdown headings, immutability, and boundary locks.",
            "test suite",
            sourcePassed));
  }

  private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.OwnershipRule
      rule(String component, String owner, String rule, String landingZone, boolean enforced) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.OwnershipRule(
        component, owner, rule, landingZone, enforced);
  }
}
