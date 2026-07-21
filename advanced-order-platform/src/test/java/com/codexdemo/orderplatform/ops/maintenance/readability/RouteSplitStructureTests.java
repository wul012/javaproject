package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class RouteSplitStructureTests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path FAMILY_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptanceroutepathsplit"));
  private static final Path FAMILY_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "releaseacceptanceroutepathsplit"));
  private static final Path SUSTAINMENT_ROOT = FAMILY_ROOT.resolve("sustainment");
  private static final Path DOC = Path.of("docs", "ops", "route-split-internals-v1883.md");
  private static final Path WALKTHROUGH =
      Path.of("代码讲解记录_生产雏形阶段9", "v1883-v1887", "v1883-route-split-internals.md");

  private static final List<String> PRODUCTION_FILES =
      List.of(
          "BoundaryCatalog.java",
          "CloseoutAssembler.java",
          "CloseoutBoundaryCatalog.java",
          "CloseoutItemCatalog.java",
          "CloseoutRenderer.java",
          "CompatibilityCatalog.java",
          "ConsumerCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitResponse.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitService.java",
          "OpsShardReadinessReleaseAcceptanceRoutePaths.java",
          "RegistryAssembler.java",
          "ReportRenderer.java",
          "RouteCatalog.java",
          "ScorecardCatalog.java",
          "SourceCatalog.java");

  private static final List<String> TEST_FILES =
      List.of(
          "CloseoutCatalogTests.java",
          "CloseoutImmutabilityTests.java",
          "CloseoutMarkdownTests.java",
          "CloseoutTestData.java",
          "SplitCatalogTests.java",
          "SplitCompatibilityTests.java",
          "SplitImmutabilityTests.java",
          "SplitMarkdownTests.java",
          "SplitTestData.java");

  private static final List<String> INTERNAL_FILES =
      List.of(
          "BoundaryCatalog.java",
          "CloseoutAssembler.java",
          "CloseoutBoundaryCatalog.java",
          "CloseoutItemCatalog.java",
          "CloseoutRenderer.java",
          "CompatibilityCatalog.java",
          "ConsumerCatalog.java",
          "RegistryAssembler.java",
          "ReportRenderer.java",
          "RouteCatalog.java",
          "ScorecardCatalog.java",
          "SourceCatalog.java");

  private static final List<String> RETIRED_FILES =
      List.of(
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitBoundaryRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutBoundaryCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutItemCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutSupport.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitCompatibilityRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitConsumerRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitRendererSupport.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitRouteRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitScorecardRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceCatalog.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSourceRenderer.java",
          "OpsShardReadinessReleaseAcceptanceRoutePathSplitSupport.java");

  @Test
  void familyKeepsPurposefulOwners() throws IOException {
    assertThat(javaFileNames(FAMILY_ROOT)).containsExactlyElementsOf(PRODUCTION_FILES);
    assertThat(javaFileNames(FAMILY_TEST_ROOT)).containsExactlyElementsOf(TEST_FILES);
    assertThat(TEST_ROOT.resolve("SplitControllerTests.java")).isRegularFile();
    assertThat(TEST_ROOT.resolve("CloseoutControllerTests.java")).isRegularFile();
  }

  @Test
  void internalNamesStayShortAndPrivate() throws IOException {
    for (String file : INTERNAL_FILES) {
      String source = read(FAMILY_ROOT.resolve(file));
      String stem = file.substring(0, file.length() - ".java".length());

      assertThat(stem).as(file).hasSizeLessThanOrEqualTo(40);
      assertThat(source).as(file).contains("final class " + stem);
      assertThat(source).as(file).doesNotContain("public class", "public final class");
    }
  }

  @Test
  void retiredShellsStayGone() {
    for (String file : RETIRED_FILES) {
      assertThat(FAMILY_ROOT.resolve(file)).as(file).doesNotExist();
    }
    assertThat(
            TEST_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitControllerTests.java"))
        .doesNotExist();
    assertThat(
            TEST_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutControllerTests.java"))
        .doesNotExist();
  }

  @Test
  void servicesDependOnDomainOwners() throws IOException {
    String registry =
        read(FAMILY_ROOT.resolve("OpsShardReadinessReleaseAcceptanceRoutePathSplitService.java"));
    String closeout =
        read(
            FAMILY_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService.java"));

    assertThat(registry)
        .contains(
            "SourceCatalog.snapshots",
            "RouteCatalog.routes",
            "CompatibilityCatalog.checks",
            "BoundaryCatalog.guards",
            "ConsumerCatalog.handoffs",
            "ScorecardCatalog.scorecard",
            "RegistryAssembler.response",
            "ReportRenderer.render");
    assertThat(closeout)
        .contains(
            "CloseoutItemCatalog.items",
            "CloseoutBoundaryCatalog.assertions",
            "CloseoutAssembler.response",
            "CloseoutRenderer.render");
  }

  @Test
  void downstreamUsesOnlyPublicCloseoutBoundary() throws IOException {
    String service =
        read(
            SUSTAINMENT_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentService.java"));
    String catalog =
        read(
            SUSTAINMENT_ROOT.resolve(
                "OpsShardReadinessReleaseAcceptanceRoutePathSplitSustainmentSourceCatalog.java"));

    assertThat(service)
        .contains("OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutService")
        .doesNotContain("CloseoutAssembler", "CloseoutRenderer");
    assertThat(catalog)
        .contains("OpsShardReadinessReleaseAcceptanceRoutePathSplitCloseoutResponse")
        .doesNotContain("CloseoutAssembler", "CloseoutRenderer");
  }

  @Test
  void docsAndShrinkRatchetsBindVersion() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix", "24 -> 17", "38 -> 32", "22 -> 14", "1258 -> 1251");
    assertThat(read(WALKTHROUGH))
        .contains(
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
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1249);
    }
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
