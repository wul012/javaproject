package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1801Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path SCREENSHOT_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "screenshotexplanationarchive"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("screenshot-explanation-archive-extraction-v1801.md");
  private static final String RETIRED_RENDERER =
      "OpsShardReadinessScreenshotExplanationArchiveRegistryRenderer.java";
  private static final List<String> EXTRACTED_IMPLEMENTATION_FILES =
      List.of(
          "OpsShardReadinessScreenshotExplanationArchiveBoundaryCatalog.java",
          "OpsShardReadinessScreenshotExplanationArchiveCurrentCatalog.java",
          "OpsShardReadinessScreenshotExplanationArchiveNamingRuleCatalog.java",
          "ReportRenderer.java",
          "OpsShardReadinessScreenshotExplanationArchiveRegistryResponse.java",
          "OpsShardReadinessScreenshotExplanationArchiveRegistryService.java",
          "OpsShardReadinessScreenshotExplanationArchiveRegistrySupport.java",
          "OpsShardReadinessScreenshotExplanationArchiveRoutePaths.java",
          "OpsShardReadinessScreenshotExplanationArchiveSegmentCatalog.java",
          "OpsShardReadinessScreenshotExplanationArchiveVerificationCatalog.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "screenshot-explanation-archive-extraction-v1801.md",
            "ops.maintenance.screenshotexplanationarchive",
            "1,290 to 1,280");
    assertThat(note)
        .contains(
            "v1801",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,280",
            "Do not rename or move archive roots",
            "/api/v1/ops/shard-readiness/screenshot-explanation-archive-registry");
  }

  @Test
  void extractedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(SCREENSHOT_SOURCE_ROOT)).isTrue();

    for (String fileName : EXTRACTED_IMPLEMENTATION_FILES) {
      assertThat(Files.isRegularFile(SCREENSHOT_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the screenshot explanation archive package")
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should no longer be directly in the root ops package")
          .isFalse();
    }
    assertThat(Files.exists(SCREENSHOT_SOURCE_ROOT.resolve(RETIRED_RENDERER))).isFalse();
    assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(RETIRED_RENDERER))).isFalse();
  }

  @Test
  void rootStillOwnsPublicControllerAndRouteAggregation() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessScreenshotExplanationArchiveRegistryController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
