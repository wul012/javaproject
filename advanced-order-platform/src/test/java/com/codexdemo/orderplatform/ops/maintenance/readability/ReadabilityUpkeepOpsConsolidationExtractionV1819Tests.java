package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1819Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path TEXT_PACKAGE_INTAKE_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(
          Path.of("maintenance", "signedapprovalartifactdrafttextpackageintake"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("signed-approval-artifact-draft-text-package-intake-extraction-v1819.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeResponse.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeSupport.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGuardCatalog.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeArchiveCloseoutService.java");
  private static final List<String> ROOT_RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeAssuranceController.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeFoundationController.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-artifact-draft-text-package-intake-extraction-v1819.md",
            "ops.maintenance.signedapprovalartifactdrafttextpackageintake",
            "993 to 977");
    assertThat(note)
        .contains(
            "v1819",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "977",
            "total `ops` Java files stay at 1,352",
            "Do not rename or move archive roots",
            "TextPackageReviewPreflight");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(TEXT_PACKAGE_INTAKE_SOURCE_ROOT)).isTrue();

    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(TEXT_PACKAGE_INTAKE_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the artifact draft text package intake package")
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should no longer be directly in the root ops package")
          .isFalse();
    }
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageIntakeGateCatalog.java")))
        .as("gate catalog should be collocated with guard catalog, not left in root")
        .isFalse();
  }

  @Test
  void rootStillOwnsPublicControllersAndRouteAggregation() {
    for (String controller : ROOT_RETAINED_CONTROLLERS) {
      assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve(controller)))
          .as(controller + " should stay in the root ops package")
          .isTrue();
    }
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void rootPackageRatchetDoesNotRegressAboveV1819Count() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(977);
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
