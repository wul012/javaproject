package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.BoundaryGuard;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.CompatibilityCheck;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ConsumerHandoff;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.RoutePathEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.ScorecardEntry;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.SourceSnapshot;
import com.codexdemo.orderplatform.ops.maintenance.releasearchivehandoff.OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse;
import java.util.ArrayList;
import java.util.List;

final class RegistryAssembler {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1846";
  static final String NODE_SPLIT_PLAN = "Node v1822-v1846";
  static final String PROFILE =
      "java-shard-readiness-release-acceptance-route-path-split-registry.v1";
  static final int EXPECTED_SOURCE_SNAPSHOT_COUNT = 1;
  static final int EXPECTED_ROUTE_PATH_COUNT = 11;
  static final int EXPECTED_COMPATIBILITY_CHECK_COUNT = 11;
  static final int EXPECTED_BOUNDARY_GUARD_COUNT = 7;
  static final int EXPECTED_CONSUMER_HANDOFF_COUNT = 5;
  static final int EXPECTED_SCORECARD_ENTRY_COUNT = 8;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 6;

  private RegistryAssembler() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse response(
      String version,
      String endpoint,
      OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse source,
      List<SourceSnapshot> sourceSnapshots,
      List<RoutePathEntry> routePaths,
      List<CompatibilityCheck> compatibilityChecks,
      List<BoundaryGuard> boundaryGuards,
      List<ConsumerHandoff> consumerHandoffs,
      List<ScorecardEntry> scorecard,
      List<MarkdownSection> markdownSections) {
    var sourceCopy = List.copyOf(sourceSnapshots);
    var routeCopy = List.copyOf(routePaths);
    var compatibilityCopy = List.copyOf(compatibilityChecks);
    var boundaryCopy = List.copyOf(boundaryGuards);
    var consumerCopy = List.copyOf(consumerHandoffs);
    var scorecardCopy = List.copyOf(scorecard);
    var markdownCopy = List.copyOf(markdownSections);

    int compatibleRouteCount =
        Math.toIntExact(routeCopy.stream().filter(RoutePathEntry::legacyCompatible).count());
    int matchedCompatibilityCount =
        Math.toIntExact(compatibilityCopy.stream().filter(CompatibilityCheck::matched).count());
    int lockedBoundaryCount =
        Math.toIntExact(boundaryCopy.stream().filter(BoundaryGuard::locked).count());
    int passedScorecardCount =
        Math.toIntExact(scorecardCopy.stream().filter(ScorecardEntry::passed).count());

    List<String> checks = new ArrayList<>(24);
    checks.add("release-acceptance-route-path-split-source-plan-" + SOURCE_PLAN);
    checks.add("release-acceptance-route-path-split-node-split-plan-" + NODE_SPLIT_PLAN);
    checks.add("release-acceptance-route-path-split-source-handoff-version-" + source.version());
    checks.add("release-acceptance-route-path-split-source-handoff-status-" + source.status());
    checks.add("release-acceptance-route-path-split-source-count-" + sourceCopy.size());
    checks.add("release-acceptance-route-path-split-route-count-" + routeCopy.size());
    checks.add(
        "release-acceptance-route-path-split-compatible-route-count-" + compatibleRouteCount);
    checks.add(
        "release-acceptance-route-path-split-compatibility-count-" + compatibilityCopy.size());
    checks.add(
        "release-acceptance-route-path-split-matched-compatibility-count-"
            + matchedCompatibilityCount);
    checks.add("release-acceptance-route-path-split-boundary-count-" + boundaryCopy.size());
    checks.add("release-acceptance-route-path-split-locked-boundary-count-" + lockedBoundaryCount);
    checks.add("release-acceptance-route-path-split-consumer-count-" + consumerCopy.size());
    checks.add("release-acceptance-route-path-split-scorecard-count-" + scorecardCopy.size());
    checks.add(
        "release-acceptance-route-path-split-passed-scorecard-count-" + passedScorecardCount);
    checks.add("release-acceptance-route-path-split-markdown-section-count-" + markdownCopy.size());
    checks.add("release-acceptance-route-path-split-stable-barrel-preserved");
    checks.add("release-acceptance-route-path-split-narrow-module-added");
    checks.add("release-acceptance-route-path-split-no-route-value-change");
    checks.add("release-acceptance-route-path-split-no-runtime-execution");
    checks.add("release-acceptance-route-path-split-no-write-routing");
    checks.add("release-acceptance-route-path-split-no-credential-value-read");
    checks.add("release-acceptance-route-path-split-no-raw-endpoint-resolution");
    checks.add("release-acceptance-route-path-split-no-managed-audit-connection");
    checks.add("release-acceptance-route-path-split-no-deployment-rollback");

    String status =
        status(
            source,
            sourceCopy,
            routeCopy,
            compatibilityCopy,
            boundaryCopy,
            consumerCopy,
            scorecardCopy,
            markdownCopy);
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse(
        PROJECT,
        version,
        true,
        false,
        true,
        SOURCE_PLAN,
        NODE_SPLIT_PLAN,
        source.version(),
        source.endpoint(),
        endpoint,
        PROFILE,
        sourceCopy.size(),
        routeCopy.size(),
        compatibilityCopy.size(),
        boundaryCopy.size(),
        consumerCopy.size(),
        scorecardCopy.size(),
        markdownCopy.size(),
        sourceCopy,
        routeCopy,
        compatibilityCopy,
        boundaryCopy,
        consumerCopy,
        scorecardCopy,
        markdownCopy,
        List.copyOf(checks),
        status);
  }

  private static String status(
      OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffResponse source,
      List<SourceSnapshot> sourceSnapshots,
      List<RoutePathEntry> routePaths,
      List<CompatibilityCheck> compatibilityChecks,
      List<BoundaryGuard> boundaryGuards,
      List<ConsumerHandoff> consumerHandoffs,
      List<ScorecardEntry> scorecard,
      List<MarkdownSection> markdownSections) {
    boolean countsMatch =
        sourceSnapshots.size() == EXPECTED_SOURCE_SNAPSHOT_COUNT
            && routePaths.size() == EXPECTED_ROUTE_PATH_COUNT
            && compatibilityChecks.size() == EXPECTED_COMPATIBILITY_CHECK_COUNT
            && boundaryGuards.size() == EXPECTED_BOUNDARY_GUARD_COUNT
            && consumerHandoffs.size() == EXPECTED_CONSUMER_HANDOFF_COUNT
            && scorecard.size() == EXPECTED_SCORECARD_ENTRY_COUNT
            && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT;
    boolean allPassed =
        "passed".equals(source.status())
            && routePaths.stream().allMatch(RoutePathEntry::legacyCompatible)
            && compatibilityChecks.stream().allMatch(CompatibilityCheck::matched)
            && boundaryGuards.stream().allMatch(BoundaryGuard::locked)
            && consumerHandoffs.stream().allMatch(handoff -> "passed".equals(handoff.status()))
            && scorecard.stream().allMatch(ScorecardEntry::passed);
    return countsMatch && allPassed ? "passed" : "blocked";
  }
}
