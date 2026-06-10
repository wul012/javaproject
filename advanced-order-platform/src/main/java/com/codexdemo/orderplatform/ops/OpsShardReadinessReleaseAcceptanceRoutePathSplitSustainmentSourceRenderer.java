package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.SourceSnapshot> snapshots
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.section(
                "Source Closeout",
                snapshots.stream()
                        .map(snapshot -> "- " + snapshot.source() + " " + snapshot.version()
                                + " status=" + snapshot.status() + " owner=" + snapshot.ownership())
                        .toList()
        );
    }
}
