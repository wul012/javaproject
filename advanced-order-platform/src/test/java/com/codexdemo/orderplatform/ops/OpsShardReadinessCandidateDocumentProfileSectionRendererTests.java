package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionRendererTests {

    @Test
    void rendererPreservesHeadingEndpointProfileAndBoundaryLines() {
        var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

        assertThat(response.renderedSections()).hasSize(5);
        assertThat(response.renderedSections())
                .allSatisfy(section -> {
                    assertThat(section.markdownHeading()).startsWith("### Candidate Document");
                    assertThat(section.markdownBody()).contains("- version: Java v");
                    assertThat(section.markdownBody()).contains("- endpoint: /api/v1/ops/shard-readiness");
                    assertThat(section.markdownBody()).contains("- profile: java-shard-readiness-");
                    assertThat(section.markdownBody()).contains("- boundary: read-only-no-runtime");
                });
    }
}
