package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1830Tests {

  private static final Path DOCS_ROOT = Path.of("docs", "ops");
  private static final Path OPS_SOURCE_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path ADAPTER_PACKAGE_ROOT =
      OPS_SOURCE_ROOT.resolve(
          Path.of("maintenance", "operatorevidencevaluesupplyadapterpreflight"));
  private static final Path EXTRACTION_NOTE =
      DOCS_ROOT.resolve("operator-evidence-value-supply-adapter-preflight-extraction-v1830.md");
  private static final Path WALKTHROUGH =
      findWalkthrough(
          "version-1830-production-excellence-operator-evidence-value-supply-adapter-preflight-extraction.md");
  private static final List<String> RELOCATED_FILES =
      List.of(
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightArchivePlanService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCatalogService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCompatibilityMatrixService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightDigestBlueprintService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightMissingValueRejectionService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightOperatorRehearsalChecklistService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightPayloadFirewallService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightProvenanceBindingService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRedactionBoundaryService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuntimeSubmissionLockService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSourceEvidenceSnapshotService.java",
          "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSupport.java");

  @Test
  void extractionNoteStaysDiscoverableFromOpsIndexAndCensus() throws IOException {
    assertThat(Files.isRegularFile(EXTRACTION_NOTE)).isTrue();
    String readme = read(DOCS_ROOT.resolve("README.md"));
    String note = read(EXTRACTION_NOTE);
    String census = read(DOCS_ROOT.resolve("extraction-endgame-census-v1828.md"));

    assertThat(readme)
        .contains(
            "operator-evidence-value-supply-adapter-preflight-extraction-v1830.md",
            "ops.maintenance.operatorevidencevaluesupplyadapterpreflight",
            "864 to 848");
    assertThat(note)
        .contains(
            "v1830",
            "Direct Java files in the root `ops` package fall from 864 to 848",
            "direct-root non-controller backlog falls from 759 to 743",
            "files stay at 1,352",
            "RuleCatalog",
            "Do not rename or move archive roots");
    assertThat(census)
        .contains("v1830 progress", "864 to 848", "759 to 743", "OperatorEvidenceValueSupply base");
  }

  @Test
  void relocatedImplementationFilesLiveInNarrowPackage() {
    assertThat(Files.isDirectory(ADAPTER_PACKAGE_ROOT)).isTrue();
    for (String fileName : RELOCATED_FILES) {
      assertThat(Files.isRegularFile(ADAPTER_PACKAGE_ROOT.resolve(fileName))).isTrue();
      assertThat(Files.exists(OPS_SOURCE_ROOT.resolve(fileName))).isFalse();
    }
  }

  @Test
  void rootKeepsOnlyAdapterPreflightControllersForHttpEntryPoints() {
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightAssuranceController.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightFoundationController.java")))
        .isTrue();
    assertThat(Files.isRegularFile(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java")))
        .isTrue();
  }

  @Test
  void routeOwnershipMovesToAdapterLeafWithoutChangingSuffixes() throws IOException {
    String leaf =
        read(
            ADAPTER_PACKAGE_ROOT.resolve(
                "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths.java"));
    String rootAggregator = read(OPS_SOURCE_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    String service =
        read(
            ADAPTER_PACKAGE_ROOT.resolve(
                "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService.java"));

    assertThat(leaf)
        .contains(
            "BASE_PATH = \"/api/v1/ops/shard-readiness\"",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CATALOG",
            "\"/operator-evidence-value-supply-adapter-preflight-catalog\"",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CLOSEOUT",
            "\"/operator-evidence-value-supply-adapter-preflight-closeout\"");
    assertThat(rootAggregator)
        .doesNotContain(
            "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CATALOG",
            "OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CLOSEOUT");
    assertThat(service)
        .contains(
            "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths.BASE_PATH",
            ".OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CLOSEOUT");
  }

  @Test
  void ruleCatalogIsCollapsedIntoSlotCatalogToAvoidFileGrowth() throws IOException {
    assertThat(
            Files.exists(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.java")))
        .isFalse();
    assertThat(
            Files.exists(
                ADAPTER_PACKAGE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRuleCatalog.java")))
        .isFalse();

    String slotCatalog =
        read(
            ADAPTER_PACKAGE_ROOT.resolve(
                "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightSlotCatalog.java"));
    assertThat(slotCatalog)
        .contains(
            "RULE_COUNT = 18",
            "allRules()",
            "rules(",
            "ADAPTER_RULE_18_CLOSEOUT_LOCK_SUMMARY_REQUIRED");
  }

  @Test
  void upstreamAndDownstreamEndpointReadersUsePublicMovedConstants() throws IOException {
    String itemCatalog =
        read(
            OPS_SOURCE_ROOT.resolve(
                Path.of(
                    "maintenance",
                    "approvalpreflight",
                    "OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightItemCatalog.java")));
    String supportTest =
        read(
            Path.of(
                "src",
                "test",
                "java",
                "com",
                "codexdemo",
                "orderplatform",
                "ops",
                "maintenance",
                "approvalpreflight",
                "OpsShardReadinessOperatorEvidenceValueSupplyApprovalPreflightSupportTests.java"));

    assertThat(itemCatalog)
        .contains(
            "ops.maintenance.operatorevidencevaluesupplyadapterpreflight."
                + "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService")
        .doesNotContain(
            "ops.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService");
    assertThat(supportTest)
        .contains(
            "ops.maintenance.operatorevidencevaluesupplyadapterpreflight."
                + "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightCloseoutService");
  }

  @Test
  void spotbugsMirrorBlocksFollowMovedResponseFqn() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "com.codexdemo.orderplatform.ops.maintenance.operatorevidencevaluesupplyadapterpreflight."
                + "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse");
    assertThat(spotbugs)
        .doesNotContain(
            "com.codexdemo.orderplatform.ops.OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightResponse");
  }

  @Test
  void baseValueSupplyEndpointConstantsRemainPublicForTheAdapterPackage() throws IOException {
    Path basePackage =
        OPS_SOURCE_ROOT.resolve(Path.of("maintenance", "operatorevidencevaluesupply"));
    assertThat(
            read(
                basePackage.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyCatalogService.java")))
        .contains("public static final String ENDPOINT");
    assertThat(
            read(
                basePackage.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.java")))
        .contains("public static final String ENDPOINT");
    assertThat(
            read(
                basePackage.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService.java")))
        .contains("public static final String ENDPOINT");
    assertThat(
            read(
                basePackage.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySideEffectGateService.java")))
        .contains("public static final String ENDPOINT");
  }

  @Test
  void rootPackageAndTotalOpsRatchetsStayTight() throws IOException {
    try (Stream<Path> paths = Files.list(OPS_SOURCE_ROOT)) {
      assertThat(
              paths
                  .filter(Files::isRegularFile)
                  .filter(path -> path.getFileName().toString().endsWith(".java"))
                  .count())
          .isLessThanOrEqualTo(848);
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
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1830",
            "\u7981\u6b62\u786c\u51d1",
            "\u672c\u9879\u76ee",
            "## \u5b9e\u9645\u5de5\u4f5c\u91cf\u8bf4\u660e",
            "## \u5165\u53e3\u8def\u7531",
            "## \u54cd\u5e94\u6a21\u578b",
            "## \u4e0a\u6e38\u8bc1\u636e\u914d\u7f6e",
            "## \u670d\u52a1\u5c42\u6838\u5fc3\u6d41\u7a0b",
            "## Java \u8bc1\u636e\u68c0\u67e5",
            "## mini-kv \u8bc1\u636e\u68c0\u67e5",
            "## \u963b\u65ad\u4e0e\u5b89\u5168\u8fb9\u754c",
            "## \u6d4b\u8bd5\u8986\u76d6",
            "## \u4e00\u53e5\u8bdd\u603b\u7ed3");
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
