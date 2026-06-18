package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1815Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path READINESS_LANE_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "signedapprovalartifactdraftreadinesslane"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("signed-approval-artifact-draft-readiness-lane-extraction-v1815.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneResponse.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneSupport.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneBlockerCatalog.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneCloseoutService.java");
  private static final List<String> ROOT_RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneAssuranceController.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneFoundationController.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-artifact-draft-readiness-lane-extraction-v1815.md",
            "ops.maintenance.signedapprovalartifactdraftreadinesslane",
            "1,057 to 1,041");
    assertThat(note)
        .contains(
            "v1815",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,041",
            "total `ops` Java files stay at 1,352",
            "Do not rename or move archive roots",
            "ReviewPackagePreflight");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(READINESS_LANE_SOURCE_ROOT)).isTrue();

    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(READINESS_LANE_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the artifact draft readiness lane package")
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should no longer be directly in the root ops package")
          .isFalse();
    }
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftReadinessLaneGateCatalog.java")))
        .as("gate catalog should be collocated with blocker catalog, not left in root")
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
  void rootPackageRatchetDoesNotRegressAboveV1815Count() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(1041);
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
