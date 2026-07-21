package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitService {

  static final String RESPONSE_VERSION = "Java v1570";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY;

  private final OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService sourceService;

  public OpsShardReadinessReleaseAcceptanceRoutePathSplitService(
      OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService sourceService) {
    this.sourceService = sourceService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse registry() {
    var source = sourceService.registry();
    var sourceSnapshots = SourceCatalog.snapshots(source);
    var routePaths = RouteCatalog.routes();
    var compatibilityChecks = CompatibilityCatalog.checks(routePaths);
    var boundaryGuards = BoundaryCatalog.guards();
    var consumerHandoffs = ConsumerCatalog.handoffs();
    var scorecard =
        ScorecardCatalog.scorecard(
            sourceSnapshots, routePaths, compatibilityChecks, boundaryGuards, consumerHandoffs);
    return RegistryAssembler.response(
        RESPONSE_VERSION,
        ENDPOINT,
        source,
        sourceSnapshots,
        routePaths,
        compatibilityChecks,
        boundaryGuards,
        consumerHandoffs,
        scorecard,
        ReportRenderer.render(
            sourceSnapshots,
            routePaths,
            compatibilityChecks,
            boundaryGuards,
            consumerHandoffs,
            scorecard));
  }
}
