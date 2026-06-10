package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageScorecardCatalog {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageScorecardCatalog() {
    }

    static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .ScorecardEntry> scorecard(
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
                    .NextChangeRule> nextChangeRules
    ) {
        return List.of(
                entry("source", sourceSnapshots.stream().allMatch(snapshot -> "passed".equals(snapshot.status())),
                        "sustainment source passed"),
                entry("lineage", lineage.stream().allMatch(item -> "passed".equals(item.status())),
                        "split, closeout, and sustainment versions are linked"),
                entry("decisions", decisions.stream()
                        .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                .DecisionRecord::accepted),
                        "acceptance decisions are explicit"),
                entry("archive", archiveItems.stream()
                        .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                .ArchiveItem::ready),
                        "archive items are ready"),
                entry("review", reviewItems.stream()
                        .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                .ReviewItem::passed),
                        "review checklist passed"),
                entry("ci", ciEvidence.stream()
                        .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                .CiEvidence::passed),
                        "CI evidence remains required"),
                entry("runtime-boundaries", runtimeBoundaries.stream()
                        .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                .RuntimeBoundary::locked),
                        "runtime boundaries are locked"),
                entry("next-change", nextChangeRules.stream()
                        .allMatch(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                                .NextChangeRule::ready),
                        "future changes have landing zones"),
                entry("maintainability", true, "acceptance package is split into focused catalogs and renderers")
        );
    }

    private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .ScorecardEntry entry(
            String category,
            boolean passed,
            String detail
    ) {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                .ScorecardEntry(category, passed, detail);
    }
}
