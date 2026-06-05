package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController {

    private final OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService consumerGatePacketService;

    public OpsShardReadinessRouteCleanupMaintenanceAssuranceBatchController(
            OpsShardReadinessRouteCleanupMaintenanceConsumerGatePacketService consumerGatePacketService
    ) {
        this.consumerGatePacketService = consumerGatePacketService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONSUMER_GATE_PACKET)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse consumerGatePacket() {
        return consumerGatePacketService.packet();
    }
}
