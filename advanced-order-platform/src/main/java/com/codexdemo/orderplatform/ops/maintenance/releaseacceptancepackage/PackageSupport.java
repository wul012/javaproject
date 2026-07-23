package com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptancepackage.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.MarkdownSection;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.sustainment.OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

final class PackageSupport {

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

  private PackageSupport() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
      response(
          String version,
          String endpoint,
          OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source,
          PackageCatalog.Evidence evidence,
          List<MarkdownSection> markdownSections) {
    var markdownCopy = List.copyOf(markdownSections);
    var checks = checks(source, evidence, markdownCopy);
    String status = status(source, evidence, markdownCopy);
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
        evidence.sourceSnapshots().size(),
        evidence.lineage().size(),
        evidence.decisions().size(),
        evidence.archiveItems().size(),
        evidence.reviewItems().size(),
        evidence.ciEvidence().size(),
        evidence.runtimeBoundaries().size(),
        evidence.nextChangeRules().size(),
        evidence.scorecard().size(),
        markdownCopy.size(),
        evidence.sourceSnapshots(),
        evidence.lineage(),
        evidence.decisions(),
        evidence.archiveItems(),
        evidence.reviewItems(),
        evidence.ciEvidence(),
        evidence.runtimeBoundaries(),
        evidence.nextChangeRules(),
        evidence.scorecard(),
        markdownCopy,
        checks,
        status);
  }

  private static List<String> checks(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse source,
      PackageCatalog.Evidence evidence,
      List<MarkdownSection> markdownSections) {
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
            + evidence.sourceSnapshots().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-lineage-entry-count-"
            + evidence.lineage().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-decision-record-count-"
            + evidence.decisions().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-accepted-decision-count-"
            + count(evidence.decisions(), item -> item.accepted()));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-archive-item-count-"
            + evidence.archiveItems().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-ready-archive-item-count-"
            + count(evidence.archiveItems(), item -> item.ready()));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-review-item-count-"
            + evidence.reviewItems().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-passed-review-item-count-"
            + count(evidence.reviewItems(), item -> item.passed()));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-ci-evidence-count-"
            + evidence.ciEvidence().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-passed-ci-evidence-count-"
            + count(evidence.ciEvidence(), item -> item.passed()));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-runtime-boundary-count-"
            + evidence.runtimeBoundaries().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-locked-runtime-boundary-count-"
            + count(evidence.runtimeBoundaries(), item -> item.locked()));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-next-change-rule-count-"
            + evidence.nextChangeRules().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-ready-next-change-rule-count-"
            + count(evidence.nextChangeRules(), item -> item.ready()));
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-scorecard-entry-count-"
            + evidence.scorecard().size());
    checks.add(
        "release-acceptance-route-path-split-acceptance-package-passed-scorecard-entry-count-"
            + count(evidence.scorecard(), item -> item.passed()));
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
      PackageCatalog.Evidence evidence,
      List<MarkdownSection> markdownSections) {
    boolean countsMatch =
        evidence.sourceSnapshots().size() == EXPECTED_SOURCE_SNAPSHOT_COUNT
            && evidence.lineage().size() == EXPECTED_LINEAGE_ENTRY_COUNT
            && evidence.decisions().size() == EXPECTED_DECISION_RECORD_COUNT
            && evidence.archiveItems().size() == EXPECTED_ARCHIVE_ITEM_COUNT
            && evidence.reviewItems().size() == EXPECTED_REVIEW_ITEM_COUNT
            && evidence.ciEvidence().size() == EXPECTED_CI_EVIDENCE_COUNT
            && evidence.runtimeBoundaries().size() == EXPECTED_RUNTIME_BOUNDARY_COUNT
            && evidence.nextChangeRules().size() == EXPECTED_NEXT_CHANGE_RULE_COUNT
            && evidence.scorecard().size() == EXPECTED_SCORECARD_ENTRY_COUNT
            && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT;
    boolean sourcePassed =
        "passed".equals(source.status())
            && "Java v1604".equals(source.version())
            && "Java v1579".equals(source.sourceCloseoutVersion())
            && "Java v1570".equals(source.sourceSplitVersion());
    boolean packagePassed =
        evidence.sourceSnapshots().stream().allMatch(snapshot -> "passed".equals(snapshot.status()))
            && evidence.lineage().stream().allMatch(item -> "passed".equals(item.status()))
            && evidence.decisions().stream().allMatch(item -> item.accepted())
            && evidence.archiveItems().stream().allMatch(item -> item.ready())
            && evidence.reviewItems().stream().allMatch(item -> item.passed())
            && evidence.ciEvidence().stream().allMatch(item -> item.passed())
            && evidence.runtimeBoundaries().stream().allMatch(item -> item.locked())
            && evidence.nextChangeRules().stream().allMatch(item -> item.ready())
            && evidence.scorecard().stream().allMatch(item -> item.passed());
    return countsMatch && sourcePassed && packagePassed ? "passed" : "blocked";
  }

  private static <T> long count(List<T> items, Predicate<T> predicate) {
    return items.stream().filter(predicate).count();
  }
}
