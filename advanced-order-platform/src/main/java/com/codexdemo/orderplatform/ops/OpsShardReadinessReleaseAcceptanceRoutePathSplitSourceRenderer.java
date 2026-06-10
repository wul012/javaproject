package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot> snapshots
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport.section(
                "Source Handoff",
                snapshots.stream()
                        .map(snapshot -> "- " + snapshot.source() + " " + snapshot.version()
                                + " status=" + snapshot.status())
                        .toList()
        );
    }
}
