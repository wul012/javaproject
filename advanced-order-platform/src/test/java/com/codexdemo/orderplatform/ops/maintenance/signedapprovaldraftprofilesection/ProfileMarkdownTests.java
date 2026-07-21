package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldraftprofilesection.OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.RenderedSection;
import org.junit.jupiter.api.Test;

class ProfileMarkdownTests {

  @Test
  void renderedMarkdownKeepsSignedApprovalDraftSectionOrderStable() {
    var response = OpsShardReadinessSignedApprovalDraftProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections())
        .containsExactly(
            section(
                1,
                "signed-approval-artifact-draft-preflight-section",
                "### Signed Approval Artifact Draft Preflight",
                "- java-version: Java v796",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-preflight-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-preflight-catalog.v1",
                "- node-marker: Node v1111",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                2,
                "signed-approval-artifact-draft-readiness-section",
                "### Signed Approval Artifact Draft Readiness",
                "- java-version: Java v771",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-readiness-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-readiness-catalog.v1",
                "- node-marker: Node v1136",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                3,
                "signed-approval-artifact-draft-review-package-preflight-section",
                "### Signed Approval Artifact Draft Review Package Preflight",
                "- java-version: Java v846",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-review-package-preflight-catalog.v1",
                "- node-marker: Node v1161",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                4,
                "signed-approval-artifact-draft-authoring-readiness-section",
                "### Signed Approval Artifact Draft Authoring Readiness",
                "- java-version: Java v871",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-authoring-readiness-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-authoring-readiness-catalog.v1",
                "- node-marker: Node v1186",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                5,
                "signed-approval-artifact-draft-instruction-preflight-section",
                "### Signed Approval Artifact Draft Instruction Preflight",
                "- java-version: Java v896",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-instruction-preflight-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-instruction-preflight-catalog.v1",
                "- node-marker: Node v1211",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"));
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

  private static RenderedSection section(
      int order, String code, String heading, String... bodyLines) {
    return new RenderedSection(order, code, heading, String.join("\n", bodyLines), "passed");
  }
}
