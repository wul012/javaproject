package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffBoundaryFlagsTests {

  @Test
  void handoffFlagsDoNotOpenMutableOrRuntimeBehavior() {
    var response = HandoffTestData.handoff();

    assertThat(response.draftArtifactMaterializationAllowed()).isFalse();
    assertThat(response.signedApprovalCaptureAllowed()).isFalse();
    assertThat(response.approvalGrantAllowed()).isFalse();
    assertThat(response.valueImportAllowed()).isFalse();
    assertThat(response.runtimePayloadAllowed()).isFalse();
    assertThat(response.writeAllowed()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
  }

  @Test
  void checksExposeZeroMutableArtifactCounts() {
    var response = HandoffTestData.handoff();

    assertThat(response.checks())
        .contains(
            "signed-approval-draft-profile-section-handoff-zero-draft-artifacts",
            "signed-approval-draft-profile-section-handoff-zero-signed-approvals",
            "signed-approval-draft-profile-section-handoff-zero-runtime-payloads",
            "signed-approval-draft-profile-section-handoff-zero-write-operations");
  }
}
