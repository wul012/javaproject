package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentController {

    private final OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService releaseChecklistService;

    public OpsShardReadinessRouteCleanupMaintenanceSustainmentController(
            OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService releaseChecklistService
    ) {
        this.releaseChecklistService = releaseChecklistService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RELEASE_CHECKLIST)
    public OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse releaseChecklist() {
        return releaseChecklistService.checklist();
    }
}
