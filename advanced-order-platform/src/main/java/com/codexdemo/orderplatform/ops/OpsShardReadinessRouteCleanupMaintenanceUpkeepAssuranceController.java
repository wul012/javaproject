package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceUpkeepAssuranceController {

    private final OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService archiveDigestLedgerService;

    private final OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService operatorReviewPacketService;

    private final OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService;

    private final OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService;

    private final OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService upkeepCloseoutService;

    public OpsShardReadinessRouteCleanupMaintenanceUpkeepAssuranceController(
            OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerService archiveDigestLedgerService,
            OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketService operatorReviewPacketService,
            OpsShardReadinessRouteCleanupMaintenanceVersionLineageService versionLineageService,
            OpsShardReadinessRouteCleanupMaintenanceReadinessGateService readinessGateService,
            OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutService upkeepCloseoutService
    ) {
        this.archiveDigestLedgerService = archiveDigestLedgerService;
        this.operatorReviewPacketService = operatorReviewPacketService;
        this.versionLineageService = versionLineageService;
        this.readinessGateService = readinessGateService;
        this.upkeepCloseoutService = upkeepCloseoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_DIGEST_LEDGER)
    public OpsShardReadinessRouteCleanupMaintenanceArchiveDigestLedgerResponse archiveDigestLedger() {
        return archiveDigestLedgerService.ledger();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_OPERATOR_REVIEW_PACKET)
    public OpsShardReadinessRouteCleanupMaintenanceOperatorReviewPacketResponse operatorReviewPacket() {
        return operatorReviewPacketService.packet();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_VERSION_LINEAGE)
    public OpsShardReadinessRouteCleanupMaintenanceVersionLineageResponse versionLineage() {
        return versionLineageService.lineage();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_READINESS_GATE)
    public OpsShardReadinessRouteCleanupMaintenanceReadinessGateResponse readinessGate() {
        return readinessGateService.gate();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_UPKEEP_CLOSEOUT)
    public OpsShardReadinessRouteCleanupMaintenanceUpkeepCloseoutResponse upkeepCloseout() {
        return upkeepCloseoutService.closeout();
    }
}
