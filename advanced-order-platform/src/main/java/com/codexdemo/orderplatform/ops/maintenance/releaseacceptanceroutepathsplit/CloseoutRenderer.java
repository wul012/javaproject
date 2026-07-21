package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.BoundaryAssertion;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.CloseoutItem;
import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.MarkdownSection;
import java.util.List;

final class CloseoutRenderer {

  private CloseoutRenderer() {}

  static List<MarkdownSection> render(
      List<CloseoutItem> closeoutItems, List<BoundaryAssertion> boundaryAssertions) {
    return List.of(
        section(
            "Closeout Items",
            closeoutItems.stream()
                .map(item -> "- " + item.item() + " passed=" + item.passed())
                .toList()),
        section(
            "Boundary Assertions",
            boundaryAssertions.stream()
                .map(assertion -> "- " + assertion.boundary() + " locked=" + assertion.locked())
                .toList()),
        section(
            "Parallel Plan",
            List.of(
                "- Node plan " + CloseoutAssembler.NODE_PARALLEL_PLAN,
                "- Java does not require fresh Node or mini-kv evidence for this closeout")));
  }

  private static MarkdownSection section(String heading, List<String> lines) {
    return new MarkdownSection(heading, List.copyOf(lines));
  }
}
