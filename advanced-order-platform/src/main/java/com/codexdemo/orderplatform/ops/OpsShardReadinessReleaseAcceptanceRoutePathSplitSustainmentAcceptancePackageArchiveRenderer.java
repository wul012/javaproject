package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageArchiveRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageArchiveRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .ArchiveItem> archiveItems
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport.section(
                "Archive Items",
                archiveItems.stream()
                        .map(item -> "- " + item.artifact() + " retention=" + item.retention()
                                + " ready=" + item.ready())
                        .toList()
        );
    }
}
