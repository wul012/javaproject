package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        boolean routePathSplitEnabled,
        String sourcePlan,
        String nodeSplitPlan,
        String sourceHandoffVersion,
        String sourceHandoffEndpoint,
        String endpoint,
        String profile,
        int sourceSnapshotCount,
        int routePathCount,
        int compatibilityCheckCount,
        int boundaryGuardCount,
        int consumerHandoffCount,
        int scorecardEntryCount,
        int markdownSectionCount,
        List<SourceSnapshot> sourceSnapshots,
        List<RoutePathEntry> routePaths,
        List<CompatibilityCheck> compatibilityChecks,
        List<BoundaryGuard> boundaryGuards,
        List<ConsumerHandoff> consumerHandoffs,
        List<ScorecardEntry> scorecard,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record SourceSnapshot(
            String source,
            String version,
            String endpoint,
            String status
    ) {
    }

    public record RoutePathEntry(
            String symbol,
            String path,
            String stablePath,
            String splitPath,
            String stableEntrypoint,
            String splitEntrypoint,
            boolean legacyCompatible,
            String status
    ) {
    }

    public record CompatibilityCheck(
            String check,
            String stableValue,
            String splitValue,
            boolean matched
    ) {
    }

    public record BoundaryGuard(
            String boundary,
            boolean locked,
            String evidence
    ) {
    }

    public record ConsumerHandoff(
            String consumer,
            String importRule,
            String expectation,
            String status
    ) {
    }

    public record ScorecardEntry(
            String category,
            boolean passed,
            String detail
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
