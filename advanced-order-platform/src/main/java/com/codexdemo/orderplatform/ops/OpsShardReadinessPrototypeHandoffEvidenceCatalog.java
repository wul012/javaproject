package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessPrototypeHandoffEvidenceCatalog {

    private OpsShardReadinessPrototypeHandoffEvidenceCatalog() {
    }

    static List<Entry> entries() {
        return List.of(
                entry(
                        429,
                        "handoff-catalog",
                        "Java shard-readiness prototype handoff catalog",
                        OpsShardReadinessPrototypeHandoffService.CATALOG_ENDPOINT,
                        "java-shard-readiness-prototype-handoff-catalog.v1",
                        "e/429/evidence/java-shard-readiness-prototype-handoff-catalog-v429.json",
                        List.of(
                                "consume-java-v428-prototype-closeout-route",
                                "freeze-handoff-entry-shape",
                                "keep-readOnly-true",
                                "keep-executionAllowed-false",
                                "prepare-node-consumer-gate-inputs"
                        )
                )
        );
    }

    static Entry entryFor(String key) {
        return entries().stream()
                .filter(entry -> entry.key().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown prototype handoff key: " + key));
    }

    private static Entry entry(
            int javaVersion,
            String key,
            String phase,
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
                "Node v368",
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
