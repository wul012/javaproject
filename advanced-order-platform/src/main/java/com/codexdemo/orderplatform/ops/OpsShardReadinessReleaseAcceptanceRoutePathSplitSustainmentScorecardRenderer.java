package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ScorecardEntry> scorecard
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.section(
                "Sustainment Scorecard",
                scorecard.stream()
                        .map(entry -> "- " + entry.category() + " passed=" + entry.passed()
                                + " detail=" + entry.detail())
                        .toList()
        );
    }
}
