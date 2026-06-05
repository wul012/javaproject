package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int itemCount,
        int passedItemCount,
        String sourcePlan,
        List<ReviewItem> items,
        List<String> checks,
        String status
) {

    public record ReviewItem(
            String name,
            String owner,
            String evidence,
            String sourceEndpoint,
            String status
    ) {
    }
}
