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
                ),
                entry(
                        453,
                        "consumer-gate-minimal-field-checklist",
                        "Java shard-readiness prototype consumer gate minimal field checklist",
                        OpsShardReadinessPrototypeConsumerGateService.MINIMAL_FIELD_CHECKLIST_ENDPOINT,
                        "java-shard-readiness-prototype-consumer-gate-minimal-field-checklist.v1",
                        "e/453/evidence/java-shard-readiness-prototype-consumer-gate-minimal-field-checklist-v453.json",
                        List.of(
                                "field-project-required",
                                "field-version-required",
                                "field-readOnly-true-required",
                                "field-executionAllowed-false-required",
                                "field-shardEnabled-shardCount-slotCount-routingMode-evidencePath-status-required"
                        )
                ),
                entry(
                        455,
                        "consumer-gate-route-topology-preview",
                        "Java shard-readiness prototype consumer gate route topology preview",
                        OpsShardReadinessPrototypeConsumerGateService.ROUTE_TOPOLOGY_PREVIEW_ENDPOINT,
                        "java-shard-readiness-prototype-consumer-gate-route-topology-preview.v1",
                        "e/455/evidence/java-shard-readiness-prototype-consumer-gate-route-topology-preview-v455.json",
                        List.of(
                                "topology-java-health-read-target",
                                "topology-ops-overview-read-target",
                                "topology-shard-readiness-prototype-read-target",
                                "topology-prototype-handoff-closeout-read-target",
                                "topology-consumer-gate-catalog-read-target"
                        )
                ),
                entry(
                        457,
                        "consumer-gate-boundary-matrix",
                        "Java shard-readiness prototype consumer gate boundary matrix",
                        OpsShardReadinessPrototypeConsumerGateService.BOUNDARY_MATRIX_ENDPOINT,
                        "java-shard-readiness-prototype-consumer-gate-boundary-matrix.v1",
                        "e/457/evidence/java-shard-readiness-prototype-consumer-gate-boundary-matrix-v457.json",
                        List.of(
                                "boundary-forbid-write-routing",
                                "boundary-forbid-credential-value-read",
                                "boundary-forbid-raw-endpoint-parse",
                                "boundary-forbid-managed-audit-connection",
                                "boundary-forbid-node-start-stop-upstreams"
                        )
                ),
                entry(
                        459,
                        "consumer-gate-digest-acceptance",
                        "Java shard-readiness prototype consumer gate digest acceptance",
                        OpsShardReadinessPrototypeConsumerGateService.DIGEST_ACCEPTANCE_ENDPOINT,
                        "java-shard-readiness-prototype-consumer-gate-digest-acceptance.v1",
                        "e/459/evidence/java-shard-readiness-prototype-consumer-gate-digest-acceptance-v459.json",
                        List.of(
                                "digest-covers-handoff-catalog-version",
                                "digest-covers-handoff-closeout-version",
                                "digest-covers-handoff-closeout-digest",
                                "digest-covers-consumer-gate-entry-key-profile",
                                "digest-covers-consumer-gate-evidence-path"
                        )
                ),
                entry(
                        461,
                        "consumer-gate-ci-batch-plan",
                        "Java shard-readiness prototype consumer gate CI batch plan",
                        OpsShardReadinessPrototypeConsumerGateService.CI_BATCH_PLAN_ENDPOINT,
                        "java-shard-readiness-prototype-consumer-gate-ci-batch-plan.v1",
                        "e/461/evidence/java-shard-readiness-prototype-consumer-gate-ci-batch-plan-v461.json",
                        List.of(
                                "ci-focused-consumer-gate-service-tests-first",
                                "ci-grouped-controller-and-route-tests-second",
                                "ci-full-maven-test-before-push",
                                "ci-smoke-only-with-explicit-user-window",
                                "ci-background-processes-stopped-after-run"
                        )
                ),
                entry(
                        463,
                        "consumer-gate-archive-manifest",
                        "Java shard-readiness prototype consumer gate archive manifest",
                        OpsShardReadinessPrototypeConsumerGateService.ARCHIVE_MANIFEST_ENDPOINT,
                        "java-shard-readiness-prototype-consumer-gate-archive-manifest.v1",
                        "e/463/evidence/java-shard-readiness-prototype-consumer-gate-archive-manifest-v463.json",
                        List.of(
                                "archive-consumer-gate-evidence-paths-versioned",
                                "archive-source-handoff-paths-retained",
                                "archive-node-v370-can-pin-versioned-paths",
                                "archive-runtime-artifacts-not-required",
                                "archive-routes-remain-read-only"
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
