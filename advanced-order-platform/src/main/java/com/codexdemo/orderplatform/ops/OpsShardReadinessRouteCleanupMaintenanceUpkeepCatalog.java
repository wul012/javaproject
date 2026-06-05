package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog {

    private OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog() {
    }

    static List<Item> items() {
        return OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogSeeds.items();
    }

    static int firstServiceVersion() {
        return items().getFirst().serviceVersion();
    }

    static int latestRouteVersion() {
        return items().getLast().routeVersion();
    }

    record Item(
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
