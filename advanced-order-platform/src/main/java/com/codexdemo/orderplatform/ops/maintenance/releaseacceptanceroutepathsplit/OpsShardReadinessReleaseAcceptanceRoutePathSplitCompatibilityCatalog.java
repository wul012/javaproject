package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck> checks(
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry> routes) {
    return routes.stream()
        .map(
            route ->
                new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck(
                    route.symbol(),
                    route.stablePath(),
                    route.splitPath(),
                    route.legacyCompatible()))
        .toList();
  }
}
