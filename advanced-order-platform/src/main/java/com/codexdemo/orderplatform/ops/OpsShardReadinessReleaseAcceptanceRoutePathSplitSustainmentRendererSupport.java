package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection section(
            String heading,
            List<String> lines
    ) {
        return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection(
                heading,
                List.copyOf(lines)
        );
    }
}
