package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitRenderer() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection> render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot> sourceSnapshots,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry> routePaths,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck> compatibilityChecks,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard> boundaryGuards,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff> consumerHandoffs,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry> scorecard
    ) {
        return List.of(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceRenderer.render(sourceSnapshots),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteRenderer.render(routePaths),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityRenderer.render(compatibilityChecks),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryRenderer.render(boundaryGuards),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerRenderer.render(consumerHandoffs),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardRenderer.render(scorecard)
        );
    }
}
