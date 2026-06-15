package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1804Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path SIGNED_APPROVAL_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "signedapproval"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("signed-approval-route-path-consolidation-v1804.md");
  private static final List<String> RELOCATED_ROUTE_PATH_FILES =
      List.of(
          "OpsShardReadinessSignedApprovalArtifactDraftReadinessRoutePaths.java",
          "OpsShardReadinessSignedApprovalCaptureArtifactPreflightRoutePaths.java",
          "OpsShardReadinessSignedApprovalCapturePreflightRoutePaths.java");

  @Test
  void consolidationNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "signed-approval-route-path-consolidation-v1804.md",
            "ops.maintenance.signedapproval",
            "1,243 to 1,240");
    assertThat(note)
        .contains(
            "v1804",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,240",
            "Do not rename or move archive roots",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_SIGNED_APPROVAL_");
  }

  @Test
  void relocatedRoutePathFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(SIGNED_APPROVAL_SOURCE_ROOT)).isTrue();

    for (String fileName : RELOCATED_ROUTE_PATH_FILES) {
      assertThat(Files.isRegularFile(SIGNED_APPROVAL_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the signed approval package")
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should no longer be directly in the root ops package")
          .isFalse();
    }
  }

  @Test
  void rootStillOwnsRouteAggregation() {
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
