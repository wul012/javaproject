package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessSignedApprovalDraftProfileSectionRendererTests {

  @Test
  void rendererEmitsOneMarkdownBlockPerSignedApprovalDraftSection() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections()).hasSize(5);
    assertThat(response.renderedSections())
        .allSatisfy(
            section -> {
              assertThat(section.markdownHeading())
                  .startsWith("### Signed Approval Artifact Draft");
              assertThat(section.markdownBody())
                  .contains("- java-version: ", "- endpoint: ", "- profile: ");
              assertThat(section.status()).isEqualTo("passed");
            });
  }

  @Test
  void rendererKeepsNodeMarkersAndFailClosedBoundaryVisible() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections())
        .allSatisfy(
            section ->
                assertThat(section.markdownBody())
                    .contains("- node-marker: Node v", "- boundary: read-only-no-runtime"));
  }
}
