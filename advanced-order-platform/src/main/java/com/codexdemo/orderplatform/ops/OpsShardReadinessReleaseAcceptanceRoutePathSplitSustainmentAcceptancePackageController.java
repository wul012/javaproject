package com.codexdemo.orderplatform.ops;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH)
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageController {

    private final OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService service;

    public OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageController(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService service
    ) {
        this.service = service;
    }

    @GetMapping(OpsShardReadinessReleaseAcceptanceRoutePaths
            .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_ACCEPTANCE_PACKAGE)
    public OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse registry() {
        return service.registry();
    }
}
