package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1842Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptancepackage"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "releaseacceptancepackage"));
  private static final Path DOC =
      Path.of("docs", "ops", "release-acceptance-package-extraction-v1842.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1838-v1842",
          "version-1842-production-excellence-release-acceptance-package-extraction.md");
  private static final List<String> CURRENT_FILES =
      List.of(
          "ArchiveIndexRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageArchiveCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCiCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexCriteriaCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexHandoffCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexItemCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexSourceCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexSupport.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexVerificationCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptCriteriaCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageDecisionCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageLineageCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageNextChangeCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageReviewCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRuntimeBoundaryCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageScorecardCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSupport.java",
          "ReceiptRenderer.java",
          "ReportRenderer.java");
  private static final List<String> REMOVED_RENDERER_FILES =
      List.of(
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageArchiveRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCiRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageDecisionRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageLineageRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageNextChangeRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererSupport.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageReviewRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRuntimeBoundaryRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageScorecardRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceRenderer.java");
  private static final List<String> CURRENT_TEST_FILES =
      List.of(
          "ArchiveIndexTestData.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCatalogTests.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexCatalogTests.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexImmutabilityTests.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageImmutabilityTests.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageRendererTests.java",
          "PackageMarkdownTests.java",
          "PackageTestData.java",
          "ReceiptTestData.java");
  private static final List<String> RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageController.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptController.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexController.java");

  @Test
  void extractedImplementationCanOnlyShrink() throws IOException {
    assertThat(CURRENT_FILES).hasSize(26);
    for (String file : CURRENT_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    assertThat(REMOVED_RENDERER_FILES).hasSize(13);
    for (String file : REMOVED_RENDERER_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(26);
    }
    for (String controller : RETAINED_CONTROLLERS) {
      assertThat(Files.isRegularFile(OPS_ROOT.resolve(controller))).as(controller).isTrue();
    }
  }

  @Test
  void packageTestsUseShortFactories() throws IOException {
    assertThat(CURRENT_TEST_FILES).hasSize(9);
    for (String file : CURRENT_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(9);
    }
    assertThat(
            PACKAGE_TEST_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageTestSupport.java"))
        .doesNotExist();
    assertThat(
            PACKAGE_TEST_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTestSupport.java"))
        .doesNotExist();
    assertThat(
            PACKAGE_TEST_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexTestSupport.java"))
        .doesNotExist();
    assertThat(
            Files.isRegularFile(
                TEST_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageControllerTests.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                TEST_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptTests.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                TEST_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexRendererControllerTests.java")))
        .isTrue();
  }

  @Test
  void serviceChainConsumesTheMovedSustainmentBoundary() throws IOException {
    String acceptance =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService.java"));
    String source =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceCatalog.java"));
    String receipt =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService.java"));
    String archive =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexService.java"));
    assertThat(acceptance)
        .contains(
            "releaseacceptanceroutepathsplit.sustainment."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService",
            "OpsShardReadinessReleaseAcceptanceRoutePaths");
    assertThat(source)
        .contains(
            "releaseacceptanceroutepathsplit.sustainment."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse");
    assertThat(receipt)
        .contains(
            "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService");
    assertThat(archive)
        .contains(
            "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptService");
  }

  @Test
  void retainedControllersImportOnlyThePublicPackageBoundary() throws IOException {
    for (String controller : RETAINED_CONTROLLERS) {
      assertThat(read(OPS_ROOT.resolve(controller)))
          .contains("ops.maintenance.releaseacceptancepackage")
          .contains("OpsShardReadinessReleaseAcceptanceRoutePaths");
    }
  }

  @Test
  void spotbugsAndCountRatchetsFollowTheMove() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "ops.maintenance.releaseacceptancepackage."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse",
            "ops.maintenance.releaseacceptancepackage."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutReceiptResponse",
            "ops.maintenance.releaseacceptancepackage."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageCloseoutArchiveIndexResponse")
        .doesNotContain(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageResponse");
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(696);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(1249);
    }
  }

  @Test
  void docsAndWalkthroughBindTheVersionBeforeVerify() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "root 732 -> 696",
            "movable 627 -> 591",
            "split bucket 36 -> 0",
            "releaseacceptancepackage");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1842",
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

  private boolean isJava(Path path) {
    return path.toString().endsWith(".java");
  }
}
