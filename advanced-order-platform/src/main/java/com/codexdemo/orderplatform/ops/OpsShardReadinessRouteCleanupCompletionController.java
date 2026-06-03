package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupCompletionController {

    private final OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService;

    private final OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService;

    public OpsShardReadinessRouteCleanupCompletionController(
            OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService,
            OpsShardReadinessRouteCleanupTransitionBriefService transitionBriefService
    ) {
        this.reviewerPacketService = reviewerPacketService;
        this.transitionBriefService = transitionBriefService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_REVIEWER_PACKET)
    public OpsShardReadinessRouteCleanupReviewerPacketResponse reviewerPacket() {
        return reviewerPacketService.packet();
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_TRANSITION_BRIEF)
    public OpsShardReadinessRouteCleanupTransitionBriefResponse transitionBrief() {
        return transitionBriefService.brief();
    }
}
