package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessScreenshotExplanationArchiveSegmentCatalog {

    private OpsShardReadinessScreenshotExplanationArchiveSegmentCatalog() {
    }

    static List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.ArchiveSegmentPlan>
            segmentPlans() {
        return List.of(
                segment(
                        "historical-runtime-archive",
                        "d",
                        "v116-v152",
                        "preserve existing runtime screenshots and explanations without moving old records",
                        false
                ),
                segment(
                        "current-screenshot-explanation-segment",
                        "d_runtime_screenshot_archive_next/v1759-v1763",
                        "v1759-v1763",
                        "hold this five-version archive segmentation registry batch",
                        true
                ),
                segment(
                        "next-screenshot-explanation-segment",
                        "d_runtime_screenshot_archive_next/v1764-v1780",
                        "v1764-v1780",
                        "pre-declare the next range when screenshot evidence grows again",
                        true
                )
        );
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.ArchiveSegmentPlan
            segment(
                    String segment,
                    String path,
                    String versionRange,
                    String purpose,
                    boolean active
            ) {
        return new OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.ArchiveSegmentPlan(
                segment,
                path,
                versionRange,
                purpose,
                active
        );
    }
}
