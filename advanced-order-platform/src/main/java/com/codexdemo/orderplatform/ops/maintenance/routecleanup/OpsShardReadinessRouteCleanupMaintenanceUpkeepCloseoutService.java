package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService {

  static final String ENDPOINT =
      RouteCleanupRoutes.BASE_PATH + RouteCleanupRoutes.MAINTENANCE_UPKEEP_CLOSEOUT;
  static final String PROFILE = "java-shard-readiness-route-cleanup-maintenance-upkeep-closeout.v1";

  private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService;
  private final OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService
      operatorReviewPacketService;
  private final OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService;
  private final OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService;
  private final OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService
      archiveDigestLedgerService;

  public OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService(
      OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogService upkeepCatalogService,
      OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService
          operatorReviewPacketService,
      OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService,
      OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService,
      OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService
          archiveDigestLedgerService) {
    this.upkeepCatalogService = upkeepCatalogService;
    this.operatorReviewPacketService = operatorReviewPacketService;
    this.readinessGateService = readinessGateService;
    this.versionLineageService = versionLineageService;
    this.archiveDigestLedgerService = archiveDigestLedgerService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse closeout() {
    OpsShardReadinessRouteCleanupMaintenanceUpkeepCatalogResponse catalog =
        upkeepCatalogService.catalog();
    OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse review =
        operatorReviewPacketService.packet();
    OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse gate =
        readinessGateService.gate();
    OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse lineage =
        versionLineageService.lineage();
    OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse ledger =
        archiveDigestLedgerService.ledger();
    List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse.CloseoutCheck>
        closeoutChecks =
            List.of(
                check("upkeep-catalog", catalog.endpoint(), catalog.status()),
                check("operator-review-packet", review.endpoint(), review.status()),
                check("readiness-gate", gate.endpoint(), gate.status()),
                check("version-lineage", lineage.endpoint(), lineage.status()),
                check("archive-digest-ledger", ledger.endpoint(), ledger.status()));
    return new OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse(
        "advanced-order-platform",
        "Java v507",
        true,
        false,
        ENDPOINT,
        PROFILE,
        "Node v549",
        closeoutChecks.size(),
        catalog.itemCount(),
        gate.gateCheckCount(),
        ledger.ledgerEntryCount(),
        lineage.latestRouteVersion(),
        closeoutChecks,
        status(closeoutChecks));
  }

  private OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse.CloseoutCheck check(
      String name, String sourceEndpoint, String status) {
    return new OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse.CloseoutCheck(
        name, sourceEndpoint, status);
  }

  private String status(
      List<OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse.CloseoutCheck> checks) {
    return checks.stream().allMatch(check -> "passed".equals(check.status()))
        ? "passed"
        : "blocked";
  }
}
