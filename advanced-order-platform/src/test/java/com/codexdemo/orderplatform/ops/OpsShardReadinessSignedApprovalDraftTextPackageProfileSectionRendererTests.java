package com.codexdemo.orderplatform.ops;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRendererTests {

    @Test
    void rendererKeepsSubmissionAndComparedEvidenceCountsStable() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.renderedSections())
                .filteredOn(section -> section.rendererGroup().equals("submission"))
                .hasSize(5);
        assertThat(response.renderedSections())
                .filteredOn(section -> section.rendererGroup().equals("compared-evidence"))
                .hasSize(4);
        assertThat(response.renderedSectionCount()).isEqualTo(9);
    }

    @Test
    void rendererKeepsRouteFacingFieldsVisible() {
        var response = OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport
                .registry();

        assertThat(response.renderedSections())
                .allSatisfy(section -> {
                    assertThat(section.markdownHeading()).startsWith("### Signed Approval Artifact Draft Text Package");
                    assertThat(section.markdownBody())
                            .contains(
                                    "- java-version: ",
                                    "- endpoint: ",
                                    "- profile: ",
                                    "- node-marker: ",
                                    "- renderer-group: ",
                                    "- boundary: read-only-no-runtime");
                });
    }
}
