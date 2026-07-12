package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService;
import com.codexdemo.orderplatform.ops.maintenance.routecleanup.RouteCleanupRoutes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RouteCleanupRoutes.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentController {

  private final OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService
      releaseChecklistService;
  private final OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService
      remediationQueueService;
  private final OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService
      freshnessWindowService;
  private final OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService
      ownershipRegisterService;
  private final OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService riskLedgerService;

  public OpsShardReadinessRouteCleanupMaintenanceSustainmentController(
      OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService releaseChecklistService,
      OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService remediationQueueService,
      OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService freshnessWindowService,
      OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterService ownershipRegisterService,
      OpsShardReadinessRouteCleanupMaintenanceRiskLedgerService riskLedgerService) {
    this.releaseChecklistService = releaseChecklistService;
    this.remediationQueueService = remediationQueueService;
    this.freshnessWindowService = freshnessWindowService;
    this.ownershipRegisterService = ownershipRegisterService;
    this.riskLedgerService = riskLedgerService;
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_RELEASE_CHECKLIST)
  public OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse releaseChecklist() {
    return releaseChecklistService.checklist();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_REMEDIATION_QUEUE)
  public OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse remediationQueue() {
    return remediationQueueService.queue();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_FRESHNESS_WINDOW)
  public OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse freshnessWindow() {
    return freshnessWindowService.window();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_OWNERSHIP_REGISTER)
  public OpsShardReadinessRouteCleanupMaintenanceOwnershipRegisterResponse ownershipRegister() {
    return ownershipRegisterService.register();
  }

  @GetMapping(RouteCleanupRoutes.MAINTENANCE_RISK_LEDGER)
  public OpsShardReadinessRouteCleanupMaintenanceRiskLedgerResponse riskLedger() {
    return riskLedgerService.ledger();
  }
}
