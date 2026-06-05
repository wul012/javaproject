package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        String sourcePlan,
        int checkedReportCount,
        int upkeepItemCount,
        int gateCheckCount,
        int archiveDigestCount,
        int latestRouteVersion,
        List<CloseoutCheck> checks,
        String status
) {

    public record CloseoutCheck(
            String name,
            String sourceEndpoint,
            String status
    ) {
    }
}
