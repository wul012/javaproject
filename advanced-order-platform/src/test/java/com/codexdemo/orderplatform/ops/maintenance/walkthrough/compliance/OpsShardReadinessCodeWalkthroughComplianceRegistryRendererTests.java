package com.codexdemo.orderplatform.ops.maintenance.walkthrough.compliance;

import static com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownOracle.sha256;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughComplianceRegistryRendererTests {

  @Test
  void rendersStableMarkdownSectionsForNewWalkthroughStandard() {
    var response = WalkthroughTestData.registry();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection::heading)
        .containsExactly(
            "Version Lineage",
            "Required Walkthrough Headings",
            "Archive Ranges",
            "Documentation Rules",
            "Runtime Boundary Rules",
            "Test Coverage");
    assertThat(response.markdownSections())
        .extracting(section -> section.lines().size())
        .containsExactly(21, 10, 6, 8, 9, 9);
    assertThat(
            sha256(
                response.markdownSections(),
                OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection::heading,
                OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.MarkdownSection::lines))
        .isEqualTo("c872ad0ea5388e7fff8264234a4c181f5e141b33576b3caa519e2f6decf4fe0e");
    assertThat(response.markdownSections().get(0).lines().get(0)).isEqualTo("version-count=20");
    assertThat(response.markdownSections().get(1).lines())
        .anySatisfy(line -> assertThat(line).contains("## 入口路由"));
    assertThat(response.markdownSections().get(3).lines())
        .anySatisfy(
            line -> assertThat(line).contains("future-nine-heading-standard", "required=true"));
    assertThat(response.markdownSections().get(4).lines())
        .anySatisfy(line -> assertThat(line).contains("no-write-routing", "allowed=false"));
    assertThat(response.markdownSections().get(5).lines())
        .anySatisfy(line -> assertThat(line).contains("OpsCodeWalkthroughArchiveComplianceTests"));
  }
}
