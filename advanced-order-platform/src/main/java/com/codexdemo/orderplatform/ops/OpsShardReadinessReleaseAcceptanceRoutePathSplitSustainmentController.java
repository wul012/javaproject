package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH)
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController {

    private final OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService service;

    public OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessReleaseAcceptanceRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_REGISTRY)
    public OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse registry() {
        return service.registry();
    }
}
