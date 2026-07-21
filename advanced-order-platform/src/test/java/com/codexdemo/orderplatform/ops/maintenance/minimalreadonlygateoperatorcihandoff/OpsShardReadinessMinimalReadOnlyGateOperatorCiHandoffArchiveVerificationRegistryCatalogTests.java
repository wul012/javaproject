package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryCatalogTests {

  @Test
  void archivesSourceHandoffArtifacts() {
    var response = ArchiveTestData.registry();

    assertThat(response.sourceHandoffSnapshotCount()).isEqualTo(1);
    assertThat(response.sourceHandoffSnapshots())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .SourceHandoffSnapshot
                ::version)
        .containsExactly("Java v1352");
    assertThat(response.artifactVerificationCount()).isEqualTo(6);
    assertThat(response.passedArtifactVerificationCount()).isEqualTo(6);
    assertThat(response.artifactVerifications())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .ArtifactVerification
                ::artifact)
        .containsExactly(
            "source-response-json",
            "markdown-section-rendering",
            "operator-lane-plan",
            "ci-batch-plan",
            "boundary-lock-plan",
            "source-scorecard-summary");
  }

  @Test
  void archivesOperatorLanesAndCiBatchesInSourceOrder() {
    var response = ArchiveTestData.registry();

    assertThat(response.operatorLaneVerificationCount()).isEqualTo(4);
    assertThat(response.passedOperatorLaneVerificationCount()).isEqualTo(4);
    assertThat(response.operatorLaneVerifications())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .OperatorLaneVerification
                ::lane)
        .containsExactly("focused", "grouped", "build", "smoke");
    assertThat(response.ciBatchVerificationCount()).isEqualTo(5);
    assertThat(response.passedCiBatchVerificationCount()).isEqualTo(5);
    assertThat(response.ciBatchVerifications())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse
                    .CiBatchVerification
                ::commandFamily)
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
  }
}
