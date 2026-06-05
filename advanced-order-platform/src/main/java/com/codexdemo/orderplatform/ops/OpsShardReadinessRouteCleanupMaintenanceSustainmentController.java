package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupMaintenanceSustainmentController {

    private final OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService releaseChecklistService;

    private final OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService remediationQueueService;

    private final OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService freshnessWindowService;

    public OpsShardReadinessRouteCleanupMaintenanceSustainmentController(
            OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistService releaseChecklistService,
            OpsShardReadinessRouteCleanupMaintenanceRemediationQueueService remediationQueueService,
            OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowService freshnessWindowService
    ) {
        this.releaseChecklistService = releaseChecklistService;
        this.remediationQueueService = remediationQueueService;
        this.freshnessWindowService = freshnessWindowService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_RELEASE_CHECKLIST)
    public OpsShardReadinessRouteCleanupMaintenanceReleaseChecklistResponse releaseChecklist() {
        return releaseChecklistService.checklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_REMEDIATION_QUEUE)
    public OpsShardReadinessRouteCleanupMaintenanceRemediationQueueResponse remediationQueue() {
        return remediationQueueService.queue();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_MAINTENANCE_FRESHNESS_WINDOW)
    public OpsShardReadinessRouteCleanupMaintenanceFreshnessWindowResponse freshnessWindow() {
        return freshnessWindowService.window();
    }
}
