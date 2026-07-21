package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class SustainmentStructureTests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptanceroutepathsplit", "sustainment"));
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "releaseacceptanceroutepathsplit", "sustainment"));
  private static final Path ACCEPTANCE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptancepackage"));
  private static final Path DOC =
      Path.of("docs", "ops", "release-acceptance-route-path-split-sustainment-extraction-v1841.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1838-v1842",
          "version-1841-production-excellence-release-acceptance-route-path-split-sustainment-extraction.md");

  private static final List<String> PRODUCTION_FILES =
      List.of(
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSupport.java",
          "ReportRenderer.java");

  private static final List<String> RETIRED_RENDERER_FILES =
      List.of(
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceRenderer.java");

  private static final List<String> TEST_FILES =
      List.of(
          "ReportMarkdownTests.java",
          "SustainmentCatalogTests.java",
          "SustainmentImmutabilityTests.java",
          "SustainmentTestData.java");

  @Test
  void familyKeepsOneOutputOwner() throws IOException {
    assertThat(javaFileNames(PACKAGE_ROOT)).containsExactlyElementsOf(PRODUCTION_FILES);
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController.java")))
        .isTrue();
  }

  @Test
  void retiredRenderersStayGone() {
    for (String file : RETIRED_RENDERER_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
  }

  @Test
  void shortTestsReplaceLongShells() throws IOException {
    assertThat(javaFileNames(PACKAGE_TEST_ROOT)).containsExactlyElementsOf(TEST_FILES);
    assertThat(Files.isRegularFile(TEST_ROOT.resolve("SustainmentControllerTests.java"))).isTrue();
    assertThat(
            Files.exists(
                TEST_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentControllerTests.java")))
        .isFalse();
    assertThat(PACKAGE_TEST_ROOT)
        .satisfies(
            root ->
                assertThat(
                        root.resolve(
                            "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCatalogTests.java"))
                    .doesNotExist(),
            root ->
                assertThat(
                        root.resolve(
                            "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentImmutabilityTests.java"))
                    .doesNotExist(),
            root ->
                assertThat(
                        root.resolve(
                            "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererTests.java"))
                    .doesNotExist(),
            root ->
                assertThat(
                        root.resolve(
                            "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentTestSupport.java"))
                    .doesNotExist());
  }

  @Test
  void keepsBoundaryDirection() throws IOException {
    assertThat(
            read(
                PACKAGE_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService.java")))
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService");
    assertThat(
            read(
                PACKAGE_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog.java")))
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse");
    assertThat(
            read(
                ACCEPTANCE_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService.java")))
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit.sustainment."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService");
    assertThat(
            read(
                ACCEPTANCE_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceCatalog.java")))
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit.sustainment."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse");
  }

  @Test
  void spotbugsAndCountsFollowFamily() throws IOException {
    assertThat(read(Path.of("config", "spotbugs-exclude.xml")))
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit.sustainment."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse")
        .doesNotContain(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse");
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(732);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1258);
    }
  }

  @Test
  void historicalDocsRemainFrozen() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix", "751 -> 732", "646 -> 627", "split bucket 55 -> 36");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1841",
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

  private boolean isJava(Path path) {
    return path.toString().endsWith(".java");
  }

  private static List<String> javaFileNames(Path root) throws IOException {
    try (Stream<Path> files = Files.list(root)) {
      return files
          .filter(Files::isRegularFile)
          .filter(path -> path.toString().endsWith(".java"))
          .map(path -> path.getFileName().toString())
          .sorted()
          .toList();
    }
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
