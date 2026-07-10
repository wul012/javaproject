package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1840Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptanceroutepathsplit"));
  private static final Path SUSTAINMENT_ROOT = PACKAGE_ROOT.resolve("sustainment");
  private static final Path DOC =
      Path.of(
          "docs", "ops", "release-acceptance-route-path-split-base-closeout-extraction-v1840.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1838-v1842",
          "version-1840-production-excellence-release-acceptance-route-path-split-base-closeout-extraction.md");
  private static final List<String> MOVED_FILES =
      List.of(
          "OpsShardReadinessReleaseAcceptanceRoutePaths.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutBoundaryCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutItemCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutSupport.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSupport.java");

  @Test
  void baseCloseoutAndRouteOwnerMoveWhileControllersStayRootVisible() {
    for (String file : MOVED_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitController.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutController.java")))
        .isTrue();
  }

  @Test
  void movedOwnerAndRetainedAggregatorExposeOnlyTheComparisonBoundary() throws IOException {
    String owner = read(PACKAGE_ROOT.resolve("OpsShardReadinessReleaseAcceptanceRoutePaths.java"));
    String root = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    String catalog =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog.java"));
    assertThat(owner)
        .contains(
            "public final class OpsShardReadinessReleaseAcceptanceRoutePaths",
            "public static final String BASE_PATH",
            "public static final String RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY");
    assertThat(root)
        .contains(
            "public final class OpsShardReadinessRoutePaths",
            "public static final String BASE_PATH",
            "public static final String RELEASE_ACCEPTANCE_ROUTE_PATH_SPLIT_REGISTRY")
        .contains("OpsShardReadinessReleaseAcceptanceRoutePaths");
    assertThat(catalog)
        .contains(
            "import com.codexdemo.orderplatform.ops.OpsShardReadinessRoutePaths",
            "stablePath.equals(splitPath)");
  }

  @Test
  void sustainmentConsumesMovedCloseoutBoundary() throws IOException {
    String sustainmentService =
        read(
            SUSTAINMENT_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService.java"));
    String sustainmentSource =
        read(
            SUSTAINMENT_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog.java"));
    assertThat(sustainmentService)
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService");
    assertThat(sustainmentSource)
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse");
  }

  @Test
  void spotbugsAndCountRatchetsFollowTheTwoMovedResponses() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse",
            "ops.maintenance.releaseacceptanceroutepathsplit."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse")
        .doesNotContain(
            "com.codexdemo.orderplatform.ops.OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse",
            "com.codexdemo.orderplatform.ops.OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse");
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(751);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(1352);
    }
  }

  @Test
  void docsAndWalkthroughBindTheVersionBeforeVerify() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "775 -> 751",
            "670 -> 646",
            "55 remaining ReleaseAcceptanceRoutePathSplit");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1840",
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
