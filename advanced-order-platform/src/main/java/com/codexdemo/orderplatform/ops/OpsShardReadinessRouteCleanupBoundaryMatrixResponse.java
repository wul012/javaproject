package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupBoundaryMatrixResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String matrixEndpoint,
        String matrixProfile,
        int ruleCount,
        List<BoundaryRule> rules,
        String status
) {

    public record BoundaryRule(
            String operation,
            boolean allowed,
            String evidence,
            String status
    ) {
    }
}
