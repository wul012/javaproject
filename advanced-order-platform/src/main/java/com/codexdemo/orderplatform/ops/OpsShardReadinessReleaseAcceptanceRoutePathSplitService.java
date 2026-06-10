package com.codexdemo.orderplatform.ops;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitService {

    static final String RESPONSE_VERSION = "Java v1567";
    static final String ENDPOINT =
            OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
                    + OpsShardReadinessReleaseAcceptanceRoutePaths.RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY;

    private final OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService sourceService;

    public OpsShardReadinessReleaseAcceptanceRoutePathSplitService(
            OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService sourceService
    ) {
        this.sourceService = sourceService;
    }

    @Transactional(readOnly = true)
    public OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse registry() {
        var source = sourceService.registry();
        var sourceSnapshots = OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceCatalog.snapshots(source);
        var routePaths = OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog.routes();
        var compatibilityChecks =
                OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog.compatibilityChecks();
        var boundaryGuards = OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryCatalog.guards();
        var consumerHandoffs = OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerCatalog.handoffs();
        var scorecard = OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardCatalog.scorecard(
                sourceSnapshots,
                routePaths,
                compatibilityChecks,
                boundaryGuards,
                consumerHandoffs
        );
        return OpsShardReadinessReleaseAcceptanceRoutePathSplitSupport.response(
                RESPONSE_VERSION,
                ENDPOINT,
                source,
                sourceSnapshots,
                routePaths,
                compatibilityChecks,
                boundaryGuards,
                consumerHandoffs,
                scorecard,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitRenderer.render(
                        sourceSnapshots,
                        routePaths,
                        compatibilityChecks,
                        boundaryGuards,
                        consumerHandoffs,
                        scorecard
                )
        );
    }
}
