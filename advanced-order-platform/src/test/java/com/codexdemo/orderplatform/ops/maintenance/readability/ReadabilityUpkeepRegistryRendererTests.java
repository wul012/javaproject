package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ReadabilityUpkeepRegistryRendererTests {

    @Test
    void rendersStableMarkdownSections() {
        var response = ReadabilityUpkeepRegistryTestSupport.registry();

        assertThat(response.markdownSections())
                .extracting(ReadabilityUpkeepRegistryResponse.MarkdownSection::heading)
                .containsExactly(
                        "Topic Maps",
                        "Package Rules",
                        "Registry Template Rules",
                        "Class Name Trials",
                        "Boundary Rules",
                        "Verification Steps"
                );
        assertThat(response.markdownSections().get(0).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("shard-readiness", "docs/ops/shard-readiness-map.md"));
        assertThat(response.markdownSections().get(1).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("new-readability-registry-subpackage",
                                "ops.maintenance.readability"));
        assertThat(response.markdownSections().get(2).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("read-only-transaction", "@Transactional(readOnly = true)"));
        assertThat(response.markdownSections().get(3).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("ReadabilityUpkeepRegistryService",
                                "activeForNewSubpackages=true"));
        assertThat(response.markdownSections().get(4).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("no-credential-value", "allowed=false"));
        assertThat(response.markdownSections().get(5).lines())
                .anySatisfy(line -> assertThat(line)
                        .contains("ReadabilityUpkeepDocsTests", "required=true"));
    }
}
