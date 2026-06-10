package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard> guards
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport.section(
                "Boundary Guards",
                guards.stream()
                        .map(guard -> "- " + guard.boundary() + " locked=" + guard.locked())
                        .toList()
        );
    }
}
