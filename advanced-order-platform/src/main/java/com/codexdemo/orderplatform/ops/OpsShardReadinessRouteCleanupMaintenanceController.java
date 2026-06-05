package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceController {

    private final OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService;

    public OpsShardReadinessRouteCleanupMaintenanceController(
            OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService
    ) {
        this.segmentCatalogService = segmentCatalogService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SEGMENT_CATALOG)
    public OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse segmentCatalog() {
        return segmentCatalogService.catalog();
    }
}
