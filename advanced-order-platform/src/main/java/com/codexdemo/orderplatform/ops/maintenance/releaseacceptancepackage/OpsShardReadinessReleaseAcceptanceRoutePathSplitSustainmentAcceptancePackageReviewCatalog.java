package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse;
import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageReviewCatalog {

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageReviewCatalog() {}

  static List<
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
              .ReviewItem>
      items(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source) {
    boolean passed = "passed".equals(source.status());
    return List.of(
        item("release-reviewer", "status-and-counts", "source status passed", passed),
        item(
            "route-owner",
            "route-delegate",
            "stable delegate remains enforced",
            ownershipHeld(source, "stable-route-delegate")),
        item(
            "test-owner",
            "coverage",
            "catalog, renderer, controller, and immutability tests exist",
            ownershipHeld(source, "test-ownership")),
        item("ci-owner", "ci-gates", "five CI gates are required", source.ciGateCount() == 5),
        item(
            "archive-owner", "archive-items", "acceptance package is ready for retention", passed));
  }

  private static boolean ownershipHeld(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source,
      String component) {
    return source.ownershipRules().stream()
        .anyMatch(rule -> rule.component().equals(component) && rule.enforced());
  }

  private static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.ReviewItem
      item(String reviewer, String checklist, String expectation, boolean passed) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
        .ReviewItem(reviewer, checklist, expectation, passed);
  }
}
