package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ConsumerPackageExtractionTests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorciconsumerpackage"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "minimalreadonlygateoperatorciconsumerpackage"));
  private static final Path DOC =
      Path.of(
          "docs", "ops", "minimal-read-only-gate-operator-ci-consumer-package-extraction-v1846.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1843-v1847",
          "version-1846-production-excellence-minimal-read-only-gate-operator-ci-consumer-package-extraction.md");
  private static final Path CURRENT_DOC =
      Path.of("docs", "ops", "consumer-package-catalog-v1894.md");
  private static final Path CURRENT_WALKTHROUGH =
      Path.of("代码讲解记录_生产雏形阶段9", "v1893-v1897", "v1894-consumer-package-catalog.md");
  private static final String PACKAGE_IMPORT =
      "ops.maintenance.minimalreadonlygateoperatorciconsumerpackage";
  private static final List<String> CURRENT_FILES =
      List.of(
          "PackageCatalog.java",
          "PackageSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService.java",
          "ReportRenderer.java");
  private static final List<String> RETIRED_CATALOG_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAudienceCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageBoundaryLockCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSectionCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceDigestCatalog.java");
  private static final String RETIRED_SUPPORT_FILE =
      "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport.java";
  private static final List<String> REMOVED_RENDERER_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAudienceRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageBoundaryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSectionRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceRenderer.java");
  private static final List<String> REQUIRED_TEST_FILES =
      List.of(
          "ConsumerPackageMarkdownTests.java",
          "ConsumerPackageTestData.java",
          "PackageCatalogTests.java",
          "PackageChecksTests.java",
          "PackageRegistryServiceTests.java",
          "PackageResponseOracleTests.java");
  private static final List<String> RETIRED_TEST_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryBoundaryChecklistTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryCatalogTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySourceTests.java");

  @Test
  void consumerPackageMovesWhileControllerStaysRootVisible() throws IOException {
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
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryController.java");
    assertThat(Files.isRegularFile(controller)).isTrue();
    assertThat(read(controller)).contains(PACKAGE_IMPORT);
  }

  @Test
  void packageTestsMoveWhileControllerMarkdownAggregateStaysRoot() throws IOException {
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
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryControllerMarkdownAggregateTests.java");
    assertThat(Files.isRegularFile(controllerTest)).isTrue();
    assertThat(read(controllerTest)).contains(PACKAGE_IMPORT);
  }

  @Test
  void pathChoiceUpstreamAndRouteOwnershipRemainExplicit() throws IOException {
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService.java"));
    assertThat(PACKAGE_ROOT.toString())
        .contains("minimalreadonlygateoperatorciconsumerpackage")
        .doesNotContain("minimalreadonlygateoperatorcihandoffarchivedigestconsumerpackage");
    assertThat(service)
        .contains(
            "ops.maintenance.minimalreadonlygateoperatorcihandoffarchivedigest",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestRegistryService",
            "OpsShardReadinessReleaseAcceptanceRoutePaths",
            "PackageCatalog.evidence(sourceDigest)");
  }

  @Test
  void packageCatalogConvergenceStaysTyped() throws IOException {
    String catalog = read(PACKAGE_ROOT.resolve("PackageCatalog.java"));
    String service =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService.java"));
    String renderer = read(PACKAGE_ROOT.resolve("ReportRenderer.java"));
    String support = read(PACKAGE_ROOT.resolve("PackageSupport.java"));

    assertThat(Files.readAllLines(PACKAGE_ROOT.resolve("PackageCatalog.java")))
        .hasSizeLessThan(300);
    assertThat(count(catalog, "List.copyOf(")).isEqualTo(9);
    assertThat(count(service, "PackageCatalog.evidence(")).isEqualTo(1);
    assertThat(catalog).contains("record Evidence(").doesNotContain("PackageSupport");
    assertThat(renderer).contains("render(PackageCatalog.Evidence evidence)");
    assertThat(support)
        .contains(
            "PackageCatalog.Evidence evidence",
            "PackageCatalog.SOURCE_COUNT",
            "PackageCatalog.MANIFEST_COUNT",
            "PackageCatalog.AUDIENCE_COUNT",
            "PackageCatalog.SECTION_COUNT",
            "PackageCatalog.ACCEPTANCE_COUNT",
            "PackageCatalog.CI_COUNT",
            "PackageCatalog.LOCK_COUNT",
            "PackageCatalog.CHECKLIST_COUNT",
            "PackageCatalog.SCORECARD_COUNT");
  }

  @Test
  void verificationDossierImportsTheMovedConsumerPackageBoundary() throws IOException {
    Path dossierRoot = OPS_ROOT.resolve(Path.of("maintenance", "operatorcidossier"));
    for (String file :
        List.of(
            "DossierCatalog.java",
            "DossierSupport.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService.java")) {
      assertThat(read(dossierRoot.resolve(file))).as(file).contains(PACKAGE_IMPORT);
    }
  }

  @Test
  void spotbugsAndCountRatchetsFollowTheMove() throws IOException {
    String response =
        "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse";
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    assertThat(spotbugs)
        .contains(PACKAGE_IMPORT + "." + response)
        .doesNotContain("com.codexdemo.orderplatform.ops." + response);
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(598);
    }
    try (Stream<Path> files = Files.walk(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava))
          .hasSizeLessThanOrEqualTo(1336);
    }
  }

  @Test
  void docsAndWalkthroughBindTheVersionBeforeVerify() throws IOException {
    assertThat(read(DOC))
        .contains(
            "Requirement Evidence Matrix",
            "Direct root 621 -> 598",
            "movable 516 -> 493",
            "Operator-CI bucket 96 -> 73",
            "264 characters",
            "244",
            "minimalreadonlygateoperatorciconsumerpackage");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1846",
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

  @Test
  void currentCatalogEvidencePrecedesVerify() throws IOException {
    assertThat(read(CURRENT_DOC))
        .contains(
            "Requirement Evidence Matrix",
            "1/5/4/5/5/5/8/5/8/9/28",
            "1ae92cfe8926ecb9ae772c8eec70dd8cddfbc1b0654e11685ef6304249803c60",
            "31 项、新增 0 项",
            "1,705 files / 20,282,267 raw bytes");
    assertThat(read(CURRENT_WALKTHROUGH))
        .contains("v1894", "禁止硬凑", "本项目", "## 实际工作量说明", "## 一句话总结");
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
