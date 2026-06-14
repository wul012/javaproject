package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1802Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path CREDENTIAL_RESOLVER_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "credentialresolver"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve(
          "credential-resolver-disabled-fake-harness-evidence-archive-extraction-v1802.md");
  private static final List<String> EXTRACTED_IMPLEMENTATION_FILES =
      List.of(
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveBoundaryCatalog.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveHandoffCatalog.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRenderer.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRequirementCatalog.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveResponse.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveRuntimeGuardCatalog.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveService.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSourceCatalog.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveSupport.java",
          "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveVerificationCatalog.java",
          "OpsShardReadinessCredentialResolverRoutePaths.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "credential-resolver-disabled-fake-harness-evidence-archive-extraction-v1802.md",
            "ops.maintenance.credentialresolver",
            "1,280 to 1,269");
    assertThat(note)
        .contains(
            "v1802",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,269",
            "Do not rename or move archive roots",
            "/api/v1/ops/shard-readiness/credential-resolver-disabled-fake-harness-evidence-archive");
  }

  @Test
  void extractedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(CREDENTIAL_RESOLVER_SOURCE_ROOT)).isTrue();

    for (String fileName : EXTRACTED_IMPLEMENTATION_FILES) {
      assertThat(Files.isRegularFile(CREDENTIAL_RESOLVER_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the credential resolver package")
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
                    "OpsShardReadinessCredentialResolverDisabledFakeHarnessEvidenceArchiveController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
