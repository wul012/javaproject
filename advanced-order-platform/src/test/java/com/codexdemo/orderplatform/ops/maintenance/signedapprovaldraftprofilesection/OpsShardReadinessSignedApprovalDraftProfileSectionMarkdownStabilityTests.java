package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionMarkdownStabilityTests {

  @Test
  void renderedMarkdownKeepsSignedApprovalDraftSectionOrderStable() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections())
        .extracting(
            OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RenderedSection
                ::markdownHeading)
        .containsExactly(
            "### Signed Approval Artifact Draft Preflight",
            "### Signed Approval Artifact Draft Readiness",
            "### Signed Approval Artifact Draft Review Package Preflight",
            "### Signed Approval Artifact Draft Authoring Readiness",
            "### Signed Approval Artifact Draft Instruction Preflight");
  }

  @Test
  void renderedMarkdownKeepsRouteFacingFieldsVisible() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections())
        .allSatisfy(
            section ->
                assertThat(section.markdownBody())
                    .contains(
                        "- java-version: ",
                        "- endpoint: ",
                        "- profile: ",
                        "- node-marker: ",
                        "- source-status: ",
                        "- boundary: "));
  }
}
