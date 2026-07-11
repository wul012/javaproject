package com.codexdemo.orderplatform.ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryCatalogTests {

  @Test
  void carriesSourceArchiveSnapshotAndDigestSections() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
            .registry();

    assertThat(response.sourceArchiveSnapshotCount()).isEqualTo(1);
    assertThat(response.sourceArchiveSnapshots())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                    .SourceArchiveSnapshot
                ::version)
        .containsExactly("Java v1377");
    assertThat(response.digestSectionCount()).isEqualTo(6);
    assertThat(response.passedDigestSectionCount()).isEqualTo(6);
    assertThat(response.digestSections())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                    .DigestSection
                ::name)
        .containsExactly(
            "source-handoff-snapshot",
            "artifact-verifications",
            "operator-lane-verifications",
            "ci-batch-verifications",
            "boundary-lock-verifications",
            "source-archive-scorecard");
  }

  @Test
  void exposesFocusedGroupedBuildSmokeReplayOrder() {
    var response =
        OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryTestSupport
            .registry();

    assertThat(response.replayInstructionCount()).isEqualTo(5);
    assertThat(response.readOnlyReplayInstructionCount()).isEqualTo(5);
    assertThat(response.replayInstructions())
        .extracting(
            OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse
                    .ReplayInstruction
                ::commandFamily)
        .containsExactly("focused", "focused", "grouped", "build", "smoke");
  }
}
