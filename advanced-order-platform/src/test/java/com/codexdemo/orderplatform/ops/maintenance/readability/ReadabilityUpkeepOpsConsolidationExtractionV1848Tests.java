package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1848Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "ciaccept"));
  private static final Path ARCHIVE_PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "ciarc"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "ciaccept"));
  private static final Path DOC =
      Path.of("docs", "ops", "operator-ci-release-acceptance-extraction-v1848.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1848-v1852",
          "version-1848-production-excellence-operator-ci-release-acceptance-extraction.md");
  private static final String PACKAGE_IMPORT = "ops.maintenance.ciaccept";
  private static final String PREFIX =
      "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptance";
  private static final List<String> CURRENT_FILES =
      List.of(
          PREFIX + "BoundaryControlCatalog.java",
          PREFIX + "CiReplayCatalog.java",
          PREFIX + "CloseoutCatalog.java",
          PREFIX + "EvidenceChainCatalog.java",
          PREFIX + "ReadinessCatalog.java",
          PREFIX + "RegistryResponse.java",
          PREFIX + "RegistryService.java",
          PREFIX + "RegistrySupport.java",
          PREFIX + "ReplayDecisionCatalog.java",
          PREFIX + "RetentionPolicyCatalog.java",
          PREFIX + "ScorecardCatalog.java",
          PREFIX + "SignoffLaneCatalog.java",
          PREFIX + "SourceDossierCatalog.java",
          "ReportRenderer.java");
  private static final List<String> REMOVED_RENDERER_FILES =
      List.of(
          PREFIX + "BoundaryRenderer.java",
          PREFIX + "CiReplayRenderer.java",
          PREFIX + "CloseoutRenderer.java",
          PREFIX + "EvidenceChainRenderer.java",
          PREFIX + "ReadinessRenderer.java",
          PREFIX + "RegistryRenderer.java",
          PREFIX + "RendererSupport.java",
          PREFIX + "ReplayDecisionRenderer.java",
          PREFIX + "RetentionRenderer.java",
          PREFIX + "ScorecardRenderer.java",
          PREFIX + "SignoffRenderer.java",
          PREFIX + "SourceRenderer.java");
  private static final List<String> REQUIRED_TEST_FILES =
      List.of(
          "ReleaseAcceptanceMarkdownTests.java",
          "ReleaseAcceptanceTestData.java",
          PREFIX + "RegistryCiBoundaryTests.java",
          PREFIX + "RegistryCloseoutScorecardTests.java",
          PREFIX + "RegistryEvidenceSignoffTests.java",
          PREFIX + "RegistryImmutabilityTests.java",
          PREFIX + "RegistryRetentionReplayTests.java",
          PREFIX + "RegistrySourceReadinessTests.java");

  @Test
  void releaseAcceptanceImplementationMovesWhileControllerStaysRootVisible() throws IOException {
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
    Path controller = OPS_ROOT.resolve(PREFIX + "RegistryController.java");
    assertThat(Files.isRegularFile(controller)).isTrue();
    assertThat(read(controller)).contains(PACKAGE_IMPORT);
  }

  @Test
  void packageTestsMoveWhileControllerMarkdownTestStaysRoot() throws IOException {
    assertThat(REQUIRED_TEST_FILES).hasSize(8);
    for (String file : REQUIRED_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(8);
    }
    Path controllerTest = TEST_ROOT.resolve(PREFIX + "RegistryControllerMarkdownTests.java");
    assertThat(Files.isRegularFile(controllerTest)).isTrue();
    assertThat(read(controllerTest)).contains(PACKAGE_IMPORT);
  }

  @Test
  void pathBudgetUpstreamAndRouteOwnershipRemainExplicit() throws IOException {
    String service = read(PACKAGE_ROOT.resolve(PREFIX + "RegistryService.java"));
    assertThat(service)
        .contains(
            "ops.maintenance.operatorcidossier", "OpsShardReadinessReleaseAcceptanceRoutePaths")
        .doesNotContain("OpsShardReadinessService.BASE_PATH");
    assertThat(PACKAGE_ROOT.toString()).contains("ciaccept");
  }

  @Test
  void archiveImportsOnlyThePublicReleaseAcceptanceBoundary() throws IOException {
    String response = PREFIX + "RegistryResponse";
    for (Path file : archiveSourceFiles()) {
      String source = read(file);
      if (source.contains(response)) {
        assertThat(source).as(file.toString()).contains(PACKAGE_IMPORT + "." + response);
      }
    }
    assertThat(read(ARCHIVE_PACKAGE_ROOT.resolve(PREFIX + "ArchiveRegistryService.java")))
        .contains(PACKAGE_IMPORT + "." + PREFIX + "RegistryService");
  }

  @Test
  void spotbugsAndShrinkOnlyRatchetsFollowTheMove() throws IOException {
    String response = PREFIX + "RegistryResponse";
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(PACKAGE_IMPORT + "." + response)
        .doesNotContain("com.codexdemo.orderplatform.ops." + response);
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(104);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1304);
    }
  }

  @Test
  void docsAndWalkthroughBindTheVersionBeforeVerify() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 573 -> 548",
            "movable 468 -> 443",
            "Operator-CI bucket 48 -> 23",
            "339/347",
            "247/255",
            "ciaccept");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1848",
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

  private List<Path> archiveSourceFiles() throws IOException {
    try (Stream<Path> files = Files.list(ARCHIVE_PACKAGE_ROOT)) {
      return files
          .filter(Files::isRegularFile)
          .filter(this::isJava)
          .filter(path -> path.getFileName().toString().startsWith(PREFIX + "Archive"))
          .toList();
    }
  }

  private boolean isJava(Path path) {
    return path.toString().endsWith(".java");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
