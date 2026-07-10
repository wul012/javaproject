package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ScorecardEntry>
      scorecard(
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
              consumerHandoffs) {
    return List.of(
        entry(
            "source-closeout",
            sourceSnapshots.stream().allMatch(snapshot -> "passed".equals(snapshot.status())),
            "source closeout remains passed"),
        entry(
            "ownership",
            ownershipRules.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                            .OwnershipRule
                        ::enforced),
            "component owners and landing zones are explicit"),
        entry(
            "drift",
            driftGuards.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard
                        ::locked),
            "source versions and counts are pinned"),
        entry(
            "boundaries",
            boundaryGuards.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                            .BoundaryGuard
                        ::locked),
            "runtime, credential, endpoint, audit, and deployment boundaries remain closed"),
        entry(
            "ci",
            ciGates.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate
                        ::required),
            "focused, related, full, diff, and remote CI gates are required"),
        entry(
            "consumers",
            consumerHandoffs.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                            .ConsumerHandoff
                        ::ready),
            "downstream consumers have explicit handoff rules"),
        entry("runtime-off", true, "sustainment is read-only and does not execute runtime work"),
        entry(
            "split-maintainability",
            true,
            "catalogs and renderers keep future work out of a giant file"));
  }

  private static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ScorecardEntry
      entry(String category, boolean passed, String detail) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ScorecardEntry(
        category, passed, detail);
  }
}
