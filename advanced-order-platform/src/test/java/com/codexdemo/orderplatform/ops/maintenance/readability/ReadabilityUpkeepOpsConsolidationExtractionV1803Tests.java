package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1803Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path SANDBOX_CONNECTION_SOURCE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "sandboxconnection"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("sandbox-connection-extraction-v1803.md");
  private static final String RETIRED_DOSSIER_RENDERER =
      "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierRenderer.java";
  private static final String RETIRED_MANIFEST_RENDERER =
      "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestRenderer.java";
  private static final List<String> EXTRACTED_IMPLEMENTATION_FILES =
      List.of(
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierBoundaryCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierContextCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierDownstreamIntakeCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierExecutionGuardCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierHandoffCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierPreconditionEvidenceCatalog.java",
          "DossierRenderer.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSourceCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierVerificationCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierWarningCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestBoundaryCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestCodeHealthCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestFieldCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestHandoffCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestReferenceCatalog.java",
          "ManifestRenderer.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSourceCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSplitCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestVerificationCatalog.java",
          "OpsShardReadinessSandboxConnectionRoutePaths.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndex() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();

    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);

    assertThat(readme)
        .contains(
            "sandbox-connection-extraction-v1803.md",
            "ops.maintenance.sandboxconnection",
            "1,269 to 1,243");
    assertThat(note)
        .contains(
            "v1803",
            "contract-preserving",
            "Direct Java files in root `ops` package",
            "1,243",
            "Do not rename or move archive roots",
            "/api/v1/ops/shard-readiness/sandbox-connection-blocked-execution-context-normalization-dossier",
            "/api/v1/ops/shard-readiness/sandbox-connection-precheck-upstream-receipt-verification-manifest");
  }

  @Test
  void extractedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(SANDBOX_CONNECTION_SOURCE_ROOT)).isTrue();

    for (String fileName : EXTRACTED_IMPLEMENTATION_FILES) {
      assertThat(Files.isRegularFile(SANDBOX_CONNECTION_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should live in the sandbox connection package")
          .isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName)))
          .as(fileName + " should no longer be directly in the root ops package")
          .isFalse();
    }
    assertThat(Files.exists(SANDBOX_CONNECTION_SOURCE_ROOT.resolve(RETIRED_DOSSIER_RENDERER)))
        .isFalse();
    assertThat(Files.exists(SANDBOX_CONNECTION_SOURCE_ROOT.resolve(RETIRED_MANIFEST_RENDERER)))
        .isFalse();
    assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(RETIRED_DOSSIER_RENDERER))).isFalse();
    assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(RETIRED_MANIFEST_RENDERER))).isFalse();
  }

  @Test
  void rootStillOwnsPublicControllersAndRouteAggregation() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierController.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
