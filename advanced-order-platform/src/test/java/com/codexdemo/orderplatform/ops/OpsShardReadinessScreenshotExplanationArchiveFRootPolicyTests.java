package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessScreenshotExplanationArchiveFRootPolicyTests {

    @Test
    void keepsNewScreenshotExplanationWorkUnderFRoot() {
        var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

        assertThat(response.nextRoot()).isEqualTo("f");
        assertThat(response.currentArchiveAssessments())
                .anySatisfy(assessment -> assertThat(assessment)
                        .extracting(
                                OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                                        .CurrentArchiveAssessment::root,
                                OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                                        .CurrentArchiveAssessment::status
                        )
                        .containsExactly("f",
                                "active-canonical-screenshot-explanation-root"));
        assertThat(response.segmentPlans())
                .filteredOn(OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                        .ArchiveSegmentPlan::active)
                .extracting(OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                        .ArchiveSegmentPlan::path)
                .allSatisfy(path -> assertThat(path).startsWith("f/"));
    }

    @Test
    void keepsTransitionRootClosedForNewSegments() {
        var response = OpsShardReadinessScreenshotExplanationArchiveRegistryTestSupport.registry();

        assertThat(response.currentArchiveAssessments())
                .anySatisfy(assessment -> assertThat(assessment)
                        .extracting(
                                OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                                        .CurrentArchiveAssessment::root,
                                OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                                        .CurrentArchiveAssessment::status
                        )
                        .containsExactly("d_runtime_screenshot_archive_next",
                                "transition-segment-root-closed-after-v1763"));
        assertThat(response.segmentPlans())
                .filteredOn(segment -> segment.path().startsWith("d_runtime_screenshot_archive_next"))
                .allSatisfy(segment -> assertThat(segment.active()).isFalse());
    }
}
