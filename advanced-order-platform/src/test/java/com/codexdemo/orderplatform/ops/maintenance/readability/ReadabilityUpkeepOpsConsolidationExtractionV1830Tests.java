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
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1829-v1833",
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
        .contains(
            "OpsShardReadinessOperatorEvidenceValueSupplyAdapterPreflightRoutePaths",
            ".OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CATALOG",
            ".OPERATOR_EVIDENCE_VALUE_SUPPLY_ADAPTER_PREFLIGHT_CLOSEOUT");
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
  void baseValueSupplyEndpointConstantsArePublicForTheAdapterPackage() throws IOException {
    assertThat(
            read(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyCatalogService.java")))
        .contains("public static final String ENDPOINT");
    assertThat(
            read(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplyMissingValuePolicyService.java")))
        .contains("public static final String ENDPOINT");
    assertThat(
            read(
                OPS_SOURCE_ROOT.resolve(
                    "OpsShardReadinessOperatorEvidenceValueSupplySourceEvidenceGuardService.java")))
        .contains("public static final String ENDPOINT");
    assertThat(
            read(
                OPS_SOURCE_ROOT.resolve(
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
          .isEqualTo(848);
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
            "禁止硬凑",
            "本项目",
            "## 实际工作量说明",
            "## 入口路由",
            "## 响应模型",
            "## 上游证据配置",
            "## 服务层核心流程",
            "## Java 证据检查",
            "## mini-kv 证据检查",
            "## 阻断与安全边界",
            "## 测试覆盖",
            "## 一句话总结");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
