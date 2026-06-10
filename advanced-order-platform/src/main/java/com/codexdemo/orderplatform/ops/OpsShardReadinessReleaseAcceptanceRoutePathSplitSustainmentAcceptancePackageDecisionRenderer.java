package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageDecisionRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageDecisionRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .DecisionRecord> decisions
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport.section(
                "Acceptance Decisions",
                decisions.stream()
                        .map(decision -> "- " + decision.decision() + " owner=" + decision.owner()
                                + " accepted=" + decision.accepted())
                        .toList()
        );
    }
}
