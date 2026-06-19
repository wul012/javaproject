package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1821Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path SUBMISSION_PREFLIGHT_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(
          Path.of("maintenance", "signedapprovalartifactdrafttextpackagesubmissionpreflight"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve(
          "signed-approval-artifact-draft-text-package-submission-preflight-extraction-v1821.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightResponse.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightComparisonControlCatalog.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutHandoffLedgerService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutResponse.java");
  private static final List<String> ROOT_RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightController.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightCloseoutController.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-artifact-draft-text-package-submission-preflight-extraction-v1821.md",
            "ops.maintenance.signedapprovalartifactdrafttextpackagesubmissionpreflight",
            "961 to 932");
    assertThat(note)
        .contains(
            "v1821",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "932",
            "total `ops` Java files stay at 1,352",
            "Do not rename or move archive roots",
            "TextPackageComparisonPreflight",
            "Closeout");
  }

  @Test
  void primaryAndCloseoutImplementationFilesLiveInOneNarrowPackage() {
    assertThat(Files.isDirectory(SUBMISSION_PREFLIGHT_SOURCE_ROOT)).isTrue();
    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(SUBMISSION_PREFLIGHT_SOURCE_ROOT.resolve(fileName))).isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageSubmissionPreflightGateCatalog.java")))
        .isFalse();
  }

  @Test
  void rootStillOwnsPublicControllersAndRouteAggregation() {
    for (String controller : ROOT_RETAINED_CONTROLLERS) {
      assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve(controller))).isTrue();
    }
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void rootPackageRatchetDoesNotRegressAboveV1821Count() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(932);
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
