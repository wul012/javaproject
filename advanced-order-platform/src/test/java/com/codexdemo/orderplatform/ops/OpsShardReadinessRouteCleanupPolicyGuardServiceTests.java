package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupPolicyGuardServiceTests {

    @Test
    void keepsDisallowedCapabilitiesBlockedByReadOnlyPolicy() {
        OpsShardReadinessRouteCleanupPolicyGuardResponse guard =
                new OpsShardReadinessRouteCleanupPolicyGuardService(
                        operationalSnapshotService(),
                        new OpsShardReadinessRouteCleanupEvidenceRegisterService(
                                OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
                                OpsShardReadinessRouteCleanupServiceFixtures.finalDigestService()
                        )
                ).guard();

        assertThat(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion()).isGreaterThanOrEqualTo(374);
        assertThat(guard.project()).isEqualTo("advanced-order-platform");
        assertThat(guard.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(guard.readOnly()).isTrue();
        assertThat(guard.executionAllowed()).isFalse();
        assertThat(guard.policyGuardEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-policy-guard");
        assertThat(guard.policyGuardProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-policy-guard.v1");
        assertThat(guard.guardRuleCount()).isEqualTo(7);
        assertThat(guard.guardRules())
                .extracting(OpsShardReadinessRouteCleanupPolicyGuardResponse.GuardRule::name)
                .containsExactly(
                        "write-routing",
                        "active-shard-router",
                        "credential-value",
                        "raw-endpoint",
                        "managed-audit-connection",
                        "deployment-rollback",
                        "node-autostart"
                );
        assertThat(guard.guardRules())
                .allSatisfy(rule -> {
                    assertThat(rule.allowed()).isFalse();
                    assertThat(rule.status()).isEqualTo("blocked-by-policy");
                });
        assertThat(guard.decision()).isEqualTo("read-only-policy-guard-held");
        assertThat(guard.status()).isEqualTo("passed");
    }

    private OpsShardReadinessRouteCleanupOperationalSnapshotService operationalSnapshotService() {
        return new OpsShardReadinessRouteCleanupOperationalSnapshotService(
                OpsShardReadinessRouteCleanupServiceFixtures.continuityReportService(),
                OpsShardReadinessRouteCleanupServiceFixtures.endpointManifestService(),
                new OpsShardReadinessRouteCleanupAcceptanceReceiptService(
                        new OpsShardReadinessRouteCleanupAuditTrailService(),
                        OpsShardReadinessRouteCleanupServiceFixtures.extendedCloseoutService()
                )
        );
    }
}
