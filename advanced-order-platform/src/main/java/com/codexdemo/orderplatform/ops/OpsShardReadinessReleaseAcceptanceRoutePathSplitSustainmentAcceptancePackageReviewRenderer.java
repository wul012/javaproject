package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageReviewRenderer {

    private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageReviewRenderer() {
    }

    static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
            .MarkdownSection render(
            List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                    .ReviewItem> reviewItems
    ) {
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport.section(
                "Review Checklist",
                reviewItems.stream()
                        .map(item -> "- " + item.reviewer() + " checklist=" + item.checklist()
                                + " passed=" + item.passed())
                        .toList()
        );
    }
}
