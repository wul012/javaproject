package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService {

    static final String RESPONSE_VERSION = "Java v1579";
    static final String ENDPOINT =
            OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
                    + OpsShardReadinessReleaseAcceptanceRoutePaths
                    .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_CLOSEOUT_REGISTRY;

    private final OpsShardReadinessReleaseAcceptanceRoutePathSplitService sourceService;

    public OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService(
            OpsShardReadinessReleaseAcceptanceRoutePathSplitService sourceService
    ) {
        this.sourceService = sourceService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse closeout() {
        var source = sourceService.registry();
        var closeoutItems = OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutItemCatalog.items(source);
        var boundaryAssertions =
                OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutBoundaryCatalog.assertions(source);
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                source,
                closeoutItems,
                boundaryAssertions,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutRenderer.render(
                        closeoutItems,
                        boundaryAssertions
                )
        );
    }
}
