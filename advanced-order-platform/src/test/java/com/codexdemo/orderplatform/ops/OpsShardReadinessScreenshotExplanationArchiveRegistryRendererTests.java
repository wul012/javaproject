package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveRegistryRendererTests {

    @Test
    void rendersStableMarkdownSectionsForArchiveSegmentation() {
        var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                        .MarkdownSection::heading)
                .containsExactly(
                        "Current Archive Assessments",
                        "Archive Segment Plans",
                        "Naming Rules",
                        "Boundary Rules",
                        "Verification Steps"
                );
        assertThat(response.markdownSections().get(0).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("d_runtime_screenshot_archive_next", "active-segmented-root"));
        assertThat(response.markdownSections().get(1).lines())
                .anySatisfy(line -> assertThat(line).contains("v1759-v1763", "active=true"));
        assertThat(response.markdownSections().get(2).lines())
                .anySatisfy(line -> assertThat(line).contains("no-root-dumping", "required=true"));
        assertThat(response.markdownSections().get(3).lines())
                .anySatisfy(line -> assertThat(line).contains("no-screenshot-capture", "allowed=false"));
        assertThat(response.markdownSections().get(4).lines())
                .anySatisfy(line -> assertThat(line).contains("archive-doc-tests"));
    }
}
