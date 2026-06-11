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
                        "legacy-runtime-root-closed",
                        "keep historical v116-v152 records in place and route new screenshot explanations to f"
                ),
                assessment(
                        "d_runtime_screenshot_archive_next",
                        1,
                        2,
                        "transition-segment-root-closed-after-v1763",
                        "preserve the v1759-v1763 segmentation handoff but do not use it as the continuing root"
                ),
                assessment(
                        "f",
                        1,
                        2,
                        "active-canonical-screenshot-explanation-root",
                        "use f/<version-range>/<version>/images and explanations for new screenshot evidence"
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
