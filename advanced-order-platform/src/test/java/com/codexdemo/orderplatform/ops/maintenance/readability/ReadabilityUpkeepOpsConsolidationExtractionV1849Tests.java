package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1849Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT = OPS_ROOT.resolve(Path.of("maintenance", "ciarc"));
  private static final Path HANDOFF_PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "releasearchivehandoff"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT = TEST_ROOT.resolve(Path.of("maintenance", "ciarc"));
  private static final Path DOC =
      Path.of("docs", "ops", "operator-ci-release-acceptance-archive-extraction-v1849.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1848-v1852",
          "version-1849-production-excellence-operator-ci-release-acceptance-archive-extraction.md");
  private static final String PACKAGE_IMPORT = "ops.maintenance.ciarc";
  private static final String PREFIX =
      "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseAcceptanceArchive";
  private static final List<String> MOVED_FILES =
      List.of(
          PREFIX + "ArtifactManifestCatalog.java",
          PREFIX + "BoundarySealCatalog.java",
          PREFIX + "BoundarySealRenderer.java",
          PREFIX + "CiAttestationCatalog.java",
          PREFIX + "CiAttestationRenderer.java",
          PREFIX + "CloseoutLedgerCatalog.java",
          PREFIX + "CloseoutLedgerRenderer.java",
          PREFIX + "ManifestRenderer.java",
          PREFIX + "OperatorPackCatalog.java",
          PREFIX + "OperatorPackRenderer.java",
          PREFIX + "RegistryRenderer.java",
          PREFIX + "RegistryResponse.java",
          PREFIX + "RegistryService.java",
          PREFIX + "RegistrySupport.java",
          PREFIX + "RendererSupport.java",
          PREFIX + "RetentionWindowCatalog.java",
          PREFIX + "RetentionWindowRenderer.java",
          PREFIX + "RoutePackageCatalog.java",
          PREFIX + "RoutePackageRenderer.java",
          PREFIX + "ScorecardCatalog.java",
          PREFIX + "ScorecardRenderer.java",
          PREFIX + "SourceCatalog.java",
          PREFIX + "SourceRenderer.java");
  private static final List<String> MOVED_TEST_FILES =
      List.of(
          PREFIX + "RegistryCiBoundaryTests.java",
          PREFIX + "RegistryImmutabilityTests.java",
          PREFIX + "RegistryRetentionCloseoutTests.java",
          PREFIX + "RegistryRouteOperatorTests.java",
          PREFIX + "RegistrySourceManifestTests.java",
          PREFIX + "RegistryTestSupport.java");

  @Test
  void archiveImplementationMovesWhileControllerStaysRootVisible() throws IOException {
    assertThat(MOVED_FILES).hasSize(23);
    for (String file : MOVED_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(23);
    }
    Path controller = OPS_ROOT.resolve(PREFIX + "RegistryController.java");
    assertThat(Files.isRegularFile(controller)).isTrue();
    assertThat(read(controller)).contains(PACKAGE_IMPORT);
  }

  @Test
  void packageTestsMoveWhileControllerMarkdownTestStaysRoot() throws IOException {
    assertThat(MOVED_TEST_FILES).hasSize(6);
    for (String file : MOVED_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(6);
    }
    Path controllerTest = TEST_ROOT.resolve(PREFIX + "RegistryControllerMarkdownTests.java");
    assertThat(Files.isRegularFile(controllerTest)).isTrue();
    assertThat(read(controllerTest)).contains(PACKAGE_IMPORT);
  }

  @Test
  void pathBudgetUpstreamAndRouteOwnershipRemainExplicit() throws IOException {
    String service = read(PACKAGE_ROOT.resolve(PREFIX + "RegistryService.java"));
    assertThat(service)
        .contains("ops.maintenance.ciaccept", "OpsShardReadinessReleaseAcceptanceRoutePaths")
        .doesNotContain("OpsShardReadinessRoutePaths.BASE_PATH");
    assertThat(PACKAGE_ROOT.toString()).contains("ciarc");
  }

  @Test
  void verificationHandoffImportsOnlyThePublicArchiveBoundary() throws IOException {
    String response = PREFIX + "RegistryResponse";
    for (Path file : handoffSourceFiles()) {
      String source = read(file);
      if (source.contains(response)) {
        assertThat(source).as(file.toString()).contains(PACKAGE_IMPORT + "." + response);
      }
    }
    assertThat(
            read(
                HANDOFF_PACKAGE_ROOT.resolve(
                    "OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoffService.java")))
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
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(219);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1352);
    }
  }

  @Test
  void docsAndWalkthroughBindTheVersionBeforeVerify() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 548 -> 525",
            "movable 443 -> 420",
            "Operator-CI bucket 23 -> 0",
            "354/361",
            "252/259",
            "ciarc");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1849",
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

  private List<Path> handoffSourceFiles() throws IOException {
    try (Stream<Path> files = Files.list(HANDOFF_PACKAGE_ROOT)) {
      return files
          .filter(Files::isRegularFile)
          .filter(this::isJava)
          .filter(
              path ->
                  path.getFileName()
                      .toString()
                      .startsWith("OpsShardReadinessReleaseAcceptanceArchiveVerificationHandoff"))
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
