package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse;
import java.util.ArrayList;
import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1903";
  static final String NODE_PARALLEL_PLAN = "Node v1879-v1903";
  static final String PROFILE =
      "java-shard-readiness-release-acceptance-route-path-split-sustainment-acceptance-package.v1";
  static final int EXPECTED_SOURCE_SNAPSHOT_COUNT = 1;
  static final int EXPECTED_LINEAGE_ENTRY_COUNT = 3;
  static final int EXPECTED_DECISION_RECORD_COUNT = 6;
  static final int EXPECTED_ARCHIVE_ITEM_COUNT = 5;
  static final int EXPECTED_REVIEW_ITEM_COUNT = 5;
  static final int EXPECTED_CI_EVIDENCE_COUNT = 5;
  static final int EXPECTED_RUNTIME_BOUNDARY_COUNT = 7;
  static final int EXPECTED_NEXT_CHANGE_RULE_COUNT = 6;
  static final int EXPECTED_SCORECARD_ENTRY_COUNT = 9;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 9;

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSupport() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
      response(
          String version,
          String endpoint,
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .SourceSnapshot>
              sourceSnapshots,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .VersionLineage>
              lineage,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .DecisionRecord>
              decisions,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .ArchiveItem>
              archiveItems,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .ReviewItem>
              reviewItems,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .CiEvidence>
              ciEvidence,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .RuntimeBoundary>
              runtimeBoundaries,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .NextChangeRule>
              nextChangeRules,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .ScorecardEntry>
              scorecard,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                      .MarkdownSection>
              markdownSections) {
    var sourceSnapshotCopy = List.copyOf(sourceSnapshots);
    var lineageCopy = List.copyOf(lineage);
    var decisionCopy = List.copyOf(decisions);
    var archiveCopy = List.copyOf(archiveItems);
    var reviewCopy = List.copyOf(reviewItems);
    var ciCopy = List.copyOf(ciEvidence);
    var runtimeBoundaryCopy = List.copyOf(runtimeBoundaries);
    var nextChangeCopy = List.copyOf(nextChangeRules);
    var scorecardCopy = List.copyOf(scorecard);
    var markdownCopy = List.copyOf(markdownSections);
    var checks =
        checks(
            source,
            sourceSnapshotCopy,
            lineageCopy,
            decisionCopy,
            archiveCopy,
            reviewCopy,
            ciCopy,
            runtimeBoundaryCopy,
            nextChangeCopy,
            scorecardCopy,
            markdownCopy);
    String status =
        status(
            source,
            sourceSnapshotCopy,
            lineageCopy,
            decisionCopy,
            archiveCopy,
            reviewCopy,
            ciCopy,
            runtimeBoundaryCopy,
            nextChangeCopy,
            scorecardCopy,
            markdownCopy);
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse(
        PROJECT,
        version,
        true,
        false,
        SOURCE_PLAN,
        NODE_PARALLEL_PLAN,
        source.version(),
        source.endpoint(),
        source.sourceCloseoutVersion(),
        source.sourceSplitVersion(),
        endpoint,
        PROFILE,
        sourceSnapshotCopy.size(),
        lineageCopy.size(),
        decisionCopy.size(),
        archiveCopy.size(),
        reviewCopy.size(),
        ciCopy.size(),
        runtimeBoundaryCopy.size(),
        nextChangeCopy.size(),
        scorecardCopy.size(),
        markdownCopy.size(),
        sourceSnapshotCopy,
        lineageCopy,
        decisionCopy,
        archiveCopy,
        reviewCopy,
        ciCopy,
        runtimeBoundaryCopy,
        nextChangeCopy,
        scorecardCopy,
        markdownCopy,
        checks,
        status);
  }

  private static List<String> checks(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .SourceSnapshot>
          sourceSnapshots,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .VersionLineage>
          lineage,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .DecisionRecord>
          decisions,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .ArchiveItem>
          archiveItems,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .ReviewItem>
          reviewItems,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .CiEvidence>
          ciEvidence,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .RuntimeBoundary>
          runtimeBoundaries,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .NextChangeRule>
          nextChangeRules,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .ScorecardEntry>
          scorecard,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .MarkdownSection>
          markdownSections) {
    List<String> checks = new ArrayList<>();
    checks.add("release-acceptance-route-path-split-acceptance-package-source-plan-" + SOURCE_PLAN);
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-node-parallel-plan-"
            + NODE_PARALLEL_PLAN);
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-sustainment-version-"
            + source.version());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-sustainment-status-"
            + source.status());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-closeout-version-"
            + source.sourceCloseoutVersion());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-split-version-"
            + source.sourceSplitVersion());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-ownership-count-"
            + source.ownershipRuleCount());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-drift-count-"
            + source.driftGuardCount());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-boundary-count-"
            + source.boundaryGuardCount());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-ci-count-"
            + source.ciGateCount());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-consumer-count-"
            + source.consumerHandoffCount());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-scorecard-count-"
            + source.scorecardEntryCount());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-snapshot-count-"
            + sourceSnapshots.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-lineage-entry-count-"
            + lineage.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-decision-record-count-"
            + decisions.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-accepted-decision-count-"
            + count(
                decisions,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .DecisionRecord
                    ::accepted));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-archive-item-count-"
            + archiveItems.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-ready-archive-item-count-"
            + count(
                archiveItems,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .ArchiveItem
                    ::ready));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-review-item-count-"
            + reviewItems.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-passed-review-item-count-"
            + count(
                reviewItems,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .ReviewItem
                    ::passed));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-ci-evidence-count-"
            + ciEvidence.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-passed-ci-evidence-count-"
            + count(
                ciEvidence,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .CiEvidence
                    ::passed));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-runtime-boundary-count-"
            + runtimeBoundaries.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-locked-runtime-boundary-count-"
            + count(
                runtimeBoundaries,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .RuntimeBoundary
                    ::locked));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-next-change-rule-count-"
            + nextChangeRules.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-ready-next-change-rule-count-"
            + count(
                nextChangeRules,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .NextChangeRule
                    ::ready));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-scorecard-entry-count-"
            + scorecard.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-passed-scorecard-entry-count-"
            + count(
                scorecard,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .ScorecardEntry
                    ::passed));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-markdown-section-count-"
            + markdownSections.size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-source-route-delegate-owner-held");
    checks.add("release-acceptance-route-path-split-acceptance-package-source-catalog-owner-held");
    checks.add("release-acceptance-route-path-split-acceptance-package-source-renderer-owner-held");
    checks.add("release-acceptance-route-path-split-acceptance-package-no-runtime-execution");
    checks.add("release-acceptance-route-path-split-acceptance-package-no-write-routing");
    checks.add("release-acceptance-route-path-split-acceptance-package-no-credential-value-read");
    checks.add("release-acceptance-route-path-split-acceptance-package-no-raw-endpoint-resolution");
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-no-managed-audit-connection");
    checks.add("release-acceptance-route-path-split-acceptance-package-no-deployment-rollback");
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-no-node-or-minikv-auto-start");
    checks.add("release-acceptance-route-path-split-acceptance-package-ready-for-archive");
    return List.copyOf(checks);
  }

  private static String status(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .SourceSnapshot>
          sourceSnapshots,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .VersionLineage>
          lineage,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .DecisionRecord>
          decisions,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .ArchiveItem>
          archiveItems,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .ReviewItem>
          reviewItems,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .CiEvidence>
          ciEvidence,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .RuntimeBoundary>
          runtimeBoundaries,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .NextChangeRule>
          nextChangeRules,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .ScorecardEntry>
          scorecard,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                  .MarkdownSection>
          markdownSections) {
    boolean countsMatch =
        sourceSnapshots.size() == EXPECTED_SOURCE_SNAPSHOT_COUNT
            && lineage.size() == EXPECTED_LINEAGE_ENTRY_COUNT
            && decisions.size() == EXPECTED_DECISION_RECORD_COUNT
            && archiveItems.size() == EXPECTED_ARCHIVE_ITEM_COUNT
            && reviewItems.size() == EXPECTED_REVIEW_ITEM_COUNT
            && ciEvidence.size() == EXPECTED_CI_EVIDENCE_COUNT
            && runtimeBoundaries.size() == EXPECTED_RUNTIME_BOUNDARY_COUNT
            && nextChangeRules.size() == EXPECTED_NEXT_CHANGE_RULE_COUNT
            && scorecard.size() == EXPECTED_SCORECARD_ENTRY_COUNT
            && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT;
    boolean sourcePassed =
        "passed".equals(source.status())
            && "Java v1604".equals(source.version())
            && "Java v1579".equals(source.sourceCloseoutVersion())
            && "Java v1570".equals(source.sourceSplitVersion());
    boolean packagePassed =
        sourceSnapshots.stream().allMatch(snapshot -> "passed".equals(snapshot.status()))
            && lineage.stream().allMatch(entry -> "passed".equals(entry.status()))
            && decisions.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                            .DecisionRecord
                        ::accepted)
            && archiveItems.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                            .ArchiveItem
                        ::ready)
            && reviewItems.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                            .ReviewItem
                        ::passed)
            && ciEvidence.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                            .CiEvidence
                        ::passed)
            && runtimeBoundaries.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                            .RuntimeBoundary
                        ::locked)
            && nextChangeRules.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                            .NextChangeRule
                        ::ready)
            && scorecard.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                            .ScorecardEntry
                        ::passed);
    return countsMatch && sourcePassed && packagePassed ? "passed" : "blocked";
  }

  private static <T> long count(List<T> items, java.util.function.Predicate<T> predicate) {
    return items.stream().filter(predicate).count();
  }
}
