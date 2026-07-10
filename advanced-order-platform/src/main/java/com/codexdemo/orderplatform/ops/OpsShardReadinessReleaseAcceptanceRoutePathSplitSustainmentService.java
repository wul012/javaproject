package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePaths;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService {

  static final String RESPONSE_VERSION = "Java v1604";
  static final String ENDPOINT =
      OpsShardReadinessReleaseAcceptanceRoutePaths.BASE_PATH
          + OpsShardReadinessReleaseAcceptanceRoutePaths
              .RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_SUSTAINMENT_REGISTRY;

  private final OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService sourceService;

  public OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService sourceService) {
    this.sourceService = sourceService;
  }

  @Transactional(readOnly = true)
  public OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse registry() {
    var source = sourceService.closeout();
    var sourceSnapshots =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog.snapshots(source);
    var ownershipRules =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipCatalog.rules(source);
    var driftGuards =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftCatalog.guards(source);
    var boundaryGuards =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryCatalog.guards(source);
    var ciGates =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiCatalog.gates(source);
    var consumerHandoffs =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerCatalog.handoffs(source);
    var scorecard =
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardCatalog.scorecard(
            sourceSnapshots,
            ownershipRules,
            driftGuards,
            boundaryGuards,
            ciGates,
            consumerHandoffs);
    return OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSupport.response(
        RESPONSE_VERSION,
        ENDPOINT,
        source,
        sourceSnapshots,
        ownershipRules,
        driftGuards,
        boundaryGuards,
        ciGates,
        consumerHandoffs,
        scorecard,
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRenderer.render(
            sourceSnapshots,
            ownershipRules,
            driftGuards,
            boundaryGuards,
            ciGates,
            consumerHandoffs,
            scorecard));
  }
}
