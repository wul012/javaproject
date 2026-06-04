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
                ),
                entry(
                        431,
                        "handoff-endpoint-inventory",
                        "Java shard-readiness prototype handoff endpoint inventory",
                        OpsShardReadinessPrototypeHandoffService.ENDPOINT_INVENTORY_ENDPOINT,
                        "java-shard-readiness-prototype-handoff-endpoint-inventory.v1",
                        "e/431/evidence/java-shard-readiness-prototype-handoff-endpoint-inventory-v431.json",
                        List.of(
                                "inventory-prototype-catalog-route-present",
                                "inventory-prototype-closeout-route-present",
                                "inventory-handoff-catalog-route-present",
                                "inventory-all-routes-read-only",
                                "inventory-no-root-controller-regression"
                        )
                ),
                entry(
                        433,
                        "handoff-boundary-matrix",
                        "Java shard-readiness prototype handoff boundary matrix",
                        OpsShardReadinessPrototypeHandoffService.BOUNDARY_MATRIX_ENDPOINT,
                        "java-shard-readiness-prototype-handoff-boundary-matrix.v1",
                        "e/433/evidence/java-shard-readiness-prototype-handoff-boundary-matrix-v433.json",
                        List.of(
                                "preserve-read-only-contract-boundary",
                                "preserve-executionAllowed-false",
                                "forbid-write-routing",
                                "forbid-managed-audit-connection",
                                "forbid-node-process-control"
                        )
                ),
                entry(
                        435,
                        "handoff-consumer-verification-checklist",
                        "Java shard-readiness prototype handoff consumer verification checklist",
                        OpsShardReadinessPrototypeHandoffService.CONSUMER_VERIFICATION_CHECKLIST_ENDPOINT,
                        "java-shard-readiness-prototype-handoff-consumer-verification-checklist.v1",
                        "e/435/evidence/java-shard-readiness-prototype-handoff-consumer-verification-checklist-v435.json",
                        List.of(
                                "verify-contractName-shard-readiness-v1",
                                "verify-readOnly-true",
                                "verify-executionAllowed-false",
                                "verify-status-passed",
                                "verify-digest-present"
                        )
                ),
                entry(
                        437,
                        "handoff-read-window-checklist",
                        "Java shard-readiness prototype handoff read window checklist",
                        OpsShardReadinessPrototypeHandoffService.READ_WINDOW_CHECKLIST_ENDPOINT,
                        "java-shard-readiness-prototype-handoff-read-window-checklist.v1",
                        "e/437/evidence/java-shard-readiness-prototype-handoff-read-window-checklist-v437.json",
                        List.of(
                                "read-window-java-health-route-observed",
                                "read-window-ops-overview-route-observed",
                                "read-window-shard-readiness-route-observed",
                                "read-window-upstream-probes-only",
                                "read-window-actions-disabled"
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
