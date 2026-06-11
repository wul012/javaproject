package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessScreenshotExplanationArchiveCurrentCatalog {

    private OpsShardReadinessScreenshotExplanationArchiveCurrentCatalog() {
    }

    static List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
            .CurrentArchiveAssessment> currentArchiveAssessments() {
        return List.of(
                assessment(
                        "d",
                        32,
                        50,
                        "legacy-active-root-closed-for-new-segmented-work",
                        "keep historical v116-v152 records in place and route new screenshot explanations to the next root"
                ),
                assessment(
                        "d_runtime_screenshot_archive_next",
                        1,
                        2,
                        "active-segmented-root",
                        "continue with version-range folders before any single folder grows crowded"
                )
        );
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
            .CurrentArchiveAssessment assessment(
                    String root,
                    int versionDirectoryCount,
                    int fileCount,
                    String status,
                    String nextAction
            ) {
        return new OpsShardReadinessScreenshotExplanationArchiveRegistryResponse
                .CurrentArchiveAssessment(
                root,
                versionDirectoryCount,
                fileCount,
                status,
                nextAction
        );
    }
}
