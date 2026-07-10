package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardCatalog {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardCatalog() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry> scorecard(
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot> sourceSnapshots,
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry> routePaths,
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck>
          compatibilityChecks,
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard> boundaryGuards,
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff>
          consumerHandoffs) {
    List<OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry> entries =
        new ArrayList<>();
    entries.add(
        entry(
            "source-handoff",
            sourceSnapshots.size() == 1,
            "Java v1547 handoff remains the source evidence"));
    entries.add(
        entry(
            "stable-barrel",
            routePaths.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry
                        ::legacyCompatible),
            "OpsShardReadinessRoutePaths keeps every migrated constant"));
    entries.add(
        entry(
            "narrow-module",
            routePaths.size() == 11,
            "release-acceptance route ownership has a dedicated module"));
    entries.add(
        entry(
            "compatibility",
            compatibilityChecks.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck
                        ::matched),
            "stable and split route values match one-for-one"));
    entries.add(
        entry(
            "consumer-migration",
            consumerHandoffs.size() == 5,
            "new consumers know whether to use the narrow module or legacy barrel"));
    entries.add(
        entry(
            "boundary-lock",
            boundaryGuards.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard::locked),
            "runtime, credential, raw endpoint, audit, and deployment paths remain locked"));
    entries.add(
        entry(
            "node-v1846-parallelism",
            true,
            "Java proceeds independently while Node owns its type-barrel split"));
    entries.add(
        entry(
            "maintainability",
            true,
            "future release-acceptance route constants now have a local owner"));
    return List.copyOf(entries);
  }

  private static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry entry(
      String category, boolean passed, String detail) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry(
        category, passed, detail);
  }
}
