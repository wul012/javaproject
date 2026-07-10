package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1841Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptanceroutepathsplit", "sustainment"));
  private static final Path ACCEPTANCE_PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptancepackage"));
  private static final Path DOC =
      Path.of("docs", "ops", "release-acceptance-route-path-split-sustainment-extraction-v1841.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1838-v1842",
          "version-1841-production-excellence-release-acceptance-route-path-split-sustainment-extraction.md");
  private static final List<String> MOVED_FILES =
      List.of(
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentBoundaryRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentCiRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentConsumerRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentDriftRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentOwnershipRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentRendererSupport.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentScorecardRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSupport.java");

  @Test
  void sustainmentImplementationMovesWhileItsControllerStaysRootVisible() {
    for (String file : MOVED_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentController.java")))
        .isTrue();
  }

  @Test
  void movedLayerConsumesOnlyTheV1840CloseoutBoundary() throws IOException {
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService.java"));
    String source =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog.java"));
    assertThat(service)
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService");
    assertThat(source)
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse");
  }

  @Test
  void acceptancePackageImportsTheMovedSustainmentBoundary() throws IOException {
    String service =
        read(
            ACCEPTANCE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageService.java"));
    String source =
        read(
            ACCEPTANCE_PACKAGE_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentAcceptancePackageSourceCatalog.java"));
    assertThat(service)
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit.sustainment."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService");
    assertThat(source)
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit.sustainment."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse");
  }

  @Test
  void spotbugsAndCountRatchetsFollowTheMove() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "ops.maintenance.releaseacceptanceroutepathsplit.sustainment."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse")
        .doesNotContain(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentResponse");
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(732);
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

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
