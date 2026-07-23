package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DossierExtractionTests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path DOSSIER_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "operatorcidossier"));
  private static final Path ACCEPTANCE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "ciaccept"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path DOSSIER_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "operatorcidossier"));
  private static final String DOSSIER_IMPORT = "ops.maintenance.operatorcidossier";
  private static final List<String> CURRENT_FILES =
      List.of(
          "DossierCatalog.java",
          "DossierSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService.java",
          "ReportRenderer.java");
  private static final List<String> CURRENT_TESTS =
      List.of(
          "DossierCatalogTests.java",
          "DossierChecksTests.java",
          "DossierMarkdownTests.java",
          "DossierRegistryServiceTests.java",
          "DossierResponseOracleTests.java",
          "DossierTestData.java");
  private static final List<String> RETIRED_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAcceptanceGateCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAudienceRouteCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierBoundaryAuditCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierCiLaneCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierHandoffReceiptCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierProvenanceCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseChecklistCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierScorecardCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSectionDigestCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSourcePackageCatalog.java");

  @Test
  void dossierKeepsFiveProductionFiles() throws IOException {
    assertThat(fileNames(DOSSIER_ROOT)).containsExactlyInAnyOrderElementsOf(CURRENT_FILES);
    for (String file : RETIRED_FILES) {
      assertThat(Files.exists(DOSSIER_ROOT.resolve(file))).as(file).isFalse();
    }
    Path controller =
        OPS_ROOT.resolve(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryController.java");
    assertThat(controller).isRegularFile();
    assertThat(read(controller)).contains(DOSSIER_IMPORT);
  }

  @Test
  void dossierKeepsSixFocusedTestFiles() throws IOException {
    assertThat(fileNames(DOSSIER_TEST_ROOT)).containsExactlyInAnyOrderElementsOf(CURRENT_TESTS);
    assertThat(fileNames(DOSSIER_TEST_ROOT)).allMatch(name -> stem(name).length() <= 40);
  }

  @Test
  void serviceUsesOneTypedEvidence() throws IOException {
    String service = read(DOSSIER_ROOT.resolve(CURRENT_FILES.get(3)));
    String catalog = read(DOSSIER_ROOT.resolve("DossierCatalog.java"));
    String support = read(DOSSIER_ROOT.resolve("DossierSupport.java"));
    String renderer = read(DOSSIER_ROOT.resolve("ReportRenderer.java"));

    assertThat(count(service, "DossierCatalog.evidence(")).isEqualTo(1);
    assertThat(service).contains("ReportRenderer.render(evidence)", "DossierSupport.response(");
    assertThat(catalog).contains("record Evidence(").doesNotContain("DossierSupport");
    assertThat(catalog.lines().count()).isLessThan(300);
    assertThat(count(catalog, "List.copyOf(")).isEqualTo(10);
    assertThat(renderer).contains("render(DossierCatalog.Evidence evidence)");
    assertThat(support)
        .contains(
            "DossierCatalog.Evidence evidence",
            "DossierCatalog.SOURCE_COUNT",
            "DossierCatalog.PROVENANCE_COUNT",
            "DossierCatalog.DIGEST_COUNT",
            "DossierCatalog.AUDIENCE_COUNT",
            "DossierCatalog.CI_COUNT",
            "DossierCatalog.GATE_COUNT",
            "DossierCatalog.AUDIT_COUNT",
            "DossierCatalog.CHECKLIST_COUNT",
            "DossierCatalog.RECEIPT_COUNT",
            "DossierCatalog.SCORECARD_COUNT");
  }

  @Test
  void countLogicHasOneSharedOwner() throws IOException {
    Path core = OPS_ROOT.resolve(Path.of("maintenance", "evidencecore", "EvidenceCounts.java"));
    assertThat(read(core))
        .contains("public final class EvidenceCounts", "Math.toIntExact", "Predicate<? super T>");

    for (Path caller :
        List.of(
            OPS_ROOT.resolve(
                Path.of(
                    "maintenance", "minimalreadonlygateoperatorcihandoff", "ArchiveCatalog.java")),
            OPS_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "minimalreadonlygateoperatorcihandoffarchivedigest",
                    "DigestCatalog.java")),
            OPS_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "minimalreadonlygateoperatorciconsumerpackage",
                    "PackageCatalog.java")),
            OPS_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "minimalreadonlygateoperatorciconsumerpackage",
                    "PackageSupport.java")),
            DOSSIER_ROOT.resolve("DossierCatalog.java"),
            DOSSIER_ROOT.resolve("DossierSupport.java"))) {
      assertThat(read(caller))
          .as(caller.getFileName().toString())
          .contains("EvidenceCounts.matching")
          .doesNotContain("java.util.function.Predicate");
    }
  }

  @Test
  void downstreamKeepsDossierImports() throws IOException {
    for (String file :
        List.of(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceBoundaryControlCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCiReplayCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceCloseoutCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceEvidenceChainCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReadinessCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistryService.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRegistrySupport.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceReplayDecisionCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceRetentionPolicyCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceScorecardCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceSignoffLaneCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceSourceDossierCatalog.java")) {
      assertThat(read(ACCEPTANCE_ROOT.resolve(file))).as(file).contains(DOSSIER_IMPORT);
    }
  }

  @Test
  void historicalExtractionEvidenceRemains() throws IOException {
    Path doc = Path.of("docs", "ops", "operator-ci-verification-dossier-extraction-v1847.md");
    Path walkthrough =
        Path.of(
            "代码讲解记录_生产雏形阶段6",
            "v1843-v1847",
            "version-1847-production-excellence-operator-ci-verification-dossier-extraction.md");
    assertThat(read(doc))
        .contains("Requirement Evidence Matrix", "Direct root 598 -> 573", "operatorcidossier");
    assertThat(read(walkthrough)).contains("version-1847", "禁止硬凑", "## 实际工作量说明", "## 一句话总结");
  }

  private static List<String> fileNames(Path root) throws IOException {
    try (Stream<Path> paths = Files.list(root)) {
      return paths
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().endsWith(".java"))
          .map(path -> path.getFileName().toString())
          .toList();
    }
  }

  private static String stem(String name) {
    return name.substring(0, name.length() - ".java".length());
  }

  private static int count(String text, String needle) {
    return (text.length() - text.replace(needle, "").length()) / needle.length();
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
