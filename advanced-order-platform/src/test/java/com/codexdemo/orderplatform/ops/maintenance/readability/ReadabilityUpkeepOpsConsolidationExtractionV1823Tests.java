package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1823Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path COMPARISON_ACCEPTANCE_PRECHECK_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(
          Path.of(
              "maintenance", "signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve(
          "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-extraction-v1823.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckResponse.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckSupport.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckCheckpointCatalog.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckPolicyExecutionArchiveService.java");
  private static final String ROOT_RETAINED_CONTROLLER =
      "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckController.java";

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-artifact-draft-text-package-comparison-acceptance-precheck-extraction-v1823.md",
            "ops.maintenance.signedapprovalartifactdrafttextpackagecomparisonacceptanceprecheck",
            "919 to 911");
    assertThat(note)
        .contains(
            "v1823",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "911",
            "total `ops` Java files stay at 1,352",
            "Do not rename or move archive roots",
            "ComparedPackageEvidenceIntake",
            "ProfileSection");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(COMPARISON_ACCEPTANCE_PRECHECK_SOURCE_ROOT)).isTrue();
    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(COMPARISON_ACCEPTANCE_PRECHECK_SOURCE_ROOT.resolve(fileName)))
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparisonAcceptancePrecheckGuardCatalog.java")))
        .isFalse();
  }

  @Test
  void rootStillOwnsPublicControllerAndRouteAggregation() {
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve(ROOT_RETAINED_CONTROLLER))).isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void rootPackageRatchetDoesNotRegressAboveV1823Count() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(911);
    }
  }

  @Test
  void totalOpsFileCountRatchetDoesNotGrowForRouteOwner() throws IOException {
    try (Stream<Path> paths = Files.walk(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(1352);
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
