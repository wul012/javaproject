package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionCatalogTests {

  @Test
  void sectionCatalogMapsEachSourceToRendererOwnedSection() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.sections())
        .allSatisfy(
            section -> {
              assertThat(section.fieldEntryCount()).isEqualTo(6);
              assertThat(section.rendererOwner())
                  .isEqualTo("signed-approval-draft-profile-section-renderer");
              assertThat(section.status()).isEqualTo("passed");
            });
  }

  @Test
  void sectionHeadingsMatchRouteFacingMarkdownLabels() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.sections())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.DraftProfileSection
                ::heading)
        .containsExactly(
            "Signed Approval Artifact Draft Preflight",
            "Signed Approval Artifact Draft Readiness",
            "Signed Approval Artifact Draft Review Package Preflight",
            "Signed Approval Artifact Draft Authoring Readiness",
            "Signed Approval Artifact Draft Instruction Preflight");
  }
}
