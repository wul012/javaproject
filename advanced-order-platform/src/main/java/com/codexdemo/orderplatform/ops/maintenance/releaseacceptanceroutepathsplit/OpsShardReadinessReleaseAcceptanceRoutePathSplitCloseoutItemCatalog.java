package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutItemCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutItemCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.CloseoutItem> items(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse source) {
    return List.of(
        item(
            "stable-barrel-preserved",
            source.routePaths().stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry
                        ::legacyCompatible)),
        item(
            "compatibility-catalog-extracted",
            source.compatibilityChecks().stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck
                        ::matched)),
        item("route-count-held", source.routePathCount() == 11),
        item("source-handoff-held", "Java v1570".equals(source.version())),
        item(
            "node-v1866-parallel-no-fresh-evidence",
            "Node v1847-v1866"
                .equals(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutSupport
                        .NODE_PARALLEL_PLAN)),
        item(
            "future-route-owner-rule",
            source.consumerHandoffs().stream()
                .anyMatch(
                    handoff -> handoff.consumer().equals("future-release-acceptance-services"))));
  }

  private static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.CloseoutItem item(
      String item, boolean passed) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.CloseoutItem(
        item, "release-acceptance-route-path-split-registry", passed);
  }
}
