package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1800Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path DEPTH_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "walkthrough", "depth"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("depth-registry-extraction-v1800.md");
  private static final List<String> EXTRACTED_IMPLEMENTATION_FILES =
      List.of(
          "OpsShardReadinessCodeWalkthroughDepthBoundaryCatalog.java",
          "OpsShardReadinessCodeWalkthroughDepthRegistryRenderer.java",
          "OpsShardReadinessCodeWalkthroughDepthRegistryResponse.java",
          "OpsShardReadinessCodeWalkthroughDepthRegistryService.java",
          "OpsShardReadinessCodeWalkthroughDepthRegistrySupport.java",
          "OpsShardReadinessCodeWalkthroughDepthRoutePaths.java",
          "OpsShardReadinessCodeWalkthroughDepthRuleCatalog.java",
          "OpsShardReadinessCodeWalkthroughDepthVerificationCatalog.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "depth-registry-extraction-v1800.md",
            "ops.maintenance.walkthrough.depth",
            "1,298 to 1,290");
    assertThat(note)
        .contains(
            "v1800",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,290",
            "Do not rename or move archive roots",
            "/api/v1/ops/shard-readiness/code-walkthrough-depth-registry");
  }

  @Test
  void extractedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(DEPTH_SOURCE_ROOT)).isTrue();

    for (String fileName : EXTRACTED_IMPLEMENTATION_FILES) {
      assertThat(Files.isRegularFile(DEPTH_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the depth package")
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should no longer be directly in the root ops package")
          .isFalse();
    }
  }

  @Test
  void rootStillOwnsPublicControllerAndRouteAggregation() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessCodeWalkthroughDepthRegistryController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
