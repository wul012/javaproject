package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRuntimeBoundaryRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRuntimeBoundaryRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .RuntimeBoundary> boundaries
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport.section(
                "Runtime Boundaries",
                boundaries.stream()
                        .map(boundary -> "- " + boundary.boundary() + " policy=" + boundary.policy()
                                + " locked=" + boundary.locked())
                        .toList()
        );
    }
}
