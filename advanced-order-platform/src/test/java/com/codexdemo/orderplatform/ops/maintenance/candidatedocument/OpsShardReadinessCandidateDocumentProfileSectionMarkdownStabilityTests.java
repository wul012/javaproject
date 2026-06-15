package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class OpsShardReadinessCandidateDocumentProfileSectionMarkdownStabilityTests {

  @Test
  void renderedMarkdownKeepsCandidateDocumentSectionOrderStable() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections())
        .extracting(
            OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RenderedSection
                ::markdownHeading)
        .containsExactly(
            "### Candidate Document Request Package",
            "### Candidate Document Submission Precheck",
            "### Candidate Document Intake Packet",
            "### Candidate Document Material Request",
            "### Candidate Document Material Submission Precheck");
  }

  @Test
  void renderedMarkdownKeepsRouteFacingFieldsVisible() {
    var response = OpsShardReadinessCandidateDocumentProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections())
        .allSatisfy(
            section ->
                assertThat(section.markdownBody())
                    .contains(
                        "- version: ",
                        "- endpoint: ",
                        "- profile: ",
                        "- status: ",
                        "- boundary: "));
  }
}
