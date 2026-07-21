package com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.releaseacceptanceroutepathsplit.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.MarkdownSection;
import java.util.List;
import org.junit.jupiter.api.Test;

class CloseoutMarkdownTests {

  private static final List<MarkdownSection> EXPECTED_REPORT =
      List.of(
          section(
              "Closeout Items",
              "- stable-barrel-preserved passed=true",
              "- compatibility-catalog-extracted passed=true",
              "- route-count-held passed=true",
              "- source-handoff-held passed=true",
              "- node-v1866-parallel-no-fresh-evidence passed=true",
              "- future-route-owner-rule passed=true"),
          section(
              "Boundary Assertions",
              "- write-routing locked=true",
              "- active-shard-router locked=true",
              "- credential-value-read locked=true",
              "- raw-endpoint-resolution locked=true",
              "- managed-audit-connection locked=true",
              "- deployment-rollback locked=true",
              "- sibling-autostart locked=true"),
          section(
              "Parallel Plan",
              "- Node plan Node v1847-v1866",
              "- Java does not require fresh Node or mini-kv evidence for this closeout"));

  @Test
  void reportMatchesLegacyOutput() {
    var response = CloseoutTestData.closeout();

    assertThat(response.markdownSections()).containsExactlyElementsOf(EXPECTED_REPORT);
    assertThat(response.markdownSections()).hasSize(3);
    assertThat(
            response.markdownSections().stream().mapToInt(section -> section.lines().size()).sum())
        .isEqualTo(15);
  }

  @Test
  void checksDescribeTheCloseoutContract() {
    var checks = CloseoutTestData.closeout().checks();

    assertThat(checks).hasSize(15);
    assertThat(checks)
        .contains(
            "release-acceptance-route-path-split-closeout-source-version-Java v1570",
            "release-acceptance-route-path-split-closeout-route-count-11",
            "release-acceptance-route-path-split-closeout-item-count-6",
            "release-acceptance-route-path-split-closeout-no-runtime-execution",
            "release-acceptance-route-path-split-closeout-no-sibling-service-startup");
  }

  private static MarkdownSection section(String heading, String... lines) {
    return new MarkdownSection(heading, List.of(lines));
  }
}
