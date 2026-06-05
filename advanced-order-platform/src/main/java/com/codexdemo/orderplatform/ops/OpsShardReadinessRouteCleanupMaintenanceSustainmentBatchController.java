package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController {

    private final OpsShardReadinessRouteCleanupMaintenanceContractFreezeService contractFreezeService;

    public OpsShardReadinessRouteCleanupMaintenanceSustainmentBatchController(
            OpsShardReadinessRouteCleanupMaintenanceContractFreezeService contractFreezeService
    ) {
        this.contractFreezeService = contractFreezeService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_CONTRACT_FREEZE)
    public OpsShardReadinessRouteCleanupMaintenanceSustainmentReviewResponse contractFreeze() {
        return contractFreezeService.freeze();
    }
}
