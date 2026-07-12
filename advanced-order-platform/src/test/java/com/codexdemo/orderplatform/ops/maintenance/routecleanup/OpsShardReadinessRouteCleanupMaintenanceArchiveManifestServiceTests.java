package com.codexdemo.orderplatform.ops.maintenance.routecleanup;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessRouteCleanupMaintenanceArchiveManifestServiceTests {

  @Test
  void buildsMaintenanceArchiveManifestForVersionedEvidenceArtifacts() {
    OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse manifest =
        new OpsShardReadinessRouteCleanupMaintenanceArchiveManifestService().manifest();

    assertThat(manifest.version()).isEqualTo("Java v485");
    assertThat(manifest.endpoint())
        .isEqualTo("/api/v1/ops/shard-readiness/route-cleanup-maintenance-archive-manifest");
    assertThat(manifest.profile())
        .isEqualTo("java-shard-readiness-route-cleanup-maintenance-archive-manifest.v1");
    assertThat(manifest.artifactCount()).isEqualTo(7);
    assertThat(manifest.artifacts())
        .extracting(
            OpsShardReadinessRouteCleanupMaintenanceArchiveManifestResponse.ArchiveArtifact::name)
        .containsExactly(
            "segment-catalog",
            "continuity",
            "latest-sibling-report",
            "handoff-pair-audit",
            "boundary-drift",
            "source-plan-alignment",
            "test-budget-plan");
    assertThat(manifest.artifacts().getFirst().sourceEndpoint())
        .isEqualTo(OpsShardReadinessRouteCleanupMaintenanceSegmentCatalogService.ENDPOINT);
    assertThat(manifest.artifacts())
        .allSatisfy(artifact -> assertThat(artifact.evidencePath()).endsWith(".json"));
    assertThat(manifest.checks()).contains("archive-remains-read-only");
    assertThat(manifest.status()).isEqualTo("passed");
  }
}
