package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReadinessGateService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceVersionLineageService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceUpkeepAssuranceController {

  private final OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService
      archiveDigestLedgerService;

  private final OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService
      operatorReviewPacketService;

  private final OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService;

  private final OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService;

  private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService upkeepCloseoutService;

  public OpsShardReadinessRouteCleanupMaintenanceUpkeepAssuranceController(
      OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService archiveDigestLedgerService,
      OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService
          operatorReviewPacketService,
      OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService,
      OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService,
      OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService upkeepCloseoutService) {
    this.archiveDigestLedgerService = archiveDigestLedgerService;
    this.operatorReviewPacketService = operatorReviewPacketService;
    this.versionLineageService = versionLineageService;
    this.readinessGateService = readinessGateService;
    this.upkeepCloseoutService = upkeepCloseoutService;
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_ARCHIVE_DIGEST_LEDGER)
  public OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse archiveDigestLedger() {
    return archiveDigestLedgerService.ledger();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_OPERATOR_REVIEW_PACKET)
  public OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse
      operatorReviewPacket() {
    return operatorReviewPacketService.packet();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_VERSION_LINEAGE)
  public OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse versionLineage() {
    return versionLineageService.lineage();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_READINESS_GATE)
  public OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse readinessGate() {
    return readinessGateService.gate();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_UPKEEP_CLOSEOUT)
  public OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse upkeepCloseout() {
    return upkeepCloseoutService.closeout();
  }
}
