package com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection;

import static org.assertj.core.api.Assertions.assertThat;

import com.codexdemo.orderplatform.ops.maintenance.signedapprovaldrafttextpackageprofilesection.OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.RenderedSection;
import org.junit.jupiter.api.Test;

class ProfileMarkdownTests {

  @Test
  void renderedMarkdownKeepsTextPackageSectionOrderStable() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections())
        .containsExactly(
            section(
                1,
                "signed-approval-artifact-draft-text-package-intake-section",
                "submission",
                "### Signed Approval Artifact Draft Text Package Intake",
                "- java-version: Java v921",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-intake-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-intake-catalog.v1",
                "- node-marker: Node v1236",
                "- renderer-group: submission",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                2,
                "signed-approval-artifact-draft-text-package-review-preflight-section",
                "submission",
                "### Signed Approval Artifact Draft Text Package Review Preflight",
                "- java-version: Java v946",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-review-preflight-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-review-preflight-catalog.v1",
                "- node-marker: Node v1261",
                "- renderer-group: submission",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                3,
                "signed-approval-artifact-draft-text-package-submission-preflight-section",
                "submission",
                "### Signed Approval Artifact Draft Text Package Submission Preflight",
                "- java-version: Java v966",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-submission-preflight-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-submission-preflight-catalog.v1",
                "- node-marker: Node v1286",
                "- renderer-group: submission",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                4,
                "signed-approval-artifact-draft-text-package-comparison-preflight-section",
                "submission",
                "### Signed Approval Artifact Draft Text Package Comparison Preflight",
                "- java-version: Java v1001",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-comparison-preflight-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-comparison-preflight-catalog.v1",
                "- node-marker: Node v1311",
                "- renderer-group: submission",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                5,
                "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-section",
                "submission",
                "### Signed Approval Artifact Draft Text Package Comparison Acceptance Precheck",
                "- java-version: Java v1009",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-catalog.v1",
                "- node-marker: Node v1321",
                "- renderer-group: submission",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                6,
                "signed-approval-artifact-draft-text-package-compared-package-evidence-intake-section",
                "compared-evidence",
                "### Signed Approval Artifact Draft Text Package Compared Package Evidence Intake",
                "- java-version: Java v1020",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-evidence-intake-catalog",
                "- profile: java-shard-readiness-operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-package-evidence-intake-catalog.v1",
                "- node-marker: Node v1331",
                "- renderer-group: compared-evidence",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                7,
                "signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight-section",
                "compared-evidence",
                "### Signed Approval Artifact Draft Text Package Compared Evidence Evaluation Preflight",
                "- java-version: Java v1050",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-evaluation-preflight-catalog",
                "- profile: java-shard-readiness-compared-evidence-evaluation-preflight-catalog.v1",
                "- node-marker: Node v1351",
                "- renderer-group: compared-evidence",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                8,
                "signed-approval-artifact-draft-text-package-compared-evidence-candidate-section",
                "compared-evidence",
                "### Signed Approval Artifact Draft Text Package Compared Evidence Candidate",
                "- java-version: Java v1060",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-blueprint-catalog",
                "- profile: java-shard-readiness-compared-evidence-candidate-blueprint-catalog.v1",
                "- node-marker: Node v1361",
                "- renderer-group: compared-evidence",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"),
            section(
                9,
                "signed-approval-artifact-draft-text-package-compared-evidence-candidate-intake-section",
                "compared-evidence",
                "### Signed Approval Artifact Draft Text Package Compared Evidence Candidate Intake",
                "- java-version: Java v1075",
                "- endpoint: /api/v1/ops/shard-readiness/operator-evidence-value-supply-signed-approval-artifact-draft-text-package-compared-evidence-candidate-intake-preflight-catalog",
                "- profile: java-shard-readiness-compared-evidence-candidate-intake-preflight-catalog.v1",
                "- node-marker: Node v1371",
                "- renderer-group: compared-evidence",
                "- source-status: passed",
                "- boundary: read-only-no-runtime"));
  }

  @Test
  void renderedMarkdownKeepsFailClosedFieldsVisible() {
    var response =
        OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryTestSupport.registry();

    assertThat(response.renderedSections())
        .allSatisfy(
            section ->
                assertThat(section.markdownBody()).contains("- boundary: read-only-no-runtime"));
  }

  private static RenderedSection section(
      int order, String code, String group, String heading, String... bodyLines) {
    return new RenderedSection(order, code, group, heading, String.join("\n", bodyLines), "passed");
  }
}
