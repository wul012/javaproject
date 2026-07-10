package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.BoundaryGuard>
      guards(OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source) {
    return source.boundaryAssertions().stream()
        .map(
            assertion ->
                new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                    .BoundaryGuard(assertion.boundary(), assertion.locked(), assertion.detail()))
        .toList();
  }
}
