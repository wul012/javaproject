package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesectionhandoff;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionHandoffModuleCatalogTests {

  @Test
  void moduleCatalogPublishesHandoffModulesAfterRegistryModules() {
    var response = HandoffTestData.handoff();

    assertThat(response.modules())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.ModuleEntry::order)
        .containsExactly(240, 241, 242, 243, 244, 245, 246, 247);
    assertThat(response.modules())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionHandoffResponse.ModuleEntry::code)
        .contains(
            "signed-approval-draft-profile-section-handoff-route-contract-catalog",
            "signed-approval-draft-profile-section-handoff-route");
  }

  @Test
  void moduleCatalogStaysPassedAndSized() {
    var response = HandoffTestData.handoff();

    assertThat(response.moduleCount()).isEqualTo(8);
    assertThat(response.modules())
        .allSatisfy(module -> assertThat(module.status()).isEqualTo("passed"));
  }
}
