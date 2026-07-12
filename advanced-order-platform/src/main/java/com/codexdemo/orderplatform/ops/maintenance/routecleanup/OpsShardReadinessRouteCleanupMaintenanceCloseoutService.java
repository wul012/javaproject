package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceCloseoutService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_CLOSEOUT;
  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-closeout.v1";

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

  public OpsShardReadinessRouteCleanupMaintenanceCloseoutService(
      OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService segmentCatalogService,
      OpsShardReadinessRouteCleanupMaintenanceContinuityService continuityService,
      OpsShardReadinessRouteCleanupMaintenanceLatestSiblingService latestSiblingService,
      OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditService handoffPairAuditService,
      OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftService boundaryDriftService,
      OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentService sourcePlanAlignmentService,
      OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanService testBudgetPlanService,
      OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService archiveManifestService) {
    this.segmentCatalogService = segmentCatalogService;
    this.continuityService = continuityService;
    this.latestSiblingService = latestSiblingService;
    this.handoffPairAuditService = handoffPairAuditService;
    this.boundaryDriftService = boundaryDriftService;
    this.sourcePlanAlignmentService = sourcePlanAlignmentService;
    this.testBudgetPlanService = testBudgetPlanService;
    this.archiveManifestService = archiveManifestService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse closeout() {
    OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogResponse segmentCatalog =
        segmentCatalogService.catalog();
    OpsShardReadinessRouteCleanupMaintenanceContinuityResponse continuity =
        continuityService.continuity();
    OpsShardReadinessRouteCleanupMaintenanceLatestSiblingResponse latestSibling =
        latestSiblingService.report();
    OpsShardReadinessRouteCleanupMaintenanceHandoffPairAuditResponse pairAudit =
        handoffPairAuditService.audit();
    OpsShardReadinessRouteCleanupMaintenanceBoundaryDriftResponse boundaryDrift =
        boundaryDriftService.audit();
    OpsShardReadinessRouteCleanupMaintenanceSourcePlanAlignmentResponse sourcePlan =
        sourcePlanAlignmentService.alignment();
    OpsShardReadinessRouteCleanupMaintenanceTestBudgetPlanResponse testBudget =
        testBudgetPlanService.plan();
    OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse archiveManifest =
        archiveManifestService.manifest();
    List<OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse.CloseoutCheck> checks =
        List.of(
            check("segment-catalog", segmentCatalog.status()),
            check("continuity", continuity.status()),
            check("latest-sibling-report", latestSibling.status()),
            check("handoff-pair-audit", pairAudit.status()),
            check("boundary-drift", boundaryDrift.status()),
            check("source-plan-alignment", sourcePlan.status()),
            check("test-budget-plan", testBudget.status()),
            check("archive-manifest", archiveManifest.status()));
    return new OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse(
        "advanced-order-platform",
        "Java v487",
        true,
        false,
        ENDPOINT,
        PROFILE,
        sourcePlan.sourcePlan(),
        checks.size(),
        segmentCatalog.segmentCount(),
        archiveManifest.artifactCount(),
        checks,
        checks.stream().allMatch(check -> "passed".equals(check.status())) ? "passed" : "blocked");
  }

  private OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse.CloseoutCheck check(
      String name, String status) {
    return new OpsShardReadinessRouteCleanupMaintenanceCloseoutResponse.CloseoutCheck(name, status);
  }
}
