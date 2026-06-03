package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupHandoffController {

    private final OpsShardReadinessRouteCleanupHandoffChecklistService handoffChecklistService;

    private final OpsShardReadinessRouteCleanupArchivePlanService archivePlanService;

    public OpsShardReadinessRouteCleanupHandoffController(
            OpsShardReadinessRouteCleanupHandoffChecklistService handoffChecklistService,
            OpsShardReadinessRouteCleanupArchivePlanService archivePlanService
    ) {
        this.handoffChecklistService = handoffChecklistService;
        this.archivePlanService = archivePlanService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_HANDOFF_CHECKLIST)
    public OpsShardReadinessRouteCleanupHandoffChecklistResponse handoffChecklist() {
        return handoffChecklistService.checklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_PLAN)
    public OpsShardReadinessRouteCleanupArchivePlanResponse archivePlan() {
        return archivePlanService.plan();
    }
}
