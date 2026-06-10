package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererTests {

    @Test
    void rendererReturnsStableMarkdownSectionsAndChecks() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitTestSupport.registry();

        assertThat(response.markdownSectionCount()).isEqualTo(6);
        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.MarkdownSection::heading)
                .containsExactly(
                        "Source Handoff",
                        "Route Path Split",
                        "Compatibility Checks",
                        "Boundary Guards",
                        "Consumer Handoffs",
                        "Scorecard"
                );
        assertThat(response.checks()).hasSize(24);
        assertThat(response.checks()).contains(
                "release-acceptance-route-path-split-source-plan-Node v1846",
                "release-acceptance-route-path-split-source-handoff-version-Java v1547",
                "release-acceptance-route-path-split-route-count-11",
                "release-acceptance-route-path-split-compatible-route-count-11",
                "release-acceptance-route-path-split-stable-barrel-preserved",
                "release-acceptance-route-path-split-no-runtime-execution"
        );
    }
}
