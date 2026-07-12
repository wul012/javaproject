package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v549";

  private OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewSupport() {}

  static OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse response(
      String version,
      String endpoint,
      String profile,
      List<OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse.ReviewItem> items,
      List<String> additionalChecks) {
    int passed = (int) items.stream().filter(item -> "passed".equals(item.status())).count();
    List<String> checks = new ArrayList<>();
    checks.add("item-count-" + items.size());
    checks.add("passed-item-count-" + passed);
    checks.add("source-plan-" + SOURCE_PLAN);
    checks.addAll(additionalChecks);
    checks.add("sustainment-review-remains-read-only");
    return new OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse(
        PROJECT,
        version,
        true,
        false,
        endpoint,
        profile,
        items.size(),
        passed,
        SOURCE_PLAN,
        List.copyOf(items),
        List.copyOf(checks),
        passed == items.size() ? "passed" : "blocked");
  }

  static OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse.ReviewItem item(
      String name, String owner, String evidence, String sourceEndpoint) {
    return new OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse.ReviewItem(
        name, owner, evidence, sourceEndpoint, "passed");
  }
}
