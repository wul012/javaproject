package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessRouteCleanupEvidenceCatalog {

    private OpsShardReadinessRouteCleanupEvidenceCatalog() {
    }

    static List<OpsShardReadinessRouteCleanupEvidenceResponse.Entry> entries() {
        return List.of(
                entry(
                        306,
                        "Node v549",
                        "route-cleanup-catalog-contract-freeze",
                        "typed-read-only-catalog-entry",
                        "java-shard-readiness-route-cleanup-catalog-contract-freeze-v306"
                )
        );
    }

    private static OpsShardReadinessRouteCleanupEvidenceResponse.Entry entry(
            int javaVersion,
            String sourceNodePlan,
            String phase,
            String evidenceType,
            String evidenceSlug
    ) {
        return new OpsShardReadinessRouteCleanupEvidenceResponse.Entry(
                javaVersion,
                sourceNodePlan,
                phase,
                evidenceType,
                "e/" + javaVersion + "/evidence/" + evidenceSlug + ".json",
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                "passed"
        );
    }
}
