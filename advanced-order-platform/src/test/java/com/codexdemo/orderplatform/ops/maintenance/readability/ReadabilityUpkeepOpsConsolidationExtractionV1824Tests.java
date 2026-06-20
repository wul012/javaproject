package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1824Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path COMPARED_PACKAGE_EVIDENCE_INTAKE_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(
          Path.of(
              "maintenance",
              "signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve(
          "signed-approval-artifact-draft-text-package-compared-package-evidence-intake-extraction-v1824.md");
  private static final List<String> SAMPLE_RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeResponse.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSupport.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeSlotCatalog.java",
          "OpsShardReadinessComparedPackageEvidenceIntakeEndpointRefs.java");
  private static final String ROOT_RETAINED_CONTROLLER =
      "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeController.java";

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-artifact-draft-text-package-compared-package-evidence-intake-extraction-v1824.md",
            "ops.maintenance.signedapprovalartifactdrafttextpackagecomparedpackageevidenceintake",
            "911 to 897");
    assertThat(note)
        .contains(
            "v1824",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "897",
            "total `ops` Java files stay at 1,352",
            "Do not rename or move archive roots",
            "ComparedPackageReview",
            "ProfileSection");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(COMPARED_PACKAGE_EVIDENCE_INTAKE_SOURCE_ROOT)).isTrue();
    for (String fileName : SAMPLE_RELOCATED_FILES) {
      assertThat(
              Files.isRegularFile(COMPARED_PACKAGE_EVIDENCE_INTAKE_SOURCE_ROOT.resolve(fileName)))
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
    assertThat(
            Files.exists(
                COMPARED_PACKAGE_EVIDENCE_INTAKE_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySignedApprovalArtifactDraftTextPackageComparedPackageEvidenceIntakeGuardCatalog.java")))
        .isFalse();
  }

  @Test
  void rootStillOwnsPublicControllerAndRouteAggregation() {
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve(ROOT_RETAINED_CONTROLLER))).isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void rootPackageRatchetDoesNotRegressAboveV1824Count() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(897);
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
