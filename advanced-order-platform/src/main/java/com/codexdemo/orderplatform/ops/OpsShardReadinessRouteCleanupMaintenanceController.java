package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceController {

    private final OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService;

    private final OpsShardReadinessRouteCleanupMaintenanceContinuityService continuityService;

    private final OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService latestSiblingService;

    private final OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService handoffPairAuditService;

    public OpsShardReadinessRouteCleanupMaintenanceController(
            OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService,
            OpsShardReadinessRouteCleanupMaintenanceContinuityService continuityService,
            OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService latestSiblingService,
            OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService handoffPairAuditService
    ) {
        this.segmentCatalogService = segmentCatalogService;
        this.continuityService = continuityService;
        this.latestSiblingService = latestSiblingService;
        this.handoffPairAuditService = handoffPairAuditService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SEGMENT_CATALOG)
    public OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse segmentCatalog() {
        return segmentCatalogService.catalog();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONTINUITY)
    public OpsShardReadinessRouteCleanupMaintenanceContinuityResponse continuity() {
        return continuityService.continuity();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_LATEST_SIBLING_REPORT)
    public OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse latestSiblingReport() {
        return latestSiblingService.report();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_HANDOFF_PAIR_AUDIT)
    public OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse handoffPairAudit() {
        return handoffPairAuditService.audit();
    }
}
