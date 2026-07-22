package com.codexdemo.orderplatform.ops.maintenance.candidatedocument;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.candidatedocument.OpsShardReadinessCandidateDocumentProfileSectionRegistryResponse.RenderedSection;
import org.junit.jupiter.api.Test;

class ProfileMarkdownTests {

  @Test
  void renderedMarkdownKeepsCandidateDocumentSectionOrderStable() {
    var response = ProfileTestData.registry();

    assertThat(response.renderedSections())
        .containsExactly(
            section(
                1,
                "candidate-document-request-package-section",
                "### Candidate Document Request Package",
                "- version: Java v1081",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-candidate-document-request-package",
                "- profile: java-shard-readiness-candidate-document-request-package.v1",
                "- status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                2,
                "candidate-document-submission-precheck-section",
                "### Candidate Document Submission Precheck",
                "- version: Java v1117",
                "- endpoint: /api/v1/ops/shard-readiness/candidate-document-submission-precheck",
                "- profile: java-shard-readiness-candidate-document-submission-precheck.v1",
                "- status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                3,
                "candidate-document-intake-packet-section",
                "### Candidate Document Intake Packet",
                "- version: Java v1142",
                "- endpoint: /api/v1/ops/shard-readiness/candidate-document-intake-packet",
                "- profile: java-shard-readiness-candidate-document-intake-packet.v1",
                "- status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                4,
                "candidate-document-material-request-section",
                "### Candidate Document Material Request",
                "- version: Java v1152",
                "- endpoint: /api/v1/ops/shard-readiness/candidate-document-material-request",
                "- profile: java-shard-readiness-candidate-document-material-request.v1",
                "- status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                5,
                "candidate-document-material-submission-precheck-section",
                "### Candidate Document Material Submission Precheck",
                "- version: Java v1162",
                "- endpoint: /api/v1/ops/shard-readiness/candidate-document-material-submission-precheck",
                "- profile: java-shard-readiness-candidate-document-material-submission-precheck.v1",
                "- status: passed",
                "- boundary: read-only-no-runtime"));
  }

  @Test
  void renderedMarkdownKeepsRouteFacingFieldsVisible() {
    var response = ProfileTestData.registry();

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

  private static RenderedSection section(
      int order, String code, String heading, String... bodyLines) {
    return new RenderedSection(order, code, heading, String.join("\n", bodyLines), "passed");
  }
}
