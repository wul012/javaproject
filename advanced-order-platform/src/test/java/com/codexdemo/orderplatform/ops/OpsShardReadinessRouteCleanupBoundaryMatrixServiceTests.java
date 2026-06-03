package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupBoundaryMatrixServiceTests {

    @Test
    void buildsFailClosedBoundaryMatrix() {
        OpsShardReadinessRouteCleanupBoundaryMatrixResponse matrix =
                new OpsShardReadinessRouteCleanupBoundaryMatrixService().matrix();

        assertThat(matrix.project()).isEqualTo("advanced-order-platform");
        assertThat(matrix.version())
                .isEqualTo(OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersionLabel());
        assertThat(matrix.readOnly()).isTrue();
        assertThat(matrix.executionAllowed()).isFalse();
        assertThat(matrix.matrixEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-boundary-matrix");
        assertThat(matrix.matrixProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-boundary-matrix.v1");
        assertThat(matrix.ruleCount()).isEqualTo(7);
        assertThat(matrix.rules())
                .extracting(OpsShardReadinessRouteCleanupBoundaryMatrixResponse.BoundaryRule::operation)
                .contains(
                        "write-routing",
                        "active-shard-router",
                        "credential-value-read",
                        "raw-endpoint-parse",
                        "managed-audit-connection",
                        "deployment-or-rollback",
                        "node-start-or-stop-java-or-mini-kv"
                );
        assertThat(matrix.rules())
                .allSatisfy(rule -> {
                    assertThat(rule.allowed()).isFalse();
                    assertThat(rule.evidence()).contains(rule.operation());
                    assertThat(rule.status()).isEqualTo("passed");
                });
        assertThat(matrix.status()).isEqualTo("passed");
    }
}
