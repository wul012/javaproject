package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutMarkdownTests {

    @Test
    void markdownAndChecksDescribeCloseout() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutTestSupport.closeout();

        assertThat(response.markdownSectionCount()).isEqualTo(3);
        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse
                        .MarkdownSection::heading)
                .containsExactly("Closeout Items", "Boundary Assertions", "Parallel Plan");
        assertThat(response.checks()).hasSize(15);
        assertThat(response.checks()).contains(
                "release-acceptance-route-path-split-closeout-source-version-Java v1570",
                "release-acceptance-route-path-split-closeout-route-count-11",
                "release-acceptance-route-path-split-closeout-item-count-6",
                "release-acceptance-route-path-split-closeout-no-runtime-execution",
                "release-acceptance-route-path-split-closeout-no-sibling-service-startup"
        );
    }
}
