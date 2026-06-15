package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionCatalogTests {

  @Test
  void sectionsUseStableHeadingsAndRendererOwner() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.sections())
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection
                ::heading)
        .containsExactly(
            "Candidate Document Request Package",
            "Candidate Document Submission Precheck",
            "Candidate Document Intake Packet",
            "Candidate Document Material Request",
            "Candidate Document Material Submission Precheck");
    assertThat(response.sections())
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection
                ::fieldEntryCount)
        .containsOnly(5);
    assertThat(response.sections())
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.ProfileSection
                ::rendererOwner)
        .containsOnly("candidate-document-profile-section-renderer");
  }
}
