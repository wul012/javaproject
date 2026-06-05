package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int gateCheckCount,
        int acceptedCheckCount,
        int blockedCheckCount,
        int firstServiceVersion,
        int latestRouteVersion,
        List<GateCheck> gateChecks,
        List<String> checks,
        String status
) {

    public record GateCheck(
            String name,
            String sourceEndpoint,
            boolean passed,
            String reason,
            String status
    ) {
    }
}
