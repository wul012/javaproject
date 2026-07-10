package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutBoundaryCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutBoundaryCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.BoundaryAssertion>
      assertions(OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse source) {
    return source.boundaryGuards().stream()
        .map(
            guard ->
                new OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse
                    .BoundaryAssertion(guard.boundary(), guard.locked(), guard.evidence()))
        .toList();
  }
}
