package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceUpkeepController {

    private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService;

    private final OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService consumerHandoffMatrixService;

    public OpsShardReadinessRouteCleanupMaintenanceUpkeepController(
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService,
            OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService consumerHandoffMatrixService
    ) {
        this.upkeepCatalogService = upkeepCatalogService;
        this.consumerHandoffMatrixService = consumerHandoffMatrixService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_UPKEEP_CATALOG)
    public OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse upkeepCatalog() {
        return upkeepCatalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONSUMER_HANDOFF_MATRIX)
    public OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse consumerHandoffMatrix() {
        return consumerHandoffMatrixService.matrix();
    }
}
