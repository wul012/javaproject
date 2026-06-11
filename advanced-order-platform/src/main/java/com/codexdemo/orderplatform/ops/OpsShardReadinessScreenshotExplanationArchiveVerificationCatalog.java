package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessScreenshotExplanationArchiveVerificationCatalog {

    private OpsShardReadinessScreenshotExplanationArchiveVerificationCatalog() {
    }

    static List<OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.VerificationStep>
            verificationSteps() {
        return List.of(
                step(
                        "archive-route-tests",
                        "OpsShardReadinessScreenshotExplanationArchiveRoutePathsTests",
                        "shared route constant and endpoint stability"
                ),
                step(
                        "archive-registry-service-tests",
                        "OpsShardReadinessScreenshotExplanationArchiveRegistryServiceTests",
                        "counts, roots, status, and segmentation policy"
                ),
                step(
                        "archive-boundary-tests",
                        "OpsShardReadinessScreenshotExplanationArchiveRegistryBoundaryTests",
                        "no screenshot capture, no historical move, no runtime side effects"
                ),
                step(
                        "archive-doc-tests",
                        "OpsScreenshotExplanationArchiveSegmentationDocsTests",
                        "next root README and segment README remain present"
                ),
                step(
                        "full-maven-regression",
                        "mvn -q test",
                        "full Java regression before tags are pushed"
                )
        );
    }

    private static OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.VerificationStep
            step(String name, String commandOrClass, String scope) {
        return new OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.VerificationStep(
                name,
                commandOrClass,
                scope,
                true
        );
    }
}
