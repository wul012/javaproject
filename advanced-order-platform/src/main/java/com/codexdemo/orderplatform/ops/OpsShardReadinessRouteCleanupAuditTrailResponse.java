package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupAuditTrailResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String auditTrailEndpoint,
        String auditTrailProfile,
        int checkpointCount,
        List<AuditCheckpoint> checkpoints,
        String sourcePlan,
        String status
) {

    public record AuditCheckpoint(
            String name,
            String evidence,
            String owner,
            boolean readOnly,
            boolean executionAllowed,
            String status
    ) {
    }
}
