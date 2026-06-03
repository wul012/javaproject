package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupReleaseHandoffResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String releaseHandoffEndpoint,
        String handoffProfile,
        String checklistEndpoint,
        String archivePlanEndpoint,
        String digestEndpoint,
        String sourcePlanAlignmentEndpoint,
        int handoffItemCount,
        List<HandoffItem> handoffItems,
        String status
) {

    public record HandoffItem(
            String name,
            String evidence,
            String status
    ) {
    }
}
