package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityClassNameTrialCatalog {

    private ReadabilityClassNameTrialCatalog() {
    }

    static List<ReadabilityUpkeepRegistryResponse.ClassNameTrial> classNameTrials() {
        return List.of(
                trial(
                        "drop-root-ops-prefix-inside-readability-package",
                        "OpsShardReadinessReadabilityUpkeepRegistryService",
                        "ReadabilityUpkeepRegistryService",
                        "the package name already says this is ops readability upkeep",
                        true
                ),
                trial(
                        "short-catalog-names-inside-topic-package",
                        "OpsShardReadinessCodeWalkthroughDepthVerificationCatalog",
                        "DepthVerificationCatalog",
                        "future topic subpackages can carry shard-readiness and walkthrough context",
                        true
                ),
                trial(
                        "preserve-public-context-at-package-boundary",
                        "ReadinessRegistry",
                        "ReadabilityUpkeepRegistryResponse",
                        "public response names still describe the registry purpose",
                        true
                )
        );
    }

    private static ReadabilityUpkeepRegistryResponse.ClassNameTrial trial(
            String code,
            String oldNamePattern,
            String newNamePattern,
            String rationale,
            boolean activeForNewSubpackages
    ) {
        return new ReadabilityUpkeepRegistryResponse.ClassNameTrial(
                code,
                oldNamePattern,
                newNamePattern,
                rationale,
                activeForNewSubpackages
        );
    }
}
