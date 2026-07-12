package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceExtendedCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRouteInventoryDigestService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController {

  private final OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService
      consumerGatePacketService;
  private final OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService
      archiveVerifierSummaryService;
  private final OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService ciBudgetLedgerService;
  private final OpsShardReadinessRouteCleanupMaintenanceRouteInventoryDigestService
      routeInventoryDigestService;
  private final OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService
      operatorSignoffService;
  private final OpsShardReadinessRouteCleanupMaintenanceExtendedCloseoutService
      extendedCloseoutService;

  public OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController(
      OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService consumerGatePacketService,
      OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService
          archiveVerifierSummaryService,
      OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService ciBudgetLedgerService,
      OpsShardReadinessRouteCleanupMaintenanceRouteInventoryDigestService
          routeInventoryDigestService,
      OpsShardReadinessRouteCleanupMaintenanceOperatorSignoffService operatorSignoffService,
      OpsShardReadinessRouteCleanupMaintenanceExtendedCloseoutService extendedCloseoutService) {
    this.consumerGatePacketService = consumerGatePacketService;
    this.archiveVerifierSummaryService = archiveVerifierSummaryService;
    this.ciBudgetLedgerService = ciBudgetLedgerService;
    this.routeInventoryDigestService = routeInventoryDigestService;
    this.operatorSignoffService = operatorSignoffService;
    this.extendedCloseoutService = extendedCloseoutService;
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_CONSUMER_GATE_PACKET)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse consumerGatePacket() {
    return consumerGatePacketService.packet();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_ARCHIVE_VERIFIER_SUMMARY)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse
      archiveVerifierSummary() {
    return archiveVerifierSummaryService.summary();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_CI_BUDGET_LEDGER)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse ciBudgetLedger() {
    return ciBudgetLedgerService.ledger();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_ROUTE_INVENTORY_DIGEST)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse routeInventoryDigest() {
    return routeInventoryDigestService.digest();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_OPERATOR_SIGNOFF)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse operatorSignoff() {
    return operatorSignoffService.signoff();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_EXTENDED_CLOSEOUT)
  public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse extendedCloseout() {
    return extendedCloseoutService.closeout();
  }
}
