package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRenderer {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRenderer() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection>
      render(
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.SourceSnapshot>
              sourceSnapshots,
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.OwnershipRule>
              ownershipRules,
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard>
              driftGuards,
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.BoundaryGuard>
              boundaryGuards,
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate> ciGates,
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ConsumerHandoff>
              consumerHandoffs,
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ScorecardEntry>
              scorecard) {
    return List.of(
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceRenderer.render(
            sourceSnapshots),
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipRenderer.render(
            ownershipRules),
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftRenderer.render(
            driftGuards),
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryRenderer.render(
            boundaryGuards),
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiRenderer.render(ciGates),
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerRenderer.render(
            consumerHandoffs),
        OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardRenderer.render(
            scorecard));
  }
}
