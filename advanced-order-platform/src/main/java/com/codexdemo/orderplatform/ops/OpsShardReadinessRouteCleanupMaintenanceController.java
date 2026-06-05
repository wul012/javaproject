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

    private final OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService boundaryDriftService;

    private final OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService sourcePlanAlignmentService;

    private final OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService testBudgetPlanService;

    public OpsShardReadinessRouteCleanupMaintenanceController(
            OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService,
            OpsShardReadinessRouteCleanupMaintenanceContinuityService continuityService,
            OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService latestSiblingService,
            OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService handoffPairAuditService,
            OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService boundaryDriftService,
            OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService sourcePlanAlignmentService,
            OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService testBudgetPlanService
    ) {
        this.segmentCatalogService = segmentCatalogService;
        this.continuityService = continuityService;
        this.latestSiblingService = latestSiblingService;
        this.handoffPairAuditService = handoffPairAuditService;
        this.boundaryDriftService = boundaryDriftService;
        this.sourcePlanAlignmentService = sourcePlanAlignmentService;
        this.testBudgetPlanService = testBudgetPlanService;
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

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_BOUNDARY_DRIFT)
    public OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse boundaryDrift() {
        return boundaryDriftService.audit();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_SOURCE_PLAN_ALIGNMENT)
    public OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse sourcePlanAlignment() {
        return sourcePlanAlignmentService.alignment();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_TEST_BUDGET_PLAN)
    public OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse testBudgetPlan() {
        return testBudgetPlanService.plan();
    }
}
