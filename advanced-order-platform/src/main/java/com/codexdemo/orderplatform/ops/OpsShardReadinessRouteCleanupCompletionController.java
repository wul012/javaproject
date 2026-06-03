package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupCompletionController {

    private final OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService;

    public OpsShardReadinessRouteCleanupCompletionController(
            OpsShardReadinessRouteCleanupReviewerPacketService reviewerPacketService
    ) {
        this.reviewerPacketService = reviewerPacketService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_REVIEWER_PACKET)
    public OpsShardReadinessRouteCleanupReviewerPacketResponse reviewerPacket() {
        return reviewerPacketService.packet();
    }
}
