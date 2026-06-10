package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .SourceSnapshot> snapshots
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport.section(
                "Source Sustainment",
                snapshots.stream()
                        .map(snapshot -> "- " + snapshot.source() + " " + snapshot.version()
                                + " status=" + snapshot.status() + " profile=" + snapshot.profile())
                        .toList()
        );
    }
}
