package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1805Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path CANDIDATE_DOCUMENT_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "candidatedocument"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("candidate-document-extraction-v1805.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessCandidateDocumentRoutePaths.java",
          "OpsShardReadinessCandidateDocumentHandoffService.java",
          "OpsShardReadinessCandidateDocumentHandoffResponse.java",
          "OpsShardReadinessCandidateDocumentProfileSectionRegistryService.java",
          "OpsShardReadinessCandidateDocumentRequestPackageResponse.java");
  private static final List<String> ROOT_RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessCandidateDocumentHandoffController.java",
          "OpsShardReadinessCandidateDocumentProfileSectionRegistryController.java",
          "OpsShardReadinessCandidateDocumentSubmissionPrecheckController.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "candidate-document-extraction-v1805.md",
            "ops.maintenance.candidatedocument",
            "1,240 to 1,183");
    assertThat(note)
        .contains(
            "v1805",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,183",
            "Do not rename or move archive roots",
            "/api/v1/ops/shard-readiness");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(CANDIDATE_DOCUMENT_SOURCE_ROOT)).isTrue();

    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(CANDIDATE_DOCUMENT_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the candidate document package")
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
