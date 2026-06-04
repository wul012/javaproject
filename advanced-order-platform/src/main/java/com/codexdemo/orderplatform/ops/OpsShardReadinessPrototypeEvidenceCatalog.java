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
                ),
                entry(
                        411,
                        "prototype-fixture-echo",
                        "Java shard-readiness.v1 fixture echo",
                        "Node v368",
                        OpsShardReadinessPrototypeEvidenceService.FIXTURE_ECHO_ENDPOINT,
                        "java-shard-readiness-prototype-fixture-echo.v1",
                        "e/411/evidence/java-shard-readiness-prototype-fixture-echo-v411.json",
                        List.of(
                                "echo-project-advanced-order-platform",
                                "echo-contract-shard-readiness-v1",
                                "echo-shard-enabled-false",
                                "echo-routing-mode-fixture",
                                "echo-status-passed"
                        )
                ),
                entry(
                        413,
                        "prototype-field-alignment",
                        "Java shard-readiness.v1 field alignment",
                        "Node v368",
                        OpsShardReadinessPrototypeEvidenceService.FIELD_ALIGNMENT_ENDPOINT,
                        "java-shard-readiness-prototype-field-alignment.v1",
                        "e/413/evidence/java-shard-readiness-prototype-field-alignment-v413.json",
                        List.of(
                                "field-project-present",
                                "field-version-present",
                                "field-readOnly-true",
                                "field-executionAllowed-false",
                                "field-shardEnabled-false",
                                "field-shardCount-zero",
                                "field-slotCount-zero",
                                "field-routingMode-fixture",
                                "field-evidencePath-present",
                                "field-status-passed"
                        )
                ),
                entry(
                        415,
                        "prototype-read-only-integration-bridge",
                        "Java read-only integration bridge for shard-readiness.v1",
                        "Node v368",
                        OpsShardReadinessPrototypeEvidenceService.READ_ONLY_INTEGRATION_BRIDGE_ENDPOINT,
                        "java-shard-readiness-prototype-read-only-integration-bridge.v1",
                        "e/415/evidence/java-shard-readiness-prototype-read-only-integration-bridge-v415.json",
                        List.of(
                                "bridge-node-v367-read-targets-passed",
                                "bridge-node-v368-archive-verified",
                                "bridge-java-does-not-start-services",
                                "bridge-executionAllowed-false",
                                "bridge-upstream-actions-disabled"
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
