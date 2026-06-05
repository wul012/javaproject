package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int scannedEntryCount,
        int readOnlyViolationCount,
        int executionAllowedViolationCount,
        int upstreamStartupViolationCount,
        int credentialValueViolationCount,
        int rawEndpointViolationCount,
        int managedAuditViolationCount,
        int writeRoutingViolationCount,
        List<String> forbiddenOperations,
        List<String> checks,
        String status
) {
}
