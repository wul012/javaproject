package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String sourcePlan,
        String nodeParallelPlan,
        String sourceSplitVersion,
        String sourceSplitEndpoint,
        String endpoint,
        String profile,
        int routePathCount,
        int compatibilityCheckCount,
        int closeoutItemCount,
        int boundaryAssertionCount,
        int markdownSectionCount,
        List<CloseoutItem> closeoutItems,
        List<BoundaryAssertion> boundaryAssertions,
        List<MarkdownSection> markdownSections,
        List<String> checks,
        String status
) {

    public record CloseoutItem(
            String item,
            String evidence,
            boolean passed
    ) {
    }

    public record BoundaryAssertion(
            String boundary,
            boolean locked,
            String detail
    ) {
    }

    public record MarkdownSection(
            String heading,
            List<String> lines
    ) {
    }
}
