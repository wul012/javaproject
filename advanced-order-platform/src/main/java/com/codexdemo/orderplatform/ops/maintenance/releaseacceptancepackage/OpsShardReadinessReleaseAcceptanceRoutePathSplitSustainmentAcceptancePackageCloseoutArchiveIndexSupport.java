package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import java.util.ArrayList;
import java.util.List;

final
class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexSupport {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1937";
  static final String NODE_PARALLEL_PLAN = "Node v1935-v1937";
  static final String PROFILE =
      "java-shard-readiness-release-acceptance-route-path-split-sustainment-acceptance-package-closeout-archive-index.v1";
  static final int EXPECTED_SOURCE_SNAPSHOT_COUNT = 1;
  static final int EXPECTED_CRITERIA_ECHO_COUNT = 7;
  static final int EXPECTED_ARCHIVE_ITEM_COUNT = 5;
  static final int EXPECTED_VERIFICATION_GATE_COUNT = 5;
  static final int EXPECTED_HANDOFF_NOTE_COUNT = 4;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 5;

  private
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexSupport() {}

  static
  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
      response(
          String version,
          String endpoint,
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
              source,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .SourceSnapshot>
              sourceSnapshots,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .CriteriaEcho>
              criteriaEchoes,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .ArchiveIndexItem>
              archiveItems,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .VerificationGate>
              verificationGates,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .HandoffNote>
              handoffNotes,
          List<
                  OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                      .MarkdownSection>
              markdownSections) {
    var sourceSnapshotCopy = List.copyOf(sourceSnapshots);
    var criteriaCopy = List.copyOf(criteriaEchoes);
    var archiveCopy = List.copyOf(archiveItems);
    var verificationCopy = List.copyOf(verificationGates);
    var handoffCopy = List.copyOf(handoffNotes);
    var markdownCopy = List.copyOf(markdownSections);
    var checks =
        checks(
            source,
            sourceSnapshotCopy,
            criteriaCopy,
            archiveCopy,
            verificationCopy,
            handoffCopy,
            markdownCopy);
    String status =
        status(
            source,
            sourceSnapshotCopy,
            criteriaCopy,
            archiveCopy,
            verificationCopy,
            handoffCopy,
            markdownCopy);
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse(
        PROJECT,
        version,
        true,
        false,
        SOURCE_PLAN,
        NODE_PARALLEL_PLAN,
        source.version(),
        source.endpoint(),
        source.sourceAcceptancePackageVersion(),
        endpoint,
        PROFILE,
        sourceSnapshotCopy.size(),
        criteriaCopy.size(),
        archiveCopy.size(),
        verificationCopy.size(),
        handoffCopy.size(),
        markdownCopy.size(),
        sourceSnapshotCopy,
        criteriaCopy,
        archiveCopy,
        verificationCopy,
        handoffCopy,
        markdownCopy,
        checks,
        status);
  }

  private static List<String> checks(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
          source,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .SourceSnapshot>
          sourceSnapshots,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .CriteriaEcho>
          criteriaEchoes,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .ArchiveIndexItem>
          archiveItems,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .VerificationGate>
          verificationGates,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .HandoffNote>
          handoffNotes,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .MarkdownSection>
          markdownSections) {
    List<String> checks = new ArrayList<>();
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-source-plan-" + SOURCE_PLAN);
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-node-parallel-plan-"
            + NODE_PARALLEL_PLAN);
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-source-receipt-version-"
            + source.version());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-source-receipt-status-"
            + source.status());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-source-package-version-"
            + source.sourceAcceptancePackageVersion());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-source-criteria-count-"
            + source.acceptedCriteriaCount());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-source-markdown-line-count-"
            + source.markdownLineCount());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-source-snapshot-count-"
            + sourceSnapshots.size());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-criteria-echo-count-"
            + criteriaEchoes.size());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-archive-item-count-"
            + archiveItems.size());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-ready-archive-item-count-"
            + count(
                archiveItems,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                        .ArchiveIndexItem
                    ::ready));
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-verification-gate-count-"
            + verificationGates.size());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-passed-verification-gate-count-"
            + count(
                verificationGates,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                        .VerificationGate
                    ::passed));
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-handoff-note-count-"
            + handoffNotes.size());
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-ready-handoff-note-count-"
            + count(
                handoffNotes,
                OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                        .HandoffNote
                    ::ready));
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-markdown-section-count-"
            + markdownSections.size());
    checks.add("release-acceptance-route-path-split-closeout-archive-index-no-runtime-execution");
    checks.add(
        "release-acceptance-route-path-split-closeout-archive-index-no-sibling-service-startup");
    checks.add("release-acceptance-route-path-split-closeout-archive-index-ready-for-retention");
    return List.copyOf(checks);
  }

  private static String status(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse
          source,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .SourceSnapshot>
          sourceSnapshots,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .CriteriaEcho>
          criteriaEchoes,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .ArchiveIndexItem>
          archiveItems,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .VerificationGate>
          verificationGates,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .HandoffNote>
          handoffNotes,
      List<
              OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                  .MarkdownSection>
          markdownSections) {
    boolean countsMatch =
        sourceSnapshots.size() == EXPECTED_SOURCE_SNAPSHOT_COUNT
            && criteriaEchoes.size() == EXPECTED_CRITERIA_ECHO_COUNT
            && archiveItems.size() == EXPECTED_ARCHIVE_ITEM_COUNT
            && verificationGates.size() == EXPECTED_VERIFICATION_GATE_COUNT
            && handoffNotes.size() == EXPECTED_HANDOFF_NOTE_COUNT
            && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT;
    boolean sourcePassed =
        "passed".equals(source.status())
            && "Java v1637".equals(source.version())
            && "Java v1634".equals(source.sourceAcceptancePackageVersion());
    boolean archivePassed =
        sourceSnapshots.stream().allMatch(snapshot -> "passed".equals(snapshot.status()))
            && criteriaEchoes.stream().allMatch(echo -> "accepted".equals(echo.status()))
            && archiveItems.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                            .ArchiveIndexItem
                        ::ready)
            && verificationGates.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                            .VerificationGate
                        ::passed)
            && handoffNotes.stream()
                .allMatch(
                    OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse
                            .HandoffNote
                        ::ready);
    return countsMatch && sourcePassed && archivePassed ? "passed" : "blocked";
  }

  private static <T> long count(List<T> items, java.util.function.Predicate<T> predicate) {
    return items.stream().filter(predicate).count();
  }
}
