package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry> scorecard
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport.section(
                "Scorecard",
                scorecard.stream()
                        .map(entry -> "- " + entry.category() + " passed=" + entry.passed()
                                + " detail=" + entry.detail())
                        .toList()
        );
    }
}
