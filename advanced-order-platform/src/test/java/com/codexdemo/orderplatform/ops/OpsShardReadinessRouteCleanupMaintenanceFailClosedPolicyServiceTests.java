package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyServiceTests {

    @Test
    void buildsFailClosedPolicyReportFromEvidenceBoundaries() {
        OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse report =
                new OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyService().report();

        assertThat(report.version()).isEqualTo("Java v497");
        assertThat(report.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-fail-closed-policy");
        assertThat(report.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-fail-closed-policy.v1");
        assertThat(report.policyCount()).isEqualTo(7);
        assertThat(report.protectedItemCount()).isEqualTo(9);
        assertThat(report.zeroViolationCount()).isEqualTo(7);
        assertThat(report.policies())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceFailClosedPolicyResponse
                        .PolicyCheck::operation)
                .containsExactly(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "raw-endpoint-parse",
                        "managed-audit-connection",
                        "deployment-or-rollback",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(report.policies().get(4).guard())
                .isEqualTo("fail-closed-before-managed-audit-connection");
        assertThat(report.policies()).allSatisfy(policy -> {
            assertThat(policy.violationCount()).isZero();
            assertThat(policy.status()).isEqualTo("passed");
        });
        assertThat(report.checks()).contains("fail-closed-policy-remains-read-only");
        assertThat(report.status()).isEqualTo("passed");
    }
}
