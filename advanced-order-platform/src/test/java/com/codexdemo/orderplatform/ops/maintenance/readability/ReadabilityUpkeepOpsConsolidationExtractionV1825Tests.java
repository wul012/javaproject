package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1825Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PROFILE_SECTION_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "signedapprovaldraftprofilesection"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("signed-approval-draft-profile-section-extraction-v1825.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessSignedApprovalDraftProfileSectionRegistryService.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionRegistryResponse.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionRegistrySupport.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionSourceCatalog.java",
          "OpsShardReadinessSignedApprovalDraftProfileSectionRenderer.java");
  private static final String ROOT_RETAINED_CONTROLLER =
      "OpsShardReadinessSignedApprovalDraftProfileSectionRegistryController.java";

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-draft-profile-section-extraction-v1825.md",
            "ops.maintenance.signedapprovaldraftprofilesection",
            "897 to 887");
    assertThat(note)
        .contains(
            "v1825",
            "36 production files",
            "first of three",
            "Direct Java files in the root `ops` package fall from 897 to 887",
            "total `ops` Java file count stays at 1,352",
            "Do not rename or move archive roots",
            "ProfileSectionHandoff");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(PROFILE_SECTION_SOURCE_ROOT)).isTrue();
    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(PROFILE_SECTION_SOURCE_ROOT.resolve(fileName))).isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftProfileSectionGateCatalog.java")))
        .isFalse();
  }

  @Test
  void rootStillOwnsControllerAndRouteAggregation() {
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve(ROOT_RETAINED_CONTROLLER))).isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void remainingProfileSectionLayersStayAvailableForNextVersions() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService.java")))
        .isTrue();
  }

  @Test
  void rootPackageRatchetDoesNotRegressAboveV1825Count() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(887);
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
