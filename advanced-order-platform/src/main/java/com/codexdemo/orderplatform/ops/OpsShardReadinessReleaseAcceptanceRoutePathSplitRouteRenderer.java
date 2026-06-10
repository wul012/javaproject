package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry> routes
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport.section(
                "Route Path Split",
                routes.stream()
                        .map(route -> "- " + route.symbol() + " " + route.path()
                                + " compatible=" + route.legacyCompatible())
                        .toList()
        );
    }
}
