package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController {

    private final OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService consumerGatePacketService;
    private final OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService archiveVerifierSummaryService;

    public OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController(
            OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService consumerGatePacketService,
            OpsShardReadinessRouteCleanupMaintenanceArchiveVerifierSummaryService archiveVerifierSummaryService
    ) {
        this.consumerGatePacketService = consumerGatePacketService;
        this.archiveVerifierSummaryService = archiveVerifierSummaryService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONSUMER_GATE_PACKET)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse consumerGatePacket() {
        return consumerGatePacketService.packet();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_ARCHIVE_VERIFIER_SUMMARY)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse archiveVerifierSummary() {
        return archiveVerifierSummaryService.summary();
    }
}
