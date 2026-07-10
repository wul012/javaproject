package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse;
import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageDecisionCatalog {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageDecisionCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
              .DecisionRecord>
      decisions(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    return List.of(
        decision(
            "accept-sustainment-registry",
            "release-acceptance-maintainer",
            "v1604 sustainment registry passed",
            "passed".equals(source.status())),
        decision(
            "freeze-stable-route-delegate",
            "route-owner",
            "stable-route-delegate ownership rule is enforced",
            ownershipHeld(source, "stable-route-delegate")),
        decision(
            "require-catalog-before-route",
            "catalog-owner",
            "catalog-ownership rule is enforced before endpoint growth",
            ownershipHeld(source, "catalog-ownership")),
        decision(
            "require-renderer-split",
            "renderer-owner",
            "renderer-ownership rule keeps markdown sections separated",
            ownershipHeld(source, "renderer-ownership")),
        decision(
            "keep-runtime-disabled",
            "ops-boundary-owner",
            "package remains read-only and execution is not allowed",
            source.readOnly() && !source.executionAllowed()),
        decision(
            "parallel-node-no-fresh-evidence",
            "sibling-plan-owner",
            "Node v1879-v1903 remains parallel and needs no fresh Java or mini-kv startup",
            "passed".equals(source.status())));
  }

  private static boolean ownershipHeld(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source,
      String component) {
    return source.ownershipRules().stream()
        .anyMatch(rule -> rule.component().equals(component) && rule.enforced());
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
          .DecisionRecord
      decision(String decision, String owner, String rationale, boolean accepted) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
        .DecisionRecord(decision, owner, rationale, accepted);
  }
}
