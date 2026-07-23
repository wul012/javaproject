package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class SandboxExtractionTests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "sandboxconnection"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("sandbox-connection-extraction-v1803.md");

  private static final List<String> DOSSIER_FILES =
      List.of(
          "DossierCatalog.java",
          "DossierRenderer.java",
          "DossierSupport.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierResponse.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService.java");

  private static final List<String> MANIFEST_FILES =
      List.of(
          "ManifestCatalog.java",
          "ManifestRenderer.java",
          "ManifestSupport.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestResponse.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService.java",
          "OpsShardReadinessSandboxConnectionRoutePaths.java");

  private static final List<String> RETIRED_DOSSIER_FILES =
      List.of(
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierBoundaryCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierContextCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierDownstreamIntakeCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierExecutionGuardCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierHandoffCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierPreconditionEvidenceCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSourceCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierSupport.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierVerificationCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierWarningCatalog.java",
          "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierRenderer.java");

  private static final List<String> RETIRED_MANIFEST_FILES =
      List.of(
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestBoundaryCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestCodeHealthCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestFieldCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestHandoffCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestReferenceCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSourceCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSplitCatalog.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestSupport.java",
          "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestVerificationCatalog.java");

  @Test
  void extractionNoteStaysDiscoverable() throws IOException {
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
  void packageHasExactCurrentOwners() throws IOException {
    var expected = Stream.concat(DOSSIER_FILES.stream(), MANIFEST_FILES.stream()).toList();
    try (var files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.map(path -> path.getFileName().toString()).toList())
          .containsExactlyInAnyOrderElementsOf(expected);
    }
    for (String fileName : expected) {
      assertThat(Files.exists(OPS_ROOT.resolve(fileName))).as(fileName).isFalse();
    }
  }

  @Test
  void dossierUsesOneTypedSnapshot() throws IOException {
    String catalog = read(PACKAGE_ROOT.resolve("DossierCatalog.java"));
    String renderer = read(PACKAGE_ROOT.resolve("DossierRenderer.java"));
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierService.java"));
    String support = read(PACKAGE_ROOT.resolve("DossierSupport.java"));

    assertThat(catalog.lines().count()).isLessThan(400);
    assertThat(occurrences(catalog, "List.copyOf(")).isEqualTo(10);
    assertThat(occurrences(service, "DossierCatalog.evidence(rehearsal)")).isEqualTo(1);
    assertThat(renderer).contains("render(DossierCatalog.Evidence evidence)");
    assertThat(support).contains("DossierCatalog.Evidence evidence");
    assertThat(catalog).doesNotContain("DossierSupport");

    for (String retired : RETIRED_DOSSIER_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(retired))).as(retired).isFalse();
      assertThat(Files.exists(OPS_ROOT.resolve(retired))).as(retired).isFalse();
    }
    assertThat(
            Files.exists(
                PACKAGE_ROOT.resolve(
                    "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestRenderer.java")))
        .isFalse();
  }

  @Test
  void manifestUsesOneTypedSnapshot() throws IOException {
    String catalog = read(PACKAGE_ROOT.resolve("ManifestCatalog.java"));
    String renderer = read(PACKAGE_ROOT.resolve("ManifestRenderer.java"));
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestService.java"));
    String support = read(PACKAGE_ROOT.resolve("ManifestSupport.java"));

    assertThat(catalog.lines().count()).isLessThan(400);
    assertThat(occurrences(catalog, "List.copyOf(")).isEqualTo(10);
    for (String field :
        List.of(
            "sourceReceipts",
            "splitModules",
            "evidenceReferences",
            "precheckFields",
            "boundaryGuards",
            "codeHealthGates",
            "verificationGates",
            "handoffNotes")) {
      assertThat(catalog).contains(field + " = List.copyOf(" + field + ")");
    }
    assertThat(occurrences(service, "ManifestCatalog.evidence(rehearsal)")).isEqualTo(1);
    assertThat(renderer).contains("render(ManifestCatalog.Evidence evidence)");
    assertThat(support).contains("ManifestCatalog.Evidence evidence");
    assertThat(catalog).doesNotContain("ManifestSupport", "ManifestRenderer");

    for (String retired : RETIRED_MANIFEST_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(retired))).as(retired).isFalse();
      assertThat(Files.exists(OPS_ROOT.resolve(retired))).as(retired).isFalse();
    }
  }

  @Test
  void rootKeepsPublicControllers() {
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessSandboxConnectionBlockedExecutionContextDossierController.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessSandboxConnectionPrecheckUpstreamReceiptVerificationManifestController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"))).isTrue();
  }

  private static long occurrences(String source, String needle) {
    return source.lines().filter(line -> line.contains(needle)).count();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
