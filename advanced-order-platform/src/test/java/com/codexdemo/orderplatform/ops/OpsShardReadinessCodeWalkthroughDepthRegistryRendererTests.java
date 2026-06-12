package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCodeWalkthroughDepthRegistryRendererTests {

    @Test
    void rendersStableMarkdownSectionsForDepthRegistry() {
        var response = OpsShardReadinessCodeWalkthroughDepthRegistryTestSupport.registry();

        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessCodeWalkthroughDepthRegistryResponse
                        .MarkdownSection::heading)
                .containsExactly(
                        "Depth Rules",
                        "Language Rules",
                        "Evidence Rules",
                        "Boundary Rules",
                        "Verification Steps"
                );
        assertThat(response.markdownSections().get(0).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("minimum-3000-chinese-characters",
                                "minimumChineseCharacters=3000"));
        assertThat(response.markdownSections().get(1).lines())
                .anySatisfy(line -> assertThat(line).contains("chinese-default"));
        assertThat(response.markdownSections().get(2).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("route-model-service-test-chain", "minimumMentions=4"));
        assertThat(response.markdownSections().get(2).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("project-local-workload-proof", "minimumMentions=5"));
        assertThat(response.markdownSections().get(3).lines())
                .anySatisfy(line -> assertThat(line).contains("no-credential-value",
                        "allowed=false"));
        assertThat(response.markdownSections().get(4).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("walkthrough-archive-compliance-tests"));
    }
}
