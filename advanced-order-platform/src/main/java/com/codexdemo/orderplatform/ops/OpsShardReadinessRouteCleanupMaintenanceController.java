package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceContinuityResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceContinuityService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceController {

  private final OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService;

  private final OpsShardReadinessRouteCleanupMaintenanceContinuityService continuityService;

  private final OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService latestSiblingService;

  private final OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService
      handoffPairAuditService;

  private final OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService boundaryDriftService;

  private final OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService
      sourcePlanAlignmentService;

  private final OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService testBudgetPlanService;

  private final OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService
      archiveManifestService;

  private final OpsShardReadinessRouteCleanupMaintenanceCloseoutService closeoutService;

  public OpsShardReadinessRouteCleanupMaintenanceController(
      OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService,
      OpsShardReadinessRouteCleanupMaintenanceContinuityService continuityService,
      OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService latestSiblingService,
      OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService handoffPairAuditService,
      OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService boundaryDriftService,
      OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService sourcePlanAlignmentService,
      OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService testBudgetPlanService,
      OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService archiveManifestService,
      OpsShardReadinessRouteCleanupMaintenanceCloseoutService closeoutService) {
    this.segmentCatalogService = segmentCatalogService;
    this.continuityService = continuityService;
    this.latestSiblingService = latestSiblingService;
    this.handoffPairAuditService = handoffPairAuditService;
    this.boundaryDriftService = boundaryDriftService;
    this.sourcePlanAlignmentService = sourcePlanAlignmentService;
    this.testBudgetPlanService = testBudgetPlanService;
    this.archiveManifestService = archiveManifestService;
    this.closeoutService = closeoutService;
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_SEGMENT_CATALOG)
  public OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse segmentCatalog() {
    return segmentCatalogService.catalog();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_CONTINUITY)
  public OpsShardReadinessRouteCleanupMaintenanceContinuityResponse continuity() {
    return continuityService.continuity();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_LATEST_SIBLING_REPORT)
  public OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse latestSiblingReport() {
    return latestSiblingService.report();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_HANDOFF_PAIR_AUDIT)
  public OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse handoffPairAudit() {
    return handoffPairAuditService.audit();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_BOUNDARY_DRIFT)
  public OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse boundaryDrift() {
    return boundaryDriftService.audit();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_SOURCE_PLAN_ALIGNMENT)
  public OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse sourcePlanAlignment() {
    return sourcePlanAlignmentService.alignment();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_TEST_BUDGET_PLAN)
  public OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse testBudgetPlan() {
    return testBudgetPlanService.plan();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_ARCHIVE_MANIFEST)
  public OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse archiveManifest() {
    return archiveManifestService.manifest();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_CLOSEOUT)
  public OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse closeout() {
    return closeoutService.closeout();
  }
}
