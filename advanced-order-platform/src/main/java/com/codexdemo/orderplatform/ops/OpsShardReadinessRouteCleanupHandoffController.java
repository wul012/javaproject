package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupHandoffController {

    private final OpsShardReadinessRouteCleanupHandoffChecklistService handoffChecklistService;

    private final OpsShardReadinessRouteCleanupArchivePlanService archivePlanService;

    private final OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService;

    private final OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService;

    private final OpsShardReadinessRouteCleanupArchiveVerificationService archiveVerificationService;

    public OpsShardReadinessRouteCleanupHandoffController(
            OpsShardReadinessRouteCleanupHandoffChecklistService handoffChecklistService,
            OpsShardReadinessRouteCleanupArchivePlanService archivePlanService,
            OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService,
            OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService,
            OpsShardReadinessRouteCleanupArchiveVerificationService archiveVerificationService
    ) {
        this.handoffChecklistService = handoffChecklistService;
        this.archivePlanService = archivePlanService;
        this.releaseHandoffService = releaseHandoffService;
        this.suiteCloseoutService = suiteCloseoutService;
        this.archiveVerificationService = archiveVerificationService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_HANDOFF_CHECKLIST)
    public OpsShardReadinessRouteCleanupHandoffChecklistResponse handoffChecklist() {
        return handoffChecklistService.checklist();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_PLAN)
    public OpsShardReadinessRouteCleanupArchivePlanResponse archivePlan() {
        return archivePlanService.plan();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_RELEASE_HANDOFF)
    public OpsShardReadinessRouteCleanupReleaseHandoffResponse releaseHandoff() {
        return releaseHandoffService.handoff();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_SUITE_CLOSEOUT)
    public OpsShardReadinessRouteCleanupSuiteCloseoutResponse suiteCloseout() {
        return suiteCloseoutService.closeout();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_ARCHIVE_VERIFICATION)
    public OpsShardReadinessRouteCleanupArchiveVerificationResponse archiveVerification() {
        return archiveVerificationService.verification();
    }
}
