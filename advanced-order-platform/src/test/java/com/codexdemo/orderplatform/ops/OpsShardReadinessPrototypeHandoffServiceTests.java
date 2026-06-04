package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessPrototypeHandoffServiceTests {

    @Test
    void buildsPrototypeHandoffCatalogFromPrototypeCloseout() {
        OpsShardReadinessPrototypeHandoffCatalogResponse catalog = service().catalog();

        assertThat(catalog.project()).isEqualTo("advanced-order-platform");
        assertThat(catalog.version()).isEqualTo(catalog.entries().getLast().version());
        assertThat(catalog.readOnly()).isTrue();
        assertThat(catalog.executionAllowed()).isFalse();
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-catalog");
        assertThat(catalog.profile()).isEqualTo("java-shard-readiness-prototype-handoff-catalog.v1");
        assertThat(catalog.sourcePrototypeVersion()).isEqualTo("Java v427");
        assertThat(catalog.sourcePrototypeEndpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-closeout");
        assertThat(catalog.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(catalog.entryCount()).isEqualTo(catalog.entries().size());
        assertThat(catalog.entries())
                .first()
                .satisfies(entry -> {
                    assertThat(entry.javaVersion()).isEqualTo(429);
                    assertThat(entry.key()).isEqualTo("handoff-catalog");
                    assertThat(entry.nodePlanVersion()).isEqualTo("Node v368");
                    assertThat(entry.checks())
                            .contains("consume-java-v428-prototype-closeout-route");
                });
        assertThat(catalog.forbiddenOperations()).contains("active-shard-router");
        assertThat(catalog.status()).isEqualTo("passed");
    }

    @Test
    void buildsEndpointInventoryEvidenceForReadOnlyRouteHandoff() {
        OpsShardReadinessPrototypeHandoffEvidenceResponse evidence = service().endpointInventory();

        assertThat(evidence.version()).isEqualTo("Java v431");
        assertThat(evidence.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-handoff-endpoint-inventory");
        assertThat(evidence.profile())
                .isEqualTo("java-shard-readiness-prototype-handoff-endpoint-inventory.v1");
        assertThat(evidence.checks())
                .contains(
                        "inventory-prototype-catalog-route-present",
                        "inventory-prototype-closeout-route-present",
                        "inventory-handoff-catalog-route-present",
                        "inventory-no-root-controller-regression"
                );
        assertThat(evidence.status()).isEqualTo("passed");
    }

    @Test
    void allHandoffCatalogEntriesProducePassedReadOnlyEvidence() {
        OpsShardReadinessPrototypeHandoffService service = service();

        assertThat(OpsShardReadinessPrototypeHandoffEvidenceCatalog.entries())
                .allSatisfy(entry -> {
                    OpsShardReadinessPrototypeHandoffEvidenceResponse evidence =
                            service.evidence(entry.key());

                    assertThat(evidence.version()).isEqualTo(entry.version());
                    assertThat(evidence.endpoint()).isEqualTo(entry.endpoint());
                    assertThat(evidence.profile()).isEqualTo(entry.profile());
                    assertThat(evidence.readOnly()).isTrue();
                    assertThat(evidence.executionAllowed()).isFalse();
                    assertThat(evidence.sourceCatalogVersion()).isEqualTo("Java v427");
                    assertThat(evidence.sourceCloseoutVersion()).isEqualTo("Java v427");
                    assertThat(evidence.digestValue()).matches("[0-9a-f]{64}");
                    assertThat(evidence.status()).isEqualTo("passed");
                });
    }

    private OpsShardReadinessPrototypeHandoffService service() {
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
