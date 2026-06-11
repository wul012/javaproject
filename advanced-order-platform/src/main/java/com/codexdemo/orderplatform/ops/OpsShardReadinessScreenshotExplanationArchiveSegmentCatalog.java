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
                        "transition-screenshot-explanation-segment",
                        "d_runtime_screenshot_archive_next/v1759-v1763",
                        "v1759-v1763",
                        "preserve the first segmentation handoff without making the transitional root permanent",
                        false
                ),
                segment(
                        "closed-f-screenshot-explanation-segment",
                        "f/v1764-v1768",
                        "v1764-v1768",
                        "preserve the canonical f-root policy correction and archive handoff",
                        false
                ),
                segment(
                        "current-f-screenshot-explanation-segment",
                        "f/v1769-v1773",
                        "v1769-v1773",
                        "hold the machine-checkable f-root layout inventory and guard batch",
                        true
                ),
                segment(
                        "next-f-screenshot-explanation-segment",
                        "f/v1774-v1785",
                        "v1774-v1785",
                        "reserve the next f range before the current segment becomes crowded",
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
