package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessPrototypeEvidenceServiceTests {

    @Test
    void buildsShardReadinessPrototypeCatalog() {
        OpsShardReadinessPrototypeCatalogResponse catalog = service().catalog();

        assertThat(catalog.project()).isEqualTo("advanced-order-platform");
        assertThat(catalog.version()).isEqualTo("Java v409");
        assertThat(catalog.readOnly()).isTrue();
        assertThat(catalog.executionAllowed()).isFalse();
        assertThat(catalog.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/prototype-catalog");
        assertThat(catalog.profile()).isEqualTo("java-shard-readiness-prototype-catalog.v1");
        assertThat(catalog.contractName()).isEqualTo("shard-readiness.v1");
        assertThat(catalog.entryCount()).isEqualTo(1);
        assertThat(catalog.requiredFields())
                .containsExactlyElementsOf(OpsShardReadinessV1Contract.minimalFields());
        assertThat(catalog.forbiddenOperations())
                .contains("write-routing", "managed-audit-connection");
        assertThat(catalog.entries())
                .singleElement()
                .satisfies(entry -> {
                    assertThat(entry.javaVersion()).isEqualTo(409);
                    assertThat(entry.key()).isEqualTo("prototype-catalog");
                    assertThat(entry.nodePlanVersion()).isEqualTo("Node v368");
                    assertThat(entry.checks())
                            .contains("reuse-route-cleanup-v408-closeout");
                });
        assertThat(catalog.status()).isEqualTo("passed");
    }

    private OpsShardReadinessPrototypeEvidenceService service() {
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
