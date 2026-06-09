package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionMarkdownStabilityTests {

    @Test
    void renderedMarkdownKeepsTextPackageSectionOrderStable() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.renderedSections())
                .extracting(OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse
                        .RenderedSection::markdownHeading)
                .containsExactly(
                        "### Signed Approval Artifact Draft Text Package Intake",
                        "### Signed Approval Artifact Draft Text Package Review Preflight",
                        "### Signed Approval Artifact Draft Text Package Submission Preflight",
                        "### Signed Approval Artifact Draft Text Package Comparison Preflight",
                        "### Signed Approval Artifact Draft Text Package Comparison Acceptance Precheck",
                        "### Signed Approval Artifact Draft Text Package Compared Package Evidence Intake",
                        "### Signed Approval Artifact Draft Text Package Compared Evidence Evaluation Preflight",
                        "### Signed Approval Artifact Draft Text Package Compared Evidence Candidate",
                        "### Signed Approval Artifact Draft Text Package Compared Evidence Candidate Intake");
    }

    @Test
    void renderedMarkdownKeepsFailClosedFieldsVisible() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.renderedSections())
                .allSatisfy(section -> assertThat(section.markdownBody())
                        .contains("- boundary: read-only-no-runtime"));
    }
}
