package com.codexdemo.orderplatform.ops;

import java.util.List;

public record OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse(
        String project,
        String version,
        boolean readOnly,
        boolean executionAllowed,
        String endpoint,
        String profile,
        int itemCount,
        int firstServiceVersion,
        int latestRouteVersion,
        List<UpkeepItem> items,
        List<String> checks,
        String status
) {

    public record UpkeepItem(
            String name,
            int serviceVersion,
            int routeVersion,
            String endpoint,
            String evidencePath,
            String consumer,
            String boundary,
            String status
    ) {
    }
}
