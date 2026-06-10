package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard> guards
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.section(
                "Drift Guards",
                guards.stream()
                        .map(guard -> "- " + guard.guard() + " signal=" + guard.signal()
                                + " expected=" + guard.expected() + " locked=" + guard.locked())
                        .toList()
        );
    }
}
