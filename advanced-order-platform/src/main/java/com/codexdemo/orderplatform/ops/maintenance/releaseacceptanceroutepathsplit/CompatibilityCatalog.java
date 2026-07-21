package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry;
import java.util.List;

final class CompatibilityCatalog {

  private CompatibilityCatalog() {}

  static List<CompatibilityCheck> checks(List<RoutePathEntry> routes) {
    return routes.stream()
        .map(
            route ->
                new CompatibilityCheck(
                    route.symbol(),
                    route.stablePath(),
                    route.splitPath(),
                    route.legacyCompatible()))
        .toList();
  }
}
