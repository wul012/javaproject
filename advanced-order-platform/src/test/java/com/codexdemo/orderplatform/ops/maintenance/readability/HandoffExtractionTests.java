package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class HandoffExtractionTests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorcihandoff"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorcihandoff"));
  private static final Path DOC =
      Path.of("docs", "ops", "minimal-read-only-gate-operator-ci-handoff-core-extraction-v1844.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1843-v1847",
          "version-1844-production-excellence-minimal-read-only-gate-operator-ci-handoff-core-extraction.md");
  private static final String PACKAGE_IMPORT =
      "ops.maintenance.minimalreadonlygateoperatorcihandoff";
  private static final List<String> CURRENT_FILES =
      List.of(
          "ArchiveCatalog.java",
          "ArchiveRenderer.java",
          "HandoffCatalog.java",
          "HandoffRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport.java");
  private static final List<String> RETIRED_CATALOG_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceHandoffCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBatchCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBoundaryLockCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffSourceArchiveCatalog.java");
  private static final List<String> REMOVED_RENDERER_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveRendererSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRendererSupport.java");
  private static final List<String> CURRENT_TEST_FILES =
      List.of(
          "ArchiveCatalogTests.java",
          "ArchiveChecksTests.java",
          "ArchiveRegistryServiceTests.java",
          "ArchiveResponseOracleTests.java",
          "ArchiveTestData.java",
          "HandoffCatalogTests.java",
          "HandoffMarkdownTests.java",
          "HandoffRegistryServiceTests.java",
          "HandoffResponseOracleTests.java",
          "HandoffTestData.java");
  private static final List<String> RETIRED_TEST_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryAggregateChecksTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryBoundaryScorecardTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryCatalogTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySourceTests.java");
  private static final List<String> RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryController.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryController.java");

  @Test
  void extractedImplementationCanOnlyShrink() throws IOException {
    assertThat(CURRENT_FILES).hasSize(10);
    for (String file : CURRENT_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    assertThat(REMOVED_RENDERER_FILES).hasSize(11);
    for (String file : REMOVED_RENDERER_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : RETIRED_CATALOG_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(10);
    }
    for (String controller : RETAINED_CONTROLLERS) {
      assertThat(Files.isRegularFile(OPS_ROOT.resolve(controller))).as(controller).isTrue();
      assertThat(read(OPS_ROOT.resolve(controller))).contains(PACKAGE_IMPORT);
    }
  }

  @Test
  void packageTestsUseCurrentOwners() throws IOException {
    assertThat(CURRENT_TEST_FILES).hasSize(10);
    for (String file : CURRENT_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : RETIRED_TEST_FILES) {
      assertThat(Files.exists(PACKAGE_TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(10);
    }
    assertThat(
            PACKAGE_TEST_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryTestSupport.java"))
        .doesNotExist();
    assertThat(
            PACKAGE_TEST_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryTestSupport.java"))
        .doesNotExist();
    for (String test :
        List.of(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryControllerMarkdownTests.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryControllerMarkdownTests.java")) {
      assertThat(Files.isRegularFile(TEST_ROOT.resolve(test))).as(test).isTrue();
      assertThat(read(TEST_ROOT.resolve(test))).contains(PACKAGE_IMPORT);
    }
  }

  @Test
  void upstreamAndRouteOwnershipRemainExplicit() throws IOException {
    String base =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService.java"));
    String catalog = read(PACKAGE_ROOT.resolve("HandoffCatalog.java"));
    String archive =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService.java"));
    assertThat(base)
        .contains(
            "ops.maintenance.minimalreadonlygateexecution",
            "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService",
            "OpsShardReadinessReleaseAcceptanceRoutePaths",
            "HandoffCatalog.evidence(sourceArchive)");
    assertThat(catalog)
        .contains(
            "ops.maintenance.minimalreadonlygateexecution",
            "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryResponse");
    assertThat(archive)
        .contains(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService",
            "OpsShardReadinessReleaseAcceptanceRoutePaths",
            "ArchiveCatalog.evidence(sourceHandoff)");
  }

  @Test
  void baseCatalogConvergenceStaysTyped() throws IOException {
    String catalog = read(PACKAGE_ROOT.resolve("HandoffCatalog.java"));
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService.java"));
    String renderer = read(PACKAGE_ROOT.resolve("HandoffRenderer.java"));
    String support =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport.java"));

    assertThat(Files.readAllLines(PACKAGE_ROOT.resolve("HandoffCatalog.java")))
        .hasSizeLessThan(200);
    assertThat(count(catalog, "List.copyOf(")).isEqualTo(5);
    assertThat(count(service, "HandoffCatalog.evidence(")).isEqualTo(1);
    assertThat(catalog).contains("record Evidence(");
    assertThat(renderer).contains("render(HandoffCatalog.Evidence evidence)");
    assertThat(support).contains("HandoffCatalog.Evidence evidence");
  }

  @Test
  void archiveCatalogConvergenceStaysTyped() throws IOException {
    String catalog = read(PACKAGE_ROOT.resolve("ArchiveCatalog.java"));
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService.java"));
    String renderer = read(PACKAGE_ROOT.resolve("ArchiveRenderer.java"));
    String support =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport.java"));

    assertThat(Files.readAllLines(PACKAGE_ROOT.resolve("ArchiveCatalog.java")))
        .hasSizeLessThan(260);
    assertThat(count(catalog, "List.copyOf(")).isEqualTo(6);
    assertThat(count(service, "ArchiveCatalog.evidence(")).isEqualTo(1);
    assertThat(catalog).contains("record Evidence(");
    assertThat(renderer).contains("render(ArchiveCatalog.Evidence evidence)");
    assertThat(support).contains("ArchiveCatalog.Evidence evidence");
  }

  @Test
  void archiveDigestImportsTheMovedArchiveBoundary() throws IOException {
    Path archiveDigestRoot =
        OPS_ROOT.resolve(
            Path.of("maintenance", "minimalreadonlygateoperatorcihandoffarchivedigest"));
    assertThat(read(archiveDigestRoot.resolve("DigestCatalog.java")))
        .contains(PACKAGE_IMPORT)
        .contains(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse");
    assertThat(
            read(
                archiveDigestRoot.resolve(
                    "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService.java")))
        .contains(PACKAGE_IMPORT)
        .contains(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService");
  }

  @Test
  void spotbugsAndCountRatchetsFollowTheMove() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(
            PACKAGE_IMPORT
                + ".OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse",
            PACKAGE_IMPORT
                + ".OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse")
        .doesNotContain(
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse",
            "com.codexdemo.orderplatform.ops."
                + "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse");
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(638);
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
            "Direct root 665 -> 638",
            "movable 560 -> 533",
            "Operator-CI bucket 140 -> 113",
            "minimalreadonlygateoperatorcihandoff");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1844",
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

  private static int count(String source, String token) {
    return (source.length() - source.replace(token, "").length()) / token.length();
  }
}
