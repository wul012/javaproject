package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererTests {

    @Test
    void markdownAndChecksDescribeAcceptancePackage() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport
                .registry();

        assertThat(response.markdownSections())
                .extracting(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .MarkdownSection::heading)
                .containsExactly(
                        "Source Sustainment",
                        "Version Lineage",
                        "Acceptance Decisions",
                        "Archive Items",
                        "Review Checklist",
                        "CI Evidence",
                        "Runtime Boundaries",
                        "Next Change Rules",
                        "Acceptance Scorecard"
                );
        assertThat(response.checks()).hasSize(40);
        assertThat(response.checks()).contains(
                "release-acceptance-route-path-split-acceptance-package-source-plan-Node v1903",
                "release-acceptance-route-path-split-acceptance-package-node-parallel-plan-Node v1879-v1903",
                "release-acceptance-route-path-split-acceptance-package-source-sustainment-version-Java v1604",
                "release-acceptance-route-path-split-acceptance-package-source-closeout-version-Java v1579",
                "release-acceptance-route-path-split-acceptance-package-source-split-version-Java v1570",
                "release-acceptance-route-path-split-acceptance-package-lineage-entry-count-3",
                "release-acceptance-route-path-split-acceptance-package-ready-for-archive"
        );
    }

    @Test
    void nextChangeRendererKeepsLandingZonesVisible() {
        var response = OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport
                .registry();

        assertThat(response.nextChangeRules())
                .extracting(OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse
                        .NextChangeRule::trigger)
                .containsExactly(
                        "new-route-path",
                        "new-consumer",
                        "new-ci-gate",
                        "new-boundary",
                        "source-plan-roll",
                        "markdown-copy-change"
                );
        assertThat(response.markdownSections().get(7).lines())
                .allSatisfy(line -> assertThat(line).contains("ready=true"));
    }
}
