package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupOperatorRunbookResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String runbookEndpoint,
        String runbookProfile,
        int stepCount,
        List<RunbookStep> steps,
        List<String> blockedOperations,
        String status
) {

    public record RunbookStep(
            int order,
            String name,
            String action,
            boolean allowed,
            String evidence,
            String status
    ) {
    }
}
