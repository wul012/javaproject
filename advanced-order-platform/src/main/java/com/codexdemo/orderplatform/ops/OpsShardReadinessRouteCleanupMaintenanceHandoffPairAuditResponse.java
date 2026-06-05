package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int handoffEntryCount,
        int serviceEntryCount,
        int routeEntryCount,
        int pairedRouteCount,
        List<String> documentedRouteOnlyEntries,
        List<String> unpairedServiceEntries,
        List<String> checks,
        String status
) {
}
