package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.BoundaryGuard> guards
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.section(
                "Boundary Guards",
                guards.stream()
                        .map(guard -> "- " + guard.boundary() + " locked=" + guard.locked()
                                + " evidence=" + guard.evidence())
                        .toList()
        );
    }
}
