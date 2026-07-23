package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class DigestExtractionTests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorcihandoffarchivedigest"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(
          Path.of("maintenance", "minimalreadonlygateoperatorcihandoffarchivedigest"));
  private static final Path DOC =
      Path.of(
          "docs",
          "ops",
          "minimal-read-only-gate-operator-ci-handoff-archive-digest-extraction-v1845.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1843-v1847",
          "version-1845-production-excellence-minimal-read-only-gate-operator-ci-handoff-archive-digest-extraction.md");
  private static final String PACKAGE_IMPORT =
      "ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest";
  private static final List<String> CURRENT_FILES =
      List.of(
          "DigestCatalog.java",
          "DigestSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService.java",
          "ReportRenderer.java");
  private static final String RETIRED_SUPPORT_FILE =
      "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySupport.java";
  private static final List<String> RETIRED_CATALOG_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestReplayInstructionCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceArchiveCatalog.java");
  private static final List<String> REMOVED_RENDERER_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestBoundaryLockRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPacketRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRendererSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestReplayInstructionRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestScorecardRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSectionRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestSourceRenderer.java");
  private static final List<String> REQUIRED_TEST_FILES =
      List.of(
          "ArchiveDigestMarkdownTests.java",
          "ArchiveDigestTestData.java",
          "DigestCatalogTests.java",
          "DigestChecksTests.java",
          "DigestRegistryServiceTests.java",
          "DigestResponseOracleTests.java");
  private static final List<String> RETIRED_TEST_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryAggregateChecksTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryCatalogTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryConsumerReplayTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistrySourceTests.java");

  @Test
  void archiveDigestMovesWhileControllerStaysRootVisible() throws IOException {
    assertThat(CURRENT_FILES).hasSize(5);
    for (String file : CURRENT_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : REMOVED_RENDERER_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : RETIRED_CATALOG_FILES) {
      assertThat(Files.exists(PACKAGE_ROOT.resolve(file))).as(file).isFalse();
    }
    assertThat(Files.exists(PACKAGE_ROOT.resolve(RETIRED_SUPPORT_FILE))).isFalse();
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(5);
    }
    Path controller =
        OPS_ROOT.resolve(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryController.java");
    assertThat(Files.isRegularFile(controller)).isTrue();
    assertThat(read(controller)).contains(PACKAGE_IMPORT);
  }

  @Test
  void packageTestsMoveWhileControllerMarkdownTestStaysRoot() throws IOException {
    assertThat(REQUIRED_TEST_FILES).hasSize(6);
    for (String file : REQUIRED_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    for (String file : RETIRED_TEST_FILES) {
      assertThat(Files.exists(PACKAGE_TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(6);
    }
    Path controllerTest =
        TEST_ROOT.resolve(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryControllerMarkdownTests.java");
    assertThat(Files.isRegularFile(controllerTest)).isTrue();
    assertThat(read(controllerTest)).contains(PACKAGE_IMPORT);
  }

  @Test
  void upstreamAndRouteOwnershipRemainExplicit() throws IOException {
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService.java"));
    assertThat(service)
        .contains(
            "ops.maintenance.minimalreadonlygateoperatorcihandoff",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveVerificationRegistryService",
            "OpsShardReadinessReleaseAcceptanceRoutePaths",
            "DigestCatalog.evidence(sourceArchive)");
  }

  @Test
  void digestCatalogConvergenceStaysTyped() throws IOException {
    String catalog = read(PACKAGE_ROOT.resolve("DigestCatalog.java"));
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService.java"));
    String renderer = read(PACKAGE_ROOT.resolve("ReportRenderer.java"));
    String support = read(PACKAGE_ROOT.resolve("DigestSupport.java"));

    assertThat(Files.readAllLines(PACKAGE_ROOT.resolve("DigestCatalog.java"))).hasSizeLessThan(260);
    assertThat(count(catalog, "List.copyOf(")).isEqualTo(6);
    assertThat(count(service, "DigestCatalog.evidence(")).isEqualTo(1);
    assertThat(catalog).contains("record Evidence(").doesNotContain("RegistrySupport");
    assertThat(renderer).contains("render(DigestCatalog.Evidence evidence)");
    assertThat(support)
        .contains(
            "DigestCatalog.Evidence evidence",
            "DigestCatalog.SOURCE_COUNT",
            "DigestCatalog.DIGEST_COUNT",
            "DigestCatalog.PACKET_COUNT",
            "DigestCatalog.REPLAY_COUNT",
            "DigestCatalog.LOCK_COUNT",
            "DigestCatalog.SCORECARD_COUNT");
  }

  @Test
  void consumerPackageImportsTheMovedDigestBoundary() throws IOException {
    Path consumerPackageRoot =
        OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorciconsumerpackage"));
    for (String file : List.of("PackageCatalog.java", "PackageSupport.java")) {
      assertThat(read(consumerPackageRoot.resolve(file)))
          .as(file)
          .contains(
              PACKAGE_IMPORT,
              "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse");
    }
    assertThat(
            read(
                consumerPackageRoot.resolve(
                    "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService.java")))
        .contains(
            PACKAGE_IMPORT,
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService");
  }

  @Test
  void spotbugsAndCountRatchetsFollowTheMove() throws IOException {
    String response =
        "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryResponse";
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(PACKAGE_IMPORT + "." + response)
        .doesNotContain("com.codexdemo.orderplatform.ops." + response);
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(621);
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
            "Direct root 638 -> 621",
            "movable 533 -> 516",
            "Operator-CI bucket 113 -> 96",
            "minimalreadonlygateoperatorcihandoffarchivedigest");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1845",
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
