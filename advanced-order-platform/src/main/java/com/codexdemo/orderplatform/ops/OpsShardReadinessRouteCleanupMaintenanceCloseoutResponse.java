package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        String sourcePlan,
        int checkedReportCount,
        int segmentCount,
        int archiveArtifactCount,
        List<CloseoutCheck> checks,
        String status
) {

    public record CloseoutCheck(
            String name,
            String status
    ) {
    }
}
