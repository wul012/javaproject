package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateexecution;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateExecutionArchiveArtifactReadTargetTests {

  @Test
  void snapshotsSourceRegistryAndVerifiesArchiveArtifacts() {
    var sourceRegistry = ArchiveTestData.sourceRegistry();
    var snapshots =
        OpsShardReadinessMinimalReadOnlyGateExecutionArchiveSourceRegistrySnapshotCatalog.snapshots(
            sourceRegistry);
    var artifacts =
        OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationCatalog
            .artifactVerifications(sourceRegistry);

    assertThat(snapshots)
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_SOURCE_REGISTRY_COUNT);
    assertThat(snapshots.get(0).version()).isEqualTo("Java v1312");
    assertThat(snapshots.get(0).status()).isEqualTo("passed");
    assertThat(artifacts)
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_ARTIFACT_VERIFICATION_COUNT);
    assertThat(artifacts)
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .ArtifactVerification
                ::artifact)
        .contains(
            "v367-json",
            "v367-markdown",
            "v367-summary",
            "v367-screenshot",
            "v367-walkthrough",
            "v367-gate-manifest");
    assertThat(artifacts).allSatisfy(artifact -> assertThat(artifact.status()).isEqualTo("passed"));
  }

  @Test
  void verifiesReadTargetsFromSourceRegistryWithoutRawEndpointValues() {
    var sourceRegistry = ArchiveTestData.sourceRegistry();
    var readTargets =
        OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetVerificationCatalog
            .readTargetVerifications(sourceRegistry);

    assertThat(readTargets)
        .hasSize(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport
                .EXPECTED_READ_TARGET_VERIFICATION_COUNT);
    assertThat(readTargets)
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse
                    .ReadTargetVerification
                ::target)
        .containsExactly(
            "java-health",
            "java-ops-overview",
            "mini-kv-health",
            "mini-kv-infojson",
            "mini-kv-statsjson");
    assertThat(readTargets)
        .allSatisfy(
            target -> {
              assertThat(target.archived()).isTrue();
              assertThat(target.status()).isEqualTo("passed");
              assertThat(target.commandOrRoute()).doesNotContain("://");
            });
  }
}
