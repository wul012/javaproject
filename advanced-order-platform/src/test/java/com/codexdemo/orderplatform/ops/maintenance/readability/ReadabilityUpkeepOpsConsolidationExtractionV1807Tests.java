package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1807Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path VALUE_DRAFT_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "operatorevidencevaluedraft"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("operator-evidence-value-draft-extraction-v1807.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueDraftRoutePaths.java",
          "OpsShardReadinessOperatorEvidenceValueDraftCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueDraftResponse.java",
          "OpsShardReadinessOperatorEvidenceValueDraftSlotCatalog.java",
          "OpsShardReadinessOperatorEvidenceValueDraftSupport.java");
  private static final List<String> ROOT_RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueDraftAssuranceController.java",
          "OpsShardReadinessOperatorEvidenceValueDraftFoundationController.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "operator-evidence-value-draft-extraction-v1807.md",
            "ops.maintenance.operatorevidencevaluedraft",
            "1,183 to 1,167");
    assertThat(note)
        .contains(
            "v1807",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,167",
            "Do not rename or move archive roots",
            "OperatorEvidenceImportPreflight");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(VALUE_DRAFT_SOURCE_ROOT)).isTrue();

    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(VALUE_DRAFT_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the operator evidence value draft package")
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

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
