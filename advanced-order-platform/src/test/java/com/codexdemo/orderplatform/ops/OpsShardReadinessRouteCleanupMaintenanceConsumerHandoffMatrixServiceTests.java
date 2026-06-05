package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixServiceTests {

    @Test
    void buildsConsumerHandoffMatrixFromUpkeepCatalog() {
        OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse matrix =
                new OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixService().matrix();

        assertThat(matrix.version()).isEqualTo("Java v491");
        assertThat(matrix.endpoint()).isEqualTo(
                "/api/v1/ops/shard-readiness/route-cleanup-maintenance-consumer-handoff-matrix");
        assertThat(matrix.profile()).isEqualTo(
                "java-shard-readiness-route-cleanup-maintenance-consumer-handoff-matrix.v1");
        assertThat(matrix.matrixEntryCount()).isEqualTo(9);
        assertThat(matrix.consumerCount()).isEqualTo(9);
        assertThat(matrix.forbiddenOperationCount()).isEqualTo(7);
        assertThat(matrix.matrix())
                .extracting(OpsShardReadinessRouteCleanupMaintenanceConsumerHandoffMatrixResponse
                        .MatrixEntry::consumer)
                .contains(
                        "catalog-maintainer",
                        "runtime-boundary-reviewer",
                        "operator-handoff-reviewer"
                );
        assertThat(matrix.matrix().get(4).boundary()).isEqualTo("read-only-boundary");
        assertThat(matrix.matrix().get(4).requiredAction())
                .isEqualTo("review-read-only-boundary-from-java-v479");
        assertThat(matrix.forbiddenOperations()).contains("managed-audit-connection");
        assertThat(matrix.checks()).contains("handoff-matrix-remains-read-only");
        assertThat(matrix.status()).isEqualTo("passed");
    }
}
