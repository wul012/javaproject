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
                ),
                entry(
                        307,
                        "Node v538",
                        "latest-sibling-evidence-intake",
                        "read-only-sibling-intake-entry",
                        "java-shard-readiness-route-cleanup-latest-sibling-evidence-intake-v307"
                ),
                entry(
                        308,
                        "Node v540",
                        "latest-sibling-evidence-report",
                        "read-only-sibling-report-entry",
                        "java-shard-readiness-route-cleanup-latest-sibling-evidence-report-v308"
                ),
                entry(
                        309,
                        "Node v541",
                        "latest-sibling-evidence-report-archive",
                        "read-only-report-archive-entry",
                        "java-shard-readiness-route-cleanup-latest-sibling-evidence-report-archive-v309"
                ),
                entry(
                        310,
                        "Node v542",
                        "latest-sibling-evidence-archive-verification",
                        "read-only-archive-verification-entry",
                        "java-shard-readiness-route-cleanup-latest-sibling-evidence-archive-verification-v310"
                ),
                entry(
                        311,
                        "Node v543",
                        "latest-sibling-evidence-archive-verification-route",
                        "read-only-archive-verification-route-entry",
                        "java-shard-readiness-route-cleanup-latest-sibling-archive-verification-route-v311"
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
