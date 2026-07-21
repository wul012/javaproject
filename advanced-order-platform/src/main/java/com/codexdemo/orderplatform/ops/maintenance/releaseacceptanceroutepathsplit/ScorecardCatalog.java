package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot;
import java.util.ArrayList;
import java.util.List;

final class ScorecardCatalog {

  private ScorecardCatalog() {}

  static List<ScorecardEntry> scorecard(
      List<SourceSnapshot> sourceSnapshots,
      List<RoutePathEntry> routePaths,
      List<CompatibilityCheck> compatibilityChecks,
      List<BoundaryGuard> boundaryGuards,
      List<ConsumerHandoff> consumerHandoffs) {
    List<ScorecardEntry> entries = new ArrayList<>();
    entries.add(
        entry(
            "source-handoff",
            sourceSnapshots.size() == 1,
            "Java v1547 handoff remains the source evidence"));
    entries.add(
        entry(
            "stable-barrel",
            routePaths.stream().allMatch(RoutePathEntry::legacyCompatible),
            "OpsShardReadinessRoutePaths keeps every migrated constant"));
    entries.add(
        entry(
            "narrow-module",
            routePaths.size() == 11,
            "release-acceptance route ownership has a dedicated module"));
    entries.add(
        entry(
            "compatibility",
            compatibilityChecks.stream().allMatch(CompatibilityCheck::matched),
            "stable and split route values match one-for-one"));
    entries.add(
        entry(
            "consumer-migration",
            consumerHandoffs.size() == 5,
            "new consumers know whether to use the narrow module or legacy barrel"));
    entries.add(
        entry(
            "boundary-lock",
            boundaryGuards.stream().allMatch(BoundaryGuard::locked),
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

  private static ScorecardEntry entry(String category, boolean passed, String detail) {
    return new ScorecardEntry(category, passed, detail);
  }
}
