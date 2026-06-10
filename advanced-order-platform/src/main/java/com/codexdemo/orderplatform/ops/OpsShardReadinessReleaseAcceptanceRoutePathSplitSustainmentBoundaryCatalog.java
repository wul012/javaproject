package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.BoundaryGuard> guards(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source
    ) {
        return source.boundaryAssertions().stream()
                .map(assertion -> new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                        .BoundaryGuard(assertion.boundary(), assertion.locked(), assertion.detail()))
                .toList();
    }
}
