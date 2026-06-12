package com.codexdemo.orderplatform.ops.maintenance.readability;

import java.util.List;

final class ReadabilityTopicCatalog {

    private ReadabilityTopicCatalog() {
    }

    static List<ReadabilityUpkeepRegistryResponse.TopicMap> topics() {
        return List.of(
                topic(
                        "shard-readiness",
                        "docs/ops/shard-readiness-map.md",
                        "OpsShardReadiness*",
                        "Can a maintainer find read-only readiness entry points before opening services?",
                        true
                ),
                topic(
                        "walkthrough-quality",
                        "docs/ops/walkthrough-registry-map.md",
                        "OpsShardReadinessCodeWalkthrough*",
                        "Can a maintainer find the explanation quality gates and depth rules?",
                        true
                ),
                topic(
                        "archive-layout",
                        "docs/ops/archive-layout-map.md",
                        "OpsScreenshotExplanation* and OpsCodeWalkthroughArchive*",
                        "Can a maintainer find archive layout rules without scanning old folders?",
                        true
                ),
                topic(
                        "blocked-execution-context",
                        "docs/ops/shard-readiness-map.md",
                        "*Boundary*, *Blocked*, *Execution*",
                        "Can a maintainer confirm runtime actions remain denied?",
                        true
                ),
                topic(
                        "evidence-registry",
                        "docs/ops/README.md",
                        "*Registry*, *Catalog*, *Renderer*, *Support*",
                        "Can a maintainer identify the standard registry layers?",
                        true
                )
        );
    }

    private static ReadabilityUpkeepRegistryResponse.TopicMap topic(
            String code,
            String docsPath,
            String sourcePattern,
            String maintainerQuestion,
            boolean indexed
    ) {
        return new ReadabilityUpkeepRegistryResponse.TopicMap(
                code,
                docsPath,
                sourcePattern,
                maintainerQuestion,
                indexed
        );
    }
}
