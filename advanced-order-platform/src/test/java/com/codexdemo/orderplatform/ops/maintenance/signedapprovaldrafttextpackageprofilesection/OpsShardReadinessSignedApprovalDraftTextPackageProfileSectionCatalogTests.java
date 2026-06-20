package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionCatalogTests {

  @Test
  void sectionCatalogSplitsSubmissionAndComparedEvidenceGroups() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.sections())
        .filteredOn(section -> section.rendererGroup().equals("submission"))
        .hasSize(5)
        .allSatisfy(
            section ->
                assertThat(section.rendererOwner())
                    .isEqualTo(
                        "signed-approval-draft-text-package-submission-profile-section-renderer"));
    assertThat(response.sections())
        .filteredOn(section -> section.rendererGroup().equals("compared-evidence"))
        .hasSize(4)
        .allSatisfy(
            section ->
                assertThat(section.rendererOwner())
                    .isEqualTo(
                        "signed-approval-draft-text-package-compared-evidence-profile-section-renderer"));
  }

  @Test
  void sectionHeadingsRemainRouteFacingAndStable() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.sections())
        .extracting(
            OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                    .TextPackageProfileSection
                ::heading)
        .containsExactly(
            "Signed Approval Artifact Draft Text Package Intake",
            "Signed Approval Artifact Draft Text Package Review Preflight",
            "Signed Approval Artifact Draft Text Package Submission Preflight",
            "Signed Approval Artifact Draft Text Package Comparison Preflight",
            "Signed Approval Artifact Draft Text Package Comparison Acceptance Precheck",
            "Signed Approval Artifact Draft Text Package Compared Package Evidence Intake",
            "Signed Approval Artifact Draft Text Package Compared Evidence Evaluation Preflight",
            "Signed Approval Artifact Draft Text Package Compared Evidence Candidate",
            "Signed Approval Artifact Draft Text Package Compared Evidence Candidate Intake");
  }
}
