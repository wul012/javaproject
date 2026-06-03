package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupRegressionGuardResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String guardProfile,
        int guardCount,
        List<GuardCheck> guards,
        String status
) {

    public record GuardCheck(
            String name,
            boolean passed,
            String evidence,
            String status
    ) {
    }
}
