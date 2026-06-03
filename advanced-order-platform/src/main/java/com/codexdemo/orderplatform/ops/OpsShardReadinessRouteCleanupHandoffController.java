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

    private final OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService;

    private final OpsShardReadinessRouteCleanupHandoffBundleService handoffBundleService;

    private final OpsShardReadinessRouteCleanupConsumerChecklistService consumerChecklistService;

    public OpsShardReadinessRouteCleanupHandoffController(
            OpsShardReadinessRouteCleanupHandoffChecklistService handoffChecklistService,
            OpsShardReadinessRouteCleanupArchivePlanService archivePlanService,
            OpsShardReadinessRouteCleanupReleaseHandoffService releaseHandoffService,
            OpsShardReadinessRouteCleanupSuiteCloseoutService suiteCloseoutService,
            OpsShardReadinessRouteCleanupArchiveVerificationService archiveVerificationService,
            OpsShardReadinessRouteCleanupConsumerPacketService consumerPacketService,
            OpsShardReadinessRouteCleanupHandoffBundleService handoffBundleService,
            OpsShardReadinessRouteCleanupConsumerChecklistService consumerChecklistService
    ) {
        this.handoffChecklistService = handoffChecklistService;
        this.archivePlanService = archivePlanService;
        this.releaseHandoffService = releaseHandoffService;
        this.suiteCloseoutService = suiteCloseoutService;
        this.archiveVerificationService = archiveVerificationService;
        this.consumerPacketService = consumerPacketService;
        this.handoffBundleService = handoffBundleService;
        this.consumerChecklistService = consumerChecklistService;
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

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONSUMER_PACKET)
    public OpsShardReadinessRouteCleanupConsumerPacketResponse consumerPacket() {
        return consumerPacketService.packet();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_HANDOFF_BUNDLE)
    public OpsShardReadinessRouteCleanupHandoffBundleResponse handoffBundle() {
        return handoffBundleService.bundle();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_CONSUMER_CHECKLIST)
    public OpsShardReadinessRouteCleanupConsumerChecklistResponse consumerChecklist() {
        return consumerChecklistService.checklist();
    }
}
