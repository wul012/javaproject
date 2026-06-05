package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceController {

    private final OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService;

    private final OpsShardReadinessRouteCleanupMaintenanceContinuityService continuityService;

    public OpsShardReadinessRouteCleanupMaintenanceController(
            OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService,
            OpsShardReadinessRouteCleanupMaintenanceContinuityService continuityService
    ) {
        this.segmentCatalogService = segmentCatalogService;
        this.continuityService = continuityService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SEGMENT_CATALOG)
    public OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse segmentCatalog() {
        return segmentCatalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONTINUITY)
    public OpsShardReadinessRouteCleanupMaintenanceContinuityResponse continuity() {
        return continuityService.continuity();
    }
}
