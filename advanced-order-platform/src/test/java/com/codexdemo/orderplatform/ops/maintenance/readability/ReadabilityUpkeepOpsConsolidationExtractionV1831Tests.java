package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1831Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path VALUE_SUPPLY_PACKAGE_ROOT =
      OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "operatorevidencevaluesupply"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("operator-evidence-value-supply-base-extraction-v1831.md");
  private static final Path WALKTHROUGH =
      findWalkthrough(
          "version-1831-production-excellence-operator-evidence-value-supply-base-extraction.md");
  private static final List<String> RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplyArchivePlanService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyDigestBlueprintService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyEnvelopeTemplateService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyOperatorReviewChecklistService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyProvenanceRequirementService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyRedactionPolicyService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyResponse.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySlotCatalog.java",
          "OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyValidationMatrixService.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndexAndCensus() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);
    String census = read(DOCS_ROOT.resolve("extraction-endgame-census-v1828.md"));

    assertThat(readme)
        .contains(
            "operator-evidence-value-supply-base-extraction-v1831.md",
            "ops.maintenance.operatorevidencevaluesupply",
            "848 to 833");
    assertThat(note)
        .contains(
            "v1831",
            "Direct Java files in the root `ops` package fall from 848 to 833",
            "direct-root non-controller backlog falls from 743 to 728",
            "files stay at 1,352",
            "Support",
            "OperatorEvidenceValueSupplyRoutePaths",
            "Do not rename or move archive roots");
    assertThat(census)
        .contains(
            "v1831 progress", "848 to 833", "743 to 728", "OperatorEvidenceValueSupply base | 0");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(VALUE_SUPPLY_PACKAGE_ROOT)).isTrue();
    for (String fileName : RELOCATED_FILES) {
      assertThat(Files.isRegularFile(VALUE_SUPPLY_PACKAGE_ROOT.resolve(fileName))).isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
    assertThat(
            Files.exists(
                VALUE_SUPPLY_PACKAGE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySupport.java")))
        .isFalse();
  }

  @Test
  void rootKeepsOnlyValueSupplyControllersForHttpEntryPoints() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyAssuranceController.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyFoundationController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void routeOwnershipMovesToValueSupplyLeafWithoutChangingSuffixes() throws IOException {
    String leaf =
        read(
            VALUE_SUPPLY_PACKAGE_ROOT.resolve(
                "OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths.java"));
    String rootAggregator = read(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    String service =
        read(
            VALUE_SUPPLY_PACKAGE_ROOT.resolve(
                "OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService.java"));

    assertThat(leaf)
        .contains(
            "BASE_PATH = \"/api/v1/ops/shard-readiness\"",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_CATALOG",
            "\"/operator-evidence-value-supply-catalog\"",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_CLOSEOUT",
            "\"/operator-evidence-value-supply-closeout\"");
    assertThat(rootAggregator)
        .doesNotContain(
            "OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_CATALOG",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_CLOSEOUT");
    assertThat(service)
        .contains(
            "OpsShardReadinessOperatorEvidenceValueSupplyRoutePaths.BASE_PATH",
            ".OPERATOR_EVIDENCE_VALUE_SUPPLY_CLOSEOUT");
  }

  @Test
  void downstreamEndpointReadersImportTheMovedBasePackage() throws IOException {
    String approvalCatalog =
        read(
            OPS_SOURCE_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "approvalpreflight",
                    "OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.java")));
    String adapterCatalog =
        read(
            OPS_SOURCE_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "operatorevidencevaluesupplyadapterpreflight",
                    "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.java")));

    assertThat(approvalCatalog)
        .contains("ops.maintenance.operatorevidencevaluesupply.")
        .doesNotContain("ops.OpsShardReadinessOperatorEvidenceValueSupplyCloseoutService");
    assertThat(adapterCatalog)
        .contains("ops.maintenance.operatorevidencevaluesupply.")
        .doesNotContain("ops.OpsShardReadinessOperatorEvidenceValueSupplyCatalogService");
  }

  @Test
  void spotbugsMirrorBlocksFollowMovedBaseResponseFqn() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupply."
                + "OpsShardReadinessOperatorEvidenceValueSupplyResponse");
    assertThat(spotbugs)
        .doesNotContain(
            "com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyResponse");
  }

  @Test
  void rootPackageAndTotalOpsRatchetsStayTight() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(833);
    }
    try (Stream<Path> paths = Files.walk(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(1352);
    }
  }

  @Test
  void chineseWalkthroughForThisVersionIsCommittedBeforeVerify() throws IOException {
    assertThat(Files.isRegularFile(WALKTHROUGH)).isTrue();
    assertThat(read(WALKTHROUGH)).contains("version-1831");
  }

  private static Path findWalkthrough(String fileName) {
    try (Stream<Path> paths = Files.walk(Path.of("."))) {
      return paths
          .filter(Files::isRegularFile)
          .filter(path -> path.getFileName().toString().equals(fileName))
          .findFirst()
          .orElseThrow(() -> new IllegalStateException("Missing walkthrough " + fileName));
    } catch (IOException ex) {
      throw new IllegalStateException("Unable to locate walkthrough " + fileName, ex);
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
