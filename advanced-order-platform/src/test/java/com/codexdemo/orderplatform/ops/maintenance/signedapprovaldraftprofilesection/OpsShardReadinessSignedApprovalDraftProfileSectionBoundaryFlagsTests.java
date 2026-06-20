package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionBoundaryFlagsTests {

  @Test
  void boundaryFlagsDoNotOpenSignedApprovalOrRuntimeExecution() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

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
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.checks())
        .contains(
            "signed-approval-draft-profile-section-registry-zero-draft-artifacts",
            "signed-approval-draft-profile-section-registry-zero-signed-approvals",
            "signed-approval-draft-profile-section-registry-zero-runtime-payloads",
            "signed-approval-draft-profile-section-registry-zero-write-operations",
            "signed-approval-draft-profile-section-registry-zero-sibling-mutations");
  }
}
