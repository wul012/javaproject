package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1809Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path MANUAL_WORKSHEET_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "manualevidenceworksheet"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("manual-evidence-worksheet-extraction-v1809.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessManualEvidenceWorksheetRoutePaths.java",
          "OpsShardReadinessManualEvidenceWorksheetCatalogService.java",
          "OpsShardReadinessManualEvidenceWorksheetResponse.java",
          "OpsShardReadinessManualEvidenceWorksheetSupport.java",
          "OpsShardReadinessManualEvidenceWorksheetCloseoutService.java");
  private static final List<String> ROOT_RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessManualEvidenceWorksheetAssuranceController.java",
          "OpsShardReadinessManualEvidenceWorksheetFoundationController.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "manual-evidence-worksheet-extraction-v1809.md",
            "ops.maintenance.manualevidenceworksheet",
            "1,152 to 1,137");
    assertThat(note)
        .contains(
            "v1809",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,137",
            "Do not rename or move archive roots",
            "OperatorEvidenceImportPreflight",
            "RuntimeExecution");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(MANUAL_WORKSHEET_SOURCE_ROOT)).isTrue();

    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(MANUAL_WORKSHEET_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the manual evidence worksheet package")
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
  void rootPackageRatchetMatchesMeasuredCountAfterExtraction() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isEqualTo(249);
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
