package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1846Tests {

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
  private static final String PACKAGE_IMPORT =
      "ops.maintenance.minimalreadonlygateoperatorciconsumerpackage";
  private static final List<String> MOVED_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAcceptanceRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAudienceCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageAudienceRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageBoundaryLockCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageBoundaryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageChecklistRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageCiMatrixRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageManifestRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryResponse.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryService.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRendererSupport.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageScorecardRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSectionCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSectionRenderer.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceDigestCatalog.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageSourceRenderer.java");
  private static final List<String> MOVED_TEST_FILES =
      List.of(
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryBoundaryChecklistTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryCatalogTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistrySourceTests.java",
          "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryTestSupport.java");

  @Test
  void consumerPackageMovesWhileControllerStaysRootVisible() throws IOException {
    assertThat(MOVED_FILES).hasSize(23);
    for (String file : MOVED_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(23);
    }
    Path controller =
        OPS_ROOT.resolve(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageRegistryController.java");
    assertThat(Files.isRegularFile(controller)).isTrue();
    assertThat(read(controller)).contains(PACKAGE_IMPORT);
  }

  @Test
  void packageTestsMoveWhileControllerMarkdownAggregateStaysRoot() throws IOException {
    assertThat(MOVED_TEST_FILES).hasSize(4);
    for (String file : MOVED_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(4);
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
            "OpsShardReadinessReleaseAcceptanceRoutePaths");
  }

  @Test
  void verificationDossierImportsTheMovedConsumerPackageBoundary() throws IOException {
    Path dossierRoot = OPS_ROOT.resolve(Path.of("maintenance", "operatorcidossier"));
    for (String file :
        List.of(
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAcceptanceGateCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierAudienceRouteCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierBoundaryAuditCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierCiLaneCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierHandoffReceiptCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierProvenanceCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistryService.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierRegistrySupport.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierReleaseChecklistCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierScorecardCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSectionDigestCatalog.java",
            "OpsShardReadinessMinimalReadOnlyGateOperatorCiHandoffArchiveDigestConsumerPackageVerificationDossierSourcePackageCatalog.java")) {
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
          .hasSizeLessThanOrEqualTo(1352);
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

  private boolean isJava(Path path) {
    return path.toString().endsWith(".java");
  }

  private static String read(Path path) throws IOException {
    return Files.readString(path, StandardCharsets.UTF_8);
  }
}
