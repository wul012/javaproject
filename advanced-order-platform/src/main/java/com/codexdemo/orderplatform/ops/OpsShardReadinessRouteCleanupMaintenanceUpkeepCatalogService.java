package com.codexdemo.orderplatform.ops;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService {

    static final String ENDPOINT =
            OpsShardReadinessRoutePaths.BASE_PATH
                    + OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_UPKEEP_CATALOG;
    static final String PROFILE =
            "java-shard-readiness-route-cleanup-maintenance-upkeep-catalog.v1";

    @Transactional(readOnly = true)
    public OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse catalog() {
        List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse.UpkeepItem> items =
                OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.items().stream()
                        .map(this::item)
                        .toList();
        List<String> checks = List.of(
                "upkeep-item-count-" + items.size(),
                "service-route-version-pairs-present",
                "evidence-paths-are-versioned-json",
                "consumers-and-boundaries-are-explicit",
                "upkeep-catalog-remains-read-only"
        );
        return new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse(
                "advanced-order-platform",
                "Java v489",
                true,
                false,
                ENDPOINT,
                PROFILE,
                items.size(),
                OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.firstServiceVersion(),
                OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.latestRouteVersion(),
                items,
                checks,
                status(items)
        );
    }

    private OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse.UpkeepItem item(
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalog.Item item
    ) {
        return new OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse.UpkeepItem(
                item.name(),
                item.serviceVersion(),
                item.routeVersion(),
                item.endpoint(),
                item.evidencePath(),
                item.consumer(),
                item.boundary(),
                item.status()
        );
    }

    private String status(
            List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse.UpkeepItem> items
    ) {
        boolean passed = items.size() == 9
                && items.stream().allMatch(item -> item.routeVersion() == item.serviceVersion() + 1)
                && items.stream().allMatch(item -> item.evidencePath().endsWith(".json"))
                && items.stream().allMatch(item -> "passed".equals(item.status()));
        return passed ? "passed" : "blocked";
    }
}
