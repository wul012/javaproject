package com.codexdemo.orderplatform.ops.maintenance.walkthrough.qualitygate;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughQualityGateRegistryRendererTests {

  @Test
  void rendersStableMarkdownSectionsForQualityReview() {
    var response = OpsShardReadinessCodeWalkthroughQualityGateRegistryTestSupport.registry();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.MarkdownSection::heading)
        .containsExactly(
            "Version Granularity Rules",
            "Explanation Rubric",
            "Evidence Anchors",
            "Review Checklist",
            "Runtime Boundary Rules");
    assertThat(response.markdownSections().get(0).lines().get(0)).isEqualTo("version-rule-count=6");
    assertThat(response.markdownSections().get(0).lines())
        .anySatisfy(line -> assertThat(line).contains("no-micro-version-by-default"));
    assertThat(response.markdownSections().get(1).lines())
        .anySatisfy(line -> assertThat(line).contains("服务层核心流程"));
    assertThat(response.markdownSections().get(2).lines())
        .anySatisfy(line -> assertThat(line).contains("quality-gate-registry-anchor"));
    assertThat(response.markdownSections().get(3).lines())
        .anySatisfy(line -> assertThat(line).contains("version-size", "blocks-release=true"));
    assertThat(response.markdownSections().get(4).lines())
        .anySatisfy(line -> assertThat(line).contains("no-write-routing", "allowed=false"));
  }
}
