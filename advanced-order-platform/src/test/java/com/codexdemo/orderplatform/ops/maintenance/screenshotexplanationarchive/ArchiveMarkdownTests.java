package com.codexdemo.orderplatform.ops.maintenance.screenshotexplanationarchive;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.rendering.MarkdownOracle;
import org.junit.jupiter.api.Test;

class ArchiveMarkdownTests {

  @Test
  void rendersStableMarkdownSectionsForArchiveSegmentation() {
    var response = ScreenshotTestData.registry();

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
    assertThat(response.markdownSections())
        .extracting(section -> section.lines().size())
        .containsExactly(4, 6, 7, 9, 6);
    assertThat(
            MarkdownOracle.sha256(
                response.markdownSections(),
                OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection
                    ::heading,
                OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.MarkdownSection
                    ::lines))
        .isEqualTo("205b7c2d1d84604b31f35a1ec6d3993c9e702a99ed122dbc58edf287f16a58f8");
  }
}
