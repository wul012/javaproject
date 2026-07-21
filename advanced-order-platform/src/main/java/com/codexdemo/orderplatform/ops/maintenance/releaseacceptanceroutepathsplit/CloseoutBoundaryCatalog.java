package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.BoundaryAssertion;
import java.util.List;

final class CloseoutBoundaryCatalog {

  private CloseoutBoundaryCatalog() {}

  static List<BoundaryAssertion> assertions(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse source) {
    return source.boundaryGuards().stream()
        .map(guard -> new BoundaryAssertion(guard.boundary(), guard.locked(), guard.evidence()))
        .toList();
  }
}
