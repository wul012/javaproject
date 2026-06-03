package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupCompletionController {

    private final OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService;

    private final OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService;

    private final OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService;

    private final OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService;

    private final OpsShardReadinessRouteCleanupThirdRunCloseoutService thirdRunCloseoutService;

    public OpsShardReadinessRouteCleanupCompletionController(
            OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService,
            OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService,
            OpsShardReadinessRouteCleanupFinalVerificationService finalVerificationService,
            OpsShardReadinessRouteCleanupFinalArchivePlanService finalArchivePlanService,
            OpsShardReadinessRouteCleanupThirdRunCloseoutService thirdRunCloseoutService
    ) {
        this.reviewerPacketService = reviewerPacketService;
        this.transitionBriefService = transitionBriefService;
        this.finalVerificationService = finalVerificationService;
        this.finalArchivePlanService = finalArchivePlanService;
        this.thirdRunCloseoutService = thirdRunCloseoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_REVIEWER_PACKET)
    public OpsShardReadinessRouteCleanupReviewerPacketResponse reviewerPacket() {
        return reviewerPacketService.packet();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_TRANSITION_BRIEF)
    public OpsShardReadinessRouteCleanupTransitionBriefResponse transitionBrief() {
        return transitionBriefService.brief();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FINAL_VERIFICATION)
    public OpsShardReadinessRouteCleanupFinalVerificationResponse finalVerification() {
        return finalVerificationService.verification();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_FINAL_ARCHIVE_PLAN)
    public OpsShardReadinessRouteCleanupFinalArchivePlanResponse finalArchivePlan() {
        return finalArchivePlanService.plan();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_THIRD_RUN_CLOSEOUT)
    public OpsShardReadinessRouteCleanupThirdRunCloseoutResponse thirdRunCloseout() {
        return thirdRunCloseoutService.closeout();
    }
}
