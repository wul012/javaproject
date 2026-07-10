package com.codexdemo.orderplatform.ops;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1878";
  static final String NODE_PARALLEL_PLAN = "Node v1867-v1878";
  static final String PROFILE =
      "java-shard-readiness-release-acceptance-route-path-split-sustainment.v1";
  static final int EXPECTED_SOURCE_SNAPSHOT_COUNT = 1;
  static final int EXPECTED_OWNERSHIP_RULE_COUNT = 6;
  static final int EXPECTED_DRIFT_GUARD_COUNT = 6;
  static final int EXPECTED_BOUNDARY_GUARD_COUNT = 7;
  static final int EXPECTED_CI_GATE_COUNT = 5;
  static final int EXPECTED_CONSUMER_HANDOFF_COUNT = 5;
  static final int EXPECTED_SCORECARD_ENTRY_COUNT = 8;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 7;

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSupport() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse response(
      String version,
      String endpoint,
      OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source,
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
          scorecard,
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection>
          markdownSections) {
    var sourceSnapshotCopy = List.copyOf(sourceSnapshots);
    var ownershipCopy = List.copyOf(ownershipRules);
    var driftCopy = List.copyOf(driftGuards);
    var boundaryCopy = List.copyOf(boundaryGuards);
    var ciCopy = List.copyOf(ciGates);
    var consumerCopy = List.copyOf(consumerHandoffs);
    var scorecardCopy = List.copyOf(scorecard);
    var markdownCopy = List.copyOf(markdownSections);
    var checks =
        checks(
            source,
            sourceSnapshotCopy,
            ownershipCopy,
            driftCopy,
            boundaryCopy,
            ciCopy,
            consumerCopy,
            scorecardCopy,
            markdownCopy);
    String status =
        status(
            source,
            sourceSnapshotCopy,
            ownershipCopy,
            driftCopy,
            boundaryCopy,
            ciCopy,
            consumerCopy,
            scorecardCopy,
            markdownCopy);
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse(
        PROJECT,
        version,
        true,
        false,
        SOURCE_PLAN,
        NODE_PARALLEL_PLAN,
        source.version(),
        source.endpoint(),
        source.sourceSplitVersion(),
        source.sourceSplitEndpoint(),
        endpoint,
        PROFILE,
        sourceSnapshotCopy.size(),
        ownershipCopy.size(),
        driftCopy.size(),
        boundaryCopy.size(),
        ciCopy.size(),
        consumerCopy.size(),
        scorecardCopy.size(),
        markdownCopy.size(),
        sourceSnapshotCopy,
        ownershipCopy,
        driftCopy,
        boundaryCopy,
        ciCopy,
        consumerCopy,
        scorecardCopy,
        markdownCopy,
        checks,
        status);
  }

  private static List<String> checks(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source,
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
          scorecard,
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection>
          markdownSections) {
    List<String> checks = new ArrayList<>();
    checks.add("release-acceptance-route-path-split-sustainment-source-plan-" + SOURCE_PLAN);
    checks.add(
        "release-acceptance-route-path-split-sustainment-node-parallel-plan-" + NODE_PARALLEL_PLAN);
    checks.add(
        "release-acceptance-route-path-split-sustainment-source-closeout-version-"
            + source.version());
    checks.add(
        "release-acceptance-route-path-split-sustainment-source-closeout-status-"
            + source.status());
    checks.add(
        "release-acceptance-route-path-split-sustainment-source-split-version-"
            + source.sourceSplitVersion());
    checks.add(
        "release-acceptance-route-path-split-sustainment-source-route-count-"
            + source.routePathCount());
    checks.add(
        "release-acceptance-route-path-split-sustainment-source-compatibility-count-"
            + source.compatibilityCheckCount());
    checks.add(
        "release-acceptance-route-path-split-sustainment-source-snapshot-count-"
            + sourceSnapshots.size());
    checks.add(
        "release-acceptance-route-path-split-sustainment-ownership-rule-count-"
            + ownershipRules.size());
    checks.add(
        "release-acceptance-route-path-split-sustainment-enforced-ownership-rule-count-"
            + count(
                ownershipRules,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.OwnershipRule
                    ::enforced));
    checks.add(
        "release-acceptance-route-path-split-sustainment-drift-guard-count-" + driftGuards.size());
    checks.add(
        "release-acceptance-route-path-split-sustainment-locked-drift-guard-count-"
            + count(
                driftGuards,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard
                    ::locked));
    checks.add(
        "release-acceptance-route-path-split-sustainment-boundary-guard-count-"
            + boundaryGuards.size());
    checks.add(
        "release-acceptance-route-path-split-sustainment-locked-boundary-guard-count-"
            + count(
                boundaryGuards,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.BoundaryGuard
                    ::locked));
    checks.add("release-acceptance-route-path-split-sustainment-ci-gate-count-" + ciGates.size());
    checks.add(
        "release-acceptance-route-path-split-sustainment-required-ci-gate-count-"
            + count(
                ciGates,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate
                    ::required));
    checks.add(
        "release-acceptance-route-path-split-sustainment-consumer-handoff-count-"
            + consumerHandoffs.size());
    checks.add(
        "release-acceptance-route-path-split-sustainment-ready-consumer-handoff-count-"
            + count(
                consumerHandoffs,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ConsumerHandoff
                    ::ready));
    checks.add(
        "release-acceptance-route-path-split-sustainment-scorecard-entry-count-"
            + scorecard.size());
    checks.add(
        "release-acceptance-route-path-split-sustainment-passed-scorecard-entry-count-"
            + count(
                scorecard,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.ScorecardEntry
                    ::passed));
    checks.add(
        "release-acceptance-route-path-split-sustainment-markdown-section-count-"
            + markdownSections.size());
    checks.add("release-acceptance-route-path-split-sustainment-stable-entrypoint-preserved");
    checks.add("release-acceptance-route-path-split-sustainment-no-runtime-execution");
    checks.add("release-acceptance-route-path-split-sustainment-no-sibling-service-startup");
    checks.add("release-acceptance-route-path-split-sustainment-no-write-routing");
    checks.add("release-acceptance-route-path-split-sustainment-no-credential-value-read");
    checks.add("release-acceptance-route-path-split-sustainment-no-raw-endpoint-resolution");
    checks.add("release-acceptance-route-path-split-sustainment-no-managed-audit-connection");
    checks.add("release-acceptance-route-path-split-sustainment-no-deployment-rollback");
    checks.add("release-acceptance-route-path-split-sustainment-no-node-or-minikv-auto-start");
    return List.copyOf(checks);
  }

  private static String status(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse source,
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
          scorecard,
      List<OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.MarkdownSection>
          markdownSections) {
    boolean countsMatch =
        sourceSnapshots.size() == EXPECTED_SOURCE_SNAPSHOT_COUNT
            && ownershipRules.size() == EXPECTED_OWNERSHIP_RULE_COUNT
            && driftGuards.size() == EXPECTED_DRIFT_GUARD_COUNT
            && boundaryGuards.size() == EXPECTED_BOUNDARY_GUARD_COUNT
            && ciGates.size() == EXPECTED_CI_GATE_COUNT
            && consumerHandoffs.size() == EXPECTED_CONSUMER_HANDOFF_COUNT
            && scorecard.size() == EXPECTED_SCORECARD_ENTRY_COUNT
            && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT;
    boolean sourcePassed =
        "passed".equals(source.status())
            && "Java v1579".equals(source.version())
            && "Java v1570".equals(source.sourceSplitVersion());
    boolean allLocked =
        ownershipRules.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                            .OwnershipRule
                        ::enforced)
            && driftGuards.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.DriftGuard
                        ::locked)
            && boundaryGuards.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                            .BoundaryGuard
                        ::locked)
            && ciGates.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.CiGate
                        ::required)
            && consumerHandoffs.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                            .ConsumerHandoff
                        ::ready)
            && scorecard.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse
                            .ScorecardEntry
                        ::passed);
    return countsMatch && sourcePassed && allLocked ? "passed" : "blocked";
  }

  private static <T> long count(List<T> items, java.util.function.Predicate<T> predicate) {
    return items.stream().filter(predicate).count();
  }
}
