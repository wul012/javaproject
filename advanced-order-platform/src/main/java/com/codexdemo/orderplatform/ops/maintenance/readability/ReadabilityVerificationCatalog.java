package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityVerificationCatalog {

    private ReadabilityVerificationCatalog() {
    }

    static List<ReadabilityUpkeepRegistryResponse.VerificationStep> verificationSteps() {
        return List.of(
                step(
                        "route-path-tests",
                        "ReadabilityUpkeepRoutePathsTests",
                        "path constants and endpoint",
                        true
                ),
                step(
                        "registry-service-tests",
                        "ReadabilityUpkeepRegistryServiceTests",
                        "counts, status, checks, source advice",
                        true
                ),
                step(
                        "registry-renderer-tests",
                        "ReadabilityUpkeepRegistryRendererTests",
                        "stable markdown section generation",
                        true
                ),
                step(
                        "registry-boundary-tests",
                        "ReadabilityUpkeepBoundaryTests",
                        "read-only and denied runtime actions",
                        true
                ),
                step(
                        "docs-compliance-tests",
                        "ReadabilityUpkeepDocsTests",
                        "docs/ops maps and registry template",
                        true
                ),
                step(
                        "walkthrough-archive-compliance-tests",
                        "OpsCodeWalkthroughArchiveComplianceTests",
                        "Chinese longform walkthrough gate",
                        true
                )
        );
    }

    private static ReadabilityUpkeepRegistryResponse.VerificationStep step(
            String name,
            String commandOrClass,
            String scope,
            boolean required
    ) {
        return new ReadabilityUpkeepRegistryResponse.VerificationStep(
                name,
                commandOrClass,
                scope,
                required
        );
    }
}
