package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1844Tests {

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
          "ArchiveRenderer.java",
          "HandoffRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveArtifactVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveBoundaryVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveCiBatchVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveLaneVerificationCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveScorecardCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveSourceHandoffCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBatchCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffBoundaryLockCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffLaneCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistrySupport.java",
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
          "ArchiveTestData.java",
          "HandoffMarkdownTests.java",
          "HandoffTestData.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryAggregateChecksTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryBoundaryScorecardTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryCatalogTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistrySourceTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryServiceTests.java");
  private static final List<String> RETAINED_CONTROLLERS =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryController.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryController.java");

  @Test
  void extractedImplementationCanOnlyShrink() throws IOException {
    assertThat(CURRENT_FILES).hasSize(18);
    for (String file : CURRENT_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    assertThat(REMOVED_RENDERER_FILES).hasSize(11);
    for (String file : REMOVED_RENDERER_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(18);
    }
    for (String controller : RETAINED_CONTROLLERS) {
      assertThat(Files.isRegularFile(OPS_ROOT.resolve(controller))).as(controller).isTrue();
      assertThat(read(OPS_ROOT.resolve(controller))).contains(PACKAGE_IMPORT);
    }
  }

  @Test
  void packageTestsUseShortFactories() throws IOException {
    assertThat(CURRENT_TEST_FILES).hasSize(8);
    for (String file : CURRENT_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(8);
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
    String archive =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService.java"));
    assertThat(base)
        .contains(
            "ops.maintenance.minimalreadonlygateexecution",
            "OpsShardReadinessMinimalReadOnlyGateExecutionArchiveVerificationRegistryService",
            "OpsShardReadinessReleaseAcceptanceRoutePaths");
    assertThat(archive)
        .contains(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffRegistryService",
            "OpsShardReadinessReleaseAcceptanceRoutePaths");
  }

  @Test
  void archiveDigestImportsTheMovedArchiveBoundary() throws IOException {
    Path archiveDigestRoot =
        OPS_ROOT.resolve(
            Path.of("maintenance", "minimalreadonlygateoperatorcihandoffarchivedigest"));
    List<String> responseConsumers =
        List.of(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySupport.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestReplayInstructionCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceArchiveCatalog.java");
    for (String file : responseConsumers) {
      assertThat(read(archiveDigestRoot.resolve(file)))
          .as(file)
          .contains(PACKAGE_IMPORT)
          .contains(
              "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryResponse");
    }
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
}
