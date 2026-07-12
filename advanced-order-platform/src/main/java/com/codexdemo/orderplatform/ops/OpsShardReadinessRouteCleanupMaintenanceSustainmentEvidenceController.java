package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentEvidenceController {

  private final OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService
      handoffDigestService;
  private final OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService
      dependencyBoundaryMapService;
  private final OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService
      archiveRetentionService;
  private final OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService
      testEvidenceRollupService;
  private final OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService
      operationsScorecardService;
  private final OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService
      sustainmentCloseoutService;

  public OpsShardReadinessRouteCleanupMaintenanceSustainmentEvidenceController(
      OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestService handoffDigestService,
      OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapService
          dependencyBoundaryMapService,
      OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarService
          archiveRetentionService,
      OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupService testEvidenceRollupService,
      OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardService operationsScorecardService,
      OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutService
          sustainmentCloseoutService) {
    this.handoffDigestService = handoffDigestService;
    this.dependencyBoundaryMapService = dependencyBoundaryMapService;
    this.archiveRetentionService = archiveRetentionService;
    this.testEvidenceRollupService = testEvidenceRollupService;
    this.operationsScorecardService = operationsScorecardService;
    this.sustainmentCloseoutService = sustainmentCloseoutService;
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_HANDOFF_ACCEPTANCE_DIGEST)
  public OpsShardReadinessRouteCleanupMaintenanceHandoffAcceptanceDigestResponse
      handoffAcceptanceDigest() {
    return handoffDigestService.digest();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_DEPENDENCY_BOUNDARY_MAP)
  public OpsShardReadinessRouteCleanupMaintenanceDependencyBoundaryMapResponse
      dependencyBoundaryMap() {
    return dependencyBoundaryMapService.map();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_ARCHIVE_RETENTION_CALENDAR)
  public OpsShardReadinessRouteCleanupMaintenanceArchiveRetentionCalendarResponse
      archiveRetentionCalendar() {
    return archiveRetentionService.calendar();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_TEST_EVIDENCE_ROLLUP)
  public OpsShardReadinessRouteCleanupMaintenanceTestEvidenceRollupResponse testEvidenceRollup() {
    return testEvidenceRollupService.rollup();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_OPERATIONS_SCORECARD)
  public OpsShardReadinessRouteCleanupMaintenanceOperationsScorecardResponse operationsScorecard() {
    return operationsScorecardService.scorecard();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_SUSTAINMENT_CLOSEOUT)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentCloseoutResponse sustainmentCloseout() {
    return sustainmentCloseoutService.closeout();
  }
}
