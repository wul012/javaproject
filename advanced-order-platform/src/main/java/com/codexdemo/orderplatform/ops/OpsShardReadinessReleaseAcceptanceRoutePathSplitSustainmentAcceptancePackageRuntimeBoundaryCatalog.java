package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRuntimeBoundaryCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRuntimeBoundaryCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .RuntimeBoundary> boundaries(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source
    ) {
        return source.boundaryGuards().stream()
                .map(boundary -> new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .RuntimeBoundary(boundary.boundary(), "locked-from-sustainment", boundary.evidence(),
                        boundary.locked()))
                .toList();
    }
}
