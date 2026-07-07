package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1826Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path TEXT_PACKAGE_PROFILE_SECTION_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(
          Path.of("maintenance", "signedapprovaldrafttextpackageprofilesection"));
  private static final Path SIGNED_APPROVAL_ROUTE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "signedapproval"));
  private static final Path PROFILE_SECTION_HANDOFF_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "signedapprovaldraftprofilesectionhandoff"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("signed-approval-draft-text-package-profile-section-extraction-v1826.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryService.java",
          "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryResponse.java",
          "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistrySupport.java",
          "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSourceCatalog.java",
          "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionSubmissionRenderer.java");
  private static final String ROOT_RETAINED_CONTROLLER =
      "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRegistryController.java";

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-draft-text-package-profile-section-extraction-v1826.md",
            "ops.maintenance.signedapprovaldrafttextpackageprofilesection",
            "887 to 874");
    assertThat(note)
        .contains(
            "v1826",
            "second of three",
            "Direct Java files in the root `ops` package fall from 887 to 874",
            "total `ops` Java file count stays at 1,352",
            "Do not rename or move archive roots",
            "ProfileSectionHandoff");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(TEXT_PACKAGE_PROFILE_SECTION_SOURCE_ROOT)).isTrue();
    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(Files.isRegularFile(TEXT_PACKAGE_PROFILE_SECTION_SOURCE_ROOT.resolve(fileName)))
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionGateCatalog.java")))
        .isFalse();
  }

  @Test
  void rootStillOwnsControllerAndRouteAggregation() {
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve(ROOT_RETAINED_CONTROLLER))).isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                SIGNED_APPROVAL_ROUTE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftTextPackageProfileSectionRoutePaths.java")))
        .isTrue();
  }

  @Test
  void remainingProfileSectionHandoffLayerWasClosedByFollowupExtraction() {
    assertThat(
            Files.isRegularFile(
                PROFILE_SECTION_HANDOFF_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService.java")))
        .isTrue();
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSignedApprovalDraftProfileSectionHandoffService.java")))
        .isFalse();
  }

  @Test
  void rootPackageRatchetDoesNotRegressAboveV1826Count() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(864);
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
