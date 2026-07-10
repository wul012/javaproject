package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff> handoffs() {
    return List.of(
        handoff(
            "release-acceptance-archive-verification-handoff-service",
            "import narrow release-acceptance route paths for endpoint assembly",
            "endpoint remains stable across the split"),
        handoff(
            "release-acceptance-archive-verification-handoff-controller",
            "import narrow release-acceptance route paths for request mapping",
            "controller exposes the same route as before"),
        handoff(
            "route-path-compatibility-tests",
            "assert stable barrel values equal narrow module values",
            "legacy tests can keep using OpsShardReadinessRoutePaths"),
        handoff(
            "future-release-acceptance-services",
            "add new release-acceptance route constants in the narrow module first",
            "prevent the root route barrel from absorbing new local ownership"),
        handoff(
            "node-v1846-parallel-review",
            "consume this registry only as Java maintainability evidence",
            "no fresh Node or mini-kv evidence is required by the split"));
  }

  private static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff handoff(
      String consumer, String importRule, String expectation) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff(
        consumer, importRule, expectation, "passed");
  }
}
