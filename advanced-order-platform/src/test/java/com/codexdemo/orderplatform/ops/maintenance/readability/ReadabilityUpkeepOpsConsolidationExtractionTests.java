package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionTests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path WALKTHROUGH_COMPLIANCE_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "walkthrough", "compliance"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("code-walkthrough-compliance-extraction-v1797.md");
  private static final String RETIRED_RENDERER =
      "OpsShardReadinessCodeWalkthroughComplianceRegistryRenderer.java";
  private static final List<String> EXTRACTED_IMPLEMENTATION_FILES =
      List.of(
          "OpsShardReadinessCodeWalkthroughComplianceArchiveRangeCatalog.java",
          "OpsShardReadinessCodeWalkthroughComplianceBoundaryRuleCatalog.java",
          "OpsShardReadinessCodeWalkthroughComplianceDocumentationRuleCatalog.java",
          "ReportRenderer.java",
          "OpsShardReadinessCodeWalkthroughComplianceRegistryResponse.java",
          "OpsShardReadinessCodeWalkthroughComplianceRegistryService.java",
          "OpsShardReadinessCodeWalkthroughComplianceRegistrySupport.java",
          "OpsShardReadinessCodeWalkthroughComplianceRequiredHeadingCatalog.java",
          "OpsShardReadinessCodeWalkthroughComplianceRoutePaths.java",
          "OpsShardReadinessCodeWalkthroughComplianceTestCoverageCatalog.java",
          "OpsShardReadinessCodeWalkthroughComplianceVersionCatalog.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "code-walkthrough-compliance-extraction-v1797.md",
            "ops.maintenance.walkthrough.compliance",
            "1,330 to 1,319");
    assertThat(note)
        .contains(
            "v1797",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,319",
            "Do not rename or move archive roots",
            "/api/v1/ops/shard-readiness/code-walkthrough-compliance-registry");
  }

  @Test
  void extractedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(WALKTHROUGH_COMPLIANCE_SOURCE_ROOT)).isTrue();

    for (String fileName : EXTRACTED_IMPLEMENTATION_FILES) {
      assertThat(Files.isRegularFile(WALKTHROUGH_COMPLIANCE_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the walkthrough compliance package")
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should no longer be directly in the root ops package")
          .isFalse();
    }
    assertThat(Files.exists(WALKTHROUGH_COMPLIANCE_SOURCE_ROOT.resolve(RETIRED_RENDERER)))
        .isFalse();
    assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(RETIRED_RENDERER))).isFalse();
  }

  @Test
  void rootStillOwnsPublicControllerAndRouteAggregation() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessCodeWalkthroughComplianceRegistryController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
