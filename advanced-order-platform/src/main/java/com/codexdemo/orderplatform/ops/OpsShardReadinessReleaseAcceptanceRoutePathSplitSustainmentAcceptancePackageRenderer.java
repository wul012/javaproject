package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRenderer() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .MarkdownSection> render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .SourceSnapshot> sourceSnapshots,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .VersionLineage> lineage,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .DecisionRecord> decisions,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .ArchiveItem> archiveItems,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .ReviewItem> reviewItems,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .CiEvidence> ciEvidence,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .RuntimeBoundary> runtimeBoundaries,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .NextChangeRule> nextChangeRules,
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .ScorecardEntry> scorecard
    ) {
        return List.of(
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceRenderer
                        .render(sourceSnapshots),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageLineageRenderer
                        .render(lineage),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageDecisionRenderer
                        .render(decisions),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageArchiveRenderer
                        .render(archiveItems),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageReviewRenderer
                        .render(reviewItems),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCiRenderer
                        .render(ciEvidence),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRuntimeBoundaryRenderer
                        .render(runtimeBoundaries),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageNextChangeRenderer
                        .render(nextChangeRules),
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageScorecardRenderer
                        .render(scorecard)
        );
    }
}
