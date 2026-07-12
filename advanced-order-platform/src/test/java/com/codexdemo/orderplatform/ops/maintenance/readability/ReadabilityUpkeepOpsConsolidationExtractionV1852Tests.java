package com.codexdemo.orderplatform.ops.maintenance.readability;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class ReadabilityUpkeepOpsConsolidationExtractionV1852Tests {

  private static final Path OPS_ROOT =
      Path.of("src", "main", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "readonlyevidence"));
  private static final Path READINESS_CORE_ROOT =
      OPS_ROOT.resolve(Path.of("maintenance", "readinesscore"));
  private static final Path TEST_ROOT =
      Path.of("src", "test", "java", "com", "codexdemo", "orderplatform", "ops");
  private static final Path PACKAGE_TEST_ROOT =
      TEST_ROOT.resolve(Path.of("maintenance", "readonlyevidence"));
  private static final Path DOC =
      Path.of("docs", "ops", "read-only-evidence-catalog-extraction-v1852.md");
  private static final Path WALKTHROUGH =
      Path.of(
          "代码讲解记录_生产雏形阶段6",
          "v1848-v1852",
          "version-1852-production-excellence-read-only-evidence-catalog-extraction.md");
  private static final String PACKAGE_IMPORT = "ops.maintenance.readonlyevidence";
  private static final List<String> MOVED_FILES =
      List.of(
          "OpsShardReadinessReadOnlyEndpointRegistryIntegrityResponse.java",
          "OpsShardReadinessReadOnlyEndpointRegistryIntegrityService.java",
          "OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffResponse.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffService.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationResponse.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationService.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogResponse.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogService.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogSnapshot.java");
  private static final List<String> MOVED_TEST_FILES =
      List.of(
          "OpsShardReadinessReadOnlyEndpointRegistryIntegrityServiceTests.java",
          "OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshotTests.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffServiceTests.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationServiceTests.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshotTests.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogServiceTests.java",
          "OpsShardReadinessReadOnlyEvidenceCatalogSnapshotTests.java",
          "OpsShardReadinessReadOnlyEvidenceTestSupport.java");
  private static final List<String> SERVICE_PREFIXES =
      List.of(
          "OpsShardReadinessReadOnlyEndpointRegistryIntegrity",
          "OpsShardReadinessReadOnlyEvidenceCatalog",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoff",
          "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerification");
  private static final List<String> CORE_SERVICES =
      List.of(
          "OpsShardReadinessService",
          "OpsShardReadinessHardeningService",
          "OpsShardReadinessEchoService",
          "OpsShardReadinessEvidenceIndexService",
          "OpsShardReadinessEvidenceVerificationService",
          "OpsShardReadinessEvidenceHandoffService",
          "OpsShardReadinessActiveShardPlanHandoffService",
          "OpsShardReadinessLiveReadGatePlanService",
          "OpsShardReadinessOperatorServiceLifecycleService",
          "OpsShardReadinessDeclaredOperatorLifecycleService");

  @Test
  void readOnlyEvidenceClosureMovesWhileWebAdapterStaysRoot() throws IOException {
    assertThat(MOVED_FILES).hasSize(11);
    for (String file : MOVED_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(OPS_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(11);
    }

    Path controller = OPS_ROOT.resolve("OpsShardReadinessReadOnlyEvidenceController.java");
    assertThat(Files.isRegularFile(controller)).isTrue();
    assertThat(read(controller)).contains(PACKAGE_IMPORT);
  }

  @Test
  void packageTestsAndSupportMoveWhileControllerSplitTestStaysRoot() throws IOException {
    assertThat(MOVED_TEST_FILES).hasSize(8);
    for (String file : MOVED_TEST_FILES) {
      assertThat(Files.isRegularFile(PACKAGE_TEST_ROOT.resolve(file))).as(file).isTrue();
      assertThat(Files.exists(TEST_ROOT.resolve(file))).as(file).isFalse();
    }
    try (Stream<Path> files = Files.list(PACKAGE_TEST_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(8);
    }
    Path controllerTest =
        TEST_ROOT.resolve("OpsShardReadinessReadOnlyEvidenceControllerSplitTests.java");
    assertThat(Files.isRegularFile(controllerTest)).isTrue();
    assertThat(read(controllerTest))
        .contains(
            "OpsShardReadinessReadOnlyEvidenceController.class",
            "/read-only-evidence-catalog",
            "/read-only-evidence-catalog-handoff",
            "/read-only-evidence-catalog-handoff-verification",
            "/read-only-endpoint-registry-integrity");
  }

  @Test
  void snapshotsStayPrivateAndExternalTestsUseTheNarrowTestSupport() throws IOException {
    for (String snapshot :
        List.of(
            "OpsShardReadinessReadOnlyEvidenceCatalogSnapshot.java",
            "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.java",
            "OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.java")) {
      assertThat(read(PACKAGE_ROOT.resolve(snapshot)))
          .as(snapshot)
          .contains("final class ")
          .doesNotContain("public final class");
    }

    String testSupport =
        read(PACKAGE_TEST_ROOT.resolve("OpsShardReadinessReadOnlyEvidenceTestSupport.java"));
    assertThat(testSupport)
        .contains(
            "public final class OpsShardReadinessReadOnlyEvidenceTestSupport",
            "v175LiveEndpoints()",
            "v179LiveEndpoints()",
            "v184LiveEndpoints()",
            "catalogService()",
            "handoffVerificationService()");
    String historical =
        read(
            TEST_ROOT.resolve(
                "OpsShardReadinessHistoricalEndpointSnapshotCompatibilityTests.java"));
    assertThat(historical)
        .contains("OpsShardReadinessReadOnlyEvidenceTestSupport")
        .doesNotContain(
            "OpsShardReadinessReadOnlyEvidenceCatalogHandoffVerificationSnapshot.",
            "OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.");

    String rootFactory = read(TEST_ROOT.resolve("OpsShardReadinessServiceGraphTestFactory.java"));
    assertThat(rootFactory)
        .contains("OpsShardReadinessReadOnlyEvidenceTestSupport")
        .doesNotContain("new OpsShardReadinessReadOnlyEvidenceCatalogService");
  }

  @Test
  void endpointPairAndRouteOwnershipRemainNarrow() throws IOException {
    String integritySnapshot =
        read(
            PACKAGE_ROOT.resolve(
                "OpsShardReadinessReadOnlyEndpointRegistryIntegritySnapshot.java"));
    assertThat(integritySnapshot)
        .contains("record EndpointPair(String liveEndpoint, String fixtureEndpoint)")
        .doesNotContain("OpsShardReadinessEvidenceEndpoints");

    String routePaths = read(OPS_ROOT.resolve("OpsShardReadinessRoutePaths.java"));
    assertThat(routePaths)
        .contains(
            "public static final String READ_ONLY_EVIDENCE_CATALOG",
            "public static final String READ_ONLY_EVIDENCE_CATALOG_HANDOFF",
            "public static final String READ_ONLY_EVIDENCE_CATALOG_HANDOFF_VERIFICATION",
            "public static final String READ_ONLY_ENDPOINT_REGISTRY_INTEGRITY");
    for (String prefix : SERVICE_PREFIXES) {
      assertThat(read(PACKAGE_ROOT.resolve(prefix + "Service.java")))
          .as(prefix)
          .contains(
              "OpsShardReadinessRoutePaths",
              "public static final String ENDPOINT",
              "public static final String FIXTURE_ENDPOINT",
              "public static final String EVIDENCE_PATH");
    }
  }

  @Test
  void immutableCoreEvidenceReferencesAreExplicitAcrossThePackageBoundary() throws IOException {
    for (String service : CORE_SERVICES) {
      assertThat(read(READINESS_CORE_ROOT.resolve(service + ".java")))
          .as(service)
          .contains(
              "public static final String ENDPOINT",
              "public static final String FIXTURE_ENDPOINT",
              "public static final String EVIDENCE_PATH");
    }
  }

  @Test
  void spotbugsAndShrinkOnlyRatchetsFollowTheMove() throws IOException {
    String spotbugs = read(Path.of("config", "spotbugs-exclude.xml"));
    for (String prefix : SERVICE_PREFIXES) {
      String response = prefix + "Response";
      assertThat(spotbugs)
          .contains(PACKAGE_IMPORT + "." + response)
          .doesNotContain("com.codexdemo.orderplatform.ops." + response);
    }
    try (Stream<Path> files = Files.list(OPS_ROOT)) {
      assertThat(files.filter(Files::isRegularFile).filter(this::isJava)).hasSize(249);
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
            "Direct root 482 -> 471",
            "movable 377 -> 366",
            "ReadOnlyEvidence bucket 11 -> 0",
            "183/188",
            "readonlyevidence");
    assertThat(read(WALKTHROUGH))
        .contains(
            "version-1852",
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
