package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1798Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path QUALITY_GATE_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "walkthrough", "qualitygate"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("quality-gate-registry-extraction-v1798.md");
  private static final List<String> EXTRACTED_IMPLEMENTATION_FILES =
      List.of(
          "OpsShardReadinessCodeWalkthroughQualityGateBoundaryRuleCatalog.java",
          "OpsShardReadinessCodeWalkthroughQualityGateEvidenceAnchorCatalog.java",
          "OpsShardReadinessCodeWalkthroughQualityGateExplanationRubricCatalog.java",
          "OpsShardReadinessCodeWalkthroughQualityGateRegistryRenderer.java",
          "OpsShardReadinessCodeWalkthroughQualityGateRegistryResponse.java",
          "OpsShardReadinessCodeWalkthroughQualityGateRegistryService.java",
          "OpsShardReadinessCodeWalkthroughQualityGateRegistrySupport.java",
          "OpsShardReadinessCodeWalkthroughQualityGateReviewChecklistCatalog.java",
          "OpsShardReadinessCodeWalkthroughQualityGateRoutePaths.java",
          "OpsShardReadinessCodeWalkthroughQualityGateVersionRuleCatalog.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "quality-gate-registry-extraction-v1798.md",
            "ops.maintenance.walkthrough.qualitygate",
            "1,319 to 1,309");
    assertThat(note)
        .contains(
            "v1798",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,309",
            "Do not rename or move archive roots",
            "/api/v1/ops/shard-readiness/code-walkthrough-quality-gate-registry");
  }

  @Test
  void extractedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(QUALITY_GATE_SOURCE_ROOT)).isTrue();

    for (String fileName : EXTRACTED_IMPLEMENTATION_FILES) {
      assertThat(Files.isRegularFile(QUALITY_GATE_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the quality gate package")
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
                    "OpsShardReadinessCodeWalkthroughQualityGateRegistryController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
