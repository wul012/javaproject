package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.BoundaryAssertion;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.CloseoutItem;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.MarkdownSection;
import java.util.ArrayList;
import java.util.List;

final class CloseoutAssembler {

  static final String PROJECT = "advanced-order-platform";
  static final String SOURCE_PLAN = "Node v1846";
  static final String NODE_PARALLEL_PLAN = "Node v1847-v1866";
  static final String PROFILE =
      "java-shard-readiness-release-acceptance-route-path-split-closeout.v1";
  static final int EXPECTED_CLOSEOUT_ITEM_COUNT = 6;
  static final int EXPECTED_BOUNDARY_ASSERTION_COUNT = 7;
  static final int EXPECTED_MARKDOWN_SECTION_COUNT = 3;

  private CloseoutAssembler() {}

  static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse response(
      String version,
      String endpoint,
      OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse source,
      List<CloseoutItem> closeoutItems,
      List<BoundaryAssertion> boundaryAssertions,
      List<MarkdownSection> markdownSections) {
    var closeoutCopy = List.copyOf(closeoutItems);
    var boundaryCopy = List.copyOf(boundaryAssertions);
    var markdownCopy = List.copyOf(markdownSections);
    int passedCloseoutCount =
        Math.toIntExact(closeoutCopy.stream().filter(CloseoutItem::passed).count());
    int lockedBoundaryCount =
        Math.toIntExact(boundaryCopy.stream().filter(BoundaryAssertion::locked).count());
    List<String> checks = new ArrayList<>(15);
    checks.add("release-acceptance-route-path-split-closeout-source-plan-" + SOURCE_PLAN);
    checks.add(
        "release-acceptance-route-path-split-closeout-node-parallel-plan-" + NODE_PARALLEL_PLAN);
    checks.add("release-acceptance-route-path-split-closeout-source-version-" + source.version());
    checks.add("release-acceptance-route-path-split-closeout-source-status-" + source.status());
    checks.add(
        "release-acceptance-route-path-split-closeout-route-count-" + source.routePathCount());
    checks.add(
        "release-acceptance-route-path-split-closeout-compatibility-count-"
            + source.compatibilityCheckCount());
    checks.add("release-acceptance-route-path-split-closeout-item-count-" + closeoutCopy.size());
    checks.add(
        "release-acceptance-route-path-split-closeout-passed-item-count-" + passedCloseoutCount);
    checks.add(
        "release-acceptance-route-path-split-closeout-boundary-count-" + boundaryCopy.size());
    checks.add(
        "release-acceptance-route-path-split-closeout-locked-boundary-count-"
            + lockedBoundaryCount);
    checks.add(
        "release-acceptance-route-path-split-closeout-markdown-section-count-"
            + markdownCopy.size());
    checks.add("release-acceptance-route-path-split-closeout-future-route-owner-rule");
    checks.add("release-acceptance-route-path-split-closeout-no-fresh-node-evidence-required");
    checks.add("release-acceptance-route-path-split-closeout-no-runtime-execution");
    checks.add("release-acceptance-route-path-split-closeout-no-sibling-service-startup");

    String status = status(source, closeoutCopy, boundaryCopy, markdownCopy);
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse(
        PROJECT,
        version,
        true,
        false,
        SOURCE_PLAN,
        NODE_PARALLEL_PLAN,
        source.version(),
        source.endpoint(),
        endpoint,
        PROFILE,
        source.routePathCount(),
        source.compatibilityCheckCount(),
        closeoutCopy.size(),
        boundaryCopy.size(),
        markdownCopy.size(),
        closeoutCopy,
        boundaryCopy,
        markdownCopy,
        List.copyOf(checks),
        status);
  }

  private static String status(
      OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse source,
      List<CloseoutItem> closeoutItems,
      List<BoundaryAssertion> boundaryAssertions,
      List<MarkdownSection> markdownSections) {
    boolean countsMatch =
        closeoutItems.size() == EXPECTED_CLOSEOUT_ITEM_COUNT
            && boundaryAssertions.size() == EXPECTED_BOUNDARY_ASSERTION_COUNT
            && markdownSections.size() == EXPECTED_MARKDOWN_SECTION_COUNT;
    boolean sourcePassed =
        "passed".equals(source.status())
            && source.routePathCount() == 11
            && source.compatibilityCheckCount() == 11;
    boolean allPassed =
        closeoutItems.stream().allMatch(CloseoutItem::passed)
            && boundaryAssertions.stream().allMatch(BoundaryAssertion::locked);
    return countsMatch && sourcePassed && allPassed ? "passed" : "blocked";
  }
}
