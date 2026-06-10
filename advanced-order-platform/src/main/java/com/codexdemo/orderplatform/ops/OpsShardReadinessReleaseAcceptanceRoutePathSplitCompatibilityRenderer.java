package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck> checks
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport.section(
                "Compatibility Checks",
                checks.stream()
                        .map(check -> "- " + check.check() + " matched=" + check.matched())
                        .toList()
        );
    }
}
