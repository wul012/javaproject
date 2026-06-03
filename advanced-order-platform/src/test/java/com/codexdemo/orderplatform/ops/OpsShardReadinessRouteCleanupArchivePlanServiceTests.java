package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupArchivePlanServiceTests {

    @Test
    void buildsReadOnlyArchivePlanForCurrentRouteCleanupSuite() {
        OpsShardReadinessRouteCleanupArchivePlanResponse plan =
                new OpsShardReadinessRouteCleanupArchivePlanService().plan();

        int version = OpsShardReadinessRouteCleanupEvidenceAnalyzer.latestJavaVersion();

        assertThat(version).isGreaterThanOrEqualTo(333);
        assertThat(plan.project()).isEqualTo("advanced-order-platform");
        assertThat(plan.version()).isEqualTo("Java v" + version);
        assertThat(plan.readOnly()).isTrue();
        assertThat(plan.executionAllowed()).isFalse();
        assertThat(plan.archivePlanEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-archive-plan");
        assertThat(plan.archiveProfile())
                .isEqualTo("java-shard-readiness-route-cleanup-archive-plan.v1");
        assertThat(plan.sourceEndpoint())
                .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-handoff-checklist");
        assertThat(plan.targetEvidenceRoot()).isEqualTo("e/" + version + "/evidence");
        assertThat(plan.artifactCount()).isEqualTo(4);
        assertThat(plan.artifacts())
                .extracting(OpsShardReadinessRouteCleanupArchivePlanResponse.ArchiveArtifact::name)
                .containsExactly(
                        "catalog-json",
                        "phase-summary-json",
                        "boundary-matrix-json",
                        "handoff-checklist-json"
                );
        assertThat(plan.artifacts())
                .allSatisfy(artifact -> {
                    assertThat(artifact.required()).isTrue();
                    assertThat(artifact.targetPath()).contains("/" + version + "/");
                    assertThat(artifact.status()).isEqualTo("planned");
                });
        assertThat(plan.status()).isEqualTo("passed");
    }
}
