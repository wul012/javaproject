package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController {

    private final OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService consumerGatePacketService;
    private final OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService archiveVerifierSummaryService;
    private final OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService ciBudgetLedgerService;

    public OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController(
            OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService consumerGatePacketService,
            OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService archiveVerifierSummaryService,
            OpsShardReadinessRouteCleanupMaintenanceCiBudgetLedgerService ciBudgetLedgerService
    ) {
        this.consumerGatePacketService = consumerGatePacketService;
        this.archiveVerifierSummaryService = archiveVerifierSummaryService;
        this.ciBudgetLedgerService = ciBudgetLedgerService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONSUMER_GATE_PACKET)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse consumerGatePacket() {
        return consumerGatePacketService.packet();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_VERIFIER_SUMMARY)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse archiveVerifierSummary() {
        return archiveVerifierSummaryService.summary();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CI_BUDGET_LEDGER)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse ciBudgetLedger() {
        return ciBudgetLedgerService.ledger();
    }
}
