package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessRoutePaths.BASE_PATH)
public class OpsShardReadinessRouteCleanupPostCompletionController {

    private final OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService;

    public OpsShardReadinessRouteCleanupPostCompletionController(
            OpsShardReadinessRouteCleanupPostPushCloseoutService postPushCloseoutService
    ) {
        this.postPushCloseoutService = postPushCloseoutService;
    }

    @GetMapping(OpsShardReadinessRoutePaths.ROUTE_CLEANUP_POST_PUSH_CLOSEOUT)
    public OpsShardReadinessRouteCleanupPostPushCloseoutResponse postPushCloseout() {
        return postPushCloseoutService.closeout();
    }
}
