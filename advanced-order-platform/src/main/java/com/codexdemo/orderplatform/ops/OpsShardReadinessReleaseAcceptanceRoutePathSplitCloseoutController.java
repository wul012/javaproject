package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH)
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutController {

    private final OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService service;

    public OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutController(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessReleaseAcceptanceRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_CLOSEOUT_REGISTRY)
    public OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse closeout() {
        return service.closeout();
    }
}
