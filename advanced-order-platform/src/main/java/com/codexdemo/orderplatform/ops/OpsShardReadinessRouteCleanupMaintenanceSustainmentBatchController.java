package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController {

    private final OpsShardReadinessRouteCleanupMaintenanceContractFreezeService contractFreezeService;
    private final OpsShardReadinessRouteCleanupMaintenanceGateHandoffService gateHandoffService;

    public OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController(
            OpsShardReadinessRouteCleanupMaintenanceContractFreezeService contractFreezeService,
            OpsShardReadinessRouteCleanupMaintenanceGateHandoffService gateHandoffService
    ) {
        this.contractFreezeService = contractFreezeService;
        this.gateHandoffService = gateHandoffService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONTRACT_FREEZE)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse contractFreeze() {
        return contractFreezeService.freeze();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_GATE_HANDOFF)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse gateHandoff() {
        return gateHandoffService.handoff();
    }
}
