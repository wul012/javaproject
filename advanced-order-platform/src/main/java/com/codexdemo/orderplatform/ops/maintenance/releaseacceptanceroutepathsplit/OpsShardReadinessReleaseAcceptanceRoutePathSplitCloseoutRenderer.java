package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import java.util.List;

final class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutRenderer {

  private OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutRenderer() {}

  static List<OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.MarkdownSection>
      render(
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.CloseoutItem>
              closeoutItems,
          List<OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.BoundaryAssertion>
              boundaryAssertions) {
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
                "- Node plan "
                    + OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutSupport
                        .NODE_PARALLEL_PLAN,
                "- Java does not require fresh Node or mini-kv evidence for this closeout")));
  }

  private static OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.MarkdownSection
      section(String heading, List<String> lines) {
    return new OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.MarkdownSection(
        heading, List.copyOf(lines));
  }
}
