package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveRegistryRendererTests {

  @Test
  void rendersStableMarkdownSectionsForArchiveSegmentation() {
    var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

    assertThat(response.markdownSections())
        .extracting(
            OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection::heading)
        .containsExactly(
            "Current Archive Assessments",
            "Archive Segment Plans",
            "Naming Rules",
            "Boundary Rules",
            "Verification Steps");
    assertThat(response.markdownSections().get(0).lines())
        .anySatisfy(
            line -> assertThat(line).contains("f", "active-canonical-screenshot-explanation-root"));
    assertThat(response.markdownSections().get(1).lines())
        .anySatisfy(line -> assertThat(line).contains("f/v1769-v1773", "active=true"));
    assertThat(response.markdownSections().get(2).lines())
        .anySatisfy(line -> assertThat(line).contains("no-root-dumping", "required=true"));
    assertThat(response.markdownSections().get(3).lines())
        .anySatisfy(line -> assertThat(line).contains("no-screenshot-capture", "allowed=false"));
    assertThat(response.markdownSections().get(4).lines())
        .anySatisfy(line -> assertThat(line).contains("archive-doc-tests"));
  }
}
