package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessPrototypeConsumerGateServiceTests {

    @Test
    void buildsPrototypeConsumerGateCatalogFromHandoffCloseout() {
        OpsShardReadinessPrototypeConsumerGateCatalogResponse catalog = service().catalog();

        assertThat(catalog.project()).isEqualTo("advanced-order-platform");
        assertThat(catalog.version()).isEqualTo(catalog.entries().getLast().version());
        assertThat(catalog.readOnly()).isTrue();
        assertThat(catalog.executionAllowed()).isFalse();
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-consumer-gate-catalog");
        assertThat(catalog.profile()).isEqualTo(
                "java-shard-readiness-prototype-consumer-gate-catalog.v1");
        assertThat(catalog.sourceHandoffVersion()).isEqualTo("Java v447");
        assertThat(catalog.sourceHandoffEndpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-closeout");
        assertThat(catalog.sourceHandoffEntryCount()).isEqualTo(10);
        assertThat(catalog.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(catalog.entryCount()).isEqualTo(catalog.entries().size());
        assertThat(catalog.entries())
                .first()
                .satisfies(entry -> {
                    assertThat(entry.javaVersion()).isEqualTo(449);
                    assertThat(entry.key()).isEqualTo("consumer-gate-catalog");
                    assertThat(entry.nodePlanVersion()).isEqualTo("Node v370");
                    assertThat(entry.checks())
                            .contains("consume-java-v448-prototype-handoff-closeout-route");
                });
        assertThat(catalog.forbiddenOperations()).contains("active-shard-router");
        assertThat(catalog.status()).isEqualTo("passed");
    }

    @Test
    void buildsSourceInventoryEvidenceForNodeConsumerGate() {
        OpsShardReadinessPrototypeConsumerGateEvidenceResponse evidence = service().sourceInventory();

        assertThat(evidence.version()).isEqualTo("Java v451");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-consumer-gate-source-inventory");
        assertThat(evidence.profile()).isEqualTo(
                "java-shard-readiness-prototype-consumer-gate-source-inventory.v1");
        assertThat(evidence.checks())
                .contains(
                        "consume-handoff-catalog-route",
                        "consume-handoff-closeout-route",
                        "consume-shard-readiness-v1-contract",
                        "verify-source-entry-count-10",
                        "keep-node-v370-consumer-gate-read-only"
                );
        assertThat(evidence.evidenceRefs())
                .contains("prototype-handoff-closeout:/api/v1/ops/shard-readiness/prototype-handoff-closeout");
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsMinimalFieldChecklistEvidence() {
        OpsShardReadinessPrototypeConsumerGateEvidenceResponse evidence = service().minimalFieldChecklist();

        assertThat(evidence.version()).isEqualTo("Java v453");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-consumer-gate-minimal-field-checklist");
        assertThat(evidence.profile()).isEqualTo(
                "java-shard-readiness-prototype-consumer-gate-minimal-field-checklist.v1");
        assertThat(evidence.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(evidence.checks())
                .contains(
                        "field-project-required",
                        "field-version-required",
                        "field-readOnly-true-required",
                        "field-executionAllowed-false-required",
                        "field-shardEnabled-shardCount-slotCount-routingMode-evidencePath-status-required"
                );
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void buildsRouteTopologyPreviewEvidence() {
        OpsShardReadinessPrototypeConsumerGateEvidenceResponse evidence = service().routeTopologyPreview();

        assertThat(evidence.version()).isEqualTo("Java v455");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-consumer-gate-route-topology-preview");
        assertThat(evidence.profile()).isEqualTo(
                "java-shard-readiness-prototype-consumer-gate-route-topology-preview.v1");
        assertThat(evidence.checks())
                .contains(
                        "topology-java-health-read-target",
                        "topology-ops-overview-read-target",
                        "topology-shard-readiness-prototype-read-target",
                        "topology-prototype-handoff-closeout-read-target",
                        "topology-consumer-gate-catalog-read-target"
                );
        assertThat(evidence.forbiddenOperations()).contains("active-shard-router");
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void allConsumerGateEntriesProducePassedReadOnlyEvidence() {
        OpsShardReadinessPrototypeConsumerGateService service = service();

        assertThat(OpsShardReadinessPrototypeConsumerGateEvidenceCatalog.entries())
                .allSatisfy(entry -> {
                    OpsShardReadinessPrototypeConsumerGateEvidenceResponse evidence =
                            service.evidence(entry.key());

                    assertThat(evidence.version()).isEqualTo(entry.version());
                    assertThat(evidence.endpoint()).isEqualTo(entry.endpoint());
                    assertThat(evidence.profile()).isEqualTo(entry.profile());
                    assertThat(evidence.readOnly()).isTrue();
                    assertThat(evidence.executionAllowed()).isFalse();
                    assertThat(evidence.sourceHandoffVersion()).isEqualTo("Java v447");
                    assertThat(evidence.sourceHandoffEndpoint()).isEqualTo(
                            "/api/v1/ops/shard-readiness/prototype-handoff-closeout");
                    assertThat(evidence.sourceHandoffEvidencePath()).isEqualTo(
                            "e/447/evidence/java-shard-readiness-prototype-handoff-closeout-v447.json");
                    assertThat(evidence.digestValue()).matches("[0-9a-f]{64}");
                    assertThat(evidence.status()).isEqualTo("passed");
                });
    }

    private OpsShardReadinessPrototypeConsumerGateService service() {
        return new OpsShardReadinessPrototypeConsumerGateService(handoffService());
    }

    private OpsShardReadinessPrototypeHandoffService handoffService() {
        return new OpsShardReadinessPrototypeHandoffService(prototypeEvidenceService());
    }

    private OpsShardReadinessPrototypeEvidenceService prototypeEvidenceService() {
        OpsShardReadinessEvidenceIndexService evidenceIndexService = new OpsShardReadinessEvidenceIndexService();
        OpsShardReadinessEvidenceVerificationService evidenceVerificationService =
                new OpsShardReadinessEvidenceVerificationService(evidenceIndexService);
        OpsShardReadinessEvidenceHandoffService evidenceHandoffService =
                new OpsShardReadinessEvidenceHandoffService(evidenceIndexService, evidenceVerificationService);
        OpsShardReadinessEchoService echoService = new OpsShardReadinessEchoService(
                new OpsShardReadinessService(),
                new OpsShardReadinessHardeningService(),
                evidenceIndexService,
                evidenceHandoffService
        );
        return new OpsShardReadinessPrototypeEvidenceService(
                new OpsShardReadinessService(),
                echoService,
                OpsShardReadinessRouteCleanupPostCompletionServiceFixtures.postCompletionCloseoutService()
        );
    }
}
