package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionModuleCatalogTests {

  @Test
  void moduleCatalogPublishesTextPackageRendererSplitModules() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.modules())
        .extracting(
            OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .ModuleEntry
                ::order)
        .containsExactly(248, 249, 250, 251, 252, 253, 254, 255, 256, 257);
    assertThat(response.modules())
        .extracting(
            OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .ModuleEntry
                ::code)
        .contains(
            "signed-approval-draft-text-package-profile-section-submission-renderer",
            "signed-approval-draft-text-package-profile-section-compared-evidence-renderer",
            "signed-approval-draft-text-package-profile-section-aggregate-renderer");
  }

  @Test
  void moduleCatalogStaysPassedAndSized() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.moduleCount()).isEqualTo(10);
    assertThat(response.modules())
        .allSatisfy(module -> assertThat(module.status()).isEqualTo("passed"));
  }
}
