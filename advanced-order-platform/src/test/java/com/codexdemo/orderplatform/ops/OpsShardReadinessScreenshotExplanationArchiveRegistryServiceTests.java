package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveRegistryServiceTests {

    @Test
    void buildsScreenshotExplanationArchiveSegmentationRegistry() {
        var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

        assertThat(response.project()).isEqualTo("advanced-order-platform");
        assertThat(response.version()).isEqualTo("Java v1763");
        assertThat(response.endpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry");
        assertThat(response.profile())
                .isEqualTo("java-shard-readiness-screenshot-explanation-archive-registry.v1");
        assertThat(response.sourcePlan()).isEqualTo("Node v367 / Java v1759-v1763");
        assertThat(response.legacyRoot()).isEqualTo("d");
        assertThat(response.nextRoot()).isEqualTo("d_runtime_screenshot_archive_next");
        assertThat(response.registryState())
                .isEqualTo("screenshot-explanation-archives-segmented-away-from-the-crowded-root");
        assertThat(response.currentArchiveAssessmentCount()).isEqualTo(2);
        assertThat(response.segmentPlanCount()).isEqualTo(3);
        assertThat(response.namingRuleCount()).isEqualTo(6);
        assertThat(response.boundaryRuleCount()).isEqualTo(8);
        assertThat(response.deniedBoundaryRuleCount()).isEqualTo(8);
        assertThat(response.verificationStepCount()).isEqualTo(5);
        assertThat(response.status()).isEqualTo("passed");
    }

    @Test
    void namesTheCurrentAndNextScreenshotArchiveRoots() {
        var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

        assertThat(response.currentArchiveAssessments())
                .extracting(OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                        .CurrentArchiveAssessment::root)
                .containsExactly("d", "d_runtime_screenshot_archive_next");
        assertThat(response.segmentPlans())
                .extracting(OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                        .ArchiveSegmentPlan::path)
                .contains(
                        "d_runtime_screenshot_archive_next/v1759-v1763",
                        "d_runtime_screenshot_archive_next/v1764-v1780"
                );
        assertThat(response.namingRules())
                .extracting(OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                        .NamingRule::code)
                .contains(
                        "range-before-version",
                        "separate-images-and-explanations",
                        "no-root-dumping"
                );
    }
}
