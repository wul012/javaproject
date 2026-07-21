package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1850Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releasearchivehandoff"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "releasearchivehandoff"));
  private static final Path ROUTE_SPLIT_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releaseacceptanceroutepathsplit"));
  private static final Path DOC =
      Path.of("docs", "ops", "release-acceptance-archive-verification-handoff-extraction-v1850.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1848-v1852",
          "version-1850-production-excellence-release-acceptance-archive-verification-handoff-extraction.md");
  private static final String PACKAGE_IMPORT = "ops.maintenance.releasearchivehandoff";
  private static final String PREFIX =
      "OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoff";
  private static final List<String> CURRENT_FILES =
      List.of(
          PREFIX + "ArtifactCatalog.java",
          PREFIX + "BoundaryCatalog.java",
          PREFIX + "CiCatalog.java",
          PREFIX + "CloseoutCatalog.java",
          PREFIX + "OperatorCatalog.java",
          PREFIX + "RequirementCatalog.java",
          PREFIX + "Response.java",
          PREFIX + "RetentionCatalog.java",
          PREFIX + "RouteCatalog.java",
          PREFIX + "ScorecardCatalog.java",
          PREFIX + "Service.java",
          PREFIX + "SourceCatalog.java",
          PREFIX + "Support.java",
          "ReportRenderer.java");
  private static final List<String> REMOVED_RENDERER_FILES =
      List.of(
          PREFIX + "ArtifactRenderer.java",
          PREFIX + "BoundaryRenderer.java",
          PREFIX + "CiRenderer.java",
          PREFIX + "CloseoutRenderer.java",
          PREFIX + "OperatorRenderer.java",
          PREFIX + "Renderer.java",
          PREFIX + "RendererSupport.java",
          PREFIX + "RequirementRenderer.java",
          PREFIX + "RetentionRenderer.java",
          PREFIX + "RouteRenderer.java",
          PREFIX + "ScorecardRenderer.java",
          PREFIX + "SourceRenderer.java");
  private static final List<String> REQUIRED_TEST_FILES =
      List.of(
          "HandoffMarkdownTests.java",
          "HandoffTestData.java",
          PREFIX + "ArtifactRouteOperatorTests.java",
          PREFIX + "CiBoundaryTests.java",
          PREFIX + "ImmutabilityTests.java",
          PREFIX + "RetentionCloseoutScorecardTests.java",
          PREFIX + "SourceRequirementTests.java");

  @Test
  void handoffImplementationMovesWhileControllerStaysRootVisible() throws IOException {
    assertThat(CURRENT_FILES).hasSize(14);
    for (String file : CURRENT_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : REMOVED_RENDERER_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(14);
    }
    Path controller = OPS_ROOT.resolve(PREFIX + "Controller.java");
    assertThat(Files.isRegularFile(controller)).isTrue();
    assertThat(read(controller)).contains(PACKAGE_IMPORT);
  }

  @Test
  void packageTestsMoveWhileControllerMarkdownTestStaysRoot() throws IOException {
    assertThat(REQUIRED_TEST_FILES).hasSize(7);
    for (String file : REQUIRED_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(7);
    }
    Path controllerTest = TEST_ROOT.resolve(PREFIX + "ControllerMarkdownTests.java");
    assertThat(Files.isRegularFile(controllerTest)).isTrue();
    assertThat(read(controllerTest)).contains(PACKAGE_IMPORT);
  }

  @Test
  void upstreamAndRouteOwnershipRemainExplicit() throws IOException {
    String service = read(PACKAGE_ROOT.resolve(PREFIX + "Service.java"));
    assertThat(service)
        .contains("ops.maintenance.ciarc", "OpsShardReadinessReleaseAcceptanceRoutePaths")
        .doesNotContain("OpsShardReadinessService.BASE_PATH");
  }

  @Test
  void routePathSplitImportsOnlyThePublicHandoffBoundary() throws IOException {
    String response = PREFIX + "Response";
    assertThat(
            read(
                ROUTE_SPLIT_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceRoutePathSplitService.java")))
        .contains(PACKAGE_IMPORT + "." + PREFIX + "Service");
    for (String file : List.of("SourceCatalog.java", "RegistryAssembler.java")) {
      assertThat(read(ROUTE_SPLIT_ROOT.resolve(file)))
          .as(file)
          .contains(PACKAGE_IMPORT + "." + response);
    }
  }

  @Test
  void spotbugsAndShrinkOnlyRatchetsFollowTheMove() throws IOException {
    String response = PREFIX + "Response";
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(PACKAGE_IMPORT + "." + response)
        .doesNotContain("com.codexdemo.orderplatform.ops." + response);
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(104);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1249);
    }
  }

  @Test
  void docsAndWalkthroughBindTheVersionBeforeVerify() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 525 -> 500",
            "movable 420 -> 395",
            "Handoff bucket 25 -> 0",
            "222/234",
            "200/212",
            "releasearchivehandoff");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1850",
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

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
