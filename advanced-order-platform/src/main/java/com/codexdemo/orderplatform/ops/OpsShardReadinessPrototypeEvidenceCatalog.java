package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessPrototypeEvidenceCatalog {

    private OpsShardReadinessPrototypeEvidenceCatalog() {
    }

    static List<Entry> entries() {
        return List.of(
                entry(
                        409,
                        "prototype-catalog",
                        "Java shard-readiness.v1 prototype catalog",
                        "Node v368",
                        OpsShardReadinessPrototypeEvidenceService.CATALOG_ENDPOINT,
                        "java-shard-readiness-prototype-catalog.v1",
                        "e/409/evidence/java-shard-readiness-prototype-catalog-v409.json",
                        List.of(
                                "freeze-minimal-shard-readiness-v1-fields",
                                "reuse-java-v153-root-readiness",
                                "reuse-java-v174-echo-boundary",
                                "reuse-route-cleanup-v408-closeout",
                                "keep-read-only-and-execution-disabled"
                        )
                )
        );
    }

    static Entry entryFor(String key) {
        return entries().stream()
                .filter(entry -> entry.key().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown prototype evidence key: " + key));
    }

    private static Entry entry(
            int javaVersion,
            String key,
            String phase,
            String nodePlanVersion,
            String endpoint,
            String profile,
            String evidencePath,
            List<String> checks
    ) {
        return new Entry(
                javaVersion,
                "Java v" + javaVersion,
                key,
                phase,
                nodePlanVersion,
                endpoint,
                profile,
                evidencePath,
                checks
        );
    }

    record Entry(
            int javaVersion,
            String version,
            String key,
            String phase,
            String nodePlanVersion,
            String endpoint,
            String profile,
            String evidencePath,
            List<String> checks
    ) {
    }
}
