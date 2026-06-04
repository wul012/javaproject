package com.codexdemo.orderplatform.ops;

import java.util.List;

final class OpsShardReadinessPrototypeConsumerGateEvidenceCatalog {

    private OpsShardReadinessPrototypeConsumerGateEvidenceCatalog() {
    }

    static List<Entry> entries() {
        return List.of(
                entry(
                        449,
                        "consumer-gate-catalog",
                        "Java shard-readiness prototype consumer gate catalog",
                        OpsShardReadinessPrototypeConsumerGateService.CATALOG_ENDPOINT,
                        "java-shard-readiness-prototype-consumer-gate-catalog.v1",
                        "e/449/evidence/java-shard-readiness-prototype-consumer-gate-catalog-v449.json",
                        List.of(
                                "consume-java-v448-prototype-handoff-closeout-route",
                                "freeze-node-v370-consumer-gate-input-shape",
                                "keep-shard-readiness-v1-contract-name",
                                "keep-readOnly-true",
                                "keep-executionAllowed-false"
                        )
                ),
                entry(
                        451,
                        "consumer-gate-source-inventory",
                        "Java shard-readiness prototype consumer gate source inventory",
                        OpsShardReadinessPrototypeConsumerGateService.SOURCE_INVENTORY_ENDPOINT,
                        "java-shard-readiness-prototype-consumer-gate-source-inventory.v1",
                        "e/451/evidence/java-shard-readiness-prototype-consumer-gate-source-inventory-v451.json",
                        List.of(
                                "consume-handoff-catalog-route",
                                "consume-handoff-closeout-route",
                                "consume-shard-readiness-v1-contract",
                                "verify-source-entry-count-10",
                                "keep-node-v370-consumer-gate-read-only"
                        )
                )
        );
    }

    static Entry entryFor(String key) {
        return entries().stream()
                .filter(entry -> entry.key().equals(key))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown prototype consumer gate key: " + key));
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
                "Node v370",
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
