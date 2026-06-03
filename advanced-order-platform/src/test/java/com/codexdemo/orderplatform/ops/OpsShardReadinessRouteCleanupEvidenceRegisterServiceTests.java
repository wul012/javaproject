package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupEvidenceRegisterServiceTests {

    @Test
    void buildsReadOnlyEvidenceRegisterFromEndpointManifest() {
        OpsShardReadinessRouteCleanupEvidenceRegisterResponse register =
                new OpsShardReadinessRouteCleanupEvidenceRegisterService(
                        OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
                        OpsShardReadinessRouteCleanupServiceFixtures.finalDigestService()
                ).register();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(370);
        assertThat(register.project()).isEqualTo("advanced-order-platform");
        assertThat(register.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(register.readOnly()).isTrue();
        assertThat(register.executionAllowed()).isFalse();
        assertThat(register.registerEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-evidence-register");
        assertThat(register.registerProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-evidence-register.v1");
        assertThat(register.registeredEvidenceCount()).isEqualTo(register.registeredEvidence().size());
        assertThat(register.registeredEvidenceCount()).isGreaterThanOrEqualTo(23);
        assertThat(register.registeredEvidence())
                .extracting(OpsShardReadinessRouteCleanupEvidenceRegisterResponse.RegisteredEvidence::endpoint)
                .contains(
                        "/api/v1/ops/shard-readiness/route-cleanup-audit-trail",
                        "/api/v1/ops/shard-readiness/route-cleanup-acceptance-receipt",
                        "/api/v1/ops/shard-readiness/route-cleanup-evidence-register"
                );
        assertThat(register.registeredEvidence())
                .allSatisfy(evidence -> {
                    assertThat(evidence.readOnly()).isTrue();
                    assertThat(evidence.executionAllowed()).isFalse();
                    assertThat(evidence.status()).isEqualTo("passed");
                });
        assertThat(register.digestValue()).matches("[0-9a-f]{64}");
        assertThat(register.status()).isEqualTo("passed");
    }
}
