package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH)
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitController {

    private final OpsShardReadinessReleaseAcceptanceRoutePathSplitService service;

    public OpsShardReadinessReleaseAcceptanceRoutePathSplitController(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessReleaseAcceptanceRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY)
    public OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse registry() {
        return service.registry();
    }
}
