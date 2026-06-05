package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int closeoutItemCount,
        int passedItemCount,
        int finalScore,
        String sourcePlan,
        List<CloseoutItem> items,
        List<String> checks,
        String status
) {

    public record CloseoutItem(
            String name,
            String sourceEndpoint,
            String evidence,
            String status
    ) {
    }
}
