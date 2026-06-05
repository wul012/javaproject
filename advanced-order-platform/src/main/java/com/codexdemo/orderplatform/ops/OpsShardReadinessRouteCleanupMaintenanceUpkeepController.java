package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceUpkeepController {

    private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService;

    public OpsShardReadinessRouteCleanupMaintenanceUpkeepController(
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService
    ) {
        this.upkeepCatalogService = upkeepCatalogService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_UPKEEP_CATALOG)
    public OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse upkeepCatalog() {
        return upkeepCatalogService.catalog();
    }
}
