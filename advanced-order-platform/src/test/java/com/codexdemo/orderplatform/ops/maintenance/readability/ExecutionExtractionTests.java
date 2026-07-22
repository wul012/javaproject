package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ExecutionExtractionTests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateexecution"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateexecution"));
  private static final Path DOC =
      Path.of("docs", "ops", "minimal-read-only-gate-execution-extraction-v1843.md");
  private static final Path BASE_CATALOG_DOC =
      Path.of("docs", "ops", "execution-registry-catalog-v1889.md");
  private static final Path CURRENT_DOC = Path.of("docs", "ops", "archive-catalog-v1890.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1843-v1847",
          "version-1843-production-excellence-minimal-read-only-gate-execution-extraction.md");
  private static final List<String> CURRENT_FILES =
      List.of(
          "ArchiveRenderer.java",
          "ArchiveCatalog.java",
          "ExecutionRenderer.java",
          "RegistryCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionRegistrySupport.java");
  private static final List<String> RETIRED_CATALOG_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryPolicyCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionCiBatchCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionOperatorHandoffCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionSourcePlanCatalog.java");
  private static final List<String> RETIRED_ARCHIVE_CATALOG_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveScorecardCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveSourceRegistrySnapshotCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionCiBatchVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionGateCheckVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionOperatorHandoffVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetVerificationCatalog.java");
  private static final List<String> REMOVED_RENDERER_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRendererSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionArtifactVerificationRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionBoundaryVerificationRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionGateRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionGateVerificationRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionReadTargetRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionRendererSupport.java");
  private static final List<String> CURRENT_TEST_FILES =
      List.of(
          "ArchiveTestData.java",
          "ArchiveCatalogTests.java",
          "ArchiveMarkdownBoundaryTests.java",
          "ArchiveRegistryServiceTests.java",
          "ArchiveRenderingTests.java",
          "ArchiveResponseOracleTests.java",
          "ExecutionMarkdownTests.java",
          "ExecutionTestData.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionMarkdownBoundaryTests.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryServiceTests.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionRendererTests.java",
          "RegistryCatalogTests.java",
          "RegistryResponseOracleTests.java");
  private static final List<String> REMOVED_TEST_SUPPORT_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryTestSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryTestSupport.java");

  @Test
  void keepsCurrentExecutionClosure() {
    assertThat(CURRENT_FILES).hasSize(10);
    CURRENT_FILES.forEach(
        file -> {
          assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
          assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
        });
    REMOVED_RENDERER_FILES.forEach(
        file -> assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse());
    RETIRED_CATALOG_FILES.forEach(
        file -> assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse());
    RETIRED_ARCHIVE_CATALOG_FILES.forEach(
        file -> assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse());
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryController.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                OPS_ROOT.resolve(
                    "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryController.java")))
        .isTrue();
  }

  @Test
  void keepsCurrentTestClosure() {
    assertThat(CURRENT_TEST_FILES).hasSize(13);
    CURRENT_TEST_FILES.forEach(
        file -> {
          assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
          assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
        });
    REMOVED_TEST_SUPPORT_FILES.forEach(
        file -> assertThat(Files.exists(PACKAGE_TEST_ROOT.resolve(file))).as(file).isFalse());
    assertThat(
            Files.isRegularFile(
                TEST_ROOT.resolve(
                    "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryControllerTests.java")))
        .isTrue();
    assertThat(
            Files.isRegularFile(
                TEST_ROOT.resolve(
                    "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryControllerTests.java")))
        .isTrue();
  }

  @Test
  void keepsRegistryCatalogConverged() throws IOException {
    String catalog = read(PACKAGE_ROOT.resolve("RegistryCatalog.java"));
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService.java"));

    assertThat(catalog.lines().count()).isLessThanOrEqualTo(300);
    assertThat(catalog).contains("record Evidence(").doesNotContain("ExecutionRenderer");
    assertThat(occurrences(catalog, "List.copyOf(")).isEqualTo(7);
    assertThat(service)
        .contains("var evidence = RegistryCatalog.evidence();", "ExecutionRenderer.render(");
    assertThat(occurrences(service, "RegistryCatalog.evidence()")).isEqualTo(1);
    assertThat(read(BASE_CATALOG_DOC))
        .contains(
            "7 -> 1",
            "5/5/20/10/4/6/5/6/20",
            "8f33da2c1ed32695ef245c69cbf4a90d4b5b62324bb98e13c115ebec26df0b36",
            "## Failure Conditions");
  }

  @Test
  void keepsArchiveCatalogConverged() throws IOException {
    String catalog = read(PACKAGE_ROOT.resolve("ArchiveCatalog.java"));
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService.java"));
    String renderer = read(PACKAGE_ROOT.resolve("ArchiveRenderer.java"));
    String support =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistrySupport.java"));

    assertThat(catalog.lines().count()).isLessThanOrEqualTo(200);
    assertThat(catalog).contains("record Evidence(").doesNotContain("ArchiveRenderer");
    assertThat(occurrences(catalog, "List.copyOf(")).isEqualTo(8);
    assertThat(service)
        .contains(
            "var evidence = ArchiveCatalog.evidence(sourceRegistry);",
            "ArchiveRenderer.render(evidence)");
    assertThat(occurrences(service, "ArchiveCatalog.evidence(sourceRegistry)")).isEqualTo(1);
    assertThat(renderer).contains("render(ArchiveCatalog.Evidence evidence)");
    assertThat(support).contains("ArchiveCatalog.Evidence evidence");
    assertThat(read(CURRENT_DOC))
        .contains(
            "8 -> 1",
            "1/6/5/20/10/4/5/7/6/20",
            "d5e75e352cee97a6f2c30111e0af57bb39af770b31cd420a018994b003e05859",
            "## Failure Conditions");
  }

  @Test
  void keepsArchiveDependencyDirection() throws IOException {
    String base =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService.java"));
    String archive =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService.java"));
    assertThat(base).contains("OpsShardReadinessReleaseAcceptanceRoutePaths");
    assertThat(archive)
        .contains(
            "OpsShardReadinessReleaseAcceptanceRoutePaths",
            "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryService");
  }

  @Test
  void keepsOperatorCiBoundary() throws IOException {
    Path operatorCiRoot =
        OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorcihandoff"));
    assertThat(
            read(
                operatorCiRoot.resolve(
                    "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService.java")))
        .contains("ops.maintenance.minimalreadonlygateexecution")
        .contains(
            "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService");
    for (String file :
        List.of(
            "HandoffCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport.java")) {
      assertThat(read(operatorCiRoot.resolve(file)))
          .as(file)
          .contains("ops.maintenance.minimalreadonlygateexecution")
          .contains(
              "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse");
    }
  }

  @Test
  void tightensGlobalAndPackageCaps() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            "ops.maintenance.minimalreadonlygateexecution."
                + "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse",
            "ops.maintenance.minimalreadonlygateexecution."
                + "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse")
        .doesNotContain(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessMinimalReadOnlyGateExecutionRegistryResponse");
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(665);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(
              files.filter(Files::isRegularFile).filter(path -> path.toString().endsWith(".java")))
          .hasSizeLessThanOrEqualTo(1249);
    }
  }

  @Test
  void keepsHistoricalV1843Evidence() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "root 696 -> 665",
            "movable 591 -> 560",
            "execution bucket 31 -> 0",
            "minimalreadonlygateexecution");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1843",
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

  private static int occurrences(String source, String token) {
    return (source.length() - source.replace(token, "").length()) / token.length();
  }
}
