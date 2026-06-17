package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1810Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "signedapprovalcaptureartifactpreflight"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("signed-approval-capture-artifact-preflight-extraction-v1810.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightResponse.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightSupport.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFragmentCatalog.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightCloseoutService.java");
  private static final List<String> ROOT_RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightAssuranceController.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalCaptureArtifactPreflightFoundationController.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-capture-artifact-preflight-extraction-v1810.md",
            "ops.maintenance.signedapprovalcaptureartifactpreflight",
            "1,137 to 1,121");
    assertThat(note)
        .contains(
            "v1810",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,121",
            "Do not rename or move archive roots",
            "CapturePreflight");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_ROOT)).isTrue();

    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(CAPTURE_ARTIFACT_PREFLIGHT_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the capture artifact preflight package")
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should no longer be directly in the root ops package")
          .isFalse();
    }
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
  void rootPackageRatchetDoesNotRegressAboveV1810Count() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(1121);
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
