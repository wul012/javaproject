package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceContractFreezeService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceGateHandoffService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController {

  private final OpsShardReadinessRouteCleanupMaintenanceContractFreezeService contractFreezeService;
  private final OpsShardReadinessRouteCleanupMaintenanceGateHandoffService gateHandoffService;
  private final OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService shardFieldMapService;
  private final OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService
      readWindowEvidenceService;
  private final OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService
      runtimeBoundaryChecklistService;

  public OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController(
      OpsShardReadinessRouteCleanupMaintenanceContractFreezeService contractFreezeService,
      OpsShardReadinessRouteCleanupMaintenanceGateHandoffService gateHandoffService,
      OpsShardReadinessRouteCleanupMaintenanceShardFieldMapService shardFieldMapService,
      OpsShardReadinessRouteCleanupMaintenanceReadWindowEvidenceService readWindowEvidenceService,
      OpsShardReadinessRouteCleanupMaintenanceRuntimeBoundaryChecklistService
          runtimeBoundaryChecklistService) {
    this.contractFreezeService = contractFreezeService;
    this.gateHandoffService = gateHandoffService;
    this.shardFieldMapService = shardFieldMapService;
    this.readWindowEvidenceService = readWindowEvidenceService;
    this.runtimeBoundaryChecklistService = runtimeBoundaryChecklistService;
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_CONTRACT_FREEZE)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse contractFreeze() {
    return contractFreezeService.freeze();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_GATE_HANDOFF)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse gateHandoff() {
    return gateHandoffService.handoff();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_SHARD_FIELD_MAP)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse shardFieldMap() {
    return shardFieldMapService.fieldMap();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_READ_WINDOW_EVIDENCE)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse readWindowEvidence() {
    return readWindowEvidenceService.evidence();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_RUNTIME_BOUNDARY_CHECKLIST)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse
      runtimeBoundaryChecklist() {
    return runtimeBoundaryChecklistService.checklist();
  }
}
