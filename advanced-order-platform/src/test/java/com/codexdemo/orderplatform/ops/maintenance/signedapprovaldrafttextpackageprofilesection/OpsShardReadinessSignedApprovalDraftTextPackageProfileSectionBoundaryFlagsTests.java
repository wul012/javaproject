package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionBoundaryFlagsTests {

  @Test
  void boundaryFlagsDoNotOpenPackageApprovalRuntimeOrSecretValueBehavior() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.packageAcceptanceAllowed()).isFalse();
    assertThat(response.signedApprovalCaptureAllowed()).isFalse();
    assertThat(response.approvalGrantAllowed()).isFalse();
    assertThat(response.valueImportAllowed()).isFalse();
    assertThat(response.runtimePayloadAllowed()).isFalse();
    assertThat(response.secretValueAllowed()).isFalse();
    assertThat(response.writeAllowed()).isFalse();
    assertThat(response.siblingMutationAllowed()).isFalse();
  }

  @Test
  void checksExposeZeroMutableCounts() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.checks())
        .contains(
            "signed-approval-draft-text-package-profile-section-registry-zero-package-acceptance",
            "signed-approval-draft-text-package-profile-section-registry-zero-signed-approvals",
            "signed-approval-draft-text-package-profile-section-registry-zero-runtime-payloads",
            "signed-approval-draft-text-package-profile-section-registry-zero-secret-values",
            "signed-approval-draft-text-package-profile-section-registry-zero-write-operations");
  }
}
