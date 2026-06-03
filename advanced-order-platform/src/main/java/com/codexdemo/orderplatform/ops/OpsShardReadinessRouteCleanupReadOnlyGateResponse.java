package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupReadOnlyGateResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String gateEndpoint,
        String gateProfile,
        String releaseHandoffEndpoint,
        String operatorRunbookEndpoint,
        int gateCheckCount,
        List<GateCheck> gateChecks,
        String decision,
        String status
) {

    public record GateCheck(
            String name,
            boolean passed,
            String evidence,
            String status
    ) {
    }
}
